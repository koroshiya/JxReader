package imgMain;

import imgMain.controls.JImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FolderParser{
	
	private static ArrayList<JImage> jj = new ArrayList<JImage>();
	private static StringHandler stringHandler = new StringHandler();
	private String path;
	private String[] listOfFiles;
	private static final String[] supportedExtensions = {".png", ".jpg", ".gif", "jpeg", ".psd", ".bmp", ".wbmp"};
	
	public FolderParser(String newPath, String[] newListOfFiles){
	     path = newPath;
	     listOfFiles = newListOfFiles;
	}
	
	public ArrayList<JImage> parseFolder() throws IOException{
	    Arrays.sort(listOfFiles);
	    	
	    String parsedPath = stringHandler.UnixPath(path);
	    Thread[] threads = new Thread[listOfFiles.length];
	    for (int i = 0; i < listOfFiles.length; i++){
	    	try{
				Thread thread2 = new Thread(new FolderThread(parsedPath, listOfFiles[i]));
				threads[i] = thread2;
				thread2.start();
	    	}catch (Exception ex){
	    		System.out.println(ex.toString());
	    	}
	    }
		waitThread(threads);
		return jj;
	}
	
	private void waitThread(Thread[] threads){
		for (Thread thread : threads){
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class FolderThread implements Runnable{
	
		private String parsedPath;
		private String s;
		
		public FolderThread(String path, String spath) {
			parsedPath = path;
		     s = spath;
		}
		
		@Override
		public void run() {
			File f = new File(parsedPath + File.separator + s);
		    if (isSupported(f.getPath())){
				jj.add(new JImage(f.getAbsolutePath()));
		    }
		}
	}
	
	  private boolean isSupported(String extension){
		  for (String s : supportedExtensions){
			  if (extension.endsWith(s)){return true;}
		  }
		  return false;
	  }
	
}