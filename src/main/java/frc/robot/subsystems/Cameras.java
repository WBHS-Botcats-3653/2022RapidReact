// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.CameraConstants.*;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Cameras extends SubsystemBase {
	private static Cameras m_singleton;

	//Cameras
	private final UsbCamera frontCamera;
	private final UsbCamera rearCamera;


	/** Creates a new Cameras. */
	public Cameras() {
		//Creates new camera objects and starts live feed
		frontCamera = CameraServer.startAutomaticCapture(kFrontCameraID);
		rearCamera = CameraServer.startAutomaticCapture(kRearCameraID);

		//Sets the resolution of the cameras
		frontCamera.setResolution(kCameraResolutionWidth, kCameraResolutionHeight);
		rearCamera.setResolution(kCameraResolutionWidth, kCameraResolutionHeight);

		//Sets the frame rate of the cameras
		frontCamera.setFPS(kCameraFPS);
		rearCamera.setFPS(kCameraFPS);
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

	/** Returns the camera on the front of the robot
	 * @return the front camera UsbCamera object
	 */
	public UsbCamera getFrontCamera() {
		return frontCamera;
	}

	/** Returns the camera on the back of the robot
	 * @return the rear camera UsbCamera object
	 */
	public UsbCamera getRearCamera() {
		return rearCamera;
	}
}
