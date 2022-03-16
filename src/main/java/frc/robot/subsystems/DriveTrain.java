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


public class DriveTrain extends SubsystemBase {
	private static DriveTrain m_singleton = null;

	private WPI_VictorSPX driveFrontLeft, driveBackLeft, driveFrontRight, driveBackRight;
	private DifferentialDrive diffDrive;

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
	
	//Returns an instance of DrainTrain, creating an instance only when one does not already exist
	public static DriveTrain getInstance() {
		if (m_singleton == null) {
			m_singleton = new DriveTrain();
		}
		return m_singleton;
	}

	/**Sets the arcade drive speed and rotation
	 * @param speed Speed to drive the robot
	 * @param rotation Amount to rotate
	 */
	public void arcadeDrive(double speed, double rotation) {
		//Prevents speed and rotation from exceeding maximum limit
		if (Math.abs(speed) > 1.0) speed = (speed < 0) ? -1.0 : 1.0;
		if (Math.abs(rotation) > 1.0) rotation = (rotation < 0) ? -1.0 : 1.0;
		//Sets the differential drive speed and rotation
		diffDrive.arcadeDrive(speed, rotation, false);
	}
 
	/**Sets the tank drive left and right drive speeds
	 * @param leftSpeed Speed of the left drive train
	 * @param rightSpeed Speed of the right drive train
	 */
	public void tankDrive(double leftSpeed, double rightSpeed) {
		//Prevents speeds from exceeding the maximum limit
		if (Math.abs(leftSpeed) > 1.0) leftSpeed = (leftSpeed < 0) ? -1.0 : 1.0;
		if (Math.abs(rightSpeed) > 1.0) rightSpeed = (rightSpeed < 0) ? -1.0 : 1.0;
		//Sets the left and right drive train speeds
		diffDrive.tankDrive(leftSpeed, rightSpeed);
	}

	//Returns the differential drive
	public DifferentialDrive getDiffDrive() {
		return diffDrive;
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
