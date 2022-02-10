// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Imports DriveTrain subsystem
import frc.robot.subsystems.DriveTrain;

public class ArcadeDriveCommand extends CommandBase {
	//Holds instances of OI and DriveTrain subsystem
	private DriveTrain m_drivetrain;

	/**Creates a new ArcadeDriveCommand.
	 * @param subsystem The subsystem used by this command.
	 */
	public ArcadeDriveCommand(DriveTrain m_drivetrain) {
		//Initializes instance variables with an instance of OI and DriveTrain
		this.m_drivetrain = m_drivetrain;
		// Use addRequirements() here to declare subsystem dependencies.
		//addRequirements();
	}

	//public void run() {}
	
	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		m_drivetrain.ArcadeDrived();
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}
