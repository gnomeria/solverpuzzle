/*
 * Created on 26 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.Point;

public class PuzzleAnalyzerHelper
{
	public static Zone[] getZones(Puzzle<Element> pPuzzle)
	{
		Puzzle<Element> puzzle = new Puzzle<Element>(pPuzzle);
		List<Zone> zones = new ArrayList<Zone>();
		for (int x = 0; x < puzzle.getWidth(); x++)
		{
			for (int y = 0; y < puzzle.getHeight(); y++)
			{
				if (puzzle.get(x, y) != null)
				{
					List<Point> points = new ArrayList<Point>();
					aggregate(puzzle, points, x, y, puzzle.get(x, y).getColor());
					if (points.size() > 3)
						zones.add(new Zone(points.toArray(new Point[points.size()])));
				}
			}
		}
		return zones.toArray(new Zone[zones.size()]);
	}

	private static void aggregate(Puzzle<Element> pPuzzle, List<Point> pPoints, int pX, int pY, Color pColor)
	{
		if ((pX >= pPuzzle.getWidth()) || (pX < 0) || (pY >= pPuzzle.getHeight()) || (pY < 0) || (pPuzzle.get(pX, pY) == null) || (!pPuzzle.get(pX, pY).getColor().equals(pColor)))
			return;
		pPoints.add(new Point(pX, pY));
		pPuzzle.set(pX, pY, null);
		aggregate(pPuzzle, pPoints, pX + 1, pY, pColor);
		aggregate(pPuzzle, pPoints, pX, pY + 1, pColor);
		aggregate(pPuzzle, pPoints, pX - 1, pY, pColor);
		aggregate(pPuzzle, pPoints, pX, pY - 1, pColor);
	}

	public static double distance(Point p1, Point p2)
	{
		return Math.sqrt(squareDistance(p1, p2));
	}

	public static double squareDistance(Point p1, Point p2)
	{
		return sqr(p1.getX() - p2.getX()) + sqr(p1.getY() - p2.getY());
	}

	public static double distance(PointDouble p1, PointDouble p2)
	{
		return Math.sqrt(squareDistance(p1, p2));
	}

	public static double squareDistance(PointDouble p1, PointDouble p2)
	{
		return sqr(p1.getX() - p2.getX()) + sqr(p1.getY() - p2.getY());
	}

	private static double sqr(double v)
	{
		return v * v;
	}

	public static PointDouble getGravityCenter(List<Point> pPoints)
	{
		double x = 0;
		double y = 0;
		for (Point point : pPoints)
		{
			x += point.getX();
			y += point.getY();
		}
		return new PointDouble(x / pPoints.size(), y / pPoints.size());
	}

	public static List<List<Point>> createAggregates(List<Point> pPoints, int pMaxDist)
	{
		List<List<Point>> aggregates = new ArrayList<List<Point>>();
		// Gravity centers for each aggregate
		List<PointDouble> centers = new ArrayList<PointDouble>();
		for (Point point : pPoints)
		{
			int pos = -1;
			PointDouble pointDouble = new PointDouble(point);
			for (int i = 0; i < centers.size() ; i++)
			{
				if (distance(pointDouble, centers.get(i)) < pMaxDist)
				{
					pos = i;
					break;
				}
			}
			if (pos == -1)
			// create new
			{
				centers.add(pointDouble);
				List<Point> arrayList = new ArrayList<Point>();
				arrayList.add(point);
				aggregates.add(arrayList);
			}
			else
			{
				PointDouble currentCenter = centers.get(pos);
				centers.set(pos, new PointDouble((currentCenter.getX() * aggregates.size() + point.getX()) / (aggregates.size() + 1), (currentCenter.getY() * aggregates.size() + point.getY()) / (aggregates.size() + 1)));
				aggregates.get(pos).add(point);
			}
		}
		return aggregates;
	}
}
