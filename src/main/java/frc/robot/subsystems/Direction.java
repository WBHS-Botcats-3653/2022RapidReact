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
	
	//Gyro
	private ADXRS450_Gyro gyro;
	//Encoders
	private Encoder leftEncoder, rightEncoder;
	
	/** Constructors a new Direction */
	public Direction() {
		//Creates new Gyro object
		gyro = new ADXRS450_Gyro();

		//Creates new encoder objects
		leftEncoder = new Encoder(kLeftMotorGroupEncoderIDA, kLeftMotorGroupEncoderIDB, false);
		rightEncoder = new Encoder(kRightMotorGroupEncoderIDA, kRightMotorGroupEncoderIDB, true);  //Direction reversed
	}

	/** Returns an instance of Direction, creating an instance only when one does not already exist
	 * @return an instance of Direction
	 */
	public static Direction getInstance() {
		if (m_singleton == null) {
			m_singleton = new Direction();
		}
		return m_singleton;
	}

	/** Calibrates the gyro */
	public void calibrateGyro() {
		gyro.calibrate();
	}
	
	/** Returns the gyro
	 * @return the gyro
	 */
	public ADXRS450_Gyro getGyro() {
		return gyro;
	}

	/** Returns the angle of the robot
	 * @return the angle of the robot
	 */
	public double getGyroAngle() {
		return gyro.getAngle();
	}

	/** Returns the current heading of the robot as a Rotation2d object
	 * @return the heading of the robot
	 */
	public Rotation2d getHeading() {
		return Rotation2d.fromDegrees(-this.getGyroAngle());  //Counterclockwise = positive
	}

	/** Returns the distance the right wheels of the drivetrain have moved
	 * @return the right encoder's distance
	 */
	public double getRightEncoderDistance() {
		//return rightEncoder.getDistance();
		return rightEncoder.getDistance() / 1.11437;
	}

	/** Returns the distance the left wheels of the drivetrain have mvoed
	 * @return the left encoder's distance
	 */
	public double getLeftEncoderDistance() {
		//return leftEncoder.getDistance();
		return leftEncoder.getDistance() / 1.11019;
	}

	/** Returns the rate the right wheels of the drivetrain are moving at
	 * @return the rate of the right encoder
	 */
	public double getRightEncoderRate() {
		return rightEncoder.getRate();
	}

	/** Returns the rate the left wheels of the drivetrain are moving at
	 * @return the rate of the left encoder
	 */
	public double getLeftEncoderRate() {
		return leftEncoder.getRate();
	}

	/** Returns the mean distance of the encoders
	 * @return the mean encoder distance
	 */
	public double getDistance() {
		return (this.getRightEncoderDistance() + this.getLeftEncoderDistance()) / 2;
	}

	/** Returns the error between the left and right distances
	 * @return the error between the left and right distances
	 */
	public double getError() {
		return this.getLeftEncoderDistance() - this.getRightEncoderDistance();
	}

	/** Returns whether the robot is currently moving
	 * @return whether the robot is currently moving
	 */
	public boolean isMoving() {
		//Returns if the rate either the left or the right encoder rate are changing
		return this.getLeftEncoderRate() != 0 || this.getRightEncoderRate() != 0;
	}

	/** Resets the encoders */
	public void resetEncoders() {
		//Resets the encoders
		rightEncoder.reset();
		leftEncoder.reset();

		//Sets the encoders distance per pulse
		leftEncoder.setDistancePerPulse(kDriveDistancePerPulse);
		rightEncoder.setDistancePerPulse(kDriveDistancePerPulse);
	}
}
