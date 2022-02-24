package frc.robot;

import java.util.HashMap;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
	//Auto Constants
	public static final class AutoConstants {
		/*Which Tarmac the robot is starting in (defaults to left tarmac)
		*"L"=Left, "R"=Right
		*/
		public static final String kStartingPosition = "L";  //MOVE OUT OF CONSTANTS SHOULD NOT BE FINAL

		//Which cargo the robot should target during autonomous (defaults to cargo on left side of field)
		public static final String[] kCargoToTarget = new String[] {"LR", "LL"};  //MOVE OUT OF CONSTANTS SHOULD NOT BE FINAL

		//The distance to travel when taxiing
		public static final double kTaxiDistanceInFeet = -7.1;

		/*Distances and angles to cargo after taxi during auto
		 *<String cargoID, double[angle, distance]>
		 */
		public static final HashMap<String, double[]> kDistancesAndAngles = new HashMap<>() {{
			put("LLL", new double[] {100.78, -86.53});
			put("LLR", new double[] {77.43, -30.58});
			put("LML", new double[] {-100.78, -86.53});
			put("LMR", new double[] {-114.66, -139.98});
			put("LRL", new double[] {0.0, 0.0});  //DO NOT PUT VALUES
			put("LRR", new double[] {0.0, 0.0});  //DO NOT PUT VALUES
			put("RLL", new double[] {0.0, 0.0});  //DO NOT PUT VALUES
			put("RLR", new double[] {0.0, 0.0});  //DO NOT PUT VALUES
			put("RML", new double[] {114.66, -139.98});
			put("RMR", new double[] {100.78, -86.53});
			put("RRL", new double[] {-77.43, -30.58});
			put("RRR", new double[] {-100.78, -86.53});
		}};

		/*//Turn angles
		public static final HashMap<String, Double> kTurnAngles = new HashMap<>() {{
			put("LLL", 100.78);
			put("LLR", 77.43);
			put("LML", -100.78);
			put("LMR", -114.66);
			put("LRL", 0.0);  //DO NOT PUT VALUE
			put("LRR", 0.0);  //DO NOT PUT VALUE
			put("RLL", 0.0);  //DO NOT PUT VALUE
			put("RLR", 0.0);  //DO NOT PUT VALUE
			put("RML", 114.66);
			put("RMR", 100.78);
			put("RRL", -77.43);
			put("RRR", -100.78);
		}};

		//Drive distances
		public static final HashMap<String, Double> kDriveDistances = new HashMap<>() {{
			put("LLL", -86.53);
			put("LLR", -30.58);
			put("LML", -86.53);
			put("LMR", -139.98);
			put("LRL", 0.0);  //DO NOT PUT VALUE
			put("LRR", 0.0);  //DO NOT PUT VALUE
			put("RLL", 0.0);  //DO NOT PUT VALUE
			put("RLR", 0.0);  //DO NOT PUT VALUE
			put("RML", -139.98);
			put("RMR", -86.53);
			put("RRL", -30.58);
			put("RRR", -86.53);
		}};*/
	}

	//Drive Constants
	public static final class DriveConstants {
		//Motor IDs
		public static final int kFrontLeftMotorID = 0;  //CAN
		public static final int kFrontRightMotorID = 1;  //CAN
		public static final int kBackLeftMotorID = 2;  //CAN
		public static final int kBackRightMotorID = 3;  //CAN
		
		//Encoders
		public static final int kLeftMotorGroupEncoder1 = 5;  //DIO 1 pin
		public static final int kLeftMotorGroupEncoder2 = 4; //DIO 3 pin
		public static final int kRightMotorGroupEncoder1 = 3;  //DIO 1 pin
		public static final int kRightMotorGroupEncoder2 = 2;  //DIO 3 pin

		//Encoder distance per pulse
		public static final double kDistancePerPulse = 1.0/256.0;

		//Something
		public static final int kP = 1;
	}

	//Climber Constants
	public static final class ClimberConstants {
		//Arm motor ID
		public static final int kArmMotorID = 10;  //CAN
		
		//Climber limit switch
		public static final int kBottomClimberLimitSwitchID = 6;  //DIO
	}

	//Intake Constants
	public static final class IntakeConstants {
		//Motor IDs
		public static final int kPivotMotorID = 20;  //CAN
		public static final int kRollersMotorID = 21;  //CAN
		public static final int kIndexerMotorID = 22;  //CAN

		//Storage photoelectric sensor IDs
		public static final int kLowerStoragePESensorID = 7;  //DIO
		public static final int kUpperStoragePESensorID = 8;  //DIO
		
		//Pivot limit switch IDs
		public static final int kBottomPivotLimitSwitchID = 0;  //DIO
		public static final int kTopPivotLimitSwitchID = 1;  //DIO
	}

	//Shooter Constants
	public static final class ShooterConstants {
		//Spinner motor ID
		public static final int kSpinnerMotorID = 30;  //CAN

		//Shooter photoelectric sensor ID
		public static final int kShooterPESensorID = 9;  //DIO
	}
}
