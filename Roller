    package org.firstinspires.ftc.teamcode.Teleop;
    import com.qualcomm.robotcore.hardware.DcMotor;
    import com.qualcomm.robotcore.hardware.DcMotorSimple;
    import com.qualcomm.robotcore.hardware.HardwareMap;

public class Pickup {



    private enum pickupEnum {Run, Stop}
    private enum rollerEnum {Run, Stop}
    private pickupEnum PickupState = pickupEnum.Stop;
    private rollerEnum RollerState = rollerEnum.Stop; 

    public DcMotor Pickup = null;
    public DcMotor Roller = null; 
    HardwareMap hwMap = null;


    public void init(HardwareMap ahwMap) {
        //Save reference to Hardware map
        hwMap = ahwMap;
        Pickup = hwMap.dcMotor.get("Pickup");
        Roller = hwMap.dcMotor.get ("Roller" );
        Pickup.setDirection(DcMotor.Direction.FORWARD);
        Roller.setDirection(DcMotor.Direction.REVERSE);

    }

    public Pickup(String Pickup, HardwareMap hardwareMap) {
        this.Pickup = hardwareMap.dcMotor.get(Pickup);
    }

    public void Reverse()
    {
        Pickup.setDirection(DcMotor.Direction.REVERSE);
    }


    public void Forward()
    {
        Pickup.setDirection(DcMotor.Direction.FORWARD);
    }

    public void run() {
        Pickup.setPower(1.0); PickupState = pickupEnum.Run;
    }

    public void stop() {
        Pickup.setPower(0); PickupState = pickupEnum.Stop;
    }

    public Roller(String Roller, HardwareMap hardwareMap) {
        this.Roller = hardwareMap.dcMotor.get(Roller);
    }

    public void ReverseRoller ()
    {
        Roller.setDirection(DcMotor.Direction.REVERSE);
    }


    public void ForwardRoller()
    {
        Roller.setDirection(DcMotor.Direction.FORWARD);
    }

    public void runRoller() {
        Roller.setPower(1.0); RollerState = pickupEnum.Run;
    }

    public void stopRoller() {
        Roller.setPower(0); RollerState = pickupEnum.Stop;
    }
    @Override
    public String toString() {
        switch (PickupState){
            case Run:
                return "Running";
            case Stop:
                return "Stopped";
            default:
                return "Unknown";
        }
    }
}
