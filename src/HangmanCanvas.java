/*
 * this program are made by 
 * Linxuan Huang (ID lxh0412)
 * and
 * Yibo Yan (ID qyan) 
 */

/*
 * CS 106A Hangman
 *
 * DO NOT MODIFY THIS FILE!
 * DO NOT MODIFY THIS FILE!
 * DO NOT MODIFY THIS FILE!
 *
 * This instructor-provided file provides a simple graphical display of the
 * game state as a secondary console.
 *
 * Author : Marty Stepp
 * Version: 2015/04/19
 *
 * Your program should work properly with an UNMODIFIED version of this file.
 * If you want to modify this file for testing or for fun, that is your choice.
 * Some students implement a fancier graphical display as an extra feature.
 *
 * This file and its contents are copyright (C) Stanford University and Marty Stepp,
 * licensed under Creative Commons Attribution 2.5 License.  All rights reserved.
 */

import java.awt.*;
import acm.graphics.*;
import acm.program.*;

public class HangmanCanvas extends GCanvas {
	private static final String FONT = "Monospaced-Bold-13";
	private static final int LINE_HEIGHT = 14;
	
	private int y;   // current y-coordinate at which to draw line of text
	private HangmanProgram program;   // program to which the canvas is attached
	
	/**
	 * Sets up initial state of canvas.
	 */
	public HangmanCanvas() {
		y = LINE_HEIGHT;
	}
	
	/**
	 * Removes any drawn text from the canvas.
	 */
	public void clear() {
		removeAll();
		y = LINE_HEIGHT;
	}
	
	/**
	 * Causes all output sent to this console to instead go to the main console
	 * of the given Hangman program.
	 * @param hangmanProgram the hangman program whose console should be used
	 */
	public void merge(HangmanProgram hangmanProgram) {
		this.program = hangmanProgram;
	}
	
	/**
	 * Prints the given text onto the console.
	 * @param text the text to print
	 */
	public void print(String text) {
		println(text);
	}
	
	/**
	 * Prints the given text onto the console.
	 * @param text the text to print
	 */
	public void println(String text) {
		if (program != null) {
			program.println(text);   // just forward to main console
		} else if (text.contains("\n")) {
			// special case: print a multi-line string
			String[] lines = text.split("\r?\n");
			for (String line : lines) {
				println(line);
			}
		} else {
			GLabel label = new GLabel(text, 0, y);
			label.setFont(FONT);
			add(label);
			y += LINE_HEIGHT;
		}
	}

	/**
	 * Ends merging of the console so that this canvas will once again
	 * display output on itself.
	 */
	public void unmerge() {
		program = null;
	}
			
	public void displayHangman(int guessCount) {	
		if(guessCount==8){
			drawHangingPlatform();
			GLabel label= new GLabel("The game is on. Good luck ;)",400,200);
			label.setFont("Charter-BOLD-20");
			add(label);
		}
		if(guessCount==7){
			drawHangingPlatform();
			drawHead();
			GLabel label= new GLabel("It's just the first try.",400,200);
			label.setFont("Charter-BOLD-20");
			add(label);
		}
		if(guessCount==6){
			drawHangingPlatform();
			drawHead();
			drawBody();
			GLabel label= new GLabel("Don't worry, you still have chances!",400,200);
			label.setFont("Charter-BOLD-20");
			add(label);
		}
		if(guessCount==5){
			drawHangingPlatform();
			drawHead();
			drawBody();
			drawArms();
			GLabel label= new GLabel("It's not that bad ^_^",400,200);
			label.setFont("Charter-BOLD-20");
			add(label);
		}
		if(guessCount==4){
			drawHangingPlatform();
			drawHead();
			drawBody();
			drawArms();
			drawLegs();
			GLabel label= new GLabel("You got this!",400,200);
			label.setFont("Charter-BOLD-20");
			add(label);
		}
		if(guessCount==3){
			drawHangingPlatform();
			drawHead();
			drawBody();
			drawArms();
			drawLegs();
			drawEyebrows();
			GLabel label= new GLabel("My eyebrows are special, aren't they?",400,200);
			label.setFont("Charter-BOLD-20");
			add(label);
		}
		if(guessCount==2){
			drawHangingPlatform();
			drawHead();
			drawBody();
			drawArms();
			drawLegs();
			drawEyebrows();
			drawEyes();
			GLabel label= new GLabel("I can feel death is coming :(",400,200);
			label.setFont("Charter-BOLD-20");
			add(label);
		}
		if(guessCount==1){
			drawHangingPlatform();
			drawHead();
			drawBody();
			drawArms();
			drawLegs();
			drawEyebrows();
			drawEyes();
			drawNose();
			GLabel label= new GLabel("My life is in your hand, just be careful!!!",400,200);
			label.setFont("Charter-BOLD-20");
			add(label);
		}
		if(guessCount==0){
			drawHangingPlatform();
			drawHead();
			drawBody();
			drawArms();
			drawLegs();
			drawEyebrows();
			drawEyes();
			drawNose();
			drawMouth();
			GLabel label= new GLabel("You bloodily killed me!!!",400,200);
			label.setFont("Charter-BOLD-20");
			add(label);
		}	
	}
		
