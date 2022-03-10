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

		//The distance to travel in reverse when taxiing
		public static final double kTaxiDistanceInInches = 80.0;  //Backwards but needs to be positive for some reason (86.0)

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

		//Speeds
		public static final double kAutoFastDriveSpeed = 0.5;
		public static final double kAutoSlowDriveSpeed = 0.5;
		public static final double kAutoIntakePivotDownSpeed = 0.15;
		public static final double kAutoIntakePivotUpSpeed = 0.35;
		public static final double kAutoIntakeRollerSpeed = 1.0;
		public static final double kAutoIndexSpeed = 0.5;
		public static final double kAutoShootSpeed = 0.85;
	}

	//Drive Constants
	public static final class DriveConstants {
		//Motor IDs
		public static final int kFrontLeftMotorID = 0;  //CAN
		public static final int kFrontRightMotorID = 1;  //CAN
		public static final int kBackLeftMotorID = 2;  //CAN
		public static final int kBackRightMotorID = 3;  //CAN
		
		//Encoder IDs
		public static final int kLeftMotorGroupEncoder1 = 5;  //DIO 1 pin
		public static final int kLeftMotorGroupEncoder2 = 4; //DIO 3 pin
		public static final int kRightMotorGroupEncoder1 = 3;  //DIO 1 pin
		public static final int kRightMotorGroupEncoder2 = 2;  //DIO 3 pin

		//Encoder distance per pulse
		private static final double kWheelDiameter = 6.0;
		private static final double kPulsePerRevolution = 360.0;
		private static final double kEncoderGearRatio = 1.0 / 1.0;
		private static final double kGearRatio = 10.71 / 1.0;
		private static final double kFudgefactor = 12.0;  //Unit conversion
		public static final double kDistancePerPulse = Math.PI * kWheelDiameter / kPulsePerRevolution / kEncoderGearRatio / kGearRatio * kFudgefactor;
		//public static final double kDistancePerPulse = 1.0/256.0;

		//Something
		public static final int kP = 1;
		public static final double kThreshold = 3.0;
	}

	//Climber Constants
	public static final class ClimberConstants {
		//Motor ID
		public static final int kArmMotorID = 10;  //CAN
	}

	//Intake Constants
	public static final class IntakeConstants {
		//Motor IDs
		public static final int kPivotMotorID = 20;  //CAN
		public static final int kRollersMotorID = 21;  //CAN
		public static final int kIndexerMotorID = 22;  //CAN

		//Storage photoelectric sensor IDs
		public static final int kLowerStoragePESensorID = 6;  //DIO
		public static final int kUpperStoragePESensorID = 7;  //DIO
		
		//Pivot limit switch IDs
		public static final int kBottomPivotLimitSwitchID = 0;  //DIO
		public static final int kTopPivotLimitSwitchID = 1;  //DIO

		//Speeds
		public static final double kSmartIntakePivotDownSpeed = 0.15;
		public static final double kSmartIntakePivotUpSpeed = 0.35;
		public static final double kIntakePivotAssistSpeed = 0.1;
		public static final double kSmartIndexSpeed = 0.7;
	}

	//Shooter Constants
	public static final class ShooterConstants {
		//Spinner motor ID
		public static final int kFlywheelMotorID = 30;  //CAN

		//Shooter photoelectric sensor ID
		public static final int kShooterPESensorID = 8;  //DIO
	}

	//Default Speeds Constants
	public static final class DefaultSpeedsConstants {
		//Default Speeds
		public static final double kDefaultDriveSpeed = 1.0;
		public static final double kDefaultIntakePivotSpeed = 0.7;
		public static final double kDefaultIntakeRollerSpeed = 1.0;
		public static final double kDefaultIndexSpeed = 1.0;
		public static final double kDefaultSmartIndexSpeed = 0.7;
		public static final double kDefaultShootSpeed = 0.85;
		public static final double kDefaultClimbSpeed = 1.0;
	}
}
