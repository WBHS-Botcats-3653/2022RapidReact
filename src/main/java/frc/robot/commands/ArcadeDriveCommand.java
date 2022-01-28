// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.DriveTrain;

/** An example command that uses an example subsystem. */
public class ArcadeDriveCommand extends CommandBase {
	private OI m_oi;
	DriveTrain m_drivetrain;

	/**
	 * Creates a new ArcadeDriveCommand.
	 *
	 * @param subsystem The subsystem used by this command.
	 */
	public ArcadeDriveCommand() {
		m_oi = OI.getInstance();
		m_drivetrain = DriveTrain.getDriveTrain();
		// Use addRequirements() here to declare subsystem dependencies.
		//addRequirements();
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}
