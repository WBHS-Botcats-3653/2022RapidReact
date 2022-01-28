package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;


import frc.robot.Constants;
import frc.robot.OI;



	//
	




public class Intake extends SubsystemBase{
	private static intake =null;
	private WPI_VictorSPX intakePivot;
	private WPI_VictorSPX rollers;

	/**Constructor
	 * it is a singleton
	 */
	public Intake() {

		//Creates VictorSPX motor controller for the arm
		//Creates WPI_VictorSPX motor controller for the arm
		intakePivot=new WPI_VictorSPX(Constants.MCID.get("Intake Pivot"));
		rollers=new WPI_VictorSPX(Constants.MCID.get("Rollers"));
	}


	public Intake getInstance(){
		if(intake == null)
			intake = new Intake();
		return intake;
	}
	//Returns the pivot motor controller
	public WPI_VictorSPX getPivotMotor() {
		return intakePivot;
	}

	public WPI_VictorSPX getRollerMotor() {
		return rollers;

	//Returns an instance of the Intake
	public static WPI_VictorSPX getInstance() {
		if (m_singleton==null) {
			m_singleton=new Intake();
		}

	}
}
