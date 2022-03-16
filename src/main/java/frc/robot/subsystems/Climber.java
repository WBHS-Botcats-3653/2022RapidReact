// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.ClimberConstants.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
	private static Climber m_singleton = null;

	private WPI_VictorSPX climberArm, extender, wench;

	//Constructor
	private Climber() {
		climberArm = new WPI_VictorSPX(kClimberArmMotorID);
		extender = new WPI_VictorSPX(kTraversalArmMotorID);
		wench = new WPI_VictorSPX(kTraversalWenchMotorID);
		extender.setInverted(true);
		wench.setInverted(true);
	}

	//Returns an instance of Climber, creating an instance only when one does not already exist (singleton)
	public static Climber getInstance() {
		if (m_singleton == null) {
			m_singleton = new Climber();
		}
		return m_singleton;
	}

	//Sets the climber arm speed
	public void setClimberArmSpeed(double speed) {
		//Sets the climber arm speed
		climberArm.set(speed);
	}

	//Sets the extension speed
	public void setExtensionSpeed(double speed) {
		//Sets the extension speed
		extender.set(speed);
	}

	//Sets the wench speed
	public void setWenchSpeed(double speed) {
		//Sets the traversal wench speed
		wench.set(speed);
	}
	
	//Enables or disabled the neutral brake on the motors
	public void enableMotors(boolean enable) {
		NeutralMode mode = enable ? NeutralMode.Brake : NeutralMode.Coast;
		climberArm.setNeutralMode(mode);
		extender.setNeutralMode(mode);
	}
}