	private void drawHangingPlatform(){
		setBackground(Color.YELLOW);
		GLine horizontal= new GLine(100,50,300,50);
		horizontal.setColor(Color.BLUE);
		add(horizontal);
		
		GLine vertical= new GLine(100,50,100,400);
		vertical.setColor(Color.BLUE);
		add(vertical);
		
		GLine hanger= new GLine(300,50,300,100);
		hanger.setColor(Color.BLUE);
		add(hanger);
		
		GLine bottom= new GLine(20,400,180,400);
		bottom.setColor(Color.BLUE);
		add(bottom);
		
		for(int i=0; i<160; i++){
			add(new GLabel("^", 180+i,400));
		}
	}
		
	private void drawHead(){
		GOval head= new GOval(270,100,60,60);
		head.setFilled(true);
		head.setFillColor(Color.PINK);
		add(head);
	}
	
	private void drawBody(){
		GRect body= new GRect(260,160,80,100);
		body.setFilled(true);
		body.setFillColor(Color.GREEN);
		add(body);
	}
	
	private void drawArms(){
		GLine leftArm= new GLine(260,180,230,220);
		leftArm.setColor(Color.BLUE);
		add(leftArm);
		
		GLine rightArm= new GLine(340,180,370,220);
		rightArm.setColor(Color.BLUE);
		add(rightArm);
	}
	
	private void drawLegs(){
		GRect leftLeg= new GRect(270,260,20,60);
		leftLeg.setFilled(true);
		leftLeg.setFillColor(Color.GRAY);
		add(leftLeg);
		
		GRect rightLeg= new GRect(310,260,20,60);
		rightLeg.setFilled(true);
		rightLeg.setFillColor(Color.GRAY);
		add(rightLeg);
	}	
		
	private void drawEyebrows(){
		GOval leftEyeBrow= new GOval(280,110,10,5);
		leftEyeBrow.setFilled(true);
		leftEyeBrow.setColor(Color.BLACK);
		add(leftEyeBrow);
		
		GOval rightEyeBrow= new GOval(310,110,10,5);
		rightEyeBrow.setFilled(true);
		rightEyeBrow.setColor(Color.BLACK);
		add(rightEyeBrow);
	}
	
	private void drawEyes(){
		GOval leftEye= new GOval(281,119,10,10);
		leftEye.setFilled(true);
		leftEye.setColor(Color.WHITE);
		add(leftEye);
		
		GOval leftEyeBall= new GOval(283,120,2,2);
		leftEyeBall.setFilled(true);
		leftEyeBall.setColor(Color.BLACK);
		add(leftEyeBall);
		
		GOval rightEye= new GOval(311,119,10,10);
		rightEye.setFilled(true);
		rightEye.setColor(Color.WHITE);
		add(rightEye);
			
		GOval rightEyeBall= new GOval(313,120,2,2);
		rightEyeBall.setFilled(true);
		rightEyeBall.setColor(Color.BLACK);
		add(rightEyeBall);
	}
		
	private void drawNose(){
		GLine nose= new GLine(300,130,300,136);
		add(nose);
	}
	
	private void drawMouth(){
		GOval mouth= new GOval(285,140,30,15);
		mouth.setFilled(true);
		mouth.setColor(Color.RED);
		add(mouth);
	}
}


/*
 * DO NOT MODIFY THIS FILE!
 * DO NOT MODIFY THIS FILE!
 * DO NOT MODIFY THIS FILE!
 */

