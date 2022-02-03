// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.subcommands.Indexersubcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Storage;

public class IndexerCommand extends CommandBase {
<<<<<<< HEAD
  /** Creates a new IndexerCommand. */
  private Storage m_indexer;
  public IndexerCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_indexer = Storage.getStorage();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_indexer.raiseCargo(1.0);
    
  }
=======
	/** Creates a new IndexerCommand. */
	public IndexerCommand() {
		// Use addRequirements() here to declare subsystem dependencies.
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {

		
	}
>>>>>>> 6e7fcb9adaee3acbe950271b4fd4b8f72ea06ea2

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {}

<<<<<<< HEAD
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_indexer.raiseCargo(0);
=======
	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {

>>>>>>> 6e7fcb9adaee3acbe950271b4fd4b8f72ea06ea2

	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}
