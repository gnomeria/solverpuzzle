/*
 * Created on 27 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jyt.game.puzzle.solving.Puzzle;

public class PuzzleAnalyzerDistances implements IPuzzleAnalyzer
{
	private boolean mDivideByNbElements;
	private boolean mSquareDistance;
	private int mBestFor4;
	private int mMinusDistance;
	private boolean mUseMaxDistancePenalty;

	public PuzzleAnalyzerDistances()
	{
		this(false, false, 80, 0, false);
	}

	/**
	 * Created on 30 mars 2010 by jtoumit.<br>
	 * @param pDivideByNbElements
	 * @param pSquareDistance
	 * @param pBestFor4
	 * @param pMinusDistance
	 * @param pUseMaxDistancePenalty
	 */
	public PuzzleAnalyzerDistances(boolean pDivideByNbElements, boolean pSquareDistance, int pBestFor4, int pMinusDistance, boolean pUseMaxDistancePenalty)
	{
		super();
		mDivideByNbElements = pDivideByNbElements;
		mSquareDistance = pSquareDistance;
		mBestFor4 = pBestFor4;
		mMinusDistance = pMinusDistance;
		mUseMaxDistancePenalty = pUseMaxDistancePenalty;
	}

	@Override
	public double computeFitness(Puzzle<Element> pPuzzle)
	{
		Map<Element, List<Point>> map = new HashMap<Element, List<Point>>();
		// First gather information about groups of elements of the same colour
		for (int x = 0; x < pPuzzle.getWidth(); x++)
		{
			for (int y = 0; y < pPuzzle.getHeight(); y++)
			{
				Element element = pPuzzle.get(x, y);
				if (element != null)
				{
					List<Point> colors = map.get(element.getColor());
					if (colors == null)
						map.put(element, colors = new ArrayList<Point>());
					colors.add(new Point(x, y));
				}
			}
		}
		double total = 0;
		double bestFor4 = mBestFor4;
		for (Element element : map.keySet())
		{
			double minDistance = -1;
			double current = 0;
			for (Point referencePoint : map.get(element))
			{
				for (Point comparedTo : map.get(element))
				{
					if (!referencePoint.equals(comparedTo))
					{
						double distance = distance(referencePoint, comparedTo);
						if ((distance < minDistance) || (minDistance == -1))
							minDistance = distance;
					}
				}
				if (minDistance != -1)
				{
					total += minDistance;
					current += minDistance;
				}
			}
			if (map.get(element).size() < 4)
			// For all groups with less than 4 elements in it, add some penalties
			{
				double maxDistance = 0;
				minDistance = Integer.MAX_VALUE;
				// get the biggest distance to the top of the grid
				for (Point point : map.get(element))
				{
					double distance = distance(point, new Point(0, 0));
					if (distance > maxDistance)
						maxDistance = distance;
					if (distance < minDistance)
						minDistance = distance;
					distance(point, new Point(pPuzzle.getWidth() - 1, 0));
					if (distance > maxDistance)
						maxDistance = distance;
					if (distance < minDistance)
						minDistance = distance;
				}
				double penalty;
				if (mUseMaxDistancePenalty)
					penalty = maxDistance;
				else
					penalty = minDistance;
				if (mDivideByNbElements)
					total += penalty / map.get(element).size();
				else
					total += penalty;
			}
			else if (current < bestFor4)
				bestFor4 = current / map.get(element).size();
		}
		if (mBestFor4 >= 0)
			total += bestFor4;
		return total;
	}

	private int distance(Point p1, Point p2)
	{
		return sqr(Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY()) - mMinusDistance);
	}

	private int sqr(int i)
	{
		if (mSquareDistance)
			return i * i;
		else
			return i;
	}
}
