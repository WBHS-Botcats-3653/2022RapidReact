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

	private boolean hasFinished;
	private boolean shooterTriggered;

	public ShootCargoCommand() {
		m_shooter = Shooter.getInstance();
		m_indexer = Indexer.getInstance();
		m_oi = OI.getInstance();
		m_si = SI.getInstance();
		// Use addRequirements() here to declare subsystem dependencies.
		addRequirements(m_shooter, m_indexer);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		//Spins the shooter at max speed
		m_shooter.setSpinSpeed(-m_oi.getMaxShootSpeed());
		//The command has not finished
		hasFinished = false;
		//The shooter photoelectric sensor has not been triggered
		shooterTriggered = false;
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		if (m_si.getLowerStorageTriggered()) {  //If there is cargo in the lower storage area
			//Sets the indexer to max speed
			m_indexer.setIndexerSpeed(m_oi.getMaxIndexerSpeed());
		}
		if (m_si.getShooterTriggered()) {  //If cargo has moved into the shooter
			//The shooter photoelectric sensor has been triggered
			shooterTriggered = true;
		} else {  //If cargo is not in the shooter
			//If the shooter has been previously triggered the command should finish
			if (shooterTriggered) hasFinished = true;
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
		return hasFinished;
	}
}
