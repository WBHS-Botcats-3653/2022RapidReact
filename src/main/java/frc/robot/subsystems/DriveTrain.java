// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//Imports WPI_VictorSPX (Motor Controller)
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
//Imports DifferentialDrive
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//Imports MotorControllerGroup
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
//Imports Subsystem Base
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//Imports Constants
import frc.robot.Constants;
//Imports OI
import frc.robot.OI;


public class DriveTrain extends SubsystemBase {
	private static DriveTrain driveTrain=null;
	public OI input= OI.getInstance();
	private DifferentialDrive diffDrive;

	//Constructor
	private DriveTrain() {
		//Left side wheel motors
		WPI_VictorSPX driveFrontLeft=new WPI_VictorSPX(Constants.MCID.get("Wheel Front Left"));
		WPI_VictorSPX driveBackLeft=new WPI_VictorSPX(Constants.MCID.get("Wheel Back Left"));
		MotorControllerGroup driveLeft=new MotorControllerGroup(driveFrontLeft, driveBackLeft);
		//Right side wheel motors
		WPI_VictorSPX driveFrontRight=new WPI_VictorSPX(Constants.MCID.get("Wheel Front Right"));
		WPI_VictorSPX driveBackRight=new WPI_VictorSPX(Constants.MCID.get("Wheel Back Right"));
		MotorControllerGroup driveRight=new MotorControllerGroup(driveFrontRight, driveBackRight);
		//Creates differential drive
		diffDrive=new DifferentialDrive(driveLeft, driveRight);
		//Reverses right motor direction
		driveLeft.setInverted(true);
	}

	//Returns an instance of the climber, creating an instance only when one does not already exist
	public static DriveTrain getDriveTrain() {
		if(driveTrain==null) {
			driveTrain=new DriveTrain();
		}
		return driveTrain;
	}

	/*Instantiates the arcade drive
	 *OVERLOADED FUNCTION
	 */
	public void ArcadeDrived() {
		getDriveTrain().diffDrive.arcadeDrive(input.getThrottle(), input.getSteering());
	}

	/*Instantiates the arcade drive
	 *OVERLOADED FUNCTION
	 */
	public void ArcadeDrived(DriveTrain train) {
		train.diffDrive.arcadeDrive(input.getThrottle(), input.getSteering());
	}


	@Override
	public void periodic() {
	// This method will be called once per scheduler run
	}

	@Override
	public void simulationPeriodic() {
	// This method will be called once per scheduler run during simulation
	}
}
