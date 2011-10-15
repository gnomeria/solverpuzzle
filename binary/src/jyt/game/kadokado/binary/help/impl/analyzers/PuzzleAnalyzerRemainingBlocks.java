package jyt.game.kadokado.binary.help.impl.analyzers;

import jyt.game.kadokado.binary.help.Element;
import jyt.game.kadokado.binary.help.IPuzzleAnalyzer;
import jyt.game.puzzle.solving.Puzzle;

public class PuzzleAnalyzerRemainingBlocks implements IPuzzleAnalyzer
{
	@Override
	public double computeFitness(Puzzle<Element> pPuzzle)
	{
		return pPuzzle.getTotalPoints();
	}

	@Override
	public String description()
	{
		return "TotalPoints";
	}

}
