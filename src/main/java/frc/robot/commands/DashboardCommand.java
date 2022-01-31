// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//Import CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Import OI
import frc.robot.OI;
import frc.robot.subsystems.Dashboard;
//Import Intake subsystem
import frc.robot.subsystems.Intake;

public class DashboardCommand extends CommandBase {
	//Holds instances of OI and Intake subsystem
	private OI m_oi;
	private Dashboard m_dashboard;

	/**
	 * Creates a new DashboardCommand.
	 *
	 * @param subsystem The subsystem used by this command.
	 */
	public DashboardCommand() {
		//Initializes instance variables with OI and Intake subsystem
		m_oi=OI.getInstance();
		m_dashboard= Dashboard.getDashboard();
		// Use addRequirements() here to declare subsystem dependencies.
		//addRequirements();
	}

	// Called when the command is initially scheduled.
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	public void execute() {
		SmartDashboard.putNumber("Joystick X value", m_oi.getThrottle());
	}

	// Called once the command ends or is interrupted.
	public void end(boolean interrupted) {}

	// Returns true when the command should end.
	public boolean isFinished() {
		return false;
	}
}