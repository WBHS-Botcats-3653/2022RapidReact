/*----------------------------------------------------------------------------*/
/* Copyright (c) 2021-2022 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.inputs;

import static frc.robot.Constants.IndexerConstants.*;
import static frc.robot.Constants.IntakeConstants.*;
import static frc.robot.Constants.ShooterConstants.kShooterPESensorID;

import edu.wpi.first.wpilibj.DigitalInput;

//Sensor Inputs
public class SI {
	private static SI m_singleton;

	//Photoelectric sensors
	private static DigitalInput intakePE, lowerStoragePE, upperStoragePE, shooterPE;
	//Limit switches
	private static DigitalInput topPivotLimitSwitch, bottomPivotLimitSwitch;

	/** Constructors a new SI */
	public SI() {
		//Photoelectric sensors
		intakePE = new DigitalInput(kIntakePESensorID);
		lowerStoragePE = new DigitalInput(kLowerStoragePESensorID);
		upperStoragePE = new DigitalInput(kUpperStoragePESensorID);
		shooterPE = new DigitalInput(kShooterPESensorID);
		//Limit switches
		topPivotLimitSwitch = new DigitalInput(kTopPivotLimitSwitchID);
		bottomPivotLimitSwitch = new DigitalInput(kBottomPivotLimitSwitchID);
	}

	/** Returns an instance of SI, creating an instance only when one does not already exist (singleton)
	 * @return an instance of SI
	 */
	public static SI getInstance() {
		if (m_singleton == null) {
			m_singleton = new SI();
		}
		return m_singleton;
	}

	/** Returns input from the intake photoelectric sensor
	 * input from the intake photoelectric sensor
	 */
	public boolean isIntakeClosed() {
		return intakePE.get();
	}

	/** Returns input from the lower storage photoelectric sensor
	 * input from the lower storage photoelectric sensor
	 */
	public boolean isLowerStorageClosed() {
		return lowerStoragePE.get();
	}

	/** Returns input from the upper storage photoelectric sensor
	 * input from the upper storage photoelectric sensor
	 */
	public boolean isUpperStorageClosed() {
		return upperStoragePE.get();
	}

	/** Returns input from the shooter photoelectric sensor
	 * input from the shooter photoelectric sensor
	 */
	public boolean isShooterClosed() {
		return shooterPE.get();
	}

	/** Returns input from the top pivot limit switch
	 * input from the top pivot limit switch
	 */
	public boolean isPivotUpLimitClosed() {
		return !topPivotLimitSwitch.get();  //Inverted
	}

	/** Returns input from the bottom pivot limit switch
	 * input from the bottom pivot limit switch
	 */
	public boolean isPivotDownLimitClosed() {
		return !bottomPivotLimitSwitch.get();  //Inverted
	}
}
