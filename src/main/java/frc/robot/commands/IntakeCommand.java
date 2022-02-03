// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
//Import CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
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
//TODO: FIX IT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		new WaitUntilCommand(() -> !(m_oi.getIntakeDown()))
		.deadlineWith(
			new StartEndCommand(
				new InstantCommand(
					()-> m_intake.dropIntake(),
					m_intake
					)/*
						.alongWith(
							new RunCommand(() -> m_intake.spinRollers(1.0), m_intake), 
							new RunCommand(() -> m_intake.raiseCargo(1.0), m_intake)
						)
				)*/,
				new WaitUntilCommand(3)
					.deadlineWith(
						new StartEndCommand(
							() -> m_intake.raiseCargo(1.0), 
							() -> m_intake.raiseCargo(0),
							m_intake
						)
					), 
					m_intake
			);

		if (m_oi.getIntakeDown()) {
			//Drops the Intake
			m_intake.dropIntake();
			;
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