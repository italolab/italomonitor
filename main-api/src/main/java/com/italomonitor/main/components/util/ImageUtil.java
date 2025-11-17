package com.italomonitor.main.components.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

@Component
public class ImageUtil {

	public String imageToBase64( BufferedImage image ) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write( image, "GIF", out );
		return "data:image/gif;base64," + Base64.getEncoder().encodeToString( out.toByteArray() );		
	}
	
}
