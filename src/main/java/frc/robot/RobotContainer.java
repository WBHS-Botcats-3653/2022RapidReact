// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
//Imports controller
import edu.wpi.first.wpilibj.XboxController;
//Imports commands
import frc.robot.commands.ArcadeDriveCommand;
import frc.robot.commands.AutoCommand;
import frc.robot.commands.ClimberCommand;
import frc.robot.commands.IndexerCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.ShooterCommand;
//Imports subsystems
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Indexer;
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
	private final DriveTrain m_driveSubsystem = DriveTrain.getInstance();
	private final Climber m_climberSubsystem = Climber.getInstance();
	private final Intake m_intakeSubsystem = Intake.getInstance();
	private final Dashboard m_dashboardSubsystem = Dashboard.getInstance();
	private final Shooter m_shooterSubsystem = Shooter.getInstance();
	private final Indexer m_indexerSubsystem = Indexer.getInstance();
	
	private final ArcadeDriveCommand m_arcadeDriveCommand = new ArcadeDriveCommand(m_driveSubsystem);
	private final ClimberCommand m_climberCommand = new ClimberCommand(m_climberSubsystem);
	private final IntakeCommand m_intakeCommand = new IntakeCommand(m_intakeSubsystem);
	private final ShooterCommand m_shooterCommand = new ShooterCommand(m_shooterSubsystem);
	private final AutoCommand m_autoCommand = new AutoCommand();
	private final IndexerCommand m_indexerCommand = new IndexerCommand(m_indexerSubsystem);

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
		return m_autoCommand;
	}

	/**
	 * Use this to pass the arcade drive command to the main {@link Robot} class.
	 *
	 * @return the command to run in teleop
	 */
	public ArcadeDriveCommand getArcadeDriveCommand() {
		return m_arcadeDriveCommand;
	}

	/**
	 * Use this to pass the climber command to the main {@link Robot} class.
	 *
	 * @return the command to run in teleop
	 */
	public ClimberCommand getClimberCommand() {
		return m_climberCommand;
	}

	/**
	 * Use this to pass the arcade intake to the main {@link Robot} class.
	 *
	 * @return the command to run in teleop
	 */
	public IntakeCommand getIntakeCommand() {
		return m_intakeCommand;
	}

	/**
	 * Use this to pass the shooter command to the main {@link Robot} class.
	 *
	 * @return the command to run in teleop
	 */
	public ShooterCommand getShooterCommand() {
		return m_shooterCommand;
	}

	/**
	 * Use this to pass the indexer command to the main {@link Robot} class.
	 *
	 * @return the command to run in teleop
	 */
	public IndexerCommand getIndexerCommand() {
		return m_indexerCommand;
	}
}
