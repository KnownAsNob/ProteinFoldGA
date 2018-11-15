/*Get cells as close as possible. Overlapping is bad. Want highest fitness value possible (Black that are adjacent on grid
but not direct neighbors

0 = hydrophil, "white"
1 = hydrophob, "black"

Example: "10100110100101100101"

DEV: 
- Stop overlapping*
- Enable multiple examples
- Loop to find the best
- Stop fitness increase of adjacent in list proteins
*/

import java.util.ArrayList;
import java.util.Random;

// Import for image processing
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ProteinFoldAlg {
	
	public static void main (String args[])
	{
		System.out.println("\n- ALGORITHM BEGIN -\n");
		
		//ExampleString
		String SEQ20 = "10100110100101100101";
		
		//Setup array list
		ArrayList<Character> Elements = new ArrayList<Character>();
		
		//Setup 2D grid
		int size = SEQ20.length();
		
		if (size % 2 != 0)
		{
			size = size + 1;
		}
		
		char[][] Grid = new char[size][size];
		
		for (int i = 0; i < size; i ++)
		{
			for (int y = 0; y < size; y ++)
			{
				Grid[i][y] = 'N';
			}
		}
		
		//Add chars to Elements
		for (int i = 0; i < SEQ20.length(); ++ i)
		{
			System.out.println("Adding to Elements list: " + SEQ20.charAt(i));
			Elements.add(i, SEQ20.charAt(i));
		}
		
		//Print Elements
		System.out.println("Contents of Elements: " + Elements);
		
		//Writing and folding protein 
		int height = size/2;
		int width = size/2;
		
		//Start in middle
		Grid[height][width] = Elements.get(0);
		
		//Set protein position
		int hPos = height;
		int wPos = width;
		
		//Set previous position
		int prevHPos = 0;
		int prevWPos = 0;
		
		int fitness = 0;
		
		int direct = 4;
		
		System.out.println("\n- BEGIN FOLDING -\n");
		
		for (int i = 0; i < SEQ20.length(); ++ i)
		{
			//4 if first element; has to be be in center
			
			if (i != 0)
			{
				direct = fold();
			}
			
			if (direct == 4)
			{
				prevHPos = hPos;
				prevWPos = wPos;
				System.out.println("MIDDLE: " + Grid[hPos][wPos]);
			}
			
			if (direct == 0)
			{
				prevHPos = hPos;
				hPos = hPos + 1;
				Grid[hPos][wPos] = Elements.get(i);
				System.out.println("DOWN: " + Grid[hPos][wPos]);
			}
			
			else if (direct == 1)
			{
				prevHPos = hPos;
				hPos = hPos - 1;
				Grid[hPos][wPos] = Elements.get(i);
				System.out.println("UP: " + Grid[hPos][wPos]);
			}
			
			else if (direct == 2)
			{
				prevWPos = wPos;
				wPos = wPos - 1;
				Grid[hPos][wPos] = Elements.get(i);
				System.out.println("LEFT: " + Grid[hPos][wPos]);
			}
			
			else if (direct == 3)
			{
				prevWPos = wPos;
				wPos = wPos + 1;
				Grid[hPos][wPos] = Elements.get(i);
				System.out.println("RIGHT: " + Grid[hPos][wPos]);
			}
			
			System.out.println("Position: " + hPos + " " + wPos);
			System.out.println("Previous Position: " + prevHPos + " " + prevWPos + "\n");
			
			if (Grid[hPos][wPos] == '1')
			{
				//We need to perform fitness check
				System.out.println("-> 1 detected; performing fitness check... <-"); 
				fitness = calcFitness(hPos, wPos, Grid, fitness, prevHPos, prevWPos);
				
				System.out.println("New fitness is: " + fitness + "\n");
			}
		}
		drawBack();
		System.out.println("- ALGORITHM COMPLETE -");
	}
	
	//Decide direction and calculate value
	public static int fold()
	{
		//0 = down, 1 = up, 2 = left, 3 = right
		Random num = new Random();
		int direction;
		
		direction = num.nextInt(4);
		
		//Make sure tile can't travel backwards
		/*while ()
		{
			System.out.println("Trying to overlap, rectifying...\n");
			direction = num.nextInt(4);
		}*/
		
		return direction;
	}
	
	public static int calcFitness(int hPos, int wPos, char [][] Grid, int fit, int pHPos, int pWPos)
	{
		//The position of the new protein is position. We need to search up down left and right for fitness
		//Proteins adjacent on line are NOT indirectly adjacent

		//Check DOWN + UP
		if (Grid[hPos + 1][wPos] == '1' || Grid[hPos - 1][wPos] == '1')
		{
			System.out.println("*Element DOWN OR UP is 1; Fitness + 1");
			fit = fit + 1;
		}
			
		//Check RIGHT + LEFT
		if (Grid[hPos][wPos + 1] == '1' || Grid[hPos][wPos - 1] == '1')
		{
			System.out.println("*Element RIGHT OR LEFT is 1; Fitness + 1");
			fit = fit + 1;
		}
		
		return fit;
		
	}
	
	public static void drawBack()
	{
		int height = 500;
		int width = 800;
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g2.setColor(Color.YELLOW);
		g2.fillRect(0, 0, width, height);

		int cellSize = 80;
		
		g2.setColor(new Color(0, 200, 0));
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

		g2.drawString(label, 50 + cellSize/2 - labelWidth/2 , 50 + cellSize/2 + ascent/2);
		
		
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

	public static void drawItem()
	{
		
	}
}
