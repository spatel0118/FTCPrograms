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

        ShooterLeft.setDirection(DcMotor.Direction.FORWARD);
        ShooterRight.setDirection(DcMotor.Direction.REVERSE);
    }


    public ParticleAccelerator(String ShooterRight, String ShooterLeft, HardwareMap hardwareMap) {
        this.ShooterRight = hardwareMap.dcMotor.get(ShooterRight);
        this.ShooterLeft = hardwareMap.dcMotor.get(ShooterLeft);
    }

    public void run10() {
        ShooterLeft.setPower(1.0);
        ShooterRight.setPower(-1.0);

        AcceleratorState = acceleratorEnum.Run;
    }
    public void run9() {
        ShooterLeft.setPower(0.9);
        ShooterRight.setPower(-0.9);

        AcceleratorState = acceleratorEnum.Run;
    }
    public void run8() {
        ShooterLeft.setPower(0.8);
        ShooterRight.setPower(-0.8);

        AcceleratorState = acceleratorEnum.Run;
    }
    public void run7() {
        ShooterLeft.setPower(0.7);
        ShooterRight.setPower(-0.7);

        AcceleratorState = acceleratorEnum.Run;
    }

    public void run6() {
        ShooterLeft.setPower(0.6);
        ShooterRight.setPower(-0.6);

        AcceleratorState = acceleratorEnum.Run;
    }

    public void run5() {
        ShooterLeft.setPower(0.5);
        ShooterRight.setPower(-0.5);

        AcceleratorState = acceleratorEnum.Run;
    }
    public void run4() {
        ShooterLeft.setPower(0.4);
        ShooterRight.setPower(-0.4);

        AcceleratorState = acceleratorEnum.Run;
    }

    public void run3() {
        ShooterLeft.setPower(0.3);
        ShooterRight.setPower(-0.3);

        AcceleratorState = acceleratorEnum.Run;
    }

    public void run2() {
        ShooterLeft.setPower(0.2);
        ShooterRight.setPower(-0.2);

        AcceleratorState = acceleratorEnum.Run;
    }

    public void run1() {
        ShooterLeft.setPower(0.1);
        ShooterRight.setPower(-0.1);

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

