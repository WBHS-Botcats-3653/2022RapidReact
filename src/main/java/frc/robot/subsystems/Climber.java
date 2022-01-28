// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.OI;

public class Climber extends SubsystemBase {
	private static Climber m_singleton=null;
	private WPI_VictorSPX arm;
	private OI m_oi = OI.getInstance();

	//Constructor
	private Climber() {
		//Creates VictorSPX motor controller for the arm
		arm=new WPI_VictorSPX(Constants.MCID.get("Arm"));
	}

	//Returns an instance of the climber
	public static Climber getInstance() {
		if (m_singleton==null) {
			m_singleton=new Climber();
		}
		return m_singleton;
	}
}