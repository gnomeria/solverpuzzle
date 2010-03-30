/*
 * Created on 28 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;

import jyt.game.puzzle.solving.Puzzle;

public class PuzzleBuilderFromKado implements IPuzzleBuilder
{
	private static final Color[][] MATCH = new Color[][]
   	{
   		new Color[] {new Color(132, 182, 148),new Color(206, 223, 206),new Color(82, 158, 115),new Color(41, 130, 82),new Color(33, 125, 82),new Color(41, 130, 82),new Color(33, 125, 82),new Color(41, 130, 82),new Color(132, 178, 148),new Color(206, 223, 206),new Color(49, 130, 82),new Color(41, 130, 82),new Color(33, 125, 82),new Color(41, 130, 82),new Color(33, 121, 74),new Color(41, 125, 82),new Color(33, 121, 82),new Color(41, 130, 82),new Color(33, 121, 74),new Color(33, 121, 74),new Color(189, 239, 41),new Color(181, 235, 33),new Color(41, 130, 82),new Color(33, 117, 74),new Color(41, 125, 82),new Color(33, 121, 74),new Color(41, 125, 82),new Color(33, 117, 74),new Color(189, 239, 41),new Color(181, 235, 33),new Color(41, 125, 82),},
   		new Color[] {new Color(140, 186, 148),new Color(206, 219, 206),new Color(123, 182, 148),new Color(82, 158, 115),new Color(90, 162, 115),new Color(90, 162, 115),new Color(90, 162, 115),new Color(82, 154, 115),new Color(156, 190, 165),new Color(206, 219, 206),new Color(123, 178, 140),new Color(41, 125, 82),new Color(41, 125, 82),new Color(33, 125, 82),new Color(41, 130, 82),new Color(41, 125, 82),new Color(41, 125, 82),new Color(33, 121, 82),new Color(41, 125, 82),new Color(41, 125, 82),new Color(181, 235, 41),new Color(181, 235, 41),new Color(33, 121, 82),new Color(41, 125, 82),new Color(41, 121, 82),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(181, 235, 41),new Color(181, 235, 41),new Color(33, 117, 74),},
   		new Color[] {new Color(132, 182, 148),new Color(206, 223, 206),new Color(206, 219, 206),new Color(206, 223, 206),new Color(206, 219, 206),new Color(206, 219, 206),new Color(206, 219, 206),new Color(214, 227, 206),new Color(206, 219, 206),new Color(206, 219, 198),new Color(206, 219, 206),new Color(140, 186, 156),new Color(33, 125, 82),new Color(41, 125, 82),new Color(41, 125, 82),new Color(41, 130, 82),new Color(33, 121, 82),new Color(41, 125, 82),new Color(41, 125, 82),new Color(33, 121, 82),new Color(189, 239, 41),new Color(181, 235, 41),new Color(41, 121, 82),new Color(41, 121, 82),new Color(41, 125, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(41, 121, 74),new Color(189, 239, 41),new Color(181, 235, 41),new Color(41, 121, 82),},
   		new Color[] {new Color(140, 182, 148),new Color(198, 215, 198),new Color(189, 207, 189),new Color(181, 203, 173),new Color(181, 203, 181),new Color(173, 199, 173),new Color(181, 203, 181),new Color(173, 195, 173),new Color(181, 203, 181),new Color(173, 199, 173),new Color(181, 203, 181),new Color(99, 166, 123),new Color(41, 125, 82),new Color(33, 121, 74),new Color(41, 125, 82),new Color(33, 121, 74),new Color(41, 125, 82),new Color(33, 121, 74),new Color(41, 125, 82),new Color(41, 125, 82),new Color(181, 231, 33),new Color(181, 235, 41),new Color(33, 117, 74),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(181, 231, 33),new Color(181, 235, 41),new Color(33, 117, 74),},
   		new Color[] {new Color(132, 182, 148),new Color(214, 227, 214),new Color(82, 154, 107),new Color(41, 130, 82),new Color(33, 125, 82),new Color(41, 130, 82),new Color(33, 121, 74),new Color(41, 130, 82),new Color(33, 121, 82),new Color(41, 130, 82),new Color(33, 121, 74),new Color(41, 125, 82),new Color(33, 121, 74),new Color(41, 130, 82),new Color(33, 121, 74),new Color(41, 125, 82),new Color(33, 121, 74),new Color(41, 125, 82),new Color(33, 117, 74),new Color(33, 121, 74),new Color(181, 235, 41),new Color(181, 231, 33),new Color(41, 125, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 125, 82),new Color(33, 117, 74),new Color(181, 235, 41),new Color(181, 231, 33),new Color(41, 121, 82),},
   		new Color[] {new Color(99, 170, 123),new Color(173, 199, 173),new Color(82, 154, 107),new Color(41, 125, 82),new Color(41, 125, 82),new Color(33, 125, 82),new Color(41, 130, 82),new Color(41, 125, 82),new Color(41, 125, 82),new Color(33, 125, 82),new Color(41, 125, 82),new Color(41, 125, 82),new Color(41, 121, 82),new Color(33, 121, 82),new Color(41, 125, 82),new Color(41, 121, 82),new Color(41, 121, 82),new Color(33, 121, 74),new Color(41, 125, 82),new Color(41, 121, 74),new Color(181, 235, 41),new Color(181, 235, 41),new Color(33, 117, 74),new Color(41, 121, 82),new Color(41, 121, 82),new Color(41, 117, 74),new Color(33, 117, 74),new Color(41, 121, 82),new Color(181, 235, 41),new Color(181, 235, 41),new Color(33, 117, 74),},
   		new Color[] {new Color(33, 125, 82),new Color(41, 125, 82),new Color(41, 125, 82),new Color(41, 130, 82),new Color(33, 125, 82),new Color(41, 125, 82),new Color(41, 125, 82),new Color(41, 130, 82),new Color(33, 125, 82),new Color(41, 125, 82),new Color(41, 121, 82),new Color(41, 125, 82),new Color(33, 121, 74),new Color(41, 125, 82),new Color(41, 121, 82),new Color(41, 125, 82),new Color(33, 121, 74),new Color(41, 121, 82),new Color(41, 121, 74),new Color(66, 65, 57),new Color(74, 73, 66),new Color(66, 69, 57),new Color(66, 65, 57),new Color(41, 121, 74),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(66, 69, 57),new Color(74, 73, 66),new Color(66, 69, 57),new Color(66, 65, 57),},
   		new Color[] {new Color(41, 130, 82),new Color(33, 121, 74),new Color(41, 130, 82),new Color(57, 138, 90),new Color(41, 125, 82),new Color(33, 121, 74),new Color(41, 125, 82),new Color(33, 121, 74),new Color(41, 125, 82),new Color(33, 121, 74),new Color(41, 125, 82),new Color(33, 121, 74),new Color(41, 125, 82),new Color(33, 117, 74),new Color(41, 125, 82),new Color(33, 121, 74),new Color(41, 121, 82),new Color(33, 117, 74),new Color(74, 73, 57),new Color(173, 170, 148),new Color(181, 182, 165),new Color(231, 227, 222),new Color(239, 235, 231),new Color(66, 69, 57),new Color(33, 117, 74),new Color(41, 121, 82),new Color(66, 65, 57),new Color(165, 162, 140),new Color(181, 178, 165),new Color(231, 227, 222),new Color(239, 235, 231),},
   		new Color[] {new Color(33, 125, 82),new Color(74, 154, 107),new Color(156, 186, 165),new Color(206, 219, 206),new Color(198, 215, 198),new Color(156, 190, 165),new Color(41, 121, 82),new Color(148, 186, 156),new Color(181, 203, 181),new Color(140, 182, 148),new Color(41, 125, 82),new Color(41, 125, 82),new Color(33, 121, 74),new Color(41, 125, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 125, 82),new Color(66, 69, 57),new Color(115, 117, 99),new Color(123, 121, 107),new Color(173, 170, 148),new Color(214, 215, 206),new Color(66, 65, 57),new Color(41, 121, 82),new Color(33, 117, 74),new Color(74, 73, 66),new Color(115, 117, 99),new Color(123, 125, 107),new Color(173, 170, 148),new Color(214, 215, 206),},
   		new Color[] {new Color(66, 142, 99),new Color(206, 219, 206),new Color(206, 223, 206),new Color(206, 223, 206),new Color(206, 219, 206),new Color(206, 219, 206),new Color(189, 211, 189),new Color(206, 219, 206),new Color(206, 219, 206),new Color(206, 219, 206),new Color(198, 215, 198),new Color(66, 138, 99),new Color(41, 121, 82),new Color(33, 121, 74),new Color(41, 121, 82),new Color(41, 121, 82),new Color(41, 121, 74),new Color(33, 117, 74),new Color(66, 65, 57),new Color(132, 134, 115),new Color(140, 138, 123),new Color(156, 154, 132),new Color(165, 162, 140),new Color(66, 69, 57),new Color(41, 117, 74),new Color(33, 117, 74),new Color(74, 73, 66),new Color(140, 138, 115),new Color(132, 134, 115),new Color(156, 154, 132),new Color(165, 162, 140),},
   		new Color[] {new Color(132, 178, 148),new Color(206, 219, 206),new Color(173, 199, 173),new Color(49, 134, 90),new Color(41, 125, 82),new Color(189, 207, 189),new Color(206, 215, 198),new Color(206, 219, 206),new Color(90, 158, 115),new Color(148, 186, 156),new Color(206, 219, 206),new Color(140, 182, 156),new Color(33, 117, 74),new Color(41, 121, 82),new Color(41, 121, 74),new Color(41, 125, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(74, 73, 57),new Color(173, 174, 156),new Color(181, 182, 165),new Color(239, 235, 231),new Color(239, 235, 231),new Color(74, 73, 66),new Color(33, 117, 74),new Color(41, 117, 74),new Color(66, 65, 57),new Color(173, 174, 156),new Color(181, 182, 165),new Color(239, 235, 231),new Color(239, 235, 231),},
   		new Color[] {new Color(140, 182, 148),new Color(198, 215, 198),new Color(66, 142, 99),new Color(33, 121, 74),new Color(41, 125, 82),new Color(148, 182, 156),new Color(206, 223, 206),new Color(132, 178, 148),new Color(41, 125, 82),new Color(41, 125, 82),new Color(206, 219, 206),new Color(173, 195, 173),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(33, 117, 74),new Color(74, 73, 66),new Color(173, 170, 148),new Color(189, 190, 173),new Color(231, 231, 231),new Color(239, 239, 239),new Color(66, 65, 57),new Color(41, 121, 82),new Color(33, 113, 74),new Color(74, 73, 66),new Color(165, 166, 148),new Color(189, 186, 173),new Color(231, 231, 231),new Color(239, 239, 239),},
   		new Color[] {new Color(132, 178, 148),new Color(206, 223, 206),new Color(99, 166, 123),new Color(41, 125, 82),new Color(41, 125, 82),new Color(198, 211, 189),new Color(198, 215, 198),new Color(90, 158, 115),new Color(33, 121, 74),new Color(82, 150, 107),new Color(198, 211, 198),new Color(165, 190, 165),new Color(33, 117, 74),new Color(41, 125, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(66, 65, 57),new Color(173, 170, 148),new Color(189, 186, 173),new Color(239, 239, 239),new Color(239, 235, 231),new Color(74, 73, 66),new Color(33, 113, 74),new Color(41, 121, 82),new Color(57, 60, 49),new Color(181, 178, 156),new Color(189, 190, 173),new Color(239, 239, 239),new Color(239, 235, 231),},
   		new Color[] {new Color(107, 170, 132),new Color(206, 219, 206),new Color(198, 211, 189),new Color(132, 178, 148),new Color(173, 195, 173),new Color(206, 219, 206),new Color(206, 223, 206),new Color(198, 215, 198),new Color(173, 195, 173),new Color(198, 211, 198),new Color(206, 223, 206),new Color(107, 170, 132),new Color(41, 121, 74),new Color(33, 117, 74),new Color(41, 121, 82),new Color(41, 121, 82),new Color(41, 117, 74),new Color(33, 117, 74),new Color(74, 73, 66),new Color(173, 174, 156),new Color(198, 195, 181),new Color(239, 235, 231),new Color(239, 239, 239),new Color(66, 69, 57),new Color(41, 117, 74),new Color(33, 113, 74),new Color(74, 73, 66),new Color(165, 166, 148),new Color(198, 190, 181),new Color(239, 235, 231),new Color(239, 239, 239),},
   		new Color[] {new Color(33, 121, 74),new Color(181, 199, 173),new Color(206, 215, 198),new Color(206, 223, 206),new Color(198, 215, 198),new Color(198, 215, 198),new Color(123, 178, 140),new Color(206, 223, 206),new Color(198, 215, 198),new Color(206, 219, 206),new Color(156, 190, 165),new Color(41, 125, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(41, 117, 74),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 117, 74),new Color(66, 65, 57),new Color(173, 174, 156),new Color(189, 186, 173),new Color(239, 239, 239),new Color(239, 235, 231),new Color(74, 73, 66),new Color(33, 113, 74),new Color(41, 113, 74),new Color(66, 65, 57),new Color(181, 178, 156),new Color(189, 186, 173),new Color(239, 239, 239),new Color(239, 235, 231),},
   		new Color[] {new Color(41, 125, 82),new Color(33, 121, 74),new Color(66, 146, 99),new Color(156, 186, 156),new Color(148, 186, 156),new Color(57, 134, 90),new Color(41, 121, 82),new Color(41, 121, 82),new Color(115, 174, 132),new Color(57, 134, 90),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(33, 113, 74),new Color(41, 121, 82),new Color(33, 113, 74),new Color(41, 117, 82),new Color(33, 113, 74),new Color(66, 69, 57),new Color(173, 166, 148),new Color(181, 178, 156),new Color(214, 215, 206),new Color(239, 239, 239),new Color(66, 65, 57),new Color(41, 117, 82),new Color(33, 109, 74),new Color(66, 69, 57),new Color(165, 166, 148),new Color(181, 178, 156),new Color(214, 215, 206),new Color(239, 239, 239),},
   		new Color[] {new Color(33, 121, 74),new Color(41, 125, 82),new Color(33, 117, 74),new Color(41, 125, 82),new Color(33, 121, 74),new Color(41, 125, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 125, 82),new Color(33, 113, 74),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(33, 113, 74),new Color(41, 117, 82),new Color(33, 113, 74),new Color(41, 121, 82),new Color(66, 65, 57),new Color(173, 170, 156),new Color(173, 170, 148),new Color(181, 182, 165),new Color(214, 211, 198),new Color(66, 69, 57),new Color(33, 113, 74),new Color(41, 117, 82),new Color(66, 65, 57),new Color(173, 170, 148),new Color(173, 170, 148),new Color(181, 182, 165),new Color(214, 211, 198),},
   		new Color[] {new Color(41, 121, 82),new Color(33, 121, 74),new Color(41, 125, 82),new Color(41, 121, 82),new Color(41, 121, 74),new Color(33, 117, 74),new Color(41, 121, 82),new Color(41, 121, 82),new Color(41, 121, 74),new Color(33, 117, 74),new Color(41, 121, 82),new Color(41, 117, 82),new Color(41, 117, 74),new Color(33, 117, 74),new Color(41, 121, 82),new Color(41, 117, 74),new Color(41, 117, 74),new Color(33, 113, 74),new Color(66, 69, 57),new Color(173, 170, 148),new Color(173, 170, 148),new Color(165, 166, 148),new Color(181, 178, 156),new Color(66, 69, 57),new Color(41, 113, 74),new Color(33, 113, 74),new Color(74, 73, 66),new Color(173, 170, 148),new Color(173, 170, 148),new Color(165, 166, 148),new Color(181, 178, 156),},
   		new Color[] {new Color(33, 121, 74),new Color(41, 121, 82),new Color(41, 121, 82),new Color(41, 125, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(41, 121, 74),new Color(41, 125, 82),new Color(33, 117, 74),new Color(41, 117, 82),new Color(41, 117, 74),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 117, 74),new Color(41, 117, 74),new Color(41, 121, 82),new Color(33, 113, 74),new Color(41, 117, 74),new Color(41, 117, 74),new Color(66, 69, 57),new Color(173, 170, 148),new Color(173, 170, 148),new Color(181, 182, 165),new Color(214, 215, 206),new Color(57, 60, 49),new Color(41, 113, 74),new Color(41, 113, 74),new Color(74, 73, 66),new Color(173, 170, 148),new Color(173, 170, 148),new Color(181, 182, 165),},
   		new Color[] {new Color(41, 125, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(33, 113, 74),new Color(41, 121, 82),new Color(33, 113, 74),new Color(41, 117, 82),new Color(33, 113, 74),new Color(41, 121, 82),new Color(33, 113, 74),new Color(41, 117, 82),new Color(33, 113, 74),new Color(41, 117, 82),new Color(33, 113, 74),new Color(66, 69, 57),new Color(165, 166, 148),new Color(198, 199, 181),new Color(231, 231, 222),new Color(231, 231, 231),new Color(66, 65, 57),new Color(41, 113, 82),new Color(33, 109, 74),new Color(66, 65, 57),new Color(165, 166, 148),new Color(198, 199, 181),},
   		new Color[] {new Color(33, 117, 74),new Color(41, 125, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(33, 113, 74),new Color(41, 121, 82),new Color(33, 117, 74),new Color(41, 121, 82),new Color(33, 113, 74),new Color(41, 117, 82),new Color(33, 113, 74),new Color(41, 121, 82),new Color(33, 113, 74),new Color(41, 117, 82),new Color(33, 113, 74),new Color(41, 117, 82),new Color(33, 109, 74),new Color(41, 117, 74),new Color(33, 113, 74),new Color(74, 73, 66),new Color(173, 170, 156),new Color(198, 199, 189),new Color(214, 207, 198),new Color(214, 211, 206),new Color(66, 65, 57),new Color(41, 113, 74),new Color(33, 109, 74),new Color(74, 73, 66),new Color(173, 170, 156),},
   	};

	private static final Color[] COLORS = new Color[] {Color.red, Color.green, Color.orange, Color.blue, Color.yellow, new Color(255, 100, 175), new Color(150, 100, 200), Color.cyan, new Color(165, 140, 30), new Color(130, 150, 40)};

	private static final int X_INTERVAL = 40;
	private static final int Y_INTERVAL = 40;

	private Point mInitialPoint = new Point(413, 349);

	public PuzzleBuilderFromKado()
	{
		super();
	}

	@Override
	public Puzzle<Element> buildPuzzle()
	{
		//anchor: 63, 388
		//441,358, 472,379
		int initialPositionX = mInitialPoint.getX();
		int initialPositionY = mInitialPoint.getY();
		Robot robot = null;
		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			throw new RuntimeException(e);
		}
		mInitialPoint = getPosition(initialPositionX, initialPositionY, robot);
		if (mInitialPoint == null)
			return null;
		System.out.println("Found at " + mInitialPoint.getX() + ", " + mInitialPoint.getY());
		initialPositionX = mInitialPoint.getX() + 489 - 413;
		initialPositionY = mInitialPoint.getY() + 419 - 349;
		Puzzle<Element> puzzle = new Puzzle<Element>(5, 5);
		for (int y = 0; y < 5; y++)
		{
			for (int x = 0; x < 5; x++)
			{
				Color originalColor = robot.getPixelColor(x * X_INTERVAL + initialPositionX, y * Y_INTERVAL + initialPositionY);
				Color recognizedColor = null;
				double fitness = Double.MAX_VALUE;
				int index = -1;
				for (int col = 0; col < COLORS.length; col++)
				{
					double diff = getColourDiff(originalColor, COLORS[col].getRed(), COLORS[col].getGreen(), COLORS[col].getBlue());
					if (diff < fitness)
					{
						fitness = diff;
						index = col;
					}
				}
				recognizedColor = COLORS[index];
				puzzle.set(x, y, new Element(recognizedColor, PuzzleBuilderRandom.POINTS[index]));
				System.out.print("[" + originalColor.getRed() + ", " + originalColor.getGreen() + ", " + originalColor.getBlue() + " => " + (recognizedColor == null ? "null" : recognizedColor.toString()) + "] ");
			}
			System.out.println();
		}
		return puzzle;
	}

	private Point getPosition(int pInitialX, int pInitialY, Robot pRobot)
	{
		if (checkPosition(pRobot, pInitialX, pInitialY))
			return new Point(pInitialX, pInitialY);
		for (int x = -3000; x < 3500; x++)
			if (checkPosition(pRobot, x, pInitialY))
				return new Point(x, pInitialY);
		for (int x = -1000; x < 3000; x++)
		{
			for (int y = pInitialY - 300; y < pInitialY + 300; y++)
			{
				if (y == pInitialY)
					break;
				if (checkPosition(pRobot, x, y))
					return new Point(x, y);
			}
		}
		return null;
	}

	private boolean checkPosition(Robot pRobot, int pCurrentX, int pCurrentY)
	{
//		for (int y = 0; y < MATCH.length; y++)
//		{
//			for (int x = 0; x < MATCH[y].length; x++)
//			{
//				if (!pRobot.getPixelColor(x + pCurrentX, y + pCurrentY).equals(MATCH[y][x]))
//					return false;
//			}
//		}
//		return true;
		for (int x = 0; x < 15; x++)
		{
			if (!pRobot.getPixelColor(x + pCurrentX, pCurrentY).equals(new Color(82, 182, 198)))
				return false;
		}
		for (int y = 0; y < 15; y++)
		{
			if (!pRobot.getPixelColor(pCurrentX, y + pCurrentY).equals(new Color(82, 182, 198)))
				return false;
		}
		if (pRobot.getPixelColor(pCurrentX + 1, pCurrentY + 1).equals(new Color(82, 182, 198)))
			return false;
		return true;
	}

	private double getColourDiff(Color pCol, int pRed, int pGreen, int pBlue)
	{
		return Math.pow(Math.abs(pRed - pCol.getRed()), 2) + Math.pow(Math.abs(pGreen - pCol.getGreen()), 2) + Math.pow(Math.abs(pBlue - pCol.getBlue()), 2);
	}

	private boolean isOkColour(Color pCol)
	{
		return isOkColour(pCol, 82, 60, 33, 15);
	}

	private boolean isOkColour(Color pCol, int pRed, int pGreen, int pBlue, int pThreshold)
	{
		return getColourDiff(pCol, pRed, pGreen, pBlue) < Math.pow(pThreshold, 2);
	}
}
