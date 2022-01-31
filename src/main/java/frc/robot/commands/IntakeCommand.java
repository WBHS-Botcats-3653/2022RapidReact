// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//Import CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Import OI
import frc.robot.OI;
//Import Intake subsystem
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {
	//Holds instances of OI and Intake subsystem
	private OI m_oi;
	private Intake m_intake;

	/**
	 * Creates a new IntakeCommand.
	 *
	 * @param subsystem The subsystem used by this command.
	 */
	public IntakeCommand() {
		//Initializes instance variables with OI and Intake subsystem
		m_oi=OI.getInstance();
		m_intake=Intake.getIntake();
		// Use addRequirements() here to declare subsystem dependencies.
		//addRequirements();
	}

	// Called when the command is initially scheduled.
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	public void execute() {
		if (m_oi.getIntakeDown()) {
			//Drops the Intake
			m_intake.dropIntake();
		} else if (m_oi.getIntakeUp()) {
			//Raises the intake
			m_intake.raiseIntake();
		}
		//Spins the rollers
		m_intake.spinRollers(m_oi.getIntakeCtrl());
		//Rolls the belt
		m_intake.raiseCargo(m_oi.getIntakeCtrl());
	}

	// Called once the command ends or is interrupted.
	public void end(boolean interrupted) {}

	// Returns true when the command should end.
	public boolean isFinished() {
		return false;
	}
}