// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {
	private OI m_oi;
	Intake m_intake;
	/**
	 * Creates a new IntakeCommand.
	 *
	 * @param subsystem The subsystem used by this command.
	 */
	public IntakeCommand() {
		m_oi=OI.GetInstance();
		m_intake=OI.GetInstance();
	}

	// Called when the command is initially scheduled.
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	public void execute() {}

	// Called once the command ends or is interrupted.
	public void end(boolean interrupted) {}

	// Returns true when the command should end.
	public boolean isFinished() {
		return false;
	}
}