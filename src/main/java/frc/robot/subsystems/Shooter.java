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
//Imports ShooterConstants
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
	private static Shooter shooter = null;
	public static WPI_VictorSPX spinner;
	private OI m_oi = OI.getInstance();

	public Shooter() {
		//Creates WPI_VictorSPX motor controller for the spinner
		spinner = new WPI_VictorSPX(ShooterConstants.spinnerMotorID);
	}

	/**Returns an instance of the Intake
	 * this helps with the singleton
	 * 
	 * @return
	 */
	public static Shooter getInstance() {
		if (shooter == null)
			shooter = new Shooter();
		return shooter;
	}

	/**SHOOTER, but with another name
	 * Spins the spinner to shoot the cargo
	 * 
	 */
	public void setSpinSpeed(double speed) {
		//Caps the spinner speed from exceeding the set maxShootSpeed
		if (speed > m_oi.getMaxShootSpeed()) speed = m_oi.getMaxShootSpeed();
		//Sets the spinner speed
		spinner.set(-speed);
	}
}