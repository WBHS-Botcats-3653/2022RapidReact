// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.inputs.OI;
import frc.robot.subsystems.DriveTrain;

public class ArcadeDriveCommand extends CommandBase {
	private DriveTrain m_driveTrain;
	private OI m_oi;

	/**Creates a new ArcadeDriveCommand.
	 * @param subsystem The subsystem used by this command.
	 */
	public ArcadeDriveCommand(DriveTrain p_driveTrain) {
		m_driveTrain = p_driveTrain;
		m_oi = OI.getInstance();
		// Use addRequirements() here to declare subsystem dependencies.
		addRequirements(p_driveTrain);
	}

	/*Called when the command is initially scheduled.
	 */
	@Override
	public void initialize() {}
	
	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//Drives the robot
		m_driveTrain.arcadeDrived(m_oi.getThrottle(), m_oi.getSteering());
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		//Stop the drive train
		m_driveTrain.arcadeDrived(0, 0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}
