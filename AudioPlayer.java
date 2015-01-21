import javazoom.jl.player.Player;
import java.io.FileInputStream;
public class AudioPlayer implements Runnable{
    private Player player; //In JLayer (external class)
    private Thread thread;
    private boolean loop = false; //If audio is looping
    private String fileName;
    private boolean playing = false; //If audio is currently playing
    public AudioPlayer(String s){
	load(s);
    }
    public void load(String s){
<<<<<<< Updated upstream
	fileName = s;
	try{
	    player = new Player(new FileInputStream(s)); //Load audio
	    thread = new Thread(this); //Create new thread for audio
	}
	catch(Exception e){
	    e.printStackTrace();
	}
=======
 fileName = s;
 try{
//     player = new Player(new FileInputStream(s)); //Load audio
     thread = new Thread(this); //Create new thread for audio
 }
 catch(Exception e){
     e.printStackTrace();
 }
>>>>>>> Stashed changes
    }
    public void play(){
	thread = null; //Reset thread
	load(fileName); //Load audio
	playing = true;
	thread.start(); //Start audio
    }
    public void loop(){
	loop = true; //Set as looping then play
	play();
    }
    public void stop(){
<<<<<<< Updated upstream
	loop = false; //Stop everything
	thread = null;
	playing = false;
	player.close();
    }
    public void run(){
	do{
	    try{
		player.play(); //Play normally
	    }
	    catch(Exception e){
		e.printStackTrace();
	    }
	    if(loop) load(fileName); //Load again if looping
	} while(loop);
	playing = false; //Stopped playing
=======
 loop = false; //Stop everything
 thread = null;
 playing = false;
// player.close();
    }
    public void run(){
 do{
     try{
//  player.play(); //Play normally
     }
     catch(Exception e){
  e.printStackTrace();
     }
     if(loop) load(fileName); //Load again if looping
 } while(loop);
 playing = false; //Stopped playing
>>>>>>> Stashed changes
    }
    public boolean isPlaying(){
	return playing;
    }
}
