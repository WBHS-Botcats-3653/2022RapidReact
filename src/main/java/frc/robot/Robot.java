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
	private final Dashboard m_dashboardSubsystem = Dashboard.getInstance();
	private final Direction m_directionSubsystem = Direction.getInstance();
	private final DriveTrain m_driveTrainSubsystem = DriveTrain.getInstance();
	private final Intake m_intakeSubsystem = Intake.getInstance();
	private final Indexer m_indexerSubsystem = Indexer.getInstance();
	private final Shooter m_shooterSubsystem = Shooter.getInstance();
	private final Climber m_climberSubsystem = Climber.getInstance();

	//Commands
	private AutoCommand m_autonomousCommand;
	private final ArcadeDriveCommand m_arcadeDriveCommand = new ArcadeDriveCommand(m_driveTrainSubsystem);
	private final IntakeCommand m_intakeCommand = new IntakeCommand(m_intakeSubsystem);
	private final IndexerCommand m_indexerCommand = new IndexerCommand(m_indexerSubsystem);
	private final ShooterCommand m_shooterCommand = new ShooterCommand(m_shooterSubsystem);
	private final ClimberCommand m_climberCommand = new ClimberCommand(m_climberSubsystem);
	
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
		m_directionSubsystem.calibrateGyro();
		//Resets the encoders
		m_directionSubsystem.resetEncoders();
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
		m_driveTrainSubsystem.enableMotors(false);
		m_intakeSubsystem.enableMotors(false);
		m_indexerSubsystem.enableMotors(false);
		m_climberSubsystem.enableMotors(true);

		//Disables the speeds
		m_dashboardSubsystem.disableSpeeds(true);
	}

	/** This function is called periodically when disabled. */
	@Override
	public void disabledPeriodic() {
		AutoCommand.isAutoShootOn = NetworkEntries.m_isAutoShootOn.getBoolean(true);
		AutoCommand.isAutoTaxiOn = NetworkEntries.m_isAutoTaxiOn.getBoolean(true);
		AutoCommand.isAutoCollectOn = NetworkEntries.m_isAutoCollectOn.getBoolean(true);
	}

	/** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
	@Override
	public void autonomousInit() {
		//Sets motors to brake
		m_driveTrainSubsystem.enableMotors(true);
		m_intakeSubsystem.enableMotors(true);
		m_indexerSubsystem.enableMotors(true);
		m_climberSubsystem.enableMotors(true);

		//Gets the autonomous command from robotContainer
		m_autonomousCommand = m_robotContainer.getAutonomousCommand();

		m_autonomousCommand.setTarmac(NetworkEntries.getTarmac());
		m_autonomousCommand.setCargoToTarget(NetworkEntries.getCargos());

		// Schedules the autonomous command
		if (m_autonomousCommand != null) {
			CommandScheduler.getInstance().schedule(m_autonomousCommand);
		}

		//Disables the speeds
		m_dashboardSubsystem.disableSpeeds(true);
	}

	/** This function is called periodically during autonomous. */
	@Override
	public void autonomousPeriodic() {
		//Schedules sequential and parallel command groups fed from the AutoCommand
		if (AutoCommand.executingCommand) return;
		SequentialCommandGroup sequentialCommand = m_autonomousCommand.getSequentialCommand();
		ParallelCommandGroup parallelCommand = m_autonomousCommand.getParallelCommand();
		if (AutoCommand.commandToScheduleNext == 'S' && sequentialCommand != null) {
			AutoCommand.executingCommand = true;
			CommandScheduler.getInstance().schedule(sequentialCommand);
		} else if (AutoCommand.commandToScheduleNext == 'P' && parallelCommand != null) {
			AutoCommand.executingCommand = true;
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
		m_driveTrainSubsystem.enableMotors(false);
		m_intakeSubsystem.enableMotors(true);
		m_indexerSubsystem.enableMotors(true);
		m_climberSubsystem.enableMotors(true);

		// Cancels all running commands at the start of Teleoperated mode.
		CommandScheduler.getInstance().cancelAll();

		//Resets the encoders
		m_directionSubsystem.resetEncoders();

		CommandScheduler.getInstance().schedule(m_arcadeDriveCommand);
		CommandScheduler.getInstance().schedule(m_intakeCommand);
		CommandScheduler.getInstance().schedule(m_indexerCommand);
		CommandScheduler.getInstance().schedule(m_shooterCommand);
		CommandScheduler.getInstance().schedule(m_climberCommand);

		//Enables the speeds
		m_dashboardSubsystem.disableSpeeds(false);
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

		//Sets motors to coast
		m_climberSubsystem.enableMotors(true);
		m_driveTrainSubsystem.enableMotors(false);
		m_indexerSubsystem.enableMotors(true);
		m_intakeSubsystem.enableMotors(true);

		CommandScheduler.getInstance().schedule(m_arcadeDriveCommand);
		CommandScheduler.getInstance().schedule(m_intakeCommand);
		CommandScheduler.getInstance().schedule(m_indexerCommand);
		CommandScheduler.getInstance().schedule(m_shooterCommand);
		CommandScheduler.getInstance().schedule(m_climberCommand);

		//Enables the speeds
		m_dashboardSubsystem.disableSpeeds(false);
	}

	/** This function is called periodically during test mode. */
	@Override
	public void testPeriodic() {
		//f*ck the developer that made c and lua
	}
}
