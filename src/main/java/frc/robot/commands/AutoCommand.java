package frc.robot.commands;


//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Imports OI
import frc.robot.OI;
//Imports Constants
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
//Imports subsystems
import frc.robot.subsystems.Direction;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;

/*This command will run and will do 
 */
public class AutoCommand extends CommandBase {
	private OI m_oi;
	private DriveTrain driveTrain;
	private Shooter shooter;
	private Direction gyro;
	private boolean hasFinished;
	private String stage;

	//Constructor
	public AutoCommand() {
		m_oi = OI.getInstance();
		driveTrain = DriveTrain.getInstance();
		shooter = Shooter.getInstance();
		gyro = Direction.getInstance();
		
		
		
		hasFinished = false;
		
	}

	//public void run() {}


	@Override
	public void initialize() {
		stage = "Shoot Preload";
	}

	@Override
	public void execute() {
		/* autonomous commented until fixed
		switch (stage) {
			case ("Shoot Preload"):
				//Shoot preload HERE
				stage = "Taxi"; //Add condition
				break;
			case ("Taxi"):
				//Gets the error rate
				double error = kP * -gyro.getRate();
				//Taxis out of the Tarmax (10 feet forward at high speed) then stops
				if (encoder.getDistance() < AutoConstants.taxiDistanceInFeet) {
					double speed = 0.95;  //Speed not 1 so that course corrections can be made
					driveTrain.tankDrived(speed + error, speed - error);
				} else {
					driveTrain.tankDrived(0, 0);  //Eventually have robot turn instead of stopping
					stage = "Collect Cargo";
				}
				break;
			case ("Collect Cargo"):
				//Collect cargo HERE
				break;
				
		}
		*/
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
