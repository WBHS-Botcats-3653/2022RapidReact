// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.DriveConstants.*;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Wrapper class to gyro on Roborio.
 */
public class Direction extends SubsystemBase {
	private static Direction m_singleton;
	
	private ADXRS450_Gyro gyro;
	private Encoder leftEncoder, rightEncoder;
	
	public Direction() {
		gyro = new ADXRS450_Gyro();
		leftEncoder = new Encoder(kLeftMotorGroupEncoderIDA, kLeftMotorGroupEncoderIDB, true);  //Direction reversed
		rightEncoder = new Encoder(kRightMotorGroupEncoderIDA, kRightMotorGroupEncoderIDB, false);
	}

	//Returns an instance of Direction, creating an instance only when one does not already exist
	public static Direction getInstance() {
		if (m_singleton == null) {
			m_singleton = new Direction();
		}
		return m_singleton;
	}

	//Calibrates the gyro
	public void calibrateGyro() {
		gyro.calibrate();
	}
	
	//Returns the gyro
	public ADXRS450_Gyro getGyro() {
		return gyro;
	}

	//Returns the angle
	public double getGyroAngle() {
		return gyro.getAngle();
	}

	//Returns the current heading of the robot
	public Rotation2d getHeading() {
		return Rotation2d.fromDegrees(-this.getGyroAngle());  //Counterclockwise = positive
	}

	//Returns the right encoder
	public Encoder getRightEncoder() {
		return rightEncoder;
	}

	//Returns the left encoder
	public Encoder getLeftEncoder() {
		return leftEncoder;
	}

	/**
	 * @return encoder's right distance
	 */
	public double getRightEncoderDistance() {
		return rightEncoder.getDistance();
		//return rightEncoder.getDistance() / 1.11437;
	}

	/**
	 * @return encoder's left distance
	 */
	public double getLeftEncoderDistance() {
		return leftEncoder.getDistance();
		//return leftEncoder.getDistance() / 1.11019;
	}

	//Return the rate of the right encoder
	public double getRightEncoderRate() {
		return rightEncoder.getRate();
	}

	//Return the rate of the left encoder
	public double getLeftEncoderRate() {
		return leftEncoder.getRate();
	}

	//Returns whether the robot is currently moving
	public boolean isMoving() {
		//Returns if the rate either the left or the right encoder rate are changing
		return this.getLeftEncoderRate() != 0 || this.getRightEncoderRate() != 0;
	}

	/**
	 * @return encoder's distance
	 */
	public double getEncoderDistance() {
		return (this.getRightEncoderDistance() + this.getLeftEncoderDistance()) / 2;
	}

	/**
	 * @return the error between the left and right distance
	 */
	public double getEncoderError() {
		return this.getLeftEncoderDistance() - this.getRightEncoderDistance();
	}

	//Resets the encoders
	public void resetEncoders() {
		rightEncoder.reset();
		leftEncoder.reset();
		// Configures the encoders' distance-per-pulse
		leftEncoder.setDistancePerPulse(kLeftDriveDistancePerPulse);
		rightEncoder.setDistancePerPulse(kRightDriveDistancePerPulse);
	}
}
