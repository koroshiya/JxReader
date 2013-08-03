package imgMain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ServiceRegistry;
import javax.imageio.stream.ImageInputStream;

import net.sf.javavp8decoder.imageio.WebPImageReaderSpi;

public class WebPParser {
	
	public static BufferedImage decodeWebP(File f) throws IOException{
		
		ImageReader imageReader = getReader();
		ImageInputStream iis = ImageIO.createImageInputStream(f);
		imageReader.setInput(iis, false);
		
		return imageReader.read(0);
		
	}
	
	public static BufferedImage decodeWebP(InputStream is) throws IOException{
		
		ImageReader imageReader = getReader();
		ImageInputStream iis = ImageIO.createImageInputStream(is);
		imageReader.setInput(iis, false);
		
		return imageReader.read(0);
		
	}
	
	private static ImageReader getReader(){

		ServiceRegistry r = IIORegistry.getDefaultInstance();
		WebPImageReaderSpi s = new WebPImageReaderSpi();
		r.registerServiceProvider(s);
		
		Iterator<ImageReader> readers = ImageIO.getImageReadersBySuffix("WEBP");
		return (ImageReader) readers.next();
		
	}
	
}
