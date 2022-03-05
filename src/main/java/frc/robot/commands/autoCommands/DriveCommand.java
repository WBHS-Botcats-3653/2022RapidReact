// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autoCommands;

import static frc.robot.Constants.DriveConstants.kP;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Direction;
import frc.robot.subsystems.DriveTrain;

public class DriveCommand extends CommandBase {
	private DriveTrain m_driveTrain;
	private Direction m_direction;

	private double distance, speed;
	private boolean isCollectingCargo;

	private double currentDistance;
	private int direction;

	public DriveCommand(double distance, double speed, boolean isCollectingCargo) {
		m_driveTrain = DriveTrain.getInstance();
		m_direction = Direction.getInstance();
		this.distance = distance;
		direction = distance < 0 ? -1 : 1;
		this.speed = speed * direction;
		this.isCollectingCargo = isCollectingCargo;
		// Use addRequirements() here to declare subsystem dependencies.
		addRequirements(m_driveTrain, m_direction);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		//Resets the encoders
		m_direction.resetEncoders();
		//Sets distance traveled to 0
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
		//If the DriveCommand is running sequentially with the CollectCargoCommand
		if (isCollectingCargo) {
			//End the cargo collection command
			CollectCargoCommand.endCommand = true;
		}
		//Stops the robot
		m_driveTrain.tankDrived(0, 0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		//Stops driving when the robot has traversed the specified distance
		return Math.abs(distance) - Math.abs(currentDistance) <= 0;
	}
}
