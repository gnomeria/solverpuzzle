/*
 * Created on 13 oct. 2011
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help.impl.analyzers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jyt.game.kadokado.binary.help.Element;
import jyt.game.kadokado.binary.help.IPuzzleAnalyzer;
import jyt.game.kadokado.binary.help.PointDouble;
import jyt.game.kadokado.binary.help.PuzzleAnalyzerHelper;
import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.Point;

/**
 * Gives higher scores when there are orphans at the bottom of the puzzle.
 * Orphans are elements that are far from others of the same colour.
 * Created on 13 oct. 2011.<br>
 * @author jtoumit
 */
public class PuzzleAnalyzerOrphans implements IPuzzleAnalyzer
{
	private double mDistance;
	private boolean mDivideByAggSize;
	private boolean mDivideByAggsSize;
	private boolean mDivideByTotalPoints;

	public PuzzleAnalyzerOrphans(double pDistance, boolean pDivideByAggSize, boolean pDivideByAggsSize, boolean pDivideByTotalPoints)
	{
		super();
		mDistance = pDistance;
		mDivideByAggSize = pDivideByAggSize;
		mDivideByAggsSize = pDivideByAggsSize;
		mDivideByTotalPoints = pDivideByTotalPoints;
	}

	@Override
	public double computeFitness(Puzzle<Element> pPuzzle)
	{
		Map<Element, List<Point>> elements = pPuzzle.getElementsMap();
		double weight = 0;
		for (Element element : elements.keySet())
		{
			// The goal is to find the best group
			List<Point> points = new ArrayList<Point>(elements.get(element));
			// Create aggregates
			List<List<Point>> aggregates = PuzzleAnalyzerHelper.createAggregates(points, mDistance);
			for (List<Point> aggregate : aggregates)
			{
				PointDouble center = PuzzleAnalyzerHelper.getGravityCenter(aggregate);
				double deepness = center.getY();
				if (mDivideByAggSize)
					deepness /=  aggregate.size();
				if (mDivideByAggsSize)
					deepness /= aggregates.size();
				weight += deepness;
			}
		}
		if (mDivideByTotalPoints && (pPuzzle.getTotalPoints() > 0))
			weight /= pPuzzle.getTotalPoints();
		return weight;
	}

	@Override
	public String description()
	{
		return "Orphans(" + PuzzleAnalyzerHelper.readableDouble(mDistance) + ", " + (mDivideByAggsSize ? "true" : "false") + ", " + (mDivideByAggSize ? "true" : "false") + ", " + (mDivideByTotalPoints ? "true" : "false") + ")";
	}
}
