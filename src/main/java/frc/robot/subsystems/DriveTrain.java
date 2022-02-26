// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.DriveConstants.*;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.inputs.OI;


public class DriveTrain extends SubsystemBase {
	private static DriveTrain driveTrain = null;
	public OI m_oi = OI.getInstance();
	private DifferentialDrive diffDrive;

	//documentation for WPI_VictorSPX: 
	//https://robotpy.readthedocs.io/projects/ctre/en/stable/ctre/WPI_VictorSPX.html

	//Constructor
	private DriveTrain() {
		//Left side wheel motors
		WPI_VictorSPX driveFrontLeft = new WPI_VictorSPX(kFrontLeftMotorID);
		WPI_VictorSPX driveBackLeft = new WPI_VictorSPX(kBackLeftMotorID);
		//Groups left wheel motors
		MotorControllerGroup driveLeft = new MotorControllerGroup(driveFrontLeft, driveBackLeft);
		//Right side wheel motors
		WPI_VictorSPX driveFrontRight = new WPI_VictorSPX(kFrontRightMotorID);
		WPI_VictorSPX driveBackRight = new WPI_VictorSPX(kBackRightMotorID);
		//Groups right wheel motors
		MotorControllerGroup driveRight = new MotorControllerGroup(driveFrontRight, driveBackRight);
		//Creates differential drive
		diffDrive = new DifferentialDrive(driveLeft, driveRight);
		//Reverses right motor direction
		driveLeft.setInverted(true);
	}
	
	//Returns an instance of DrainTrain, creating an instance only when one does not already exist
	public static DriveTrain getInstance() {
		if (driveTrain == null) {
			driveTrain = new DriveTrain();
		}
		return driveTrain;
	}

	/**Sets the arcade drive speed and rotation
	 * OVERLOADED FUNCTION
	 * @param speed
	 * @param rotation
	 */
	public void arcadeDrived(double speed, double rotation) {
		//Caps the speed from exceeding the set maxDriveSpeed
		if (Math.abs(speed) > m_oi.getMaxDriveSpeed()) speed = (speed < 0 ? -1 : 1) * m_oi.getMaxDriveSpeed();
		//Sets the differential drive speed and rotation
		diffDrive.arcadeDrive(speed, rotation);
	}

	/**Sets the tank drive left wheel speed and right wheel speed
	 * Used for autonomous
	 * @param leftSpeed
	 * @param rightSpeed
	 */
	public void tankDrived(double leftSpeed, double rightSpeed) {
		//Caps the speed from exceeding the set maxDriveSpeed
		if (Math.abs(leftSpeed) > m_oi.getMaxDriveSpeed()) leftSpeed = (leftSpeed < 0 ? -1 : 1) * m_oi.getMaxDriveSpeed();
		if (Math.abs(rightSpeed) > m_oi.getMaxDriveSpeed()) rightSpeed = (rightSpeed < 0 ? -1 : 1) * m_oi.getMaxDriveSpeed();
		//Sets the differential drive left wheel speed and right wheel speed
		diffDrive.tankDrive(leftSpeed, rightSpeed);
	}

	@Override
	public void periodic() {
	// This method will be called once per scheduler run
	}

	@Override
	public void simulationPeriodic() {
	// This method will be called once per scheduler run during simulation
	}
}
