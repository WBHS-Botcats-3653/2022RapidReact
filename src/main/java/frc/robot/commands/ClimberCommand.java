// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.inputs.OI;
import frc.robot.subsystems.Climber;

public class ClimberCommand extends CommandBase {
	private Climber m_climber;
	private final OI m_oi = OI.getInstance();

	/**Creates a new ClimberCommand.
	 * @param subsystem The subsystem used by this command.
	 */
	public ClimberCommand(Climber p_climber) {
		m_climber = p_climber;
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
		if (m_oi.getClimbUp()) {  //If climber up button is pressed
			//Move climber arm up
			m_climber.setClimberArmSpeed(-m_oi.getMaxClimbSpeed());
		} else if (m_oi.getClimbDown()) {  //If climber down button is pressed
			//Move climber arm down
			m_climber.setClimberArmSpeed(m_oi.getMaxClimbSpeed());
		} else {  //Nothing being pressed (climber wise)
			//Stop climber arm
			m_climber.setClimberArmSpeed(0);
		}

		//Extender winch
		//Checks whether there is input coming in to move the extender
		if (m_oi.getExtenderWinchClockwise()) {  //If extender winch clockwise button is pressed
			//Winch extender clockwise
			m_climber.setExtenderWinchSpeed(m_oi.getMaxExtenderWinchSpeed());
		} else if (m_oi.getExtenderWinchCounterclockwise()) {  //If extender winch counterclockwise button is pressed
			//Winch extender counterclockwise
			m_climber.setExtenderWinchSpeed(-m_oi.getMaxExtenderWinchSpeed());
		} else {  //Nothing being pressed (extender wise)
			//Stop the extender winch
			m_climber.setExtenderWinchSpeed(0);
		}

		//Hook winch
		if (m_oi.getHookWinchClockwise()) {  //If the hook winch clockwise button is being pressed
			//Winch the hook clockwise
			m_climber.setHookWinchSpeed(m_oi.getMaxHookWinchSpeed());
		} else if (m_oi.getHookWinchCounterclockwise()) {  //If the hook winch counterclockwise button is being pressed
			//Winch the hook counterclockwise
			m_climber.setHookWinchSpeed(-m_oi.getMaxHookWinchSpeed());
		} else {  //Nothing being pressed (winch wise)
			//Stop the hook winch
			m_climber.setHookWinchSpeed(0);
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		//Stop the climber arm
		m_climber.setClimberArmSpeed(0);
		//Stop the extender winch
		m_climber.setExtenderWinchSpeed(0);
		//Stop the hook wench
		m_climber.setHookWinchSpeed(0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}
