// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.DrivePIDConstants.*;

import java.util.Arrays;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
	private RobotContainer m_robotContainer;

	//Subsystems
	private final Dashboard m_dashboard = Dashboard.getInstance();
	private final Direction m_direction = Direction.getInstance();
	private final DrivePID m_drivePID = DrivePID.getInstance();
	private final Drivetrain m_drivetrain = Drivetrain.getInstance();
	private final Intake m_intake = Intake.getInstance();
	private final Indexer m_indexer = Indexer.getInstance();
	private final Shooter m_shooter = Shooter.getInstance();
	private final Climber m_climber = Climber.getInstance();

	//Commands
	private AutoCommand m_autoCommand;
	private final ArcadeDriveCommand m_arcadeDriveCommand = new ArcadeDriveCommand(m_drivetrain);
	private final IntakeCommand m_intakeCommand = new IntakeCommand(m_intake);
	private final IndexerCommand m_indexerCommand = new IndexerCommand(m_indexer);
	private final ShooterCommand m_shooterCommand = new ShooterCommand(m_shooter);
	private final ClimberCommand m_climberCommand = new ClimberCommand(m_climber);
	
	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	@Override
	public void robotInit() {
		// Instantiate our RobotContainer.  This will perform all our button bindings, and put our
		// autonomous chooser on the dashboard.
		m_robotContainer = new RobotContainer();
		//Calibrates the gyro
		m_direction.calibrateGyro();
		//Resets the encoders
		m_direction.resetEncoders();
	}

	/**
	 * This function is called every robot packet, no matter the mode. Use this for items like
	 * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
	 *
	 * <p>This runs after the mode specific periodic functions, but before LiveWindow and
	 * SmartDashboard integrated updating.
	 */
	@Override
	public void robotPeriodic() {
		// Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
		// commands, running already-scheduled commands, removing finished or interrupted commands,
		// and running subsystem periodic() methods.  This must be called from the robot's periodic
		// block in order for anything in the Command-based framework to work.
		CommandScheduler.getInstance().run();
	}

	/** This function is called once each time the robot enters Disabled mode. */
	@Override
	public void disabledInit() {
		// Cancels all running commands when disabled.
		CommandScheduler.getInstance().cancelAll();

		//Ends the smart intake
		IntakeCommand.endSmartIntake();

		//Sets motors to coast or brake
		m_drivetrain.enableBrake(false);
		m_indexer.enableBrake(false);

		//Disables the speeds
		m_dashboard.disableSpeeds(true);
	}

	/** This function is called periodically when disabled. */
	@Override
	public void disabledPeriodic() {}

	/** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
	@Override
	public void autonomousInit() {
		//Sets motors to brake
		m_drivetrain.enableBrake(true);
		m_indexer.enableBrake(true);

		//Gets the autonomous command from robotContainer
		m_autoCommand = m_robotContainer.getAutonomousCommand();

		// Schedules the autonomous command
		if (m_autoCommand != null) {
			CommandScheduler.getInstance().schedule(m_autoCommand);
		}

		//Disables the speeds
		m_dashboard.disableSpeeds(true);
	}

	/** This function is called periodically during autonomous. */
	@Override
	public void autonomousPeriodic() {
		//Schedules sequential and parallel command groups fed from the AutoCommand
		if (m_autoCommand.hasFinished()) return;
		if (m_autoCommand.getExecutingCommand()) return;
		SequentialCommandGroup sequentialCommand = m_autoCommand.getSequentialCommandGroup();
		ParallelCommandGroup parallelCommand = m_autoCommand.getParallelCommandGroup();
		if (m_autoCommand.getCommandToScheduleNext() == 'S' && sequentialCommand != null) {
			AutoCommand.setExecutingCommand(true);
			CommandScheduler.getInstance().schedule(sequentialCommand);
		} else if (m_autoCommand.getCommandToScheduleNext() == 'P' && parallelCommand != null) {
			AutoCommand.setExecutingCommand(true);
			CommandScheduler.getInstance().schedule(parallelCommand);
		}
	} 

	/** This function is called once each time the robot enters Teleoperated mode. */
	@Override
	public void teleopInit() {
		// Cancels all running commands at the start of teleop.
		CommandScheduler.getInstance().cancelAll();

		//Sets motors to coast or brake
		m_drivetrain.enableBrake(false);
		m_indexer.enableBrake(true);

		// Cancels all running commands at the start of Teleoperated mode.
		CommandScheduler.getInstance().cancelAll();

		//Resets the encoders
		m_direction.resetEncoders();

		CommandScheduler.getInstance().schedule(m_arcadeDriveCommand);
		CommandScheduler.getInstance().schedule(m_intakeCommand);
		CommandScheduler.getInstance().schedule(m_indexerCommand);
		CommandScheduler.getInstance().schedule(m_shooterCommand);
		CommandScheduler.getInstance().schedule(m_climberCommand);

		//Enables the speeds
		m_dashboard.disableSpeeds(false);
	}

	/** This function is called periodically during operator control. */
	@Override
	public void teleopPeriodic() {}

	/** This function is called once each time the robot enters Test mode. */
	@Override
	public void testInit() {
		// Cancels all running commands at the start of test mode.
		CommandScheduler.getInstance().cancelAll();

		//Sets motors to coast or brake
		m_drivetrain.enableBrake(false);
		m_indexer.enableBrake(true);
		//Creates a trajectory configuration with a max velocity of 2 meters per second and a max acceleration of 2 meters per seconds squared
		TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(2), Units.feetToMeters(2));
		//Passes a Kinematics object to the trajectory config
		config.setKinematics(m_drivePID.getKinematics());
		//Test trajectory
		Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
			Arrays.asList(
				new Pose2d(),  //Starting position
				new Pose2d(1.0, 1.0, new Rotation2d())),  //End position
			config
		);
		/*Takes the robots current position, trajectory, and wheel speeds along with other
		 *Objects, Suppliers, and BiConsumers and calculates the linear and angular velocities to
		 *move the robot in order to follow the path of the trajectory
		 */
		RamseteCommand ramCommand = new RamseteCommand(
			trajectory,  //Trajectory to follow
			m_drivePID::getPose,  //Method to supply the current position (Supplier)
			new RamseteController(kRamseteB, kRamseteZeta),  //Calculates the current linear and angular velocity of the robot
			m_drivePID.getFeedForward(),  //Gets SimpleMotorFeedForward which converts left and right wheel speeds to motor voltages
			m_drivePID.getKinematics(),  //Gets Kinematics which converts linear and angular velocity into left and right wheel speeds
			m_drivePID::getWheelSpeeds,  //Method to supply DifferentialDriveWheelSpeeds which contains the left and right wheel speeds (Supplier)
			m_drivePID.getLeftPIDController(),  //Gets the left drivetrain PID controller which calculates the motor voltage required to smoothly get the robot to the desired endpoint
			m_drivePID.getRightPIDController(),  //Gets the right drivetrain PID controller which calculates the motor voltage required to smoothly get the robot to the desired endpoint
			m_drivetrain::tankDriveVolts,  //Method which sets the left and right motor voltages of the drivetrain (BiConsumer)
			m_drivePID,  //Required subsystem
			m_drivetrain,  //Required subsystem
			m_direction  //Required subsystem
		);

		//Schedule the command
		CommandScheduler.getInstance().schedule(ramCommand);
	}

	/** This function is called periodically during test mode. */
	@Override
	public void testPeriodic() {}
}
