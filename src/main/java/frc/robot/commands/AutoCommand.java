package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.DriveTrain;

public class AutoCommand extends CommandBase {
	private DriveTrain driveTrain = DriveTrain.getDriveTrain();
	//Constructor
	public AutoCommand() {
		
	}

	//@Override
	public void initialize() {

		new InstantCommand(() -> driveTrain.ArcadeDrived(0.5, 1.0), driveTrain);
		// TODO Auto-generated method stub
		//super.initialize();
	}

}
