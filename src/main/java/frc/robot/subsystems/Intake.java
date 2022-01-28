package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase{
	private static Intake m_singleton=null;
	private WPI_VictorSPX intakePivot;
	private WPI_VictorSPX rollers;

	//Constructor
	public Intake() {
		//Creates VictorSPX motor controller for the arm
		//Creates WPI_VictorSPX motor controller for the arm
		intakePivot=new WPI_VictorSPX(Constants.MCID.get("Intake Pivot"));
		rollers=new WPI_VictorSPX(Constants.MCID.get("Rollers"));
	}
	//Returns an instance of the Intake
	public static WPI_VictorSPX getInstance() {
		if (m_singleton==null) {
			m_singleton=new Intake();
	}
}
