// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//Import CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Import OI
import frc.robot.OI;
//Import Shooter subsystem
import frc.robot.subsystems.Shooter;

public class Shooter extends CommandBase {
	//Holds instances of OI and Shooter subsystem
	private OI m_oi;
	private Shooter m_shooter;

	public Shooter() {
		//Initializes instance variables with OI and Shooter subsystem
		m_oi=OI.getInstance();
		m_shooter=Shooter.getShooter();
		// Use addRequirements() here to declare subsystem dependencies.
		//addRequirements();
	}

	// Called when the command is initially scheduled.
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	public void execute() {
		m_shooter.spinSpinner(m_oi.getShoot());
	}

	// Called once the command ends or is interrupted.
	public void end(boolean interrupted) {}

	// Returns true when the command should end.
	public boolean isFinished() {
		return false;
	}
}