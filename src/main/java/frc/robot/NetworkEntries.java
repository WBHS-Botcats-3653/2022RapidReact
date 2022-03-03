// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTableEntry;

/** This class will store the NetworkTableEntry(ies)
 * 
 * 
*/
public class NetworkEntries {
    // Config Tab
	public static NetworkTableEntry m_nteIsPivotAssistEnabled;
	public static NetworkTableEntry m_nteIsSmartIntakeEnabled;

	// Test Tab
	public static NetworkTableEntry m_nteDriveEncLeft;  //double
	public static NetworkTableEntry m_nteDriveEncRight;  //double
	public static NetworkTableEntry m_nteIntakeUpLimit;
    public static NetworkTableEntry m_nteIntakeDownLimit;
	public static NetworkTableEntry m_nteResetEncoders;
		

	// pe 's'
	public static NetworkTableEntry m_nteLowerStoragePE;
    public static NetworkTableEntry m_nteUpperStoragePE;
	public static NetworkTableEntry m_nteShooterPE;
	

	// Drive Tab
	//private NetworkTableEntry m_nteArmAngle = null;
	public static NetworkTableEntry m_nteDriveSpeed;  //double
	public static NetworkTableEntry m_nteDriveDistance;  //double
	public static NetworkTableEntry m_nteEndSmartIntake;  //boolean

    //Auto Phases
	public static NetworkTableEntry m_isAutoShootOn;  //boolean
	public static NetworkTableEntry m_isAutoTaxiOn;  //boolean
	public static NetworkTableEntry m_isAutoCollectOn;  //boolean

	/*public static NetworkTableEntry m_isAutoShootOnBox = null;
	public static NetworkTableEntry m_isAutoTaxiOnBox = null;
	public static NetworkTableEntry m_isAutoCollectOnBox = null;*/

	//Speeds Tab
	public static NetworkTableEntry m_nteMaxDriveSpeed;  //double
	public static NetworkTableEntry m_nteMaxArmSpeed;  //double
	public static NetworkTableEntry m_nteMaxIntakePivotSpeed;  //double
	public static NetworkTableEntry m_nteMaxSmartIntakePivotDownSpeed;  //double
	public static NetworkTableEntry m_nteMaxSmartIntakePivotUpSpeed;  //double
	public static NetworkTableEntry m_nteMaxIntakeRollerSpeed;  //double
	public static NetworkTableEntry m_nteMaxShootSpeed;  //double
	public static NetworkTableEntry m_nteMaxIndexerSpeed;  //double
	public static NetworkTableEntry m_nteMaxAutoIndexerSpeed;  //double
	public static NetworkTableEntry m_nteMaxPivotAssistSpeed;  //double
	public static NetworkTableEntry m_nteMaxCargoCollectDriveSpeed;  //double

	//Ball Pos
	public static NetworkTableEntry m_nteTarmac;  //boolean

	public static NetworkTableEntry m_nteLLCargo;  //boolean
	public static NetworkTableEntry m_nteLRCargo;  //boolean
	public static NetworkTableEntry m_nteMLCargo;  //boolean
	public static NetworkTableEntry m_nteMRCargo;  //boolean
	public static NetworkTableEntry m_nteRLCargo;  //boolean
	public static NetworkTableEntry m_nteRRCargo;  //boolean

	//Returns the selected Tarmac
	public static char getTarmac() {
		return m_nteTarmac.getBoolean(false) ? 'R' : 'L';
	}

	//Returns the selected cargo to target
	public static ArrayList<String> getCargos() {
		ArrayList<String> cargo = new ArrayList<>();
		if (m_nteLLCargo.getBoolean(false)) {
			cargo.add("LL");
		}
		if (m_nteLRCargo.getBoolean(false)) {
			cargo.add("LR");
		}
		if (m_nteMLCargo.getBoolean(false)) {
			cargo.add("ML");
		}
		if (m_nteMRCargo.getBoolean(false)) {
			cargo.add("MR");
		}
		if (m_nteRLCargo.getBoolean(false)) {
			cargo.add("RL");
		}
		if (m_nteRRCargo.getBoolean(false)) {
			cargo.add("RR");
		}
		return cargo;
	}
	

	public static boolean endSmartIntake(){
		return m_nteEndSmartIntake.getBoolean(false);
	}
	//Returns whether the pivot assist is enabled
	public static boolean isPivotAssistEnabled() {
		return true;
		//return m_nteIsPivotAssistEnabled.getBoolean(false);
	}

	//Returns whether the smart intake is enabled
	public static boolean isSmartIntakeEnabled() {
		return m_nteIsSmartIntakeEnabled.getBoolean(false);
		//return m_nteIsSmartIntakeEnabled.getBoolean(false);
	}
}
