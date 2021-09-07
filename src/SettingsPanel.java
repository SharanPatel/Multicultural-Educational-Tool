/*--------------------------------------------------------------------------------------*/
/* SettingsPanel.java  -  Description: This class exends JPanel and is customized to be	*/
/* used in Applicatoion.java. It will create a panel with the nesseccary buttons,  	 	*/
/* images and music to allow the user to customize their experince while using this 	*/
/* program. 																			*/
/*--------------------------------------------------------------------------------------*/
/*  Author: Sharan Patel                                                                */
/*  Date: January 20, 2020                                                              */
/*--------------------------------------------------------------------------------------*/
/*  Input: Image files (.png,.jpg) for picutres, backgrounds and buttons.  Audio files	*/
/*  (.wav) for music during the execution of the program.User will also provide button	*/
/*  press which will be taken as an input and used in the process.						*/
/* 																						*/
/*  Output: Background image, button for India, China, Russia, Sri Lanka music, and		*/ 
/* 	pause/play music. 																	*/  
/*--------------------------------------------------------------------------------------*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.Component;
import java.awt.Image;

//create class
@SuppressWarnings("serial")
public class SettingsPanel extends JPanel implements ActionListener, ComponentListener{
	
	//declare global variables
	private JButton home, settings, indiaMusic, chinaMusic, russiaMusic, srilankaMusic, pause;
	private JLabel backgroundImage;
	//for the music
	private Clip clip; 
	private String status = " "; 
	private AudioInputStream audioInputStream; 
	private String filePath = null; 
	
	//declare global constants
	final String StartFilepath = "/Users/SharanP/Desktop";
    //screen size optimal
    final int StartingW = 1024;
    final int StartingH = 768;
    //ratio for the width/height of buttons
    //based on the optimal size on the optimal screen size
    final double BasicBWR = (double)StartingW/216;
    final double BasicBHR = (double)StartingH/96;
    final double MusicBWR = (double)StartingW/225;
    final double MusicBHR = (double)StartingH/150;
    final double PauseBWR = (double)StartingW/150;
    final double PauseBHR = (double)StartingH/90;
    //x, y coordinated for buttons
    //based on the positions on the optimal screen size
    final double RussiaChinaXR = (double)StartingW/170;
    final double IndiaSrilankaXR = (double)StartingW/620;
    final double RussiaSrilankaYR = (double)StartingH/180;
    final double IndiaChinaYR = (double)StartingH/510;
    final double PauseXR = (double)StartingW/440;
    final double PauseYR = (double)StartingH/365;
    
    //constructor
	public SettingsPanel(ActionListener ac)
	{
		//construct label to hold image
		backgroundImage = new JLabel();
				
		//construct basic buttons
		//create home button with image and make it transparent
		home = new JButton("Home");
		home.setOpaque(false);
		home.setContentAreaFilled(false);
		home.setBorderPainted(false);
		//create home button with image and make it transparent
		settings = new JButton("Settings");
		settings.setOpaque(false);
		settings.setContentAreaFilled(false);
		settings.setBorderPainted(false);
				
		//create music buttons
		russiaMusic = new JButton("Russia Music");
		indiaMusic = new JButton("India Music");
		chinaMusic = new JButton("China Music");
		srilankaMusic = new JButton("SriLanka Music");
		//make buttons transparent
		russiaMusic.setBorderPainted(false);
		indiaMusic.setBorderPainted(false);
		chinaMusic.setBorderPainted(false);
		srilankaMusic.setBorderPainted(false);
		
		//construct pause button and make transparent
		pause = new JButton("Pause");
		pause.setBorderPainted(false);
		
		//add action listeners
		home.addActionListener(ac);
		settings.addActionListener(ac);
		indiaMusic.addActionListener(this);
		chinaMusic.addActionListener(this);
		russiaMusic.addActionListener(this);
		srilankaMusic.addActionListener(this);
		pause.addActionListener(this);
		
		//add backgound image to panel
		this.add(backgroundImage);
		//add buttons to backgound image
		backgroundImage.add(home);
		backgroundImage.add(settings);
		backgroundImage.add(indiaMusic);
		backgroundImage.add(chinaMusic);
		backgroundImage.add(russiaMusic);
		backgroundImage.add(srilankaMusic);
		backgroundImage.add(pause);
		
		//set layout to null
		this.setLayout(null);
		//add component listener to know when screen size changes
		this.addComponentListener(this);
	}
	
	//method that accepts filepath and plays music
	public void playMusic (String file)
	{
		//catch IOException for AudioSystem
		try
		{ 
			//stop previously playing music
			if (filePath !=null)
			{
				clip.stop(); 
				clip.close();
			}
			//assign to global variable
			filePath = file;
			
			// create AudioInputStream object 
			audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile()); 
			
			// create clip reference 
			clip = AudioSystem.getClip(); 
			
			// open audioInputStream to the clip 
			clip.open(audioInputStream); 
			
			// play music and change status
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY); 
			status = "play";
		} 
		
		//if error caught, print message to user
		catch (Exception ex) 
		{ 
			System.out.println("Error with playing sound."); 
			ex.printStackTrace(); 
		} 
	}
	
	// actionPerformed method
	public void actionPerformed(ActionEvent arg0) {
		// if russia music button is selected
		if (arg0.getSource() == russiaMusic)
		{
			//call playMusic method to play music from russia 
			playMusic (StartFilepath + "/JavaProject/music/russia.wav");
		}
		// if india music button is selected
		else if (arg0.getSource() == indiaMusic)
		{
			//call playMusic method to play music from india 
			playMusic (StartFilepath + "/JavaProject/music/india.wav");
		}
		// if china music button is selected
		else if (arg0.getSource() == chinaMusic)
		{
			//call playMusic method to play music from china 
			playMusic (StartFilepath + "/JavaProject/music/china.wav");
		}
		// if srilanka music button is selected
		else if (arg0.getSource() == srilankaMusic)
		{
			//call playMusic method to play music from srilanka 
			playMusic (StartFilepath + "/JavaProject/music/srilanka.wav");
		}
		//if pause/play button is selected
		else
		{
			//if currently playing
			if (status.equals("play"))
			{
				//pause music
				clip.stop(); 
				status = "paused"; 
			}
			//if currently paused
			else
			{
				//play music
				clip.start();
				status = "play"; 
			}
		}
		
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
	   
	    //call method that chages image sizes based on screen size
		changeImageSizes(width, height, BasicButtonW, BasicButtonH);
	    //call method that chages button placements based on screen size
		changeButtonBounds(width, height, BasicButtonW, BasicButtonH);
		
		//re-validate image
		backgroundImage.validate();
		
	}
	
	// method that chages image sizes based on screen size
	private void changeImageSizes(int width, int height, int BasicButtonW, int BasicButtonH) {
		//declare local variables
	    Image tempImage;
	    Icon tempIcon;
	    
		//change background image size
	    tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/settings.jpg").getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
		tempIcon = new ImageIcon(tempImage);
    	backgroundImage.setIcon(tempIcon);
    	
	    //change basic button image sizes
	    tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/homeB.png").getImage().getScaledInstance(BasicButtonW,BasicButtonH,Image.SCALE_SMOOTH);
	    tempIcon = new ImageIcon(tempImage);
		home.setIcon(tempIcon);
		tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/settingsB.png").getImage().getScaledInstance(BasicButtonW,BasicButtonH,Image.SCALE_SMOOTH);
		tempIcon = new ImageIcon(tempImage);
		settings.setIcon(tempIcon);
		
		//change flag button sizes
	    tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/indiaFlag.png").getImage().getScaledInstance((int) (width/MusicBWR),(int) (height/MusicBHR),Image.SCALE_SMOOTH);
	    tempIcon = new ImageIcon(tempImage);
		indiaMusic.setIcon(tempIcon);
		tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/chinaFlag.jpg").getImage().getScaledInstance((int) (width/MusicBWR),(int) (height/MusicBHR),Image.SCALE_SMOOTH);
		tempIcon = new ImageIcon(tempImage);
		chinaMusic.setIcon(tempIcon);
	    tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/russiaFlag.png").getImage().getScaledInstance((int) (width/MusicBWR),(int) (height/MusicBHR),Image.SCALE_SMOOTH);
	    tempIcon = new ImageIcon(tempImage);
		russiaMusic.setIcon(tempIcon);
		tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/srilankaFlag.jpg").getImage().getScaledInstance((int) (width/MusicBWR),(int) (height/MusicBHR),Image.SCALE_SMOOTH);
		tempIcon = new ImageIcon(tempImage);
		srilankaMusic.setIcon(tempIcon);
		
		//change pause/play button size
	    tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/playpause.png").getImage().getScaledInstance((int) (width/PauseBWR),(int) (height/PauseBHR),Image.SCALE_SMOOTH);
	    tempIcon = new ImageIcon(tempImage);
		pause.setIcon(tempIcon);
		
	}
	
	//method that chages button placements based on current screen size
	private void changeButtonBounds(int width, int height, int BasicButtonW, int BasicButtonH) {
		//change background label bounds
		backgroundImage.setBounds(0, 0, width, height);
		
		//change basic button bounds
		//top right
		home.setBounds(width-BasicButtonW, 0, BasicButtonW, BasicButtonH);
		//top left
		settings.setBounds(0, 0, BasicButtonW, BasicButtonH);
		
		//change music button bounds
		russiaMusic.setBounds((int) (width/RussiaChinaXR), (int) (height/RussiaSrilankaYR), (int) (width/MusicBWR), (int) (height/MusicBHR));
		srilankaMusic.setBounds((int) (width/IndiaSrilankaXR), (int) (height/RussiaSrilankaYR), (int) (width/MusicBWR), (int) (height/MusicBHR));
		chinaMusic.setBounds((int) (width/RussiaChinaXR), (int) (height/IndiaChinaYR), (int) (width/MusicBWR), (int) (height/MusicBHR));
		indiaMusic.setBounds((int) (width/IndiaSrilankaXR), (int) (height/IndiaChinaYR), (int) (width/MusicBWR), (int) (height/MusicBHR));
		
		//change pause button bounds
		pause.setBounds((int) (width/PauseXR), (int) (height/PauseYR), (int) (width/PauseBWR), (int) (height/PauseBHR));
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
