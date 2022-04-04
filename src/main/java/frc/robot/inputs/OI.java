package frc.robot.inputs;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.NetworkEntries;

//Operator Inputs
public class OI {
	private static OI m_singleton;

	//Controller
	private XboxController m_controller;
	
	//Max speeds
	private double maxDriveSpeed = 0.0;
	private double maxPivotSpeed = 0.0;
	private double maxRollerSpeed = 0.0;
	private double maxIndexSpeed = 0.0;
	private double maxShootSpeed = 0.0;
	private double maxClimbSpeed = 0.0;
	private double maxExtenderWinchSpeed = 0.0;
	private double maxHookWinchSpeed = 0.0;

	/** Constructs a new OI */
	public OI() {
		m_controller = new XboxController(0);
	}
	
	/** Returns an instance of OI, creating an instance only when one does not already exist (singleton)
	 * @return an instance of OI
	 */
	public static OI getInstance() {
		if (m_singleton == null) {
			m_singleton = new OI();
		}
		return m_singleton;
	}

	/** Sets the max drive speed
	 * @param speed Max drive speed
	 */
	public void setMaxDriveSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) maxDriveSpeed = speed;
	}

	/** Returns the max drive speed
	 * @return the max drive speed
	 */
	public double getMaxDriveSpeed() {
		return maxDriveSpeed;
	}

	/** Sets the max pivot speed
	 * @param speed Max pivot speed
	 */
	public void setMaxPivotSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) maxPivotSpeed = speed;
	}

	/** Returns the max pivot speed
	 * @return the max pivot speed
	 */
	public double getMaxPivotSpeed() {
		return maxPivotSpeed;
	}

	/** Sets the max roller speed
	 * @param speed Max roller speed
	 */
	public void setMaxRollerSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) maxRollerSpeed = speed;
	}

	/** Returns the max roller speed
	 * @return the max roller speed
	 */
	public double getMaxRollerSpeed() {
		return maxRollerSpeed;
	}

	/** Sets the max index speed
	 * @param speed Max index speed
	 */
	public void setMaxIndexSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) maxIndexSpeed = speed;
	}

	/** Returns the max index speed
	 * @return the max index speed
	 */
	public double getMaxIndexSpeed() {
		return maxIndexSpeed;
	}

	/** Sets the max shoot speed
	 * @param speed Max shoot speed
	 */
	public void setMaxShootSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) maxShootSpeed = speed;
	}
	
	/** Returns the max shoot speed
	 * @return the max shoot speed
	 */
	public double getMaxShootSpeed() {
		return maxShootSpeed;
	}

	/** Sets the max climb speed
	 * @param speed Max climb speed
	 */
	public void setMaxClimbSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) maxClimbSpeed = speed;
	}

	/** Returns the max climb speed
	 * @return the max climb speed
	 */
	public double getMaxClimbSpeed() {
		return maxClimbSpeed;
	}

	/** Sets the max extender winch speed
	 * @param speed Max extender winch speed
	 */
	public void setMaxExtenderWinchSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) maxExtenderWinchSpeed = speed;
	}

	/** Returns the max extender winch speed
	 * @return the max extender winch speed
	 */
	public double getMaxExtenderWinchSpeed() {
		return maxExtenderWinchSpeed;
	}

	/** Sets the max hook winch speed
	 * @param speed Max hook winch speed
	 */
	public void setMaxHookWinchSpeed(double speed) {
		if (0.0 < speed && speed <= 1.0) maxHookWinchSpeed = speed;
	}

	/** Returns the max hook winch speed
	 * @return the max hook winch speed
	 */
	public double getMaxHookWinchSpeed() {
		return maxHookWinchSpeed;
	}
	
	/** Returns the throttle input
	 * @return the speed to move forward/backward
	 */
	public double getThrottle() {
		return -m_controller.getLeftY() * maxDriveSpeed;  // correct throttle (-)
	}

	/** Returns the steering input
	 * @return the speed to rotate left/right
	 */
	public double getSteering() {
		return m_controller.getRightX() * maxDriveSpeed;
	}

	/** Returns whether the right DPad is being pressed
	 * @return whther the right DPad is being pressed
	 */
	public boolean getManualIntakeUp() {
		return NetworkEntries.isSmartIntakeEnabled() ? POVIsRight() : m_controller.getLeftBumper();
	}

	/** Returns whether the left DPad is being pressed
	 * @return whether the left DPad is being pressed
	 */
	public boolean getManualIntakeDown() {
		return NetworkEntries.isSmartIntakeEnabled() ? POVIsLeft() : m_controller.getLeftTriggerAxis() > 0;
	}

	/** Returns whether the A button is being pressed
	 * @return whether the A button is being pressed
	 */
	public boolean getManualIntakeIn() {
		return m_controller.getAButton();
	}

	/** Returns whether the X button is being pressed
	 * @return whther the X button is being pressed
	 */
	public boolean getManualIntakeOut() {
		return m_controller.getXButton();
	}

	/** Returns whether the B button is being pressed
	 * @return whether the B button is being pressed
	 */
	public boolean getIndexIn() {
		return m_controller.getBButton();
	}

	/** Returns whether the Y button is being pressed
	 * @return whether the Y button is being pressed
	 */
	public boolean getIndexOut() {
		return m_controller.getYButton();
	}

	/** Returns whether the left trigger bumper is being pressed
	 * @return whether the left trigger bumper is being pressed
	 */
	public boolean getSmartIntakeDown() {
		return NetworkEntries.isSmartIntakeEnabled() ? m_controller.getLeftBumper() : false;
	}

	/** Returns whether the left trigger bumper is not being pressed
	 * @return whether the left trigger bumper is not being pressed
	 */
	public boolean getSmartIntakeUp() {
		return NetworkEntries.isSmartIntakeEnabled() ? m_controller.getLeftBumperReleased() : false;
	}

	/** Whether the right trigger is being pressed
	 * @return whether the right trigger is being pressed
	 */
	public boolean getShoot() {
		return m_controller.getRightTriggerAxis() > 0;
	}

	/** Whether the right bumper is being pressed
	 * @return the right bumper is being pressed
	 */
	public boolean getShootReverse() {
		return m_controller.getRightBumper();
	}

	/** Whether the up botton on the DPad is being pressed
	 * @return the up botton on the DPad is being pressed
	 */
	public boolean getClimbUp() {
		return POVIsUp();
	}

	/** Whether the down button on the DPad is being pressed
	 * @return the down button on the DPad is being pressed
	 */
	public boolean getClimbDown() {
		return POVIsDown();
	}

	/** Whether the right botton on the DPad is being pressed
	 * @return the right botton on the DPad is being pressed
	 */
	public boolean getExtenderWinchClockwise() {
		return POVIsLeft();
	}

	/** Whether the left button on the DPad is being pressed
	 * @return the left button on the DPad is being pressed
	 */
	public boolean getExtenderWinchCounterclockwise() {
		return POVIsRight();
	}

	/** Whether the start button is being pressed
	 * @return the start button is being pressed
	 */
	public boolean getHookWinchClockwise() {
		return m_controller.getStartButton();
	}

	/** Whether the back button is being pressed
	 * @return the back button is being pressed
	 */
	public boolean getHookWinchCounterclockwise() {
		return m_controller.getBackButton();
	}

	//Converts the POV from the Dpad into boolean values for up, down, left, and right
	/** Returns whether the up button on the DPad is being pressed
	 * @return whether the up button on the DPad is being pressed
	 */
	public boolean POVIsUp() {
		return m_controller.getPOV() != -1 && (m_controller.getPOV() >= 315 || m_controller.getPOV() <= 45);
	}

	/** Returns whether the down button on the DPad is being pressed
	 * @return whether the down button on the DPad is being pressed
	 */
	public boolean POVIsDown() {
		return m_controller.getPOV() >= 135 && m_controller.getPOV() <= 225;
	}

	/** Returns whether the right button on the DPad is being pressed
	 * @return whether the right button on the DPad is being pressed
	 */
	public boolean POVIsRight() {
		return m_controller.getPOV() > 45 && m_controller.getPOV() < 135;
	}

	/** Returns whether the left button on the DPad is being pressed
	 * @return whether the left button on the DPad is being pressed
	 */
	public boolean POVIsLeft() {
		return m_controller.getPOV() > 225 && m_controller.getPOV() < 315;
	}
}
