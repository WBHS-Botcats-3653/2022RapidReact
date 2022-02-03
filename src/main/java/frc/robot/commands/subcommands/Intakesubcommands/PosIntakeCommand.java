// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.subcommands.Intakesubcommands;

import edu.wpi.first.wpilibj.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.OI;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PosIntakeCommand extends InstantCommand {
  private OI m_oi;
	private Intake m_intake;
  public PosIntakeCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_oi=OI.getInstance();
		m_intake=Intake.getIntake();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    new StartEndCommand(
      () -> m_intake.raiseCargo(1.0), 
      () -> m_intake.raiseCargo(0),
      m_intake
    ).withTimeout(3);
  }
}
