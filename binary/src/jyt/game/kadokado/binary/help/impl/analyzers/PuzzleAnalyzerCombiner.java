/*
 * Created on 13 oct. 2011
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help.impl.analyzers;

import jyt.game.kadokado.binary.help.Element;
import jyt.game.kadokado.binary.help.IPuzzleAnalyzer;
import jyt.game.puzzle.solving.Puzzle;

public class PuzzleAnalyzerCombiner implements IPuzzleAnalyzer
{
	private IPuzzleAnalyzer[] mAnalyzers;// all the wrapped analyzers
	private double[] mWeights;// weights for the analyzers

	/**
	 * Created on 13 oct. 2011 by jtoumit.<br>
	 * @param pAnalyzers
	 * @param pWeights
	 */
	public PuzzleAnalyzerCombiner(IPuzzleAnalyzer[] pAnalyzers, double[] pWeights)
	{
		super();
		mAnalyzers = pAnalyzers;
		mWeights = pWeights;
	}

	@Override
	public double computeFitness(Puzzle<Element> pPuzzle)
	{
		double total = 0;
		for (int i = 0; i < mAnalyzers.length; i++)
			total += mAnalyzers[i].computeFitness(pPuzzle) * mWeights[i];
		return total;
	}

	@Override
	public String description()
	{
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < mAnalyzers.length; i++)
		{
			if (i > 0)
				b.append(", ");
			b.append(mAnalyzers[i].description()).append(": ").append(mWeights[i]);
		}
		return b.toString();
	}
}
