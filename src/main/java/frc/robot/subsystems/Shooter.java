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

public class Shooter extends SubsystemBase {
	private static Shooter shooter=null;
	private WPI_VictorSPX spinner;

	public Shooter() {
		//Creates WPI_VictorSPX motor controller for the spinner
		spinner=new WPI_VictorSPX(Constants.MCID.get("Spinner"));
	}

	/**Returns an instance of the Intake
	 * this helps with the singleton
	 * 
	 * @return
	 */
	public static Shooter getShooter() {
		if (shooter == null)
			shooter = new Shooter();
		return shooter;
	}

	//Spins the spinner to shoot the cargo
	public void spinSpinner(double speed) {
		spinner.set(speed);
	}
}