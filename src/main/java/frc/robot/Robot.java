// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.*;
import frc.robot.inputs.OI;
import frc.robot.inputs.SI;
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
	private final DriveTrain m_driveTrainSubsystem = DriveTrain.getInstance();
	private final Climber m_climberSubsystem = Climber.getInstance();
	private final Intake m_intakeSubsystem = Intake.getInstance();
	private final Dashboard m_dashboardSubsystem = Dashboard.getInstance();
	private final Shooter m_shooterSubsystem = Shooter.getInstance();
	private final Direction m_directionSubsystem = Direction.getInstance();
	private final Indexer m_indexerSubsystem = Indexer.getInstance();

	private static NetworkEntries network = NetworkEntries.getInstance();

	//Inputs
	private final OI m_oi = OI.getInstance();
	private final SI m_si = SI.getInstance();

	//Commands
	private AutoCommand m_autonomousCommand;
	private final ArcadeDriveCommand m_arcadeDriveCommand = new ArcadeDriveCommand(m_driveTrainSubsystem);
	private final ClimberCommand m_climberCommand = new ClimberCommand(m_climberSubsystem);
	private final IntakeCommand m_intakeCommand = new IntakeCommand(m_intakeSubsystem);
	private final ShooterCommand m_shooterCommand = new ShooterCommand(m_shooterSubsystem);
	private final IndexerCommand m_indexerCommand = new IndexerCommand(m_indexerSubsystem);
	
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

		m_dashboardSubsystem.periodic();

		//Print stuff
		System.out.println("TEST PRINTLN");
		new PrintCommand("-----------------------------------------------").initialize();

		//Encoders
		new PrintCommand("Left Drive Distance: " + m_directionSubsystem.getLeftDistance()).initialize();
		new PrintCommand("Right Drive Distance: " + m_directionSubsystem.getRightDistance()).initialize();

		//Photoelectric sensors
		new PrintCommand("Shooter PE: " + m_si.getShooterTriggered()).initialize();
		new PrintCommand("Upper Storage PE: " + m_si.getUpperStorageTriggered()).initialize();
		new PrintCommand("Lower Storage PE: " + m_si.getLowerStorageTriggered()).initialize();

		//Limit switches
		new PrintCommand("Top Pivot Limit Switch: " + m_si.getPivotUpLimitTriggered()).initialize();
		new PrintCommand("Bottom Pivot Limit Switch: " + m_si.getPivotDownLimitTriggered()).initialize();
		new PrintCommand("Bottom Climber Limit Switch: " + m_si.getClimberDownLimitTriggered()).initialize();
	}

	/** This function is called once each time the robot enters Disabled mode. */
	@Override
	public void disabledInit() {
		//difDrive.reset();

		// Cancels all running commands when disabled.
		CommandScheduler.getInstance().cancelAll();

		//Sets max motor speeds
		m_oi.setMaxShootSpeed(0);
		m_oi.setMaxIntakePivotSpeed(0);
		m_oi.setMaxSmartIntakePivotDownSpeed(0);
		m_oi.setMaxSmartIntakePivotUpSpeed(0);
		m_oi.setMaxIntakeRollerSpeed(0);
		m_oi.setMaxArmSpeed(0);
		m_oi.setMaxDriveSpeed(0);
		m_oi.setMaxIndexerSpeed(0);
		m_oi.setMaxPivotAssistSpeed(0);
	}

	@Override
	public void disabledPeriodic() {
		AutoCommand.isAutoShootOn = network.m_isAutoShootOn.getBoolean(true);
		AutoCommand.isAutoTaxiOn = network.m_isAutoTaxiOn.getBoolean(true);
		AutoCommand.isAutoCollectOn = network.m_isAutoCollectOn.getBoolean(true);
	}

	/** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
	@Override
	public void autonomousInit() {
		//Resets the encoders
		m_directionSubsystem.resetEncoders();

		//Gets the autonomous command from robotContainer
		m_autonomousCommand = m_robotContainer.getAutonomousCommand();

		/*
		 *String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 *switch(autoSelected) {
		 *	case "My Auto": autonomousCommand=new MyAutoCommand();
		 *		break;
		 *	case "Default Auto":
		 *	default:
		 *		autonomousCommand=new ExampleCommand();
		 *		break;
		 *}
		 */

		// Schedules the autonomous command
		if (m_autonomousCommand != null) {
			//m_autonomousCommand.schedule();
			CommandScheduler.getInstance().schedule(m_autonomousCommand);
		}

		//Sets max motor speeds
		m_oi.setMaxShootSpeed(0.6);
		m_oi.setMaxIntakePivotSpeed(0.5);
		m_oi.setMaxSmartIntakePivotDownSpeed(0);
		m_oi.setMaxSmartIntakePivotUpSpeed(0);
		m_oi.setMaxIntakeRollerSpeed(1.0);
		m_oi.setMaxArmSpeed(1.0);
		m_oi.setMaxDriveSpeed(0.5);
		m_oi.setMaxIndexerSpeed(1.0);
		m_oi.setMaxPivotAssistSpeed(0.05);
	}

	/** This function is called periodically during autonomous. */
	@Override
	public void autonomousPeriodic() {
		//This is for testing the encoders if they are working

		//Schedules SquentialCommandGroups fed from the AutoCommand
		SequentialCommandGroup command = m_autonomousCommand.getCommand();
		if (command != null) {
			//command.schedule();
			CommandScheduler.getInstance().schedule(command);
		}
	} 

	@Override
	public void teleopInit() {
		// Cancels all running commands at the start of teleoperated mode.
		CommandScheduler.getInstance().cancelAll();

		//Resets the encoders
		m_directionSubsystem.resetEncoders();

		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}

		//Schedule teleop commands
		/*m_arcadeDriveCommand.schedule();
		m_climberCommand.schedule();
		m_indexerCommand.schedule();
		m_intakeCommand.schedule();
		m_shooterCommand.schedule();*/
		CommandScheduler.getInstance().schedule(m_arcadeDriveCommand);
		CommandScheduler.getInstance().schedule(m_climberCommand);
		CommandScheduler.getInstance().schedule(m_indexerCommand);
		CommandScheduler.getInstance().schedule(m_intakeCommand);
		CommandScheduler.getInstance().schedule(m_shooterCommand);

		//Sets max motor speeds
		m_oi.setMaxShootSpeed(0.6);
		m_oi.setMaxIntakePivotSpeed(0.5);
		m_oi.setMaxSmartIntakePivotDownSpeed(0.15);
		m_oi.setMaxSmartIntakePivotUpSpeed(0.35);
		m_oi.setMaxIntakeRollerSpeed(1.0);
		m_oi.setMaxArmSpeed(1.0);
		m_oi.setMaxDriveSpeed(1.0);
		m_oi.setMaxIndexerSpeed(1.0);
		m_oi.setMaxPivotAssistSpeed(0.05);
	}

	/** This function is called periodically during operator control. */
	@Override
	public void teleopPeriodic() {}

	@Override
	public void testInit() {
		// Cancels all running commands at the start of test mode.
		CommandScheduler.getInstance().cancelAll();

		//Schedule test commands
		/*m_arcadeDriveCommand.schedule();
		m_climberCommand.schedule();
		m_indexerCommand.schedule();
		m_intakeCommand.schedule();
		m_shooterCommand.schedule();*/
		CommandScheduler.getInstance().schedule(m_arcadeDriveCommand);
		CommandScheduler.getInstance().schedule(m_climberCommand);
		CommandScheduler.getInstance().schedule(m_indexerCommand);
		CommandScheduler.getInstance().schedule(m_intakeCommand);
		CommandScheduler.getInstance().schedule(m_shooterCommand);

		//Sets max motor speeds
		m_oi.setMaxShootSpeed(0.1);
		m_oi.setMaxIntakePivotSpeed(0.5);
		m_oi.setMaxSmartIntakePivotDownSpeed(0.3);
		m_oi.setMaxSmartIntakePivotUpSpeed(0.4);
		m_oi.setMaxIntakeRollerSpeed(0.5);
		m_oi.setMaxArmSpeed(1.0);
		m_oi.setMaxDriveSpeed(0.5);
		m_oi.setMaxIndexerSpeed(0.5);
		m_oi.setMaxPivotAssistSpeed(0.05);
	}

	/** This function is called periodically during test mode. */
	@Override
	public void testPeriodic() {}
}
