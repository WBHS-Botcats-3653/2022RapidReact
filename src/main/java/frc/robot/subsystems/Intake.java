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

	private WPI_VictorSPX pivot, rollers;

	private Intake() {
		//Creates WPI_VictorSPX motor controllers for the Pivot and Rollers in the Intake
		pivot = new WPI_VictorSPX(kPivotMotorID);
		rollers = new WPI_VictorSPX(kRollersMotorID);
		rollers.setInverted(true);
	}
	
	//Returns an instance of Intake, creating an instance only when one does not already exist (singleton)
	public static Intake getInstance() {
		if (m_singleton == null)
			m_singleton = new Intake();
		return m_singleton;
	}

	//Sets the intake pivot speed
	public void setPivotSpeed(double speed) {
		//Sets the pivot speed
		pivot.set(speed);
	}

	//Sets the intake roller speed
	public void setRollerSpeed(double speed) {
		//Sets the roller speed
		rollers.set(speed);
	}

	//Enables or disabled the neutral brake on the motors
	public void enableMotors(boolean enable) {
		pivot.setNeutralMode(enable ? NeutralMode.Brake : NeutralMode.Coast);
	}
}
