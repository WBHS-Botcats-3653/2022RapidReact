// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.robot.Constants;
import frc.robot.OI;

public class Climber extends SubsystemBase {
	private WPI_VictorSPX arm;
	private OI m_oi = OI.getInstance();

	//Constructor
	public Climber() {
		//Creates VictorSPX motor controller for the arm
		arm=new WPI_VictorSPX(Constants.MCID.get("Arm"));
	}

	//Returns the arm motor controller
	public WPI_VictorSPX getMotor() {
		return arm;
	}
}