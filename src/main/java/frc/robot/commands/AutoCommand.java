// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.AutoConstants.*;
import static frc.robot.Constants.DrivePIDConstants.*;

import java.util.*;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.*;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.NetworkEntries;
import frc.robot.commands.autoCommands.CollectCargoCommand;
import frc.robot.commands.autoCommands.ShootCargoCommand;
import frc.robot.subsystems.*;

public class AutoCommand extends CommandBase {
	private static final DrivePID m_drivePID = DrivePID.getInstance();
	private static final Drivetrain m_drivetrain = Drivetrain.getInstance();
	private static final Direction m_direction = Direction.getInstance();

	private static final RamseteCommand taxiRamCommand = generateTrajectory(
		Units.feetToMeters(2.0),  //Max velocity (Meters per second)
		Units.feetToMeters(2.0),  //Max acceleration (Meters per seconds squared)
		Arrays.asList(
			new Pose2d(),  //Starting position
			new Pose2d(0, Units.feetToMeters(kTaxiDistance), new Rotation2d())  //End position
		)
	);
	private static RamseteCommand customRamCommand, collectCargoRamCommand;

	private static int numCargoToCollect = 0;

	private char commandToScheduleNext;
	private static boolean executingCommand = false;

	private boolean shootPreloadEnabled, hasPreload, taxiEnabled, customTrajectoryEnabled, collectCargoEnabled, shootCollectedCargoEnabled;

	private int collectStage = 0;

	private SequentialCommandGroup sequential;
	private ParallelCommandGroup parallel;

	private static boolean hasFinished = false;
		
	/** Creates a new AutoCommand. */
	public AutoCommand() {}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		//Gets user selections
		hasPreload = NetworkEntries.m_nteHasPreload.getBoolean(false);
		shootPreloadEnabled = NetworkEntries.m_nteShootPreloadSelected.getBoolean(false);
		taxiEnabled = NetworkEntries.m_nteTaxiSelected.getBoolean(false);
		customTrajectoryEnabled = NetworkEntries.m_nteCustomTrajectorySelected.getBoolean(false);
		collectCargoEnabled = NetworkEntries.m_nteCollectCargoSelected.getBoolean(false);
		shootCollectedCargoEnabled = NetworkEntries.m_nteShootCollectedCargoSelected.getBoolean(false);

		//If the custom or collect cargo trajectories have not been generated set their execution to false
		if (customRamCommand == null) customTrajectoryEnabled = false;
		if (collectCargoRamCommand == null) collectCargoEnabled = false;

