// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.inputs.OI;
import frc.robot.inputs.SI;
//Imports Climber subsystem
import frc.robot.subsystems.Climber;

public class ClimberCommand extends CommandBase {
	private Climber m_climber;
	private OI m_oi;
	private SI m_si;

	/**Creates a new ClimberCommand.
	 * @param subsystem The subsystem used by this command.
	 */
	public ClimberCommand(Climber p_climber) {
		m_climber = p_climber;
		m_oi = OI.getInstance();
		m_si = SI.getInstance();
		// Use addRequirements() here to declare subsystem dependencies.
		//addRequirements();
	}

	/*Called when the command is initially scheduled.
	 */
	@Override
	public void initialize() {}

	@Override
	// Called every time the scheduler runs while the command is scheduled.
	public void execute() {
		//Checks whether there is input coming in to move the arm
		if (m_oi.getClimbUp()) {  //If up Dpad up is pressed
			//Move arm up
			m_climber.setArmSpeed(-m_oi.getMaxArmSpeed());  //Climber motor is inverted
		} else if (m_si.getClimberDownLimitTriggered()) {  //If the button on the bottom if the climber is pressed
			//Stop arm
			m_climber.setArmSpeed(0);
		} else if (m_oi.getClimbDown()) {  //If Dpad down is pressed
			//Move arm down
			m_climber.setArmSpeed(m_oi.getMaxArmSpeed());  //Climber motor is inverted
		} else {  //Nothing being pressed (climber wise)
			//Stop arm
			m_climber.setArmSpeed(0);
		}
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
