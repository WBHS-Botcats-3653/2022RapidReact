package frc.robot.commands;

import static frc.robot.Constants.AutoConstants.kAutoDriveSpeed;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.autoCommands.CollectCargoCommand;
import frc.robot.commands.autoCommands.ShootCargoCommand;
import frc.robot.subsystems.Direction;

public class AutoCommand extends CommandBase {
	private Direction m_direction;

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
	public AutoCommand() {
		m_direction = Direction.getInstance();
		
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
			isAutoShootOn ? new ShootCargoCommand() : new PrintCommand("Auto shoot preload disabled"),   //Shoots Preload
			isAutoTaxiOn || isAutoCollectOn ? new DriveCommand(kTaxiDistanceInInches, kAutoDriveSpeed, false) : new PrintCommand ("Taxi is disabled"),
			new InstantCommand(() -> {AutoCommand.executingCommand = false;})  //Completed executing a sequential command
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
		double[] distanceAndAngle = kAnglesAndDistances.get(startingTarmac + cargoToTarget.get(cargoTargetIndex));
		//If there is no distance or angle to move skip this cargo
		if (distanceAndAngle[0] != 0 && distanceAndAngle[1] != 0) {
			commandToScheduleNext = 'S';
			//Creates a new sequential command to be scheduled
			sequentialCommandToSchedule = new SequentialCommandGroup(
				//Turn specified distance
				new TurnCommand(distanceAndAngle[0]),
				//Execute parallel command next
				new InstantCommand(() -> {AutoCommand.commandToScheduleNext = 'P';}),
				//End command
				new InstantCommand(() -> {AutoCommand.executingCommand = false;})
			);
			//Creates a new parallel command to be scheduled (Collect cargo)
			parallelCommandToSchedule = new ParallelCommandGroup(
				//Drive specified distance
				new DriveCommand(distanceAndAngle[1], kAutoDriveSpeed, true),
				//Intake the cargo
				new CollectCargoCommand(),
				//Execute parallel command next
				new InstantCommand(() -> {AutoCommand.commandToScheduleNext = 'S';})
			);
		}
		//Target next cargo
		cargoTargetIndex++;
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
