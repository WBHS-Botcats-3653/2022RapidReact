package frc.robot.commands;

import edu.wpi.first.wpilibj.Encoder;
//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.AutoConstants;
import frc.robot.Constants;
//Imports subsystems
import frc.robot.subsystems.Direction;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;

/*This command will run and will do 
 */
public class AutoCommand extends CommandBase {
	private DriveTrain driveTrain;
	private Shooter shooter;
	private Direction gyro;
	private double kP;
	private Encoder encoder;
	private boolean hasFinished;
	private String stage;

	//Constructor
	public AutoCommand() {
		driveTrain = DriveTrain.getInstance();
		shooter = Shooter.getInstance();
		gyro = Direction.getInstance();
		kP = 1;
		encoder = new Encoder(Constants.AIID.get("Left Motor Group Encoder"), Constants.AIID.get("Right Motor Group Encoder"));
		hasFinished = false;
		// Configures the encoders' distance-per-pulse
		// The robot moves forward 1 foot per encoder rotation
		// There are 256 pulses per encoder rotation
		encoder.setDistancePerPulse(1./256.);
	}

	//public void run() {}

	@Override
	public void initialize() {
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
					() -> driveTrain.ArcadeDrived(0, 0),
					driveTrain
				).withTimeout(3)
			)
		).initialize();
		//super.initialize();*/
	}

	@Override
	public void execute() {
		switch (stage) {
			case ("Shoot Preload"):
				//Shoot preload here
				stage = "Taxi"; //Add condition
				break;
			case ("Taxi"):
				//Gets the error rate
				double error = kP * -gyro.getRate();
				//Taxis out of the Tarmax (10 feet back at high speed) then stops
				if (encoder.getDistance() > AutoConstants.taxiDistanceInFeet) {
					double speed = -0.95;  //Speed not 1 so that course corrections can be made
					driveTrain.tankDrived(speed + error, speed - error);
				} else {
					driveTrain.tankDrived(0, 0);  //Eventually have robot turn instead of stopping
					stage = "Collect Cargo";
				}
				break;
			case ("Collect Cargo"):
				//Collect cargo here
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
