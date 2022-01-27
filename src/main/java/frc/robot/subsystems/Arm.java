// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.MotorSafety;
import edu.wpi.first.motorcontrol.PWMMotorController;
import edu.wpi.first.motorcontrol.PWMVictorSPX;
import frc.robot.Constants;
import frc.robot.OI;

public class Arm extends SubsystemBase {
	public VictorSPX arm;

	//Constructor
	public Arm() {
		//Creates VictorSPX motor controller for the arm
		arm=new VictorSPX(Constants.MCID.get("Arm"));
	}

	//Returns the arm motor controller
	public VictorSPX getMotor() {
		return arm;
	}
}