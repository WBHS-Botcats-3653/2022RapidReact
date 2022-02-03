// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

<<<<<<< HEAD
import edu.wpi.first.wpilibj.command.InstantCommand;
//Import CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
=======
//Import InstantCommand
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
>>>>>>> 6e7fcb9adaee3acbe950271b4fd4b8f72ea06ea2
//Import OI
import frc.robot.OI;
import frc.robot.commands.subcommands.Indexersubcommands.IndexerCommand;
//Import Shooter subsystem
import frc.robot.subsystems.Shooter;

public class ShooterCommand extends ParallelCommandGroup {
	//Holds instances of OI and Shooter subsystem
	private OI m_oi;
	private Shooter m_shooter;
	private double shoot;

	public ShooterCommand() {
		//Initializes instance variables with OI and Shooter subsystem
		m_oi=OI.getInstance();
		m_shooter = Shooter.getShooter();
		// Use addRequirements() here to declare subsystem dependencies.
		//addRequirements();
	}

	// Called when the command is initially scheduled.
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	public void execute() {
<<<<<<< HEAD
		//m_shooter.spinSpinner(m_oi.getShoot());
		shoot = m_oi.getShoot();
=======
>>>>>>> 6e7fcb9adaee3acbe950271b4fd4b8f72ea06ea2
		addCommands(
			new RunCommand(
				() -> m_shooter.spinSpinner(shoot),
					m_shooter
<<<<<<< HEAD
			)
			.withInterrupt(() -> m_oi.getShoot() == 0),
			new IndexerCommand().withInterrupt(() -> m_oi.getShoot() == 0)
=======
			),
			new IndexerCommand()
>>>>>>> 6e7fcb9adaee3acbe950271b4fd4b8f72ea06ea2
		);
	}

	// Called once the command ends or is interrupted.
	public void end(boolean interrupted) {}

	// Returns true when the command should end.
	public boolean isFinished() {
		return false;
	}
}