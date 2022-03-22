// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
	private RobotContainer m_robotContainer;

	//Subsystems
	private final Dashboard m_dashboard = Dashboard.getInstance();
	private final Direction m_direction = Direction.getInstance();
	private final Drivetrain m_drivetrain = Drivetrain.getInstance();
	private final Intake m_intake = Intake.getInstance();
	private final Indexer m_indexer = Indexer.getInstance();
	private final Shooter m_shooter = Shooter.getInstance();
	private final Climber m_climber = Climber.getInstance();

	//Commands
	private AutoCommand m_autoCommand;
	private final ArcadeDriveCommand m_arcadeDriveCommand = new ArcadeDriveCommand(m_drivetrain);
	private final IntakeCommand m_intakeCommand = new IntakeCommand(m_intake);
	private final IndexerCommand m_indexerCommand = new IndexerCommand(m_indexer);
	private final ShooterCommand m_shooterCommand = new ShooterCommand(m_shooter);
	private final ClimberCommand m_climberCommand = new ClimberCommand(m_climber);
	
	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	@Override
	public void robotInit() {
		// Instantiate our RobotContainer.  This will perform all our button bindings, and put our
		// autonomous chooser on the dashboard.
		m_robotContainer = new RobotContainer();
		//Calibrates the gyro
		m_direction.calibrateGyro();
		//Resets the encoders
		m_direction.resetEncoders();
	}

	/**
	 * This function is called every robot packet, no matter the mode. Use this for items like
	 * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
	 *
	 * <p>This runs after the mode specific periodic functions, but before LiveWindow and
	 * SmartDashboard integrated updating.
	 */
	@Override
	public void robotPeriodic() {
		// Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
		// commands, running already-scheduled commands, removing finished or interrupted commands,
		// and running subsystem periodic() methods.  This must be called from the robot's periodic
		// block in order for anything in the Command-based framework to work.
		CommandScheduler.getInstance().run();
	}

	/** This function is called once each time the robot enters Disabled mode. */
	@Override
	public void disabledInit() {
		// Cancels all running commands when disabled.
		CommandScheduler.getInstance().cancelAll();

		//Ends the smart intake
		IntakeCommand.endSmartIntake();

		//Sets motors to coast or brake
		m_drivetrain.enableBrake(false);
		m_intake.enableBrake(false);
		m_indexer.enableBrake(false);

		//Disables the speeds
		m_dashboard.disableSpeeds(true);
	}

	/** This function is called periodically when disabled. */
	@Override
	public void disabledPeriodic() {}

	/** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
	@Override
	public void autonomousInit() {
		//Sets motors to brake
		m_drivetrain.enableBrake(true);
		m_intake.enableBrake(true);
		m_indexer.enableBrake(true);

		//Gets the autonomous command from robotContainer
		m_autoCommand = m_robotContainer.getAutonomousCommand();

		// Schedules the autonomous command
		if (m_autoCommand != null) {
			CommandScheduler.getInstance().schedule(m_autoCommand);
		}

		//Disables the speeds
		m_dashboard.disableSpeeds(true);
	}

	/** This function is called periodically during autonomous. */
	@Override
	public void autonomousPeriodic() {
		//Schedules sequential and parallel command groups fed from the AutoCommand
		if (m_autoCommand.hasFinished()) return;
		if (m_autoCommand.getExecutingCommand()) return;
		SequentialCommandGroup sequentialCommand = m_autoCommand.getSequentialCommandGroup();
		ParallelCommandGroup parallelCommand = m_autoCommand.getParallelCommandGroup();
		if (m_autoCommand.getCommandToScheduleNext() == 'S' && sequentialCommand != null) {
			AutoCommand.setExecutingCommand(true);
			CommandScheduler.getInstance().schedule(sequentialCommand);
		} else if (m_autoCommand.getCommandToScheduleNext() == 'P' && parallelCommand != null) {
			AutoCommand.setExecutingCommand(true);
			CommandScheduler.getInstance().schedule(parallelCommand);
		}
	} 

	/** This function is called once each time the robot enters Teleoperated mode. */
	@Override
	public void teleopInit() {
		// Cancels all running commands at the start of teleop.
		CommandScheduler.getInstance().cancelAll();

		//Ends the smart intake
		IntakeCommand.endSmartIntake();

		//Sets motors to coast or brake
		m_drivetrain.enableBrake(false);
		m_intake.enableBrake(true);
		m_indexer.enableBrake(true);

		// Cancels all running commands at the start of Teleoperated mode.
		CommandScheduler.getInstance().cancelAll();

		//Resets the encoders
		m_direction.resetEncoders();

		CommandScheduler.getInstance().schedule(m_arcadeDriveCommand);
		CommandScheduler.getInstance().schedule(m_intakeCommand);
		CommandScheduler.getInstance().schedule(m_indexerCommand);
		CommandScheduler.getInstance().schedule(m_shooterCommand);
		CommandScheduler.getInstance().schedule(m_climberCommand);

		//Enables the speeds
		m_dashboard.disableSpeeds(false);
	}

	/** This function is called periodically during operator control. */
	@Override
	public void teleopPeriodic() {}

	/** This function is called once each time the robot enters Test mode. */
	@Override
	public void testInit() {
		// Cancels all running commands at the start of test mode.
		CommandScheduler.getInstance().cancelAll();

		//Ends the smart intake
		IntakeCommand.endSmartIntake();

		//Sets motors to coast or brake
		m_drivetrain.enableBrake(false);
		m_indexer.enableBrake(true);
		m_intake.enableBrake(true);

		CommandScheduler.getInstance().schedule(m_arcadeDriveCommand);
		CommandScheduler.getInstance().schedule(m_intakeCommand);
		CommandScheduler.getInstance().schedule(m_indexerCommand);
		CommandScheduler.getInstance().schedule(m_shooterCommand);
		CommandScheduler.getInstance().schedule(m_climberCommand);

		//Enables the speeds
		m_dashboard.disableSpeeds(false);
	}

	/** This function is called periodically during test mode. */
	@Override
	public void testPeriodic() {
		//f*ck the developer that made c and lua
	}
}