		//Resets the encoders to ensure the robot starts zeroed
		m_direction.resetEncoders();
		//Next command to be schedule will be a SequentialCommandGroup
		commandToScheduleNext = 'S';
		//Auto commands to be scheduled sequentially
		sequential = new SequentialCommandGroup(
			shootPreloadEnabled ? new ShootCargoCommand(1) : new PrintCommand("Auto shoot disabled"),  //Shoot preload
			taxiEnabled ? (customTrajectoryEnabled ? customRamCommand : taxiRamCommand) : new PrintCommand("Taxi and custom trajectory disabled"),  //Taxi and custom trajectory
			new InstantCommand(() -> executingCommand = false)  //Has finished executing the SequentialCommandGroup
		);
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//If currently executing an auto command break out of the method
		if (executingCommand) return;
		//If collect cargo is disabled
		if (!collectCargoEnabled) {
			//Auto has finished
			hasFinished = true;
			//Break out of the method
			return;
		}
		switch (collectStage) {  //The current stage of the cargo collection and shooting process
			case (0): //Collecting cargo
				//The next command to be scheduled will be a ParallelCommandGroup
				commandToScheduleNext = 'P';
				//Auto commands to be scheduled parallel
				parallel = new ParallelCommandGroup(
					new CollectCargoCommand(),  //Intake cargo
					collectCargoRamCommand  //Drive along tajectory where cargo will be located
				);
				//Switch to the next stage of auto collection
				collectStage++;
				//Break out of the switch
				break;
			case (1):  //Shooting cargo
				//If the robot is moving
				if (m_direction.isMoving()) {
					//Break out of the switch
					break;
				} else {  //If the robot is not moving
					//Not currently executing an auto command
					executingCommand = false;
				}
				//If shoot collected cargo is disabled
				if (!shootCollectedCargoEnabled) {
					//Auto has finished
					hasFinished = true;
					//Break out of the switch
					break;
				}
				//The next command to be scheduled will be a SequentialCommandGroup
				commandToScheduleNext = 'S';
				//Auto commands to be scheduled sequentially
				sequential = new SequentialCommandGroup(
					new ShootCargoCommand(numCargoToCollect + ((hasPreload && !shootPreloadEnabled) ? 1 : 0)),  //Shoot cargo (The amount of cargo to shoot is the number of cargo collected plus any preloaded cargo if it has not already been shot)
					new InstantCommand(() -> hasFinished = true),  //Auto has finished
					new InstantCommand(() -> executingCommand = false)  //Has finished executing the sequential command
				);
				//Break out of the switch
				break;
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		//Stop the drivetrain
		m_drivetrain.arcadeDrive(0, 0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		//Return whether all auto commands have finished
		return hasFinished;
	}

	/** Generates a new trajectory for the robot to follow during the autonomous period
	 * @param maxVelocityMetersPerSecond The max velocity the robot can travel
	 * @param maxAccelerationMetersPerSecondSq The max the robot can accelerate
	 * @param waypoints The points the robot will travel through during the trajectory
	 * @return RamseteCommand to be executed by the scheduler
	 */
	public static RamseteCommand generateTrajectory(double maxVelocityMetersPerSecond, double maxAccelerationMetersPerSecondSq, List<Pose2d> waypoints) {
		//Creates a trajectory config with the given max velocity and acceleration
		TrajectoryConfig config = new TrajectoryConfig(maxVelocityMetersPerSecond, maxAccelerationMetersPerSecondSq);
		//Passes a Kinematics object to the trajectory config
		config.setKinematics(m_drivePID.getKinematics());
		//Creates a new trajectory with the given waypoints
		Trajectory trajectory = TrajectoryGenerator.generateTrajectory(waypoints, config);
		/*Takes the robots current position, trajectory, and wheel speeds along with other
		 *Objects, Suppliers, and BiConsumers and calculates the linear and angular velocities to
		 *move the robot in order to follow the path of the trajectory
		 */
		return new RamseteCommand(
			trajectory,  //Trajectory to follow
			m_drivePID::getPose,  //Method to supply the current position (Supplier)
			new RamseteController(kRamseteB, kRamseteZeta),  //Calculates the current linear and angular velocity of the robot
			m_drivePID.getFeedForward(),  //Gets SimpleMotorFeedForward which converts left and right wheel speeds to motor voltages
			m_drivePID.getKinematics(),  //Gets Kinematics which converts linear and angular velocity into left and right wheel speeds
			m_drivePID::getWheelSpeeds,  //Method to supply DifferentialDriveWheelSpeeds which contains the current left and right wheel speeds (Supplier)
			m_drivePID.getLeftPIDController(),  //Gets the left drivetrain PID controller which calculates the motor voltage required to smoothly get the robot to the desired endpoint
			m_drivePID.getRightPIDController(),  //Gets the right drivetrain PID controller which calculates the motor voltage required to smoothly get the robot to the desired endpoint
			m_drivetrain::tankDriveVolts,  //Method which sets the left and right motor voltages of the drivetrain (BiConsumer)
			m_drivePID,  //Required subsystem
			m_drivetrain,  //Required subsystem
			m_direction  //Required subsystem
		);
	}

	/** Generates a trajectory which passes through all the targeted cargo
	 * @param tarmac The tarmac the robot starts the match in
	 * @param cargo The cargo to be targeted
	 */
	public static void generateCargoCollectionTrajectory(char tarmac, ArrayList<String> cargo) {
		ArrayList<Pose2d> waypoints = new ArrayList<>();
		//Starting waypoint
		waypoints.add(new Pose2d());
		//Traverse the cargo pieces to be targeted
		for (String c : cargo) {
			//Stores the coordinates of the cargo
			final Pose2d coords = kCargoCoordinates.get(tarmac + c);
			//If the cargo is inaccessable from the starting Tarmac skip to the next piece of cargo
			if (coords == null) continue;
			//Get the waypoint for each piece of cargo
			waypoints.add(coords);
		}
		//Add the trajectory's return middle waypoint (used to avoid hitting any potential obstacles)
		waypoints.add(tarmac == 'L' ? kReturnMiddleWaypointL : kReturnMiddleWaypointR);
		//Add the trajectory's end waypoint (where it will shoot from in front of the Hub)
		waypoints.add(new Pose2d());  //Starting position
		//Generate the trajectory
		collectCargoRamCommand = generateTrajectory(
			Units.feetToMeters(2),  //Max velocity (meters per second)
			Units.feetToMeters(2),  //Max acceleration (meters per second squared)
			waypoints  //Waypoints
		);
		//Sets the number of cargo to be collected
		numCargoToCollect = cargo.size();
	}

	/** Generates a new custom trajectory for the robot to follow during the autonomous period
	 * @param maxVelocityMetersPerSecond The max velocity the robot can travel
	 * @param maxAccelerationMetersPerSecondSq The max the robot can accelerate
	 * @param x The distance the robot should move along the x-axis (Feet)
	 * @param y The distance the robot should move along the y-axis (Feet)
	 * @param endAngle The angle the robot should end the trajectory at (Degrees)
	 */
	public static void generateCustomTrajectory(double maxVelocityMetersPerSecond, double maxAccelerationMetersPerSecondSq, double x, double y, double endAngle) {
		//Converts x and y from feet to meters
		x = Units.feetToMeters(x);
		y = Units.feetToMeters(y);
		//Converts the angle from degrees to radians
		endAngle = Units.degreesToRadians(-endAngle);
		//Generates a RamseteCommand with the custom trajectory
		customRamCommand = generateTrajectory(
			maxVelocityMetersPerSecond,  //Max velocity (meters per second)
			maxAccelerationMetersPerSecondSq,  //Max acceleration (meters per seconds squared)
			Arrays.asList(
				new Pose2d(),  //Start position
				new Pose2d(x, y, new Rotation2d(endAngle))  //End position
			)
		);
	}

	//Returns where an auto command is currently being executed
	public boolean getExecutingCommand() {return executingCommand;}

	//Sets whether an auto command is currently being executed
	public static void setExecutingCommand(boolean isExecuting) {executingCommand = isExecuting;}

	//Returns the type of command which is to be scheduled next
	public char getCommandToScheduleNext() {return commandToScheduleNext;}

	//Returns the SequentialCommandGroup to be scheduled
	public SequentialCommandGroup getSequentialCommandGroup() {
		SequentialCommandGroup command = sequential;
		sequential = null;
		return command;
	}

	//Returns the ParallelCommandGroup to be scheduled
	public ParallelCommandGroup getParallelCommandGroup() {
		ParallelCommandGroup command = parallel;
		parallel = null;
		return command;
	}

	//Returns whether all of the auto commands have finished executing
	public boolean hasFinished() {return hasFinished;}
}
