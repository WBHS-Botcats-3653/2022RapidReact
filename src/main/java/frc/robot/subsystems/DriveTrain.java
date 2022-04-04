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


public class Drivetrain extends SubsystemBase {
	private static Drivetrain m_singleton = null;

	//Motor controllers
	private final WPI_VictorSPX driveFrontLeft, driveBackLeft, driveFrontRight, driveBackRight;

	//Differential drive
	private final DifferentialDrive diffDrive;

	//documentation for WPI_VictorSPX: 
	//https://robotpy.readthedocs.io/projects/ctre/en/stable/ctre/WPI_VictorSPX.html

	/** Constructs a new Drivetrain */
	private Drivetrain() {
		//Wheel motors
		driveFrontLeft = new WPI_VictorSPX(kFrontLeftMotorID);
		driveBackLeft = new WPI_VictorSPX(kBackLeftMotorID);
		driveFrontRight = new WPI_VictorSPX(kFrontRightMotorID);
		driveBackRight = new WPI_VictorSPX(kBackRightMotorID);

		//Groups the wheel motors
		final MotorControllerGroup driveLeft = new MotorControllerGroup(driveFrontLeft, driveBackLeft);
		final MotorControllerGroup driveRight = new MotorControllerGroup(driveFrontRight, driveBackRight);

		//Creates differential drive
		diffDrive = new DifferentialDrive(driveLeft, driveRight);

		//Reverses left motor direction
		driveLeft.setInverted(false);
		driveRight.setInverted(true);

		//Sets motors to coast
		enableBrake(false);
	}

	/** Returns an instance of Draintrain, creating an instance only when one does not already exist
	 * @return an instance of Drivetrain
	 */
	public static Drivetrain getInstance() {
		if (m_singleton == null) {
			m_singleton = new Drivetrain();
		}
		return m_singleton;
	}

	/** Sets the arcade drive speed and rotation
	 * @param xSpeed The speed along the x-axis
	 * @param zRotation The rotation rate around the z-axis
	 */
	public void arcadeDrive(double xSpeed, double zRotation) {
		//Prevents speed and rotation from exceeding maximum limit
		if (Math.abs(xSpeed) > 1.0) xSpeed = Math.signum(xSpeed);
		if (Math.abs(zRotation) > 1.0) zRotation = Math.signum(zRotation);
		//Sets the differential drive speed and rotation
		diffDrive.arcadeDrive(xSpeed, zRotation, false);
	}
 
	/** Sets the tank drive left and right drive speeds
	 * @param leftSpeed Speed of the left drive train
	 * @param rightSpeed Speed of the right drive train
	 */
	public void tankDrive(double leftSpeed, double rightSpeed) {
		//Prevents speeds from exceeding the maximum limit
		if (Math.abs(leftSpeed) > 1.0) leftSpeed = Math.signum(leftSpeed);
		if (Math.abs(rightSpeed) > 1.0) rightSpeed = Math.signum(rightSpeed);
		//Sets the left and right drive train speeds
		diffDrive.tankDrive(leftSpeed, rightSpeed);
	}

	/** Sets the tank drive left and right drive volts
	 * @param leftVolts Volts of the left drive train
	 * @param rightVolts Volts of the right drive train
	 */
	public void tankDriveVolts(double leftVolts, double rightVolts) {
		diffDrive.tankDrive(leftVolts / 12, rightVolts / 12);
	}

	/** Returns the differential drive
	 * @return the differential drive
	 */
	public DifferentialDrive getDiffDrive() {
		return diffDrive;
	}

	/** Enables or disables the neutral brake on the motors
	 * @param enable whether to enable or disable the brake mode
	 */
	public void enableBrake(boolean enable) {
		final NeutralMode mode = enable ? NeutralMode.Brake : NeutralMode.Coast;
		driveFrontLeft.setNeutralMode(mode);
		driveBackLeft.setNeutralMode(mode);
		driveFrontRight.setNeutralMode(mode);
		driveBackRight.setNeutralMode(mode);
	}
}
