package org.firstinspires.ftc.teamcode;

/**
 * Created by SaajanPatel on 12/29/16.
 */


import android.widget.TextView;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import ftclib.FtcAnalogGyro;
import ftclib.FtcAndroidTone;
import ftclib.FtcDcMotor;
import ftclib.FtcMRColorSensor;
import ftclib.FtcMRGyro;
import ftclib.FtcMRRangeSensor;
import ftclib.FtcOpMode;
import ftclib.FtcOpticalDistanceSensor;
import ftclib.FtcRobotBattery;
import ftclib.FtcServo;
import hallib.HalDashboard;
import trclib.TrcAnalogTrigger;
import trclib.TrcDbgTrace;
import trclib.TrcDriveBase;
import trclib.TrcGyro;
import trclib.TrcPidController;
import trclib.TrcPidDrive;
import trclib.TrcRobot;

public class Robot implements TrcPidController.PidInput, TrcAnalogTrigger.TriggerHandler
{
    public static final boolean USE_RANGE_SENSOR = true;
    public static final boolean USE_LINE_DETECTOR = true;
    public static final boolean USE_ODS_LINE_DETECTOR = true;
    public static final boolean USE_COLOR_SENSOR = true;
    public static final boolean USE_ANALOG_GYRO = true;

    private static final String moduleName = "Robot";
    //
    // Global objects.
    //
    public FtcOpMode opmode;
    public HardwareMap hardwareMap;
    public HalDashboard dashboard;
    public FtcRobotControllerActivity activity;
    public TrcDbgTrace tracer;
    //
    // Sensors.
    //
    public TrcGyro gyro = null;
    public FtcMRColorSensor beaconColorSensor = null;
    public FtcMRColorSensor lineDetectionSensor = null;
    public FtcOpticalDistanceSensor odsLineDetector = null;
    public FtcMRRangeSensor rangeSensor = null;
    private double prevRangeValue = 0.0;
    //
    // DriveBase subsystem.
    //
    public FtcDcMotor Left1 = null;
    public FtcDcMotor Right1 = null;
    public FtcDcMotor Left2 = null;
    public FtcDcMotor Right2 = null;
    public TrcDriveBase driveBase = null;
    public FtcRobotBattery battery = null;
    public FtcAndroidTone androidTone = null;

    public TrcPidController encoderXPidCtrl = null;
    public TrcPidController encoderYPidCtrl = null;
    public TrcPidController gyroPidCtrl = null;
    public TrcPidController rangePidCtrl = null;
    public TrcPidDrive pidDrive = null;
    public TrcPidDrive rangePidDrive = null;

    public TrcAnalogTrigger lineTrigger = null;
    public double targetHeading = 0.0;
    //
    // Other subsystems.
    //
    public Shooter shooter = null;
    public FtcServo leftButtonPusher = null;
    public FtcServo rightButtonPusher = null;
    public FtcServo whisker = null;
    public FtcDcMotor ballPickUp = null;
    public FtcDcMotor conveyor = null;

