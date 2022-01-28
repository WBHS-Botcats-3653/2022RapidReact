package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.MotorSafety;
import edu.wpi.first.motorcontrol.PWMMotorController;
import edu.wpi.first.motorcontrol.PWMVictorSPX;
import frc.robot.Constants;
import frc.robot.OI;

public class Intake {
	private VictorSPX intakePivot;
	private VictorSPX rollers;

	//Constructor
	public Arm() {
		//Creates VictorSPX motor controller for the arm
		intakePivot=new VictorSPX(Constants.MCID.get("Intake Pivot"));
		rollers=new VictorSPX(Constants.MCID.get("Rollers"));
	}

	//Returns the pivot motor controller
	public VictorSPX getPivotMotor() {
		return intakePivot;
	}

	public VictorSPX getRollerMotor() {
		return rollers;
	}
}
