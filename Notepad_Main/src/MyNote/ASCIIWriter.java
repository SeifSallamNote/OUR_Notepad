package MyNote;

import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ASCIIWriter 
{
	private String characters = "`^\\\",::;Il!i~+_-?][}{1)(|\\\\/tfjrxnnuvczXYUJCLQ0OZmwqpdbkhao**#MW&8%B@$$";
	private Map<Integer, Character> mapping = new HashMap<Integer, Character>();
	
	private BufferedImage resize(BufferedImage img, int w, int h)
	{
		Image resizedImage;
		
		resizedImage = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		
		BufferedImage returnBuffer = new BufferedImage(resizedImage.getWidth(null), resizedImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = returnBuffer.getGraphics();
		g.drawImage(resizedImage, 0, 0, null);
		g.dispose();
		
		return returnBuffer;
	}
	
	private BufferedImage rotate(BufferedImage img, boolean orientationRight)
	{
		int width  = img.getWidth();
		int height = img.getHeight();
		BufferedImage rotatedImage = new BufferedImage(height, width, img.getType());
		
		if(orientationRight == true)
		{
			for(int i=0; i < width; i++)
			{
				for(int j=0; j < height; j++)
				{
					rotatedImage.setRGB(height-1-j, i, img.getRGB(i,j));
				}
			}
		}
		
		else
		{
			for(int i=0; i < width; i++)
			{
				for(int j=0; j < height; j++)
				{
					rotatedImage.setRGB(j, width-1-i, img.getRGB(i,j));
				}
			}
		}
		
	    return rotatedImage;
	}
	
	public ASCIIWriter()
	{
		for(int i = 0; i < 25501; i++)
		{
			mapping.put(i/100, characters.charAt((int)(i/364)));
		}
	}
	
	public String WriteTXT(BufferedImage img)
	{
		int width = img.getWidth();
		int height = img.getHeight();
		
		BufferedImage adjustedImg = new BufferedImage(width, height, img.getType());
		
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				adjustedImg.setRGB(j, i, img.getRGB(j, i));
			}
		}
		
		if(width >= height)
		{
			int ratio = (int) Math.ceil((double) width / height);

			while(height > 150)
			{
				height /= 1.1;
				width = height * ratio;
			}
		}
		
		else
		{
			int ratio = (int) Math.ceil((double) height / width);

			while(width > 257)
			{
				width /= 1.1;
				height = width * ratio;
			}
		}
		
		adjustedImg = resize(adjustedImg, width, height);
		adjustedImg = rotate(adjustedImg, false);
		
		width = adjustedImg.getWidth();
		height = adjustedImg.getHeight();

		String res = "";
		
		int p,r,g,b,avg;
		
		for(int i = 0; i < width; i++)
		{
			for(int j = height - 1; j >= 0; j--)
			{
				p = adjustedImg.getRGB(i, j);
		
				r = (p>>16)&0xff;
				g = (p>>8)&0xff;
				b = p&0xff;
				
				avg = (r+g+b)/3;
				
				res += mapping.get(avg);
				res += mapping.get(avg);
				res += mapping.get(avg);
			}
			res += "\n";
		}
		return res;
	}
	
}