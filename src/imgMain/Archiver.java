package imgMain;

import imgMain.controls.JImage;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.archivers.ar.ArArchiveEntry;
import org.apache.commons.compress.archivers.ar.ArArchiveInputStream;
import org.apache.commons.compress.archivers.tar.*;

import junrar.Archive;
import junrar.exception.RarException;
import junrar.rarfile.FileHeader;

public class Archiver {
		
	/**
	 * TODO: Threading for oldz, oldr, r
	 * TODO: Finish threading
	 * TODO: Add threading to folder reader
	 * TODO: UTF-16+ for non-zip
	 * TODO: Add support for gz, bz2, tar.* formats
	 * */
	
	  private static final byte[] BUFFER = new byte[8192];
	  private static ArrayList<JImage> jj = new ArrayList<JImage>();
	  private String TEMP_DIR;
	  private static StringHandler stringHandler;
	  private static ZipFile zip;
	  private static final String[] supportedExtensions = {".png", ".jpg", ".gif", "jpeg", ".psd", ".bmp", ".wbmp"};
	
	Archiver(){
		TEMP_DIR = System.getProperty("java.io.tmpdir");
		stringHandler = new StringHandler();
	}
	
	private String delDir(){
		File tempFile = new File(TEMP_DIR + File.separator + "JXR");
		if (!tempFile.exists()){
	      	boolean result = tempFile.mkdir();
	      	if (!result){
	      		System.out.println("Coudln't write to temp directory");
	      	}
	      }
		tempFile.deleteOnExit();
		return TEMP_DIR + File.separator + "JXR" + File.separator;
	}
	
	  public ArrayList<JImage> oldZarchiver(File file){
		    ZipFile zip;
		    ZipArchiveEntry entry;
		    Enumeration<ZipArchiveEntry> entries;

			try {				
				String extractDir = delDir();
				OutputStream writtenFile;
				InputStream inFile;
			    int l = 0;
				//fis = new FileInputStream(file);
				zip = new ZipFile(file);
				entries = zip.getEntries();
				try {
					
					while(entries.hasMoreElements()) {
						entry = (ZipArchiveEntry) entries.nextElement();
						if (entry.isDirectory()){
							//System.out.println(extractDir + entry.getName());
							//new File(extractDir + File.separator + entry.getName()).mkdir();
							continue;
						}
						String tempPath = extractDir + entry.getName().substring(entry.getName().lastIndexOf('/') + 1);
						writtenFile = new FileOutputStream(tempPath);
						inFile = zip.getInputStream(entry);
						while ((l = inFile.read(BUFFER)) >= 0) {
							writtenFile.write(BUFFER, 0, l);
						}
						writtenFile.close();
							
						File f = new File(stringHandler.UnixPath(tempPath));
				        if (isSupported(f.getAbsolutePath())){
					    	jj.add(new JImage(f.getAbsolutePath()));
				        }
				        f.deleteOnExit();
						
					}
				} finally {
				    zip.close();
				}
			    zip.close();
			 } catch(Exception e) {
			     e.printStackTrace();
			 }
			return jj;
		  }
	
	  public ArrayList<JImage> oldRarchiver(File file) throws IOException{
		
		  String extractDir = delDir();
		  Archive arch = null;
		  
		  try {
			arch = new Archive(file);
			List<FileHeader> headers = arch.getFileHeaders();
		    Iterator<FileHeader> iterator = headers.iterator();
		    FileOutputStream os;
		    int i = 0;
		    File f;
		    String[] listOfFiles = new String[500];
		    
			while (iterator.hasNext()){
				//if (iterator.next() == null){break;}
				f = new File(extractDir + headers.get(i).getFileNameString());
				f.deleteOnExit();
				if (f.getName() != null){
					listOfFiles[i] = stringHandler.UnixPath(extractDir + f.getName());
				}
				
				os = new FileOutputStream(f);
			    arch.extractFile(iterator.next(), os);
			    os.close();
		        i++;
			}

			for (int j = 0 ; j < i; j++){
				String s = listOfFiles[j];
				f = new File(s);
		        if (isSupported(s)){
			    	jj.add(new JImage(f.getAbsolutePath()));
		        }
			}
			
		} catch (RarException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			arch.close();
		}
		  return jj;
	  }

