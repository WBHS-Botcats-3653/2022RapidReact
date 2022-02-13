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
//Imports subsystems
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;

public class IndexerCommand extends CommandBase {
	private Indexer m_indexer;
	private Intake m_intake;
	private OI m_oi;
	private SI m_si;

	/** Creates a new StorageCommand. */
	public IndexerCommand(Indexer m_indexer) {
		this.m_indexer = m_indexer;
		m_oi = OI.getInstance();
		m_si = SI.getInstance();
		// Use addRequirements() here to declare subsystem dependencies.
	}
	
	@Override
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//All stop called (used for testing)
		if (m_oi.getAllStop()) {  //If the B button is being pressed
			//Stop motor
			m_indexer.setIndexerSpeed(0);
			m_intake.setRollerSpeed(0);
			return;
		}
		//Checks whether there is input coming in to spin the indexer
		if (m_oi.getSpinIndexer()) {  //If the X button is being pressed
			//Spin the indexer at max speed
			m_indexer.setIndexerSpeed(m_oi.getMaxIndexerSpeed());
			m_intake.setRollerSpeed(-m_oi.getMaxIntakeRollerSpeed());
		}/* else if (m_si.getLowerStorageTriggered()) {  //If the lower storage photoelectric sensor is triggered
			if (!m_si.getUpperStorageTriggered()) {  //If the lower storage photoelectric sensor is not triggered
				//Spin the indexer at max speed
				m_indexer.setIndexerSpeed(m_oi.getMaxIndexerSpeed());
			} else {
				//Stop spinning the indexer
				m_indexer.setIndexerSpeed(0);
			}
		}*/ else {
			//Stop spinning the indexer
			m_indexer.setIndexerSpeed(0);
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
