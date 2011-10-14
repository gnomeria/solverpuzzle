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
			List<List<Point>> aggregates = PuzzleAnalyzerHelper.createAggregates(points, 3);
			for (List<Point> aggregate : aggregates)
			{
				PointDouble center = PuzzleAnalyzerHelper.getGravityCenter(aggregate);
				weight += center.getY() / aggregate.size() / aggregates.size();
			}
		}
		if (pPuzzle.getTotalPoints() > 0)
			weight /= pPuzzle.getTotalPoints();
		return weight;
	}

	@Override
	public String description()
	{
		return "Orphans";
	}
}
