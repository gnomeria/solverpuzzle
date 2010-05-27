/*
 * Created on 15 avr. 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;

import jyt.game.kadokado.xianxiang.help.Element.Colour;
import jyt.game.kadokado.xianxiang.help.Element.Geometry;
import jyt.game.kadokado.xianxiang.help.Element.InsidePattern;
import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.Point;

public class PuzzleBuilderKadoKado implements IPuzzleBuilder
{
	private static final int X_INTERVAL = 43;
	private static final int Y_INTERVAL = 34;

	private static final Point sInitialPoint = new Point(22, 25);

	private static final Point sColorPoint = new Point(389 - 358, 515 - 510);
	private static final Color[] sColorMatch = new Color[] {Color.RED, Color.CYAN, Color.YELLOW, Color.GREEN};
	private static final Colour[] sColorColour = new Colour[] {Colour.RED, Colour.BLUE, Colour.YELLOW, Colour.GREEN};

	private static final Point sCrossPoint = new Point(21, 9);
	private static final Point sRectanglePoint = new Point(28, 6);
	private static final Point sTrianglePoint = new Point(18, 7);
	private static final Point sDotsPoint = new Point(20, 16);
	private static final Point sWavesPoint = new Point(25, 7);

	private static final Point[] sInsidePatternPoints = new Point[] {sCrossPoint, sRectanglePoint, sTrianglePoint, sDotsPoint, sWavesPoint};
	private static final InsidePattern[] sInsidePattern = new InsidePattern[] {InsidePattern.CROSS, InsidePattern.RECTANGLE, InsidePattern.TRIANGLE, InsidePattern.DOTS, InsidePattern.WAVES};

	private static final Point sGeomTrianglePoint = new Point(35, 10);
	private static final Color sGeomTriangleColor = new Color(239, 207, 165);
	private static final Point sGeomRoundPoint = new Point(36, 4);
	private static final Color sGeomRoundColor = new Color(231, 215, 165);
	private static final Point sGeomRectanglePoint = new Point(38, 23);
	private static final Color sGeomRectangleColor = new Color(247, 235, 198);

	private static final Point[] sGeomPoints = new Point[] {sGeomRectanglePoint, sGeomRoundPoint, sGeomTrianglePoint};
	private static final Color[] sGeomColors = new Color[] {sGeomRectangleColor, sGeomRoundColor, sGeomTriangleColor};
	private static final Geometry[] sGeomGeom = new Geometry[] {Geometry.RECTANGLE, Geometry.ROUND, Geometry.TRIANGLE};

	public PuzzleBuilderKadoKado()
	{
		super();
	}

	@Override
	public Puzzle<Element> buildPuzzle()
	{
		int initialPositionX = sInitialPoint.getX();
		int initialPositionY = 349;
		Robot robot = null;
		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			throw new RuntimeException(e);
		}
		Point initialPoint = getPosition(initialPositionX, initialPositionY, robot);
		if (initialPoint == null)
			return null;
		System.out.println("Found at " + initialPoint.getX() + ", " + initialPoint.getY());
		initialPositionX = initialPoint.getX() + sInitialPoint.getX();
		initialPositionY = initialPoint.getY() + sInitialPoint.getY();
		Puzzle<Element> puzzle = new Puzzle<Element>(6, 7);
		for (int y = 0; y < 7; y++)
		{
			for (int x = 0; x < 6; x++)
			{
				int curx = x * X_INTERVAL + initialPositionX;
				int cury = y * Y_INTERVAL + initialPositionY;
				Color originalColor = robot.getPixelColor(curx + sColorPoint.getX(), cury + sColorPoint.getY());
				Colour recognizedColor = null;
				double fitness = Double.MAX_VALUE;
				for (int col = 0; col < sColorMatch.length; col++)
				{
					double diff = getColourDiff(originalColor, sColorMatch[col].getRed(), sColorMatch[col].getGreen(), sColorMatch[col].getBlue());
					if (diff < fitness)
					{
						fitness = diff;
						recognizedColor = sColorColour[col];
					}
				}
				fitness = Double.MAX_VALUE;
				Geometry recognizedGeometry = null;
				for (int form = 0 ; form < sGeomPoints.length ; form++)
				{
					double diff = getColourDiff(robot.getPixelColor(curx + sGeomPoints[form].getX(), cury + sGeomPoints[form].getY()), sGeomColors[form].getRed(), sGeomColors[form].getGreen(), sGeomColors[form].getBlue());
					if (diff < fitness)
					{
						fitness = diff;
						recognizedGeometry = sGeomGeom[form];
					}
				}
				fitness = Double.MAX_VALUE;
				InsidePattern recognizedInsidePattern = null;
				for (int pat = 0 ; pat < sInsidePatternPoints.length ; pat++)
				{
					double diff = getColourDiff(robot.getPixelColor(curx + sInsidePatternPoints[pat].getX(), cury + sInsidePatternPoints[pat].getY()), 255, 255, 255);
					if (diff < fitness)
					{
						fitness = diff;
						recognizedInsidePattern = sInsidePattern[pat];
					}
				}
				
				puzzle.set(x, y, new Element(recognizedColor, recognizedGeometry, recognizedInsidePattern));
//				System.out.print("[" + originalColor.getRed() + ", " + originalColor.getGreen() + ", " + originalColor.getBlue() + " => " + (recognizedColor == null ? "null" : recognizedColor.toString()) + "] ");
			}
//			System.out.println();
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
