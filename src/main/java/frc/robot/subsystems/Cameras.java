// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Cameras extends SubsystemBase {
	private static Cameras m_singleton;

	//Cameras
	private final UsbCamera fieldCamera;
	private final UsbCamera indexerCamera;

	/** Creates a new Cameras. */
	public Cameras() {
		//Creates new camera objects and starts live feed
		fieldCamera = CameraServer.startAutomaticCapture(0);
		indexerCamera = CameraServer.startAutomaticCapture(1);

		//Sets the resolution of the cameras
		fieldCamera.setResolution(142, 90);
		indexerCamera.setResolution(142, 90);

		//Sets the frame rate of the cameras
		fieldCamera.setFPS(20);
		indexerCamera.setFPS(20);
	}

	/** Returns an instance of Cameras
	 * @return an instance of Cameras
	 */
	public static Cameras getInstance() {
		if (m_singleton == null) {
			m_singleton = new Cameras();
		}
		return m_singleton;
	}

	/** Returns the camera facing towards the field
	 * @return the field camera UsbCamera object
	 */
	public UsbCamera getFieldCamera() {
		return fieldCamera;
	}

	/** Returns the camera facing into the indexer
	 * @return the indexer camera UsbCamera object
	 */
	public UsbCamera getIndexerCamera() {
		return indexerCamera;
	}
}
