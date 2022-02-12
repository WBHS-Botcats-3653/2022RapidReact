// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.OI;

public class Dashboard {
	private static Dashboard m_singleton = null;

	private UsbCamera cam0 = null;

	// Config Tab
	private NetworkTableEntry m_nteMaxSpd = null;
	private NetworkTableEntry m_nteMaxArmSpd = null;
	private NetworkTableEntry m_nteMaxIntake = null;
	private NetworkTableEntry m_nteArmDownEnc = null;

	// Test Tab
	private NetworkTableEntry m_nteArmEncoderRaw = null;
	private NetworkTableEntry m_nteDriveEncLeft = null;
	private NetworkTableEntry m_nteDriveEncRight = null;
	private NetworkTableEntry m_nteArmUpLimit = null;
	private NetworkTableEntry m_nteArmDnLimit = null;

	// Drive Tab
	private NetworkTableEntry m_nteArmAngle = null;
	private NetworkTableEntry m_nteDriveSpeed = null;

	private Dashboard() {
		ShuffleboardTab tabConfig = Shuffleboard.getTab("Config");
		ShuffleboardTab tabDrive = Shuffleboard.getTab("Drive");
		ShuffleboardTab tabTest = Shuffleboard.getTab("Test");

		// Config Tab
		//m_nteMaxSpd = tabConfig.addPersistent("Max Speed", 1.0).withSize(1, 1).withPosition(0, 0).getEntry();
		m_nteMaxIntake = tabConfig.addPersistent("Max Intake", 1.0).withSize(1, 1).withPosition(1, 0).getEntry();
		m_nteMaxArmSpd = tabConfig.addPersistent("Max Arm Spd", 1.0).withSize(1, 1).withPosition(2, 0).getEntry();
		m_nteArmDownEnc = tabConfig.addPersistent("Arm Down", 1024.0).withSize(1, 1).withPosition(3, 0).getEntry();

		// Drive Tab
		cam0 = CameraServer.startAutomaticCapture(0);
		cam0.setResolution(142, 90);
		cam0.setFPS(20);
		tabDrive.add("Field View", cam0).withSize(3, 2).withPosition(0, 0);

		tabDrive.add("Gyro", Direction.getInstance().getGyro()).withSize(2, 2).withPosition(3, 0);
		/*
		m_nteArmAngle = tabDrive.add("Arm", 0.0).withWidget(BuiltInWidgets.kDial)
				.withProperties(Map.of("min", 0, "max", 180)).withSize(1, 1).withPosition(5, 0).getEntry();
		*/
		m_nteDriveSpeed = tabDrive.addPersistent("Speed", 1.0).withWidget(BuiltInWidgets.kNumberSlider)
				.withProperties(Map.of("min", 0, "max", 1.0)).withSize(1, 1).withPosition(6, 0).getEntry();

		// Test Tab
		m_nteArmDnLimit = tabTest.add("Arm Down", false).withSize(1, 1).withPosition(0, 0).getEntry();
		m_nteArmUpLimit = tabTest.add("Arm Up", false).withSize(1, 1).withPosition(1, 0).getEntry();
		//m_nteArmEncoderRaw = tabTest.add("Arm Encoder", 1024).withSize(1, 1).withPosition(2, 0).getEntry();
		//m_nteDriveEncLeft = tabTest.add("Drive Left", 0).withSize(1, 1).withPosition(0, 1).getEntry();
		//m_nteDriveEncRight = tabTest.add("Drive Right", 0).withSize(1, 1).withPosition(1, 1).getEntry();
	}

	public void refresh() {
		OI ctrl = OI.getInstance();

		ctrl.setMaxDriveSpeed(m_nteDriveSpeed.getDouble(1.0));
		ctrl.setMaxArmSpeed(m_nteMaxArmSpd.getDouble(1.0));
		ctrl.setMaxIntakePivotSpeed(m_nteMaxIntake.getDouble(1.0));
		ctrl.setMaxIntakeRollerSpeed(m_nteMaxIntake.getDouble(1.0));

		//Intake.setArmEncoderFloor((int) m_nteArmDownEnc.getDouble(1024));
	}

	public void telopPeriodic() {
		OI ctrl = OI.getInstance();
		Intake arm = Intake.getInstance();
		DriveTrain drive = DriveTrain.getInstance();
		OI oi = OI.getInstance();
		//m_nteArmAngle.setDouble(arm.getAngle());
		m_nteDriveSpeed.setDouble(Math.abs(oi.getMaxDriveSpeed()));
		ctrl.setMaxDriveSpeed(m_nteDriveSpeed.getDouble(1.0));
	}

	public void testPeriodic() {
		Intake arm = Intake.getInstance();
		DriveTrain drive = DriveTrain.getInstance();

		//m_nteArmEncoderRaw.setNumber(arm.getRawEncoder());
		/*
		m_nteArmDnLimit.setBoolean(arm.getLowerLimitSwitch());
		m_nteArmUpLimit.setBoolean(arm.getUpperLimitSwitch());
		m_nteDriveEncLeft.setNumber(drive.getLeftEncoder());
		m_nteDriveEncRight.setNumber(drive.getRightEncoder());
		*/
	}

	//Returns an instance of the dashboard, creating an instance only when one does not already exist (singleton)
	public static Dashboard getInstance() {
		if (m_singleton == null) {
			m_singleton = new Dashboard();
		}
		return m_singleton;
	}
}
