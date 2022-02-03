// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.subcommands.Intakesubcommands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
//Import OI
import frc.robot.OI;
//Import Intake subsystem
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Storage;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class IntakeControlCommand extends ParallelCommandGroup {
	/** Creates a new IntakeControlCommand. */
	private OI m_oi;
	private Intake m_intake;
	private Storage m_storage;

	/**this command will execute the first part of the intake.
	 * 
	 * this command was created to divide the work of the intake into 2 separate subcommands, 
	 * thus making it easier to find any errors.
	 */
	public IntakeControlCommand() {

		//Initializes instance variables with OI and Intake subsystem
		m_oi=OI.getInstance();
		m_intake=Intake.getIntake();
		m_storage=Storage.getStorage();
		
		// Add your commands in the addCommands() call, e.g.
		// addCommands(new FooCommand(), new BarCommand());
		addCommands(
			new DropIntakeCommand(), 
			new StartEndCommand(
				() -> m_intake.spinRollers(1.0),
				() -> m_intake.spinRollers(0),
				m_intake
			),
			new InstantCommand(
				() -> m_storage.raiseCargo(1.0),
				m_storage
			)
		);
	}
}
