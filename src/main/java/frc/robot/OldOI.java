/*----------------------------------------------------------------------------*/
/* Copyright (c) 2021-2022 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

//Imports XBoxController
import edu.wpi.first.wpilibj.XboxController;

/**
 * Operator input
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OldOI {
	private static OldOI m_singleton = null;
	//Controller
	private XboxController m_controller = null;
	//Max speeds
	private double m_maxDriveSpeed;
	private double m_maxArmSpeed;
	private double m_maxIntakePivotSpeed;
	private double m_maxIntakeRollerSpeed;
	private double m_maxShootSpeed;
	private double m_maxIndexerSpeed;
	//Holds whether the intake is down
	public boolean isIntakeDown = false;

	private OldOI() {
		m_controller = new XboxController(0);
	}

	//Returns an instance of OI, creating an instance only when one does not already exist (singleton)
	public static OldOI getInstance() {
		if (m_singleton == null) {
			m_singleton = new OldOI();
		}
		return m_singleton;
	}

	//Sets the max drive speed
	public void setMaxDriveSpeed(double maxspd) {
		if (0.0 < maxspd && maxspd <= 1.0) {
			m_maxDriveSpeed = maxspd;
		}
	}

	//Returns the max drive speed
	public double getMaxDriveSpeed(){
		return m_maxDriveSpeed;
	}

	//Sets the max arm speed
	public void setMaxArmSpeed(double maxspd) {
		if (0.0 < maxspd && maxspd <= 1.0) {
			m_maxArmSpeed = maxspd;
		}
	}

	//Returns the max arm speed
	public double getMaxArmSpeed(){
		return m_maxArmSpeed;
	}

	//Sets the max intake pivot speed
	public void setMaxIntakePivotSpeed(double maxspd) {
		if (0.0 < maxspd && maxspd <= 1.0) {
			m_maxIntakePivotSpeed = maxspd;
		}
	}

	//Returns the max intake pivot speed
	public double getMaxIntakePivotSpeed(){
		return m_maxIntakePivotSpeed;
	}

	//Sets the max intake roller speed
	public void setMaxIntakeRollerSpeed(double maxspd) {
		if (0.0 < maxspd && maxspd <= 1.0) {
			m_maxIntakeRollerSpeed = maxspd;
		}
	}

	//Returns the max intake roller speed
	public double getMaxIntakeRollerSpeed(){
		return m_maxIntakeRollerSpeed;
	}

	//Sets the max indexer speed
	public void setMaxIndexerSpeed(double maxspd) {
		if (0.0 < maxspd && maxspd <= 1.0) {
			m_maxIndexerSpeed = maxspd;
		}
	}

	//Returns the max indexer speed
	public double getMaxIndexerSpeed() {
		return m_maxIndexerSpeed;
	}

	//Sets the max shoot speed
	public void setMaxShootSpeed(double maxspd) {
		if (0.0 < maxspd && maxspd <= 1.0) {
			m_maxShootSpeed = maxspd;
		}
	}
	
	//Returns the max shoot speed (invert after return)
	public double getMaxShootSpeed(){
		return m_maxShootSpeed;
	}
	
	//Returns the speed to move forward/backward
	public double getThrottle() {
		return m_controller.getLeftY() * m_maxDriveSpeed;
	}

	//Returns the speed to move left/right
	public double getSteering() {
		return -m_controller.getRightX() * m_maxDriveSpeed;  // correct stearing (-)
	}

	//Returns the speed to move the intake rollers
	public double getIntakeCtrl() {
		double ret_value = 0.0;
		if (m_controller.getLeftBumper()) {
			ret_value = -m_maxIntakeRollerSpeed;
		} else if (m_controller.getLeftTriggerAxis() > 0 || m_controller.getRightBumper()) {
			ret_value = m_maxIntakeRollerSpeed;
		}
		return ret_value;
	}

	//Returns whether the operator is trying to move the intake down and the intake is not already down
	public boolean getIntakeDown() {
		return m_controller.getLeftTriggerAxis() > 0 && !isIntakeDown;
	}

	//Returns whether the operator is trying to move the intake up and the intake is down
	public boolean getIntakeUp() {
		return m_controller.getLeftTriggerAxis() == 0 && isIntakeDown;
	}

	//Returns whether the manual intake up is being pressed
	public boolean getManualIntakeUp() {
		return m_controller.getYButton();
	}

	//Returns whether the manual intake down is being pressed
	public boolean getManualIntakeDown() {
		return m_controller.getAButton();
	}
	
	//Returns whether the manual indexer is being being pressed
	public boolean getSpinIndexer() {
		return m_controller.getXButton();
	}
	
	//Returns the speed to spin the 
	public double getShoot() {
		if (m_controller.getRightTriggerAxis() > 0) {
			return m_maxShootSpeed;
		}
		return 0;
	}

	//Returns whether the up bottom on the DPad is being pressed
	public boolean POVIsUp() {
		return m_controller.getPOV() != -1 && (m_controller.getPOV() >= 315 || m_controller.getPOV() <= 45);
	}

	//Returns whether the down button on the DPad is being pressed
	public boolean POVIsDown() {
		return m_controller.getPOV() >= 135 && m_controller.getPOV() <= 225;
	}

	//Returns whether the right button on the DPad is being pressed
	public boolean POVIsRight() {
		return m_controller.getPOV() > 45 && m_controller.getPOV() < 135;
	}

	//Returns whether the left button on the DPad is being pressed
	public boolean POVIsLeft() {
		return m_controller.getPOV() > 225 && m_controller.getPOV() < 315;
	}

	//Returns whether the all motor stop button is being pressed (used for testing purposes, no smoke from the motors is nice)
	public boolean getAllStop() {
		return m_controller.getBButton();
	}
}
