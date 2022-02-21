// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.inputs.OI;
//Imports subsystems
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;

public class IndexerCommand extends CommandBase {
	private Indexer m_indexer;
	private Intake m_intake;
	private OI m_oi;

	/** Creates a new StorageCommand. */
	public IndexerCommand(Indexer m_indexer) {
		this.m_indexer = m_indexer;
		m_intake = Intake.getInstance();
		m_oi = OI.getInstance();
		// Use addRequirements() here to declare subsystem dependencies.
	}
	
	@Override
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		if (m_oi.getIndexerIn()) {  //If the A button is being pressed
			m_indexer.setIndexerSpeed(m_oi.getMaxIndexerSpeed());
			m_intake.setRollerSpeed(m_oi.getMaxIntakeRollerSpeed());
		} else if (m_oi.getIndexerOut()) {  //If the Y button is being pressed
			m_indexer.setIndexerSpeed(-m_oi.getMaxIndexerSpeed());
			m_intake.setRollerSpeed(-m_oi.getMaxIntakeRollerSpeed());
		} else {  //Nothing being pressed (indexer wise)
			//Stops the indexer
			m_indexer.setIndexerSpeed(0);
			//Stops the rollers
			m_intake.setRollerSpeed(0);
		}
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
