package frc.robot;

import java.util.Hashtable;

public class SIConstants {
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
