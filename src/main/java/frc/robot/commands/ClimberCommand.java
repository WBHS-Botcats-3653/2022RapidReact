// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Imports OI
import frc.robot.OI;
import frc.robot.subsystems.Climber;

public class ClimberCommand extends CommandBase {
	//Holds instances of OI and Climber subsystem
	private OI m_oi;
	private Climber m_climber;
	private boolean autoClimbing = false;

	/**Creates a new ClimberCommand.
	 * @param subsystem The subsystem used by this command.
	 */
	public ClimberCommand() {
		//Initializes instance variables with instances of OI and Climber subsystem
		m_oi = OI.getInstance();
		m_climber = Climber.getInstance();
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
		if (m_oi.getEMStop()) {
			m_climber.setArmSpeed(0);
			return;
		}
		if (m_oi.POVIsUp()) {
			m_climber.setArmSpeed(m_oi.getMaxArmSpeed());
			autoClimbing = true;
		} else if (m_oi.POVIsDown()) {
			m_climber.setArmSpeed(-m_oi.getMaxArmSpeed());
			autoClimbing = true;
		} else if (m_oi.POVIsRight()) {
			m_climber.setArmSpeed(m_oi.getMaxArmSpeed());
		} else if (m_oi.POVIsLeft()) {
			m_climber.setArmSpeed(-m_oi.getMaxArmSpeed());
		} else if (!autoClimbing/*||Check if arm has reached highest point with encoder*/) {
			m_climber.setArmSpeed(0);
			if (autoClimbing) autoClimbing = false;
		}
	}
}
