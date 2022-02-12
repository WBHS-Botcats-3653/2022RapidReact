// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//Imports WPI_VictorSPX (Motor Controller)
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

//Imports DifferentialDrive
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//Imports MotorControllerGroup
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
//Imports Subsystem Base
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//Imports OI
import frc.robot.OI;
//Imports DriveConstants
import frc.robot.Constants.DriveConstants;


public class DriveTrain extends SubsystemBase {
	private static DriveTrain driveTrain = null;
	public OI m_oi = OI.getInstance();
	private DifferentialDrive diffDrive;

	//documentation for WPI_VictorSPX: 
	//https://robotpy.readthedocs.io/projects/ctre/en/stable/ctre/WPI_VictorSPX.html

	//Constructor
	private DriveTrain() {
		//Left side wheel motors
		WPI_VictorSPX driveFrontLeft = new WPI_VictorSPX(DriveConstants.frontLeftMotorID);
		WPI_VictorSPX driveBackLeft = new WPI_VictorSPX(DriveConstants.backLeftMotorID);
		MotorControllerGroup driveLeft = new MotorControllerGroup(driveFrontLeft, driveBackLeft);
		//Right side wheel motors
		WPI_VictorSPX driveFrontRight = new WPI_VictorSPX(DriveConstants.frontRightMotorID);
		WPI_VictorSPX driveBackRight = new WPI_VictorSPX(DriveConstants.backRightMotorID);
		MotorControllerGroup driveRight = new MotorControllerGroup(driveFrontRight, driveBackRight);
		//Creates differential drive
		diffDrive = new DifferentialDrive(driveLeft, driveRight);
		//Reverses right motor direction
		driveLeft.setInverted(true);
	}
	
	//Returns an instance of the climber, creating an instance only when one does not already exist
	public static DriveTrain getInstance() {
		if (driveTrain == null) {
			driveTrain = new DriveTrain();
		}
		return driveTrain;
	}

	/**Sets the arcade drive speed and rotation
	 *OVERLOADED FUNCTION
	 */
	public void arcadeDrived() {
		//All stop called (used for testing)
		if (m_oi.getAllStop()) {
			//Stops motor(s)
			diffDrive.tankDrive(0, 0);
			return;
		}
		double speed = m_oi.getThrottle();
		//Caps the throttle speed from exceeding the set maxDriveSpeed
		if (Math.abs(speed) > m_oi.getMaxDriveSpeed()) speed = (speed < 0 ? -1 : 1) * m_oi.getMaxDriveSpeed();
		//Sets the differential drive speed and rotation
		diffDrive.arcadeDrive(speed, m_oi.getSteering());
	}

	/**Sets the arcade drive speed and rotation
	 * OVERLOADED FUNCTION
	 * @param speed
	 * @param rotation
	 */
	public void arcadeDrived(double speed, double rotation) {
		//All stop called (used for testing)
		if (m_oi.getAllStop()) {
			//Stops motor(s)
			diffDrive.tankDrive(0, 0);
			return;
		}
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
		//All stop called (used for testing)
		if (m_oi.getAllStop()) {
			//Stops motor(s)
			diffDrive.tankDrive(0, 0);
			return;
		}
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