    public Robot(TrcRobot.RunMode runMode)
    {
        //
        // Initialize global objects.
        //
        opmode = FtcOpMode.getInstance();
        hardwareMap = opmode.hardwareMap;
        dashboard = HalDashboard.getInstance();
        activity = (FtcRobotControllerActivity)hardwareMap.appContext;
        hardwareMap.logDevices();
        dashboard.setTextView((TextView)activity.findViewById(FtcSampleCode.R.id.textOpMode));
        tracer = FtcOpMode.getGlobalTracer();
        //
        // Initialize sensors.
        //
        if (USE_ANALOG_GYRO)
        {
            gyro = new FtcAnalogGyro("analogGyro", RobotInfo.ANALOG_GYRO_VOLT_PER_DEG_PER_SEC);
            ((FtcAnalogGyro)gyro).calibrate();
        }
        else
        {
            gyro = new FtcMRGyro("gyroSensor");
            ((FtcMRGyro)gyro).calibrate();
        }

        if (USE_COLOR_SENSOR)
        {
            beaconColorSensor = new FtcMRColorSensor("colorSensor");
            beaconColorSensor.sensor.enableLed(false);
            beaconColorSensor.setEnabled(false);
        }

        if (USE_LINE_DETECTOR)
        {
            if (USE_ODS_LINE_DETECTOR)
            {
                odsLineDetector = new FtcOpticalDistanceSensor("odsLineDetector");
            }
            else
            {
                lineDetectionSensor = new FtcMRColorSensor("lineDetectionSensor");
                lineDetectionSensor.sensor.setI2cAddress(I2cAddr.create8bit(0x40));
                lineDetectionSensor.sensor.enableLed(true);
                lineDetectionSensor.setEnabled(false);
            }
        }

        if (USE_RANGE_SENSOR)
        {
            rangeSensor = new FtcMRRangeSensor("rangeSensor");
        }
        //
        // Initialize DriveBase.
        //
        Left1 = new FtcDcMotor("leftFrontWheel");
        Right1 = new FtcDcMotor("rightFrontWheel");
        Left2 = new FtcDcMotor("leftRearWheel");
        Right2 = new FtcDcMotor("rightRearWheel");

        Left1.motor.setMaxSpeed(RobotInfo.DRIVE_MAX_SPEED);
        Right1.motor.setMaxSpeed(RobotInfo.DRIVE_MAX_SPEED);
        Left2.motor.setMaxSpeed(RobotInfo.DRIVE_MAX_SPEED);
        Right2.motor.setMaxSpeed(RobotInfo.DRIVE_MAX_SPEED);
        Left1.motor.setMode(RobotInfo.DRIVE_MOTOR_MODE);
        Right1.motor.setMode(RobotInfo.DRIVE_MOTOR_MODE);
        Left2.motor.setMode(RobotInfo.DRIVE_MOTOR_MODE);
        Right2.motor.setMode(RobotInfo.DRIVE_MOTOR_MODE);

        Left1.setInverted(true);
        Left2.setInverted(true);

        driveBase = new TrcDriveBase(Left1, Left2, Right1, Right2, gyro);
        driveBase.setXPositionScale(RobotInfo.ENCODER_X_INCHES_PER_COUNT);
        driveBase.setYPositionScale(RobotInfo.ENOCDER_Y_INCHES_PER_COUNT);

        battery = new FtcRobotBattery(Left1.motor.getController());
        //
        // Initialize tone device.
        //
        androidTone = new FtcAndroidTone("AndroidTone");
        //
        // Initialize PID drive.
        //
        encoderXPidCtrl = new TrcPidController(
                "encoderXPidCtrl",
                RobotInfo.ENCODER_X_KP, RobotInfo.ENCODER_X_KI, RobotInfo.ENCODER_X_KD, RobotInfo.ENCODER_X_KF,
                RobotInfo.ENCODER_X_TOLERANCE, RobotInfo.ENCODER_X_SETTLING,
                this);
        encoderYPidCtrl = new TrcPidController(
                "encoderYPidCtrl",
                RobotInfo.ENCODER_Y_KP, RobotInfo.ENCODER_Y_KI, RobotInfo.ENCODER_Y_KD, RobotInfo.ENCODER_Y_KF,
                RobotInfo.ENCODER_Y_TOLERANCE, RobotInfo.ENCODER_Y_SETTLING,
                this);
        gyroPidCtrl = new TrcPidController(
                "gyroPidCtrl",
                RobotInfo.GYRO_KP, RobotInfo.GYRO_KI, RobotInfo.GYRO_KD, RobotInfo.GYRO_KF,
                RobotInfo.GYRO_TOLERANCE, RobotInfo.GYRO_SETTLING,
                this);
        gyroPidCtrl.setAbsoluteSetPoint(true);
        gyroPidCtrl.setOutputRange(-RobotInfo.TURN_POWER_LIMIT, RobotInfo.TURN_POWER_LIMIT);
        if (USE_RANGE_SENSOR)
        {
            rangePidCtrl = new TrcPidController(
                    "rangePidCtrl",
                    RobotInfo.RANGE_KP, RobotInfo.RANGE_KI, RobotInfo.RANGE_KD, RobotInfo.RANGE_KF,
                    RobotInfo.RANGE_TOLERANCE, RobotInfo.RANGE_SETTLING,
                    this);
            rangePidCtrl.setAbsoluteSetPoint(true);
            rangePidCtrl.setNoOscillation(true);
        }

        pidDrive = new TrcPidDrive("pidDrive", driveBase, encoderXPidCtrl, encoderYPidCtrl, gyroPidCtrl);
        pidDrive.setStallTimeout(RobotInfo.PIDDRIVE_STALL_TIMEOUT);
        pidDrive.setBeep(androidTone);

        if (USE_LINE_DETECTOR)
        {
            double[] lightZones = {RobotInfo.LINE_DARK_LEVEL, RobotInfo.LINE_WHITE_LEVEL};

            if (USE_ODS_LINE_DETECTOR)
            {
                lineTrigger = new TrcAnalogTrigger("lineTrigger", odsLineDetector, 0, lightZones, this);
            }
            else
            {
                lineTrigger = new TrcAnalogTrigger("lineTrigger", lineDetectionSensor, 0, lightZones, this);
            }
        }

        if (USE_RANGE_SENSOR)
        {
            rangePidDrive = new TrcPidDrive("rangePidDrive", driveBase, rangePidCtrl, encoderYPidCtrl, gyroPidCtrl);
            rangePidDrive.setStallTimeout(RobotInfo.PIDDRIVE_STALL_TIMEOUT);
            rangePidDrive.setBeep(androidTone);
        }

        //
        // Initialize other subsystems.
        //

        shooter = new Shooter("shooter");

        leftButtonPusher = new FtcServo("leftButtonPusherServo");
        leftButtonPusher.setPosition(RobotInfo.BUTTON_PUSHER_RETRACT_POSITION);
        rightButtonPusher = new FtcServo("rightButtonPusherServo");
        rightButtonPusher.setPosition(RobotInfo.BUTTON_PUSHER_RETRACT_POSITION);
        whisker = new FtcServo("whiskerServo");
        whisker.setPosition(RobotInfo.FIND_WALL_POSITION);
        //ballPickUp = new FtcDcMotor("pickUpMotor");
       // ballPickUp.setInverted(true);

        //conveyor = new FtcDcMotor("conveyorMotor");
    }   //Robot

