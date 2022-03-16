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
		//Climber arm
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

		//Traversal arm
		//Checks whether there is input coming in to move the traversal arm
		if (m_oi.getTraverseUp()) {  //If Dpad right is pressed
			//Move traversal arm up
			m_climber.setTraversalArmSpeed(-m_oi.getMaxClimbSpeed());
		} else if (m_oi.getTraverseDown()) {  //If Dpad left is pressed
			//Move traversal arm down
			m_climber.setTraversalArmSpeed(m_oi.getMaxClimbSpeed());
		} else {  //Nothing being pressed (traversal wise)
			//Stop traversal arm
			m_climber.setTraversalArmSpeed(0);
		}

		//Traversal Wench
		if (m_oi.getWench()) {  //If the back button is being pressed
			//Wench the traversal thing
			m_climber.setTraversalWenchSpeed(m_oi.getMaxWenchSpeed());
		} else {  //If the back button is not being pressed
			//Stop the traversal wench
			m_climber.setTraversalArmSpeed(0);
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		//Stop the climber arm
		m_climber.setClimberArmSpeed(0);
		//Stop the traversal arm
		m_climber.setTraversalArmSpeed(0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}
