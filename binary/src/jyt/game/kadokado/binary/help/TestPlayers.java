/*
 * Created on 28 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class TestPlayers
{
	private static class OneTest implements Runnable
	{
		private int mResult;
		private boolean mDivideByNbElements;
		private boolean mSquareDistance;
		private int mBestFor4;
		private int mMinusDistance;
		private boolean mIncludeMinMax;

		public OneTest(boolean pDivideByNbElements, boolean pSquareDistance, int pBestFor4, int pMinusDistance, boolean pIncludeMinMax)
		{
			super();
			mDivideByNbElements = pDivideByNbElements;
			mSquareDistance = pSquareDistance;
			mBestFor4 = pBestFor4;
			mMinusDistance = pMinusDistance;
			mIncludeMinMax = pIncludeMinMax;
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
			int nbRun = 100;
			for (int i = 0; i < nbRun; i++)
			{
				int current = evaluate();
				if (max < current)
					max = current;
				if (min > current)
					min = current;
				total += current;
			}
			if (mIncludeMinMax)
				return (total + max + min) / nbRun;
			else
				return total / nbRun;
		}

		private int evaluate()
		{
			ScoreComputer scoreComputer = new ScoreComputer();
			int evaluate = new Player(new CombinationSearcher(new PuzzleAnalyzerDistances(mDivideByNbElements, mSquareDistance, mBestFor4, mMinusDistance))).play(new PuzzleBuilderRandom().buildPuzzle(), new PuzzleRefill(scoreComputer), scoreComputer);
			return evaluate;
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException
	{
		int bestHit = 0;
		boolean bestDivide = false;
		boolean bestSquare = false;
		int bestFor4 = -1;
		int bestMinusDistance = 0;
		int current = 1;
		int total = 2 * 2 * 2 * 11;
		ExecutorService poolExecutor = Executors.newFixedThreadPool(2);
		List<Future<OneTest>> futures = new ArrayList<Future<OneTest>>();
		for (int minusDistance = 0; minusDistance < 2; minusDistance++)
		{
			for (int divides = 0; divides < 2; divides++)
			{
				for (int squares = 0; squares < 2; squares++)
				{
					for (int best = -1; best <= 100; )
					{
						OneTest task = new OneTest(divides == 1, squares == 1, best, minusDistance, true);
						futures.add(poolExecutor.submit(task, task));
						if (best == -1)
							best = 0;
						else
							best += 10;
					}
				}
			}
		}
		long start = System.currentTimeMillis();
		for (Future<OneTest> future : futures)
		{
			OneTest test = future.get();
			
			if (test.getResult() > bestHit)
			{
				bestHit = test.getResult();
				bestDivide = test.mDivideByNbElements;
				bestSquare = test.mSquareDistance;
				bestFor4 = test.mBestFor4;
				bestMinusDistance = test.mMinusDistance;
			}
			if (current++ % 3 == 0)
				System.out.println(((System.currentTimeMillis() - start) / 1000) + "s, " + current + " / " + total + ", currentBestHit: " + bestHit + ", currentBestDivide = " + bestDivide + ", currentBestSquare: " + bestSquare + ", currentBestFor4: " + bestFor4 + ", currentBestMinusDistance: " + bestMinusDistance);
		}
		System.out.println("Best: " + bestDivide + ", " + bestSquare + ", " + bestFor4 + ", " + bestMinusDistance + ", " + bestHit);
	}

}
