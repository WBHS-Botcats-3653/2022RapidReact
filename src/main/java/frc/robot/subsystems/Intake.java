// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//Imports WPI_VictorSPX (MotorController)
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
//Imports SubsystemBase
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//Imports Constants
import frc.robot.Constants;

public class Intake extends SubsystemBase {
	private static Intake intake=null;
	private WPI_VictorSPX pivot;
	private WPI_VictorSPX rollers;
	private WPI_VictorSPX belt;

	/**Constructor
	 * it is a singleton
	 */
	public Intake() {
		//Creates VictorSPX motor controller for the arm
		//Creates WPI_VictorSPX motor controller for the arm
		pivot=new WPI_VictorSPX(Constants.MCID.get("Pivot"));
		rollers=new WPI_VictorSPX(Constants.MCID.get("Rollers"));
		belt=new WPI_VictorSPX(Constants.MCID.get("Belt"));
	}


	/**Returns an instance of the Intake
	 * this helps with the singleton
	 * 
	 * @return
	 */
	public Intake getIntake(){
		if(intake == null)
			intake = new Intake();
		return intake;
	}

	

}
