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

public class ProteinFoldAlg {
	
	public static void main (String args[])
	{
		System.out.println("\n- ALGORITHM BEGIN -\n");
		
		//ExampleString
		String SEQ20 = "10100110100101100101";
		
		//Setup array list
		ArrayList<AminoObject> Elements = new ArrayList<AminoObject>();
		
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
			AminoObject graphObject = new AminoObject(i, SEQ20.charAt(i), 0, 0); //Create object
			Elements.add(i, graphObject); //Add object to list
			System.out.println("Adding object to Elements list with value: " + graphObject.aminoValue);
		}
		
		//Print Elements
		System.out.println("Contents of Elements: " + Elements);
		
		//Start in middle of graph
		int height = size/2;
		int width = size/2;
		
		//Start in middle
		Grid[height][width] = Elements.get(0).aminoValue;
		
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
				Grid[hPos][wPos] = Elements.get(i).aminoValue;
				System.out.println("DOWN: " + Grid[hPos][wPos]);
			}
			
			else if (direct == 1)
			{
				prevHPos = hPos;
				hPos = hPos - 1;
				Grid[hPos][wPos] = Elements.get(i).aminoValue;
				System.out.println("UP: " + Grid[hPos][wPos]);
			}
			
			else if (direct == 2)
			{
				prevWPos = wPos;
				wPos = wPos - 1;
				Grid[hPos][wPos] = Elements.get(i).aminoValue;
				System.out.println("LEFT: " + Grid[hPos][wPos]);
			}
			
			else if (direct == 3)
			{
				prevWPos = wPos;
				wPos = wPos + 1;
				Grid[hPos][wPos] = Elements.get(i).aminoValue;
				System.out.println("RIGHT: " + Grid[hPos][wPos]);
			}
			
			Elements.get(i).posX = wPos;
			Elements.get(i).posY = hPos;
			System.out.println("Position is: X = " + Elements.get(i).posX + " and Y = " + Elements.get(i).posY);
			System.out.println("Previous Position: " + prevHPos + " " + prevWPos + "\n");
			
			if (Grid[hPos][wPos] == '1')
			{
				//We need to perform fitness check
				System.out.println("-> 1 detected; performing fitness check... <-"); 
				fitness = calcFitness(hPos, wPos, Grid, fitness, prevHPos, prevWPos);
				
				System.out.println("New fitness is: " + fitness + "\n");
			}
		}
		
		System.out.println("- ALGORITHM COMPLETE -");
		
		//Draw image
		Graphics drawGraph = new Graphics();
		drawGraph.drawObject(Elements);
		
	} //END MAIN
	
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
}
