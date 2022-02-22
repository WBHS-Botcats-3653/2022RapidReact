// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.ClimberConstants.kArmMotorID;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.inputs.OI;

public class Climber extends SubsystemBase {
	private static Climber m_singleton = null;
	private OI m_oi = OI.getInstance();
	private WPI_VictorSPX arm;

	//Constructor
	private Climber() {
		//Creates VictorSPX motor controller for the arm
		arm = new WPI_VictorSPX(kArmMotorID);
	}

	//Returns an instance of Climber, creating an instance only when one does not already exist (singleton)
	public static Climber getInstance() {
		if (m_singleton == null) {
			m_singleton = new Climber();
		}
		return m_singleton;
	}

	//Sets the arms speed
	public void setArmSpeed(double speed) {
		//Caps the speed from exceeding the set maxArmSpeed
		if (speed > m_oi.getMaxArmSpeed()) speed = m_oi.getMaxArmSpeed();
		//Sets the arm speed
		arm.set(speed);
	}
}