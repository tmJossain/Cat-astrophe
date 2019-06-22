import java.util.Random;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;


public class CatGame extends Applet implements KeyListener, Runnable
{  
    int y = getHeight () + 280; //intialize y val of character in 1st game
    int score = 0; //initialize score of 1st game
    Boxes box1, box2, box3; //box objects
    //Arrays
    //x and y vals of the birds
    int[] birdX = new int [60];
    int[] birdY = new int [60];
    //x and y vals of the ducks (enemies)
    int[] enemyX = new int [17];
    int[] enemyY = new int [17];
    //For scrolling effects
    double grassX = -70;
    double grassDx = 3;
    //check if key is being pressed
    boolean clicked;
    //check if key was ever pressed
    boolean everClick = false;
    //check is 1st game is over
    boolean gameOver1 = false;
    //check if it's the 2nd game yet
    boolean next = false;
    Graphics bufferGraphics; // name for buffered graphics
    //Images
    Image finishSign;
    Image offscreen; //for buffering
    Image winnerCat;
    Image loserCat;
    Image picture;
    Image catpic;
    Image catfly;
    Image bigBird;
    Image bird;
    Image cat;
    Image fish;
    Image rock;
    Image chicken;
    AudioClip loopMusic2,loopMusic1;
    //Initialize score of 2nd game
    int score2 = 0;
    //Initalize x val of basket
    int x = 0;
    //checks if BasketGame is over
    boolean gameOver2 = false;

    Random rand = new Random ();

    int fishy = 0; //y val of fishes
    int time = 30; //initialize time
    Label timing; //for displaying time
    int count = 0; //count seconds down
    int fishX = rand.nextInt (1000); //randomize x val of fishes
    int chickenY = -500; //initialize y val of chickens
    int chickenX = rand.nextInt (1000); //randomize x val of chickens
    int rockX = -800; // initialize x val of rocks
    int rockY = rand.nextInt (1000); //randomize y val of rocks
    Boxes boxB; //another box object
    //Fonts
    Font f1;
    Font f2;
    Font f3;
    Font f4;

    //Applet dimensions
    Dimension dim;


    public void init ()
    {
	setSize (1500, 1000);
	dim = getSize ();
	addKeyListener (this);
	setBackground (new Color (179, 255, 255));
	//Buffer
	offscreen = createImage (dim.width, dim.height);
	bufferGraphics = offscreen.getGraphics ();
	//Images
	picture = getImage (getDocumentBase (), "grass.png");
	catpic = getImage (getDocumentBase (), "CatStanding.png");
	catfly = getImage (getDocumentBase (), "CatFlying.png");
	bird = getImage (getDocumentBase (), "birdFlying.png");
	finishSign = getImage (getDocumentBase (), "finishSign.png");
	winnerCat = getImage (getDocumentBase (), "winnerCat.png");
	loserCat = getImage (getDocumentBase (), "loserCat.png");
	bigBird = getImage (getDocumentBase (), "bigBird.png");
	cat = getImage (getDocumentBase (), "catchingCat.png");
	fish = getImage (getDocumentBase (), "fish.png");
	chicken = getImage (getDocumentBase (), "chicken.png");
	rock = getImage (getDocumentBase (), "rock.png");
    
       loopMusic2 =getAudioClip(getDocumentBase(), "Pim Poy.wav");
       loopMusic1 = getAudioClip(getDocumentBase(), "Patakas World.wav");
     
   
	//Text for time and it's dimensions
	timing = new Label ("Time: 30");
	timing.setBounds (20, 10, 50, 20);
	//Fonts
	f1 = new Font ("Algerian", Font.BOLD, 29);
	f2 = new Font ("Jokerman", Font.PLAIN, 150);
	f3 = new Font ("Improv", Font.BOLD, 32);
	f4 = new Font ("Arial", Font.ITALIC, 24);
    }


    //For animation
    public void start ()
    {

	Thread thread = new Thread (this);
	thread.start ();
    }


