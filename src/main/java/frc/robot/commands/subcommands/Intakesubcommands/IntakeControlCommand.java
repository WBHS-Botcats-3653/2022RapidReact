// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.subcommands.Intakesubcommands;

//Imports InstantCommand
import edu.wpi.first.wpilibj2.command.InstantCommand;
//Imports ParallelCommandGroup
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
//Imports StartEndCommand
import edu.wpi.first.wpilibj2.command.StartEndCommand;
//Imports OI
import frc.robot.OI;
//Imports Intake subsystem
import frc.robot.subsystems.Intake;
//Imports Storage subsystem
import frc.robot.subsystems.Indexer;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeControlCommand extends ParallelCommandGroup {
	//Creates a new IntakeControlCommand
	private Intake m_intake;
	private Indexer m_storage;
	private OI m_oi;

	/*this command will execute the first part of the intake.
	 * this command was created to divide the work of the intake into 2 separate subcommands, 
	 * thus making it easier to find any errors.
	 */
	public IntakeControlCommand() {
		//Initializes instance variables with Intake and Storage subsystems
		m_intake = Intake.getInstance();
		m_storage = Indexer.getInstance();
		m_oi = OI.getInstance();
		double intakeSpeed = m_oi.getIntakeCtrl();
		// Add your commands in the addCommands() call, e.g.
		// addCommands(new FooCommand(), new BarCommand());
		addCommands(
			new DropIntakeCommand(), 
			new StartEndCommand(() -> m_intake.spinRollers(intakeSpeed), () -> m_intake.spinRollers(intakeSpeed), m_intake),
			new InstantCommand(() -> m_storage.raiseCargo(intakeSpeed), m_storage)
		);
	}
}
