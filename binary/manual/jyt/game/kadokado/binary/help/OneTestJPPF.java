/**
 * 
 */
package jyt.game.kadokado.binary.help;

import java.io.Serializable;
import java.util.concurrent.Callable;

import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzer4Blocks;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerCombiner;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerDistances;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerExp;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerMultiplyer;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerOrphans;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerRemainingBlocks;

class OneTestJPPF implements Callable<OneTestJPPF>, Serializable
{
	private int mResult;
	private double[] mWeights;
	private String mStringRepresentation;
	private int mNbRun;
	private int mMin;
	private int mMax;

	/**
	 * Created on 13 oct. 2011 by jtoumit.<br>
	 * @param pWeights
	 */
	public OneTestJPPF(double[] pWeights, int pNbRun)
	{
		super();
		mWeights = pWeights;
		mNbRun = pNbRun;
	}

	@Override
	public OneTestJPPF call() throws Exception
	{
		mResult = fullEvaluate();
		return this;
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

		int w = 0;
		IPuzzleAnalyzer distances = new PuzzleAnalyzerExp(new PuzzleAnalyzerMultiplyer(new PuzzleAnalyzerDistances(mWeights[w++] > 50, mWeights[w++] > 50, (int)translate(mWeights[w++], 0, 4), (int)translate(mWeights[w++], 1, 4), mWeights[w++] > 50), mWeights[w++] / 10), makePower(mWeights[w++]));
		IPuzzleAnalyzer fourBlocks = new PuzzleAnalyzerExp(new PuzzleAnalyzerMultiplyer(new PuzzleAnalyzer4Blocks(mWeights[w++] / 20 + 1), makeMultiplyer(mWeights[w++])), makePower(mWeights[w++]));
		IPuzzleAnalyzer orphans = new PuzzleAnalyzerExp(new PuzzleAnalyzerMultiplyer(new PuzzleAnalyzerOrphans(mWeights[w++] / 20 + 1, mWeights[w++] > 50, mWeights[w++] > 50, mWeights[w++] > 50), makeMultiplyer(mWeights[w++])), makePower(mWeights[w++]));
		IPuzzleAnalyzer total1 = new PuzzleAnalyzerExp(new PuzzleAnalyzerMultiplyer(new PuzzleAnalyzerRemainingBlocks(), makeMultiplyer(mWeights[w++])), makePower(mWeights[w++]));
		IPuzzleAnalyzer[] analyzers = new IPuzzleAnalyzer[] {distances, fourBlocks, orphans, total1};
		double[] weights = new double[] {mWeights[w++] - 50, mWeights[w++] - 50, mWeights[w++] - 50, mWeights[w++] - 50};
		IPuzzleAnalyzer puzzleAnalyzer = new PuzzleAnalyzerCombiner(analyzers, weights);
		mStringRepresentation = puzzleAnalyzer.description();
		System.out.println("Evaluating " + mStringRepresentation);

		for (int i = 0; i < mNbRun; i++)
		{
			ScoreComputer scoreComputer = new ScoreComputer();
			int current = new Player(new CombinationSearcher(puzzleAnalyzer)).play(new PuzzleBuilderRandom().buildPuzzle(), new PuzzleRefill(scoreComputer), scoreComputer);
			if (max < current)
				max = current;
			if (min > current)
				min = current;
			total += current;
		}

		total /= mNbRun;
		System.out.println("Evaluation done for " + mStringRepresentation + " : " + total / mNbRun);
		mMin = min;
		mMax = max;
		return total;
//		return min;
	}

	public double[] getWeights()
	{
		return mWeights;
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

	public String getStringRepresentation()
	{
		return mStringRepresentation;
	}

	public int getMin()
	{
		return mMin;
	}

	public int getMax()
	{
		return mMax;
	}
}