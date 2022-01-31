// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//Import CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Import OI
import frc.robot.OI;
//Import Climber subsystem
import frc.robot.subsystems.Climber;

public class ClimberCommand extends CommandBase {
	//Holds instances of OI and Climber subsystem
	private OI m_oi;
	private Climber m_climber;

	/**
	 * Creates a new ClimberCommand.
	 *
	 * @param subsystem The subsystem used by this command.
	 */
	public ClimberCommand() {
		//Initializes instance variables with instances of OI and Climber subsystem
		m_oi=OI.getInstance();
		m_climber=Climber.getClimber();
		// Use addRequirements() here to declare subsystem dependencies.
		//addRequirements();
	}

	// Called when the command is initially scheduled.
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	public void execute() {
		if (/*OI HERE*/ false) {
			//Fully extends the arm
			m_climber.raiseArm();
		} else if (/*OI HERE*/ true) {
			//Lowers the arm completely
			m_climber.raiseArm();
		} else {
			m_climber.lowerArm(/*OI HERE*/);
		}
	}

	// Called once the command ends or is interrupted.
	public void end(boolean interrupted) {}

	// Returns true when the command should end.
	public boolean isFinished() {
		return false;
	}
}
