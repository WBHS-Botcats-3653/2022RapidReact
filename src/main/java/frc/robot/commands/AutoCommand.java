package frc.robot.commands;

import static frc.robot.Constants.AutoConstants.*;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.NetworkEntries;
import frc.robot.commands.autoCommands.*;
import frc.robot.subsystems.Direction;

public class AutoCommand extends CommandBase {
	private Direction m_direction;

	/*Which Tarmac the robot is starting in
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

	@Override
	public void initialize() {
		//Gets user selections
		isAutoShootOn = NetworkEntries.m_nteShootPreloadSelected.getBoolean(false);
		isAutoTaxiOn = NetworkEntries.m_nteTaxiSelected.getBoolean(false);
		isAutoCollectOn = NetworkEntries.m_nteCollectCargoSelected.getBoolean(false);

		//Resets the encoders
		m_direction.resetEncoders();
		//Target first selected cargo
		cargoTargetIndex = 0;
		//Schedules preload shoot and taxi if applicable
		commandToScheduleNext = 'S';
		sequentialCommandToSchedule = new SequentialCommandGroup(
			isAutoShootOn ? new ShootCargoCommand() : new PrintCommand("Auto shoot preload disabled"),   //Shoots Preload
			isAutoTaxiOn || isAutoCollectOn ? new DriveCommand(kTaxiDistance, kAutoDriveSpeed, false) : new PrintCommand ("Taxi is disabled"),  //Taxi
			new InstantCommand(() -> {AutoCommand.executingCommand = false;})  //Completed executing a sequential command
		);
	}

	@Override
	public void execute() {
		//If currently executing an auto command break out of the method
		if (executingCommand) return;
		if (!isAutoCollectOn || cargoTargetIndex >= cargoToTarget.size()) {  //If collect cargo is disabled in the Dashboard or there is no more cargo left to collect
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

	//Returns the next SequentialCommandGroup to be scheduled
	public SequentialCommandGroup getSequentialCommandGroup() {
		SequentialCommandGroup cmd = sequentialCommandToSchedule;
		sequentialCommandToSchedule = null;
		return cmd;
	}

	//Returns the next ParallelCommandGroup to be scheduled
	public ParallelCommandGroup getParallelCommandGroup() {
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

	/** Returns where an auto command is currently being executed
	 * @return whether a command group is currently being executed in the scheduler
	 */
	public boolean getExecutingCommand() {return executingCommand;}

	/** Sets whether an auto command is currently being executed
	 * @param isExecuting whether a command group is currently being executed by the scheduler
	 */
	public static void setExecutingCommand(boolean isExecuting) {executingCommand = isExecuting;}

	/** Returns the type of command which is to be scheduled next
	 * @return either 'S' for Sequential or 'P' for Parallel indicating which command group is to be scheduled next
	 */
	public char getCommandToScheduleNext() {return commandToScheduleNext;}
}
