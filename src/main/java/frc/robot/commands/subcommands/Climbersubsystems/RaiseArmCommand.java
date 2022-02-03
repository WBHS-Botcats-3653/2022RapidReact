// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.subcommands.Climbersubsystems;

//Imports InstantCommand
import edu.wpi.first.wpilibj2.command.InstantCommand;
//Imports OI
import frc.robot.OI;
//Imports Climber subsystems
//import frc.robot.subsystems.Climber;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class RaiseArmCommand extends InstantCommand {
	//private Climber m_climber;
	private OI m_oi;
	
	public RaiseArmCommand() {
		// Use addRequirements() here to declare subsystem dependencies.
		//m_climber = Climber.getClimber();
		m_oi = OI.getInstance();
	}
	
	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		new ArmControlCommand(m_oi.getMaxArmSpeed())
		.withInterrupt(
			() -> false /*this will depend with the encoder*/
		);
	}
}
