// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.IndexerConstants.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Indexer extends SubsystemBase {
	private static Indexer m_singleton = null;

	//Motor controllers
	private final WPI_VictorSPX horizontalIndexer, verticalIndexer;

	/** Creates a new Indexer. */
	public Indexer() {
		//Creates motor controller objects
		horizontalIndexer = new WPI_VictorSPX(kHorizontalIndexerMotorID);
		verticalIndexer = new WPI_VictorSPX(kVerticalIndexerMotorID);

		//Sets whether the motors are inverted
		horizontalIndexer.setInverted(false);
		verticalIndexer.setInverted(true);

		//Sets whether the motors are in brake or coast mode
		horizontalIndexer.setNeutralMode(NeutralMode.Coast);
		verticalIndexer.setNeutralMode(NeutralMode.Brake);
	}

	/** Returns an instance of Indexer, creating an instance only when one does not already exist (singleton)
	 * @return an instance of Indexer
	 */
	public static Indexer getInstance() {
		if (m_singleton == null) {
			m_singleton = new Indexer();
		}
		return m_singleton;
	}

	/** Moves the cargo from the intake to the vertical indexer
	 * @param speed to spin the motor
	 */
	public void setHorizontalIndexerSpeed(double speed) {
		//Sets the horizontal indexer speed
		horizontalIndexer.set(speed);
	}

	/** Raises the cargo up the storage system
	 * @param speed to spin the motor
	 */
	public void setVerticalIndexerSpeed(double speed) {
		//Sets the vertical indexer speed
		verticalIndexer.set(speed);
	}

	/** Enable or disable the neutral brake on the motor
	 * @param enable whether to enable or disable the brake mode
	 */
	public void enableBrake(boolean enable) {
		NeutralMode mode = enable ? NeutralMode.Brake : NeutralMode.Coast;
		verticalIndexer.setNeutralMode(mode);
		horizontalIndexer.setNeutralMode(mode);
	}
}
