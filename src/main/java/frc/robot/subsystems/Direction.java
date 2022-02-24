// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.DriveConstants.*;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Wrapper class to gyro on Roborio.
 */
public class Direction extends SubsystemBase {
	private static Direction m_singleton;
	private ADXRS450_Gyro gyro;
	private Encoder leftEncoder;
	private Encoder rightEncoder;

	
	public Direction() {
		gyro = new ADXRS450_Gyro();
		leftEncoder = new Encoder(kLeftMotorGroupEncoder1, kLeftMotorGroupEncoder2);
		rightEncoder = new Encoder(kRightMotorGroupEncoder1, kRightMotorGroupEncoder2);
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
	public Sendable getGyro() {
		return gyro;
	}

	//Returns the angle
	public double getAngle() {
		return gyro.getAngle();
	}

	//Returns the rate
	public double getRate() {
		return gyro.getRate();
	}
	public Encoder getRightEncoder(){
		return rightEncoder;
	}

	public Encoder getLeftEncoder(){
		return leftEncoder;
	}
	/**
	 * @return encoder's right distance
	 */
	public double getRightDistance() {
		return rightEncoder.getDistance() * -1;  //Inverted (corrects values)
	}

	/**
	 * @return encoder's left distance
	 */
	public double getLeftDistance() {
		return leftEncoder.getDistance();
	}

	/**
	 * @return encoder's distance
	 */
	public double getDistance() {
		return (this.getRightDistance() + this.getLeftDistance()) / 2;
	}

	/**
	 * @return the error between the left and right distance
	 */
	public double getError() {
		return this.getLeftDistance() - this.getRightDistance();
	}

	//Resets the encoders
	public void resetEncoders(){
		rightEncoder.reset();
		leftEncoder.reset();
		// Configures the encoders' distance-per-pulse
		// The robot moves forward 1 foot per encoder rotation
		// There are 256 pulses per encoder rotation
		leftEncoder.setDistancePerPulse(kDistancePerPulse);
		rightEncoder.setDistancePerPulse(kDistancePerPulse);
	}
}
