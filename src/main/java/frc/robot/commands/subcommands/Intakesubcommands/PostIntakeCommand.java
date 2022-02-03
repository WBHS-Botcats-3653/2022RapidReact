// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.subcommands.Intakesubcommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
//Imports OI
import frc.robot.OI;
//Imports Intake subsystem
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Storage;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PostIntakeCommand extends InstantCommand {
	private OI m_oi;
	private Intake m_intake;
	private Storage m_storage;

	public PostIntakeCommand() {
		// Use addRequirements() here to declare subsystem dependencies.
		m_oi=OI.getInstance();
		m_intake=Intake.getIntake();
		m_storage=Storage.getStorage();
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		new StartEndCommand(
			() -> m_storage.raiseCargo(1.0), 
			() -> m_storage.raiseCargo(0),
			m_storage
		).withTimeout(3);
	}
}