    //Takes cat to the right
    public void right ()
    {
	if (x < (getWidth () - 150))
	    x += 25;

    }


    //Takes cat to the left
    public void left ()
    {
	if (x > 25)
	    x -= 25;

    }


    //Make cat go up
    public void jump ()
    {
	if (y > 0)
	{
	    y -= 10;
	}
    }


    public void keyPressed (KeyEvent e)
    {
	everClick = true; //if key is ever pressed
	int keyy = e.getKeyCode ();
	clicked = true; //if key is being pressed

	//If up or space key is pressed, call jump then repaint
	if (keyy == KeyEvent.VK_UP || keyy == KeyEvent.VK_SPACE)
	{
	    jump ();

	    repaint ();

	}

	//If right key is pressed, call right then repaint
	if (keyy == KeyEvent.VK_RIGHT)
	{
	    right ();

	    repaint ();
	}
	//If left key is pressed, call left then repaint
	else if (keyy == KeyEvent.VK_LEFT)
	{

	    left ();
	    repaint ();
	}

    }
    
    
   //Displays the ending page, either a win celebration or a game over (lose) 
    public void winOrLose (Graphics g)
    {   

       //If the 1st game is over and it's still not the 2nd game
	if (gameOver1 == true && next == false)
	{   //then while the score is more than 22
	    if (score >= 22)
	    {   //display graphics and score
		bufferGraphics.setColor (new Color (159, 212, 254));
		bufferGraphics.fillRect (0, 0, getWidth (), getHeight ());
		bufferGraphics.setColor (new Color (69, 12, 102));
		bufferGraphics.drawString ("Your Score: " + score, getWidth () / 3, (getHeight () / 2) - 30);
		bufferGraphics.drawImage (winnerCat, (getWidth () / 2) - 400, getHeight () - 210, this);
		bufferGraphics.setFont (f2);
		bufferGraphics.drawString ("YOU WIN!", (getWidth () / 2) - 420, (getHeight () / 2) - 160);


	    }
	    //Otherwise, display "game over" graphics and score
	    else
	    {
		bufferGraphics.setColor (new Color (107, 102, 96));
		bufferGraphics.fillRect (0, 0, getWidth (), getHeight ());
		bufferGraphics.setColor (new Color (22, 252, 221));
		bufferGraphics.drawString ("Your Score: " + score, getWidth () / 3, (getHeight () / 2 - 26));
		bufferGraphics.drawString ("Points needed to win: " + (22 - score), getWidth () / 3, (getHeight () / 2 + 40));
		bufferGraphics.drawImage (loserCat, (getWidth () / 2) - 400, getHeight () - 170, this);
		bufferGraphics.setColor (new Color (79, 255, 255));
		bufferGraphics.setFont (f2);
		bufferGraphics.drawString ("GAME OVER!", (getWidth () / 2) - 420, (getHeight () / 2) - 160);
	    }
	    
	    //Displays more graphics
	    bufferGraphics.drawImage (finishSign, getWidth () / 3, getHeight () - 270, this);
	   
	    bufferGraphics.setFont (f3);
	    //Display message for how to enter the 2nd game
	     bufferGraphics.setColor (new Color (79, 255, 255));
	    bufferGraphics.fillRect(getWidth()/2+50,getHeight()- 220,getWidth()/2-140,150);
	     bufferGraphics.setColor (Color.black);
	    bufferGraphics.drawString ("Press any letter to choose the ", getWidth () / 2 + 100, getHeight () - 182);
	    bufferGraphics.drawString ("next game (Catch the Food)", (getWidth () / 2) + 100, getHeight () - 140);
	    bufferGraphics.drawString ("Press space to return here.", (getWidth () / 2) + 100, getHeight () - 95);
	    

	}
	//If the second game started and ended
	if (gameOver2 == true && next == true)
	{
	   //when the score is more than or equal to 18
	    if (score2 >= 18)
	    {   
	       //Display graphics and score
		bufferGraphics.setColor (new Color (159, 212, 254));
		bufferGraphics.fillRect (0, 0, getWidth (), getHeight ());
		bufferGraphics.setColor (new Color (69, 12, 102));
		bufferGraphics.drawString ("Your Score: " + score2, getWidth () / 3, (getHeight () / 2) - 30);
		bufferGraphics.drawImage (winnerCat, (getWidth () / 2) - 130, getHeight () - 170, this);
		bufferGraphics.setFont (f2);
		bufferGraphics.drawString ("YOU WIN!", (getWidth () / 2) - 420, (getHeight () / 2) - 160);
	    }
	     //Otherwise display game over graphics
	    else
	    {
		bufferGraphics.setColor (new Color (107, 102, 96));
		bufferGraphics.fillRect (0, 0, getWidth (), getHeight ());
		bufferGraphics.setColor (new Color (22, 252, 221));
		bufferGraphics.drawString ("Your Score: " + score2, getWidth () / 3, (getHeight () / 2 - 26));
		bufferGraphics.drawString ("Points needed to win: " + (18 - score2), getWidth () / 3, (getHeight () / 2 + 40));
		bufferGraphics.drawImage (loserCat, (getWidth () / 2) - 130, getHeight () - 240, this);
		bufferGraphics.setColor (new Color (79, 255, 255));
		bufferGraphics.setFont (f2);
		bufferGraphics.drawString ("GAME OVER!", (getWidth () / 2) - 420, (getHeight () / 2) - 160);
	    }

	}

    }

//When a key is entered (not held down)
    public void keyTyped (KeyEvent e)
    {   
	char choice = e.getKeyChar ();
	//As long as the 1st game is over
	if (gameOver1 == true)
	{   
	    //Any key other than space bar leads to next game
	    if (choice == ' ')
		next = false;
	    else
		next = true;
	    repaint (); 

	}
    }
    public void musicPlay1 () {
     if(next == false && gameOver1 == false) {
    loopMusic1.loop();
    }
    else
    loopMusic1.stop();
    
    }

