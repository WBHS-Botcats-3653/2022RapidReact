// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.AutoCommand;
import frc.robot.inputs.OI;
import frc.robot.inputs.SI;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;

public class CollectCargoCommand extends CommandBase {
	private Intake m_intake;
	private Indexer m_indexer;
	private OI m_oi;
	private SI m_si;

	public static boolean endCommand;

	public CollectCargoCommand() {
		m_intake = Intake.getInstance();
		m_indexer =  Indexer.getInstance();
		m_oi = OI.getInstance();
		m_si = SI.getInstance();
		// Use addRequirements() here to declare subsystem dependencies.
		addRequirements(m_intake, m_indexer);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		//Do not end the command
		endCommand = false;
		//Sets the pivot to max speed down
		m_intake.setPivotSpeed(m_oi.getMaxSmartIntakePivotDownSpeed());
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		if (m_si.getPivotDownLimitTriggered() && !endCommand) {  //If the intake has finished it's downward pivot
			//Stops the intake pivot
			m_intake.setPivotSpeed(m_oi.getMaxPivotAssistSpeed());
			//Sets the intake roller speed to max
			m_intake.setRollerSpeed(m_oi.getMaxIntakeRollerSpeed());
		} else if (endCommand) {  //If the command has been told to end
			//Sets the intake pivot speed to max up
			m_intake.setPivotSpeed(-m_oi.getMaxSmartIntakePivotUpSpeed());
			//Stops the intake rollers
			m_intake.setRollerSpeed(0);
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
		//No longer executing a command
		AutoCommand.executingCommand = false;
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		//If the intake has finished it's upward pivot
		return m_si.getPivotUpLimitTriggered() && endCommand;
	}
}
