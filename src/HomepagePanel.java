/*--------------------------------------------------------------------------------------*/
/* HomepagePanel.java  -  Description: This class exends JPanel and is customized to be	*/
/* used in Applicatoion.java. It will create a panel with the nesseccary buttons and 	*/
/* images to provide a simple and elegant GUI for the user to navigate through the 		*/
/* progam. 																				*/
/*--------------------------------------------------------------------------------------*/
/*  Author: Sharan Patel                                                                */
/*  Date: January 20, 2020                                                              */
/*--------------------------------------------------------------------------------------*/
/*  Input: Image files (.png,.jpg) for picutres, backgrounds and buttons. User will 	*/
/*  also provide button press which will be taken as an input and used in the process.	*/
/* 																						*/
/*  Output: Background image (map), home button, settings button, quiz button and exit	*/
/*  button all with images. India, China, Russia transparent buttons 	         		*/  
/*--------------------------------------------------------------------------------------*/

import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.*;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

//create class
@SuppressWarnings("serial")
public class HomepagePanel extends JPanel implements ComponentListener{
	
	//declare global private variables
	private JButton home, settings, india, china, russia, quiz,exit;
	private Font bigFont;
	private JLabel backgroundImage;
    
    //declare global constants
	final String StartFilepath = "/Users/SharanP/Desktop";
    //screen size optimal
    final int StartingW = 1024;
    final int StartingH = 768;
    //ratio for the width/height of button
    //based on the optimal size on the optimal screen size
    final double BasicBWR = (double)StartingW/216;
    final double BasicBHR = (double)StartingH/96;
    final double CountryBWR = (double)StartingW/150;
    final double CountryBHR = (double)StartingH/100;
    //x, y coordinated for buttons
    //based on the positions on the optimal screen size
    final double RussiaXR = (double)StartingW/450;
    final double RussiaYR = (double)StartingH/125;
    final double ChinaXR = (double)StartingW/525;
    final double ChinaYR = (double)StartingH/360;
    final double IndiaXR = (double)StartingW/335;
    final double IndiaYR = (double)StartingH/475;
    //font size based on the optimal font size on the optimal screen size
    final double FontR = (double)StartingH/30;
    
    //constructor
	public HomepagePanel(ActionListener ac)
	{
		//construct label to hold image
		backgroundImage = new JLabel();
		
		//construct basic buttons
		//create home button and make it transparent
		home = new JButton("Home");
		home.setBorderPainted(false);
		//create settings button and make it transparent
		settings = new JButton("Settings");
		settings.setBorderPainted(false);
		//create quiz button and make it transparent
		quiz = new JButton("Quiz");
		quiz.setBorderPainted(false);
		//create exit button and make it transparent
		exit = new JButton("Exit");
		exit.setBorderPainted(false);
		
		//construct country buttons
		india = new JButton("India");
		china = new JButton("China");
		russia = new JButton("Russia");
		//make country buttons transpartent
		india.setBorderPainted(false);
		china.setBorderPainted(false);
		russia.setBorderPainted(false);
		
		//add action listeners
		home.addActionListener(ac);
		settings.addActionListener(ac);
		india.addActionListener(ac);
		china.addActionListener(ac);
		russia.addActionListener(ac);
		quiz.addActionListener(ac);
		exit.addActionListener(ac);
		
		//add backgound image to panel
		this.add(backgroundImage);
		//add buttons to backgound image
		backgroundImage.add(home);
		backgroundImage.add(settings);
		backgroundImage.add(india);
		backgroundImage.add(china);
		backgroundImage.add(russia);
		backgroundImage.add(quiz);
		backgroundImage.add(exit);
		
		//set layout to null
		this.setLayout(null);
		//add component listener to know when screen size changes
		this.addComponentListener(this);
	}

	//method that resizes all components based on size of screen
	public void componentResized(ComponentEvent e) {
		// get the current width and height of screen
		Component c =  (Component)e.getSource();
	    int width = c.getWidth();
	    int height = c.getHeight();
	    
	    //contant for basic button size
	    final int BasicButtonW = (int) (width/BasicBWR);
	    final int BasicButtonH = (int) (height/BasicBHR);
	   
	    //call method that chages font size based on screen size
	    changeFontSize(height);
	    //call method that chages image sizes based on screen size
		changeImageSizes(width, height, BasicButtonW, BasicButtonH);
	    //call method that chages button placements based on screen size
		changeButtonBounds(width, height, BasicButtonW, BasicButtonH);

		//re-validate image
		backgroundImage.validate();
	}

    // method that chages font size based on screen size
	private void changeFontSize(int height) {
	    //change font size
	    bigFont = new Font("TimesRoman", Font.BOLD, (int)((height)/FontR));
	    //add to country buttons
	    india.setFont(bigFont);
	    china.setFont(bigFont);
	    russia.setFont(bigFont);
	}

	// method that chages image sizes based on screen size
	private void changeImageSizes(int width, int height, int BasicButtonW, int BasicButtonH) {
		//declare local vars
	    Image tempImage;
	    Icon tempIcon;
	    
	    //change background image size
		tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/home.jpg").getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
		tempIcon = new ImageIcon(tempImage);
    	backgroundImage.setIcon(tempIcon);
    	
	    //change basic button image sizes
	    tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/homeB.png").getImage().getScaledInstance(BasicButtonW,BasicButtonH,Image.SCALE_SMOOTH);
	    tempIcon = new ImageIcon(tempImage);
		home.setIcon(tempIcon);
		tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/settingsB.png").getImage().getScaledInstance(BasicButtonW,BasicButtonH,Image.SCALE_SMOOTH);
		tempIcon = new ImageIcon(tempImage);
		settings.setIcon(tempIcon);
		tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/quizB.png").getImage().getScaledInstance(BasicButtonW,BasicButtonH,Image.SCALE_SMOOTH);
		tempIcon = new ImageIcon(tempImage);
		quiz.setIcon(tempIcon);
		tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/exitB.png").getImage().getScaledInstance(BasicButtonW,BasicButtonH,Image.SCALE_SMOOTH);
		tempIcon = new ImageIcon(tempImage);
		exit.setIcon(tempIcon);
	}

	//method that chages button placements based on screen size
	private void changeButtonBounds(int width, int height, int BasicButtonW, int BasicButtonH) {
		//change background label bounds
		backgroundImage.setBounds(0, 0, width, height);
		
		//change basic button bounds
		//top right
		home.setBounds(width-BasicButtonW, 0, BasicButtonW, BasicButtonH);
		//top left
		settings.setBounds(0, 0, BasicButtonW, BasicButtonH);
		//bottom left
		quiz.setBounds(0, height-BasicButtonH, BasicButtonW, BasicButtonH);
		//bottom right
		exit.setBounds(width-BasicButtonW, height-BasicButtonH, BasicButtonW, BasicButtonH);
		//change country button bounds
		india.setBounds((int)(width/IndiaXR),(int)(height/IndiaYR), (int)(width/CountryBWR), (int)(height/CountryBHR));
		china.setBounds((int)(width/ChinaXR), (int)(height/ChinaYR), (int)(width/CountryBWR), (int)(height/CountryBHR));
		russia.setBounds((int)(width/RussiaXR), (int)(height/RussiaYR), (int)(width/CountryBWR), (int)(height/CountryBHR));
	}

	public void componentMoved(ComponentEvent e) {
		//do nothing
		//method is needed to run program
	}
	public void componentShown(ComponentEvent e) {
		//do nothing
		//method is needed to run program
	}
	public void componentHidden(ComponentEvent e) {
		//do nothing
		//method is needed to run program
	}

}
