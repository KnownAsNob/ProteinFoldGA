import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.util.List;

public class Graphics 
{
	public void drawObject(List<AminoObject> Elements)
	{
		//Draw background
		int height = 500;
		int width = 800;
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g2.setColor(Color.YELLOW);
		g2.fillRect(0, 0, width, height);

		int cellSize = 50;
		int increase = 60;
		
		for (int i = 0; i < Elements.size(); i ++)
		{
			g2.setColor(new Color(0, 200, 0));
			g2.fillRect(Elements.get(i).posX * 10, Elements.get(i).posY * 10, cellSize, cellSize);
			
			increase = increase + increase;
		}
		
		/*g2.setColor(new Color(0, 200, 0));
		g2.fillRect(50, 50, cellSize, cellSize);

		g2.setColor(new Color(255, 0, 0));
		g2.fillRect(250, 50, cellSize, cellSize);
		
		g2.setColor(Color.BLACK);
		g2.drawLine(50 + cellSize, 50+cellSize/2, 250, 50+cellSize/2);
		
		g2.setColor(new Color(255, 255, 255));
		String label = "GA";
		Font font = new Font("Serif", Font.PLAIN, 40);
		g2.setFont(font);
		FontMetrics metrics = g2.getFontMetrics();
		int ascent = metrics.getAscent();
		int labelWidth = metrics.stringWidth(label);

		g2.drawString(label, 50 + cellSize/2 - labelWidth/2 , 50 + cellSize/2 + ascent/2);*/
		
		
		String folder = "/tmp/alex/ga";
		String filename = "ProteinPicture.png";
		if (new File(folder).exists() == false) new File(folder).mkdirs();
		
		try 
		{
			ImageIO.write(image, "png", new File(filename));
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void drawAcidElement()
	{
		
	}
}