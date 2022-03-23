// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoCommands;

import static frc.robot.Constants.AutoConstants.*;
import static frc.robot.Constants.IntakeConstants.kIntakePivotAssistSpeed;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.inputs.SI;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;

public class CollectCargoCommand extends CommandBase {
	private final Intake m_intake = Intake.getInstance();
	private final Indexer m_indexer = Indexer.getInstance();
	private final SI m_si = SI.getInstance();

	public static boolean endCommand;

	public CollectCargoCommand() {
		addRequirements(m_intake, m_indexer);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		//Do not end the command
		endCommand = false;
		//Pivots the intake down
		m_intake.setPivotSpeed(kAutoIntakePivotDownSpeed);
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//Intake pivot and rollers
		if (endCommand) {  //If the command has been told to end
			//Pivots the intake up
			m_intake.setPivotSpeed(-kAutoIntakePivotUpSpeed);
			//Stops the intake rollers
			m_intake.setRollerSpeed(0);
		} else if (m_si.isPivotDownLimitClosed()) {  //If the intake has finished it's downward pivot
			//Pivot assist
			m_intake.setPivotSpeed(kIntakePivotAssistSpeed);
			//Spins the intake rollers
			m_intake.setRollerSpeed(kAutoIntakeRollerSpeed);
		}
		//Indexer
		if (m_si.isUpperStorageClosed()) {  //If there is cargo in the upper storage area
			//Spin the indexer in reverse
			m_indexer.setIndexerSpeed(-kAutoIndexSpeed);
		} else if (m_si.isLowerStorageClosed()) {  //If there is cargo is the lower storage area
			//Stop the indexer
			m_indexer.setIndexerSpeed(0);
		} else if (m_si.isIntakeClosed()) {  //If there is cargo in the intake
			//Spin the indexer
			m_indexer.setIndexerSpeed(kAutoIndexSpeed);
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		//Stops the intake pivot
		m_intake.setPivotSpeed(0);
		//Stops the intake rollers
		m_intake.setRollerSpeed(0);
		//Stops the indexer
		m_indexer.setIndexerSpeed(0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		//If the intake has finished it's upward pivot
		return m_si.isPivotUpLimitClosed() && endCommand;
	}
}
