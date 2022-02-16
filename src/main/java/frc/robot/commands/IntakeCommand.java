// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//Imports CommandBase
import edu.wpi.first.wpilibj2.command.CommandBase;
//Imports OI
import frc.robot.OI;
//Imports SI
import frc.robot.SI;
//Imports Intake
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {
	//Holds instances of OI and Intake subsystem
	private Intake m_intake;
	private OI m_oi;
	private SI m_si;
	private boolean smartControl;

	/**Creates a new IntakeCommand.
	 * @param subsystem The subsystem used by this command.
	 */
	public IntakeCommand(Intake m_intake) {
		this.m_intake = m_intake;
		m_oi = OI.getInstance();
		m_si = SI.getInstance();
		smartControl = false;
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//Smart intake control
		smartIntakeLogic();
		//Manual controls
		if (!smartControl) {  //If smart controls are not currently being used
			//Intake pivot controls
			manualPivotLogic();
			//Intake roller controls
			manualRollerLogic();
		}
	}

	//Manual pivot OI logic
	public void manualPivotLogic() {
		if (m_oi.getManualIntakeDown()) {  //If the left trigger is being pressed
			//Pivot the intake down
			m_intake.setPivotSpeed(m_oi.getMaxIntakePivotSpeed());
		} else if (m_oi.getManualIntakeUp()) {  //If the left bumper is being pressed
			//Pivot the intake up
			m_intake.setPivotSpeed(-m_oi.getMaxIntakePivotSpeed());
		} else {  //Nothing being pressed (intake pivot wise)
			//Stops the intake pivot
			m_intake.setPivotSpeed(0);
		}
	}

	//Manual roller OI logic
	public void manualRollerLogic() {
		if (m_oi.getManualIntakeIn()) {  //If the A button is being pressed
			//Spin the rollers at max speed
			m_intake.setRollerSpeed(m_oi.getMaxIntakeRollerSpeed());
		} else if (m_oi.getManualIntakeOut()) {  //If the X button is being pressed
			//Reverse the rollers and spin at max speed
			m_intake.setRollerSpeed(-m_oi.getMaxIntakeRollerSpeed());
		} else {  //Nothing being pressed (intake roller wise)
			//Stops the rollers
			m_intake.setRollerSpeed(0);
		}
	}

	//Smart control pivot/roller logic (button down-pivot down, button pressed-spin rollers, button released-pivot up)
	public void smartIntakeLogic() {
		if (m_oi.getSmartIntakeUp() && !m_si.getPivotUpLimitTriggered()) {  //If the smart intake is being called to go up and the top pivot limit switch is not being triggered
			//Pivots the intake up at the set max speed
			m_intake.setPivotSpeed(-m_oi.getMaxSmartIntakePivotSpeed());
			//Allows manual control to take over from smart control
			smartControl = false;
		} else if (m_oi.getSmartIntakeDown() && !m_si.getPivotDownLimitTriggered()) {  //If the smart intake is being called to go down and the bottom pivot limit switch is not being triggered
			//Pivots the intake down at the set max speed
			m_intake.setPivotSpeed(m_oi.getMaxSmartIntakePivotSpeed());
			//Prevents manual control from interferring with smart control
			smartControl = true;
			//Don't set roller speed until the downward pivot has already started. This is to avoid damage to the robot from the rollers
			return;
		} else if (m_si.getPivotDownLimitTriggered() || m_si.getPivotUpLimitTriggered()) {  //If either of the pivot limit switches are triggered
			//Checks if the smart intake is being pressed incase the intake has already been manually moved down
			if (m_oi.getSmartIntakeDown()) {  //If the smart intake is being pressed
				//Prevents manual control from interferring with smart control
				smartControl = true;
			}
			if (m_si.getPivotUpLimitTriggered()) {  //If the top pivot limit switch is being triggered
				//Allows manual control to take over from smart control
				smartControl = false;
			}
			//Stops the intake pivot
			m_intake.setPivotSpeed(0);
		}
		//Sets the speed of the intake rollers based off whether the left bumper is being pressed
		m_intake.setRollerSpeed(m_oi.getSmartIntakeDown() ? 1.0 : 0);
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