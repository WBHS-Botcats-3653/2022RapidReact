package frc.robot.commands;

import static frc.robot.Constants.AutoConstants.*;

import java.util.*;

import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.autoCommands.*;
import frc.robot.inputs.OI;
import frc.robot.subsystems.Direction;

public class AutoCommand extends CommandBase {
	//private DriveTrain driveTrain;
	//private Shooter m_shooter;
	//private Indexer m_indexer;
	private Direction m_direction;
	private OI m_oi;
	//private SI m_si;


	/*Which Tarmac the robot is starting in (defaults to left tarmac)
	*"L"=Left, "R"=Right
	*/
	private char startingTarmac;

	//Which cargo the robot should target during autonomous
	private ArrayList<String> cargoToTarget = new ArrayList<>();

	public static boolean executingCommand;
	public static String stage;

	private boolean hasFinished;
	private int cargoTargetIndex;

	public static boolean isAutoShootOn;
	public static boolean isAutoTaxiOn;
	public static boolean isAutoCollectOn;

	public static String commandToScheduleNext;
	private SequentialCommandGroup sequentialCommandToSchedule;
	private ParallelCommandGroup parallelCommandToSchedule;

	//Constructor
	public AutoCommand() {
		//driveTrain = DriveTrain.getInstance();
		//m_shooter = Shooter.getInstance();
		//m_indexer = Indexer.getInstance();
		m_direction = Direction.getInstance();
		m_oi = OI.getInstance();
		//m_si = SI.getInstance();
		//kP = 1;
		hasFinished = false;
		executingCommand = false;
		commandToScheduleNext = "Sequential";
		sequentialCommandToSchedule = new SequentialCommandGroup(
			isAutoShootOn ? new ShootCargoCommand() : new PrintCommand("Auto shoot preload disabled"),   //Shoots Preload
			isAutoTaxiOn||isAutoCollectOn ? new DriveCommand(kTaxiDistanceInFeet, m_oi.getMaxDriveSpeed(), false) : new PrintCommand ("Taxi is disabled"),
			new InstantCommand(() -> {AutoCommand.stage = "Taxi";}),  //Switches to Taxi stage
			new InstantCommand(() -> {AutoCommand.executingCommand = false;})  //Completed executing a sequential command
		);
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
	public void setCargoToTarget(HashMap<String, Boolean> cargos) {
		for (Map.Entry<String, Boolean> cargo : cargos.entrySet()) {
			if (cargo.getValue()) {
				cargoToTarget.add(cargo.getKey());
			}
			if (cargoToTarget.size() == 2) return;
		}
	}

	@Override
	public void initialize() {
		//Target first selected cargo
		cargoTargetIndex = 0;
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
		/*The distance and angle the robot needs to move to get to the desired cargo
		 *double[angle, distance]
		 */
		double[] distanceAndAngle = kDistancesAndAngles.get(startingTarmac + cargoToTarget.get(cargoTargetIndex));
		//If there is no distance or angle to move skip this cargo
		if (distanceAndAngle[0] != 0 && distanceAndAngle[1] != 0) {
			commandToScheduleNext = "Sequential";
			//Creates a new sequential command to be scheduled
			sequentialCommandToSchedule = new SequentialCommandGroup(
				//Turn specified distance
				new TurnCommand(distanceAndAngle[0]),
				//Execute parallel command next
				new InstantCommand(() -> {AutoCommand.commandToScheduleNext = "Parallel";}),
				//End command
				new InstantCommand(() -> {AutoCommand.executingCommand = false;})
			);
			//Creates a new parallel command to be scheduled
			parallelCommandToSchedule = new ParallelCommandGroup(
				//Drive specified distance
				new DriveCommand(distanceAndAngle[1], m_oi.getMaxDriveSpeed(), true),
				//Intake the cargo
				new CollectCargoCommand(),
				//Execute parallel command next
				new InstantCommand(() -> {AutoCommand.commandToScheduleNext = "Sequential";})
			);
		}
		//Target next cargo
		cargoTargetIndex++;


		/*switch (stage) {
			case ("Shoot Preload"):  //Shoots the preload if enabled
				if (shootPreload(isAutoShootOn) || !isAutoShootOn) stage = "Taxi";
				break;
			case ("Taxi"):  //Moves (Taxis) the robot out of the Tarmac (starting area)
				if (taxiDrive(isAutoTaxiOn) || !isAutoTaxiOn) stage = "Collect Cargo";
				break;
			case ("Collect Cargo"):
				//Collect cargo HERE
				if (collectCargo(isAutoCollectOn) || !isAutoCollectOn) hasFinished = true;
				break;
				
		}*/
	}

	/*/**Is in chagre of doing the preload shooting portion of auto
	 * 
	 * @param isActive is dependant on whether we choose to use it (if true, it will do it)
	 *
	public boolean shootPreload(boolean isActive) {
		/**
		 * Steps:
		 * 1. Runs the indexer until the high pe is off
		 * 2. Stops the indexer
		 * 3. Runs the spinner until the shooter pe is on
		 * 4. Stops and passes to next
		 *
		if (isActive) {
			//Checks if the robot has moved the specified taxi distance (aka, has left the Tarmac)
			m_shooter.setSpinSpeed(m_oi.getMaxShootSpeed());
			if (m_si.getUpperStorageTriggered()) {  //If the upper storage photoelectric sensor is triggered
				//Stops the indexer
				m_indexer.setIndexerSpeed(0);
			} else if (m_si.getLowerStorageTriggered()) {  //If the lower storage photoelectric sensor is triggered
				//Spins the indexer at max speed
				m_indexer.setIndexerSpeed(m_oi.getMaxIndexerSpeed());
			} else if (m_si.getShooterTriggered()) {  //If the shooter photoelectric sensor is triggered aka cargo has been ejected
				//Stops the shooter
				m_shooter.setSpinSpeed(0);
				//Ends shoot preload stage
				return true;
			}
		}
		//Continue shoot preload stage
		return false;
	}

	/**Is in chagre of doing the taxi portion of auto
	 * 
	 * @param isActive is dependant on whether we choose to use it (if true, it will do it)
	 *
	public boolean taxiDrive(boolean isActive) {
		//Taxis out of the Tarmax (10 feet backward at high speed) then stops
		if (isActive) {
			//Checks if the robot has moved the specified taxi distance (aka, has left the Tarmac)
			if (m_direction.getDistance() > AutoConstants.taxiDistanceInFeet) {
				double speed = m_oi.getMaxDriveSpeed() + 0.05;  //Speed not max so that course corrections can be made
				//Gets the error rate
				double error = kP * -m_direction.getError();
				//Moves the robot at the set speed and makes course corrections based off the encoders
				driveTrain.tankDrived(speed + error, speed - error);
			} else {
				//Stops the robot
				driveTrain.tankDrived(0, 0);  //Eventually have robot turn instead of stopping
				//End taxi stage and switch to collect cargo stage
				return true;
				//stage = "Collect Cargo";
			}
		}
		//Continue taxi stage
		return false;	
	}

	/**Is in chagre of doing the cargo collection portion of auto
	 * 
	 * @param isActive is dependant on whether we choose to use it (if true, it will do it)
	 *
	public boolean collectCargo(boolean isActive) {
		if (isActive) {
			for (int i = 0; i < AutoConstants.cargoToTarget.length; i++) {
				
			}
		}
		//Continue collect cargo stage
		return false;
	}*/

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		m_direction.resetEncoders();


		/*//Stops the shooter
		m_shooter.setSpinSpeed(0);
		//Stops the Indexer
		m_indexer.setIndexerSpeed(0);
		//Stops the DriveTrain
		driveTrain.tankDrived(0, 0);*/
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		//If auto has finished
		return hasFinished;
	}
	//esteban is driving us nuts :):)
}
