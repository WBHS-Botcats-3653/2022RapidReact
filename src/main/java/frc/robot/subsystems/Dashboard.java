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

		//AutoConfig Tab
			//Auto Shoot
			NetworkEntries.m_isAutoShootOn = tabAutoConfig.addPersistent("Auto Shoot", true).withWidget(BuiltInWidgets.kToggleButton)
			.withSize(1, 1).withPosition(0, 0).getEntry();
			//Auto Taxi
			NetworkEntries.m_isAutoTaxiOn = tabAutoConfig.addPersistent("Auto Taxi", true).withWidget(BuiltInWidgets.kToggleButton)
			.withSize(1, 1).withPosition(1, 0).getEntry();
			//Auto collect
			NetworkEntries.m_isAutoCollectOn = tabAutoConfig.addPersistent("Auto Collect", true).withWidget(BuiltInWidgets.kToggleButton)
			.withSize(1, 1).withPosition(2, 0).getEntry();
			//AutoConfig Tab
			NetworkEntries.m_nteTarmac = tabAutoConfig.add("Is Right Tarmac?", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(1, 1).getEntry(); //boolean
			
			/*
			ShuffleboardLayout Cargos = Shuffleboard.getTab("AutoConfig")
				.getLayout("Cargo options", BuiltInLayouts.kList)
				.withSize(2, 2).withPosition(1, 0)
				.withProperties(Map.of("Label position", "HIDDEN")); // hide labels for commands
			*/
			NetworkEntries.m_nteLLCargo = tabAutoConfig.add("LL Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(5, 0).getEntry(); //boolean
			NetworkEntries.m_nteLRCargo = tabAutoConfig.add("LR Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(5, 1).getEntry(); //boolean
			NetworkEntries.m_nteMLCargo = tabAutoConfig.add("ML Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(6, 0).getEntry(); //boolean
			NetworkEntries.m_nteMRCargo = tabAutoConfig.add("MR Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(7, 0).getEntry(); //boolean
			NetworkEntries.m_nteRLCargo = tabAutoConfig.add("RL Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(8, 0).getEntry(); //boolean
			NetworkEntries.m_nteRRCargo = tabAutoConfig.add("RR Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(8, 1).getEntry(); //boolean


		// Drive Tab
			/*
			cam0 = CameraServer.startAutomaticCapture(0);
			cam0.setResolution(142, 90);
			cam0.setFPS(20);
			tabDrive.add("Field View", cam0).withSize(3, 2).withPosition(6, 0);
			tabDrive.add("Gyro", Direction.getInstance().getGyro()).withSize(2, 2).withPosition(3, 0);
			*/
			NetworkEntries.m_nteIsSmartIntakeEnabled = tabDrive.addPersistent("is Smart Intake Enabled", true).withWidget(BuiltInWidgets.kToggleButton)
			.withSize(1, 1).withPosition(5, 1).getEntry();
			NetworkEntries.m_nteDriveSpeed = tabDrive.addPersistent("Speed", m_oi.getMaxDriveSpeed()).withWidget(BuiltInWidgets.kNumberSlider)
				.withProperties(Map.of("min", 0, "max", 1.0)).withSize(1, 1).withPosition(5, 0).getEntry();

			NetworkEntries.m_nteDriveDistance = tabDrive.add("Drived Distance", 0).withWidget(BuiltInWidgets.kDial).withSize(1, 1).withPosition(3, 0).getEntry();
			NetworkEntries.m_nteEndSmartIntake = tabDrive.add("End Smart Intake", false).withWidget(BuiltInWidgets.kToggleButton).withSize(2, 1).withPosition(3, 6).getEntry();
			// Config Tab
			NetworkEntries.m_nteIsPivotAssistEnabled = tabConfig.addPersistent("is Pivot Assist Enabled", true).withWidget(BuiltInWidgets.kToggleButton)
			.withSize(1, 1).withPosition(0, 1).getEntry();
		//Speeds Tab			
			NetworkEntries.m_nteMaxDriveSpeed = tabSpeeds.addPersistent("Max Drive speed", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(0, 0).getEntry();//double
			NetworkEntries.m_nteMaxArmSpeed = tabSpeeds.addPersistent("Max Arm speed", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(2, 0).getEntry();//double
			NetworkEntries.m_nteMaxIntakePivotSpeed = tabSpeeds.addPersistent("Max Intake Pivot speed", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(4, 0).getEntry();//double
			NetworkEntries.m_nteMaxShootSpeed = tabSpeeds.addPersistent("Max Shoot speed", 0.85).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(6, 0).getEntry();//double
			NetworkEntries.m_nteMaxIndexerSpeed = tabSpeeds.addPersistent("Max Indexer speed", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(0, 1).getEntry();//double
			NetworkEntries.m_nteMaxPivotAssistSpeed = tabSpeeds.addPersistent("Max Pivot Assist speed", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(6, 1).getEntry(); //double
			NetworkEntries.m_nteMaxSmartIntakePivotDownSpeed = tabSpeeds.addPersistent("Max Smart Down Intake Pivot speed", 0).withWidget(BuiltInWidgets.kTextView).withSize(1, 1).withPosition(2, 1).getEntry();//double
			NetworkEntries.m_nteMaxSmartIntakePivotUpSpeed = tabSpeeds.addPersistent("Max Smart Up Intake Pivot speed", 0).withWidget(BuiltInWidgets.kTextView).withSize(1, 1).withPosition(5, 1).getEntry();//double
			NetworkEntries.m_nteMaxCargoCollectDriveSpeed = tabSpeeds.addPersistent("Max Cargo Collect Drive speed", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(3, 1).getEntry(); //double
			NetworkEntries.m_nteMaxIntakeRollerSpeed = tabSpeeds.addPersistent("Max Intake Roller Speed", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(3, 2).getEntry(); //double

		// Test Tab
			NetworkEntries.m_nteIntakeUpLimit = tabTest.add("Intake up limit", false).withSize(1, 1).withPosition(3, 0).getEntry();
			NetworkEntries.m_nteIntakeDownLimit = tabTest.add("Intake Down limit", false).withSize(1, 1).withPosition(4, 0).getEntry();
			NetworkEntries.m_nteLowerStoragePE = tabTest.add("lower pe", false).withSize(1, 1).withPosition(1, 0).getEntry();
			NetworkEntries.m_nteUpperStoragePE = tabTest.add("upper pe", false).withSize(1, 1).withPosition(2, 0).getEntry();
			NetworkEntries.m_nteShooterPE = tabTest.add("shooter", false).withSize(1, 1).withPosition(2, 1).getEntry();
			

			NetworkEntries.m_nteDriveEncLeft = tabTest.add("Drive Left", 0).withWidget(BuiltInWidgets.kTextView).withSize(1, 1).withPosition(0, 1).getEntry();
			NetworkEntries.m_nteDriveEncRight = tabTest.add("Drive Right", 0).withWidget(BuiltInWidgets.kTextView).withSize(1, 1).withPosition(1, 1).getEntry();	
	}

	
	public void refresh() {
		m_oi.setMaxDriveSpeed(NetworkEntries.m_nteDriveSpeed.getDouble(1.0));

		//Intake.setArmEncoderFloor((int) m_nteArmDownEnc.getDouble(1024));
	}
	/**This is in charge of making sure the user won't be able to select the cargo that are too far.*/
	public static void selectorLogic(){
		if(NetworkEntries.m_nteTarmac.getBoolean(false)){
			NetworkEntries.m_nteLLCargo.setBoolean(false);
			NetworkEntries.m_nteLRCargo.setBoolean(false);
		} else if(!NetworkEntries.m_nteTarmac.getBoolean(false)){
			NetworkEntries.m_nteRLCargo.setBoolean(false);
			NetworkEntries.m_nteRRCargo.setBoolean(false);
		}
	}
	/**@Important This Method method should be executed in RobotPeriodic().
	 * It is an alternative to the refresh() method.
	 * 
	 * @description This Method will update the values in the dashboard and in the networkTable
	 * in order to change the aprepiate values accordingly
	 */
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
		m_oi.setMaxIntakeRollerSpeed(NetworkEntries.m_nteMaxIntakeRollerSpeed.getDouble(1.0));

		//updates the values on the dashboard so they wont change but they will show up
		m_oi.setMaxSmartIntakePivotUpSpeed(NetworkEntries.m_nteMaxSmartIntakePivotUpSpeed.getDouble(0.35));
		m_oi.setMaxSmartIntakePivotDownSpeed(NetworkEntries.m_nteMaxSmartIntakePivotDownSpeed.getDouble(0.15));
		//m_oi.setMaxIntakeRollerSpeed(NetworkEntries.m_nteMaxIntakeRollerSpeed.getDouble(1.0));
		m_oi.setMaxShootSpeed(NetworkEntries.m_nteMaxShootSpeed.getDouble(0.85)/*0.85*/);
		m_oi.setMaxCargoCollectDriveSpeed(NetworkEntries.m_nteMaxCargoCollectDriveSpeed.getDouble(1.0));
		NetworkEntries.m_nteMaxPivotAssistSpeed.getDouble(1.0);

		//Updates the PhotoElectric sensors in the dashboard
		NetworkEntries.m_nteLowerStoragePE.setBoolean(m_si.getLowerStorageTriggered());
		NetworkEntries.m_nteUpperStoragePE.setBoolean(m_si.getUpperStorageTriggered());
		NetworkEntries.m_nteShooterPE.setBoolean(m_si.getShooterTriggered());
		//FIX
		//m_nteArmEncoderRaw.setNumber(arm.getRawEncoder());
		/*
		m_nteArmDnLimit.setBoolean(arm.getLowerLimitSwitch());
		m_nteArmUpLimit.setBoolean(arm.getUpperLimitSwitch());
		m_nteDriveEncLeft.setNumber(drive.getLeftEncoder());
		m_nteDriveEncRight.setNumber(drive.getRightEncoder());
		*/
	}
	/**This Method Starts the robot with updating some values */
	public static void robotInit() {
		//Updates the PhotoElectric sensors in the dashboard
		NetworkEntries.m_nteLowerStoragePE.setBoolean(m_si.getLowerStorageTriggered());
		NetworkEntries.m_nteUpperStoragePE.setBoolean(m_si.getUpperStorageTriggered());
		NetworkEntries.m_nteShooterPE.setBoolean(m_si.getShooterTriggered());
	}
	/**Returns an instance of Dashboard, creating an instance only when one does not already exist (singleton)*/
	public static Dashboard getInstance() {
		if (m_singleton == null) {
			m_singleton = new Dashboard();
		}
		return m_singleton;
	}
}
