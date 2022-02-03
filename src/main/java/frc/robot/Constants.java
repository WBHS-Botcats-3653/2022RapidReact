// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//Imports Java utilities
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
	/*Holds ALL the Motor Controller IDs
	 *<String motorControllerName, int ID>
	 *Feel free to change I thought a dictionary would be better than multiple variables but idk either way works (Remove if all good)
	 *URGENT!!! ID NAMES HAVE NOT BEEN CONFIRMED. MUST UPDATE IDS. DO NOT RUN CODE ON ROBOT WITHOUT UPDATING WITH CORRECT IDS!
	 *URGENT!!! ID NAMES HAVE NOT BEEN CONFIRMED. MUST UPDATE IDS. DO NOT RUN CODE ON ROBOT WITHOUT UPDATING WITH CORRECT IDS!
	 *URGENT!!! ID NAMES HAVE NOT BEEN CONFIRMED. MUST UPDATE IDS. DO NOT RUN CODE ON ROBOT WITHOUT UPDATING WITH CORRECT IDS!
	 *Remove the above 3 comments (and this one) when the IDs have been updated completely
	 */
	public static final Hashtable<String, Integer> MCID=new Hashtable<>() {{
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
	
}
