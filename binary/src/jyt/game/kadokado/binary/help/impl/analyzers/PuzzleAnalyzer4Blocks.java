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
import jyt.game.kadokado.binary.help.PuzzleAnalyzerHelper;
import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.Point;

public class PuzzleAnalyzer4Blocks implements IPuzzleAnalyzer
{
	private double mFitness;

	public PuzzleAnalyzer4Blocks(double pFitness)
	{
		super();
		mFitness = pFitness / 30 + 1;
	}

	@Override
	public double computeFitness(Puzzle<Element> pPuzzle)
	{
		Map<Element, List<Point>> elements = pPuzzle.getElementsMap();
		int nbAggregatesFound = 10;
		for (Element element : elements.keySet())
		{
			// The goal is to find the best group
			List<Point> points = new ArrayList<Point>(elements.get(element));
			List<List<Point>> aggregates = PuzzleAnalyzerHelper.createAggregates(points, mFitness);
			for (List<Point> aggregate : aggregates)
				if (aggregate.size() >= 4)
					nbAggregatesFound *= 2;
		}
		if (pPuzzle.getTotalPoints() > 0)
			nbAggregatesFound *= pPuzzle.getTotalPoints();
		return 100 / nbAggregatesFound;
	}

	@Override
	public String description()
	{
		return "4Blocks(" + PuzzleAnalyzerHelper.readableDouble(mFitness) + ")";
	}
}
