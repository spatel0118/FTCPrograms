package org.firstinspires.ftc.teamcode;
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


    public void init(HardwareMap ahwMap) {
        //Save reference to Hardware map
        hwMap = ahwMap;
        ShooterRight = hwMap.dcMotor.get("ShooterRight");
        ShooterLeft = hwMap.dcMotor.get("ShooterLeft");

        ShooterLeft.setDirection(DcMotor.Direction.REVERSE);
        ShooterRight.setDirection(DcMotor.Direction.REVERSE);
    }


    public ParticleAccelerator(String ShooterRight, String ShooterLeft, HardwareMap hardwareMap) {
        this.ShooterRight = hardwareMap.dcMotor.get(ShooterRight);
        this.ShooterLeft = hardwareMap.dcMotor.get(ShooterLeft);
    }


    public void run() {
        ShooterLeft.setPower(1.0);
        ShooterRight.setPower(1.0);

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

