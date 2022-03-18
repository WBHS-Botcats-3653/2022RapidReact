// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoCommands;

import static frc.robot.Constants.DriveConstants.*;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Direction;
import frc.robot.subsystems.DriveTrain;

public class TurnCommand extends CommandBase {
	private DriveTrain m_driveTrain = DriveTrain.getInstance();
	private Direction m_direction = Direction.getInstance();

	private double angle, targetAngle;
	private boolean hasFinished = false;
	
	public TurnCommand(double angle) {
		this.angle = angle;
		// Use addRequirements() here to declare subsystem dependencies.
		addRequirements(m_driveTrain, m_direction);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		//Calculates the desired angle based off the current angle
		targetAngle = m_direction.getGyroAngle() + angle;
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//Difference of the between the desired angle the current angle of the robot
		double difference = targetAngle - m_direction.getGyroAngle();
		if (Math.abs(difference) > kThreshold) {  //Robot is not within the threshold of the desired angle
			//Turn towards the desired angle
			m_driveTrain.arcadeDrive(0, difference * kP);
			//Has not finished turning
			hasFinished = false;
		} else {  //Robot is within the threshold of the desired angle
			//Stops the drive
			m_driveTrain.arcadeDrive(0, 0);
			//Has finished turning
			hasFinished = true;
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		//Stops the robot
		m_driveTrain.arcadeDrive(0, 0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		//Whether the robot has finished it's turn
		return hasFinished;
	}
}
