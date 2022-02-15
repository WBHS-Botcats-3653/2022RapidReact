package frc.robot;

//Imports XboxController
import edu.wpi.first.wpilibj.XboxController;

public class OI {
	private static OI m_singleton = null;
	//Controller
	private XboxController m_controller = null;
	//Max speeds
	private double m_maxDriveSpeed;
	private double m_maxArmSpeed;
	private double m_maxIntakePivotSpeed;
	private double m_maxIntakeRollerSpeed;
	private double m_maxShootSpeed;
	private double m_maxIndexerSpeed;

	public OI() {
		m_controller = new XboxController(0);
	}
	
	//Returns an instance of OI, creating an instance only when one does not already exist (singleton)
	public static OI getInstance() {
		if (m_singleton == null) {
			m_singleton = new OI();
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
	public double getMaxShootSpeed() {
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

	//Returns whether the left trigger is being pressed
	public boolean getIntakeUp() {
		return m_controller.getLeftBumper();
	}

	//Returns whether the left bumper is being pressed
	public boolean getIntakeDown() {
		return m_controller.getLeftTriggerAxis() > 0;
	}

	//Returns whether the B button is being pressed
	public boolean getIndexerIn() {
		return m_controller.getBButton();
	}

	//Returns whether the Y button is being pressed
	public boolean getIndexerOut() {
		return m_controller.getYButton();
	}

	//Returns whether the A button is being pressed
	public boolean getIntakeIn() {
		return m_controller.getAButton();
	}

	//Returns whether the A button is being pressed
	public boolean getIntakeOut() {
		return m_controller.getXButton();
	}

	//
	public boolean getSmartIntakeDown() {
		return false;
	}

	//
	public boolean getSmartIntakeUp() {
		return false;
	}

	public boolean getShoot() {
		return m_controller.getRightTriggerAxis() > 0;
	}

	public boolean getShootReverse() {
		return m_controller.getRightBumper();
	}

	//Returns whether the up bottom on the DPad is being pressed
	public boolean POVIsUp() {
		return m_controller.getPOV()!=-1&&(m_controller.getPOV()>=315||m_controller.getPOV()<=45);
	}

	//Returns whether the down button on the DPad is being pressed
	public boolean POVIsDown() {
		return m_controller.getPOV()>=135&&m_controller.getPOV()<=225;
	}

	//Returns whether the right button on the DPad is being pressed
	public boolean POVIsRight() {
		return m_controller.getPOV()>45&&m_controller.getPOV()<135;
	}

	//Returns whether the left button on the DPad is being pressed
	public boolean POVIsLeft() {
		return m_controller.getPOV()>225&&m_controller.getPOV()<315;
	}
}
