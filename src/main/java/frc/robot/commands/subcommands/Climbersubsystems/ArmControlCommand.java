// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.subcommands.Climbersubsystems;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.Climber;

public class ArmControlCommand extends CommandBase {
  private Climber m_climber;

  private double speed;
  /** Creates a new ArmControlCommand. */
  public ArmControlCommand(double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climber = Climber.getClimber();
    this.speed = speed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    new StartEndCommand(
      () -> m_climber.setArmSpeed(speed),
      () -> m_climber.setArmSpeed(0),
      m_climber
    )
    .withInterrupt(() -> speed == 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
