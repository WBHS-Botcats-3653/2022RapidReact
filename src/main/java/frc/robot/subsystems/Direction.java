// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//Imports Sendable (Who even knows)
import edu.wpi.first.util.sendable.Sendable;
//Imports ADIS16470_IMU (Gyroscope)
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.Encoder;
//Imports SubsystemBase
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.OI;
import frc.robot.Constants.DriveConstants;

/**
 * Wrapper class to gyro on Roborio.
 */
public class Direction extends SubsystemBase {
	private static Direction m_singleton;
	private ADIS16470_IMU m_gyro = new ADIS16470_IMU();
	private OI m_oi;
	private DriveTrain driveTrain;
	private Shooter shooter;
	private double kP;
	private Encoder encoder;
	private boolean hasFinished;
	private String stage;

	
	public Direction() {
		//
		//m_gyro.calibrate();
		m_oi = OI.getInstance();
		driveTrain = DriveTrain.getInstance();
		shooter = Shooter.getInstance();

		
		kP = 1;
		encoder = new Encoder(DriveConstants.leftMotorGroupEncoder, DriveConstants.rightMotorGroupEncoder);
		hasFinished = false;
		// Configures the encoders' distance-per-pulse
		// The robot moves forward 1 foot per encoder rotation
		// There are 256 pulses per encoder rotation
		encoder.setDistancePerPulse(1./256.);
	}

	public static Direction getInstance() {
		if (m_singleton == null) {
			m_singleton = new Direction();
		}
		return m_singleton;
	}
	
	public Sendable getGyro() {
		return m_gyro;
	}

	public double getAngle() {
		return m_gyro.getAngle();
	}

	public double getRate() {
		return  m_gyro.getRate();
	}
	
	/**
	 * @return encoder's distance since last reset
	 */
	
	public double getDrivedDistance(){
		return encoder.getDistance();
	}
	
}
