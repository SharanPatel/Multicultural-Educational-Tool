/*--------------------------------------------------------------------------------------*/
/* QuizPanel.java  -  Description: This class exends JPanel and is customized to be		*/
/* used in Applicatoion.java. It will create a panel with the nesseccary buttons and 	*/
/* images to allow the user to demonstrate their knowledge by writing a quiz. The quiz 	*/
/* will be of 2 difficulty levels and will provide the user with their final score.		*/
/*--------------------------------------------------------------------------------------*/
/*  Author: Sharan Patel                                                                */
/*  Date: January 20, 2020                                                              */
/*--------------------------------------------------------------------------------------*/
/*  Input: Image files (.png,.jpg) for picutres, backgrounds and buttons.  Text files 	*/
/*  (.txt) for question/answer file. User will also various provide button press 		*/
/* 	multiple times which will be taken as an input and used in the process.				*/
/* 																						*/
/*  Output: Background image, home, settings, easy and hard buttons. Question, timer, 	*/
/*  and question number label. Radio buttons for answer options. Next and result buttons*/
/*  as well as progress bar for the quiz. Finally, messages to user informing them the 	*/
/*  correct answer and final score.														*/
/*--------------------------------------------------------------------------------------*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;

//create class
@SuppressWarnings("serial")
public class QuizPanel extends JPanel implements ActionListener, ComponentListener{
	
	//declare private global variables
	private JButton home, settings, easy, hard, nextB,resultB;
	private JLabel backgroundImage, questionL, qNumberL, timerL;
	private ButtonGroup bg;  
	private JRadioButton optionRB[]=new JRadioButton[5];  
    private Font font, bigFont;
    private BufferedReader quizReader;
    private Timer timer;
    private JProgressBar bar;
    private String [] fileQuestions;
    private int[] mylist;
    private int count = 0,current = 0, key;
    private int timeCount;
    
	//declare global constants
	final String StartFilepath = "/Users/SharanP/Desktop";
	final int NumberOfQuestions = 10;
    final int LinesPerQuestion = 5;
    //screen size optimal
    final int StartingW = 1024;
    final int StartingH = 768;
    //ratio for the width/height of button
    //based on the optimal pixel size on the optimal screen size
    final double BasicBWR = (double)StartingW/216;
    final double BasicBHR = (double)StartingH/96;
    final double DifficultyAndLabelBWR = (double)StartingW/250;
    final double DifficultyBHR = (double)StartingH/225;
    final double QuesionLWR = (double)StartingW/450;
    final double LabelsBarAndNextBHR = (double)StartingH/50;
    final double BarWR = (double)StartingW/480;
    final double RadioBWR = (double)StartingW/400;
    final double RadioBHR = (double)StartingH/20;
    final double NextAndResultBWR = (double)StartingW/100;
    //x, y coordinated for buttons
    //based on the positions on the optimal screen size
    final double EasyXR = (double)StartingW/250;
    final double HardXR = (double)StartingW/500;
    final double DifficultyYR = (double)StartingH/345;
    final double QuestionAndOptionsXR = (double)StartingW/265;
    final double QuestionsYR = (double)StartingH/350;
    final double QuestionLXR = (double)StartingW/285;
    final double TimerLXR = (double)StartingW/600;
    final double LabelsYR = (double)StartingH/285;
    final double BarXR = (double)StartingW/260;
    final double BarYR = (double)StartingH/530;
    final double RadioYR1 = (double)StartingH/400;
    final double RadioAndNextYR2 = (double)StartingH/440;
    final double RadioYR3 = (double)StartingH/480;
    final double RadioYR4 = (double)StartingH/520;
    final double NextAndResultXR = (double)StartingW/625;
    final double ResultYR = (double)StartingH/500;
    //font size based on the optimal font size on the optimal screen size
    final double FontR = (double)StartingH/15;
    final double BigFontR = (double)StartingH/30;
    
    //constructor
	public QuizPanel(ActionListener ac)
	{
		//construct label to hold image
		backgroundImage = new JLabel();
		
		//construct basic buttons
		//create home button and make it transparent
		home = new JButton("Home");
		home.setBorderPainted(false);
		//create home button and make it transparent
		settings = new JButton("Settings");
		settings.setBorderPainted(false);
		
		//create other buttons
		//make buttons visible and blue
		easy = new JButton("Beginner Quiz");
		easy.setVisible(true);
		easy.setForeground(Color.blue);
		hard = new JButton("Expert Quiz");
		hard.setVisible(true);
		hard.setForeground(Color.blue);
		
		//constructors for quiz
		//labels
		questionL = new JLabel();  
		qNumberL = new JLabel();  
		timerL = new JLabel();  
		//buttons
        nextB = new JButton("Next");
        resultB = new JButton ("Result");
        //radiobuttons
        bg = new ButtonGroup();  
        for(int i=0;i<5;i++)  
        {  
            optionRB[i]=new JRadioButton();  
        }  
        //progress bar
        bar = new JProgressBar(0,10); 
        
		//add action listeners
		home.addActionListener(ac);
		settings.addActionListener(ac);
		easy.addActionListener(this);
		hard.addActionListener(this);
        nextB.addActionListener(this);  
        resultB.addActionListener(this);
		
		//add backgound image to panel
		this.add(backgroundImage);
		//add buttons to backgound image
		backgroundImage.add(home);
		backgroundImage.add(settings);
		backgroundImage.add(easy);
		backgroundImage.add(hard);
		
		//set layout to null
		this.setLayout(null);
		//add component listener to know when screen size changes
		this.addComponentListener(this);
	}
	
	//action performed method
	public void actionPerformed(ActionEvent arg0) {
		//do after user selects question difficulty
		if (arg0.getSource() == easy || arg0.getSource() == hard)
		{
			//call method that starts the quiz
			startQuiz(arg0);
		}
		else if(arg0.getSource()==nextB)  
        {  
			//call method that keeps count of score
			nextQuestion();
        }  
		//perform when the user answers the last question
		//when the user clicks result
		else
        {  
			//call method that ends the quiz
			endQuiz();
        }  
	}

	//method that starts the quiz
	private void startQuiz(ActionEvent arg0) {
		//declare local variables
	    int start;
	    int end;
	    //declare constants
	    final int NumberOfQuestions = 25;
	    final int EasyQStart = 0;
	    final int EasyQEnd = 14;
	    final int HardQStart = 11;
	    final int HardQEnd = 24;
	    final int EasyTime = 150;
	    final int HardTime = 60;

	    //construct array to hold the lines from file
	    fileQuestions = new String [NumberOfQuestions*LinesPerQuestion];
	    
	    //set all buttons to notVisible
		easy.setVisible(false);
		hard.setVisible(false);
		home.setVisible(false);
		settings.setVisible(false);
		 
		//change progress bar value to the question number
		bar.setValue(current);
		bar.setStringPainted(true);  
		
		//set next button visible
		nextB.setEnabled(true);
        resultB.setVisible(false);
        
		//add question buttons and labels to panel
		backgroundImage.add(questionL); 
		backgroundImage.add(qNumberL);
		backgroundImage.add(timerL);
		backgroundImage.add(nextB);
		backgroundImage.add(resultB); 
		backgroundImage.add(bar);   
        //add option buttons to panel and to button group
        for(int i=0;i<5;i++)  
        {  
        	
        	backgroundImage.add(optionRB[i]);   
            bg.add(optionRB[i]);  
        }  
        
        //determine users choice set start and end based on user's choice
    	if (arg0.getSource() == easy)
    	{
    		//set start and end for easy questions
    		start = EasyQStart;
    		end = EasyQEnd;
    		//start timer for easyTime seconds 
    		startTimer (EasyTime);
    	}
    	//if user selected hard quiz
        else 
        {
        	//set start and end for easy questions
        	start = HardQStart;
    		end = HardQEnd;
    		//start timer for hardTime seconds 
    		startTimer (HardTime);
        }
    	
    	//generate random list between start and end
    	mylist = createRandomList (start,end);
    	
    	//put in try-catch to aviod errors
    	try {
	    	//open file reader
	    	quizReader = new BufferedReader(new FileReader(StartFilepath + "/JavaProject/text/quiz.txt"));
	        
	    	//create array to copy the entire file
	        for (int i=0; i<(NumberOfQuestions*LinesPerQuestion); i++)
	        {
	        	fileQuestions[i] = quizReader.readLine();
	        }
	        //close reader
	        quizReader.close();
	        
	        //call the set method to output the first question
			set();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//method that outputs the question and options to user
	public void set() throws IOException  
    {  
		//array to randomize the options shown to users
		int[] list = createRandomList (0,3);
    	optionRB[4].setSelected(true); 
    	
		//first option after each question in the file is the correct answer
		key = list[0];
		
    	//set label to random question
		questionL.setText(fileQuestions[mylist[current]*LinesPerQuestion]);
		//set label to question number
		qNumberL.setText("Question " + (current + 1) + ".");
		
		//set options randomly
		optionRB[list[0]].setText(fileQuestions[mylist[current]*LinesPerQuestion+1]);
		optionRB[list[1]].setText(fileQuestions[mylist[current]*LinesPerQuestion+2]);
	 	optionRB[list[2]].setText(fileQuestions[mylist[current]*LinesPerQuestion+3]);
		optionRB[list[3]].setText(fileQuestions[mylist[current]*LinesPerQuestion+4]);
    }  
	
	//method that displays quiz timer and accepts amount of time as parameter
	public void startTimer (int time)
	{
		//construct timer with new actionlistener that goes up by seconds
		timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            //increaser counter
            timeCount++;
            	// output the amount of time left
	            if (timeCount <= time) 
	            {
	            	  //display to user how much time they have left
	            	  timerL.setText("Time left: " + Integer.toString(time-timeCount) + " seconds");
	            } 
	            //if time reaches 0, output message to user
	            else 
	            {
	            	//stop timer
	                timer.stop();
	                //output the final score to the user
	                JOptionPane.showMessageDialog(null,"Sorry.. looks like you've run out of time.\nBetter luck next time!\n\nYour Score: "+count+"/10"); 
	                
	                //return back to the home screen
	                home.doClick();
	            }
            }
		});
		//start timer
        timer.setInitialDelay(0);
        timer.start();
	}
	
	//method that keeps count of score
	private void nextQuestion() {
		//if user selects correct answer
        if(optionRB[key].isSelected())
        {
        	//record their point and output message
        	count=count+1;  
        	JOptionPane.showMessageDialog(null,"Correct!"); 
        }
        else
        {
        	//output message indicating the correct answer
        	JOptionPane.showMessageDialog(null,"Incorrect.\nCorrect Answer: '"+optionRB[key].getText()+"'"); 
        }
        //keep track of the number of questions asked 
        current++; 
        //update the value of the progress bar
        bar.setValue(current);
        
        //on the last question disable next button and enable result button
        if(current== (NumberOfQuestions-1))  
        {  
        	//disable next button
            nextB.setEnabled(false);
            //enable result button
            resultB.setVisible(true);   
        }  
        
        //call the set method to output next question and options
        try {
			set();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	//method that ends the quiz
	private void endQuiz() {
		//stop the timer
		timer.stop();
		
		//if user selects correct answer
    	 if(optionRB[key].isSelected()) 
    	 {
    		//record their point and output message
            count=count+1;  
        	JOptionPane.showMessageDialog(null,"Correct!"); 
    	 }
    	 else
    	 {
    		//output message indicating the correct answer
    		 JOptionPane.showMessageDialog(null,"Incorrect.\nCorrect Answer: '"+optionRB[key].getText()+"'"); 
    	 }
        //keep track of the number of questions asked 
        current++;  
        //update the value of the progress bar
        bar.setValue(current);
        
        //output the final score to the user
        JOptionPane.showMessageDialog(null,"Your Score: "+count+"/"+ current+" !"); 
        
        //return back to the home screen
        home.doClick();
		
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

	//method that chages button placements based on screen size
	private void changeButtonBounds(int width, int height, int BasicButtonW, int BasicButtonH) {
		//change background label bounds
		backgroundImage.setBounds(0, 0, width, height);
		
		//change basic button bounds
		//top right
		home.setBounds(width-BasicButtonW, 0, BasicButtonW, BasicButtonH);
		//top left
		settings.setBounds(0, 0, BasicButtonW, BasicButtonH);
	    
		//change difficuly level button bounds
		easy.setBounds((int)(width/EasyXR), (int)(height/DifficultyYR), (int)(width/DifficultyAndLabelBWR), (int)(height/DifficultyBHR));
		hard.setBounds((int)(width/HardXR), (int)(height/DifficultyYR), (int)(width/DifficultyAndLabelBWR), (int)(height/DifficultyBHR));
	    
		//change question label bounds
		questionL.setBounds((int)(width/QuestionAndOptionsXR),(int)(height/QuestionsYR),(int)(width/QuesionLWR),(int)(height/LabelsBarAndNextBHR)); 

		//change question number and timer additional label bbounds
		qNumberL.setBounds((int)(width/QuestionLXR),(int)(height/LabelsYR),(int)(width/DifficultyAndLabelBWR),(int)(height/LabelsBarAndNextBHR)); 
        timerL.setBounds((int)(width/TimerLXR),(int)(height/LabelsYR),(int)(width/DifficultyAndLabelBWR),(int)(height/LabelsBarAndNextBHR)); 
        
        //change progress bar button bounds
        bar.setBounds((int)(width/BarXR),(int)(height/BarYR),(int)(width/BarWR),(int)(height/LabelsBarAndNextBHR));
        bar.setSize((int)(width/BarWR), (int)(height/LabelsBarAndNextBHR));
        
        //change radio button bounds
        optionRB[0].setBounds((int)(width/QuestionAndOptionsXR),(int)(height/RadioYR1),(int)(width/RadioBWR),(int)(height/RadioBHR));  
        optionRB[1].setBounds((int)(width/QuestionAndOptionsXR),(int)(height/RadioAndNextYR2),(int)(width/RadioBWR),(int)(height/RadioBHR));  
        optionRB[2].setBounds((int)(width/QuestionAndOptionsXR),(int)(height/RadioYR3),(int)(width/RadioBWR),(int)(height/RadioBHR));  
        optionRB[3].setBounds((int)(width/QuestionAndOptionsXR),(int)(height/RadioYR4),(int)(width/RadioBWR),(int)(height/RadioBHR)); 
        
        //change next and result button bounds
        nextB.setBounds((int)(width/NextAndResultXR),(int)(height/RadioAndNextYR2),(int)(width/NextAndResultBWR),(int)(height/LabelsBarAndNextBHR));  
        resultB.setBounds((int)(width/NextAndResultXR),(int)(height/ResultYR),(int)(width/NextAndResultBWR),(int)(height/LabelsBarAndNextBHR)); 
	}

	// method that chages image sizes based on screen size
	private void changeImageSizes(int width, int height, int BasicButtonW, int BasicButtonH) {
		//declare local vars
	    Image tempImage;
	    Icon tempIcon;
	    
		//change background image size
	    tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/quiz.jpg").getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
		tempIcon = new ImageIcon(tempImage);
    	backgroundImage.setIcon(tempIcon);
		
	    //change basic button image sizes
	    tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/homeB.png").getImage().getScaledInstance(BasicButtonW,BasicButtonH,Image.SCALE_SMOOTH);
	    tempIcon = new ImageIcon(tempImage);
		home.setIcon(tempIcon);
		tempImage = new ImageIcon(StartFilepath + "/JavaProject/pictures/settingsB.png").getImage().getScaledInstance(BasicButtonW,BasicButtonH,Image.SCALE_SMOOTH);
		tempIcon = new ImageIcon(tempImage);
		settings.setIcon(tempIcon);
	}

	// method that chages font size based on screen size
	private void changeFontSize(int height) {
	    //change font size
		bigFont = new Font("TimesRoman", Font.BOLD, (int)(height/BigFontR));
		font = new Font("Symbol", Font.PLAIN, (int)(height/FontR));
		
		//add font to labels
        questionL.setFont(font);
        qNumberL.setFont(font);
        timerL.setFont(font);
        //add bigFont to buttons
		easy.setFont(bigFont);
		hard.setFont(bigFont);
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
	
	//method that creates a random list of numbers based on parameters
	public int[] createRandomList (int start, int end)
	{
		SecureRandom rd = new SecureRandom();
		
		//determine length
		int n = end - start + 1;
		//declare array
		int a[] = new int [n];
		//initialize array
		for (int i = 0; i < n; i++)
		{
			a[i] = i;
		}
		//declare array
		int[] result = new int [n];
		int x = n;
		
		//create list of random numbers
		for (int i = 0; i < n; i++)
		{
			int k = rd.nextInt(x); //generate random number within range
			result [i] = a[k]+start; //add to result array 
			a[k] = a[x-1]; //{1,2,3,4} if 3 is selected--> {1,2,2,4} --> if 4 is selected {1,2,2,2} until every number is slected
			x--;
		}
		//return list
		return result;
	}

}
