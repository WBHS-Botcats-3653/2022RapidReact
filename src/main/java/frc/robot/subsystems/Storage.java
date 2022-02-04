// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//Imports WPI_VictorSPX (Motor Controller)
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

//Imports SubsystemBase
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//Imports Constants
import frc.robot.Constants;

public class Storage extends SubsystemBase {
	private static Storage storage = null;
	private WPI_VictorSPX indexer;

	/** Creates a new Indexer. */
	public Storage() {
		indexer = new WPI_VictorSPX(Constants.MCID.get("Indexer"));
	}

	public static Storage getInstance() {
		if (storage == null) {
			storage = new Storage();
		}
		return storage;
	}

	/**Raises the cargo up the storage system
	 * --This is the indexer--
	 */
	public void raiseCargo(double speed) {
		indexer.set(speed);
	}
}
