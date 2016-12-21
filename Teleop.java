package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by SaajanPatel on 12/3/16.
 */

@TeleOp(name="Teleop version 1.0", group="Teleop2016")
class Teleop_Iteration_1 extends OpMode{
    GamepadWrapper joy1;
    GamepadWrapper joy2;
    Drivetrain drivetrain = new Drivetrain();
    ParticleAccelerator Shooter;
    Pickup Pickup;
    //Column column;

    @Override
    public void init() {
        drivetrain.init(hardwareMap);
        Pickup = new Pickup("Pickup", hardwareMap);
        Shooter = new ParticleAccelerator("ShooterLeft", "ShooterRight", hardwareMap);
        // column = new Column("Column", hardwareMap);
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


        /**
         *  Hold right trigger to pickup, press lightly to pickup slower
         */


        if (joy1.toggle.left_trigger > 0.2 && joy1.toggle.left_trigger < 0.5) {

            Pickup.run();

        }
        else if (joy1.toggle.left_trigger >= 0.5) {

            Pickup.run();
            Pickup.run();
        }
        else {
            Pickup.stop();
            Pickup.stop();
        }


        /**
         *  Hold right trigger to shoot, press lightly to shoot slower
         */


        if (joy1.toggle.right_trigger > 0.3 && joy1.toggle.right_trigger < 0.5) {

            Shooter.run();
        }
        else if (joy1.toggle.right_trigger >= 0.5) {

            Shooter.run();
            Shooter.run();
        }
        else {
            Shooter.stop();
            Shooter.stop();
        }


        /**
         *  Press a to Pickup and Shoot
         */


        if (joy1.toggle.a) {
            Shooter.run();
            Shooter.run();
            Pickup.run();
            Pickup.run();
        }
        else {
            Shooter.stop();
            Shooter.stop();
            Pickup.stop();
            Pickup.stop();
        }

        /**
         *  Press B to reverse Pickup
         */

        if (joy1.toggle.b) {
            Pickup.Forward();
            Pickup.run();
        }
        else {
            Pickup.Reverse();
            Pickup.stop();
            Pickup.stop();

        }





        telemetry.addData("left", "%.2f", left);
        telemetry.addData("right", "%.2f", right);
        telemetry.addData("Shooter", Shooter);

        telemetry.addData("Pickup", Pickup);


    }
}
