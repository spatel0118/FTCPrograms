package org.firstinspires.ftc.teamcode;

import ftclib.FtcOpMode;
import trclib.TrcDbgTrace;
import trclib.TrcEvent;
import trclib.TrcRobot;
import trclib.TrcStateMachine;
import trclib.TrcTimer;

public class AutoDriveStraight implements TrcRobot.AutoStrategy
{
    private enum State
    {
        DO_DELAY,
        DRIVE_DISTANCE,
        DONE
    }   //enum State

    private static final String moduleName = "AutoDriveStraight";

    private TrcDbgTrace tracer = FtcOpMode.getGlobalTracer();

    private Robot robot;
    private double delay;
    private double distance;
    private TrcEvent event;
    private TrcTimer timer;
    private TrcStateMachine<State> sm;

    public AutoDriveStraight(Robot robot, double delay, double distance)
    {
        this.robot = robot;
        this.delay = delay;
        this.distance = distance;
        event = new TrcEvent(moduleName);
        timer = new TrcTimer(moduleName);
        sm = new TrcStateMachine<>(moduleName);
        sm.start(State.DO_DELAY);
    }   //AutoDriveStraight

    @Override
    public void autoPeriodic(double elapsedTime)
    {
        //
        // Print debug info.
        //
        State state = sm.getState();
        robot.dashboard.displayPrintf(1, "State: %s", state != null? state.toString(): "Disabled");

        if (sm.isReady())
        {
            state = sm.getState();

            robot.traceStateInfo(elapsedTime, state.toString());
            switch (state)
            {
                case DO_DELAY:
                    if (delay == 0.0)
                    {
                        sm.setState(State.DRIVE_DISTANCE);
                    }
                    else
                    {
                        timer.set(delay, event);
                        sm.waitForSingleEvent(event, State.DRIVE_DISTANCE);
                    }
                    break;

                case DRIVE_DISTANCE:
                    //
                    // Drive the set distance.
                    //
                    robot.pidDrive.setTarget(distance*12.0, 0.0, false, event);
                    sm.waitForSingleEvent(event, State.DONE);
                    break;

                case DONE:
                default:
                    //
                    // We are done.
                    //
                    sm.stop();
                    break;
            }
        }
    }

}   //class AutoDriveStraight
