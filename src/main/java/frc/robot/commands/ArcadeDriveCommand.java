// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.DriveConstants.kP;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.NetworkEntries;
import frc.robot.inputs.OI;
import frc.robot.subsystems.Direction;
import frc.robot.subsystems.DriveTrain;

public class ArcadeDriveCommand extends CommandBase {
	private DriveTrain m_driveTrain;
	private Direction m_direction;
	private OI m_oi;

	private boolean errorCorrectionOn = false;

	/**Creates a new ArcadeDriveCommand.
	 * @param subsystem The subsystem used by this command.
	 */
	public ArcadeDriveCommand(DriveTrain p_driveTrain) {
		m_driveTrain = p_driveTrain;
		m_direction = Direction.getInstance();
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
		//Gets the throttle from the controller
		double throttle = m_oi.getThrottle();
		//Gets the steering from the controller
		double steering = m_oi.getSteering();
		//If drive error correction is enabled and the controller is not giving any steering input
		if (NetworkEntries.isErrorCorrectionEnabled() && steering == 0 && throttle !=0) {
			//If not already correcting drive errors
			if (!errorCorrectionOn) {
				//Reset the drive encoders
				m_direction.resetEncoders();
				//Now correcting errros
				errorCorrectionOn = true;
			}
			//Calculates the steering to keep the robot straight
			steering = kP * m_direction.getError();
			//Ensures the steering is never more than 1 (or less than -1)
			if (Math.abs(steering) > 1.0) steering = 1.0 * steering < 0 ? -1 : 1;
		} else {  //Drive error correction is disabled or player is giving steering input or the robot is not moving
			//Not correcting drive error
			errorCorrectionOn = false;
		}
		//Drives the robot
		m_driveTrain.arcadeDrived(throttle, steering);
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
