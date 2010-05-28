/*
 * Created on 28 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;

import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.Point;

public class PuzzleBuilderFromKado implements IPuzzleBuilder
{
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