    public void musicPlay2 () {
    
    if(next == true && gameOver2 == false) {
    loopMusic2.loop();
    }
    else
    loopMusic2.stop();
    
  }
   //When key is released
    public void keyReleased (KeyEvent e)
    {   
	clicked = false; // not held down
	everClick = true; //has been pressed before at least once
    }

   //Checks if the 1st game goes beyond boundaries of
   // where the birds stop appearing
    public void isItOver ()
    {  
	if (birdX [0] < -10600)
	//1st game is declared over
	 gameOver1 = true;
    }
    
    //Timer
    public void keepTime (Graphics g)
    {
	count++;
	if (count == 100) //limit
	{
	    time--; //decrement time
	    count = 0;
	}
	//display time as it decrements
	bufferGraphics.drawString ("Time: " + time, 10, 100);
	
	//when there is no time left, the 2nd game is over
	if (time <= 0)
	{
	    gameOver2 = true;
	}
	else
	    gameOver2 = false;

    }

//Run method
    public void run ()
    {
       

	while (true)
	{    
	   //if y val of fish reaches ground limit
	    if (fishy > 600)
	    {   
		fishy = 0;//y starts from starting point
		fishX = rand.nextInt (1000); //randomize x val again
	    }
	    //otherwise, increment the y val
	    else
		fishy += 7;
	   //same as fishX and fishY but with chicken imgs
	    if (chickenY > 600)
	    {
		chickenY = -500;
		chickenX = rand.nextInt (1000);
	    }
	    else
		chickenY += 7;
	    //Same as above but with the rocks
	    if (rockY > 600)
	    {
		rockY = -800;
		rockX = rand.nextInt (1000);
	    }
	    else
		rockY += 12; //increment faster
		
	    //Scrolling background effect
	    for (int k = 0 ; k < 60 ; k++)
	    {   //when game ends, don't move horizontal ground
		if (birdX [0] < -10600)
		    grassDx = 0;
		//scroll birds and ducks at constant speed when score less than 4
		if (score < 4)
		{
		    birdX [k] -= grassDx;
		    if (k < 17)
		    {
			enemyX [k] -= grassDx;
		    }
		}
		//Increase slowly when score is more than 3
		else
		{
		    birdX [k] -= grassDx;
		    if (k < 17)
		    {
			enemyX [k] -= grassDx;
		    }
		    grassDx = grassDx + 0.0002;
		}

	    }
	    //moving the grass
	    if (grassX > getWidth () * -1)
	    {
		grassX -= grassDx;
	    }
	    else
	    {
		grassX = -70;
	    }
	    
	    
	    //Animate cat falling
	    if (y < 360 && clicked == false) //when the y val is at ground level and key is released
		y += 10; //increment y

	    repaint ();

	    try
	    {
		Thread.sleep (17);
	    }
	    catch (InterruptedException e)
	    {
		e.printStackTrace ();

	    }
	}

    }

