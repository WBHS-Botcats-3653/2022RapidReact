// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.IntakeConstants.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.NetworkEntries;
import frc.robot.inputs.OI;
import frc.robot.inputs.SI;

public class Intake extends SubsystemBase {
	private static Intake m_singleton = null;
	private static final OI m_oi = OI.getInstance();
	private static final SI m_si = SI.getInstance();

	//Motor controllers
	private final WPI_VictorSPX pivot, rollers;
	private boolean isLowered = false;

	//The previous speed of the pivot when the speed has been lowered
	private double prevPivotSpeed;

	private Intake() {
		//Creates motor controllers
		pivot = new WPI_VictorSPX(kPivotMotorID);
		rollers = new WPI_VictorSPX(kRollersMotorID);

		//Sets whether the motors are inverted
		pivot.setInverted(false);
		rollers.setInverted(false);
		
		//Sets whether the motors are in brake or coast mode
		pivot.setNeutralMode(NeutralMode.Coast);
	}

	@Override
	public void periodic() {
		//Decreases the speed of the pivot when the lower limit is triggered
		//If the pivot speed is less than the lowered pivot speed break out of the method
		if (NetworkEntries.m_nteMaxPivotSpeed.getDouble(0) < kLoweredPivotSpeed) return;
		//If the pivot down limit is closed
		if (m_si.isPivotDownLimitClosed() && !m_oi.getManualIntakeUp()) {
			//If the pivot is already lowered break out of the method
			if (isLowered) return;
			//Set the previous pivot speed to the current pivot speed
			prevPivotSpeed = NetworkEntries.m_nteMaxPivotSpeed.getDouble(0);
			//Set the pivot speed to a lowered speed
			NetworkEntries.m_nteMaxPivotSpeed.setDouble(kLoweredPivotSpeed);
			//The pivot speed has been lowered
			isLowered = true;
		} else if (isLowered) {  //If the pivot speed has been lowered
			//Set the pivot speed to the previous speed
			NetworkEntries.m_nteMaxPivotSpeed.setDouble(prevPivotSpeed);
			//The pivot speed has not been lowered
			isLowered = false;
		}
	}
	
	/** Returns an instance of Intake, creating an instance only when one does not already exist (singleton)
	 * @return an instance of Intake
	 */
	public static Intake getInstance() {
		if (m_singleton == null)
			m_singleton = new Intake();
		return m_singleton;
	}

	/** Sets the intake pivot speed
	 * @param speed to run the motor at
	 */
	public void setPivotSpeed(double speed) {
		//Sets the pivot speed
		pivot.set(speed);
	}

	/** Sets the intake roller speed
	 * @param speed to run the motor at
	 */
	public void setRollerSpeed(double speed) {
		//Sets the roller speed
		rollers.set(speed);
	}
}
