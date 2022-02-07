// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

//Imports TimedRobot
import edu.wpi.first.wpilibj.TimedRobot;
//Imports Scheduler
import edu.wpi.first.wpilibj.command.Scheduler;
//Imports Command
import edu.wpi.first.wpilibj2.command.Command;
//Imports CommandScheduler
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import frc.robot.commands.ClimberCommand;
import frc.robot.commands.IndexerCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.ShooterCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Dashboard;
//Imports DriveTrain subsystem
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
	private Command m_autonomousCommand;
	private RobotContainer m_robotContainer;

	private final DriveTrain train = DriveTrain.getInstance();
	private final Climber m_climberSubsystem = Climber.getInstance();
	private final Intake m_intakeSubsystem = Intake.getInstance();
	private final Dashboard m_dashboardSubsystem = Dashboard.getInstance();
	private final Shooter m_shooterSubsystem = Shooter.getInstance();
	private final OI m_oi = OI.getInstance();
	private final SI m_si = SI.getInstance();
	private final WPI_VictorSPX spinner = Shooter.spinner;
	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	@Override
	public void robotInit() {
		// Instantiate our RobotContainer.  This will perform all our button bindings, and put our
		// autonomous chooser on the dashboard.
		m_robotContainer = new RobotContainer();
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
		//difDrive.reset();
		// Cancels all running commands when disabled.
		CommandScheduler.getInstance().cancelAll();
		m_oi.setMaxShootSpeed(0);
		m_oi.setMaxIntakePivotSpeed(0);
		m_oi.setMaxIntakeRollerSpeed(0);
		m_oi.setMaxArmSpeed(0);
		m_oi.setMaxDriveSpeed(0);
		m_oi.setMaxIndexerSpeed(0);
	}

	@Override
	public void disabledPeriodic() {
		//Scheduler.getInstance().run();
	}

	/** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
	@Override
	public void autonomousInit() {
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
			m_autonomousCommand.schedule();
		}
		m_oi.setMaxShootSpeed(1.0);
		m_oi.setMaxIntakePivotSpeed(1.0);
		m_oi.setMaxIntakeRollerSpeed(1.0);
		m_oi.setMaxArmSpeed(1.0);
		m_oi.setMaxDriveSpeed(1.0);
		m_oi.setMaxIndexerSpeed(1.0);
	}

	/** This function is called periodically during autonomous. */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		m_oi.setMaxShootSpeed(0.5);
		m_oi.setMaxIntakePivotSpeed(0.4);
		m_oi.setMaxIntakeRollerSpeed(0.5);
		m_oi.setMaxArmSpeed(0.5);
		m_oi.setMaxDriveSpeed(0.5);
		m_oi.setMaxIndexerSpeed(0.5);
	}

	/** This function is called periodically during operator control. */
	@Override
	public void teleopPeriodic() {
		new PrintCommand("is all stop true? " + m_oi.getAllStop()).initialize();;
		train.ArcadeDrived();
		new ShooterCommand().execute();
		new IntakeCommand().execute();
		new IndexerCommand().execute();
		new ClimberCommand().execute();
		/*
		Scheduler.getInstance()
			.add(new InstantCommand(() -> m_shooterSubsystem.spinSpinner(m_oi.getShoot())));
		*/
			/* it works!!!!!!
		Scheduler.getInstance()
			.add(new InstantCommand(() -> m_intakeSubsystem.ControlIntake(m_oi.getIntakeCtrl(), false)));
*/		
		/*new ShooterCommand().schedule();
		
		
		train.ArcadeDrived();
		
		m_shooterSubsystem.spinSpinner(m_oi.getShoot());
		m_intakeSubsystem.ControlIntake(m_oi.getIntakeCtrl(), false);*/
		//this might not work
		
	}

	@Override
	public void testInit() {
		// Cancels all running commands at the start of test mode.
		CommandScheduler.getInstance().cancelAll();
		m_oi.setMaxShootSpeed(1.0);
		m_oi.setMaxIntakePivotSpeed(0.3);
		m_oi.setMaxIntakeRollerSpeed(0.5);
		m_oi.setMaxArmSpeed(0.3);
		m_oi.setMaxDriveSpeed(0.5);
		m_oi.setMaxIndexerSpeed(0.5);
	}

	/** This function is called periodically during test mode. */
	@Override
	public void testPeriodic() {

		//new PrintCommand("testing the button:" + m_si.getPivotUpTriggered()).initialize();
		//new ShooterCommand().schedule();
		train.ArcadeDrived();
		new ShooterCommand().execute();
		new IntakeCommand().execute();
		new IndexerCommand().execute();
		new ClimberCommand().execute();
	}
}
