// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Imports ParallelCommandGroup
import edu.wpi.first.wpilibj2.command.PrintCommand;
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

	public ShooterCommand() {
		//Initializes instance variables with OI and Shooter subsystem
		m_oi = OI.getInstance();
		m_shooter = Shooter.getInstance();
		m_indexer = Indexer.getInstance();
		// Use addRequirements() here to declare subsystem dependencies.
		//addRequirements();
	}

	@Override
	public void initialize() {

		if (m_oi.getAllStop()) {
			//Stops motor(s)
			m_shooter.setSpinSpeed(0);
			return;
		}
		m_shooter.setSpinSpeed(m_oi.getShoot());
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//All stop called (used for testing)
		if (m_oi.getAllStop()) {
			//Stops motor(s)
			m_shooter.setSpinSpeed(0);
			return;
		}
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