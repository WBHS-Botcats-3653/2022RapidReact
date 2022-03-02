// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.ShooterConstants.kSpinnerMotorID;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.inputs.OI;

public class Shooter extends SubsystemBase {
	private static Shooter shooter = null;
	private OI m_oi = OI.getInstance();
	public static WPI_VictorSPX spinner;

	public Shooter() {
		//Creates WPI_VictorSPX motor controller for the spinner
		spinner = new WPI_VictorSPX(kSpinnerMotorID);
	}

	//Returns an instance of Shooter, creating an instance only when one does not already exist (singleton)
	public static Shooter getInstance() {
		if (shooter == null)
			shooter = new Shooter();
		return shooter;
	}

	/**SHOOTER, but with another name
	 * Spins the spinner to shoot the cargo
	 * 
	 */
	public void setSpinSpeed(double speed) {
		//Caps the spinner speed from exceeding the set maxShootSpeed
		if (Math.abs(speed) > m_oi.getMaxShootSpeed()) speed = speed < 0 ? -1 : 1 * m_oi.getMaxShootSpeed();
		//Sets the spinner speed
		spinner.set(-speed);
	}
}