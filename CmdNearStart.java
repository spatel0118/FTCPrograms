package org.firstinspires.ftc.teamcode.Autonomous;

import ftclib.FtcOpMode;
import trclib.TrcDbgTrace;
import trclib.TrcEvent;
import trclib.TrcRobot;
import trclib.TrcStateMachine;
import trclib.TrcTimer;

public class CmdNearStart implements TrcRobot.RobotCommand
{
    private static final boolean debugXPid = false;
    private static final boolean debugYPid = false;
    private static final boolean debugTurnPid = false;
    private TrcDbgTrace tracer = FtcOpMode.getGlobalTracer();

    private enum State
    {
        SHOOT_PARTICLES,
        MOVE_OUT,
        TURN_TO_CAPBALL,
        KNOCK_OUT_CAPBALL,
        DONE
    }   //enum State

    private static final String moduleName = "CmdNearStart";

    private Robot robot;
    private FtcAuto.Alliance alliance;
    private double delay;
    private int numParticles;
    private boolean shortRun;
    private TrcEvent event;
    private TrcTimer timer;
    private TrcStateMachine<State> sm;
    private boolean particleLoaded = true;

    public CmdNearStart(Robot robot, FtcAuto.Alliance alliance, double delay, int numParticles, boolean shortRun)
    {
        this.robot = robot;
        this.alliance = alliance;
        this.delay = delay;
        this.numParticles = numParticles;
        this.shortRun = shortRun;
        event = new TrcEvent(moduleName);
        timer = new TrcTimer(moduleName);
        sm = new TrcStateMachine<>(moduleName);
        sm.start(State.SHOOT_PARTICLES);
    }   //CmdNearStart

    //
    // Implements the TrcRobot.RobotCommand interface.
    //

    @Override
    public boolean cmdPeriodic(double elapsedTime)
    {
        boolean done = !sm.isEnabled();
        //
        // Print debug info.
        //
        State state = sm.getState();
        robot.dashboard.displayPrintf(1, "State: %s", state != null? sm.getState().toString(): "Disabled");

        if (sm.isReady())
        {
            state = sm.getState();
            double xDistance, yDistance;

            switch (state)
            {
                case SHOOT_PARTICLES:
                    tracer.traceInfo(state.toString(), "NumParticles=%d", numParticles);
                    //
                    // Fire a particle if any.
                    //
                    if (numParticles > 0)
                    {
                        if (particleLoaded)
                        {
                            robot.shooter.fireOneShot(event);
                            particleLoaded = false;
                        }
                        else
                        {
                            robot.shooter.loadAndFireOneShot(event);
                        }
                        numParticles--;
                        sm.waitForSingleEvent(event, State.SHOOT_PARTICLES);
                    }
                    //
                    // Do delay if any.
                    //
                    else
                    {
                        if (delay > 0.0 && delay - elapsedTime > 0)
                        {
                            timer.set(delay - elapsedTime, event);
                            sm.waitForSingleEvent(event, State.MOVE_OUT);
                        }
                        else
                        {
                            sm.setState(State.MOVE_OUT);
                        }
                    }
                    break;

                case MOVE_OUT:
                    xDistance = 0.0;
                    yDistance = 35.0;

                    robot.setPIDDriveTarget(xDistance, yDistance, robot.targetHeading, false, event);
                    sm.waitForSingleEvent(event, State.TURN_TO_CAPBALL);
                    break;

                case TURN_TO_CAPBALL:
                    xDistance = yDistance = 0.0;
                    robot.targetHeading = alliance == FtcAuto.Alliance.RED_ALLIANCE? -30.0: 30.0;

                    robot.setPIDDriveTarget(xDistance, yDistance, robot.targetHeading, false, event);
                    sm.waitForSingleEvent(event, State.KNOCK_OUT_CAPBALL);
                    break;

                case KNOCK_OUT_CAPBALL:
                    xDistance = 0.0;
                    yDistance = shortRun? 42.0: 60.0;

                    robot.setPIDDriveTarget(xDistance, yDistance, robot.targetHeading, false, event);
                    sm.waitForSingleEvent(event, State.DONE);
                    break;

                case DONE:
                default:
                    //
                    // We are done.
                    //
                    sm.stop();
                    done = true;
                    break;
            }
            robot.traceStateInfo(elapsedTime, state.toString());
        }

        if (robot.pidDrive.isActive() && (debugXPid || debugYPid || debugTurnPid))
        {
            tracer.traceInfo("Battery", "Voltage=%5.2fV (%5.2fV)",
                    robot.battery.getCurrentVoltage(), robot.battery.getLowestVoltage());

            if (debugXPid)
            {
                robot.encoderXPidCtrl.printPidInfo(tracer);
            }

            if (debugYPid)
            {
                robot.encoderYPidCtrl.printPidInfo(tracer);
            }

            if (debugTurnPid)
            {
                robot.gyroPidCtrl.printPidInfo(tracer);
            }
        }

        return done;
    }   //cmdPeriodic

}   //class CmdNearStart
