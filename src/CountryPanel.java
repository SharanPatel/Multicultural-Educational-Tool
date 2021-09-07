/*--------------------------------------------------------------------------------------*/
/* CountryPanel.java  -  Description: This class exends JPanel and is customized to be	*/
/* used in Applicatoion.java. It will create a panel with the nesseccary buttons,		*/
/* images and text boxes to educate the user about the cutlute of a select few Asian	*/
/* countries.																			*/
/*--------------------------------------------------------------------------------------*/
/*  Author: Sharan Patel                                                                */
/*  Date: January 20, 2020                                                              */
/*--------------------------------------------------------------------------------------*/
/*  Input: Image files (.png,.jpg) for picutres, backgrounds and buttons. Text files 	*/
/*  (.txt) for information about each country such as food, holidays and customs. User 	*/
/*  will also provide button press which will be taken as an input and used in the 		*/
/*  process.																			*/
/* 																						*/
/*  Output: Background image, home and settings button, food, holidays, and				*/
/*  customs buttons, text boxes containing information about the country and images 	*/
/*  representing that information 	         											*/  
/*--------------------------------------------------------------------------------------*/


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

//create class
@SuppressWarnings("serial")
public class CountryPanel extends JPanel implements ActionListener,ComponentListener{
	
	//declare global variables
	private JButton home, settings, food, holidays, customs;
	private TextBox basicText, additionalText;
	private Font font, smallFont;
	private JLabel basicImage, additionalImage;
	private String country;
	private JLabel backgroundImage;
	private int w = Application.getW();
	private int h = Application.getH();
	private String backgroundFile;
	private String basicIconPath, additionalIconPath;
	
