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
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
/**<<in this file is where you add buttons or/and joysticks>>
 */
public class OI {
	private static OI m_singleton = null;
	private XboxController m_controller = null;
	private static double m_maxDriveSpeed;
	private static double m_maxArmSpeed;
	private static double m_maxIntakeSpeed;
	private static double m_maxShootSpeed;
	public boolean isIntakeDown = false;

	/**it is the constructor
	 * 
	 * take controll of you life!!!!
	 */
	private OI() {
		m_controller = new XboxController(0);
		m_maxDriveSpeed = 1.0;

	}

	public static OI getInstance() {
		if (m_singleton == null) {
			m_singleton = new OI();
		}
		if(m_maxArmSpeed == 0) m_maxArmSpeed = 1.0;

		if(m_maxIntakeSpeed == 0) m_maxArmSpeed = 1.0;
		return m_singleton;
	}

	public void setMaxDriveSpeed(double maxspd) {
		System.out.println("setMaxDriveSpeed( " + maxspd + " )");
		if (0.0 < maxspd && maxspd <= 1.0) {
			m_maxDriveSpeed = maxspd;
		}
	}

	public double getMaxDriveSpeed(){
		return m_maxDriveSpeed;
	}

	public void setMaxArmSpeed(double maxspd) {
		if (0.0 < maxspd && maxspd <= 1.0) {
			m_maxArmSpeed = maxspd;
		}
	}

	public double getMaxArmSpeed(){
		return m_maxArmSpeed;
	}

	public void setMaxIntakeSpeed(double maxspd) {
		if (0.0 < maxspd && maxspd <= 1.0) {
			m_maxIntakeSpeed = maxspd;
		}
	}
	
	public double getMaxIntakeSpeed(){
		return m_maxIntakeSpeed;
	}
	/**this one will return what is commonly as the forward and back
	 * 
	 * @return double Throttle
	 */
	public double getThrottle() {
		return m_controller.getLeftY() * m_maxDriveSpeed;
	}

	public double getSteering() {
		return -m_controller.getRightX() * m_maxDriveSpeed;// correct stearing (-)
	}

	/**
	 * this one is for the rollers 
	 * 
	 * @return ret_value * m_maxIntakeSpeed
	 */
	public double getIntakeCtrl() {
		double ret_value = 0.0;

		if (m_controller.getLeftBumper()) {
			ret_value = -1;
		} else if (m_controller.getLeftTriggerAxis() > 0) {
			ret_value = 1;
		}

		return ret_value * m_maxIntakeSpeed;
	}

	public boolean getIntakeDown() {
		return m_controller.getLeftTriggerAxis() > 0 && !isIntakeDown;
	}

	public boolean getIntakeUp() {
		return m_controller.getLeftTriggerAxis() == 0 && isIntakeDown;
	}
	/**Returns the value for the shoota
	 * 
	 * @return m_maxShootSpeed; or 0
	 * 
	 * life can feel meaningless, but you should always remember that this day will pass. 
	 */
	public double getShoot() {
		if (m_controller.getRightTriggerAxis() > 0) {
			return m_maxShootSpeed;
		}
		return 0;
	}
	//Easter egg \o/
	public boolean POVIsUp() {
		return m_controller.getPOV()>=315||m_controller.getPOV()<=45;
	}

	public boolean POVIsDown() {
		return m_controller.getPOV()>=135||m_controller.getPOV()<=225;
	}

	public boolean POVIsRight() {
		return m_controller.getPOV()>45||m_controller.getPOV()<135;
	}

	public boolean POVIsLeft() {
		return m_controller.getPOV()>225||m_controller.getPOV()<315;
	}
	
	/*
	public void setRumble(boolean is_rumble) {
		m_controller.setRumble(RumbleType.kLeftRumble, is_rumble ? 0.0 : 0.5);
	}
	*/
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
