// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//Imports WPI_VictorSPX (MotorController)
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.StartEndCommand;
//Imports SubsystemBase
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//Imports Constants
import frc.robot.Constants;

public class Intake extends SubsystemBase {
	private static Intake intake=null;
	private WPI_VictorSPX pivot;
	private WPI_VictorSPX rollers;
	private WPI_VictorSPX indexer;

	/**Constructor
	 * it is a singleton
	 */
	private Intake() {
		//Creates WPI_VictorSPX motor controller for the arm
		pivot=new WPI_VictorSPX(Constants.MCID.get("Pivot"));
		rollers=new WPI_VictorSPX(Constants.MCID.get("Rollers"));
		indexer=new WPI_VictorSPX(Constants.MCID.get("Indexer"));
	}

	/**Returns an instance of the Intake
	 * this helps with the singleton
	 * 
	 * @return
	 */
	public static Intake getIntake() {
		if (intake == null)
			intake = new Intake();
		return intake;
	}

	//Spins the rollers on the intake
	public void spinRollers(double speed) {
		rollers.set(speed);
	}

	/**Pivots the intake down
	 * NOTE: it doesn't stop the intake
	 * to stop the intake (the pivot), use stopIntake()
	 * @param speed
	 */
	public void dropIntake(double speed) {
		pivot.set(speed);
	}
	/**this method will set the speed of the pivot to 0
	 * 
	 */
	public void stopIntake(){
		pivot.set(0);
	}
	/**Pivots the intake up
	 * @param speed
	 * NOTE: the parameter speed is inverted, so don't input negative values
	 */
	public void raiseIntake(double speed) {
		pivot.set(- speed);
	}

	//Ejects cargo from intake
	public void ejectCargo(double speed) {
		rollers.set(-speed);
	}

	//Raises the cargo up the intake system
	public void raiseCargo(double speed) {
		indexer.set(speed);
	}
}
