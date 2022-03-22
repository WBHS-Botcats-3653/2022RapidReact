// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.inputs.OI;
import frc.robot.subsystems.Shooter;

public class ShooterCommand extends CommandBase {
	private Shooter m_shooter;
	private final OI m_oi = OI.getInstance();

	public ShooterCommand(Shooter p_shooter) {
		m_shooter = p_shooter;
		// Use addRequirements() here to declare subsystem dependencies.
		addRequirements(p_shooter);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		if (m_oi.getShoot()) {  //If the right trigger is being pressed
			//Spins the shooter at max speed
			m_shooter.setShootSpeed(m_oi.getMaxShootSpeed());
		} else if (m_oi.getShootReverse()) {  //If the right bumper is being pressed
			//Spins the shooter at max speed in reverse
			m_shooter.setShootSpeed(-m_oi.getMaxShootSpeed());
		} else {  //Nothing is being pressed (shooter wise)
			//Stops the flywheel
			m_shooter.setShootSpeed(0);
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		//Stops the flywheel
		m_shooter.setShootSpeed(0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}