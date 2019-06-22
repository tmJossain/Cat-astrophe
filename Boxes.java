// The "Birds" class.
import java.applet.*;
import java.awt.*;

public class Boxes extends Applet
{
    // Place instance variables here
   
    int x, y,w,h;
    public Boxes (int startX,int startY, int width,int height) {
    x = startX;
    y = startY;
    w = width;
    h = height;
    
    }
    public Rectangle bounds (){
    
    return(new Rectangle(x,y,w,h));
    }
  
} // Birds class
