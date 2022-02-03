package frc.robot.commands;

//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Imports StartEndCommand
import edu.wpi.first.wpilibj2.command.StartEndCommand;
//Imports WaitUntilCommand
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
//Imports DriveTrain and Shooter subsystems
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;

/*This command will run and will do 
 */
public class AutoCommand extends CommandBase {
	private DriveTrain driveTrain = DriveTrain.getDriveTrain();
	private Shooter shooter = Shooter.getShooter();

	//Constructor
	public AutoCommand() {
		
	}

	//@Override
	public void initialize() {
		/*this one will execute the shooter for 3 seconds and then stop */
		new WaitUntilCommand(3).deadlineWith(new StartEndCommand(
			//run when it starts
			() -> shooter.spinSpinner(1.0), 
			//run when it ends
			() -> shooter.spinSpinner(0),
		 	driveTrain)
		)
		 .andThen(
		/*what this will do is:
		 * it will run the WaitUntilCommand (which will run for the time (in this case 3 seconds) and then stop)
		 * because it is used the deadlineWith (which will make the parameter stop when the 
		 * (in this case) WaitUntilCommand stops).
		 * so it will basically go and .5 speed, straight, and it will last for 3 seconds, 
		 * thus making the cut for the taxi drive part.
		 */
			new WaitUntilCommand(3).deadlineWith(new StartEndCommand(
				//run when it starts
				() -> driveTrain.ArcadeDrived(0.5, 0), 
				//run when it ends
				() -> driveTrain.ArcadeDrived(0, 0),
				driveTrain)
			)
		);
		//super.initialize();
		new WaitUntilCommand(3).deadlineWith(new StartEndCommand(
			//run when it starts
			() -> driveTrain.ArcadeDrived(0.5, 0), 
			//run when it ends
			() -> driveTrain.ArcadeDrived(0, 0),
			driveTrain));
	}
}
