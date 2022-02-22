// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.inputs.OI;
import frc.robot.inputs.SI;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {
	//Holds instances of OI and Intake subsystem
	private Intake m_intake;
	private Indexer m_indexer;
	private OI m_oi;
	private SI m_si;
	private boolean smartControl;
	private boolean smartPivotGoingUp;
	private boolean smartPivotGoingDown;

	/**Creates a new IntakeCommand.
	 * @param subsystem The subsystem used by this command.
	 */
	public IntakeCommand(Intake p_intake) {
		m_intake = p_intake;
		m_indexer = Indexer.getInstance();
		m_oi = OI.getInstance();
		m_si = SI.getInstance();
		//Not in smart control
		smartControl = false;
		//Not pivoting up while under smart control
		smartPivotGoingUp = false;
		//Not pivoting down while under smart control
		smartPivotGoingDown = false;
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
		} else {  //Smart indexer
			//Smart indexer control
			smartIndexerLogic();
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
			m_intake.setPivotSpeed(-m_oi.getMaxSmartIntakePivotUpSpeed());
			//Stops the intake rollers
			m_intake.setRollerSpeed(0);
			//Initial pivot up started
			smartPivotGoingUp = true;
		} else if (m_oi.getSmartIntakeDown() && !m_si.getPivotDownLimitTriggered()) {  //If the smart intake is being called to go down and the bottom pivot limit switch is not being triggered
			//Pivots the intake down at the set max speed
			m_intake.setPivotSpeed(m_oi.getMaxSmartIntakePivotDownSpeed());
			//Prevents manual control from interferring with smart control
			smartControl = true;
			//Initial pivot down started
			smartPivotGoingDown = true;
		} else if (m_si.getPivotDownLimitTriggered() || m_si.getPivotUpLimitTriggered()) {  //If either of the pivot limit switches are triggered
			//Checks if the smart intake is being pressed incase the intake has already been manually moved down
			if (!smartControl && m_oi.getSmartIntakeDown()) {  //If the smart intake is being pressed
				//Prevents manual control from interferring with smart control
				smartControl = true;
			}
			if (m_si.getPivotUpLimitTriggered()) {  //If the top pivot limit switch is being triggered
				//Stops the intake rollers
				m_intake.setRollerSpeed(0);
				//Allows manual control to take over from smart control
				smartControl = false;
			}
			//If the intake has lifted off the bottom pivot limit switch
			if (smartPivotGoingUp && !m_si.getPivotDownLimitTriggered()) {
				//Stops the intake rollers
				m_intake.setRollerSpeed(0);
				//Allows manual control to take over from smart control
				smartControl = false;
				//Smart pivot has made initial upward pivot
				smartPivotGoingUp = false;
			}
			//If the intake has lifted off the top pivot limit switch
			if (smartPivotGoingDown && !m_si.getPivotUpLimitTriggered()) {
				//Smart pivot has made initial upward pivot
				smartPivotGoingDown = false;
				//Spins the intake rollers at max speed
				m_intake.setRollerSpeed(m_oi.getMaxIntakeRollerSpeed());
			}
			//If the intake isn't going up
			if (!smartPivotGoingUp || m_si.getPivotUpLimitTriggered()) {
				//Stops the intake pivot
				m_intake.setPivotSpeed(0);
			}
		}
		//Rollers
		if (smartControl) {  //If smart control has priority over manual controls
			//Sets the speed of the intake rollers based off whether the left bumper is being pressed
			m_intake.setRollerSpeed(m_oi.getMaxIntakeRollerSpeed());
		}
	}

	//Smart control indexer logic
	public void smartIndexerLogic() {
		if (m_si.getUpperStorageTriggered()) {  //If there is cargo in the lower storage
			//Stops the indexer
			m_indexer.setIndexerSpeed(0);
		} else if (m_si.getLowerStorageTriggered()) {  //If there is cargo in the upper storage
			//Sets the indexer to max speed
			m_indexer.setIndexerSpeed(m_oi.getMaxIndexerSpeed());
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