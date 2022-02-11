package frc.robot.commands;

import java.util.ArrayList;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Imports DriveTrain and Shooter subsystems
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;

/*This command will run and will do 
 */
public class AutoCommand extends CommandBase {
	private DriveTrain driveTrain = DriveTrain.getInstance();
	private Shooter shooter = Shooter.getInstance();
	private boolean hasFinished = false;
	private ArrayList<Trajectory> trajectories;

	//Constructor
	public AutoCommand() {
		
	}

	public void setTrajectories(ArrayList<Trajectory> trajectories) {
		this.trajectories = trajectories;
	}

	//public void run() {}

	@Override
	public void initialize() {
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
		RamseteController controller = new RamseteController();
		ChassisSpeeds adjustedSpeeds = controller.calculate(currentRobotPose, goal);
		DifferentialDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(adjustedSpeeds);
		double left = wheelSpeeds.leftMetersPerSecond;
		double right = wheelSpeeds.rightMetersPerSecond;
		driveTrain.TankDrived(left, right);
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
