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

	//Motor controllers
	private final WPI_VictorSPX climberArm, extenderWinch, hookWinch;

	/** Constructor */
	private Climber() {
		//Creates motor controller objects
		climberArm = new WPI_VictorSPX(kClimberArmMotorID);
		extenderWinch = new WPI_VictorSPX(kExtenderWinchMotorID);
		hookWinch = new WPI_VictorSPX(kHookWinchMotorID);

		//Sets whether the motor should be inverted
		climberArm.setInverted(false);
		extenderWinch.setInverted(true);
		hookWinch.setInverted(true);

		//Sets whether the motor should be in Brake or Coast mode when neutral
		climberArm.setNeutralMode(NeutralMode.Brake);
		extenderWinch.setNeutralMode(NeutralMode.Coast);
		hookWinch.setNeutralMode(NeutralMode.Brake);
	}

	/** Returns an instance of Climber, creating an instance only when one does not already exist (singleton)
	 * @return an instance of Climber
	 */
	public static Climber getInstance() {
		if (m_singleton == null) {
			m_singleton = new Climber();
		}
		return m_singleton;
	}

	/** Sets the climber arm speed
	 * @param speed the speed to run the motor at
	 */
	public void setClimberArmSpeed(double speed) {
		//Sets the climber arm speed
		climberArm.set(speed);
	}

	/** Sets the extender winch speed
	 * @param speed the speed to run the motor at
	 */
	public void setExtenderWinchSpeed(double speed) {
		//Sets the extender winch speed
		extenderWinch.set(speed);
	}

	/** Sets the hook winch speed
	 * @param speed the speed to run the motor at
	 */
	public void setHookWinchSpeed(double speed) {
		//Sets the hook winch speed
		hookWinch.set(speed);
	}
}