package imgMain;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Hotkeys implements KeyListener {

	private static JxReader jxReader;
	  
	public Hotkeys(JxReader reader) {
		jxReader = reader;
	}

	public void keyReleased(KeyEvent e) {
	  //System.out.println(e.getKeyCode());
		if (e.getKeyCode() == 27){
			jxReader.exit();
		}else if (e.getKeyCode() == 70){	
			jxReader.fullScreen();
		}else if (e.getKeyCode() == 74){	
			jxReader.Jump();
		}else if (e.getKeyCode() == 72){	
			jxReader.HideMenu();
		}else if (e.getKeyCode() == 65){	
			jxReader.AlwaysOnTop();			
		}else if(e.getKeyCode() == 39){ //Right
			jxReader.pageRouter(1);
		}else if(e.getKeyCode() == 37){ //Left
			jxReader.pageRouter(2);
		}else if(e.getKeyCode() == 44){ //Comma
			jxReader.pageRouter(3);
		}else if(e.getKeyCode() == 46){ //Period
			jxReader.pageRouter(4);
		}else if(e.getKeyCode() == 90){ //Z
			jxReader.archiveChooser();
		}
	}
	
	public void keyTyped(KeyEvent e) {}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 38){ //Up
			jxReader.scroll(-80);
		}else if(e.getKeyCode() == 40){ //Down
			jxReader.scroll(80);
		}
  }
  
}
