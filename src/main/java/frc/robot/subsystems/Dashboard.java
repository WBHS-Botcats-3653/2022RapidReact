// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.DefaultSpeedsConstants.*;

import java.util.Arrays;
import java.util.Map;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.NetworkEntries;
import frc.robot.commands.AutoCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.inputs.OI;
import frc.robot.inputs.SI;

public class Dashboard extends SubsystemBase {
	private static Dashboard m_singleton;
	private Drivetrain m_driveTrain = Drivetrain.getInstance();
	private Direction m_direction = Direction.getInstance();
	private Cameras m_cameras = Cameras.getInstance();
	private OI m_oi = OI.getInstance();
	private SI m_si =  SI.getInstance();

	public ShuffleboardTab tabDrive, tabTest, tabSpeeds, tabAuto;

	private boolean prevShootPreload, prevTaxi, prevCollectCargo, prevShootCollectedCargo, prevCustomTrajectory, prevTarmac, prevSmartIntake;
	private String[] prevCargoToTarget;
	private double prevVelocity, prevAcceleration, prevX, prevY, prevEndAngle;

	public boolean speedsDisabled = true;

	private Dashboard() {
		tabAuto = Shuffleboard.getTab("Auto");
		tabDrive = Shuffleboard.getTab("Drive");
		tabSpeeds = Shuffleboard.getTab("Speeds");
		tabTest = Shuffleboard.getTab("Test");

		//Auto Tab
			NetworkEntries.m_nteShootPreloadSelected = tabAuto.addPersistent("Shoot Cargo", false).withWidget(BuiltInWidgets.kToggleButton).withSize(1, 1).withPosition(0, 0).getEntry();
			NetworkEntries.m_nteHasPreload = tabAuto.addPersistent("Has Preload", false).withWidget(BuiltInWidgets.kToggleButton).withSize(1, 1).withPosition(0, 1).getEntry();
			NetworkEntries.m_nteTaxiSelected = tabAuto.addPersistent("Taxi", false).withWidget(BuiltInWidgets.kToggleButton).withSize(1, 1).withPosition(1, 0).getEntry();
			NetworkEntries.m_nteCollectCargoSelected = tabAuto.addPersistent("Collect Cargo", false).withWidget(BuiltInWidgets.kToggleButton).withSize(1, 1).withPosition(2, 0).getEntry();
			NetworkEntries.m_nteShootCollectedCargoSelected = tabAuto.addPersistent("Shoot Collected Cargo", false).withWidget(BuiltInWidgets.kToggleButton).withSize(1, 1).withPosition(2, 1).getEntry();

			NetworkEntries.m_nteRightTarmac = tabAuto.add("Is Right Tarmac?", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(3, 0).getEntry();
			NetworkEntries.m_nteLLCargo = tabAuto.add("LL Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(4, 0).getEntry();
			NetworkEntries.m_nteLRCargo = tabAuto.add("LR Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(4, 1).getEntry();
			NetworkEntries.m_nteMLCargo = tabAuto.add("ML Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(5, 0).getEntry();
			NetworkEntries.m_nteMRCargo = tabAuto.add("MR Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(5, 1).getEntry();
			NetworkEntries.m_nteRLCargo = tabAuto.add("RL Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(6, 0).getEntry();
			NetworkEntries.m_nteRRCargo = tabAuto.add("RR Cargo", false).withWidget(BuiltInWidgets.kToggleSwitch).withSize(1, 1).withPosition(6, 1).getEntry();
			NetworkEntries.m_nteGenerateCargoCollection = tabAuto.add("Generate Cargo Collection", false).withWidget(BuiltInWidgets.kToggleButton).withSize(1, 1).withPosition(3, 1).getEntry();
			NetworkEntries.m_nteCargoCollectionHasGenerated = tabAuto.add("Cargo Collection Has Generated", false).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(3, 2).getEntry();

			NetworkEntries.m_nteMaxVelocity = tabAuto.add("Max Velocity m/s", 2.0).withWidget(BuiltInWidgets.kTextView).withSize(1, 1).withPosition(0, 2).getEntry();
			NetworkEntries.m_nteMaxAcceleration = tabAuto.add("Max Acceleration m/s^2", 2.0).withWidget(BuiltInWidgets.kTextView).withSize(1, 1).withPosition(1, 2).getEntry();
			NetworkEntries.m_nteX = tabAuto.add("X", 0.0).withWidget(BuiltInWidgets.kTextView).withSize(1, 1).withPosition(0, 3).getEntry();
			NetworkEntries.m_nteY = tabAuto.add("Y", 0.0).withWidget(BuiltInWidgets.kTextView).withSize(1, 1).withPosition(1, 3).getEntry();
			NetworkEntries.m_nteEndAngle = tabAuto.add("End Angle", 0.0).withWidget(BuiltInWidgets.kTextView).withSize(1, 1).withPosition(2, 3).getEntry();
			NetworkEntries.m_nteGenerateCustomTrajectory = tabAuto.add("Generate Trajectory", false).withWidget(BuiltInWidgets.kToggleButton).withSize(1, 1).withPosition(0, 4).getEntry();
			NetworkEntries.m_nteCustomTrajectoryHasGenerated = tabAuto.add("Custom Trajectory Has Generated", false).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(1, 4).getEntry();

		//Drive Tab
			NetworkEntries.m_nteIsSmartIntakeEnabled = tabDrive.add("Is Smart Intake Enabled", true).withWidget(BuiltInWidgets.kToggleButton).withSize(2, 1).withPosition(0, 0).getEntry();
			NetworkEntries.m_nteIsPivotAssistEnabled = tabDrive.add("Is Pivot Assist Enabled", true).withWidget(BuiltInWidgets.kToggleButton).withSize(2, 1).withPosition(2, 0).getEntry();
			
			NetworkEntries.m_nteEndSmartIntake = tabDrive.add("End Smart Intake", false).withWidget(BuiltInWidgets.kToggleButton).withSize(2, 1).withPosition(0, 1).getEntry();
			
			NetworkEntries.m_nteDriveDistance = tabDrive.add("Distance Drived", 0).withWidget(BuiltInWidgets.kTextView).withSize(0, 0).withPosition(2, 1).getEntry();
			
			tabDrive.add("Gyro", m_direction.getGyro()).withWidget(BuiltInWidgets.kGyro).withSize(2, 2).withPosition(3, 1);

			tabDrive.add("Field View", m_cameras.getFieldCamera()).withWidget(BuiltInWidgets.kCameraStream).withSize(3, 2).withPosition(3, 2);
			tabDrive.add("Indexer View", m_cameras.getIndexerCamera()).withWidget(BuiltInWidgets.kCameraStream).withSize(3, 2).withPosition(6, 2);
			
		//Speeds Tab			
			NetworkEntries.m_nteMaxDriveSpeed = tabSpeeds.addPersistent("Max Drive Speed", kDefaultDriveSpeed).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(0, 0).getEntry();  //double
			NetworkEntries.m_nteMaxIntakePivotSpeed = tabSpeeds.addPersistent("Max Intake Pivot Speed", kDefaultIntakePivotSpeed).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 0).withPosition(2, 0).getEntry();  //double
			NetworkEntries.m_nteMaxIntakeRollerSpeed = tabSpeeds.addPersistent("Max Intake Roller Speed", kDefaultIntakeRollerSpeed).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(4, 0).withPosition(4, 0).getEntry();  //double
			NetworkEntries.m_nteMaxIndexerSpeed = tabSpeeds.addPersistent("Max Indexer Speed", kDefaultIndexSpeed).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(6, 0).getEntry();  //double
			NetworkEntries.m_nteMaxShootSpeed = tabSpeeds.addPersistent("Max Shoot Speed", kDefaultShootSpeed).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(2, 1).getEntry();  //double
			NetworkEntries.m_nteMaxClimbSpeed = tabSpeeds.addPersistent("Max Climb Speed", kDefaultClimbSpeed).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(2, 1).getEntry();  //double
			NetworkEntries.m_nteMaxExtenderWinchSpeed = tabSpeeds.addPersistent("Max Extender Winch Speed", kDefaultExtendSpeed).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(2, 1).getEntry();  //double
			NetworkEntries.m_nteMaxHookWinchSpeed = tabSpeeds.addPersistent("Max Hook Winch Speed", kDefaultWinchSpeed).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1.0)).withSize(2, 1).withPosition(4, 1).getEntry();  //double
			
		//Test Tab
			NetworkEntries.m_nteUpperStoragePE = tabTest.add("Upper Storage PE", false).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(1, 0).getEntry();
			NetworkEntries.m_nteLowerStoragePE = tabTest.add("Lower Storage PE", false).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(1, 1).getEntry();
			NetworkEntries.m_nteShooterPE = tabTest.add("Shooter PE", false).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(2, 0).getEntry();
			
			NetworkEntries.m_nteIntakeUpLimit = tabTest.add("Intake Up Limit", false).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(0, 0).getEntry();
			NetworkEntries.m_nteIntakeDownLimit = tabTest.add("Intake Down Limit", false).withWidget(BuiltInWidgets.kBooleanBox).withSize(1, 1).withPosition(0, 1).getEntry();

			NetworkEntries.m_nteDriveEncLeft = tabTest.add("Left Encoder", 0).withWidget(BuiltInWidgets.kTextView).withSize(1, 1).withPosition(2, 1).getEntry();
			NetworkEntries.m_nteDriveEncRight = tabTest.add("Right Encoder", 0).withWidget(BuiltInWidgets.kTextView).withSize(1, 1).withPosition(3, 1).getEntry();
			
			NetworkEntries.m_nteResetEncoders = tabTest.add("Reset Encoders", false).withWidget(BuiltInWidgets.kToggleButton).withSize(1, 1).withPosition(3, 0).getEntry();

			tabTest.add("Differential Drive", m_driveTrain.getDiffDrive()).withSize(2, 2).withPosition(0, 2);
			
		//Initialize variables
		prevShootPreload = NetworkEntries.m_nteShootPreloadSelected.getBoolean(false);
		prevTaxi = NetworkEntries.m_nteTaxiSelected.getBoolean(false);
		prevCollectCargo = NetworkEntries.m_nteCollectCargoSelected.getBoolean(false);
		prevCustomTrajectory = NetworkEntries.m_nteCustomTrajectorySelected.getBoolean(false);
		prevTarmac = NetworkEntries.m_nteRightTarmac.getBoolean(false);
		prevCargoToTarget = NetworkEntries.getCargoToTarget();
		prevShootCollectedCargo = NetworkEntries.m_nteShootCollectedCargoSelected.getBoolean(false);
		prevVelocity = NetworkEntries.m_nteMaxVelocity.getDouble(0);
		prevAcceleration = NetworkEntries.m_nteMaxAcceleration.getDouble(0);
		prevX = NetworkEntries.m_nteX.getDouble(0);
		prevY = NetworkEntries.m_nteY.getDouble(0);
		prevEndAngle = NetworkEntries.m_nteEndAngle.getDouble(0);
		prevSmartIntake = NetworkEntries.m_nteIsSmartIntakeEnabled.getBoolean(false);
	}

	/**@description This Method will update the values in the dashboard and in the networkTable
	 * in order to change the appropriate values accordingly
	 */
	@Override
	public void periodic() {
		selectorLogic();

		//Relay sensor readings to the dashboard
		//Encoders
		NetworkEntries.m_nteDriveEncLeft.setDouble(Units.metersToInches(m_direction.getLeftEncoderDistance()));  //Inches
		NetworkEntries.m_nteDriveEncRight.setDouble(Units.metersToInches(m_direction.getRightEncoderDistance()));  //Inches

		//Distance Drived
		NetworkEntries.m_nteDriveDistance.setDouble(Units.metersToInches(m_direction.getEncoderDistance()));  //Inches

		//Limit switches
		NetworkEntries.m_nteIntakeUpLimit.setBoolean(m_si.isPivotUpLimitClosed());
		NetworkEntries.m_nteIntakeDownLimit.setBoolean(m_si.isPivotDownLimitClosed());

		if (speedsDisabled) {
			//Sets max speeds to 0
			m_oi.setMaxDriveSpeed(0);
			m_oi.setMaxIntakePivotSpeed(0);
			m_oi.setMaxIntakeRollerSpeed(0);
			m_oi.setMaxIndexSpeed(0);
			m_oi.setMaxSmartIndexSpeed(0);
			m_oi.setMaxShootSpeed(0);
			m_oi.setMaxClimbSpeed(0);
			m_oi.setMaxExtenderWinchSpeed(0);
			m_oi.setMaxHookWinchSpeed(0);
		} else {
			//Sets max speeds from shuffleboard
			m_oi.setMaxDriveSpeed(NetworkEntries.m_nteMaxDriveSpeed.getDouble(0));
			m_oi.setMaxIntakePivotSpeed(NetworkEntries.m_nteMaxIntakePivotSpeed.getDouble(0));
			m_oi.setMaxIntakeRollerSpeed(NetworkEntries.m_nteMaxIntakeRollerSpeed.getDouble(0));
			m_oi.setMaxIndexSpeed(NetworkEntries.m_nteMaxIndexerSpeed.getDouble(0));
			m_oi.setMaxShootSpeed(NetworkEntries.m_nteMaxShootSpeed.getDouble(0));
			m_oi.setMaxClimbSpeed(NetworkEntries.m_nteMaxClimbSpeed.getDouble(0));
			m_oi.setMaxExtenderWinchSpeed(NetworkEntries.m_nteMaxExtenderWinchSpeed.getDouble(0));
			m_oi.setMaxHookWinchSpeed(NetworkEntries.m_nteMaxHookWinchSpeed.getDouble(0));
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
	public void selectorLogic() {
		//If shoot preload has been enabled
		if (NetworkEntries.m_nteShootPreloadSelected.getBoolean(false) && !prevShootPreload) {
			//Robot has a piece of cargo preloaded
			NetworkEntries.m_nteHasPreload.setBoolean(true);
		}
		//Updates previous value of shoot preload
		prevShootPreload = NetworkEntries.m_nteShootPreloadSelected.getBoolean(false);

		//If shoot collected cargo has been enabled
		if (NetworkEntries.m_nteShootCollectedCargoSelected.getBoolean(false) && !prevShootCollectedCargo) {
			//Enable collect cargo
			NetworkEntries.m_nteCollectCargoSelected.setBoolean(true);
		} else if (!NetworkEntries.m_nteCollectCargoSelected.getBoolean(false) && prevCollectCargo) {
			//Disableds shoot collected cargo
			NetworkEntries.m_nteShootCollectedCargoSelected.setBoolean(false);
		}
		//Updates previous value of shoot collected cargo
		prevShootCollectedCargo = NetworkEntries.m_nteShootCollectedCargoSelected.getBoolean(false);

		if (NetworkEntries.m_nteTaxiSelected.getBoolean(true) && !prevTaxi) {  //If taxi has been enabled
			//Disable collect cargo and custom trajectory
			NetworkEntries.m_nteCollectCargoSelected.setBoolean(false);
			NetworkEntries.m_nteCustomTrajectorySelected.setBoolean(false);
		} else if (NetworkEntries.m_nteCollectCargoSelected.getBoolean(false) && !prevCollectCargo) {  //If collect cargo has been enabled
			//Disable taxi and custom trajectory
			NetworkEntries.m_nteTaxiSelected.setBoolean(false);
			NetworkEntries.m_nteCustomTrajectorySelected.setBoolean(false);
		} else if (NetworkEntries.m_nteCustomTrajectorySelected.getBoolean(true) && !prevCustomTrajectory) {  //If custom trajectory has been enabled
			//Disable taxi and collect cargo
			NetworkEntries.m_nteTaxiSelected.setBoolean(false);
			NetworkEntries.m_nteCollectCargoSelected.setBoolean(false);
		}
		//Set with the current reading of the taxi, collect cargo, and custom trajectory
		prevTaxi = NetworkEntries.m_nteTaxiSelected.getBoolean(false);
		prevCollectCargo = NetworkEntries.m_nteCollectCargoSelected.getBoolean(false);
		prevCustomTrajectory = NetworkEntries.m_nteCustomTrajectorySelected.getBoolean(false);

		//If any cargo collection settings have been changed
		if (NetworkEntries.m_nteRightTarmac.getBoolean(false) != prevTarmac || Arrays.equals(NetworkEntries.getCargoToTarget(), prevCargoToTarget)) {
			//Cargo collection trajectory has not been generated
			NetworkEntries.m_nteCargoCollectionHasGenerated.setBoolean(false);
		}
		//Update previous values
		prevTarmac = NetworkEntries.m_nteRightTarmac.getBoolean(false);
		prevCargoToTarget = NetworkEntries.getCargoToTarget();

		//If any custom trajectory settings have been changed
		if (NetworkEntries.m_nteMaxVelocity.getDouble(0) != prevVelocity || NetworkEntries.m_nteMaxAcceleration.getDouble(0) != prevAcceleration || NetworkEntries.m_nteX.getDouble(0) != prevX || NetworkEntries.m_nteY.getDouble(0) != prevY || NetworkEntries.m_nteEndAngle.getDouble(0) != prevEndAngle) {
			//Custom trajectory has not been updated
			NetworkEntries.m_nteCustomTrajectoryHasGenerated.setBoolean(false);
		}
		//Update previous values
		prevVelocity = NetworkEntries.m_nteMaxVelocity.getDouble(0);
		prevAcceleration = NetworkEntries.m_nteMaxAcceleration.getDouble(0);
		prevX = NetworkEntries.m_nteX.getDouble(0);
		prevY = NetworkEntries.m_nteY.getDouble(0);
		prevEndAngle = NetworkEntries.m_nteEndAngle.getDouble(0);

		//If the generate cargo collection has been pressed
		if (NetworkEntries.m_nteGenerateCargoCollection.getBoolean(false)) {
			//Generate trajectory for cargo collection
			AutoCommand.generateCargoCollectionTrajectory(NetworkEntries.getTarmac(), NetworkEntries.getCargoToTarget());
			//Deselect the generate cargo collection button
			NetworkEntries.m_nteGenerateCargoCollection.setBoolean(false);
			//Cargo collection tracjectory has been generated
			NetworkEntries.m_nteCargoCollectionHasGenerated.setBoolean(true);
		}

		//If the generate custom trajectory has been pressed
		if (NetworkEntries.m_nteGenerateCustomTrajectory.getBoolean(false)) {
			//Generate custom trajectory
			AutoCommand.generateCustomTrajectory(
				NetworkEntries.m_nteMaxVelocity.getDouble(0.0),
				NetworkEntries.m_nteMaxAcceleration.getDouble(0.0),
				NetworkEntries.m_nteX.getDouble(0.0),
				NetworkEntries.m_nteY.getDouble(0.0),
				NetworkEntries.m_nteEndAngle.getDouble(0.0)
			);
			//Deselect the generate custom trajectory button
			NetworkEntries.m_nteGenerateCustomTrajectory.setBoolean(false);
			//Custom trajectory has been generated
			NetworkEntries.m_nteCustomTrajectoryHasGenerated.setBoolean(true);
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
		if (!NetworkEntries.m_nteIsSmartIntakeEnabled.getBoolean(true) && prevSmartIntake) {
			//End the smart intake
			IntakeCommand.endSmartIntake();
		}
		//Set with the current reading of the smart intake button
		prevSmartIntake = NetworkEntries.m_nteIsSmartIntakeEnabled.getBoolean(false);
	}

	/**Disables or enables the speeds*/
	public void disableSpeeds(boolean disable) {
		speedsDisabled = disable;
	}
}
