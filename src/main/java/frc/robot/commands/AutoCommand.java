package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.DriveTrain;
/**This command will run and will do 
 * 
 */
public class AutoCommand extends CommandBase {
	private DriveTrain driveTrain = DriveTrain.getDriveTrain();
	//Constructor

	

	public AutoCommand() {
		
	}

	//@Override
	public void initialize() {

		/**what this will do is:
		 * it will run the WaitUntilCommand (which will run for the time (in this case 3 seconds) and then stop)
		 * because it is used the deadlineWith (which will make the parameter stop when the 
		 * (in this case) WaitUntilCommand stops).
		 * so it will basically go and .5 speed, straight, and it will last for 3 seconds, 
		 * thus making the cut for the taxi drive part.
		 */
		new WaitUntilCommand(3).deadlineWith(new StartEndCommand(
<<<<<<< HEAD
			//run when it starts
			() -> driveTrain.ArcadeDrived(0.5, 0), 
			//run when it ends
			() -> driveTrain.ArcadeDrived(0, 0),
			driveTrain));
		// TODO Auto-generated method stub
=======
		//run when it starts
		() -> driveTrain.ArcadeDrived(0.5, 0), 
		//run when it ends
		() -> driveTrain.ArcadeDrived(0, 0),
		 driveTrain));
>>>>>>> d14905f9fcc8172ca3a5bddaf076b9125812ebac
		//super.initialize();
	}

}
