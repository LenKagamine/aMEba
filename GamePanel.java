import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, MouseListener, MouseMotionListener{
    //Width, Height
    public static int WIDTH,HEIGHT;    
    //Thread
    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000/FPS;
    //Image/Drawing
    private BufferedImage image;
    private Graphics2D g;
    //Game
    private Level level;
    
    public static void main(String[] args){
	JFrame window = new JFrame("Summative");
	window.setContentPane(new GamePanel());
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//window.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
	//window.setUndecorated(true);
	window.setResizable(false);
	window.setExtendedState(JFrame.MAXIMIZED_BOTH);
	window.pack();
	window.setLocationRelativeTo(null);
	window.setVisible(true);
	
    }

    public GamePanel(){
	super();
	Toolkit tk = Toolkit.getDefaultToolkit();
	WIDTH = tk.getScreenSize().width;
	HEIGHT = tk.getScreenSize().height;
	setPreferredSize(new Dimension(WIDTH,HEIGHT)); //Set size
	setFocusable(true); //Allow focus;
	requestFocus(); //Set focus
    }
    
    public void addNotify(){
	super.addNotify();
	if(thread == null){
	    addMouseListener(this);
	    addMouseMotionListener(this);
	    thread = new Thread(this); //Create the thread if not created before
	    thread.start(); //Start thread
	}
    }
    
    public void run(){ //Main game loop
	image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	g = (Graphics2D) image.getGraphics();
	level = new Level();
	running = true;
	
	long start,elapsed,wait; //Time keeping for FPS
	while(running){
	    start = System.currentTimeMillis();
	    
	    update();
	    draw();
	    drawToScreen();
	    
	    elapsed = System.currentTimeMillis()-start;
	    
	    wait = targetTime - elapsed;
	    
	    if(wait < 0) wait = 0; //Negative wait time
	    try{
		Thread.sleep(wait); //Wait for FPS
	    } catch(Exception e){
		e.printStackTrace();
	    }
	}
    }
    
    public void update(){//Update game
	level.update();
    }
    public void draw(){//Draw game
	g.setColor(Color.white);
	g.fillRect(0,0,WIDTH,HEIGHT);
	level.draw(g);
    }
    public void drawToScreen(){ //Draw buffered image to level
	Graphics g2 = getGraphics();
	g2.drawImage(image,0,0,WIDTH,HEIGHT,null);
	g2.dispose();
    }
    public void mouseDragged(MouseEvent e){
	try{
	    level.mouse(e.getX(),e.getY());
	} catch(Exception ex){}
    }
    public void mouseMoved(MouseEvent e){
	try{
	    level.mouse(e.getX(),e.getY());
	} catch(Exception ex){}
    }
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){
	level.click(e.getX(),e.getY());
    }
    public void mouseReleased(MouseEvent e){
	level.release(e.getX(),e.getY());
    }
}
