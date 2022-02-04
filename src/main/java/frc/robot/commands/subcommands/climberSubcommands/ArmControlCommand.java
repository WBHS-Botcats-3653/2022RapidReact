// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.subcommands.climberSubcommands;


//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Imports Climber
import frc.robot.subsystems.Climber;

public class ArmControlCommand extends CommandBase {
	private Climber m_climber;
	private double speed;

	/** Creates a new ArmControlCommand. */
	public ArmControlCommand(double speed) {
		// Use addRequirements() here to declare subsystem dependencies.
		m_climber = Climber.getInstance();
		this.speed = speed;
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {

		m_climber.setArmSpeed(speed);

	}
	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {

		m_climber.setArmSpeed(0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}