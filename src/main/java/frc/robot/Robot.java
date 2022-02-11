// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;
import java.util.List;

//Imports Pose2d
import edu.wpi.first.math.geometry.Pose2d;
//Imports Rotation2d
import edu.wpi.first.math.geometry.Rotation2d;
//Imports Translation2d
import edu.wpi.first.math.geometry.Translation2d;
//Imports Trajectory
import edu.wpi.first.math.trajectory.Trajectory;
//Imports TrajectoryConfig
import edu.wpi.first.math.trajectory.TrajectoryConfig;
//Imports TrajectoryGenerator
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
//Imports Units
import edu.wpi.first.math.util.Units;
//Imports TimedRobot
import edu.wpi.first.wpilibj.TimedRobot;
//Imports Scheduler
import edu.wpi.first.wpilibj.command.Scheduler;
//Imports CommandScheduler
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.PrintCommand;
//Imports Commands
import frc.robot.commands.ArcadeDriveCommand;
import frc.robot.commands.AutoCommand;
import frc.robot.commands.ClimberCommand;
import frc.robot.commands.DashboardCommand;
import frc.robot.commands.IndexerCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.ShooterCommand;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
	//private Command m_autonomousCommand;
	private RobotContainer m_robotContainer;

	/*private final DriveTrain train = DriveTrain.getInstance();
	private final Climber m_climberSubsystem = Climber.getInstance();
	private final Intake m_intakeSubsystem = Intake.getInstance();
	private final Dashboard m_dashboardSubsystem = Dashboard.getInstance();
	private final Shooter m_shooterSubsystem = Shooter.getInstance();*/

	//Inputs
	private final OI m_oi = OI.getInstance();
	private final SI m_si = SI.getInstance();

	//Commands
	private AutoCommand m_autonomousCommand;
	private ArcadeDriveCommand m_arcadeDriveCommand;
	private ClimberCommand m_climberCommand;
	private IntakeCommand m_intakeCommand;
	private DashboardCommand m_dashboardCommand;
	private ShooterCommand m_shooterCommand;
	private IndexerCommand m_indexerCommand;

	//Trajectories
	public ArrayList<Trajectory> trajectories = new ArrayList<Trajectory>();
	
	/**
	 * This function is run when the robot is first started up and should be used for any
	 * initialization code.
	 */
	@Override
	public void robotInit() {
		// Instantiate our RobotContainer.  This will perform all our button bindings, and put our
		// autonomous chooser on the dashboard.
		m_robotContainer = new RobotContainer();

		//Autonomous trajectories initialization
		//Test trajectory
		trajectories.add(TrajectoryGenerator.generateTrajectory(
			new Pose2d(0, 0, Rotation2d.fromDegrees(0)),
			List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
			new Pose2d(3, 0, Rotation2d.fromDegrees(0)),
			new TrajectoryConfig(Units.feetToMeters(3.0), Units.feetToMeters(3.0))
		));
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
		//Sets max motor speeds
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
		m_autonomousCommand.setTrajectories(trajectories);
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
		//Sets max motor speeds
		m_oi.setMaxShootSpeed(1.0);
		m_oi.setMaxIntakePivotSpeed(0.50);
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
		m_arcadeDriveCommand = m_robotContainer.getArcadeDriveCommand();
		m_climberCommand = m_robotContainer.getClimberCommand();
		m_intakeCommand = m_robotContainer.getIntakeCommand();
		m_dashboardCommand = m_robotContainer.getDashboardCommand();
		m_shooterCommand = m_robotContainer.getShooterCommand();
		m_indexerCommand = m_robotContainer.getIndexerCommand();
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		//Schedule teleop commands
		m_arcadeDriveCommand.schedule();
		m_climberCommand.schedule();
		m_indexerCommand.schedule();
		m_intakeCommand.schedule();
		m_dashboardCommand.schedule();
		m_shooterCommand.schedule();
		//Sets max motor speeds
		m_oi.setMaxShootSpeed(1.0);
		m_oi.setMaxIntakePivotSpeed(0.5);
		m_oi.setMaxIntakeRollerSpeed(1.0);
		m_oi.setMaxArmSpeed(1.0);
		m_oi.setMaxDriveSpeed(1.0);
		m_oi.setMaxIndexerSpeed(1.0);
	}

	/** This function is called periodically during operator control. */
	@Override
	public void teleopPeriodic() {
		//new PrintCommand("is all stop true? " + m_oi.getAllStop()).initialize();
		/*train.ArcadeDrived();
		m_shooterCommand.run();
		m_intakeCommand.run();
		m_indexerCommand.run();
		m_climberCommand.run();*/
	}

	@Override
	public void testInit() {
		// Cancels all running commands at the start of test mode.
		CommandScheduler.getInstance().cancelAll();
		//Schedule teleop commands
		m_arcadeDriveCommand.schedule();
		m_climberCommand.schedule();
		m_indexerCommand.schedule();
		m_intakeCommand.schedule();
		m_dashboardCommand.schedule();
		m_shooterCommand.schedule();
		//Sets max motor speeds
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
		//train.ArcadeDrived();
		
		new PrintCommand("is all stop true? " + m_si.getPivotDownTriggered()).initialize();
		/*
		m_shooterCommand.initialize();
		m_intakeCommand.initialize();
		m_indexerCommand.initialize();
		m_climberCommand.initialize();
		*/
	}
}
