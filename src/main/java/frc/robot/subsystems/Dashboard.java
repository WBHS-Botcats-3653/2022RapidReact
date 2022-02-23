// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;
import frc.robot.NetworkEntries;
import frc.robot.commands.AutoCommand;
import frc.robot.inputs.OI;

public class Dashboard {
	private static Dashboard m_singleton = null;
	private static OI ctrl = OI.getInstance();
	private static NetworkEntries network = NetworkEntries.getInstance();
	//private UsbCamera cam0 = null;

	public static ShuffleboardTab tabConfig;
	public static ShuffleboardTab tabDrive;
	public static ShuffleboardTab tabTest;



	private Dashboard() {
		tabConfig = Shuffleboard.getTab("Config");
		tabDrive = Shuffleboard.getTab("Drive");
		tabTest = Shuffleboard.getTab("Test");

		// Config Tab
		
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
		network.m_nteDriveSpeed = tabDrive.addPersistent("Speed", ctrl.getMaxDriveSpeed()).withWidget(BuiltInWidgets.kNumberSlider)
				.withProperties(Map.of("min", 0, "max", 1.0)).withSize(1, 1).withPosition(5, 0).getEntry();

		//Auto Shoot
		network.m_isAutoShootOn = tabDrive.addPersistent("Auto Shoot", true).withWidget(BuiltInWidgets.kToggleButton)
		.withSize(1, 1).withPosition(0, 1).getEntry();
		//this one is the boolean box
		//--m_isAutoShootOnBox = tabDrive.addPersistent("is Auto Shoot", AutoCommand.isAutoShootOn).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(0, 0).getEntry();

		//Auto Taxi
		network.m_isAutoTaxiOn = tabDrive.addPersistent("Auto Taxi", true).withWidget(BuiltInWidgets.kToggleButton)
		.withSize(1, 1).withPosition(1, 1).getEntry();
		//this one is the boolean box
		//--m_isAutoTaxiOnBox = tabDrive.addPersistent("is Auto Taxi", AutoCommand.isAutoTaxiOn).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(1, 0).getEntry();

		//Auto collect
		network.m_isAutoCollectOn = tabDrive.addPersistent("Auto Collect", true).withWidget(BuiltInWidgets.kToggleButton)
		.withSize(1, 1).withPosition(2, 1).getEntry();
		//this one is the boolean box
		//--m_isAutoCollectOnBox = tabDrive.addPersistent("is Auto Collect", AutoCommand.isAutoCollectOn).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(2, 0).getEntry();

		// Test Tab
		network.m_nteArmDnLimit = tabTest.add("Arm Down", false).withSize(1, 1).withPosition(0, 0).getEntry();
		//m_nteArmUpLimit = tabTest.add("Arm Up", false).withSize(1, 1).withPosition(1, 0).getEntry();
		//this is for testing the encoders if they are working
		//--network.testingEncoderLeft = tabTest.add("Left encoder testing it", 0).withSize(1, 1).withPosition(1, 0).getEntry();
		//m_nteArmEncoderRaw = tabTest.add("Arm Encoder", 1024).withSize(1, 1).withPosition(2, 0).getEntry();
		//m_nteDriveEncLeft = tabTest.add("Drive Left", 0).withSize(1, 1).withPosition(0, 1).getEntry();
		//m_nteDriveEncRight = tabTest.add("Drive Right", 0).withSize(1, 1).withPosition(1, 1).getEntry();
	}

	
	public void refresh() {
		

		ctrl.setMaxDriveSpeed(network.m_nteDriveSpeed.getDouble(1.0));


		//Intake.setArmEncoderFloor((int) m_nteArmDownEnc.getDouble(1024));
	}

	public void periodic() {
		OI ctrl = OI.getInstance();
		//Intake arm = Intake.getInstance();
		//DriveTrain drive = DriveTrain.getInstance();
		OI oi = OI.getInstance();
		//m_nteArmAngle.setDouble(arm.getAngle());
		network.m_nteDriveSpeed.setDouble(Math.abs(oi.getMaxDriveSpeed()));
		ctrl.setMaxDriveSpeed(network.m_nteDriveSpeed.getDouble(1.0));

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
