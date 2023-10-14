/*
Copyright 2023 FIRST Tech Challenge Team FTC

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * Remove a @Disabled the on the next line or two (if present) to add this opmode to the Driver Station OpMode list,
 * or add a @Disabled annotation to prevent this OpMode from being added to the Driver Station
 */
@TeleOp(name="driver control", group="stuff")

public class DriverControl extends OpMode {
    /* Declare OpMode members. */
    DcMotor leftFrontMotor;
    DcMotor rightFrontMotor;
    DcMotor leftBackMotor;
    DcMotor rightBackMotor;
    DcMotor intake;
    //DcMotor slideMotor1;
    //DcMotor slideMotor2;
    int apress=0;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        
    
    }
    
    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        
        intake = hardwareMap.get(DcMotor.class,"intake");
        //slideMotor1 = hardwareMap.get(DcMotor.class,"slide_motor1");
        //slideMotor2 = hardwareMap.get(DcMotor.class,"slide_motor2");
        //slideMotor1.setDirection(DcMotor.Direction.REVERSE);
        double xdistance=0;
        double ydistance=0;
        double speed=0;
        double direction=0;
        
        //drivetrain
        if (gamepad1.left_stick_x<0){
            xdistance =gamepad1.left_stick_x*-1;}
        else{
            xdistance =gamepad1.left_stick_x;}
        if (gamepad1.left_stick_y<0){
             ydistance =gamepad1.left_stick_y*-1;}
        else{
        ydistance =gamepad1.left_stick_y;
        }
        speed=(xdistance+ydistance)/1;//chage the number to adjust speed
        if ((Math.atan2(gamepad1.left_stick_x, gamepad1.left_stick_y*-1)*57.29577951)<0){
          
          direction=(Math.atan2(gamepad1.left_stick_x, gamepad1.left_stick_y*-1))*57.29577951;
          direction=360+direction;
          
        }
        else{
          direction=(Math.atan2(gamepad1.left_stick_x, gamepad1.left_stick_y*-1))*57.29577951;
        }
        if(speed<-0.05||speed>0.05/*&&gamepad1.right_stick_x>0.05||gamepad1.right_stick_x<-0.05*/){  
      
          drive(direction, speed,gamepad1.right_stick_x*speed*0.75, gamepad1.right_stick_x*-0.75*speed);
    }
    else {
      turn((gamepad1.right_stick_x)/1.5);
      
    }
    telemetry.addData("Status",gamepad1.a);
    if (gamepad1.a){
    intake.setPower(1);
    } 
    if (gamepad1.x){
    intake.setPower(0);
    }
    
    /*if (gamepad1.right_bumper) {
      slideMotor1.setPower(1.0); // Adjust power level as needed
      slideMotor2.setPower(1.0); // Adjust power level as needed
    }
    // Use the right trigger (gamepad1.right_trigger) to retract the slides
    else if (gamepad1.right_trigger > 0) {
      slideMotor1.setPower(-1.0); // Adjust power level as needed
      slideMotor2.setPower(-1.0); // Adjust power level as needed
    }
    else {
      slideMotor1.setPower(0.0);
      slideMotor2.setPower(0.0);
    }*/
    
    
    
    
    telemetry.addData("direction", (Math.atan2(gamepad1.left_stick_x, gamepad1.left_stick_y*-1)*57.29577951));

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }
    public void drive (double direction, double speed, double leftOffset, double rightOffset){
    //how to use: drive(direction(0-359),time(in miliseconds),speed(from 0-1));
    //ex: drive(90,1000,0.3);
    
    leftFrontMotor  = hardwareMap.get(DcMotor.class,"motor1");
    rightFrontMotor = hardwareMap.get(DcMotor.class, "motor2");
    leftBackMotor = hardwareMap.get(DcMotor.class,"motor3");
    rightBackMotor = hardwareMap.get(DcMotor.class,"motor4");
    leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
    leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
    double difference =0;
    double lf=0;
    double rf=0;
    double lb=0;
    double rb=0;
  if(direction>=0&&direction<=45){
    difference = (10-(direction/4.5));
    rf=((difference/10)*speed);
    lb=((difference/10)*speed);
    rb=(speed);
    lf=(speed);
    }
  if(direction>=45&&direction<=90){
    difference = ((direction-45)*(1/-4.5));
    rf=((difference/10)*speed);
    lb=((difference/10)*speed);
    rb=(speed);
    lf=(speed);;
    }
  if(direction>=90&&direction<=135){
    difference = (10-((direction-90)/4.5));
    rf=(-1*speed);
    lb=(-1*speed);
    rb=((difference/10)*speed);
    lf=((difference/10)*speed); 
  }
  if(direction>=135&&direction<=180){
    difference=((direction-135)*(1/-4.5));
    rf=(-1*speed);
    lb=(-1*speed);
    rb=((difference/10)*speed);
    lf=((difference/10)*speed);
  }
  if(direction>=180&&direction<=225){
    difference = (-10-((direction-180)/-4.5));
    rf=((difference/10)*speed);
    lb=((difference/10)*speed);
    rb=(-speed);
    lf=(-speed);
  }
  if (direction>=225&&direction<=270){
  difference = ((direction-225)*(1/4.5));
    rf=((difference/10)*speed);
    lb=((difference/10)*speed);
    rb=(-speed);
    lf=(-speed);
  }
  if (direction>=270&&direction<=315){
    difference=(-10-((direction-270)/-4.5));
    rf=(speed);
    lb=(speed);
    rb=((difference/10)*speed);
    lf=((difference/10)*speed); 
  }
  if (direction>=315&&direction<360){
  difference=((direction-315)*(1/4.5));
    rf=(speed);
    lb=(speed);
    rb=((difference/10)*speed);
    lf=((difference/10)*speed);
    }
    
    rf=rf+rightOffset;
    rb=rb+rightOffset;
    lf=lf+leftOffset;
    lb=lb+leftOffset;
    leftFrontMotor.setPower(lf);
    leftBackMotor.setPower(lb);
    rightFrontMotor.setPower(rf);
    rightBackMotor.setPower(rb);
  

  }
  public void turn ( double speed){
    
    leftFrontMotor  = hardwareMap.get(DcMotor.class,"motor1");
    rightFrontMotor = hardwareMap.get(DcMotor.class, "motor2");
    leftBackMotor = hardwareMap.get(DcMotor.class,"motor3");
    rightBackMotor = hardwareMap.get(DcMotor.class,"motor4");
    leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
    leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
    leftFrontMotor.setPower(speed);
    leftBackMotor.setPower(speed);
    rightFrontMotor.setPower(speed*-1);
    rightBackMotor.setPower(speed*-1);
    
  }
}
