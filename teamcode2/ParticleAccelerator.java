package org.firstinspires.ftc.teamcode.Teleop;
import android.os.SystemClock;
import android.provider.Settings;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by SaajanPatel on 12/3/16.
 */

class ParticleAccelerator {
    private enum acceleratorEnum {Run, Stop}

    private acceleratorEnum AcceleratorState = acceleratorEnum.Stop;

    public DcMotor ShooterRight = null;
    public DcMotor ShooterLeft = null;
    //public DcMotor Accelerator = null;
    HardwareMap hwMap = null;


    //Max encoder clicks per second
    public static final int SHOOTER_MAX_SPEED = 4000;


    //Percentage of speed
    public static final double SHOOTER_LEFT_POWER = 0.45;
    public static final double SHOOTER_RIGHT_POWER = -0.45;


    public void init(HardwareMap ahwMap) {
        //Save reference to Hardware map
        hwMap = ahwMap;
        ShooterRight = hwMap.dcMotor.get("ShooterRight");
        ShooterLeft = hwMap.dcMotor.get("ShooterLeft");

        ShooterLeft.setDirection(DcMotor.Direction.FORWARD);
        ShooterRight.setDirection(DcMotor.Direction.REVERSE);

        ShooterLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ShooterRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        ShooterLeft.setMaxSpeed(SHOOTER_MAX_SPEED);
        ShooterRight.setMaxSpeed(SHOOTER_MAX_SPEED);

    }
    // formula for setting RPM. Ask colin mom for help
   /* public int getCurrentPos() {
                                                            //is this right?
      *  int currentPos = ShooterLeft.getCurrentPosition();
       * return currentPos;
    }
    public double getPower() {return ShooterLeft.getPower();}
    public double getRate () {
        double posC = getCurrentPos() - prevPos;
        double tChange = System.nanoTime() - priviousTime;
         previousTime = System.nanoTime();
        tChange = tChange /1e9;
        prevPos = getCurrentPos();
        return posC/ tChange;
    }
  */

    public ParticleAccelerator(String ShooterRight, String ShooterLeft, HardwareMap hardwareMap) {
        this.ShooterRight = hardwareMap.dcMotor.get(ShooterRight);
        this.ShooterLeft = hardwareMap.dcMotor.get(ShooterLeft);
    }

    public void run() {
        ShooterLeft.setPower(SHOOTER_LEFT_POWER);
        ShooterRight.setPower(SHOOTER_RIGHT_POWER);

        AcceleratorState = acceleratorEnum.Run;
    }


    public void stop() {
        ShooterLeft.setPower(0);
        ShooterRight.setPower(0);
        // Accelerator.setPower(0);
        AcceleratorState = acceleratorEnum.Stop;
    }

    @Override
    public String toString() {
        switch (AcceleratorState){
            case Run:
                return "Running";
            case Stop:
                return "Stopped";
            default:
                return "Unknown";



        }
    }
}


