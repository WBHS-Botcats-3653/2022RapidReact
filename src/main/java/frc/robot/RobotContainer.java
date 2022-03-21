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
	private final DriveTrain m_drivetrain = DriveTrain.getInstance();
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
		//Creates a trajectory configuration with a max velocity of 2 meters per second and a max acceleration of 2 meters per seconds squared
		TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(2), Units.feetToMeters(2));
		//Passes a Kinematics object to the trajectory config
		config.setKinematics(m_drivePID.getKinematics());
		//Test trajectory
		Trajectory trajectory = TrajectoryGenerator.generateTrajectory(Arrays.asList(new Pose2d(), new Pose2d(1.0, 1.0, new Rotation2d())), config);
		/*Takes the robots current position, trajectory, and wheel speeds along with other
		 *Objects, Suppliers, and BiConsumers and calculates the linear and angular velocities to
		 *move the robot in order to follow the path of the trajectory
		 */
		RamseteCommand ramCommand = 
			new RamseteCommand(
				trajectory,  //Trajectory to follow
				m_drivePID::getPose,  //Method to supply the current position (Supplier)
				new RamseteController(kRamseteB, kRamseteZeta),  //Calculates the current linear and angular velocity of the robot
				m_drivePID.getFeedForward(),  //Gets SimpleMotorFeedForward which converts left and right wheel speeds to motor voltages
				m_drivePID.getKinematics(),  //Gets Kinematics which converts linear and angular velocity into left and right wheel speeds
				m_drivePID::getWheelSpeeds,  //Method to supply DifferentialDriveWheelSpeeds which contains the left and right wheel speeds (Supplier)
				m_drivePID.getLeftPIDController(),  //Gets the left drivetrain PID controller which calculates the motor voltage required to smoothly get the robot to the desired endpoint
				m_drivePID.getRightPIDController(),  //Gets the right drivetrain PID controller which calculates the motor voltage required to smoothly get the robot to the desired endpoint
				m_drivetrain::tankDriveVolts,  //Method which sets the left and right motor voltages of the drivetrain (BiConsumer)
				m_drivePID,  //Required subsystem
				m_drivetrain,  //Required subsystem
				m_direction  //Required subsystem
			);
		//return ramCommand;  //Set return type to Command
		return m_autoCommand;
	}
}
