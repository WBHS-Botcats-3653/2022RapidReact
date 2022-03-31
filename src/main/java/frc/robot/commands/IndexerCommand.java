// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.IndexerConstants.kSmartIndexSpeed;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.NetworkEntries;
import frc.robot.inputs.OI;
import frc.robot.inputs.SI;
import frc.robot.subsystems.Indexer;

public class IndexerCommand extends CommandBase {
	private Indexer m_indexer;
	private final OI m_oi = OI.getInstance();
	private final SI m_si = SI.getInstance();

	private boolean smartControl = false;

	/** Creates a new StorageCommand. */
	public IndexerCommand(Indexer p_indexer) {
		m_indexer = p_indexer;
		addRequirements(p_indexer);
	}
	
	@Override
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//Run manual indexer logic
		manualIndexerLogic();
		//If under smart control and the smart indexer is enabled, run the smart indexer logic
		if (smartControl && NetworkEntries.isSmartIndexerEnabled()) smartIndexerLogic();
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		//Stop the indexers
		m_indexer.setHorizontalIndexerSpeed(0);
		m_indexer.setVerticalIndexerSpeed(0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}

	//Manual control indexer logic
	public void manualIndexerLogic() {
		if (m_oi.getIndexIn()) {  //If the index in button is being pressed
			//Sets the indexer speeds to the set max
			m_indexer.setHorizontalIndexerSpeed(m_oi.getMaxIndexSpeed());
			m_indexer.setVerticalIndexerSpeed(m_oi.getMaxIndexSpeed());
			//Not under smart control
			smartControl = false;
		} else if (m_oi.getIndexOut()) {  //If the index out button is being pressed
			//Sets the indexer speeds to the negative set max
			m_indexer.setHorizontalIndexerSpeed(-m_oi.getMaxIndexSpeed());  //Reverse indexer
			m_indexer.setVerticalIndexerSpeed(-m_oi.getMaxIndexSpeed());  //Reverse indexer
			//Not under smart control
			smartControl = false;
		} else if (!smartControl) {  //If the indexer is not under smart control
			//Stops the indexers
			m_indexer.setHorizontalIndexerSpeed(0);
			m_indexer.setVerticalIndexerSpeed(0);
			//Under smart control
			smartControl = true;
		}
	}

	//Smart control indexer logic
	public void smartIndexerLogic() {
		//If the driver is giving shooting input
		if (ShooterCommand.isGivingInput()) {
			//If there is no cargo in the upper storage but there is cargo in the lower storage or intake
			if (!m_si.isUpperStorageClosed() && (m_si.isIntakeClosed() || m_si.isLowerStorageClosed())) {
				//Spin the indexers
				m_indexer.setHorizontalIndexerSpeed(kSmartIndexSpeed);
				m_indexer.setVerticalIndexerSpeed(kSmartIndexSpeed);
			}
		} else if (m_si.isUpperStorageClosed()) {  //If there is cargo in the upper storage
			//If there is cargo in the lower storage area
			if (m_si.isLowerStorageClosed()) {
				//Stops the indexers
				m_indexer.setHorizontalIndexerSpeed(0);
				m_indexer.setVerticalIndexerSpeed(0);
			} else {  //If there is no cargo in the lower storage area
				//Stops the horizontal indexer
				m_indexer.setHorizontalIndexerSpeed(0);
				//Spins the vertical indexer in reverse
				m_indexer.setVerticalIndexerSpeed(-kSmartIndexSpeed);
			}
		} else if (m_si.isIntakeClosed()) {  //If there is cargo in the intake but not in the upper storage area
			//Spins the indexers
			m_indexer.setHorizontalIndexerSpeed(kSmartIndexSpeed);
			m_indexer.setVerticalIndexerSpeed(kSmartIndexSpeed);
		} else if (m_si.isLowerStorageClosed()) {  //If there is cargo only in the lower storage area
			//Stops the indexers
			m_indexer.setHorizontalIndexerSpeed(0);
			m_indexer.setVerticalIndexerSpeed(0);
		}
	}
}
