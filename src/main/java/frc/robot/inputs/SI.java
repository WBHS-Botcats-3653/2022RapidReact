/*----------------------------------------------------------------------------*/
/* Copyright (c) 2021-2022 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.inputs;

//Imports DigitalInput
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.constants.ClimberConstants;
import frc.robot.constants.IntakeConstants;
import frc.robot.constants.ShooterConstants;

//Sensor Inputs
public class SI {
	private static SI m_singleton;
	//Photoelectric sensors
	private static DigitalInput lowerStoragePE;
	private static DigitalInput upperStoragePE;
	private static DigitalInput shooterPE;
	//Limit switches
	private static DigitalInput topPivotLimitSwitch;
	private static DigitalInput bottomPivotLimitSwitch;
	private static DigitalInput climberDownButton;

	public SI() {
		//Photoelectric sensors
		lowerStoragePE = new DigitalInput(IntakeConstants.lowerStoragePESensorID);
		upperStoragePE = new DigitalInput(IntakeConstants.upperStoragePESensorID);
		shooterPE = new DigitalInput(ShooterConstants.shooterPESensorID);
		//Limit switches
		topPivotLimitSwitch = new DigitalInput(IntakeConstants.topPivotLimitSwitchID);
		bottomPivotLimitSwitch = new DigitalInput(IntakeConstants.bottomPivotLimitSwitchID);
		climberDownButton = new DigitalInput(ClimberConstants.bottomClimberLimitSwitchID);
	}

	//Returns an instance of SI, creating an instance only when one does not already exist (singleton)
	public static SI getInstance() {
		if (m_singleton == null) {
			m_singleton = new SI();
		}
		return m_singleton;
	}

	//Returns input from the lower storage photoelectric sensor
	public boolean getLowerStorageTriggered() {
		return lowerStoragePE.get();
	}

	//Returns input from the upper storage photoelectric sensor
	public boolean getUpperStorageTriggered() {
		return upperStoragePE.get();
	}

	//Returns input from the shooter photoelectric sensor
	public boolean getShooterTriggered() {
		return shooterPE.get();
	}

	//Returns input from the top pivot limit switch
	public boolean getPivotUpLimitTriggered() {
		return !topPivotLimitSwitch.get();  //Inverted (electrical issue)
	}

	//Returns input from the bottom pivot limit switch
	public boolean getPivotDownLimitTriggered() {
		return !bottomPivotLimitSwitch.get();
	}

	//Returns input from the top pivot limit switch
	public boolean getClimberDownLimitTriggered() {
		return !climberDownButton.get();  //Inverted (electrical issue)
	}
}