    public void startMode(TrcRobot.RunMode runMode)
    {
        if (USE_LINE_DETECTOR && !USE_ODS_LINE_DETECTOR)
        {
            lineDetectionSensor.sensor.enableLed(true);
        }
        gyro.setEnabled(true);
        driveBase.resetPosition();
        targetHeading = 0.0;
    }   //startMode

    public void stopMode(TrcRobot.RunMode runMode)
    {
        shooter.stop();
        gyro.setEnabled(false);

        if (USE_LINE_DETECTOR && !USE_ODS_LINE_DETECTOR)
        {
            lineDetectionSensor.sensor.enableLed(false);
        }
    }   //stopMode

    //
    // Implements TrcPidController.PidInput
    //

    @Override
    public double getInput(TrcPidController pidCtrl)
    {
        double input = 0.0;

        if (pidCtrl == encoderXPidCtrl)
        {
            input = driveBase.getXPosition();
        }
        else if (pidCtrl == encoderYPidCtrl)
        {
            input = driveBase.getYPosition();
        }
        else if (pidCtrl == gyroPidCtrl)
        {
            input = driveBase.getHeading();
        }
        else if (pidCtrl == rangePidCtrl)
        {
            input = rangeSensor.sensor.getDistance(DistanceUnit.INCH);
            //
            // The range sensor sometimes does not detect the echo and returns 255cm (approx. 100.4 inches).
            // If so, throw it away and use the previous value instead.
            //
            if (input > 100.0 && prevRangeValue <= 100.0)
            {
                input = prevRangeValue;
            }
            else
            {
                prevRangeValue = input;
            }
        }

        return input;
    }   //getInput

    //
    // Implements TrcAnalogTrigger.TriggerHandler
    //

    @Override
    public void AnalogTriggerEvent(TrcAnalogTrigger analogTrigger, int zoneIndex, double zoneValue)
    {
        tracer.traceInfo(moduleName, "%s: Entering zone %d (%.2f).", analogTrigger.toString(), zoneIndex, zoneValue);
        if (analogTrigger == lineTrigger && pidDrive.isEnabled())
        {
            if (zoneIndex > 0)
            {
                //
                // Encountering white line, abort PID drive.
                //
                pidDrive.cancel();
            }
        }
    }   //AnalogTriggerEvent

    public void setTurnPID(double xDistance, double yDistance, double heading)
    {
        double degrees = Math.abs(heading - driveBase.getHeading());

        if (xDistance != 0.0 || yDistance != 0)
        {
            gyroPidCtrl.setPID(RobotInfo.GYRO_KP, RobotInfo.GYRO_KI, RobotInfo.GYRO_KD, 0.0);
        }
        else if (degrees < RobotInfo.SMALL_TURN_THRESHOLD)
        {
            //
            // We are turning a small angle, use stronger PID.
            //
            gyroPidCtrl.setPID(RobotInfo.GYRO_SMALL_TURN_KP, RobotInfo.GYRO_SMALL_TURN_KI,
                    RobotInfo.GYRO_SMALL_TURN_KD, 0.0);
        }
        else if (degrees < RobotInfo.LARGE_TURN_THRESHOLD)
        {
            //
            // We are turning a medium angle, use normal PID.
            //
            gyroPidCtrl.setPID(RobotInfo.GYRO_KP, RobotInfo.GYRO_KI, RobotInfo.GYRO_KD, 0.0);
        }
        else
        {
            //
            // We are turning a large angle, use weaker PID.
            //
            gyroPidCtrl.setPID(RobotInfo.GYRO_LARGE_TURN_KP, RobotInfo.GYRO_LARGE_TURN_KI,
                    RobotInfo.GYRO_LARGE_TURN_KD, 0.0);
        }
    }   //setTurnPID

    public void traceStateInfo(double elapsedTime, String stateName)
    {
        tracer.traceInfo(
                moduleName, "[%5.3f] %17s: xPos=%6.2f,yPos=%6.2f,heading=%6.1f/%6.1f,range=%5.2f,volt=%5.2fV(%5.2fV)",
                elapsedTime, stateName,
                driveBase.getXPosition(), driveBase.getYPosition(), driveBase.getHeading(), targetHeading,
                getInput(rangePidCtrl), battery.getCurrentVoltage(), battery.getLowestVoltage());
    }   //traceStateInfo

    public double selectParameter(
            boolean startNear, FtcAuto.Alliance alliance,
            double nearRed, double nearBlue, double farRed, double farBlue)
    {
        if (startNear)
        {
            if (alliance == FtcAuto.Alliance.RED_ALLIANCE)
            {
                return nearRed;
            }
            else
            {
                return nearBlue;
            }
        }
        else
        {
            if (alliance == FtcAuto.Alliance.RED_ALLIANCE)
            {
                return farRed;
            }
            else
            {
                return farBlue;
            }
        }
    }   //selectParameter

}   //class Robot