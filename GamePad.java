package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.Gamepad;
/**
 * Created by SaajanPatel on 12/3/16.
 */

class GamepadWrapper
{
    public class ButtonMap
    {
        public float left_trigger = 0;
        public float right_trigger = 0;
        public boolean dpad_up = false;
        public boolean dpad_down = false;
        public boolean dpad_right = false;
        public boolean dpad_left = false;
        public boolean a = false;
        public boolean b = false;
        public boolean x = false;
        public boolean y = false;
        public boolean guide = false;
        public boolean start = false;
        public boolean back = false;
        public boolean right_bumper = false;
        public boolean left_bumper = false;
        public boolean left_stick_button = false;
        public boolean right_stick_button = false;


    }

    public ButtonMap toggle = new ButtonMap();
    private ButtonMap previousButtonStates = new ButtonMap();

    private void updateButtonMap(ButtonMap map, Gamepad gamepad)

    {
        map.left_trigger = gamepad.left_trigger;
        map.right_trigger = gamepad.right_trigger;
        map.dpad_up = gamepad.dpad_up;
        map.dpad_down = gamepad.dpad_down;
        map.dpad_right = gamepad.dpad_right;
        map.dpad_left = gamepad.dpad_left;
        map.a = gamepad.a;
        map.b = gamepad.b;
        map.x = gamepad.x;
        map.y = gamepad.y;
        map.start = gamepad.start;
        map.back = gamepad.back;
        map.right_bumper = gamepad.right_bumper;
        map.left_bumper = gamepad.left_bumper;
        map.left_stick_button = gamepad.left_stick_button;
        map.right_stick_button = gamepad.right_stick_button;
    }

    public void update(Gamepad gamepad)
    {
        if (gamepad.right_trigger > 0 )
        {
            toggle.right_trigger = gamepad.right_trigger;
        }
        if (gamepad.left_trigger > 0 )
        {
            toggle.left_trigger = gamepad.left_trigger;
        }
        if (!gamepad.dpad_up && previousButtonStates.dpad_up)
        {
            toggle.dpad_up = !toggle.dpad_up;
        }
        if (!gamepad.dpad_down && previousButtonStates.dpad_down)
        {
            toggle.dpad_down = !toggle.dpad_down;
        }
        if (!gamepad.dpad_left && previousButtonStates.dpad_left) {
            toggle.dpad_left = !toggle.dpad_left;
        }
        if (!gamepad.dpad_right && previousButtonStates.dpad_right)
        {
            toggle.dpad_right = !toggle.dpad_right;
        }
        if (!gamepad.a && previousButtonStates.a)
        {
            toggle.a = !toggle.a;
        }
        if (!gamepad.b && previousButtonStates.b)
        {
            toggle.b = !toggle.b;
        }
        if(!gamepad.x && previousButtonStates.x)
        {
            toggle.x = !toggle.x;
        }
        if (!gamepad.y && previousButtonStates.y)
        {
            toggle.y = !toggle.y;
        }
        if (!gamepad.guide && previousButtonStates.guide)
        {
            toggle.guide = !toggle.guide;
        }
        if (!gamepad.start && previousButtonStates.start)
        {
            toggle.start = !toggle.start;
        }
        if (!gamepad.back && previousButtonStates.back)

        {
            toggle.back = !toggle.back;

        }
        if (!gamepad.right_bumper && previousButtonStates.right_bumper)
        {
            toggle.right_bumper = !toggle.right_bumper;
        }
        if (!gamepad.left_bumper && previousButtonStates.left_bumper)
        {
            toggle.left_bumper = !toggle.left_bumper;
        }
        if (!gamepad.left_stick_button && previousButtonStates.left_stick_button)
        {
            toggle.left_stick_button = !toggle.left_stick_button;
        }
        if (!gamepad.right_stick_button && previousButtonStates.right_stick_button)
        {
            toggle.right_stick_button = !toggle.right_stick_button;
        }
        updateButtonMap(previousButtonStates, gamepad);
    }

    @Override
    public String toString()
    {
        String toggleStr = "";
        if(toggle.right_trigger>0){
            toggleStr = toggleStr + "tog.right_trigger " +  toggle.right_trigger;
        }
        if(toggle.left_trigger>0){
            toggleStr = toggleStr + "tog.left_trigger " +  toggle.left_trigger;
        }
        if(toggle.dpad_up) {
            toggleStr = toggleStr + "tog.dpad_up ";
        }
        if(toggle.dpad_down) {
            toggleStr = toggleStr + "tog.dpad_down ";
        }
        if(toggle.dpad_left) {
            toggleStr = toggleStr + "tog.dpad_left ";
        }
        if(toggle.dpad_right) {
            toggleStr = toggleStr + "tog.dpad_right ";
        }
        if(toggle.a) {
            toggleStr = toggleStr + "tog.a ";
        }
        if(toggle.b) {
            toggleStr = toggleStr + "tog.b ";
        }
        if(toggle.x) {
            toggleStr = toggleStr + "tog.x ";
        }
        if(toggle.y) {
            toggleStr = toggleStr + "tog.y ";
        }
        if(toggle.guide) {
            toggleStr = toggleStr + "tog.guide ";
        }
        if(toggle.start) {
            toggleStr = toggleStr + "tog.start ";
        }
        if(toggle.back) {
            toggleStr = toggleStr + "tog.back ";
        }
        if(toggle.left_bumper) {
            toggleStr = toggleStr + "tog.left_bumper ";
        }
        if(toggle.right_bumper) {
            toggleStr = toggleStr + "tog.right_bumper ";
        }
        if(toggle.left_stick_button) {
            toggleStr = toggleStr + "tog.left_stick_button ";
        }
        if(toggle.right_stick_button) {
            toggleStr = toggleStr + "toggle.right_stick_button";
        }
        return toggleStr;
    }
}
//Save

