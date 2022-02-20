// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoCommands;

//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Imports Direction
import frc.robot.subsystems.Direction;
//Imports DriveTrain subsystem
import frc.robot.subsystems.DriveTrain;

public class DriveCommand extends CommandBase {
	private DriveTrain m_driveTrain;
	private Direction m_direction;
	private double distance;
	private double speed;
	private double currentDistance;
	private int direction;
	private final int kP = 1;

	public DriveCommand(double distance, double speed) {
		m_driveTrain = DriveTrain.getInstance();
		m_direction = Direction.getInstance();
		this.distance = distance;
		direction = distance < 0 ? -1 : 1;
		this.speed = speed * direction;
		// Use addRequirements() here to declare subsystem dependencies.
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		m_direction.resetEncoders();
		currentDistance = 0;
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//Gets the distance the robot has moved from the encoders
		currentDistance = m_direction.getDistance();
		//Gets the error rate
		double error = kP * m_direction.getError() * direction;
		//Moves the robot at the set speed and makes course corrections based off the encoders
		m_driveTrain.tankDrived(speed + error, speed - error);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		m_driveTrain.tankDrived(0, 0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return Math.abs(distance) - Math.abs(currentDistance) <= 0;
	}
}
