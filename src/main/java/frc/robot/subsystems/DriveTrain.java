// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

<<<<<<< HEAD
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.OI;
=======
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


//import com.ctre.CANTalon.TalonControlMode;

//import edu.wpi.first.MotorSafety;
//import edu.wpi.first.motorcontrol.PWMMotorController;
//import edu.wpi.first.motorcontrol.PWMVictorSPX;


import frc.robot.Constants;
import frc.robot.OI;

>>>>>>> ee3fa6992441453660423ae4707e40103058302c

public class DriveTrain extends SubsystemBase {
	private DriveTrain driveTrain=null;
	public OI input= OI.getInstance();
	private DifferentialDrive diffDrive;

	//Constructor
	public DriveTrain() {
		//Left side wheel motors
		VictorSPX driveFrontLeft=new VictorSPX(Constants.MCID.get("Wheel Front Left"));
		VictorSPX driveBackLeft=new VictorSPX(Constants.MCID.get("Wheel Back Left"));
		MotorControllerGroup driveLeft=new MotorControllerGroup(driveFrontLeft, driveBackLeft);
		//Right side wheel motors
		VictorSPX driveFrontRight=new VictorSPX(Constants.MCID.get("Wheel Front Right"));
		VictorSPX driveBackRight=new VictorSPX(Constants.MCID.get("Wheel Back Right"));
		MotorControllerGroup driveRight=new MotorControllerGroup(driveFrontRight, driveBackRight);
		//Creates differential drive
		diffDrive=new DifferentialDrive(driveLeft, driveRight);
		//Reverses right motor direction
		driveLeft.Invert();
	}

	//Initializes the the drive train if necessary and returns the drive train
	public DriveTrain getDriveTrain() {
		if(driveTrain==null) {
			driveTrain=new DriveTrain();
		}
		return driveTrain;
	}


    
  }

	/*Instantiates the arcade drive
	 *OVERLOADED FUNCTION
	 */
	public void ArcadeDrived() {  //Ruben, is this method named correctly???
		getDriveTrain().diffDrive.arcadeDrive(input.getThrottle(), input.getSteering());
	}

	/*Instantiates the arcade drive
	 *OVERLOADED FUNCTION
	 */
	public void ArcadeDrived(DriveTrain train) {  //Ruben, is this method named correctly???
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
