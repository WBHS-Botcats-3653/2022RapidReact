// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//Imports WPI_VictorSPX (Motor Controller)
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

//Imports SubsystemBase
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//Imports Constants
import frc.robot.Constants;

public class Climber extends SubsystemBase {
	private static Climber m_singleton=null;
	private WPI_VictorSPX arm;

	//Constructor
	private Climber() {
		//Creates VictorSPX motor controller for the arm
		arm=new WPI_VictorSPX(Constants.MCID.get("Arm"));
	}

	//Returns an instance of the climber, creating an instance only when one does not already exist (singleton)
	public static Climber getClimber() {
		if (m_singleton==null) {
			m_singleton=new Climber();
		}
		return m_singleton;
	}

	//Raises the arm to it's fully extended position
	public void setArmSpeed(double speed) {
		arm.set(speed);
	}


}