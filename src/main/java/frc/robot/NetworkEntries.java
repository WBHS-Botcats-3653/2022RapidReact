// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.HashMap;

import edu.wpi.first.networktables.NetworkTableEntry;

/** This class will store the NetworkTableEntry(ies)
 * 
 * 
*/
public class NetworkEntries {
    // Config Tab

	// Test Tab
	public static NetworkTableEntry m_nteDriveEncLeft; //double
	public static NetworkTableEntry m_nteDriveEncRight; //double
	public static NetworkTableEntry m_nteIntakeUpLimit;
    public static NetworkTableEntry m_nteIntakeDownLimit;

	// Drive Tab
	//private NetworkTableEntry m_nteArmAngle = null;
	public static NetworkTableEntry m_nteDriveSpeed; //double

    //Auto Phases
	public static NetworkTableEntry m_isAutoShootOn; //boolean
	public static NetworkTableEntry m_isAutoTaxiOn; //boolean
	public static NetworkTableEntry m_isAutoCollectOn; //boolean

	/*public static NetworkTableEntry m_isAutoShootOnBox = null;
	public static NetworkTableEntry m_isAutoTaxiOnBox = null;
	public static NetworkTableEntry m_isAutoCollectOnBox = null;*/

	//Speeds Tab
	public static NetworkTableEntry m_nteMaxDriveSpeed; //double
	public static NetworkTableEntry m_nteMaxArmSpeed; //double
	public static NetworkTableEntry m_nteMaxIntakePivotSpeed; //double
	public static NetworkTableEntry m_nteMaxSmartIntakePivotDownSpeed; //double
	public static NetworkTableEntry m_nteMaxSmartIntakePivotUpSpeed; //double
	public static NetworkTableEntry m_nteMaxIntakeRollerSpeed; //double
	public static NetworkTableEntry m_nteMaxShootSpeed; //double
	public static NetworkTableEntry m_nteMaxIndexerSpeed; //double
	public static NetworkTableEntry m_nteMaxPivotAssistSpeed; //double

	//TODO: Implement
	public static NetworkTableEntry isPivotAssistEnabled;
	public static NetworkTableEntry isSmartIntakeEnabled;


	//Ball Pos
	//public static NetworkTableEntry m_nteMaxDriveSpeed; //double
	public static NetworkTableEntry m_nteTarmac; //boolean
	//public static NetworkTableEntry m_nteLTarmac; //boolean

	public static NetworkTableEntry m_nteLLCargo; //boolean
	public static NetworkTableEntry m_nteLRCargo; //boolean
	public static NetworkTableEntry m_nteMLCargo; //boolean
	public static NetworkTableEntry m_nteMRCargo; //boolean
	public static NetworkTableEntry m_nteRLCargo; //boolean
	public static NetworkTableEntry m_nteRRCargo; //boolean

	public static char getTarmac(){
		return m_nteTarmac.getBoolean(false) ? 'R' : 'L';
	}

	public static HashMap<String, Boolean> getCargos(){

		return new HashMap<>() {{
			put("LL", m_nteLLCargo.getBoolean(false));
			put("LR", m_nteLRCargo.getBoolean(false));
			put("ML", m_nteMLCargo.getBoolean(false));
			put("MR", m_nteMRCargo.getBoolean(false));
			put("RL", m_nteRLCargo.getBoolean(false));  
			put("RR", m_nteRRCargo.getBoolean(false));  
		}};
	}
}
