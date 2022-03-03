// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.DriveConstants.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.inputs.OI;


public class DriveTrain extends SubsystemBase {
	private static DriveTrain driveTrain = null;
	public OI m_oi = OI.getInstance();

	private WPI_VictorSPX driveFrontLeft;
	private WPI_VictorSPX driveBackLeft;
	private WPI_VictorSPX driveFrontRight;
	private WPI_VictorSPX driveBackRight;
	private DifferentialDrive diffDrive;
	private double maxDriveSpeed;

	//documentation for WPI_VictorSPX: 
	//https://robotpy.readthedocs.io/projects/ctre/en/stable/ctre/WPI_VictorSPX.html

	//Constructor
	private DriveTrain() {
		//Wheel motors
		driveFrontLeft = new WPI_VictorSPX(kFrontLeftMotorID);
		driveBackLeft = new WPI_VictorSPX(kBackLeftMotorID);
		driveFrontRight = new WPI_VictorSPX(kFrontRightMotorID);
		driveBackRight = new WPI_VictorSPX(kBackRightMotorID);

		//Groups the wheel motors
		MotorControllerGroup driveLeft = new MotorControllerGroup(driveFrontLeft, driveBackLeft);
		MotorControllerGroup driveRight = new MotorControllerGroup(driveFrontRight, driveBackRight);

		//Creates differential drive
		diffDrive = new DifferentialDrive(driveLeft, driveRight);

		//Reverses left motor direction
		driveLeft.setInverted(true);
	}

	@Override
	public void periodic() {
		maxDriveSpeed = m_oi.getMaxDriveSpeed();
	}
	
	//Returns an instance of DrainTrain, creating an instance only when one does not already exist
	public static DriveTrain getInstance() {
		if (driveTrain == null) {
			driveTrain = new DriveTrain();
		}
		return driveTrain;
	}

	/**Sets the arcade drive speed and rotation
	 * @param speed Speed to drive the robot
	 * @param rotation Amount to rotate
	 */
	public void arcadeDrived(double speed, double rotation) {
		//Caps the speed from exceeding the set maxDriveSpeed
		if (Math.abs(speed) > maxDriveSpeed) speed = (speed < 0 ? -1 : 1) * maxDriveSpeed;
		//Sets the differential drive speed and rotation
		diffDrive.arcadeDrive(speed, rotation);
	}

	/**Sets the tank drive left wheel speed and right wheel speed
	 * Used for autonomous
	 * @param leftSpeed Speed to set to the left side of the drive train
	 * @param rightSpeed Speed to set to the right side of the drive train
	 */
	public void tankDrived(double leftSpeed, double rightSpeed) {
		//Caps the speed from exceeding the set maxDriveSpeed
		if (Math.abs(leftSpeed) > maxDriveSpeed) leftSpeed = (leftSpeed < 0 ? -1 : 1) * maxDriveSpeed;
		if (Math.abs(rightSpeed) > maxDriveSpeed) rightSpeed = (rightSpeed < 0 ? -1 : 1) * maxDriveSpeed;
		//Sets the differential drive left wheel speed and right wheel speed
		diffDrive.tankDrive(leftSpeed, rightSpeed);
	}

	//Enables or disabled the neutral brake on the motors
	public void enableMotors(boolean enable) {
		NeutralMode mode = enable ? NeutralMode.Brake : NeutralMode.Coast;
		driveFrontLeft.setNeutralMode(mode);
		driveBackLeft.setNeutralMode(mode);
		driveFrontRight.setNeutralMode(mode);
		driveBackRight.setNeutralMode(mode);
	}
}
