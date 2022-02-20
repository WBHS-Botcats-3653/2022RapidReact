package frc.robot.constants;

public class AutoConstants {
	//Our teams alliance color (defaults to blue)
	public static String alliance = "Blue";

	/*Which Tarmac the robot is starting in (defaults to left tarmac)
	 *"L"=Left, "R"=Right
	 */
	public static String startingPosition = "L";

	//Which cargo the robot should target during autonomous (defaults to cargo on left side of field)
	public static String[] cargoToTarget = new String[] {"LR", "LL"};

	//The distance to travel when taxiing
	public static final double taxiDistanceInFeet = -7.5;
}
