package imgMain;

import javax.swing.UIManager;

class JxDriver {
	 	  
	public static void main(String args[]) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();     
		}
		JxReader jx = null;
		try{
			jx = new JxReader();
		}catch (Exception ex){
			System.out.println(ex.toString());
		}
		
		
		
		if (args.length > 0){
			jx.commandLine(args);
		}
	}
}