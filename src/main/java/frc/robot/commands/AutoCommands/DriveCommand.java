// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoCommands;

//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Imports OI
import frc.robot.OI;
//Imports subsystems
import frc.robot.subsystems.Direction;
import frc.robot.subsystems.DriveTrain;

public class DriveCommand extends CommandBase {
	private OI m_oi;
	private DriveTrain driveTrain;
	private Direction gyro;
	private double kP;
	private double distance;
	private double speed;
	private boolean isMovingBack;
	
	public DriveCommand(double distance, double speed, boolean isMovingBack) {
		this.distance = distance;
		this.speed = speed;
		this.isMovingBack = isMovingBack;
		m_oi = OI.getInstance();
		driveTrain = DriveTrain.getInstance();
		gyro = Direction.getInstance();
		kP = 1;
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//Gets the error rate
		double error = kP * -gyro.getRate();
		//Speed decreased (or increased if moving backwards) so that course corrections can be made
		speed = speed + (isMovingBack ? 0.025 : -0.025);
		//Moves the robot at the set speed and making course corrections based off the gyro
		driveTrain.tankDrived(speed + error, speed - error);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		//Stops the robot
		driveTrain.tankDrived(0, 0);  //Eventually have robot start a turn instead of stopping
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		//Stops after having moved the specified distance
		return isMovingBack ? gyro.getDistance() <= distance : gyro.getDistance() >= distance;
	}
}
