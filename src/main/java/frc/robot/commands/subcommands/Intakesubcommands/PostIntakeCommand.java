// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.subcommands.intakeSubcommands;

//Imports InstantCommand
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
//Imports StartEndCommand
import edu.wpi.first.wpilibj2.command.StartEndCommand;
//Imports Storage subsystem
import frc.robot.subsystems.Indexer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PostIntakeCommand extends InstantCommand {
	private Indexer m_storage;

	public PostIntakeCommand() {
		// Use addRequirements() here to declare subsystem dependencies.
		m_storage = Indexer.getInstance();
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		new ScheduleCommand(
			new StartEndCommand(() -> m_storage.raiseCargo(1.0), () -> m_storage.raiseCargo(0), m_storage).withTimeout(3)
		).initialize();
	}
}
