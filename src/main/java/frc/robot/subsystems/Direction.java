// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
//Imports SubsystemBase
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

/**
 * Wrapper class to gyro on Roborio.
 */
public class Direction extends SubsystemBase {
	private static Direction m_singleton;
	//private ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	private Encoder leftEncoder;
	private Encoder rightEncoder;

	
	public Direction() {
		//gyro.calibrate();
		leftEncoder = new Encoder(DriveConstants.leftMotorGroupEncoder1, DriveConstants.leftMotorGroupEncoder2);
		rightEncoder = new Encoder(DriveConstants.rightMotorGroupEncoder1, DriveConstants.rightMotorGroupEncoder2);
		
	}

	//Returns an instance of Direction, creating an instance only when one does not already exist
	public static Direction getInstance() {
		if (m_singleton == null) {
			m_singleton = new Direction();
		}
		return m_singleton;
	}
	
	/*//Returns the gyro
	public Sendable getGyro() {
		return gyro;
	}

	//Returns the angle
	public double getAngle() {
		return gyro.getAngle();
	}

	//Returns the rate
	public double getRate() {
		return  gyro.getRate();
	}*/
	
	/**
	 * @return encoder's right distance
	 */
	public double getRightDistance(){
		return rightEncoder.getDistance();
	}

	/**
	 * @return encoder's left distance
	 */
	public double getLeftDistance(){
		return leftEncoder.getDistance();
	}

	/**
	 * @return encoder's distance
	 */
	public double getDistance(){
		return (this.getRightDistance() + this.getLeftDistance()) / 2;
	}

	//Resets the encoders
	public void resetEncoders(){
		rightEncoder.reset();
		leftEncoder.reset();
		// Configures the encoders' distance-per-pulse
		// The robot moves forward 1 foot per encoder rotation
		// There are 256 pulses per encoder rotation
		leftEncoder.setDistancePerPulse(1./256.);
		rightEncoder.setDistancePerPulse(1./256.);
	}
}
