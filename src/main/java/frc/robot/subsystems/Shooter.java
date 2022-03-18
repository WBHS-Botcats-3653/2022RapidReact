// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.ShooterConstants.kFlywheelMotorID;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
	private static Shooter shooter = null;

	private static WPI_VictorSPX flywheel;

	public Shooter() {
		//Creates WPI_VictorSPX motor controller for the fly wheel
		flywheel = new WPI_VictorSPX(kFlywheelMotorID);
		flywheel.setInverted(true);
	}

	//Returns an instance of Shooter, creating an instance only when one does not already exist (singleton)
	public static Shooter getInstance() {
		if (shooter == null)
			shooter = new Shooter();
		return shooter;
	}

	//Spins the flyWheel at the given speed
	public void setShootSpeed(double speed) {
		//Sets the fly wheel speed
		flywheel.set(speed);
	}
}