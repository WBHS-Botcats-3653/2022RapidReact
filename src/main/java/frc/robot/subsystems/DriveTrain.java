// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.DriveConstants.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class DriveTrain extends SubsystemBase {
	private static DriveTrain m_singleton = null;
	private Direction m_direction = Direction.getInstance();

	private WPI_VictorSPX driveFrontLeft, driveBackLeft, driveFrontRight, driveBackRight;
	private DifferentialDrive diffDrive;

	private DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Units.inchesToMeters(kTrackWidth));
	private DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(m_direction.getHeading(), new Pose2d(0.0, 0.0, new Rotation2d()));

	private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(kS, kV, kA);

	private PIDController leftPIDController = new PIDController(kP, kI, kD);
	private PIDController rightPIDController = new PIDController(kP, kI, kD);

	private Pose2d position;

	//documentation for WPI_VictorSPX: 
	//https://robotpy.readthedocs.io/projects/ctre/en/stable/ctre/WPI_VictorSPX.html

	//Constructor
	private DriveTrain() {
		//Wheel motors
		driveFrontLeft = new WPI_VictorSPX(kFrontLeftMotorID);
		driveBackLeft = new WPI_VictorSPX(kBackLeftMotorID);
		driveFrontRight = new WPI_VictorSPX(kFrontRightMotorID);
		driveBackRight = new WPI_VictorSPX(kBackRightMotorID);

		//Groups the wheel motors
		MotorControllerGroup driveLeft = new MotorControllerGroup(driveFrontLeft, driveBackLeft);
		MotorControllerGroup driveRight = new MotorControllerGroup(driveFrontRight, driveBackRight);

		//Creates differential drive
		diffDrive = new DifferentialDrive(driveLeft, driveRight);

		//Reverses left motor direction
		driveLeft.setInverted(true);
	}

	@Override
	public void periodic() {
		position = odometry.update(m_direction.getHeading(), m_direction.getLeftDistance(), m_direction.getRightDistance());
	}
	
	//Returns an instance of DrainTrain, creating an instance only when one does not already exist
	public static DriveTrain getInstance() {
		if (m_singleton == null) {
			m_singleton = new DriveTrain();
		}
		return m_singleton;
	}

	/**Sets the arcade drive speed and rotation
	 * @param speed Speed to drive the robot
	 * @param rotation Amount to rotate
	 */
	public void arcadeDrive(double speed, double rotation) {
		//Prevents speed and rotation from exceeding maximum limit
		if (Math.abs(speed) > 1.0) speed = Math.signum(speed);
		if (Math.abs(rotation) > 1.0) rotation = Math.signum(rotation);
		//Sets the differential drive speed and rotation
		diffDrive.arcadeDrive(speed, rotation, false);
	}
 
	/**Sets the tank drive left and right drive speeds
	 * @param leftSpeed Speed of the left drive train
	 * @param rightSpeed Speed of the right drive train
	 */
	public void tankDrive(double leftSpeed, double rightSpeed) {
		//Prevents speeds from exceeding the maximum limit
		if (Math.abs(leftSpeed) > 1.0) leftSpeed = Math.signum(leftSpeed);
		if (Math.abs(rightSpeed) > 1.0) rightSpeed = Math.signum(rightSpeed);
		//Sets the left and right drive train speeds
		diffDrive.tankDrive(leftSpeed, rightSpeed);
	}

	//Returns the differential drive
	public DifferentialDrive getDiffDrive() {
		return diffDrive;
	}

	//Returns the feedforward
	public SimpleMotorFeedforward getFeedForward() {
		return feedforward;
	}

	//Returns the left PIDController
	public PIDController getLeftPIDController() {
		return leftPIDController;
	}

	//Returns the right PIDController
	public PIDController getRightPIDController() {
		return rightPIDController;
	}

	//Enables or disabled the neutral brake on the motors
	public void enableMotors(boolean enable) {
		NeutralMode mode = enable ? NeutralMode.Brake : NeutralMode.Coast;
		driveFrontLeft.setNeutralMode(mode);
		driveBackLeft.setNeutralMode(mode);
		driveFrontRight.setNeutralMode(mode);
		driveBackRight.setNeutralMode(mode);
	}
}
