package frc.robot.inputs;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.NetworkEntries;

//Operator Inputs
public class OI {
	private static OI m_singleton = null;

	//Controller
	private XboxController m_controller = null;
	
	//Max speeds
	private double maxDriveSpeed = 0;
	private double maxIntakePivotSpeed = 0;
	private double maxIntakeRollerSpeed = 0;
	private double maxIndexSpeed = 0;
	private double maxSmartIndexSpeed = 0;
	private double maxShootSpeed = 0;
	private double maxClimbSpeed = 0;
	private double maxExtendSpeed = 0;
	private double maxWinchSpeed = 0;

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
			maxDriveSpeed = speed;
		}
	}

	//Returns the max drive speed
	public double getMaxDriveSpeed() {
		return maxDriveSpeed;
	}

	//Sets the max arm speed
	public void setMaxClimbSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) {
			maxClimbSpeed = speed;
		}
	}

	//Returns the max arm speed
	public double getMaxClimbSpeed() {
		return maxClimbSpeed;
	}

	//Sets the max extend speed
	public void setMaxExtendSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) {
			maxExtendSpeed = speed;
		}
	}

	//Returns the max extend speed
	public double getMaxExtendSpeed() {
		return maxExtendSpeed;
	}

	//Sets the max winch speed
	public void setMaxWinchSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) {
			maxWinchSpeed = speed;
		}
	}

	//Returns the max winch speed
	public double getMaxWinchSpeed() {
		return maxWinchSpeed;
	}

	//Sets the max intake pivot speed
	public void setMaxIntakePivotSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) {
			maxIntakePivotSpeed = speed;
		}
	}

	//Returns the max intake pivot speed
	public double getMaxIntakePivotSpeed() {
		return maxIntakePivotSpeed;
	}

	//Sets the max intake roller speed
	public void setMaxIntakeRollerSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) {
			maxIntakeRollerSpeed = speed;
		}
	}

	//Returns the max intake roller speed
	public double getMaxIntakeRollerSpeed() {
		return maxIntakeRollerSpeed;
	}

	//Sets the max indexer speed
	public void setMaxIndexSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) {
			maxIndexSpeed = speed;
		}
	}

	//Returns the max indexer speed
	public double getMaxIndexSpeed() {
		return maxIndexSpeed;
	}

	//Sets the max indexer speed
	public void setMaxSmartIndexSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) {
			maxSmartIndexSpeed = speed;
		}
	}

	//Returns the max indexer speed
	public double getMaxSmartIndexSpeed() {
		return maxSmartIndexSpeed;
	}

	//Sets the max shoot speed
	public void setMaxShootSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) {
			maxShootSpeed = speed;
		}
	}
	
	//Returns the max shoot speed (invert after return)
	public double getMaxShootSpeed() {
		return maxShootSpeed;
	}
	
	//Returns the speed to move forward/backward
	public double getThrottle() {
		return m_controller.getLeftY() * maxDriveSpeed;
	}

	//Returns the speed to move left/right
	public double getSteering() {
		return -m_controller.getRightX() * maxDriveSpeed;  // correct stearing (-)
	}

	//Returns whether the right DPad is being pressed
	public boolean getManualIntakeUp() {
		if (NetworkEntries.isSmartIntakeEnabled()) {
			return POVIsRight();
		} else {
			return m_controller.getLeftBumper();
		}
	}

	/**Returns whether the left DPad is being pressed */
	public boolean getManualIntakeDown() {
		if (NetworkEntries.isSmartIntakeEnabled()) {
			return POVIsLeft();
		} else {
			return m_controller.getLeftTriggerAxis() > 0;
		}
	}

	/**Returns whether the B button is being pressed */
	public boolean getIndexerIn() {
		return m_controller.getBButton();
	}

	/**Returns whether the Y button is being pressed
	*/
	public boolean getIndexerOut() {
		return m_controller.getYButton();
	}

	/**Returns whether the A button is being pressed
	 */
	public boolean getManualIntakeIn() {
		return m_controller.getAButton();
	}

	/**Returns whether the X button is being pressed
	 */
	public boolean getManualIntakeOut() {
		return m_controller.getXButton();
	}

	/**Returns whether the left trigger (bumper) is being pressed
	 */
	public boolean getSmartIntakeDown() {
		if (NetworkEntries.isSmartIntakeEnabled()) {
			return m_controller.getLeftBumper();
		} else {
			return false;
		}
	}

	/**Returns whether the left trigger (bumper) is not being pressed
	 */
	public boolean getSmartIntakeUp() {
		if (NetworkEntries.isSmartIntakeEnabled()) {
			return m_controller.getLeftBumperReleased();
		} else {
			return false;
		}
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

	//Whether the down button on the DPad is being pressed
	public boolean getClimbDown() {
		return POVIsDown();
	}

	//Whether the right botton on the DPad is being pressed
	public boolean getTraverseUp() {
		return POVIsRight();
	}

	//Whether the left button on the DPad is being pressed
	public boolean getTraverseDown() {
		return POVIsLeft();
	}

	//Whether the back button is being pressed
	public boolean getWinch() {
		return m_controller.getBackButton();
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
