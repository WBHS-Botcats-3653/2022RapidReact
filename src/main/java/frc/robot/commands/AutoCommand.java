package frc.robot.commands;

//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.OI;
import frc.robot.Constants.AutoConstants;
import frc.robot.subsystems.Direction;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

/*This command will run and will do 
 */
public class AutoCommand extends CommandBase {
	private OI m_oi;
	private DriveTrain driveTrain;
	private Shooter m_shooter;
	private Indexer m_indexer;
	private Direction gyro;
	private boolean hasFinished;
	private String stage;
	private double kP;

	//Constructor
	public AutoCommand() {
		m_oi = OI.getInstance();
		driveTrain = DriveTrain.getInstance();
		m_shooter = Shooter.getInstance();
		m_indexer = Indexer.getInstance();
		gyro = Direction.getInstance();
		kP = 1;
		hasFinished = false;
	}


	@Override
	public void initialize() {
		/*//Runs auto commands sequentially
		new SequentialCommandGroup(
			//Shoots 1 preloaded cargo
			new ShootCargoCommand(),
			//Taxiing, move back 10 feet at max speed
			new DriveCommand(-m_oi.getMaxDriveSpeed(), AutoConstants.taxiDistanceInFeet, true)
		);
		hasFinished = true;*/

		stage = "Shoot Preload";
		/*new ScheduleCommand(
			//this one will execute the shooter for 3 seconds and then stop
			new StartEndCommand(
				//run when it starts
				() -> shooter.spinSpinner(1.0), 
				//run when it ends
				() -> shooter.spinSpinner(0),
				driveTrain)
			.withTimeout(3)
			.andThen(
			/**what this will do is:
			 * it will run the WaitUntilCommand (which will run for the time (in this case 3 seconds) and then stop)
			 * because it is used the deadlineWith (which will make the parameter stop when the 
			 * (in this case) WaitUntilCommand stops).
			 * so it will basically go and .5 speed, straight, and it will last for 3 seconds, 
			 * thus making the cut for the taxi drive part.
			 *
				new StartEndCommand(
					//run when it starts
					() -> driveTrain.ArcadeDrived(0.5, 0), 
					//run when it ends
					() -> driveTrain.ArcadeDrived(0, 0), driveTrain).withTimeout(3)
				)
			).initialize();
		//super.initialize();*/
	}

	@Override
	public void execute() {
		switch (stage) {
			case ("Shoot Preload"):
				//Shoot preload HERE
				new StartEndCommand(
					() -> m_shooter.setSpinSpeed(m_oi.getMaxShootSpeed()),
					() -> new StartEndCommand(
						() -> m_indexer.setIndexerSpeed(m_oi.getMaxIndexerSpeed()),
						() -> m_indexer.setIndexerSpeed(0)
					).withTimeout(2)
				).withTimeout(2).andThen(
					() -> m_shooter.setSpinSpeed(0)
				).initialize();
				//End shoot preload stage and switch to taxi stage
				stage = "Taxi"; //Add condition
				break;
			case ("Taxi"):
				//Gets the error rate
				double error = kP * -gyro.getRate();
				//Taxis out of the Tarmax (10 feet forward at high speed) then stops
				if (gyro.getDistance() > AutoConstants.taxiDistanceInFeet) {
					double speed = -m_oi.getMaxDriveSpeed() - 0.05;  //Speed not max so that course corrections can be made
					//Moves the robot at the set speed and making course corrections based off the gyro
					driveTrain.tankDrived(speed + error, speed - error);
				} else {
					//Stop the robot
					driveTrain.tankDrived(0, 0);  //Eventually have robot turn instead of stopping
					//End taxi stage and switch to collect cargo stage
					stage = "Collect Cargo";
				}
				break;
			case ("Collect Cargo"):
				//Collect cargo HERE
				break;
				
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return hasFinished;
	}
}
