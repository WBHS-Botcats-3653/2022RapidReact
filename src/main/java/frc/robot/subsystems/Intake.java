// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.




	

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {


	private static Intake intake =null;

	private WPI_VictorSPX intakePivot;
	private WPI_VictorSPX rollers;

	/**Constructor
	 * it is a singleton
	 */
	public Intake() {




		//Creates VictorSPX motor controller for the arm
		//Creates WPI_VictorSPX motor controller for the arm
		intakePivot=new WPI_VictorSPX(Constants.MCID.get("Intake Pivot"));
		rollers=new WPI_VictorSPX(Constants.MCID.get("Rollers"));
	}


	/**Returns an instance of the Intake
	 * this helps with the singleton
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
	}

	

}
