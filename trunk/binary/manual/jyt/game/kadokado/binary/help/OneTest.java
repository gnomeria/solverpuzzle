/**
 * 
 */
package jyt.game.kadokado.binary.help;

import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzer4Blocks;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerCombiner;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerDistances;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerExp;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerMultiplyer;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerOrphans;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerRemainingBlocks;

class OneTest implements Runnable
{
	int mResult;
	private double[] mWeights;
	String mStringRepresentation;
	private int mNbRun;
	int mMin;
	int mMax;

	/**
	 * Created on 13 oct. 2011 by jtoumit.<br>
	 * @param pWeights
	 */
	public OneTest(double[] pWeights, int pNbRun)
	{
		super();
		mWeights = pWeights;
		mNbRun = pNbRun;
	}

	@Override
	public void run()
	{
		mResult = fullEvaluate();
	}

	public int getResult()
	{
		return mResult;
	}

	private int fullEvaluate()
	{
		int min = Integer.MAX_VALUE;
		int max = 0;
		int total = 0;
		for (int i = 0; i < mNbRun; i++)
		{
			int current = evaluate();
			if (max < current)
				max = current;
			if (min > current)
				min = current;
			total += current;
		}
		mMin = min;
		mMax = max;
		return total / mNbRun;
//		return min;
	}

	public double[] getWeights()
	{
		return mWeights;
	}

	private int evaluate()
	{
		ScoreComputer scoreComputer = new ScoreComputer();
		int w = 0;
		PuzzleAnalyzerExp distances = new PuzzleAnalyzerExp(new PuzzleAnalyzerMultiplyer(new PuzzleAnalyzerDistances(mWeights[w++] > 50, mWeights[w++] > 50, (int)translate(mWeights[w++], 0, 4), (int)translate(mWeights[w++], 1, 4), mWeights[w++] > 50), mWeights[w++] / 10), makePower(mWeights[w++]));
		PuzzleAnalyzerExp fourBlocks = new PuzzleAnalyzerExp(new PuzzleAnalyzerMultiplyer(new PuzzleAnalyzer4Blocks(mWeights[w++] / 20 + 1), makeMultiplyer(mWeights[w++])), makePower(mWeights[w++]));
		PuzzleAnalyzerExp orphans = new PuzzleAnalyzerExp(new PuzzleAnalyzerMultiplyer(new PuzzleAnalyzerOrphans(mWeights[w++] / 20 + 1, mWeights[w++] > 50, mWeights[w++] > 50, mWeights[w++] > 50), makeMultiplyer(mWeights[w++])), makePower(mWeights[w++]));
		IPuzzleAnalyzer total = new PuzzleAnalyzerExp(new PuzzleAnalyzerMultiplyer(new PuzzleAnalyzerRemainingBlocks(), makeMultiplyer(mWeights[w++])), makePower(mWeights[w++]));
		IPuzzleAnalyzer[] analyzers = new IPuzzleAnalyzer[] {distances, fourBlocks, orphans, total};
		double[] weights = new double[] {mWeights[w++] - 50, mWeights[w++] - 50, mWeights[w++] - 50, mWeights[w++] - 50};
		IPuzzleAnalyzer puzzleAnalyzer = new PuzzleAnalyzerCombiner(analyzers, weights);
		mStringRepresentation = puzzleAnalyzer.description();
		return new Player(new CombinationSearcher(puzzleAnalyzer)).play(new PuzzleBuilderRandom().buildPuzzle(), new PuzzleRefill(scoreComputer), scoreComputer);
	}

	private double makeMultiplyer(double v)
	{
		return (v - 50) / 10;
	}

	private double translate(double val, double min, int max)
	{
		return val * (max - min) / 100 + min;
	}

	private double makePower(double val)
	{
		return translate(val, 0.1, 3);
	}
}