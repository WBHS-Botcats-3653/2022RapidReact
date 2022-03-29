package frc.robot;

import java.util.HashMap;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;

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
		public static final double kTaxiDistance = -80.0;  //Inches (-86.0)
		
		/*Holds the coordinates of the different pre-positioned cargo with the robot in the left or right Tarmac at x = 0, y = 0, angle = 0
		 *<String cargoID, Pose2d(x, y, Rotation2d(angle))>
		 */
		public static final HashMap<String, Pose2d> kCargoCoordinates = new HashMap<>() {{
			put("LLL", new Pose2d(Units.inchesToMeters(-105.82), Units.inchesToMeters(-38.46), Rotation2d.fromDegrees(-30.0)));
			put("LLR", new Pose2d(Units.inchesToMeters(-62.52), Units.inchesToMeters(-79.56), Rotation2d.fromDegrees(-15.0)));
			put("LML", new Pose2d(Units.inchesToMeters(52.9), Units.inchesToMeters(-99.39), Rotation2d.fromDegrees(120.0)));
			put("LMR", new Pose2d(Units.inchesToMeters(107.43), Units.inchesToMeters(-75.11), Rotation2d.fromDegrees(150.0)));
			put("LRL", null);  //DO NOT PUT VALUES
			put("LRR", null);  //DO NOT PUT VALUES
			put("RLL", null);  //DO NOT PUT VALUES
			put("RLR", null);  //DO NOT PUT VALUES
			put("RML", new Pose2d(Units.inchesToMeters(-107.43), Units.inchesToMeters(-75.11), Rotation2d.fromDegrees(-150.0)));
			put("RMR", new Pose2d(Units.inchesToMeters(-52.9), Units.inchesToMeters(-99.39), Rotation2d.fromDegrees(-120.0)));
			put("RRL", new Pose2d(Units.inchesToMeters(62.52), Units.inchesToMeters(-79.56), Rotation2d.fromDegrees(15.0)));
			put("RRR", new Pose2d(Units.inchesToMeters(105.82), Units.inchesToMeters(-38.46), Rotation2d.fromDegrees(30.0)));
		}};

		//The middle waypoint used in the return trajectory to avoid hitting any obstacles (L for the left Tarmac and R for the right Tarmac)
		public static final Pose2d kReturnMiddleWaypointL = new Pose2d(Units.inchesToMeters(0.0), Units.inchesToMeters(kTaxiDistance), Rotation2d.fromDegrees(0.0));
		public static final Pose2d kReturnMiddleWaypointR = new Pose2d(Units.inchesToMeters(0.0), Units.inchesToMeters(kTaxiDistance), Rotation2d.fromDegrees(0.0));

		//Speeds
		public static final double kAutoDriveSpeed = 0.5;
		public static final double kAutoIntakePivotDownSpeed = 0.15;
		public static final double kAutoIntakePivotUpSpeed = 0.35;
		public static final double kAutoIntakeRollerSpeed = 1.0;
		public static final double kAutoIndexSpeed = 0.5;
		public static final double kAutoShootSpeed = 0.85;
	}

	//Drive PID Constants
	public static final class DrivePIDConstants {
		//The distance between the center line of the left and right wheels
		public static final double kTrackWidth = 0.0;  //Inches

		/*You can get kS, kV, kA, and kP from the frc robot characterization tool (sysId)
		 *The tool can be found in the WPILib Tools desktop folder (Windows)
		 *The characterization should be performed while the robot in on a surface similar to that of the game field for the most accurate results
		 *Instructions: https://docs.wpilib.org/en/stable/docs/software/pathplanning/system-identification/identification-routine.html#running-the-identification-routine
		 */
		//Static gain (voltage required to overcome static friction)
		public static final double kS = 0.0;  //Get correct value
		//Velocity gain
		public static final double kV = 0.0;  //Get correct value
		//Acceleration gain
		public static final double kA = 0.0;  //Get correct value

		//Proportional (multiplied to the error)
		public static final double kP = 0.4;  //Find good value
		//Integral (multiplied to the error sum)
		public static final double kI = 0.1;  //Find good value
		//Derivative (multiplied to the error rate)
		public static final double kD = 0.1;  //Find good value

		//Constants you don't need to understand
		public static final double kRamseteB = 2.0;  //DO NOT CHANGE
		public static final double kRamseteZeta = 0.7;  //DO NOT CHANGE
	}

	//Drive Constants
	public static final class DriveConstants {
		//Motor IDs
		public static final int kFrontLeftMotorID = 0;  //CAN
		public static final int kFrontRightMotorID = 1;  //CAN
		public static final int kBackLeftMotorID = 2;  //CAN
		public static final int kBackRightMotorID = 3;  //CAN
		
		//Encoder IDs
		public static final int kLeftMotorGroupEncoderID1 = 5;  //DIO 1 pin
		public static final int kLeftMotorGroupEncoderID2 = 4; //DIO 3 pin
		public static final int kRightMotorGroupEncoderID1 = 3;  //DIO 1 pin
		public static final int kRightMotorGroupEncoderID2 = 2;  //DIO 3 pin

		//Diameter of the wheel directly mounted to the motor shaft
		//private static final double kWheelDiameter = 6.0;  //Inches
		private static final double kWheelDiameter = Units.inchesToMeters(6.0);  //Meters
		//Circumference of the wheel directly mounted to the motor shaft
		private static final double kWheelCircumference = kWheelDiameter * Math.PI;  //Meters
		//The number of pulses per rotation of the motor (also known as the encoder resolution)
		//private static final double kPulsePerRevolution = 360.0;  //This is not the correct value
		private static final double kPulsePerRevolution = 3900.0;
		//The gear ratio between the encoder shaft and the wheels
		private static final double kEncoderGearRatio = 1.0 / 1.0;
		//The gear ratio between the motors and the shaft
		private static final double kGearRatio = 10.71 / 1.0;
		//Custom variable to adjust what units the encoder returns a value in (in this case inches)
		//private static final double kFudgefactor = 12.0 / 1.11228;
		//Encoder distance per pulse calculation
		//public static final double kDistancePerPulse = Math.PI * kWheelDiameter / kPulsePerRevolution / kEncoderGearRatio / kGearRatio * kFudgefactor;
		public static final double kDistancePerPulse = kWheelCircumference / kPulsePerRevolution / kEncoderGearRatio / kGearRatio;

		public static final double kThreshold = 3.0;
	}

	//Intake Constants
	public static final class IntakeConstants {
		//Motor IDs
		public static final int kPivotMotorID = 20;  //CAN
		public static final int kRollersMotorID = 21;  //CAN

		//Photoelectric sensor ID
		public static final int kIntakePESensorID = 9;  //DIO
		
		//Pivot limit switch IDs
		public static final int kBottomPivotLimitSwitchID = 0;  //DIO
		public static final int kTopPivotLimitSwitchID = 1;  //DIO

		//Speeds
		public static final double kSmartIntakePivotDownSpeed = 0.15;
		public static final double kSmartIntakePivotUpSpeed = 0.35;
		public static final double kPivotAssistSpeed = 0.1;
	}

	//Indexer Constants
	public static final class IndexerConstants {
		//Motor ID
		public static final int kIndexerMotorID = 22;  //CAN

		//Photoelectric sensor IDs
		public static final int kLowerStoragePESensorID = 6;  //DIO
		public static final int kUpperStoragePESensorID = 7;  //DIO

		//Speed
		public static final double kSmartIndexSpeed = 0.7;
	}

	//Shooter Constants
	public static final class ShooterConstants {
		//Spinner motor ID
		public static final int kFlywheelMotorID = 30;  //CAN

		//Shooter photoelectric sensor ID
		public static final int kShooterPESensorID = 8;  //DIO
	}

	//Climber Constants
	public static final class ClimberConstants {
		//Motor IDs
		public static final int kClimberArmMotorID = 10;  //CAN
		public static final int kExtenderWinchMotorID = 11;  //CAN
		public static final int kHookWinchMotorID = 12;  //CAN
	}

	//Default Speeds Constants
	public static final class DefaultSpeedsConstants {
		//Default Max Speeds
		public static final double kDefaultDriveSpeed = 1.0;
		public static final double kDefaultPivotSpeed = 0.5;
		public static final double kDefaultRollerSpeed = 1.0;
		public static final double kDefaultIndexSpeed = 1.0;
		public static final double kDefaultSmartIndexSpeed = 0.7;
		public static final double kDefaultShootSpeed = 0.85;
		public static final double kDefaultClimbSpeed = 1.0;
		public static final double kDefaultExtenderWinchSpeed = 0.5;
		public static final double kDefaultHookWinchSpeed = 1.0;
	}
}
