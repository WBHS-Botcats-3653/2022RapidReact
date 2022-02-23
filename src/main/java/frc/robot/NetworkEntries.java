// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;

/** This class will store the NetworkTableEntry(ies)
 * 
 * 
*/
public class NetworkEntries {

    private static NetworkEntries m_singleton = null;
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
/*
	public static NetworkTableEntry m_isAutoShootOnBox = null;
	public static NetworkTableEntry m_isAutoTaxiOnBox = null;
	public static NetworkTableEntry m_isAutoCollectOnBox = null;
*/

	//TODO: Implement
    //TODO: I know you don't like 'em, but I DO...
	public static NetworkTableEntry isPivotAssistEnabled;
	public static NetworkTableEntry isSmartIntakeEnabled;

    private NetworkEntries(){}

    public static NetworkEntries getInstance(){
        if(m_singleton == null) m_singleton = new NetworkEntries();
        return m_singleton;
    }



}
