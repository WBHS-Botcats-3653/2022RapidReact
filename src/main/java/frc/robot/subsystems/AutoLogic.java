package frc.robot.subsystems;

//import static frc.robot.Constants.ClimberConstants.kArmMotorID;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class AutoLogic {
      
    private static DriveTrain m_driveTrain = DriveTrain.getInstance();
    private static Direction m_direction = Direction.getInstance();
    /**
     * 
     * @return true while the method is running
     */
    private boolean isRunningMethod(){
        return true;
    }
    /**
     * 
     * @return true when a method has been "scheduled"
     */
    private boolean hasBeenExecuted(){
        return true;
    }
    /**
     * 
     * @param sendable this is the lambda that will be executed once
     * @return true when the  has been executed and
     * false, while it hasn't
     */
    private boolean runWhenInitialized(Sendable sendable){

        if(/*sendable == */true){
            return true;
        } else{
            return false;
        }
    }
    /**
     * 
     * @param distance
     * @param speed
     * @param isCollectingCargo
     * @return false while it is executing, true when it finishes
     */
    public static boolean DriveMethod(double distance, double speed, boolean isCollectingCargo) {
		//this.distance = -distance;  //Inverted (moves in the correct direction)
		//this.speed = speed * (this.distance < 0 ? -1 : 1);
		//this.isCollectingCargo = isCollectingCargo;
        /*
        String s;
        switch(s){
            case 1:

        }
        */
        if(10 == 10){
            return true;
        }
        return false;

	}
}
