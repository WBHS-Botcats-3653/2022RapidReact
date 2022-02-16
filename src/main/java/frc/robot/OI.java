package frc.robot;

//Imports XboxController
import edu.wpi.first.wpilibj.XboxController;

public class OI {
	private static OI m_singleton = null;
	//Controller
	private XboxController m_controller = null;
	//Max speeds
	private double m_maxDriveSpeed = 0;
	private double m_maxArmSpeed = 0;
	private double m_maxIntakePivotSpeed = 0;
	private double m_maxSmartIntakePivotSpeed = 0;
	private double m_maxIntakeRollerSpeed = 0;
	private double m_maxShootSpeed = 0;
	private double m_maxIndexerSpeed = 0;

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
	public void setMaxDriveSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) {
			m_maxDriveSpeed = speed;
		}
	}

	//Returns the max drive speed
	public double getMaxDriveSpeed(){
		return m_maxDriveSpeed;
	}

	//Sets the max arm speed
	public void setMaxArmSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) {
			m_maxArmSpeed = speed;
		}
	}

	//Returns the max arm speed
	public double getMaxArmSpeed(){
		return m_maxArmSpeed;
	}

	//Sets the max intake pivot speed
	public void setMaxIntakePivotSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) {
			m_maxIntakePivotSpeed = speed;
		}
	}

	//Returns the max intake pivot speed
	public double getMaxIntakePivotSpeed(){
		return m_maxIntakePivotSpeed;
	}

	//Sets the max smart intake pivot speed
	public void setMaxSmartIntakePivotSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) {
			m_maxSmartIntakePivotSpeed = speed;
		}
	}

	//Returns the max smart intake pivot speed
	public double getMaxSmartIntakePivotSpeed(){
		return m_maxSmartIntakePivotSpeed;
	}

	//Sets the max intake roller speed
	public void setMaxIntakeRollerSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) {
			m_maxIntakeRollerSpeed = speed;
		}
	}

	//Returns the max intake roller speed
	public double getMaxIntakeRollerSpeed(){
		return m_maxIntakeRollerSpeed;
	}

	//Sets the max indexer speed
	public void setMaxIndexerSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) {
			m_maxIndexerSpeed = speed;
		}
	}

	//Returns the max indexer speed
	public double getMaxIndexerSpeed() {
		return m_maxIndexerSpeed;
	}

	//Sets the max shoot speed
	public void setMaxShootSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) {
			m_maxShootSpeed = speed;
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

	/*//Returns whether the left trigger is being pressed
	public boolean getIntakeUp() {
		return m_controller.getLeftBumper();
	}

	//Returns whether the left bumper is being pressed
	public boolean getIntakeDown() {
		return m_controller.getLeftTriggerAxis() > 0;
	}*/

	//Returns whether the right DPad is being pressed
	public boolean getManualIntakeUp() {
		return POVIsRight();
	}

	//Returns whether the left DPad is being pressed
	public boolean getManualIntakeDown() {
		return POVIsLeft();
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
	public boolean getManualIntakeIn() {
		return m_controller.getAButton();
	}

	//Returns whether the A button is being pressed
	public boolean getManualIntakeOut() {
		return m_controller.getXButton();
	}

	//Returns whether the left trigger is being pressed
	public boolean getSmartIntakeDown() {
		return m_controller.getLeftBumper();
	}

	//Returns whether the left trigger is not being pressed
	public boolean getSmartIntakeUp() {
		return m_controller.getLeftBumperReleased();
	}

	//Whether the right trigger is being pressed
	public boolean getShoot() {
		return m_controller.getRightTriggerAxis() > 0;
	}

	//Whether the right bumper is being pressed
	public boolean getShootReverse() {
		return m_controller.getRightBumper();
	}

	//Whether the up botton on the DPad is being pressed
	public boolean getClimbUp() {
		return POVIsUp();
	}

	//Whether the down button on the DPas is being pressed
	public boolean getClimbDown() {
		return POVIsDown();
	}

	//Returns whether the up botton on the DPad is being pressed
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
}
