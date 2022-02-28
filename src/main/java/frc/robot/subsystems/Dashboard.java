// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import edu.wpi.first.wpilibj.shuffleboard.*;
import frc.robot.NetworkEntries;
import frc.robot.inputs.OI;
import frc.robot.inputs.SI;

public class Dashboard {
	private static Dashboard m_singleton = null;
	private static OI m_oi = OI.getInstance();
	private static SI m_si =  SI.getInstance();
	private static Direction m_direction = Direction.getInstance();
	//private UsbCamera cam0 = null;

	public static ShuffleboardTab tabConfig;
	public static ShuffleboardTab tabDrive;
	public static ShuffleboardTab tabTest;
	public static ShuffleboardTab tabSpeeds;
	public static ShuffleboardTab tabAutoConfig;


	private Dashboard() {
		tabAutoConfig = Shuffleboard.getTab("AutoConfig");
		tabDrive = Shuffleboard.getTab("Drive");
		tabConfig = Shuffleboard.getTab("Config");
		tabSpeeds = Shuffleboard.getTab("Speeds");
		tabTest = Shuffleboard.getTab("Test");

		

		// Config Tab
		NetworkEntries.m_nteIsPivotAssistEnabled = tabConfig.addPersistent("is Pivot Assist Enabled", true).withWidget(BuiltInWidgets.kToggleButton)
		.withSize(1, 1).withPosition(0, 1).getEntry();
		
		// Drive Tab
		/*
		cam0 = CameraServer.startAutomaticCapture(0);
		cam0.setResolution(142, 90);
		cam0.setFPS(20);
		tabDrive.add("Field View", cam0).withSize(3, 2).withPosition(6, 0);
		tabDrive.add("Gyro", Direction.getInstance().getGyro()).withSize(2, 2).withPosition(3, 0);
		/*
		m_nteArmAngle = tabDrive.add("Arm", 0.0).withWidget(BuiltInWidgets.kDial)
				.withProperties(Map.of("min", 0, "max", 180)).withSize(1, 1).withPosition(5, 0).getEntry();
		*/
		NetworkEntries.m_nteIsSmartIntakeEnabled = tabDrive.addPersistent("is Smart Intake Enabled", true).withWidget(BuiltInWidgets.kToggleButton)
			.withSize(1, 1).withPosition(5, 1).getEntry();
		NetworkEntries.m_nteDriveSpeed = tabDrive.addPersistent("Speed", m_oi.getMaxDriveSpeed()).withWidget(BuiltInWidgets.kNumberSlider)
				.withProperties(Map.of("min", 0, "max", 1.0)).withSize(1, 1).withPosition(5, 0).getEntry();

		//Auto Shoot
		NetworkEntries.m_isAutoShootOn = tabDrive.addPersistent("Auto Shoot", true).withWidget(BuiltInWidgets.kToggleButton)
		.withSize(1, 1).withPosition(0, 1).getEntry();
		//this one is the boolean box
		//--m_isAutoShootOnBox = tabDrive.addPersistent("is Auto Shoot", AutoCommand.isAutoShootOn).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(0, 0).getEntry();

		//Auto Taxi
		NetworkEntries.m_isAutoTaxiOn = tabDrive.addPersistent("Auto Taxi", true).withWidget(BuiltInWidgets.kToggleButton)
		.withSize(1, 1).withPosition(1, 1).getEntry();
		//this one is the boolean box
		//--m_isAutoTaxiOnBox = tabDrive.addPersistent("is Auto Taxi", AutoCommand.isAutoTaxiOn).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(1, 0).getEntry();

		//Auto collect
		NetworkEntries.m_isAutoCollectOn = tabDrive.addPersistent("Auto Collect", true).withWidget(BuiltInWidgets.kToggleButton)
		.withSize(1, 1).withPosition(2, 1).getEntry();
		//this one is the boolean box
		//--m_isAutoCollectOnBox = tabDrive.addPersistent("is Auto Collect", AutoCommand.isAutoCollectOn).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(2, 0).getEntry();

		// Test Tab
		NetworkEntries.m_nteIntakeUpLimit = tabTest.add("Intake up limit", false).withSize(1, 1).withPosition(0, 0).getEntry();
		NetworkEntries.m_nteIntakeDownLimit = tabTest.add("Intake Down limit", false).withSize(1, 1).withPosition(0, 0).getEntry();
		//m_nteArmUpLimit = tabTest.add("Arm Up", false).withSize(1, 1).withPosition(1, 0).getEntry();
		//this is for testing the encoders if they are working
		//--NetworkEntries.testingEncoderLeft = tabTest.add("Left encoder testing it", 0).withSize(1, 1).withPosition(1, 0).getEntry();
		//m_nteArmEncoderRaw = tabTest.add("Arm Encoder", 1024).withSize(1, 1).withPosition(2, 0).getEntry();
		NetworkEntries.m_nteDriveEncLeft = tabTest.add("Drive Left", 0).withWidget(BuiltInWidgets.kTextView).withSize(1, 1).withPosition(0, 1).getEntry();
		NetworkEntries.m_nteDriveEncRight = tabTest.add("Drive Right", 0).withWidget(BuiltInWidgets.kTextView).withSize(1, 1).withPosition(1, 1).getEntry();
		

		//Speeds Tab
		/*
		ShuffleboardLayout Speeds = Shuffleboard.getTab("Speeds")
  			.getLayout("Speeds", BuiltInLayouts.kList)
  			.withSize(2, 2)
  			.withProperties(Map.of("Label position", "HIDDEN")); // hide labels for commands
		*/
		
		NetworkEntries.m_nteMaxDriveSpeed = tabSpeeds.addPersistent("Max Drive speed", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(0, 0).getEntry();//double
		NetworkEntries.m_nteMaxArmSpeed = tabSpeeds.addPersistent("Max Arm speed", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(2, 0).getEntry();//double
		NetworkEntries.m_nteMaxIntakePivotSpeed = tabSpeeds.addPersistent("Max Intake Pivot speed", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(4, 0).getEntry();//double
		NetworkEntries.m_nteMaxShootSpeed = tabSpeeds.addPersistent("Max Shoot speed", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(6, 0).getEntry();//double
		NetworkEntries.m_nteMaxIndexerSpeed = tabSpeeds.addPersistent("Max Indexer speed", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(0, 1).getEntry();//double
		NetworkEntries.m_nteMaxPivotAssistSpeed = tabSpeeds.addPersistent("Max Pivot Assist speed", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(6, 1).getEntry(); //double
		NetworkEntries.m_nteMaxSmartIntakePivotDownSpeed = tabSpeeds.addPersistent("Max Smart Down Intake Pivot speed", 0).withWidget(BuiltInWidgets.kTextView).withSize(1, 1).withPosition(2, 1).getEntry();//double
		NetworkEntries.m_nteMaxSmartIntakePivotUpSpeed = tabSpeeds.addPersistent("Max Smart Up Intake Pivot speed", 0).withWidget(BuiltInWidgets.kTextView).withSize(1, 1).withPosition(5, 1).getEntry();//double
		

		//AutoConfig Tab
		NetworkEntries.m_nteTarmac = tabAutoConfig.add("Is Right Tarmac?", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(0, 0).getEntry(); //boolean
		//NetworkEntries.m_nteLTarmac = tabAutoConfig.add("Left Tarmac", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(0, 0).getEntry(); //boolean
		/*
		ShuffleboardLayout Cargos = Shuffleboard.getTab("AutoConfig")
  			.getLayout("Cargo options", BuiltInLayouts.kList)
  			.withSize(2, 2).withPosition(1, 0)
  			.withProperties(Map.of("Label position", "HIDDEN")); // hide labels for commands
*/
		NetworkEntries.m_nteLLCargo = tabAutoConfig.add("LL Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(2, 0).getEntry(); //boolean
		NetworkEntries.m_nteLRCargo = tabAutoConfig.add("LR Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(2, 1).getEntry(); //boolean
		NetworkEntries.m_nteMLCargo = tabAutoConfig.add("ML Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(3, 0).getEntry(); //boolean
		NetworkEntries.m_nteMRCargo = tabAutoConfig.add("MR Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(4, 0).getEntry(); //boolean
		NetworkEntries.m_nteRLCargo = tabAutoConfig.add("RL Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(5, 0).getEntry(); //boolean
		NetworkEntries.m_nteRRCargo = tabAutoConfig.add("RR Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(5, 1).getEntry(); //boolean
	}

	
	public void refresh() {
		m_oi.setMaxDriveSpeed(NetworkEntries.m_nteDriveSpeed.getDouble(1.0));

		//Intake.setArmEncoderFloor((int) m_nteArmDownEnc.getDouble(1024));
	}
	/**This is in charge of making sure the user won't be able to select the cargo that are too far.
	 * 
	 */
	public static void selectorLogic(){
		if(NetworkEntries.m_nteTarmac.getBoolean(false)){
			NetworkEntries.m_nteLLCargo.setBoolean(false);
			NetworkEntries.m_nteLRCargo.setBoolean(false);
		} else if(!NetworkEntries.m_nteTarmac.getBoolean(false)){
			NetworkEntries.m_nteRLCargo.setBoolean(false);
			NetworkEntries.m_nteRRCargo.setBoolean(false);
		}
	}
	public void periodic() {
		OI m_oi = OI.getInstance();
		//Intake arm = Intake.getInstance();
		//DriveTrain drive = DriveTrain.getInstance();
		//m_nteArmAngle.setDouble(arm.getAngle());

		//updates the speeds in the dashboard:
		m_oi.setMaxDriveSpeed(NetworkEntries.m_nteDriveSpeed.getDouble(1.0));

		NetworkEntries.m_nteDriveEncLeft.setDouble(m_direction.getLeftDistance());
		NetworkEntries.m_nteDriveEncRight.setDouble(m_direction.getRightDistance());

		NetworkEntries.m_nteIntakeUpLimit.setBoolean(m_si.getPivotUpLimitTriggered());
		NetworkEntries.m_nteIntakeDownLimit.setBoolean(m_si.getPivotDownLimitTriggered());
		//updates the following speeds
		m_oi.setMaxDriveSpeed(NetworkEntries.m_nteMaxDriveSpeed.getDouble(1.0));
		m_oi.setMaxIndexerSpeed(NetworkEntries.m_nteMaxIndexerSpeed.getDouble(1.0));
		m_oi.setMaxIntakePivotSpeed(NetworkEntries.m_nteMaxIntakePivotSpeed.getDouble(1.0));
		m_oi.setMaxArmSpeed(NetworkEntries.m_nteMaxArmSpeed.getDouble(1.0));

		//updates the values on the dashboard so they wont change but they will show up
		NetworkEntries.m_nteMaxSmartIntakePivotDownSpeed.setDouble(1.0);
		NetworkEntries.m_nteMaxSmartIntakePivotUpSpeed.setDouble(1.0);

		//m_oi.setMaxIntakeRollerSpeed(NetworkEntries.m_nteMaxIntakeRollerSpeed.getDouble(1.0));
		m_oi.setMaxShootSpeed(NetworkEntries.m_nteMaxShootSpeed.getDouble(1.0));
		
		NetworkEntries.m_nteMaxPivotAssistSpeed.getDouble(1.0);
		//FIX
		//m_nteArmEncoderRaw.setNumber(arm.getRawEncoder());
		/*
		m_nteArmDnLimit.setBoolean(arm.getLowerLimitSwitch());
		m_nteArmUpLimit.setBoolean(arm.getUpperLimitSwitch());
		m_nteDriveEncLeft.setNumber(drive.getLeftEncoder());
		m_nteDriveEncRight.setNumber(drive.getRightEncoder());
		*/
	}

	//Returns an instance of Dashboard, creating an instance only when one does not already exist (singleton)
	public static Dashboard getInstance() {
		if (m_singleton == null) {
			m_singleton = new Dashboard();
		}
		return m_singleton;
	}
}
