/**
 * 
 */
package jyt.game.kadokado.binary.help;

import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzer4Blocks;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerCombiner;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerDistances;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerExp;
import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerOrphans;

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
//		return total / mNbRun + min;
		return min;
	}

	public double[] getWeights()
	{
		return mWeights;
	}

	private int evaluate()
	{
		ScoreComputer scoreComputer = new ScoreComputer();
		IPuzzleAnalyzer[] analyzers = new IPuzzleAnalyzer[] {new PuzzleAnalyzerExp(new PuzzleAnalyzerDistances(), mWeights[1]), new PuzzleAnalyzerExp(new PuzzleAnalyzer4Blocks(mWeights[6]), mWeights[3]), new PuzzleAnalyzerExp(new PuzzleAnalyzerOrphans(mWeights[7]), mWeights[5])};
		double[] weights = new double[] {mWeights[0], mWeights[2], mWeights[4]};
		IPuzzleAnalyzer puzzleAnalyzer = new PuzzleAnalyzerCombiner(analyzers, weights);
		mStringRepresentation = puzzleAnalyzer.description();
		return new Player(new CombinationSearcher(puzzleAnalyzer)).play(new PuzzleBuilderRandom().buildPuzzle(), new PuzzleRefill(scoreComputer), scoreComputer);
	}
}