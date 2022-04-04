// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.IntakeConstants.*;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.NetworkEntries;
import frc.robot.inputs.OI;
import frc.robot.inputs.SI;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {
	private Intake m_intake;
	private final OI m_oi = OI.getInstance();
	private final SI m_si = SI.getInstance();

	private static boolean smartControl = false;
	private static boolean smartPivotGoingUp = false;
	private static boolean smartPivotGoingDown = false;

	/**Creates a new IntakeCommand.
	 * @param subsystem The subsystem used by this command.
	 */
	public IntakeCommand(Intake p_intake) {
		m_intake = p_intake;
		//Not pivoting up while under smart control
		smartPivotGoingUp = false;
		addRequirements(p_intake);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//If the smart intake is enabled in the shuffleboard
		if (NetworkEntries.isSmartIntakeEnabled()) {
			//Smart intake control
			smartIntakeLogic();
		}
		//Manual controls
		if (!smartControl) {  //If smart controls are not currently being used
			//Intake pivot controls
			manualPivotLogic();
			//Intake roller controls
			manualRollerLogic();
		}
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		//End the smart intake
		endSmartIntake();
		//Stop the intake pivot
		m_intake.setPivotSpeed(0);
		//Stops the intake rollers
		m_intake.setRollerSpeed(0);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}

	//Manual pivot OI logic
	public void manualPivotLogic() {
		if (m_oi.getManualIntakeDown()) {  //If the intake down button is being pressed
			//Pivot the intake down
			m_intake.setPivotSpeed(m_oi.getMaxPivotSpeed());
		} else if (m_oi.getManualIntakeUp()) {  //If the intake up button is being pressed
			//Pivot the intake up
			m_intake.setPivotSpeed(-m_oi.getMaxPivotSpeed());
		} else {  //Nothing being pressed (intake pivot wise)
			//Stops the intake pivot
			m_intake.setPivotSpeed(0);
		}
	}

	//Manual roller OI logic
	public void manualRollerLogic() {
		if (m_oi.getManualIntakeIn()) {  //If the intake in button is being pressed
			//Spin the rollers at max speed
			m_intake.setRollerSpeed(m_oi.getMaxRollerSpeed());
			//Pivot assist (pivots the intake down at a low speed when spinning the rollers)
			if (NetworkEntries.isPivotAssistEnabled()) {  //If pivot assist is enabled in the Dashboard
				//Sets the pivot speed to max assist speed
				m_intake.setPivotSpeed(kPivotAssistSpeed);
			}
		} else if (m_oi.getManualIntakeOut()) {  //If the intake out button is being pressed
			//Reverse the rollers and spin at max speed
			m_intake.setRollerSpeed(-m_oi.getMaxRollerSpeed());
		} else {  //Nothing being pressed (intake roller wise)
			//Stops the rollers
			m_intake.setRollerSpeed(0);
		}
	}

	//Smart control pivot/roller logic (button down-pivot down, button pressed-spin rollers, button released-pivot up)
	public void smartIntakeLogic() {
		//If the smart intake is being called to go down and the bottom pivot limit switch is open
		if (m_oi.getSmartIntakeDown() && !m_si.isPivotDownLimitClosed()) {
			//Pivots the intake down at the set max speed
			m_intake.setPivotSpeed(kSmartPivotDownSpeed);
			//Prevents manual control from interferring with smart control
			smartControl = true;
			//Initial pivot down started
			smartPivotGoingDown = true;
			//Stop upward pivot
			smartPivotGoingUp = false;
		}
		//If the smart intake is being called to go up and the top pivot limit switch is open
		if (m_oi.getSmartIntakeUp() && smartControl && !m_si.isPivotUpLimitClosed()) {
			//Pivots the intake up at the set max speed
			m_intake.setPivotSpeed(-kSmartPivotUpSpeed);
			//Initial pivot up started
			smartPivotGoingUp = true;
			//Stop downward pivot
			smartPivotGoingDown = false;
		}
		//If either of the pivot limit switches are closed
		if (m_si.isPivotDownLimitClosed() || m_si.isPivotUpLimitClosed()) {
			//If the smart intake is being pressed (incase the intake has already been manually moved down)
			if (m_oi.getSmartIntakeDown()) {
				//Prevents manual control from interferring with smart control
				smartControl = true;
				//Stop upward pivot
				smartPivotGoingUp = false;
			}
			//If smart control has priority over manual controls
			if (smartControl) {
				//If the intake is not pivoting up or down
				if (!smartPivotGoingUp && !smartPivotGoingDown) {
					//If pivot assist is enabled
					if (NetworkEntries.isPivotAssistEnabled()) {
						//Pivots the intake down at the set pivot assist speed
						m_intake.setPivotSpeed(kPivotAssistSpeed);
					} else {  //If pivot assist is disabled
						//Stops the intake pivot
						m_intake.setPivotSpeed(0);
					}
				}
				//If the intake has lifted off the bottom pivot limit switch
				if (smartPivotGoingUp && m_si.isPivotUpLimitClosed()) {
					//Allows manual control to take over from smart control
					smartControl = false;
					//Smart pivot has made initial upward pivot
					smartPivotGoingUp = false;
				}
			}
		}
		//Rollers
		if (smartControl && !smartPivotGoingUp && !m_si.isPivotUpLimitClosed()) {  //If smart control has priority over manual controls and the intake is not up and is not pivoting up
			//Sets the speed of the intake rollers based off whether the left bumper is being pressed
			m_intake.setRollerSpeed(m_oi.getMaxRollerSpeed());
		} else {  //If smart control does not have priority over manual controls or the intake is up or is pivoting up
			//Stops the intake rollers
			m_intake.setRollerSpeed(0);
		}
	}

	
	//If the end smart intake button on the shuffleboard is pressed, end the smart intake
	public static void endSmartIntake() {
		//Switches from smart control to manual
		smartControl = false;
		smartPivotGoingUp = false;
		smartPivotGoingDown = false;
		Intake intake = Intake.getInstance();
		//Stops the intake pivot
		intake.setPivotSpeed(0);
		//Stops the intake rollers
		intake.setRollerSpeed(0);
	}

	//Returns whether the intake in under smart control
	public static boolean isUnderSmartControl() {
		//Returns if the intake is currently in a smart control period
		return smartControl;
	}

	//Returns whether the intake is ending it's smart control period
	public static boolean isEndingSmartControl() {
		//Returns if the intake is ending it's smart control period by returning if the intake is pivoting up
		return smartPivotGoingUp;
	}
}