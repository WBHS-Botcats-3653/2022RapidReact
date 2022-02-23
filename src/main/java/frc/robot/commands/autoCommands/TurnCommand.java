// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
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
		addRequirements(m_driveTrain, m_direction);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		//Gets the angle of the robot when the turn command is first called
		startAngle = m_direction.getAngle();
		//Turns the robot 
		m_driveTrain.arcadeDrived(0, turnSpeed);
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		//Stops the robot
		m_driveTrain.arcadeDrived(0, 0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		//If the robot has turned to the specified angle
		return Math.abs(m_direction.getAngle() - startAngle) >= Math.abs(angle);
	}
}
