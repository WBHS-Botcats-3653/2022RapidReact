// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.DefaultSpeedsConstants.*;

import java.util.Map;

import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.NetworkEntries;
import frc.robot.commands.IntakeCommand;
import frc.robot.inputs.OI;
import frc.robot.inputs.SI;

public class Dashboard extends SubsystemBase {
	private static Dashboard m_singleton;
	private static Direction m_direction = Direction.getInstance();
	private static Drivetrain m_drivetrain = Drivetrain.getInstance();
	private static OI m_oi = OI.getInstance();
	private static SI m_si =  SI.getInstance();

	//private UsbCamera cam0 = null;

	public static ShuffleboardTab tabConfig;
	public static ShuffleboardTab tabDrive;
	public static ShuffleboardTab tabTest;
	public static ShuffleboardTab tabSpeeds;
	public static ShuffleboardTab tabAutoConfig;

	public boolean speedsDisabled = true;

	private static boolean previousTaxi, previousCollect, previousSmartIntake;

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
			NetworkEntries.m_nteRightTarmac = tabAutoConfig.add("Is Right Tarmac?", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(3, 0).getEntry(); //boolean
			
			NetworkEntries.m_nteLLCargo = tabAutoConfig.add("LL Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(4, 0).getEntry(); //boolean
			NetworkEntries.m_nteLRCargo = tabAutoConfig.add("LR Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(4, 1).getEntry(); //boolean
			NetworkEntries.m_nteMLCargo = tabAutoConfig.add("ML Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(5, 0).getEntry(); //boolean
			NetworkEntries.m_nteMRCargo = tabAutoConfig.add("MR Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(5, 1).getEntry(); //boolean
			NetworkEntries.m_nteRLCargo = tabAutoConfig.add("RL Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(6, 0).getEntry(); //boolean
			NetworkEntries.m_nteRRCargo = tabAutoConfig.add("RR Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(6, 1).getEntry(); //boolean


		// Drive Tab
			/*
			cam0 = CameraServer.startAutomaticCapture(0);
			cam0.setResolution(142, 90);
			cam0.setFPS(20);
			tabDrive.add("Field View", cam0).withSize(3, 2).withPosition(6, 0);
			tabDrive.add("Gyro", Direction.getInstance().getGyro()).withSize(2, 2).withPosition(3, 0);
			*/

			NetworkEntries.m_nteIsSmartIntakeEnabled = tabDrive.add("Is Smart Intake Enabled", true).withWidget(BuiltInWidgets.kToggleButton)
			.withSize(2, 1).withPosition(0, 0).getEntry();
			NetworkEntries.m_nteIsPivotAssistEnabled = tabDrive.add("Is Pivot Assist Enabled", true).withWidget(BuiltInWidgets.kToggleButton)
			.withSize(2, 1).withPosition(2, 0).getEntry();
			NetworkEntries.m_nteIsErrorCorrectionEnabled = tabDrive.addPersistent("Is Error Correction Enabled", false).withWidget(BuiltInWidgets.kToggleButton)
			.withSize(2, 1).withPosition(4, 0).getEntry();
			NetworkEntries.m_nteEndSmartIntake = tabDrive.add("End Smart Intake", false).withWidget(BuiltInWidgets.kToggleButton).withSize(2, 1).withPosition(0, 1).getEntry();
			NetworkEntries.m_nteDriveDistance = tabDrive.add("Distance Drived", 0).withWidget(BuiltInWidgets.kTextView).withSize(0, 0).withPosition(2, 1).getEntry();
			NetworkEntries.m_nteGyro = tabDrive.add("Gyro", 0).withWidget(BuiltInWidgets.kGyro).withSize(1, 1).withPosition(3, 1).getEntry();
			
			// Config Tab
			
		//Speeds Tab			
			NetworkEntries.m_nteMaxDriveSpeed = tabSpeeds.addPersistent("Max Drive Speed", kDefaultDriveSpeed).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(0, 0).getEntry();  //double
			NetworkEntries.m_nteMaxIntakePivotSpeed = tabSpeeds.addPersistent("Max Intake Pivot Speed", kDefaultIntakePivotSpeed).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 0).withPosition(2, 0).getEntry();  //double
			NetworkEntries.m_nteMaxIntakeRollerSpeed = tabSpeeds.addPersistent("Max Intake Roller Speed", kDefaultIntakeRollerSpeed).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(4, 0).withPosition(4, 0).getEntry();  //double
			NetworkEntries.m_nteMaxIndexerSpeed = tabSpeeds.addPersistent("Max Indexer Speed", kDefaultIndexSpeed).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(6, 0).getEntry();  //double
			NetworkEntries.m_nteMaxShootSpeed = tabSpeeds.addPersistent("Max Shoot Speed", kDefaultShootSpeed).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(2, 1).getEntry();  //double
			NetworkEntries.m_nteMaxClimbSpeed = tabSpeeds.addPersistent("Max Climb Speed", kDefaultClimbSpeed).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(4, 1).getEntry();  //double
			
		// Test Tab
			NetworkEntries.m_nteIntakeUpLimit = tabTest.add("Intake Up Limit", false).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(0, 0).getEntry();
			NetworkEntries.m_nteIntakeDownLimit = tabTest.add("Intake Down Limit", false).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(0, 1).getEntry();
			NetworkEntries.m_nteUpperStoragePE = tabTest.add("Upper Storage PE", false).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(1, 0).getEntry();
			NetworkEntries.m_nteLowerStoragePE = tabTest.add("Lower Storage PE", false).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(1, 1).getEntry();
			NetworkEntries.m_nteShooterPE = tabTest.add("Shooter PE", false).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(2, 0).getEntry();
			NetworkEntries.m_nteResetEncoders = tabTest.add("Reset Encoders", false).withWidget(BuiltInWidgets.kToggleButton).withSize(1,1).withPosition(3,0).getEntry();
			NetworkEntries.m_nteDriveEncLeft = tabTest.add("Left Encoder", 0).withWidget(BuiltInWidgets.kTextView).withSize(1, 1).withPosition(2, 1).getEntry();
			NetworkEntries.m_nteDriveEncRight = tabTest.add("Right Encoder", 0).withWidget(BuiltInWidgets.kTextView).withSize(1, 1).withPosition(3, 1).getEntry();
			NetworkEntries.m_nteDiffDrive = tabTest.add("Differential Drive", 0).withWidget(BuiltInWidgets.kDifferentialDrive).withSize(1, 1).withPosition(4, 4).getEntry();
			
			//Initialize variables
			previousTaxi = NetworkEntries.m_isAutoTaxiOn.getBoolean(false);
			previousCollect = NetworkEntries.m_isAutoCollectOn.getBoolean(false);
			previousSmartIntake = NetworkEntries.m_nteIsSmartIntakeEnabled.getBoolean(false);
		}

	/**@description This Method will update the values in the dashboard and in the networkTable
	 * in order to change the appropriate values accordingly
	 */
	@Override
	public void periodic() {
		selectorLogic();

		//Relay sensor readings to the dashboard
		//Encoders
		NetworkEntries.m_nteDriveEncLeft.setDouble(m_direction.getLeftDistance());
		NetworkEntries.m_nteDriveEncRight.setDouble(m_direction.getRightDistance());

		//Distance Drived
		NetworkEntries.m_nteDriveDistance.setDouble(m_direction.getDistance());

		//Limit switches
		NetworkEntries.m_nteIntakeUpLimit.setBoolean(m_si.isPivotUpLimitClosed());
		NetworkEntries.m_nteIntakeDownLimit.setBoolean(m_si.isPivotDownLimitClosed());

		//Gyro
		NetworkEntries.m_nteGyro.setValue(m_direction.getGyro());

		//Differential Drive
		NetworkEntries.m_nteDiffDrive.setValue(m_drivetrain.getDiffDrive());

		if (speedsDisabled) {
			//Sets max speeds to 0
			m_oi.setMaxDriveSpeed(0);
			m_oi.setMaxIntakePivotSpeed(0);
			m_oi.setMaxIntakeRollerSpeed(0);
			m_oi.setMaxIndexSpeed(0);
			m_oi.setMaxSmartIndexSpeed(0);
			m_oi.setMaxShootSpeed(0);
			m_oi.setMaxClimbSpeed(0);
		} else {
			//Sets max speeds from shuffleboard
			m_oi.setMaxDriveSpeed(NetworkEntries.m_nteMaxDriveSpeed.getDouble(0));
			m_oi.setMaxIntakePivotSpeed(NetworkEntries.m_nteMaxIntakePivotSpeed.getDouble(0));
			m_oi.setMaxIntakeRollerSpeed(NetworkEntries.m_nteMaxIntakeRollerSpeed.getDouble(0));
			m_oi.setMaxIndexSpeed(NetworkEntries.m_nteMaxIndexerSpeed.getDouble(0));
			m_oi.setMaxShootSpeed(NetworkEntries.m_nteMaxShootSpeed.getDouble(0));
			m_oi.setMaxClimbSpeed(NetworkEntries.m_nteMaxClimbSpeed.getDouble(0));
		}

		//Updates the photoelectric sensors in the dashboard
		NetworkEntries.m_nteLowerStoragePE.setBoolean(m_si.isLowerStorageClosed());
		NetworkEntries.m_nteUpperStoragePE.setBoolean(m_si.isUpperStorageClosed());
		NetworkEntries.m_nteShooterPE.setBoolean(m_si.isShooterClosed());
	}

	/**Returns an instance of Dashboard, creating an instance only when one does not already exist (singleton)*/
	public static Dashboard getInstance() {
		if (m_singleton == null) {
			m_singleton = new Dashboard();
		}
		return m_singleton;
	}

	/**Makes sure buttons do not conflict and are reset properly
	 */
	public static void selectorLogic() {
		//If auto collect has been enabled
		if (NetworkEntries.m_isAutoCollectOn.getBoolean(false) && !previousCollect) {
			//Enable auto taxi
			NetworkEntries.m_isAutoTaxiOn.setBoolean(true);
		}
		//If auto taxi has been disabled
		if (!NetworkEntries.m_isAutoTaxiOn.getBoolean(true) && previousTaxi) {
			//Disable auto collect
			NetworkEntries.m_isAutoCollectOn.setBoolean(false);
		}
		//Set with the current reading of the auto collect and taxi
		previousCollect = NetworkEntries.m_isAutoCollectOn.getBoolean(false);
		previousTaxi = NetworkEntries.m_isAutoTaxiOn.getBoolean(false);

		//If the right tarmac is selected
		if (NetworkEntries.m_nteRightTarmac.getBoolean(false)) {
			//Deselect the left most cargo
			NetworkEntries.m_nteLLCargo.setBoolean(false);
			NetworkEntries.m_nteLRCargo.setBoolean(false);
		} else {  //If the left tarmac is selected
			//Deselect the right most cargo
			NetworkEntries.m_nteRLCargo.setBoolean(false);
			NetworkEntries.m_nteRRCargo.setBoolean(false);
		}

		//If the reset encoders button has been pressed
		if (NetworkEntries.m_nteResetEncoders.getBoolean(false)) {
			//Reset the encoders
			m_direction.resetEncoders();
			//Deselect the reset encoders button
			NetworkEntries.m_nteResetEncoders.setBoolean(false);
		}

		//If the end smart intake button has been pressed
		if (NetworkEntries.m_nteEndSmartIntake.getBoolean(false)) {
			//End the smart intake
			IntakeCommand.endSmartIntake();
			//Deselect the end smart intake button
			NetworkEntries.m_nteEndSmartIntake.setBoolean(false);
		}

		//If the smart intake has been disabled
		if (!NetworkEntries.m_nteIsSmartIntakeEnabled.getBoolean(true) && previousSmartIntake) {
			//End the smart intake
			IntakeCommand.endSmartIntake();
		}
		//Set with the current reading of the smart intake button
		previousSmartIntake = NetworkEntries.m_nteIsSmartIntakeEnabled.getBoolean(false);
	}

	/**Disables or enables the speeds*/
	public void disableSpeeds(boolean disabled) {
		speedsDisabled = disabled;
	}
}
