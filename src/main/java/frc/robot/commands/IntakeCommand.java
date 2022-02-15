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
	private OI m_oi;
	private Intake m_intake;
	private SI m_si;

	/**Creates a new IntakeCommand.
	 * @param subsystem The subsystem used by this command.
	 */
	public IntakeCommand(Intake m_intake) {
		//Initializes instance variable with OI subsystem
		m_oi = OI.getInstance();
		this.m_intake = m_intake;
		m_si = SI.getInstance();
		// Use addRequirements() here to declare subsystem dependencies.
		//addRequirements();
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		//pivot
		pivotLogic();
		//rollers
		rollerLogics();
		/*All stop called (used for testing)
		if (m_oi.getAllStop()) {  //If the B button is being pressed
			//Stop motors
			m_intake.setRollerSpeed(0);
			m_intake.setPivotSpeed(0);
			return;
		}
		*/
		/**/
	}


	public void rollerLogics(){
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

	public void pivotLogic(){
		if (m_oi.getIntakeDown()) {  //If the left trigger is being pressed
			//Pivot the intake down
			m_intake.setPivotSpeed(m_oi.getMaxIntakePivotSpeed());
		} else if (m_oi.getIntakeUp()) {  //If the left bumper is being pressed
			//Pivot the intake up
			m_intake.setPivotSpeed(-m_oi.getMaxIntakePivotSpeed());
		} else {
			//Stops the intake pivot
			m_intake.setPivotSpeed(0);
		}
	}

	public void smartPivotLogic(){
		/*
		if (m_si.getPivotUpTriggered()) {  //If the bottom pivot limit switch is being depressed
			//Stops the pivot
			m_intake.setPivotSpeed(0);
			//Intake is down
			m_oi.isIntakeDown = false;
		} else if (m_si.getPivotDownTriggered()) {  //If the bottom pivot limit switch is being depressed
			//Stops the pivot
			m_intake.setPivotSpeed(0);
			//Intake is up
			m_oi.isIntakeDown = true;
		} else if (m_oi.getIntakeDown()) {  //If the intake is currently down
			//Pivots the intake down at max speed
			m_intake.setPivotSpeed(0);
		} else if (m_oi.getIntakeUp()) {  //If the intake is currently up
			//Pivots the intake up at max speed
			m_intake.setPivotSpeed(0);
		}
		*/
		//this will execute while the button (and the intake is down)
		if(true /*the button for the smart thingi*/){
			//in here
			if(m_oi.getIntakeDown()){
				m_intake.setPivotSpeed(0);
			}
		} else if (false /*this will be when the button is getting releaced*/){
			//here the intake will go up until it presses the button.
		} 

		
		//Spins the rollers based off a combination of possible inputs (handled in OI)
		m_intake.setRollerSpeed((true /*the button for the smart thingi*/) ? 1.0 : 0);
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