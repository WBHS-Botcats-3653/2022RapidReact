// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.IntakeConstants.kIndexerMotorID;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Indexer extends SubsystemBase {
	private static Indexer m_singleton = null;

	private WPI_VictorSPX indexer;

	/** Creates a new Indexer. */
	public Indexer() {
		indexer = new WPI_VictorSPX(kIndexerMotorID);
		indexer.setInverted(true);
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
		//Sets the indexer speed
		indexer.set(speed);
	}

	//Enables or disabled the neutral brake on the motors
	public void enableMotors(boolean enable) {
		indexer.setNeutralMode(enable ? NeutralMode.Brake : NeutralMode.Coast);
	}
}
