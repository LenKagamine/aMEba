import java.awt.*;

public class Button{
    private int x,y,width,height;
    private static int border = 2;
    private static Color box,text;
    private static Font font;
    private String str;
    public Button(int x,int y,int w,int h,String str){
	this.x = x; //Set properties
	this.y = y;
	width = w;
	height = h;
	this.str = str;
	box = Color.black;
	text = Color.white;
	font = new Font("Tahoma",Font.PLAIN,20);
    }
    public void draw(Graphics2D g){ //Draw the button
	g.setColor(Color.gray);
	g.fillRect(x-border,y-border,width+border*2,height+border*2); //Border
	g.setColor(box);
	g.fillRect(x,y,width,height); //Button
	g.setColor(text);
	g.setFont(font);
	FontMetrics fm = g.getFontMetrics();
	g.drawString(str,x+width/2-fm.stringWidth(str)/2,y+height/2+fm.getHeight()/4); //Centered text
    }
    public void setString(String str){
	this.str = str;
    }
    public boolean click(int mx,int my){
	return (new Rectangle(x,y,width,height)).contains(new Point(mx,my)); //Check if mouse click is inside button
    }
}
