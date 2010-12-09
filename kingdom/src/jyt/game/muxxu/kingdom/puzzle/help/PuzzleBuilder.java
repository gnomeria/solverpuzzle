/*
 * Created on 23 janv. 2010
 * @author jtoumit
 */
package jyt.game.muxxu.kingdom.puzzle.help;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;

import jyt.game.puzzle.solving.Puzzle;

public class PuzzleBuilder
{
	private static final int DEFAULT_Y = 567;
	public static final int RECTANGLE_SIZE = 289;
	public static final int SQUARE_SIZE = 36;

	private int mLastX = -1;
	private int mLastY = -1;

	private static final ElementRecognitionData[] mRecognitionData = new ElementRecognitionData[]
	{
		new ElementRecognitionData(Element.SWORD,  new int[] {140, 130, 107}, new int[] { 82,  65,  41}),
		new ElementRecognitionData(Element.HUMAN,  new int[] { 90, 162, 173}, new int[] {140,  89,  33}),
		new ElementRecognitionData(Element.BUILD,  new int[] {148, 150, 148}, new int[] {189, 105,  66}),
		new ElementRecognitionData(Element.LEAF,   new int[] {247, 223, 165}, new int[] {222, 186, 115}),
		new ElementRecognitionData(Element.MONEY,  new int[] {231, 203, 148}, new int[] {214,  89,   0}),
		new ElementRecognitionData(Element.REPLAY, new int[] {132,  32,  24}, new int[] { 24, 130,   8}),
		new ElementRecognitionData(Element.WOOD,   new int[] { 57,  36,  24}, new int[] {132,  77,  33})
	};

	public Puzzle<Element> buildPuzzle()
	{
		Puzzle<Element> puzzle = null;
		Robot robot;
		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			throw new RuntimeException(e);
		}
		boolean found = false;
		if (mLastX != -1)
		// Check if it's still at the last position found
			found = checkPosition(robot, mLastX, mLastY);

		// Check 2 positions in which it's very likely to be found
		if (!found)
			found = checkPosition(robot, 2505, DEFAULT_Y);
		if (!found)
			found = checkPosition(robot, 825, DEFAULT_Y);
		if (!found)
			found = checkPosition(robot, -615, DEFAULT_Y);

		if (!found)
		// Couldn't find it: reset the x value and search for it accross the screen
		{
			mLastX = -1;
			int x;
			int y;
			for (x = -3000; x < 3500; x++)
			{
				if (checkPosition(robot, x, DEFAULT_Y))
				{
					found = true;
					break;
				}
			}
			if (!found)
				for (x = -500; x < 3500; x++)
				{
					for (y = 0; y < 1500; y++)
					{
						found = checkPosition(robot, x, y);
						if (found)
							break;
					}
					if (found)
						break;
				}
		}

		if (found)
		{
			// Read the puzzle
			System.out.println("found puzzle at " + mLastX + ", " + mLastY);
			puzzle = new Puzzle<Element>(8, 8);
			for (int j = 0; j < 8; j++)
			{
				for (int i = 0; i < 8; i++)
				{
					Object squareElement = getSquareElement(robot, mLastX + i * SQUARE_SIZE + 1, mLastY + j * SQUARE_SIZE + 1, (i + j) % 2 == 0);
					puzzle.set(i, j, (Element)squareElement);
					System.out.print("" + squareElement + "\t");
				}
				System.out.println();
			}
		}
		return puzzle;
	}

	private boolean checkPosition(Robot pRobot, int x, int y)
	{
		for (int i = 0; i < RECTANGLE_SIZE; i++)
		{
			if (!isOkColour(pRobot.getPixelColor(x + i, y)))
				return false;
			if (!isOkColour(pRobot.getPixelColor(x, y + i)))
				return false;
			if (!isOkColour(pRobot.getPixelColor(x + i, y + RECTANGLE_SIZE)))
				return false;
			if (!isOkColour(pRobot.getPixelColor(x + RECTANGLE_SIZE, y + i)))
				return false;
		}
		mLastX = x;
		mLastY = y;
		return true;
	}

	private boolean isOkColour(Color pCol)
	{
		return isOkColour(pCol, 82, 60, 33, 15);
	}

	private boolean isOkColour(Color pCol, int pRed, int pGreen, int pBlue, int pThreshold)
	{
		return getColourDiff(pCol, pRed, pGreen, pBlue) < Math.pow(pThreshold, 2);
	}

	private double getColourDiff(Color pCol, int pRed, int pGreen, int pBlue)
	{
		return Math.pow(Math.abs(pRed - pCol.getRed()), 2) + Math.pow(Math.abs(pGreen - pCol.getGreen()), 2) + Math.pow(Math.abs(pBlue - pCol.getBlue()), 2);
	}

	private Object getSquareElement(Robot pRobot, int x, int y, boolean pEven)
	{
		Element elementFound = null;
		long miniFound = Long.MAX_VALUE;
		for (int i = 0; i < mRecognitionData.length; i++)
		{
			double firstValue = getColourDiff(pRobot.getPixelColor(x + ElementRecognitionData.sFirstX, y + ElementRecognitionData.sFirstY), mRecognitionData[i].mFirstRGB[0], mRecognitionData[i].mFirstRGB[1], mRecognitionData[i].mFirstRGB[2]);
			double secondValue = getColourDiff(pRobot.getPixelColor(x + ElementRecognitionData.secondX, y + ElementRecognitionData.secondY), mRecognitionData[i].mSecondRGB[0], mRecognitionData[i].mSecondRGB[1], mRecognitionData[i].mSecondRGB[2]);
			if (firstValue + secondValue < miniFound)
			{
				miniFound = (long)(firstValue + secondValue);
				elementFound = mRecognitionData[i].mElement;
			}
		}
		return elementFound;
	}
}
