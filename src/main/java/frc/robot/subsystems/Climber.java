// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//Imports WPI_VictorSPX (Motor Controller)
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

//Imports SubsystemBase
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//Imports OI
import frc.robot.OI;
import frc.robot.constants.ClimberConstants;

public class Climber extends SubsystemBase {
	private static Climber m_singleton = null;
	private WPI_VictorSPX arm;
	private OI m_oi = OI.getInstance();

	//Constructor
	private Climber() {
		//Creates VictorSPX motor controller for the arm
		arm = new WPI_VictorSPX(ClimberConstants.armMotorID);
		arm.configSelectedFeedbackSensor(com.ctre.phoenix.motorcontrol.FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);  //Fix feedback device
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

	public void resetPosition() {
		arm.setSelectedSensorPosition(0, 0, 0);
	}

	public double getPosition() {
		final double distPerCount = ( 1.756 * Math.PI ) / (2.083 * 4096 );
		return arm.getSelectedSensorPosition(0) * distPerCount;
	}
}