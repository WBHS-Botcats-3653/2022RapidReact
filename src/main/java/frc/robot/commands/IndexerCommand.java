// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.inputs.OI;
import frc.robot.subsystems.Indexer;

public class IndexerCommand extends CommandBase {
	private Indexer m_indexer;
	private OI m_oi;

	/** Creates a new StorageCommand. */
	public IndexerCommand(Indexer p_indexer) {
		m_indexer = p_indexer;
		m_oi = OI.getInstance();
		// Use addRequirements() here to declare subsystem dependencies.
		addRequirements(p_indexer);
	}
	
	@Override
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		if (m_oi.getIndexerIn()) {  //If the A button is being pressed
			//Sets the indexer speed to the set max
			m_indexer.setIndexerSpeed(m_oi.getMaxIndexerSpeed());
		} else if (m_oi.getIndexerOut()) {  //If the Y button is being pressed
			//Sets the indexer speed to the negative set max
			m_indexer.setIndexerSpeed(-m_oi.getMaxIndexerSpeed());  //Reverse indexer
		} else {  //Nothing being pressed (indexer wise)
			//Stops the indexer
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
