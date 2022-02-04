// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
//Imports OI
import frc.robot.OI;
import frc.robot.commands.subcommands.intakeSubcommands.IntakeControlCommand;
import frc.robot.commands.subcommands.intakeSubcommands.PostIntakeCommand;
import frc.robot.commands.subcommands.intakeSubcommands.RaiseIntakeCommand;


public class IntakeCommand extends CommandBase {
	//Holds instances of OI and Intake subsystem
	private OI m_oi;

	/**Creates a new IntakeCommand.
	 * @param subsystem The subsystem used by this command.
	 */
	public IntakeCommand() {
		//Initializes instance variable with OI subsystem
		m_oi = OI.getInstance();
		// Use addRequirements() here to declare subsystem dependencies.
		//addRequirements();
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		if (m_oi.getIntakeDown()) {
			//Drops the Intake
			new ScheduleCommand(new IntakeControlCommand()).initialize();
		} else if (m_oi.getIntakeUp()) {
			//Raises the intake
			new ScheduleCommand(new RaiseIntakeCommand().andThen(new PostIntakeCommand())).initialize();
		}
	}
}