package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
<<<<<<< Updated upstream
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
=======

>>>>>>> Stashed changes
import frc.robot.Constants;
import frc.robot.OI;

public class Intake {
	private WPI_VictorSPX intakePivot;
	private WPI_VictorSPX rollers;

	//Constructor
	public Arm() {
<<<<<<< Updated upstream
		//Creates VictorSPX motor controller for the arm
=======
		//Creates WPI_VictorSPX motor controller for the arm
>>>>>>> Stashed changes
		intakePivot=new WPI_VictorSPX(Constants.MCID.get("Intake Pivot"));
		rollers=new WPI_VictorSPX(Constants.MCID.get("Rollers"));
	}

	//Returns the pivot motor controller
	public WPI_VictorSPX getPivotMotor() {
		return intakePivot;
	}

	public WPI_VictorSPX getRollerMotor() {
		return rollers;
	}
}
