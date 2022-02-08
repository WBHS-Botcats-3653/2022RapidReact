// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Imports OI
import frc.robot.OI;
//Imports SI
import frc.robot.SI;
//Imports Intake
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {
	//Holds instances of OI and Intake subsystem
	private OI m_oi;
	private Intake m_intake;
	private SI m_si;

	/**Creates a new IntakeCommand.
	 * @param subsystem The subsystem used by this command.
	 */
	public IntakeCommand() {
		//Initializes instance variable with OI subsystem
		m_oi = OI.getInstance();
		m_intake = Intake.getInstance();
		m_si = SI.getInstance();
		// Use addRequirements() here to declare subsystem dependencies.
		//addRequirements();
	}

	public void run() {
		//All stop called (used for testing)
		if (m_oi.getAllStop()) {
			//Stops motor(s)
			m_intake.setRollerSpeed(0);
			m_intake.setPivotSpeed(0);
			return;
		}
		if (m_oi.getManualIntakeDown()) {
			m_intake.setPivotSpeed(m_oi.getMaxIntakePivotSpeed());
		} else if (m_oi.getManualIntakeUp()) {
			m_intake.setPivotSpeed(m_oi.getMaxIntakePivotSpeed());
		} else if (m_si.getPivotUpTriggered()) {
			m_intake.setPivotSpeed(0);
			m_oi.isIntakeDown = true;
		} else if (m_si.getPivotDownTriggered()) {
			m_intake.setPivotSpeed(0);
			m_oi.isIntakeDown = false;
		} else if (m_oi.getIntakeDown()) {
			m_intake.setPivotSpeed(m_oi.getMaxIntakePivotSpeed());
		} else if (m_oi.getIntakeUp()) {
			m_intake.setPivotSpeed(-m_oi.getMaxIntakePivotSpeed());
		}
		m_intake.setRollerSpeed(m_oi.getIntakeCtrl());
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