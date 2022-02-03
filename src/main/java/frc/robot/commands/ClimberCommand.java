// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Imports OI
import frc.robot.OI;
import frc.robot.commands.subcommands.Climbersubsystems.LowerArmCommand;
import frc.robot.commands.subcommands.Intakesubcommands.RaiseIntakeCommand;
//Imports Climber subsystem

public class ClimberCommand extends CommandBase {
	//Holds instances of OI and Climber subsystem
	private OI m_oi;

	/**Creates a new ClimberCommand.
	 * @param subsystem The subsystem used by this command.
	 */
	public ClimberCommand() {
		//Initializes instance variables with instances of OI and Climber subsystem
		m_oi=OI.getInstance();
		// Use addRequirements() here to declare subsystem dependencies.
		//addRequirements();
	}

	/*Called when the command is initially scheduled.
	 */
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	public void execute() {

		if (m_oi.POVIsUp()) {
			//Fully extends the arm
			new RaiseIntakeCommand();
		} else if (m_oi.POVIsDown()) {
			//Lowers the arm completely
			new LowerArmCommand();
		} /*else if (m_oi.POVIsRight()) {
			//Raises the arm
			m_climber.raiseArm(m_oi.getMaxArmSpeed());
		} else if (m_oi.POVIsLeft()) {
			//Lowers the arm
			m_climber.lowerArm(m_oi.getMaxArmSpeed());
		}*/
	}

	// Called once the command ends or is interrupted.
	public void end(boolean interrupted) {}

	// Returns true when the command should end.
	public boolean isFinished() {
		return false;
	}
}
