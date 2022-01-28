// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

<<<<<<< HEAD
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;


import frc.robot.Constants;
import frc.robot.OI;


public class Intake extends SubsystemBase{
<<<<<<< Updated upstream
	private static intake =null;
=======
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
	private static Intake m_singleton=null;
>>>>>>> 831bd9717004a8c19ca6d1f0b11fb748ea6ed4ca
=======
	private static Intake intake =null;
>>>>>>> Stashed changes
	private WPI_VictorSPX intakePivot;
	private WPI_VictorSPX rollers;

	/**Constructor
	 * it is a singleton
	 */
	public Intake() {
<<<<<<< HEAD

=======
>>>>>>> 831bd9717004a8c19ca6d1f0b11fb748ea6ed4ca
		//Creates VictorSPX motor controller for the arm
		//Creates WPI_VictorSPX motor controller for the arm
		intakePivot=new WPI_VictorSPX(Constants.MCID.get("Intake Pivot"));
		rollers=new WPI_VictorSPX(Constants.MCID.get("Rollers"));
	}
<<<<<<< HEAD

	/**this helps with the singleton
	 * 
	 * @return
	 */
	public Intake getInstance(){
		if(intake == null)
			intake = new Intake();
		return intake;
	}
	//Returns the pivot motor controller
	public WPI_VictorSPX getPivotMotor() {
		return intakePivot;
	}

	public WPI_VictorSPX getRollerMotor() {
		return rollers;

<<<<<<< Updated upstream
=======
>>>>>>> 831bd9717004a8c19ca6d1f0b11fb748ea6ed4ca
	//Returns an instance of the Intake
	public static Intake getInstance() {
		if (m_singleton==null) {
			m_singleton=new Intake();
		}
<<<<<<< HEAD

=======
		return m_singleton;
>>>>>>> 831bd9717004a8c19ca6d1f0b11fb748ea6ed4ca
=======
>>>>>>> Stashed changes
	}
}
