// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.inputs.OI;
import frc.robot.inputs.SI;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class ShootCargoCommand extends CommandBase {
	private Shooter m_shooter;
	private Indexer m_indexer;
	private OI m_oi;
	private SI m_si;

	private final double indexSpeed = 0.5;

	public ShootCargoCommand() {
		m_shooter = Shooter.getInstance();
		m_indexer = Indexer.getInstance();
		m_oi = OI.getInstance();
		m_si = SI.getInstance();
		// Use addRequirements() here to declare subsystem dependencies.
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		//Spins the shooter at max speed
		m_shooter.setSpinSpeed(m_oi.getMaxShootSpeed());
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		/*if (m_si.getUpperStorageTriggered()) {  //If there is cargo in the upper storage area
			//Stops the indexer
			m_indexer.setIndexerSpeed(0);
		} else if (m_si.getLowerStorageTriggered()) {  //If there is cargo in the lower storage area
			//Sets the indexer to max speed
			m_indexer.setIndexerSpeed(m_oi.getMaxIndexerSpeed());
		}*/
		if (m_si.getLowerStorageTriggered() || m_si.getUpperStorageTriggered()) {  //If there is cargo in the storage area
			//Sets the indexer to max speed
			m_indexer.setIndexerSpeed(indexSpeed);
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		//Stops the indexer
		m_indexer.setIndexerSpeed(0);
		//Stops the shooter
		m_shooter.setSpinSpeed(0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		//Stops the command when the cargo has left the shooter
		return m_si.getShooterTriggered();
	}
}
