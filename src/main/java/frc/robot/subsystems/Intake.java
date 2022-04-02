// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.IntakeConstants.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
	private static Intake m_singleton = null;

	//Motor controllers
	private final WPI_VictorSPX pivot, rollers;

	private Intake() {
		//Creates motor controllers
		pivot = new WPI_VictorSPX(kPivotMotorID);
		rollers = new WPI_VictorSPX(kRollersMotorID);

		//Sets whether the motors are inverted
		pivot.setInverted(false);
		rollers.setInverted(false);
		
		//Sets whether the motors are in brake or coast mode
		pivot.setNeutralMode(NeutralMode.Coast);
		rollers.setNeutralMode(NeutralMode.Coast);
	}
	
	/** Returns an instance of Intake, creating an instance only when one does not already exist (singleton)
	 * @return an instance of Intake
	 */
	public static Intake getInstance() {
		if (m_singleton == null)
			m_singleton = new Intake();
		return m_singleton;
	}

	/** Sets the intake pivot speed
	 * @param speed to run the motor at
	 */
	public void setPivotSpeed(double speed) {
		//Sets the pivot speed
		pivot.set(speed);
	}

	/** Sets the intake roller speed
	 * @param speed to run the motor at
	 */
	public void setRollerSpeed(double speed) {
		//Sets the roller speed
		rollers.set(speed);
	}
}
