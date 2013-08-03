package imgMain;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class StringHandler {
	
	public String GetName(String fullName){
		int lastIndex = fullName.lastIndexOf(File.separatorChar);
		String newName = fullName.substring(lastIndex + 1);
		return newName;
	}
	
	public String UnixPath(String path){
		if (path.startsWith("file://")){path = path.substring(7);}
		try {
			return URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return path;
		}
	}
	
}
