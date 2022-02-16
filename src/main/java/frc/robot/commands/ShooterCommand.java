// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Imports OI
import frc.robot.OI;
//Imports Indexer subsystem
import frc.robot.subsystems.Indexer;
//Imports Shooter subsystem
import frc.robot.subsystems.Shooter;

public class ShooterCommand extends CommandBase {
	private Shooter m_shooter;
	private Indexer m_indexer;
	private OI m_oi;

	public ShooterCommand(Shooter m_shooter) {
		this.m_shooter = m_shooter;
		m_oi = OI.getInstance();
		m_indexer = Indexer.getInstance();
		// Use addRequirements() here to declare subsystem dependencies.
		//addRequirements();
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		if (m_oi.getShoot()) {  //If the right trigger is being pressed
			//Spins the shooter at max speed
			m_shooter.setSpinSpeed(m_oi.getMaxShootSpeed());
		} else if (m_oi.getShootReverse()) {  //If the right bumper is being pressed
			//Spins the shooter at max speed in reverse
			m_shooter.setSpinSpeed(-m_oi.getMaxShootSpeed());
		} else {  //Nothing is being pressed (shooter wise)
			//Stops the spinner
			m_shooter.setSpinSpeed(0);
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