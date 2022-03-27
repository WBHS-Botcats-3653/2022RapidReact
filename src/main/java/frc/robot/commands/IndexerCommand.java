// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.IntakeConstants.kSmartIndexSpeed;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.inputs.OI;
import frc.robot.inputs.SI;
import frc.robot.subsystems.Indexer;

public class IndexerCommand extends CommandBase {
	private Indexer m_indexer;
	private final OI m_oi = OI.getInstance();
	private final SI m_si = SI.getInstance();

	private boolean smartControl, endingSmartControl;

	/** Creates a new StorageCommand. */
	public IndexerCommand(Indexer p_indexer) {
		m_indexer = p_indexer;
		addRequirements(p_indexer);
	}
	
	@Override
	public void initialize() {
		//Is not under smart control
		smartControl = false;
		//Is not ending smart control
		endingSmartControl = false;
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//If the intake is under smart control put the indexer under smart control
		if (IntakeCommand.isUnderSmartControl()) smartControl = true;
		//If the intake is ending it's smart control period then indexer smart control is ending
		if (smartControl && IntakeCommand.isEndingSmartControl()) endingSmartControl = true;
		//Run manual indexer logic, If no user input is given to the indexer by the driver and under smart control then run the smart indexer logic
		if (manualIndexerLogic() && smartControl) smartIndexerLogic();
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		//Stop the indexer
		m_indexer.setIndexerSpeed(0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}

	//Manual control indexer logic, returns whether any user input was given to the indexer
	public boolean manualIndexerLogic() {
		if (m_oi.getIndexerIn()) {  //If the index in button is being pressed
			//Sets the indexer speed to the set max
			m_indexer.setIndexerSpeed(m_oi.getMaxIndexSpeed());
			//Returns user input given
			return true;
		} else if (m_oi.getIndexerOut()) {  //If the index out button is being pressed
			//Sets the indexer speed to the negative set max
			m_indexer.setIndexerSpeed(-m_oi.getMaxIndexSpeed());  //Reverse indexer
			//Returns user input given
			return true;
		}
		//Stops the indexer
		m_indexer.setIndexerSpeed(0);
		//Returns user input not given
		return false;
	}

	//Smart control indexer logic
	public void smartIndexerLogic() {
		//If the smart control period is ending
		if (endingSmartControl) {
			//If there is no cargo in the upper storage but there is cargo in the lower storage or intake
			if (!m_si.isUpperStorageClosed() && (m_si.isIntakeClosed() || m_si.isLowerStorageClosed())) {
				//Spin the indexer
				m_indexer.setIndexerSpeed(kSmartIndexSpeed);
			} else {  //If there is cargo in the upper storage or no cargo in the lower storage or intake
				//Is not under smart control
				smartControl = false;
				//Is not ending smart control
				endingSmartControl = false;
			}
		} else if (m_si.isUpperStorageClosed()) {  //If there is cargo in the upper storage
			//Stops the indexer
			m_indexer.setIndexerSpeed(0);
		} else if (m_si.isIntakeClosed()) {  //If there is cargo in the intake but not in the upper storage area
			//Spins the indexer
			m_indexer.setIndexerSpeed(kSmartIndexSpeed);
		} else if (m_si.isLowerStorageClosed()) {  //If there is cargo only in the lower storage
			//Stops the indexer
			m_indexer.setIndexerSpeed(0);
		}
	}
}
