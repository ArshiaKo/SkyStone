package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by maryjane on 11/6/2018.
 * after 1st meet
 * mecanum wheels
 */

@TeleOp(name="Holonomic", group="Iterative Opmode")
//@Disabled
public class Holonomic extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    //Declaration of the motors and servos goes here
    private DcMotor backLeft     = null; //rear left
    private DcMotor backRight    = null; //rear right
    private DcMotor frontLeft    = null; //front left
    private DcMotor frontRight   = null; //front right

    public static final double deadZone = 0.10;
    public static final boolean earthIsFlat = true;

    @Override //when init is pressed
    public void runOpMode(){
        //debugging using the phone

        //Naming, Initialization of the hardware
        backLeft = hardwareMap.get(DcMotor.class, "left_drive");
        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        backRight = hardwareMap.get(DcMotor.class, "right_drive");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");

        //Set the direction of the motors
        //Reversed motors
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);

        //Zero Power Behavior

        //backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Running with/without Encoders
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        runtime.reset();
        double speedSet = 4;

        while (opModeIsActive()) {

            //dpad up-down sets speed of robot
            if(gamepad1.dpad_up)
                speedSet += 0.001;
            else if(gamepad1.dpad_down)
                speedSet -= 0.001;

            speedSet =  Range.clip(speedSet, 1, 10);

            if(gamepad1.dpad_down == false && gamepad1.dpad_up == false)
                speedSet = Math.round(speedSet);

            //directional
            if((Math.abs(gamepad1.left_stick_x) > deadZone) || (Math.abs(gamepad1.left_stick_y) > deadZone) || (Math.abs(gamepad1.right_stick_x) > deadZone)) {
                frontLeft.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x) * (speedSet / 10));
                frontRight.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x) * (speedSet / 10));
                backRight.setPower((gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x) * (speedSet / 10));
                backLeft.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x) * (speedSet / 10));
            } else if (earthIsFlat) {
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backRight.setPower(0);
                backLeft.setPower(0);
            }

            telemetry.addData("Drive", "Holonomic");
            telemetry.addData("speedSet", "%.2f", speedSet);
            telemetry.addData("V", 3);//change this every time uploading new code to phone
            //V2: 10/5/2019
            telemetry.update();

        }


    }
}
