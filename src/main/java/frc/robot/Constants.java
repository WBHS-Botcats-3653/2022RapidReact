// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//Imports Hashtable utility
import java.util.Hashtable;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

	/*Holds all the Motor Controller IDs
	 *<String motorControllerName, Integer ID>
	 */
	public static final Hashtable<String, Integer> MCID = new Hashtable<>() {{
		put("Wheel Front Left", 0);
		put("Wheel Front Right", 1);
		put("Wheel Back Left", 2);
		put("Wheel Back Right", 3);
		put("Arm", 10);
		put("Pivot", 20);
		put("Rollers", 21);
		put("Indexer", 22);
		put("Spinner", 30);
	}};

	/*Holds all the Digital Input Output(DIO) IDs
	 *<String inputName, Integer ID>
	 */
	public static final Hashtable<String, Integer> DIOID = new Hashtable<>() {{
		put("Lower Storage PE Sensor", 2);
		put("Upper Storage PE Sensor", 3);
		put("Shooter PE Sensor", 4);
		put("Pivot Up Button", 1);
		put("Pivot Down Button", 0);
		put("Climber Down Button", 6);
	}};

	/*Holds all the Analong Input IDs
	 *<String inputName, Integer ID>
	 */
	public static final Hashtable<String, Integer> AIID = new Hashtable<>() {{
		put("Left Motor Group Encoder", 0);
		put("Right Motor Group Encoder", 1);
		put("Pivot Encoder", 2);
		put("Arm Encoder", 3);
	}};
}
