// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.ShooterConstants.kFlyWheelMotorID;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.inputs.OI;

public class Shooter extends SubsystemBase {
	private static Shooter shooter = null;
	private OI m_oi = OI.getInstance();

	private static WPI_VictorSPX flyWheel;
	private double maxFlyWheelSpeed;

	public Shooter() {
		//Creates WPI_VictorSPX motor controller for the fly wheel
		flyWheel = new WPI_VictorSPX(kFlyWheelMotorID);
		flyWheel.setInverted(true);
	}

	@Override
	public void periodic() {
		maxFlyWheelSpeed = m_oi.getMaxShootSpeed();
	}

	//Returns an instance of Shooter, creating an instance only when one does not already exist (singleton)
	public static Shooter getInstance() {
		if (shooter == null)
			shooter = new Shooter();
		return shooter;
	}

	/**SHOOTER, but with another name
	 * Spins the flyWheel to shoot the cargo
	 * 
	 */
	public void setSpinSpeed(double speed) {
		//Caps the fly wheel speed from exceeding the set maxShootSpeed
		if (Math.abs(speed) > maxFlyWheelSpeed) speed = (speed < 0 ? -1 : 1) * maxFlyWheelSpeed;
		//Sets the fly wheel speed
		flyWheel.set(speed);
	}
}