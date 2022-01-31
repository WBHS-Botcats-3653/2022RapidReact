// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
//Imports controller
import edu.wpi.first.wpilibj.XboxController;
//Imports Command
import edu.wpi.first.wpilibj2.command.Command;
//Imports commands
import frc.robot.commands.ArcadeDriveCommand;
import frc.robot.commands.ClimberCommand;
import frc.robot.commands.DashboardCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.ShooterCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Dashboard;
//Imports subsystems
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
	// The robot's subsystems and commands are defined here...
	private final DriveTrain m_driveSubsystem = DriveTrain.getDriveTrain();
	private final Climber m_climberSubsystem = Climber.getClimber();
	private final Intake m_intakeSubsystem = Intake.getIntake();

	private final Dashboard m_dashboardSubsystem = Dashboard.getDashboard();

	private final Shooter m_shooterSubsystem = Shooter.getShooter();

	//private final ArcadeDriveCommand arcadeDriveCommand = new ArcadeDriveCommand();

	/** The container for the robot. Contains subsystems, OI devices, and commands. */
	
	public RobotContainer() {
		// Configure the button bindings
		configureButtonBindings();
		//Sets default commands for subsystems
		m_driveSubsystem.setDefaultCommand(new ArcadeDriveCommand());
		m_climberSubsystem.setDefaultCommand(new ClimberCommand());
		m_intakeSubsystem.setDefaultCommand(new IntakeCommand());

		m_dashboardSubsystem.setDefaultCommand(new DashboardCommand());

		m_shooterSubsystem.setDefaultCommand(new ShooterCommand());

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
	public Command getAutonomousCommand() {
		// An ExampleCommand will run in autonomous
		//TODO: add the command for autonomous
		return null;
	}
}
