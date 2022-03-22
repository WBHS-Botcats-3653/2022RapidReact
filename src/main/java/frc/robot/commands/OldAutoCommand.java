package frc.robot.commands;

import static frc.robot.Constants.AutoConstants.*;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.autoCommands.*;
import frc.robot.subsystems.Direction;

public class OldAutoCommand extends CommandBase {
	private final Direction m_direction = Direction.getInstance();

	/*Which Tarmac the robot is starting in (defaults to left tarmac)
	*"L"=Left, "R"=Right
	*/
	private char startingTarmac;

	//Which cargo the robot should target during autonomous
	private ArrayList<String> cargoToTarget;

	public static boolean executingCommand;

	private boolean hasFinished;
	private int cargoTargetIndex;

	public static boolean isAutoShootOn, isAutoTaxiOn, isAutoCollectOn;

	public static char commandToScheduleNext;  //'S' = Sequential command / 'P' = Parallel command
	private SequentialCommandGroup sequentialCommandToSchedule;
	private ParallelCommandGroup parallelCommandToSchedule;

	//Constructor
	public OldAutoCommand() {
		hasFinished = false;
		executingCommand = false;
	}

	//Returns the next SequentialCommandGroup to be scheduled
	public SequentialCommandGroup getSequentialCommand() {
		SequentialCommandGroup cmd = sequentialCommandToSchedule;
		sequentialCommandToSchedule = null;
		return cmd;
	}

	//Returns the next ParallelCommandGroup to be scheduled
	public ParallelCommandGroup getParallelCommand() {
		ParallelCommandGroup cmd = parallelCommandToSchedule;
		parallelCommandToSchedule = null;
		return cmd;
	}

	//Sets the tarmax the robot is starting in
	public void setTarmac(char tarmac) {
		startingTarmac = tarmac;
	}

	//Sets the cargo the robot should target during auto
	public void setCargoToTarget(ArrayList<String> cargo) {
		cargoToTarget = cargo;
	}

	@Override
	public void initialize() {
		//Resets the encoders
		m_direction.resetEncoders();
		//Target first selected cargo
		cargoTargetIndex = 0;
		//Schedules preload shoot and taxi if applicable
		commandToScheduleNext = 'S';
		sequentialCommandToSchedule = new SequentialCommandGroup(
			isAutoShootOn ? new ShootCargoCommand(1) : new PrintCommand("Auto shoot preload disabled"),   //Shoots Preload
			isAutoTaxiOn || isAutoCollectOn ? new DriveCommand(kTaxiDistance, kAutoDriveSpeed, false) : new PrintCommand ("Taxi is disabled"),
			new InstantCommand(() -> OldAutoCommand.executingCommand = false)  //Completed executing a sequential command
		);
	}

	@Override
	public void execute() {
		//If currently executing an auto command break out of the method
		if (executingCommand) return;
		if (!isAutoCollectOn || cargoTargetIndex == cargoToTarget.size()) {  //If collect cargo is disabled in the Dashboard or there is no more cargo left to collect
			//Ends auto command
			hasFinished = true;
			//Breaks out of the switch
			return;
		}
		/*The angle and distance the robot needs to move to get to the desired cargo
		*double[angle, distance]
		*/
		double[] angleAndDistance = kAnglesAndDistances.get(startingTarmac + cargoToTarget.get(cargoTargetIndex));
		if (cargoTargetIndex == 0) {
			//If there is no distance or angle to move skip this cargo
			if (angleAndDistance[0] != 0 && angleAndDistance[1] != 0) {
				commandToScheduleNext = 'S';
				//Creates a new sequential command to be scheduled
				sequentialCommandToSchedule = new SequentialCommandGroup(
					//Turn specified distance
					new TurnCommand(angleAndDistance[0]),
					//Execute parallel command next
					new InstantCommand(() -> OldAutoCommand.commandToScheduleNext = 'P'),
					//End command
					new InstantCommand(() -> OldAutoCommand.executingCommand = false)
				);
				//Creates a new parallel command to be scheduled (Collect cargo)
				parallelCommandToSchedule = new ParallelCommandGroup(
					//Drive specified distance
					new DriveCommand(angleAndDistance[1], kAutoDriveSpeed, true),
					//Intake the cargo
					new CollectCargoCommand(),
					//Execute parallel command next
					new InstantCommand(() -> OldAutoCommand.commandToScheduleNext = 'S')
				);
			}
		} else {
			double[] prevAngleAndDistance = kAnglesAndDistances.get(startingTarmac + cargoToTarget.get(cargoTargetIndex - 1));
			double a = prevAngleAndDistance[1];
			double b = angleAndDistance[1];
			double C = prevAngleAndDistance[0] - angleAndDistance[0];
			double dist = FindMissingSide(a, b, C);
			double ang = FindMissingAngle(a, b, dist);
			commandToScheduleNext = 'S';
			//Creates a new sequential command to be scheduled
			sequentialCommandToSchedule = new SequentialCommandGroup(
				//Turn specified distance
				new TurnCommand(dist),
				//Execute parallel command next
				new InstantCommand(() -> OldAutoCommand.commandToScheduleNext = 'P'),
				//End command
				new InstantCommand(() -> OldAutoCommand.executingCommand = false)
			);
			//Creates a new parallel command to be scheduled (Collect cargo)
			parallelCommandToSchedule = new ParallelCommandGroup(
				//Drive specified distance
				new DriveCommand(ang, kAutoDriveSpeed, true),
				//Intake the cargo
				new CollectCargoCommand(),
				//Execute parallel command next
				new InstantCommand(() -> OldAutoCommand.commandToScheduleNext = 'S')
			);
		}
		//Target next cargo
		cargoTargetIndex++;
	}

	/**Return the missing side of a scalene triangle
	 *@param a Side a length
	 *@param b Side b length
	 *@param C Angle C in degrees
	 *@return Length of side c
	 */
	public double FindMissingSide(double a, double b, double C) {
		return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2) - 2 * a * b * Math.cos(C));
	}

	/**Finds the missing angle of a scalemne triangle
	 *@param a Side a length
	 *@param b Side b length
	 *@param c Side c length
	 *@return Angle C in degrees
	 */
	public double FindMissingAngle(double a, double b, double c) {
		return Math.acos((Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2)) / 2 * a * b);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		m_direction.resetEncoders();
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		//If auto has finished
		return hasFinished;
	}
	//esteban is driving us nuts :):)
}
