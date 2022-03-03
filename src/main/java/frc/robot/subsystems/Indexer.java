// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.IntakeConstants.kIndexerMotorID;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.inputs.OI;

public class Indexer extends SubsystemBase {
	private static Indexer m_singleton = null;
	private OI m_oi = OI.getInstance();

	private WPI_VictorSPX indexer;
	private double maxIndexSpeed;

	/** Creates a new Indexer. */
	public Indexer() {
		indexer = new WPI_VictorSPX(kIndexerMotorID);
		indexer.setInverted(true);
	}

	@Override
	public void periodic() {
		maxIndexSpeed = m_oi.getMaxIndexerSpeed();
	}

	//Returns an instance of Indexer, creating an instance only when one does not already exist (singleton)
	public static Indexer getInstance() {
		if (m_singleton == null) {
			m_singleton = new Indexer();
		}
		return m_singleton;
	}

	/**Raises the cargo up the storage system
	 * --This is the indexer--
	 */
	public void setIndexerSpeed(double speed) {
		//Caps the spinner speed from exceeding the set maxIndexerSpeed
		if (Math.abs(speed) > maxIndexSpeed) speed = (speed < 0 ? -1 : 1) * maxIndexSpeed;
		//Sets the indexer speed
		indexer.set(speed);
	}

	//Enables or disabled the neutral brake on the motors
	public void enableMotors(boolean enable) {
		indexer.setNeutralMode(enable ? NeutralMode.Brake : NeutralMode.Coast);
	}
}
