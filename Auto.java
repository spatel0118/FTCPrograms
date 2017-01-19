package org.firstinspires.ftc.teamcode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by Colin Zhu on 1/16/17.
 */

@Autonomous(name="Auto", group="Test")
   public class Auto extends LinearOpMode {

    ColorSensor color_sensor;

    public DcMotor ShooterRight = null;
    public DcMotor ShooterLeft = null;

    public DcMotor Pickup = null;

    public DcMotor Left1 = null;
    public DcMotor Left2 = null;
    public DcMotor Right1 = null;
    public DcMotor Right2 = null;

    Servo leftServo;
    Servo rightServo;


    double DRIVE_POWER = 1;

    //Max encoder clicks per second
    public static final int SHOOTER_MAX_SPEED = 4000;


    //Percentage of speed
    public static final double SHOOTER_POWER = -0.46;

    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() throws InterruptedException {
        color_sensor = hardwareMap.colorSensor.get("Color Sensor");

        //init Shooter
        ShooterRight = hardwareMap.dcMotor.get("ShooterRight");
        ShooterLeft = hardwareMap.dcMotor.get("ShooterLeft");

        ShooterLeft.setDirection(DcMotor.Direction.FORWARD);
        ShooterRight.setDirection(DcMotor.Direction.REVERSE);

        ShooterLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ShooterRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        ShooterLeft.setMaxSpeed(SHOOTER_MAX_SPEED);
        ShooterRight.setMaxSpeed(SHOOTER_MAX_SPEED);

        //init Pickup
        Pickup = hardwareMap.dcMotor.get("Pickup");

        Pickup.setDirection(DcMotor.Direction.FORWARD);

        //init Drivetrain
        Left1 = hardwareMap.dcMotor.get("Left1");
        Left2 = hardwareMap.dcMotor.get("Left2");
        Right1 = hardwareMap.dcMotor.get("Right1");
        Right2 = hardwareMap.dcMotor.get("Right2");

        Left1.setDirection(DcMotor.Direction.REVERSE);
        Left2.setDirection(DcMotor.Direction.REVERSE);
        Right1.setDirection(DcMotor.Direction.FORWARD);
        Right2.setDirection(DcMotor.Direction.FORWARD);

        Left1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Left2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Right1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Right2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        //Start Autonomous


        color_sensor.enableLed(false);


        AutonomousCode(DRIVE_POWER);


    }

    public void AutonomousCode(double power) throws InterruptedException {

        runtime.reset();

        ShooterLeft.setPower(-SHOOTER_POWER);
        ShooterRight.setPower(-SHOOTER_POWER);

        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
            telemetry.addData("shooter", "starting");
            telemetry.update();

            idle();
        }

        runtime.reset();

        ShooterLeft.setPower(-SHOOTER_POWER);
        ShooterRight.setPower(-SHOOTER_POWER);

        Pickup.setPower(-DRIVE_POWER);

        while (opModeIsActive() && (runtime.seconds() < 5.0)) {
            telemetry.addData("shooter", "pickup");
            telemetry.update();

            idle();
        }

        DriveForward(DRIVE_POWER);


        double color = color_sensor.argb();
        if (color_sensor.argb() > 6) {
            telemetry.addData("Color: ", color);
        } else {
            telemetry.addData("Color: ", color);
        }

    }




    public void DriveForward(double power) {
        Left1.setPower(power);
        Left2.setPower(power);
        Right1.setPower(power);
        Right2.setPower(power);

        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 0.5)) {
            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
            idle();
        }

    }

    public void DriveBackward(double power) {
        Left1.setPower(-power);
        Left2.setPower(-power);
        Right1.setPower(-power);
        Right2.setPower(-power);

        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 0.05)) {
            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
            idle();
        }

    }

    public void TurnLeft(double Power) {
        Left1.setPower(-Power);
        Left2.setPower(-Power);
        Right1.setPower(Power);
        Right2.setPower(Power);

        runtime.reset();

        if (opModeIsActive() && (runtime.seconds() < 0.3)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
            idle();
        }
    }

    public void TurnRight(double Power) {
        Left1.setPower(Power);
        Left2.setPower(Power);
        Right1.setPower(-Power);
        Right2.setPower(-Power);

        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 1.3)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
            idle();
        }
    }

    public void RunShooterAndPickup(double Power) {
        telemetry.addData("Will This Work?", "123");
        telemetry.update();
        Pickup.setPower(Power);

        ShooterLeft.setPower(SHOOTER_POWER);
        ShooterRight.setPower(-SHOOTER_POWER);
        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 5.0)) {
            telemetry.addData("Path", "Shooter: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
            idle();
        }
    }

    public void SetServoLeft() {
        leftServo.setPosition(0.6);
    }

    public void SetServoRight() {
        rightServo.setPosition(0.4);
    }


}
