package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
<<<<<<< HEAD

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.Constants;
import frc.robot.OI;

public class Intake {
	private Intake intake = null;
	private WPI_VictorSPX intakePivot;
	private WPI_VictorSPX rollers;

	//
	/**Constructor
	 * it is a singleton
	 */
	private Intake() {
=======
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.robot.Constants;
import frc.robot.OI;

public class Intake extends SubsystemBase{
	private static m_singleton=null;
	private WPI_VictorSPX intakePivot;
	private WPI_VictorSPX rollers;

	//Constructor
	public Intake() {
>>>>>>> 7945652138508edd93b70f5d008570fb7e564c54
		//Creates VictorSPX motor controller for the arm
		//Creates WPI_VictorSPX motor controller for the arm
		intakePivot=new WPI_VictorSPX(Constants.MCID.get("Intake Pivot"));
		rollers=new WPI_VictorSPX(Constants.MCID.get("Rollers"));
	}

<<<<<<< HEAD
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
=======
	//Returns an instance of the Intake
	public static WPI_VictorSPX getInstance() {
		if (m_singleton==null) {
			m_singleton=new Intake();
		}
>>>>>>> 7945652138508edd93b70f5d008570fb7e564c54
	}
}
