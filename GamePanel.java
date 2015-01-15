import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, MouseListener, MouseMotionListener{
    //Width, Height
    //public static final int WIDTH = 1024,HEIGHT = 600;
    public static int WIDTH, HEIGHT;
    //Thread
    private Thread thread;
    private boolean running;
    private int FPS = 60;
    private long targetTime = 1000/FPS;
    //Image/Drawing
    private BufferedImage image;
    private Graphics2D g;
    //Games
    private static Menu menu;
    private static Level level;
    private static boolean ingame = false;
    
    public static void main(String[] args){
	JFrame window = new JFrame("Summative");
	window.setContentPane(new GamePanel());
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//window.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
	//window.setUndecorated(true);
	window.setResizable(false);
	//window.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
	menu = new Menu();
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
	if(ingame) level.update();
	else menu.update();
    }
    public void draw(){//Draw game
	g.setColor(Color.white);
	g.fillRect(0,0,WIDTH,HEIGHT);
	if(ingame) level.draw(g);
	else menu.draw(g);
    }
    public void drawToScreen(){ //Draw buffered image to level
	Graphics g2 = getGraphics();
	g2.drawImage(image,0,0,WIDTH,HEIGHT,null);
	g2.dispose();
    }
    public static void startGame(){
	level = new Level();
	ingame = true;
	menu = null;
    }
    public void mouseDragged(MouseEvent e){
	try{
	    if(ingame) level.mouse(e.getX(),e.getY());
	    else menu.mouse(e.getX(),e.getY());
	} catch(Exception ex){}
    }
    public void mouseMoved(MouseEvent e){
	try{
	    if(ingame) level.mouse(e.getX(),e.getY());
	    else menu.mouse(e.getX(),e.getY());
	} catch(Exception ex){}
    }
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){
	try{
	    if(ingame) level.click(e.getX(),e.getY());
	    else menu.click(e.getX(),e.getY());
	} catch(Exception ex){}
    }
    public void mouseReleased(MouseEvent e){}
}
