// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTableEntry;

/** This class stores the NetworkTableEntries */
public class NetworkEntries {
	//Auto Tab
	public static NetworkTableEntry m_nteShootPreloadSelected;  //boolean
	public static NetworkTableEntry m_nteHasPreload; //boolean
	public static NetworkTableEntry m_nteTaxiSelected;  //boolean
	public static NetworkTableEntry m_nteCustomTrajectorySelected;  //boolean
	public static NetworkTableEntry m_nteCollectCargoSelected;  //boolean
	public static NetworkTableEntry m_nteShootCollectedCargoSelected; //boolean

	public static NetworkTableEntry m_nteRightTarmac;  //boolean
	public static NetworkTableEntry m_nteLLCargo;  //boolean
	public static NetworkTableEntry m_nteLRCargo;  //boolean
	public static NetworkTableEntry m_nteMLCargo;  //boolean
	public static NetworkTableEntry m_nteMRCargo;  //boolean
	public static NetworkTableEntry m_nteRLCargo;  //boolean
	public static NetworkTableEntry m_nteRRCargo;  //boolean
	public static NetworkTableEntry m_nteGenerateCargoCollection;  //boolean
	public static NetworkTableEntry m_nteCargoCollectionHasGenerated;  //boolean

	public static NetworkTableEntry m_nteMaxVelocity;  //double (meters per second)
	public static NetworkTableEntry m_nteMaxAcceleration;  //double (meters per second squared)
	public static NetworkTableEntry m_nteX;  //double
	public static NetworkTableEntry m_nteY;  //double
	public static NetworkTableEntry m_nteEndAngle;  //double
	public static NetworkTableEntry m_nteGenerateCustomTrajectory;  //boolean
	public static NetworkTableEntry m_nteCustomTrajectoryHasGenerated;  //boolean

	//Drive Tab
	public static NetworkTableEntry m_nteIsSmartIntakeEnabled;  //boolean
	public static NetworkTableEntry m_nteIsSmartIndexerEnabled;  //boolean
	public static NetworkTableEntry m_nteIsPivotAssistEnabled;  //boolean

	public static NetworkTableEntry m_nteEndSmartIntake;  //boolean

	public static NetworkTableEntry m_nteDriveDistance;  //double (inches)

	//Speeds Tab
	public static NetworkTableEntry m_nteMaxDriveSpeed;  //double
	public static NetworkTableEntry m_nteMaxPivotSpeed;  //double
	public static NetworkTableEntry m_nteMaxRollerSpeed;  //double
	public static NetworkTableEntry m_nteMaxIndexerSpeed;  //double
	public static NetworkTableEntry m_nteMaxShootSpeed;  //double
	public static NetworkTableEntry m_nteMaxClimbSpeed;  //double
	public static NetworkTableEntry m_nteMaxExtenderWinchSpeed;  //double
	public static NetworkTableEntry m_nteMaxHookWinchSpeed;  //double

	//Test Tab
	public static NetworkTableEntry m_nteLowerStoragePE;  //boolean
    public static NetworkTableEntry m_nteUpperStoragePE;  //boolean
	public static NetworkTableEntry m_nteIntakePE;  //boolean
	public static NetworkTableEntry m_nteShooterPE;  //boolean

	public static NetworkTableEntry m_nteIntakeUpLimit;  //boolean
    public static NetworkTableEntry m_nteIntakeDownLimit;  //boolean

	public static NetworkTableEntry m_nteDriveEncLeft;  //double (inches)
	public static NetworkTableEntry m_nteDriveEncRight;  //double (inches)

	public static NetworkTableEntry m_nteResetEncoders;  //boolean

	//Returns the selected Tarmac
	public static char getTarmac() {
		return m_nteRightTarmac.getBoolean(false) ? 'R' : 'L';
	}

	//Returns the selected cargo to target
	public static ArrayList<String> getCargoToTarget() {
		ArrayList<String> cargo = new ArrayList<>();
		switch (getTarmac()) {  //Get the starting Tarmac
			case ('L'):  //Starting in the left Tarmac
				//Add the cargo to the ArrayList in the order they should be targeted in
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
				break;
			case ('R'):  //Starting in the right Tarmac
				//Add the cargo to the ArrayList in the order they should be targeted in
				if (m_nteRRCargo.getBoolean(false)) {
					cargo.add("RR");
				}
				if (m_nteRLCargo.getBoolean(false)) {
					cargo.add("RL");
				}
				if (m_nteMRCargo.getBoolean(false)) {
					cargo.add("MR");
				}
				if (m_nteMLCargo.getBoolean(false)) {
					cargo.add("ML");
				}
				if (m_nteLRCargo.getBoolean(false)) {
					cargo.add("LR");
				}
				if (m_nteLLCargo.getBoolean(false)) {
					cargo.add("LL");
				}
				break;
		}
		//Returns the cargo to be targeted
		return cargo;
	}

	//Returns whether the pivot assist is enabled
	public static boolean isPivotAssistEnabled() {
		return m_nteIsPivotAssistEnabled.getBoolean(false);
	}

	//Returns whether the smart intake is enabled
	public static boolean isSmartIntakeEnabled() {
		return m_nteIsSmartIntakeEnabled.getBoolean(false);
	}

	//Returns whether the smart indexer is enabled
	public static boolean isSmartIndexerEnabled() {
		return m_nteIsSmartIndexerEnabled.getBoolean(false);
	}
}
