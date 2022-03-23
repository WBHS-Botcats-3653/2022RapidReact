// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoCommands;

import static frc.robot.Constants.AutoConstants.*;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.inputs.SI;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class ShootCargoCommand extends CommandBase {
	private final Indexer m_indexer = Indexer.getInstance();
	private final Shooter m_shooter = Shooter.getInstance();
	private final SI m_si = SI.getInstance();

	private int numCargo, numShot;

	private boolean shooterTriggered;

	public ShootCargoCommand(int numCargo) {
		this.numCargo = numCargo;
		addRequirements(m_indexer, m_shooter);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		//Spins the shooter at max speed
		m_shooter.setShootSpeed(kAutoShootSpeed);
		//The shooter photoelectric sensor has not been triggered
		shooterTriggered = false;
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		if (m_si.isLowerStorageClosed()) {  //If there is cargo in the lower storage area
			//Sets the indexer to max speed
			m_indexer.setIndexerSpeed(kAutoIndexSpeed);
		}
		if (m_si.isShooterClosed()) {  //If cargo has moved into the shooter
			//The shooter photoelectric sensor has been triggered
			shooterTriggered = true;
		} else if (shooterTriggered) {  //If the shooter has been previously triggered
			//Increment the number of cargo that has been shot by 1
			numShot++;
			//The shooter photoelectric sensor has not been triggered
			shooterTriggered = false;
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		//Stops the indexer
		m_indexer.setIndexerSpeed(0);
		//Stops the shooter
		m_shooter.setShootSpeed(0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		//Stops the command when the correct number of cargo to be shot have left the shooter
		return numShot >= numCargo;
	}
}
