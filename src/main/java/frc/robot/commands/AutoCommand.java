package frc.robot.commands;

//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Imports InstantCommand
import edu.wpi.first.wpilibj2.command.InstantCommand;
//Imports SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
//Imports autoCommands
import frc.robot.commands.autoCommands.DriveCommand;
import frc.robot.commands.autoCommands.ShootCommand;
//Imports constants
import frc.robot.constants.AutoConstants;
import frc.robot.inputs.OI;

public class AutoCommand extends CommandBase {
	//private DriveTrain driveTrain;
	//private Shooter m_shooter;
	//private Indexer m_indexer;
	//private Direction m_direction;
	private OI m_oi;
	//private SI m_si;

	private boolean hasFinished;
	private boolean initialCommandsDone;
	private int cargoTargetIndex;
	//private String stage;
	//private double kP;

	public static boolean isAutoShootOn;
	public static boolean isAutoTaxiOn;
	public static boolean isAutoCollectOn;

	//Constructor
	public AutoCommand() {
		//driveTrain = DriveTrain.getInstance();
		//m_shooter = Shooter.getInstance();
		//m_indexer = Indexer.getInstance();
		//m_direction = Direction.getInstance();
		m_oi = OI.getInstance();
		//m_si = SI.getInstance();
		//kP = 1;
		hasFinished = false;
		initialCommandsDone = false;

		//Still taxi when collecting cargo? Can prob skip since we'll leave the Tarmac anyways
		new SequentialCommandGroup(
			new ShootCommand(),  //Shoots Preload
			new DriveCommand(AutoConstants.taxiDistanceInFeet, m_oi.getMaxDriveSpeed()),  //Taxi (remove if isAutoCollectOn is true and just go for cargo)
			new InstantCommand(() -> {initialCommandsDone = true;})
		);
	}


	@Override
	public void initialize() {
		cargoTargetIndex = 0;
		//stage = "Shoot Preload";
	}

	@Override
	public void execute() {
		if (!initialCommandsDone) return;
		//"L"=Left, "M"=Middle, "R"=Right
		switch(AutoConstants.startingPosition) {
			case("L"):
				switch(AutoConstants.cargoToTarget[cargoTargetIndex]) {
					case("LL"):

						break;
					case("LR"):

						break;
					case("ML"):

						break;
					case("MR"):

						break;
				}
				break;
			case("R"):
				switch(AutoConstants.cargoToTarget[cargoTargetIndex]) {
					case("ML"):

						break;
					case("MR"):

						break;
					case("RL"):

						break;
					case("RR"):

						break;
				}
				break;
		}
		if (++cargoTargetIndex == AutoConstants.cargoToTarget.length) hasFinished = true;


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
		return hasFinished;
	}
	//esteban is driving us nuts :):)
}
