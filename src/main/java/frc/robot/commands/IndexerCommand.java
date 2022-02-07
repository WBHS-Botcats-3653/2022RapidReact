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
//Imports Storage subsystem
import frc.robot.subsystems.Indexer;

public class IndexerCommand extends CommandBase {
	private Indexer m_indexer;
	private OI m_oi;
	private SI m_si;

	/** Creates a new StorageCommand. */
	public IndexerCommand() {
		m_indexer = Indexer.getInstance();
		m_oi = OI.getInstance();
		m_si = SI.getInstance();
		// Use addRequirements() here to declare subsystem dependencies.
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//All stop called (used for testing)
		if (m_oi.getAllStop()) {
			//Stops motor(s)
			m_indexer.setIndexerSpeed(0);
			return;
		}
		if (m_oi.getSpinIndexer()) {
			m_indexer.setIndexerSpeed(m_oi.getMaxIndexerSpeed());
		} else if (m_si.getLowerStorageTriggered()) {
			if (!m_si.getUpperStorageTriggered()) {
				m_indexer.setIndexerSpeed(m_oi.getMaxIndexerSpeed());
			} else {
				m_indexer.setIndexerSpeed(0);
			}
		} else {
			m_indexer.setIndexerSpeed(0);
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
