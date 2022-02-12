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
	//Holds instances of OI and Shooter subsystem
	private OI m_oi;
	private Shooter m_shooter;
	private Indexer m_indexer;

	public ShooterCommand(Shooter m_shooter) {
		//Initializes instance variables with OI and Shooter subsystem
		m_oi = OI.getInstance();
		this.m_shooter = m_shooter;
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
		//All stop called (used for testing)
		if (m_oi.getAllStop()) {  //If the B button is being pressed
			//Stop motor
			m_shooter.setSpinSpeed(0);
			return;
		}
		//Spins the shooter based on controller input
		m_shooter.setSpinSpeed(m_oi.getShoot());
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