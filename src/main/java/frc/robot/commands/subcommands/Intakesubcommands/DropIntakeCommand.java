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

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DropIntakeCommand extends InstantCommand {
	
	private OI m_oi;
	private Intake m_intake;

	public DropIntakeCommand() {
		// Use addRequirements() here to declare subsystem dependencies.
		m_oi=OI.getInstance();
		m_intake=Intake.getIntake();
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		new StartEndCommand(
			()-> m_intake.dropIntake(1.0), 
			()-> m_intake.stopIntake(), 
			m_intake
		)
		.withInterrupt(() -> false /*HERE goes the conditional of the encoder to drop */);

	}
}
