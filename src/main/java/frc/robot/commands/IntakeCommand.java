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
		//Initializes instance variable with OI subsystem
		m_oi = OI.getInstance();
		this.m_intake = m_intake;
		m_si = SI.getInstance();
		smartControl = false;
		// Use addRequirements() here to declare subsystem dependencies.
		//addRequirements();
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//Smart intake control
		smartLogic();
		//Manual controls
		if (!smartControl) {  //If smart controls are not currently being used
			//Intake pivot controls
			pivotLogic();
			//Intake roller controls
			rollerLogic();
		}

		/*Delete later
		All stop called (used for testing)
		if (m_oi.getAllStop()) {  //If the B button is being pressed
			//Stop motors
			m_intake.setRollerSpeed(0);
			m_intake.setPivotSpeed(0);
			return;
		}
		*/
	}

	//Pivot OI logic
	public void pivotLogic() {
		if (m_oi.getIntakeDown()) {  //If the left trigger is being pressed
			//Pivot the intake down
			m_intake.setPivotSpeed(m_oi.getMaxIntakePivotSpeed());
		} else if (m_oi.getIntakeUp()) {  //If the left bumper is being pressed
			//Pivot the intake up
			m_intake.setPivotSpeed(-m_oi.getMaxIntakePivotSpeed());
		} else {  //Nothing being pressed (intake pivot wise)
			//Stops the intake pivot
			m_intake.setPivotSpeed(0);
		}
	}

	//Roller OI logic
	public void rollerLogic() {
		if (m_oi.getIntakeIn()) {  //If the A button is being pressed
			//Spin the rollers at max speed
			m_intake.setRollerSpeed(m_oi.getMaxIntakeRollerSpeed());
		} else if (m_oi.getIntakeOut()) {  //If the X button is being pressed
			//Reverse the rollers and spin at max speed
			m_intake.setRollerSpeed(-m_oi.getMaxIntakeRollerSpeed());
		} else {  //Nothing being pressed (intake roller wise)
			//Stops the rollers
			m_intake.setRollerSpeed(0);
		}
	}

	//Smart control logic (button down-pivot down, button pressed-spin rollers, button released-pivot up)
	public void smartLogic() {
		if (m_oi.getSmartIntakeUp() && !m_si.getPivotUpLimitTriggered()) {  //If the smart intake is being called to go up
			//Pivots the intake up at the set max speed
			m_intake.setPivotSpeed(-m_oi.getMaxIntakePivotSpeed());
			//Stops the intake rollers
			m_intake.setRollerSpeed(0);
			smartControl = true;
		} else if (m_oi.getSmartIntakeDown() && !m_si.getPivotDownLimitTriggered()) {  //If the smart intake is being called to go down
			//Pivots the intake down at the set max speed
			m_intake.setPivotSpeed(m_oi.getMaxIntakePivotSpeed());
			//Spins the intake rollers at the set max speed
			m_intake.setRollerSpeed(m_oi.getMaxIntakeRollerSpeed());
			smartControl = true;
		} else if (m_si.getPivotDownLimitTriggered() || m_si.getPivotUpLimitTriggered()) {  //If either of the pivot limit switches are triggered
			//Stops the intake pivot
			m_intake.setPivotSpeed(0);
			smartControl = false;
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