// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Cameras extends SubsystemBase {
	private static Cameras m_singleton;
	private UsbCamera fieldCamera;
	private UsbCamera indexerCamera;

	/** Creates a new Cameras. */
	public Cameras() {
		fieldCamera = CameraServer.startAutomaticCapture(0);
		indexerCamera = CameraServer.startAutomaticCapture(1);
		//fieldCamera.setResolution(142, 90);
		//indexerCamera.setResolution(142, 90);
		//fieldCamera.setFPS(20);
		//indexerCamera.setFPS(20);
	}

	//Returns an instance of Camera
	public static Cameras getInstance() {
		if (m_singleton == null) {
			m_singleton = new Cameras();
		}
		return m_singleton;
	}

	//Returns the camera facing towards the field
	public UsbCamera getFieldCamera() {
		return fieldCamera;
	}

	//Returns the camera facing into the indexer
	public UsbCamera getIndexerCamera() {
		return indexerCamera;
	}
}
