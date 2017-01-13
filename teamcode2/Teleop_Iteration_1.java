package org.firstinspires.ftc.teamcode.Teleop;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by SaajanPatel on 12/3/16.
 */

@TeleOp(name="Teleop version 1.0", group="Teleop2016")
    class Teleop_Iteration_1 extends OpMode{
    GamepadWrapper joy1;
    GamepadWrapper joy2;
    Drivetrain drivetrain = new Drivetrain();
    ParticleAccelerator Shooter;
    org.firstinspires.ftc.teamcode.Teleop.Pickup Pickup;
    Servo leftServo;
    Servo rightServo;


    @Override
    public void init() {
        drivetrain.init(hardwareMap);
        Pickup = new Pickup("Pickup", hardwareMap);
        Shooter = new ParticleAccelerator("ShooterLeft", "ShooterRight", hardwareMap);
        leftServo = hardwareMap.servo.get("left servo");
        rightServo = hardwareMap.servo.get("right servo");

        joy1 = new GamepadWrapper();
        joy2 = new GamepadWrapper();

    }


    @Override
    public void init_loop(){
    }

    @Override
    public void start(){
    }

    /**
     *  Hold right trigger to shoot, press lightly to shoot slower
     */
    @Override
    public void loop() {
        /**
         *  Driving the Robot using left and right stick
         */
        joy1.update(gamepad1);
        joy2.update(gamepad2);
        double left;
        double right;
        left = -gamepad1.left_stick_y;
        right = -gamepad1.right_stick_y;
        drivetrain.Left1.setPower(left);
        drivetrain.Left2.setPower(left);
        drivetrain.Right1.setPower(right);
        drivetrain.Right2.setPower(right);

        //Press a to have to move servos out
        if (joy1.toggle.a)
        {
            for (int i = 0; i < 2; i++) {
                leftServo.setPosition(0.0);
                rightServo.setPosition(0.0);
            }
        }
        else
        {
            leftServo.setPosition(1.0);
            rightServo.setPosition(1.0);
        }

        double posLeft = leftServo.getPosition();
        double posRight = rightServo.getPosition();
        telemetry.addData("Test servo position: ", Double.toString(posLeft));
        telemetry.addData("Test servo position: ", Double.toString(posRight));

        //Toggle left bumper for pickup

        if (joy1.toggle.left_bumper) {
            Pickup.Reverse();
            Pickup.run();

        }
        else if (!joy1.toggle.b &&!joy1.toggle.left_bumper)   {
            Pickup.Reverse();
            Pickup.stop();

        }


        /**
         *  Toggle right bumper to shoots
         */


        if (joy1.toggle.right_bumper){

        Shooter.run();
        }
        else {


            Shooter.stop();
        }



        /**
         *  Press B to reverse Pickup
         */

        if (joy1.toggle.b  ) {
            Pickup.Forward();
            Pickup.run();
        }
        else if (!joy1.toggle.left_bumper && !joy1.toggle.b  ){
            Pickup.Reverse();
            Pickup.stop();


        }

        if (joy1.toggle.back  ) {
            Pickup.stop();
            Pickup.Forward();
            Shooter.stop();


        }







        telemetry.addData("left", "%.2f", left);
        telemetry.addData("right", "%.2f", right);
        telemetry.addData("Shooter", Shooter);

        telemetry.addData("Pickup", Pickup);

        telemetry.update();

    }
}
