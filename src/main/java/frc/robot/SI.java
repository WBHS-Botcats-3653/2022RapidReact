/*----------------------------------------------------------------------------*/
/* Copyright (c) 2021-2022 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

//Imports DigitalInput
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.ShooterConstants;

//Sensor Inputs
public class SI {
	private static SI m_singleton;
	private static DigitalInput lowerStoragePE;
	private static DigitalInput upperStoragePE;
	private static DigitalInput shooterPE;
	private static DigitalInput pivotUpButton;
	private static DigitalInput pivotDownButton;
	private static DigitalInput climberDownButton;

	public SI() {
		lowerStoragePE = new DigitalInput(IntakeConstants.lowerStoragePESensorID);
		upperStoragePE = new DigitalInput(IntakeConstants.upperStoragePESensorID);
		shooterPE = new DigitalInput(ShooterConstants.shooterPESensorID);
		pivotUpButton = new DigitalInput(IntakeConstants.pivotUpButtonID);
		pivotDownButton = new DigitalInput(IntakeConstants.pivotDownButtonID);
		climberDownButton = new DigitalInput(ClimberConstants.climberDownButtonID);
	}

	public static SI getInstance() {
		if (m_singleton == null) {
			m_singleton = new SI();
		}
		return m_singleton;
	}

	public boolean getLowerStorageTriggered() {
		return lowerStoragePE.get();
	}

	public boolean getUpperStorageTriggered() {
		return upperStoragePE.get();
	}

	public boolean getShooterTriggered() {
		return shooterPE.get();
	}

	public boolean getPivotUpTriggered() {
		return !pivotUpButton.get();  //Invert (Nick wired it wrong lol)
	}

	public boolean getPivotDownTriggered() {
		return pivotDownButton.get();
	}

	public boolean getClimberDownTriggered() {
		return climberDownButton.get();
	}
}