	  public ArrayList<JImage> Tarchiver(File file){
		  TarArchiveInputStream zip;
		  TarArchiveEntry entry;
			try {			
				
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				ArchiveInputStream arc = new ArchiveStreamFactory().createArchiveInputStream(bis);
				zip = (TarArchiveInputStream) arc;
				try {
					entry = zip.getNextTarEntry();
					System.out.println(entry.getName());
					do{
						if (isSupported(entry.getName())){
							ByteArrayInputStream s = new ByteArrayInputStream(parseEntry(zip, entry));
							jj.add(new JImage(s, stringHandler.GetName(entry.getName())));
						}
						entry = zip.getNextTarEntry();
					}while(entry != null);
				}catch (Exception ex){
					System.out.println(ex.toString());
					System.out.println("Tarchiver exception");
				}finally {
				    zip.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return jj;
		}
	  
	  public ArrayList<JImage> ArArchiver(File file){
		  ArArchiveInputStream zip;
		  ArArchiveEntry entry;
			try {			
				
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				ArchiveInputStream arc = new ArchiveStreamFactory().createArchiveInputStream(bis);
				zip = (ArArchiveInputStream) arc;
				try {
					entry = zip.getNextArEntry();

					do{
						if (isSupported(entry.getName())){
							ByteArrayInputStream s = new ByteArrayInputStream(parseEntry(zip, entry));
							jj.add(new JImage(s, stringHandler.GetName(entry.getName())));
						}
						entry = zip.getNextArEntry();						
					}while(entry != null);
				}catch (Exception ex){
					System.out.println(ex.toString());
					System.out.println("ArArchiver exception");
				}finally {
				    zip.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return jj;
		}
	  
	  private byte[] parseEntry(ArchiveInputStream stream, ArchiveEntry entry) throws IOException{
				if (isSupported(entry.getName())){
					
					byte[] content = new byte[(int) entry.getSize()];
					do {
						stream.read(content, 0, content.length - 0);
					}while (stream.getBytesRead() == entry.getSize());
					return content;
				}
				return null;
	  }
	  
	  public ArrayList<JImage> Zarchiver(File file){
		    
		    ZipArchiveEntry entry;
		    Enumeration<ZipArchiveEntry> entries;
			try {				
				
				zip = new ZipFile(file);
				
				entries = zip.getEntries();
				try {
					ArrayList<Thread> threads = new ArrayList<Thread>();
					//Thread[] threads = new Thread[zip.getEntries()];
					//int i = 0;
					while(entries.hasMoreElements()) {
						entry = (ZipArchiveEntry) entries.nextElement();
						
						Thread thread2 = new Thread(new zarThread(entry));
						threads.add(thread2);
						thread2.start();
					}
					waitThread(threads);
				}catch (Exception ex){
					System.out.println(ex.toString());
					System.out.println("Zarchiver exception");
				}finally {
				    zip.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return jj;
		}

	public ArrayList<JImage> rarchiver(File file) throws IOException{
		  Archive arch = null;
		  List<FileHeader> headers = null;
		  try {
			arch = new Archive(file);
			headers = arch.getFileHeaders();
		    
		    int i = 0;
		    //Thread[] threads = new Thread[headers.size()];
		    InputStream iStream;
		    for (i = 0; i < headers.size(); i++){
		    	
		    	iStream = arch.getInputStream(headers.get(i));
		    	String s = headers.get(i).getFileNameString();
				if (isSupported(s)){
					try{
						JImage jImage = new JImage(iStream, stringHandler.GetName(s));
				    	jj.add(jImage);
					}catch (Exception ex){
						System.out.println("rarThread: " + ex.toString());
					}
		        }
				/*Thread thread2 = new Thread(new rarThread(headers.get(i).getFileNameString(), iStream));
				threads[i] = thread2;
				thread2.start();*/
				//thread2.join();
		    }
		    //waitThread(threads);
		} catch (RarException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			arch.close();
			headers.clear();
		}
		  return jj;
	  }
	
	private void waitThread(ArrayList<Thread> threads){
		for (Thread thread : threads){
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class zarThread implements Runnable{
		private ZipArchiveEntry entry = null;
		
		public zarThread(ZipArchiveEntry entry) {
		     this.entry = entry;
		}

		public void run(){
			if (isSupported(entry.getName())){
				InputStream inFile = null;
				try {
					inFile = zip.getInputStream(entry);
					jj.add(new JImage(inFile, stringHandler.GetName(entry.getName())));
					inFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}       	
		}
	}

	public static class rarThread implements Runnable{
		private String s;
		private InputStream iStream;
		
		public rarThread(String s, InputStream iStream) {
		     this.s = stringHandler.GetName(s);
		     this.iStream = iStream;
		}

		public void run(){
			if (isSupported(s)){
				try{
					JImage jImage = new JImage(iStream, stringHandler.GetName(s));
			    	jj.add(jImage);
				}catch (Exception ex){
					System.out.println("rarThread: " + ex.toString());
				}
	        }
		}
	}
	
	/*private static File writeStreamToFile(InputStream inputStream, String s) throws FileNotFoundException{

		File f = new File(s);
		OutputStream outs = new FileOutputStream(f);
	 
		try {
			int read = 0;
			byte[] bytes = new byte[1024];
		 
			while ((read = inputStream.read(bytes)) != -1) {
				outs.write(bytes, 0, read);
			}
		} catch (IOException e) {
		    System.out.println(e.getMessage());
		}
		
		return f;
		
	}*/
	
	private static boolean isSupported(String extension){
		for (String s : supportedExtensions){
			if (extension.toLowerCase().endsWith(s)){return true;}
		}
		return false;
	}
	
}
