// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.DrivePIDConstants.kP;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.NetworkEntries;
import frc.robot.inputs.OI;
import frc.robot.subsystems.Direction;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDriveCommand extends CommandBase {
	private Drivetrain m_drivetrain;
	private Direction m_direction = Direction.getInstance();
	private OI m_oi = OI.getInstance();

	private boolean errorCorrectionOn = false;

	/**Creates a new ArcadeDriveCommand.
	 * @param subsystem The subsystem used by this command.
	 */
	public ArcadeDriveCommand(Drivetrain p_drivetrain) {
		m_drivetrain = p_drivetrain;
		// Use addRequirements() here to declare subsystem dependencies.
		addRequirements(p_drivetrain);
	}

	/*Called when the command is initially scheduled.
	 */
	@Override
	public void initialize() {}
	
	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//Gets the throttle and steering from the controller
		double throttle = m_oi.getThrottle();
		double steering = m_oi.getSteering();
		//If drive error correction is enabled and the controller is not giving any steering input
		if (NetworkEntries.isErrorCorrectionEnabled() && steering == 0 && throttle > 0.2) {
			//If not already correcting drive errors
			if (!errorCorrectionOn) {
				//Reset the drive encoders
				m_direction.resetEncoders();
				//Now correcting drive errors
				errorCorrectionOn = true;
			}
			//Calculates the steering to keep the robot straight
			steering = kP * m_direction.getEncoderError();
		} else {  //Drive error correction is disabled or player is giving steering input or the robot is not moving at a high enough speed
			//Not correcting drive errors
			errorCorrectionOn = false;
		}
		//Drives the robot
		m_drivetrain.arcadeDrive(throttle, steering);
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		//Stop the drive train
		m_drivetrain.arcadeDrive(0, 0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}
