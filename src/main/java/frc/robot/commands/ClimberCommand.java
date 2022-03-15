// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.inputs.OI;
import frc.robot.subsystems.Climber;

public class ClimberCommand extends CommandBase {
	private Climber m_climber;
	private OI m_oi;

	/**Creates a new ClimberCommand.
	 * @param subsystem The subsystem used by this command.
	 */
	public ClimberCommand(Climber p_climber) {
		m_climber = p_climber;
		m_oi = OI.getInstance();
		// Use addRequirements() here to declare subsystem dependencies.
		addRequirements(p_climber);
	}

	/*Called when the command is initially scheduled.
	 */
	@Override
	public void initialize() {}

	@Override
	// Called every time the scheduler runs while the command is scheduled.
	public void execute() {
		//Climber
		//Checks whether there is input coming in to move the climber arm
		if (m_oi.getClimbUp()) {  //If Dpad up is pressed
			//Move climber arm up
			m_climber.setClimberArmSpeed(-m_oi.getMaxClimbSpeed());
		} else if (m_oi.getClimbDown()) {  //If Dpad down is pressed
			//Move climber arm down
			m_climber.setClimberArmSpeed(m_oi.getMaxClimbSpeed());
		} else {  //Nothing being pressed (climber wise)
			//Stop climber arm
			m_climber.setClimberArmSpeed(0);
		}

		//Traversal
		//Checks whether there is input coming in to move the traversal arms
		if (m_oi.getTraverseUp()) {  //If Dpad right is pressed
			//Move traversal arms up
			m_climber.setTraversalArmsSpeed(-m_oi.getMaxClimbSpeed());
		} else if (m_oi.getTraverseDown()) {  //If Dpad left is pressed
			//Move traversal arms down
			m_climber.setTraversalArmsSpeed(m_oi.getMaxClimbSpeed());
		} else {  //Nothing being pressed (traversal wise)
			//Stop traversal arms
			m_climber.setTraversalArmsSpeed(0);
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		//Stop the climber arm
		m_climber.setClimberArmSpeed(0);
		//Stop the traversal arms
		m_climber.setTraversalArmsSpeed(0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}