   //Constructor
    public CatGame ()
    {   
	//Loop through the varying x and y values for the birds/ducks and store in arrays
	for (int i = 0 ; i < 60 ; i++)
	{
	    box1 = new Boxes (i * 200, (int) (Math.random () * 400) + 70, 66, 56); //parameters x,y,width (w),height(h)
	    birdX [i] = box1.x; 
	    birdY [i] = box1.y;
	 
	    if (i < 17)
	    {
		box3 = new Boxes (i * 700, (int) (Math.random () * 400), 78, 80);
		enemyX [i] = box3.x + 27;
		enemyY [i] = box3.y + 27;
	    }
	}
	
	box2 = new Boxes (50, 100, 67, 147); //box for the flying cat in game 1
	boxB = new Boxes (0, 570, 140, 100); //box for cat with basket in game 2

    }

//Paint method

    public void paint (Graphics g)
    {
	//clear rect for buffering
	bufferGraphics.clearRect (0, 0, dim.width, dim.width);
	//onceMusic.play();
	
	//loopMusic.loop();
       
	
       
	//set Background
	bufferGraphics.setColor (new Color (153, 102, 51));
	bufferGraphics.fillRect (0, this.getHeight () - 100, this.getWidth (), 100);
	bufferGraphics.setColor (new Color (179, 255, 255));
	//Draw the birds
	for (int j = 0 ; j < 60 ; j++)
	{

	    bufferGraphics.drawRect (birdX [j] + 7, birdY [j] + 7, box1.w, box1.h);
	    bufferGraphics.drawImage (bird, birdX [j], birdY [j], this);
	    //Draw the ducks
	    if (j < 17)
	    {

		bufferGraphics.drawRect (enemyX [j] + 25, enemyY [j] + 27, box3.w, box3.h);
		bufferGraphics.drawImage (bigBird, enemyX [j], enemyY [j], this);
	    }

	}

	//Draw the grass  
	for (int i = 0 ; i <= getWidth () * 2 ; i += 270)
	{
	    bufferGraphics.drawImage (picture, (int) grassX + i, getHeight () - 220, this);
	}
	 
	//when the cat is not moving
	if ((clicked == false && everClick == false) || (clicked == false && y == 360))
	    {
		bufferGraphics.drawImage (catpic, 50, getHeight () - 210, this); //draw the standing cat pic
	    }
	//when it is moving
	else
	    {   //draw the rectangle around cat 
		box2.y = y + 100;
		bufferGraphics.drawRect (box2.x, box2.y, box2.w, box2.h);
		//draw image of cat "flying"
		bufferGraphics.drawImage (catfly, 50, y, this);
	    }
	//Rectangles wrapping the images in (to be used for collision detection)
	Rectangle rect1; // for birds
	Rectangle rect2 = box2.bounds (); // for the cat
	Rectangle rect3; // for the ducks
	
	//Loop through array
	for (int m = 0 ; m < 60 ; m++)
	{
	    box1.x = birdX [m]; //each x pos is the argument
	    box1.y = birdY [m];//each y pos is the argument
	    rect1 = box1.bounds ();//access method in Boxes class
	    
	    //when cat collides with the birds
	    if ((rect1.intersects (rect2)))
	    {   
		birdY [m] -= 1000; //birds go off screen
		score++; //increase score
	    }
	}
	//Loop through array for ducks (enemy birds)
	for (int p = 0 ; p < 17 ; p++)
	{
	    box3.x = enemyX [p];
	    box3.y = enemyY [p];
	    rect3 = box3.bounds ();
	    //when cat collides with ducks
	    if (rect2.intersects (rect3))
	    {
		//score restarts from 0
		score = 0;

	    }


	}
	
	 isItOver (); //call method to check is game 1 is over or not
	//Display score
	bufferGraphics.setFont (f1);
	bufferGraphics.setColor (Color.black);
	bufferGraphics.drawString (("Score: " + score), 10, 50);
	//Display instructions in game 1
	bufferGraphics.setFont (f4);
	bufferGraphics.drawString ("Press the UP arrow key or the space bar. Catch the birds. Avoid ducks.", 300, 40);
	bufferGraphics.setFont (f1);
	//Display end page
	winOrLose (g);
	 
	//if playing game 2
	if (next == true)
	{   
	    //Draw graphics
	    bufferGraphics.setColor (new Color (179, 255, 255));
	    bufferGraphics.fillRect (0, 0, getWidth (), getHeight ());
	    bufferGraphics.setColor (new Color (213, 149, 100));
	    bufferGraphics.fillRect (0, this.getHeight () - 100, this.getWidth (), 100);
	    bufferGraphics.setColor (new Color (179, 255, 255));
	    boxB.x = x; // x value of basket 
	    
	    bufferGraphics.drawImage (fish, fishX, fishy, this);

	    bufferGraphics.drawImage (chicken, chickenX, chickenY, this);

	    bufferGraphics.drawImage (cat, x, getHeight () - 300, this);

	    bufferGraphics.drawImage (rock, rockX, rockY, this);
	    
	    
	    Rectangle rectA = new Rectangle (fishX, fishy, 60, 93); //Rectangle for fishes
	    Rectangle rectB = boxB.bounds (); //rectangle for basket
	    Rectangle rectCat = new Rectangle (x + 20, getHeight () - 300, 130, 270); //rectangle for full cat
	    Rectangle rectC = new Rectangle (chickenX, chickenY, 100, 60); // rectangle for chickens
	    Rectangle rectD = new Rectangle (rockX, rockY, 140, 100); //rectangle for rocks
	    //As long as game 2 is not over 
	    if (gameOver2 == false)
	    {   //when basket collides with fishes
		if (rectB.intersects (rectA))
		{   
		    fishX -= 1000; //fishes off screen
		    score2++; //increase score by 1
		}
		//when basket collides with chickens
		else if (rectB.intersects (rectC))
		{
		    chickenX -= 1000; //chickens go off screen
		    score2 += 5; //increase score by 5

		}
		//when the rock touches any part of the cat
		else if (rectCat.intersects (rectD))
		{
		    rockX -= 1000; //rock disappears
		    score2 = 0; //score restarts from 0

		}
	    }
	    //Display score
	    bufferGraphics.setFont (f1);
	    bufferGraphics.setColor (Color.black);
	    bufferGraphics.drawString (("Score: " + score2), 10, 50);
	    //Display instructions for game 2
	    bufferGraphics.setFont (f4);
	    bufferGraphics.drawString ("Use the LEFT and RIGHT arrow keys. Catch the food. Avoid rocks.", 300, 40);
	    bufferGraphics.setFont (f1);
	    //count the time and display
	    keepTime (g);
	    //display end page
	    winOrLose (g);
	}
	 musicPlay2();
	 musicPlay1();
	g.drawImage (offscreen, 0, 0, this); //for double buffering

    }

   //Update method
    public void update (Graphics g)
    {
	paint (g);
    }
} // CatGame class

