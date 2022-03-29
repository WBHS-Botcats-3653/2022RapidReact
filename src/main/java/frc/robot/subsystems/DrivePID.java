// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.DrivePIDConstants.*;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.*;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/*Good explanation video
 *http://youtube.com/watch?v=wqJ4tY0u6IQ
 */
public class DrivePID extends SubsystemBase {
	private static DrivePID m_singleton;
	private Direction m_direction = Direction.getInstance();

	//Holds the heading and encoder values
	private DifferentialDriveOdometry odometry;

	//Converts linear and angular velocity into left and right wheel speeds
	private DifferentialDriveKinematics kinematics;

	//Converts left and right wheels speeds to motor voltages needed to achieve those speeds, uses robot characterization to achieve this
	private SimpleMotorFeedforward feedforward;

	//Calculates the motor voltage required to smoothly get the robot to the desired endpoint
	private PIDController leftPIDController;
	private PIDController rightPIDController;

	//Holds the current position of the robot on the field
	private Pose2d pose;

	/** Creates a new DrivePID. */
	public DrivePID() {
		odometry = new DifferentialDriveOdometry(m_direction.getHeading(), new Pose2d(0.0, 0.0, new Rotation2d()));
		kinematics = new DifferentialDriveKinematics(Units.inchesToMeters(kTrackWidth));
		feedforward = new SimpleMotorFeedforward(kS, kV, kA);
		leftPIDController = new PIDController(kP, kI, kD);
		rightPIDController = new PIDController(kP, kI, kD);
	}

	@Override
	public void periodic() {
		//Updates the current position of the robot on the field
		pose = odometry.update(m_direction.getHeading(), m_direction.getLeftEncoderDistance(), m_direction.getRightEncoderDistance());
	}

	//Returns an instance of DrivePID
	public static DrivePID getInstance() {
		if (m_singleton == null) {
			m_singleton = new DrivePID();
		}
		return m_singleton;
	}

	//Returns the pose
	public Pose2d getPose() {
		return pose;
	}

	//Returns the feedforward
	public SimpleMotorFeedforward getFeedForward() {
		return feedforward;
	}

	//Returns the kinematics
	public DifferentialDriveKinematics getKinematics() {
		return kinematics;
	}

	//Returns the left and right wheel speeds
	public DifferentialDriveWheelSpeeds getWheelSpeeds() {
		//Converts the left and right encoder rates into left and right wheel speeds
		return new DifferentialDriveWheelSpeeds(m_direction.getLeftEncoderRate(), m_direction.getRightEncoderRate());
	}

	//Returns the left PIDController
	public PIDController getLeftPIDController() {
		return leftPIDController;
	}

	//Returns the right PIDController
	public PIDController getRightPIDController() {
		return rightPIDController;
	}
}
