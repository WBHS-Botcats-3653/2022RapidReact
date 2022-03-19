// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.DrivePIDConstants.*;

import java.util.Arrays;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.*;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.commands.AutoCommand;
import frc.robot.subsystems.*;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
	// The robot's subsystems and commands are defined here...
	private final DriveTrain m_driveTrain = DriveTrain.getInstance();
	private final DrivePID m_drivePID = DrivePID.getInstance();
	private final Direction m_direction = Direction.getInstance();
	private final AutoCommand m_autoCommand = new AutoCommand();

	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	
	public RobotContainer() {
		// Configure the button bindings
		configureButtonBindings();
	}

	/**
	 * Use this method to define your button->command mappings. Buttons can be created by
	 * instantiating a {@link GenericHID} or one of its subclasses ({@link
	 * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
	 * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
	 */
	private void configureButtonBindings() {}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	public AutoCommand getAutonomousCommand() {
		TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(2), Units.feetToMeters(2));
		config.setKinematics(m_drivePID.getKinematics());
		//Test trajectory
		Trajectory trajectory = TrajectoryGenerator.generateTrajectory(Arrays.asList(new Pose2d(), new Pose2d(1.0, 0.0, new Rotation2d())), config);
		//Takes the robots current position, trajectory, and wheel speeds along with other methods and calculates a linear and angular velocity to move the robot
		RamseteCommand ramCommand = 
			new RamseteCommand(
				trajectory,
				m_drivePID::getPose,
				new RamseteController(kRamseteB, kRamseteZeta),
				m_drivePID.getFeedForward(),
				m_drivePID.getKinematics(),
				m_drivePID::getWheelSpeeds,
				m_drivePID.getLeftPIDController(),
				m_drivePID.getRightPIDController(),
				m_driveTrain::tankDriveVolts,
				m_drivePID,
				m_driveTrain,
				m_direction
			);
		//return ramCommand;  //Set return type to Command
		return m_autoCommand;
	}
}
