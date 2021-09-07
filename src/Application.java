/*--------------------------------------------------------------------------------------*/
/* Applcation.java  -  Description: This program will educate users about the cultures	*/
/* of different countries in Asia by providing them with a graphical user interface they*/
/* can use to self-learn about some Asain countries, including India, China, and Russia.*/
/* Information regarding each country's food, customs and holidays, will be provided for*/
/* the user to learn more about each country's culture. Additionally, the user will be	*/
/* able to demonstrate their learning by completing a quiz of a difficulty level of		*/
/* their choosing.																		*/
/*--------------------------------------------------------------------------------------*/
/*  Author: Sharan Patel                                                                */
/*  Date: January 20, 2020                                                              */
/*--------------------------------------------------------------------------------------*/
/*  Input: Image files (.png,.jpg) for picutres, backgrounds and buttons. Text files 	*/
/*  (.txt) for information about each country such as food, holidays and customs. Audio	*/
/*  files (.wav) for music during the execution of the program. User will also provide	*/
/*  button press which will be taken as an input and used in the processes.				*/
/* 																						*/
/*  Output: Initial landing screen, home screen, settings screen, India screen, Russia 	*/
/*  screen, China screen and quiz screen. Each screen will output buttons, labels,  	*/
/*  pictures, text boxs, etc. based on the purpose of that specific page.          		*/  
/*--------------------------------------------------------------------------------------*/

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.*;
import java.io.IOException;

//create class
@SuppressWarnings("serial")
public class Application extends JPanel implements ComponentListener,  ActionListener {

	//declare private global variables
	//to set initial width and height of frame
	private static int w = 1024;
	private static int h = 768;
	//declare frame and start buttons
	private JFrame f;
	private JButton start;
	//declare panel from different panel classes
	private HomepagePanel homepagePanel;
	private SettingsPanel settingsPagePanel;
	private CountryPanel indiaPagePanel, chinaPagePanel, russiaPagePanel;
	private QuizPanel quizPagePanel;
	
	//delcare global constants
	final String StartFilepath = "/Users/SharanP/Desktop";
	
	// initialization, called from main method
	Application() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

		// create JFrame object and set background colour
		f = new JFrame();
		f.setBackground(Color.lightGray);
		
		//construct panels
		settingsPagePanel = new SettingsPanel(this); 
		homepagePanel = new HomepagePanel(this);
		//construct panels by sending filepath to background image
		indiaPagePanel = new CountryPanel(this,StartFilepath + "/JavaProject/pictures/india.png", "India"); 
		chinaPagePanel = new CountryPanel(this,StartFilepath + "/JavaProject/pictures/china.jpg","China"); 
		russiaPagePanel = new CountryPanel(this,StartFilepath + "/JavaProject/pictures/russia.png","Russia"); 

		// construct button
		start = new JButton();
		// add actionListener
		start.addActionListener(this);
		// set button bounds to begin program
		start.setBounds(0, 0, w, h);
		
		//set appropriate settings to Jframe
		//add component listener, add button, setsize, set to no layout and make it visible
		f.addComponentListener(this);
		f.add(start);
		f.pack();
		f.setSize(w, h);
		f.setLayout(null);
		f.setVisible(true);
	}

	//main method calls constructor
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		new Application();
	}
	
	//action performed method
	public void actionPerformed(ActionEvent e) {

		//set homepagePanel to frame if home or start button is clicked
		if (e.getActionCommand() == "Home" || e.getSource() == start) {
			//set to frame
			f.setContentPane (homepagePanel);
			
			//refresh panel and frame
			homepagePanel.validate();
			f.validate();
		}
		//set settingsPagePanel to frame if settings button is clicked
		else if (e.getActionCommand() == "Settings") {
			//set to frame
			f.setContentPane (settingsPagePanel);
			
			//refresh panel and frame
			settingsPagePanel.validate();
			f.validate();
		}
		//construct new quiz and set to frame if quiz button is clicked
		else if (e.getActionCommand() == "Quiz") {
			//contruct new quiz
			quizPagePanel = new QuizPanel(this);
			//set to frame
			f.setContentPane (quizPagePanel);
			
			//refresh panel and frame
			quizPagePanel.validate();
			f.validate();
		}
		//set indiaPagePanel to frame if India button is clicked
		else if (e.getActionCommand() == "India") {
			//set to frame
			f.setContentPane (indiaPagePanel);
			
			//refresh panel and frame
			indiaPagePanel.validate();
			f.validate();
		}
		//set chinaPagePanel to frame if China button is clicked
		else if (e.getActionCommand() == "China") {
			//set to frame
			f.setContentPane (chinaPagePanel);
			
			//refresh panel and frame
			chinaPagePanel.validate();
			f.validate();
		}
		//set russiaPagePanel to frame if Russia button is clicked
		else if (e.getActionCommand() == "Russia") {
			//set to frame
			f.setContentPane (russiaPagePanel);
			
			//refresh panel and frame
			russiaPagePanel.validate();
			f.validate();
		}
		//exit program if exit button is clicked
		else{
			//dispose window
			f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
			f.dispose();
		}
	}

	//method that resizes all components based on size of screen
	public void componentResized(ComponentEvent e) {	
		// get the current width and height of screen
       Component c =  (Component)e.getSource();
       w = c.getWidth();
       h = c.getHeight();
       
       //set a title to frame
       f.setTitle("W: " + w + " H: " + h);
       
       //call method that changes image sizes based on the size of the screen
       changeImageSizes();

	}

	//method that changes image sizes based on the size of the screen
	private void changeImageSizes() {
		//declare local vars
	    Image tempImage;
	    Icon tempIcon;
	    
	    //change to current screen size
	    tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/initialPage.png").getImage().getScaledInstance(w,h,Image.SCALE_SMOOTH);
	    tempIcon = new ImageIcon(tempImage);
	    //add to button
	    start.setIcon(tempIcon);
	    start.setBounds(0, 0, w, h);
	}
	
	//allows other files to access the variable w 
	public static int getW()
	{
		return w;
	}
	
	//allows other files to access the variable h
	public static int getH()
	{
		return h;
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

