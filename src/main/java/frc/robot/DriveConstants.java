package frc.robot;

import java.util.Hashtable;

public class DriveConstants {
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
}
