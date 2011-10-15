package jyt.game.kadokado.binary.help.impl.analyzers;

import jyt.game.kadokado.binary.help.Element;
import jyt.game.kadokado.binary.help.IPuzzleAnalyzer;
import jyt.game.kadokado.binary.help.PuzzleAnalyzerHelper;
import jyt.game.puzzle.solving.Puzzle;

public class PuzzleAnalyzerMultiplyer implements IPuzzleAnalyzer
{
	private IPuzzleAnalyzer mWrappedAnalyzer;
	private double mFactor;

	public PuzzleAnalyzerMultiplyer(IPuzzleAnalyzer pWrappedAnalyzer, double pFactor)
	{
		super();
		mWrappedAnalyzer = pWrappedAnalyzer;
		mFactor = pFactor;
	}

	@Override
	public double computeFitness(Puzzle<Element> pPuzzle)
	{
		return mWrappedAnalyzer.computeFitness(pPuzzle) * mFactor;
	}

	@Override
	public String description()
	{
		return "(" + mWrappedAnalyzer.description() + "x" + PuzzleAnalyzerHelper.readableDouble(mFactor) + ")";
	}

}
