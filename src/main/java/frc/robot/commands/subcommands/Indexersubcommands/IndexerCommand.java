// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.subcommands.Indexersubcommands;

//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Imports Storage subsystem
import frc.robot.subsystems.Indexer;

public class IndexerCommand extends CommandBase {
	/** Creates a new IndexerCommand. */
	private Indexer m_indexer;
	
	public IndexerCommand() {
		// Use addRequirements() here to declare subsystem dependencies.
		m_indexer = Indexer.getInstance();
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		m_indexer.raiseCargo(1.0);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		m_indexer.raiseCargo(0);
	}
}
