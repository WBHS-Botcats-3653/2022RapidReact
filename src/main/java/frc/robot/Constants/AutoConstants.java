package frc.robot.constants;

import java.util.HashMap;

public class AutoConstants {
	//Our teams alliance color
	public static String alliance = "Blue";

	/*Which Tarmac the robot is starting in
	 *"L"=Left, "R"=Right
	 */
	public static String startingPosition = "L";

	//Which cargo the robot should target during autonomous
	public static String[] cargoToTarget = new String[] {"LR", "LL"};

	/*Holds the starting positions of the cargo on the field
	 *<String position, String color>
	 *"L"=Left, "R"=Right, "M"=Middle
	 */
	public static HashMap<String, double[]> cargoPositions = new HashMap<>() {{
		put("LL", new double[] {});
		put("LR", new double[] {});
		put("ML", new double[] {});
		put("MR", new double[] {});
		put("RL", new double[] {});
		put("RR", new double[] {});
	}};

	//The distance to travel when taxiing
	public static final double taxiDistanceInFeet = -7.5;
}
