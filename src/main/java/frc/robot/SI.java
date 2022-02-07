/*----------------------------------------------------------------------------*/
/* Copyright (c) 2021-2022 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

//Imports DigitalInput
import edu.wpi.first.wpilibj.DigitalInput;

//Sensor Inputs
public class SI {
	private static SI m_singleton;
	private static DigitalInput lowerStoragePE;
	private static DigitalInput upperStoragePE;
	private static DigitalInput shooterPE;
	private static DigitalInput pivotUpButton;
	private static DigitalInput pivotDownButton;

	public SI() {
		lowerStoragePE = new DigitalInput(Constants.DIOID.get("Lower Storage PE Sensor"));
		upperStoragePE = new DigitalInput(Constants.DIOID.get("Upper Storage PE Sensor"));
		shooterPE = new DigitalInput(Constants.DIOID.get("Shooter PE Sensor"));
		pivotUpButton = new DigitalInput(Constants.DIOID.get("Pivot Up Button"));
		pivotDownButton = new DigitalInput(Constants.DIOID.get("Pivot Down Button"));
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
		return pivotUpButton.get();
	}

	public boolean getPivotDownTriggered() {
		return pivotDownButton.get();
	}

	public DigitalInput getButton(){
		return pivotDownButton;
	}
}