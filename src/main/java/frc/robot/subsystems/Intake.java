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
//Imports constants
import frc.robot.constants.IntakeConstants;

public class Intake extends SubsystemBase {
	private static Intake m_singleton = null;
	private OI m_oi = OI.getInstance();
	private WPI_VictorSPX pivot;
	private WPI_VictorSPX rollers;

	private Intake() {
		//Creates WPI_VictorSPX motor controllers for the Pivot and Rollers in the Intake
		pivot = new WPI_VictorSPX(IntakeConstants.pivotMotorID);
		rollers = new WPI_VictorSPX(IntakeConstants.rollersMotorID);
		//pivotEncoder = new AnalogInput(/*DriveConstants*/ 1);
	}
	
	//Returns an instance of Intake, creating an instance only when one does not already exist (singleton)
	public static Intake getInstance() {
		if (m_singleton == null)
			m_singleton = new Intake();
		return m_singleton;
	}

	//Spins the rollers on the intake
	public void setRollerSpeed(double speed) {
		//Caps the speed from exceeding the set maxIntakeRollerSpeed
		if (speed > m_oi.getMaxIntakeRollerSpeed()) {
			speed = m_oi.getMaxIntakeRollerSpeed();
		} else if (speed < -m_oi.getMaxIntakeRollerSpeed()) {
			speed = -m_oi.getMaxIntakeRollerSpeed();
		}
		//Sets the roller speed
		rollers.set(-speed);
	}

	public void setPivotSpeed(double speed) {
		//Caps the speed from exceeding the set maxIntakePivotSpeed
		if (speed > m_oi.getMaxIntakePivotSpeed()) {
			speed = m_oi.getMaxIntakePivotSpeed();
		} else if (speed < -m_oi.getMaxIntakePivotSpeed()) {
			speed = -m_oi.getMaxIntakePivotSpeed();
		}
		//Sets the pivot speed
		pivot.set(speed);
	}
}
