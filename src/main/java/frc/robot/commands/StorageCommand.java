// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Imports OI
import frc.robot.OI;
//Imports Storage subsystem
import frc.robot.subsystems.Storage;

public class StorageCommand extends CommandBase {
	private Storage m_storage;
	private OI m_oi;

	/** Creates a new StorageCommand. */
	public StorageCommand() {
		m_storage = Storage.getStorage();
		m_oi = OI.getInstance();
		// Use addRequirements() here to declare subsystem dependencies.
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		if (m_oi.getSpinIndexer()) {
			m_storage.raiseCargo(1.0);
		} else {
			m_storage.raiseCargo(0);
		}
	}
}
