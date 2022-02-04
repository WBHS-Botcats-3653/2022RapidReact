// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.subcommands.intakeSubcommands;

//Imports Instant Command
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
//Imports StartEndCommand
import edu.wpi.first.wpilibj2.command.StartEndCommand;
//Imports Intake subsystem
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class RaiseIntakeCommand extends InstantCommand {
	private Intake m_intake;

	public RaiseIntakeCommand() {
	// Use addRequirements() here to declare subsystem dependencies.
		m_intake=Intake.getInstance();
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		new ScheduleCommand(
			new StartEndCommand(
			()-> m_intake.dropIntake(0.3), 
			()-> m_intake.stopIntake(), 
			m_intake
			)
			.withInterrupt(() -> false /*HERE goes the conditional of the encoder to raise */))
			.initialize();

	}
}
