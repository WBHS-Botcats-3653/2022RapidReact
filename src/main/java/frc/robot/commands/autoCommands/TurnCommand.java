// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoCommands;

//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Imports subsystems
import frc.robot.subsystems.Direction;
import frc.robot.subsystems.DriveTrain;

public class TurnCommand extends CommandBase {
	private DriveTrain m_driveTrain;
	private Direction m_direction;

	private double turnSpeed = 0.6;  //Change later maybe
	private double angle;
	private double startAngle;
	
	public TurnCommand(double angle) {
		m_driveTrain = DriveTrain.getInstance();
		m_direction = Direction.getInstance();
		this.angle = angle;
		if (angle < 0) turnSpeed *= -1;
		// Use addRequirements() here to declare subsystem dependencies.
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		startAngle = m_direction.getAngle();
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		m_driveTrain.arcadeDrived(0, turnSpeed);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		m_driveTrain.arcadeDrived(0, 0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return Math.abs(m_direction.getAngle() - startAngle) >= Math.abs(angle);
	}
}
