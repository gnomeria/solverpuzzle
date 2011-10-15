package jyt.game.kadokado.binary.help.impl.analyzers;

import jyt.game.kadokado.binary.help.Element;
import jyt.game.kadokado.binary.help.IPuzzleAnalyzer;
import jyt.game.kadokado.binary.help.PuzzleAnalyzerHelper;
import jyt.game.puzzle.solving.Puzzle;

public class PuzzleAnalyzerExp implements IPuzzleAnalyzer
{
	private IPuzzleAnalyzer mWrappedAnalyzer;
	private double mIntensity;

	public PuzzleAnalyzerExp(IPuzzleAnalyzer pWrappedAnalyzer, double pIntensity)
	{
		super();
		mWrappedAnalyzer = pWrappedAnalyzer;
		mIntensity = pIntensity;
	}

	@Override
	public double computeFitness(Puzzle<Element> pPuzzle)
	{
		return Math.pow(mWrappedAnalyzer.computeFitness(pPuzzle), mIntensity);
	}

	@Override
	public String description()
	{
		return mWrappedAnalyzer.description() + "^" + PuzzleAnalyzerHelper.readableDouble(mIntensity);
	}

}