	//declare global contants
	final String StartFilepath = "/Users/SharanP/Desktop";
    final int BorderWidth = 3;
	//screen size optimal
    final int StartingW = 1024;
    final int StartingH = 768;
    //ratio for the width/height of button
    //based on the optimal size on the optimal screen size
    final double BasicBWR = (double)StartingW/216;
    final double BasicBHR = (double)StartingH/96;
    final double TextImageWR = (double)StartingW/425;
    final double TextImageHR = (double)StartingH/250;
    //x, y coordinated for buttons
    //based on the positions on the optimal screen size
    final double InformationYR = (double)StartingH/665;
    final double FoodXR = (double)StartingW/95;
    final double HolidaysXR = (double)StartingW/405;
    final double CustomsXR = (double)StartingW/715;
    final double TextXR = (double)StartingW/50;
    final double ImageXR = (double)StartingW/550;
    final double BasicYR = (double)StartingH/100;
    final double AdditionalYR = (double)StartingH/400;
    //font size based on the optimal font size on the optimal screen size
    final double FontR = (double)StartingH/15;
    final double SmallFontR = (double)StartingH/13;
    
    
	//constructor that accepts filepath
	public CountryPanel(ActionListener ac, String filepath, String country)
	{
		//set filepath received from parameter to global variable
		backgroundFile = filepath;
		//set country name received from parameter to global variable
		this.country = country;	
		
		//construct label to hold image
		backgroundImage = new JLabel();
		
		// construct basic and additional info image
		additionalImage = new JLabel();
		basicImage = new JLabel();
		
		// construct basic info text area
		basicText = new TextBox();
		basicText.setLineWrap(true);
		basicText.setWrapStyleWord(true);
		basicText.setEditable(false);
		basicText.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,BorderWidth), "Country's Basic Information"));
		// construct additional info text area 
		additionalText = new TextBox();
		additionalText.setLineWrap(true);
		additionalText.setWrapStyleWord(true);
		additionalText.setEditable(false);
		additionalText.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,BorderWidth), "Additional Information"));
		
		//construct basic buttons
		//create home button and make it transparent
		home = new JButton("Home");
		home.setBorderPainted(false);
		//create home button and make it transparent
		settings = new JButton("Settings");
		settings.setBorderPainted(false);
		
		//construct other buttons
		//create food button and make it transparent
		food = new JButton("Food");
		food.setBorderPainted(false);
		//create holidays button and make it transparent
		holidays = new JButton("Holidays");
		holidays.setBorderPainted(false);
		//create customs button and make it transparent
		customs = new JButton("Customs");
		customs.setBorderPainted(false);
		
		//add action listeners
		home.addActionListener(ac);
		settings.addActionListener(ac);
		food.addActionListener(this);
		holidays.addActionListener(this);
		customs.addActionListener(this);
		
		//add backgound image to panel
		this.add(backgroundImage);
		//add to backgound image
		backgroundImage.add(home);
		backgroundImage.add(settings);
		backgroundImage.add(food);
		backgroundImage.add(holidays);
		backgroundImage.add(customs);
		backgroundImage.add(basicText);
		backgroundImage.add(additionalText);
		backgroundImage.add(basicImage);
		backgroundImage.add(additionalImage);

		//set additional text not visible at first
		additionalText.setVisible(false);
		
		//set layout to null
		this.setLayout(null);
		//add component listener to know when screen size changes
		this.addComponentListener(this);

		//display basic information image and text
		try {
			displayBasicItems();
		} catch (IOException arg0) { 
			arg0.printStackTrace();
		}
	}

	//method that displays basic information and basic image
	private void displayBasicItems() throws IOException {
		//if india is selected
		if (country.equals("India"))
		{
			//call method to display image for india
			displayImage(StartFilepath + "/JavaProject/pictures/indiaStandard.jpg","basic");
			//display basic text for India
			basicText.readFile(StartFilepath + "/JavaProject/text/indiaBasic.txt");
		}
		//if china is selected
		else if (country.equals("China"))
		{
			//call method to display image for china
			displayImage(StartFilepath + "/JavaProject/pictures/chinaStandard.gif", "basic");
			//display basic text for china
			basicText.readFile(StartFilepath + "/JavaProject/text/chinaBasic.txt");
		}
		//if russia is selected
		else
		{
			//call method to display image for russia
			displayImage(StartFilepath + "/JavaProject/pictures/russiaStandard.png", "basic");
			//display basic text for russia
			basicText.readFile(StartFilepath + "/JavaProject/text/russiaBasic.txt");
		}
	}

	// method that displays image
	public void displayImage(String path, String type) {
		//declare local variables and rescale image to the current screen sice
		Image tempImage = new ImageIcon(path).getImage().getScaledInstance((int)(w/TextImageWR),(int)(h/TextImageHR),Image.SCALE_SMOOTH);
		Icon tempIcon = new ImageIcon(tempImage);
		
		//display the image
		if (type.equals("basic"))
		{
			//set to the basicImage label
			basicImage.setIcon(tempIcon);
			basicIconPath = path;
		}
		else
		{
			//set to the additionalImage label
			additionalImage.setIcon(tempIcon);
			additionalIconPath = path;
		}
	}

	// actionPerformed method
	public void actionPerformed(ActionEvent arg0) {
		//set visible once a button has been clicked
		additionalText.setVisible(true);
		
		//surround in try catch since actionPerformed method can not have throws
		try {
			//determine which country and one of the three buttons has been clicked
			//india selected
			if (country.equals("India"))
			{
				//determine which button has been clicked
				if (arg0.getSource() == food)
				{
					//display additional image and text for india food
					additionalText.readFile(StartFilepath + "/JavaProject/text/indiaFood.txt");
					displayImage(StartFilepath + "/JavaProject/pictures/indiaFood.png", "additional");
				}
				else if (arg0.getSource() == holidays)
				{
					//display additional image and text for india holidays
					additionalText.readFile(StartFilepath + "/JavaProject/text/indiaHolidays.txt");
					displayImage(StartFilepath + "/JavaProject/pictures/indiaHolidays.png", "additional");
				}
				else
				{
					//display additional image and text for india customs
					additionalText.readFile(StartFilepath + "/JavaProject/text/indiaCustoms.txt");
					displayImage(StartFilepath + "/JavaProject/pictures/indiaCustoms.png", "additional");
				}
			}
			//china selected
			else if (country.equals("China"))
			{
				//determine which button has been clicked
				if (arg0.getSource() == food)
				{
					//display additional image and text for china food
					additionalText.readFile(StartFilepath + "/JavaProject/text/chinaFood.txt");
					displayImage(StartFilepath + "/JavaProject/pictures/chinaFood.jpg", "additional");
				}
				else if (arg0.getSource() == holidays)
				{
					//display additional image and text for china holidays
					additionalText.readFile(StartFilepath + "/JavaProject/text/chinaHolidays.txt");
					displayImage(StartFilepath + "/JavaProject/pictures/chinaHolidays.png", "additional");
				}
				else
				{
					//display additional image and text for china customs
					additionalText.readFile(StartFilepath + "/JavaProject/text/chinaCustoms.txt");
					displayImage(StartFilepath + "/JavaProject/pictures/chinaCustoms.png", "additional");
				}
			}
			//russia selected
			else
			{
				//determine which button has been clicked
				if (arg0.getSource() == food)
				{
					//display additional image and text for russia food
					additionalText.readFile(StartFilepath + "/JavaProject/text/russiaFood.txt");
					displayImage(StartFilepath + "/JavaProject/pictures/russiaFood.jpg", "additional");
				}
				else if (arg0.getSource() == holidays)
				{
					//display additional image and text for russia holidays
					additionalText.readFile(StartFilepath + "/JavaProject/text/russiaHolidays.txt");
					displayImage(StartFilepath + "/JavaProject/pictures/russiaHolidays.png", "additional");
				}
				else
				{
					//display additional image and text for russia customs
					additionalText.readFile(StartFilepath + "/JavaProject/text/russiaCustoms.txt");
					displayImage(StartFilepath + "/JavaProject/pictures/russiaCustoms.png", "additional");
				}
			}
		}
		//catch any potential errors
		catch (IOException e) {
			e.printStackTrace();
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
	    
	    //call method that chages font size based on screen size
	    changeFontSize(height);
	    //call method that chages image sizes based on screen size
		changeImageSizes(width, height, BasicButtonW, BasicButtonH);
	    //call method that chages button placements based on screen size
		changeButtonBounds(width, height, BasicButtonW, BasicButtonH);
		
		//set  w and h to current width and height
	  	w = width;
		h = height;
		
		//re-validate image
		backgroundImage.validate();
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
		
		//change additional button bounds
		food.setBounds((int)(width/FoodXR), (int)(height/InformationYR), BasicButtonW, BasicButtonH);
		holidays.setBounds((int)(width/HolidaysXR), (int)(height/InformationYR), BasicButtonW, BasicButtonH);
		customs.setBounds((int)(width/CustomsXR), (int)(height/InformationYR), BasicButtonW, BasicButtonH);

		//change basic and additional image label bounds
		basicImage.setBounds((int)(width/ImageXR), (int)(height/BasicYR), (int)(width/TextImageWR),(int)(height/TextImageHR));
		additionalImage.setBounds((int)(width/ImageXR),(int)(height/AdditionalYR), (int)(width/TextImageWR),(int)(height/TextImageHR));
		
		//change text box bounds
		basicText.setBounds((int)(width/TextXR), (int)(height/BasicYR), (int)(width/TextImageWR),(int)(height/TextImageHR));
		additionalText.setBounds((int)(width/TextXR), (int)(height/AdditionalYR), (int)(width/TextImageWR),(int)(height/TextImageHR));
	}

	// method that chages image sizes based on screen size
	private void changeImageSizes(int width, int height, int BasicButtonW, int BasicButtonH) {
		//declare local vars
	    Image tempImage;
	    Icon tempIcon;
	    
		//change background image size
	    tempImage = new ImageIcon(backgroundFile).getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
		tempIcon = new ImageIcon(tempImage);
    	backgroundImage.setIcon(tempIcon);
    	
	    //change basic button image sizes
	    tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/homeB.png").getImage().getScaledInstance(BasicButtonW,BasicButtonH,Image.SCALE_SMOOTH);
	    tempIcon = new ImageIcon(tempImage);
		home.setIcon(tempIcon);
		tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/settingsB.png").getImage().getScaledInstance(BasicButtonW,BasicButtonH,Image.SCALE_SMOOTH);
		tempIcon = new ImageIcon(tempImage);
		settings.setIcon(tempIcon);
		
		//change other button sizes
	    tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/foodB.png").getImage().getScaledInstance(BasicButtonW,BasicButtonH,Image.SCALE_SMOOTH);
	    tempIcon = new ImageIcon(tempImage);
		food.setIcon(tempIcon);
		tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/holidaysB.png").getImage().getScaledInstance(BasicButtonW,BasicButtonH,Image.SCALE_SMOOTH);
		tempIcon = new ImageIcon(tempImage);
		holidays.setIcon(tempIcon);
	    tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/customsB.png").getImage().getScaledInstance(BasicButtonW,BasicButtonH,Image.SCALE_SMOOTH);
	    tempIcon = new ImageIcon(tempImage);
		customs.setIcon(tempIcon);
		
		//change basic and additional picture sizes
		tempImage = new ImageIcon(basicIconPath).getImage().getScaledInstance((int)(width/TextImageWR),(int)(height/TextImageHR),Image.SCALE_SMOOTH);
		tempIcon = new ImageIcon(tempImage);
		basicImage.setIcon(tempIcon);
	    tempImage = new ImageIcon(additionalIconPath).getImage().getScaledInstance((int)(width/TextImageWR),(int)(height/TextImageHR),Image.SCALE_SMOOTH);
	    tempIcon = new ImageIcon(tempImage);
	    additionalImage.setIcon(tempIcon);
		
	}

	// method that chages font size based on screen size
	private void changeFontSize(int height) {
	    //change font sizes
	    font = new Font("Symbol", Font.PLAIN, (int)(height/FontR));
	    smallFont = new Font("Symbol", Font.PLAIN, (int)(height/SmallFontR));
	    //add to text boxes
		basicText.setFont(font);
		additionalText.setFont(smallFont);
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
