// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.ADIS16470_IMU;

/**
 * Wrapper class to gyro on Roborio.
 */
public class Direction {
	private static Direction m_singleton;
	private ADIS16470_IMU m_gyro;
	
	private Direction() {
		m_gyro=new ADIS16470_IMU();
		m_gyro.calibrate();
	}

	public Sendable getGyro() {
		return m_gyro;
	}

	public double getAngle() {
		return m_gyro.getAngle();
	}

	public static Direction getInstance() {
		if (m_singleton==null) {
			m_singleton=new Direction();
		}
		return m_singleton;
	}
}