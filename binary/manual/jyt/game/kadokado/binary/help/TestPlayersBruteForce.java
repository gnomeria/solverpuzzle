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

import jyt.game.kadokado.binary.help.impl.analyzers.PuzzleAnalyzerDistances;


public class TestPlayersBruteForce
{
	private static class OneTest implements Runnable
	{
		private int mResult;
		private boolean mDivideByNbElements;
		private boolean mSquareDistance;
		private int mBestFor4;
		private int mMinusDistance;
		private boolean mIncludeMinMax;
		private boolean mUseMaxPenalty;

		/**
		 * Created on 30 mars 2010 by jtoumit.<br>
		 * @param pDivideByNbElements
		 * @param pSquareDistance
		 * @param pBestFor4
		 * @param pMinusDistance
		 * @param pIncludeMinMax
		 * @param pUseMaxPenalty
		 * @param pUsePenalty4
		 */
		public OneTest(boolean pDivideByNbElements, boolean pSquareDistance, int pBestFor4, int pMinusDistance, boolean pIncludeMinMax, boolean pUseMaxPenalty)
		{
			super();
			mDivideByNbElements = pDivideByNbElements;
			mSquareDistance = pSquareDistance;
			mBestFor4 = pBestFor4;
			mMinusDistance = pMinusDistance;
			mIncludeMinMax = pIncludeMinMax;
			mUseMaxPenalty = pUseMaxPenalty;
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
			int evaluate = new Player(new CombinationSearcher(new PuzzleAnalyzerDistances(mDivideByNbElements, mSquareDistance, mBestFor4, mMinusDistance, mUseMaxPenalty))).play(new PuzzleBuilderRandom().buildPuzzle(), new PuzzleRefill(scoreComputer), scoreComputer);
			return evaluate;
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException
	{
		OneTest bestTest = null;
		int current = 1;
		int total = 2 * 2 * 2 * 2 * 12;
		ExecutorService poolExecutor = Executors.newFixedThreadPool(4);
		List<Future<OneTest>> futures = new ArrayList<Future<OneTest>>();
		for (int maxPenalty = 0; maxPenalty < 2; maxPenalty++)
		{
			for (int minusDistance = 0; minusDistance < 2; minusDistance++)
			{
				for (int divides = 0; divides < 2; divides++)
				{
					for (int squares = 0; squares < 2; squares++)
					{
						for (int best = -1; best <= 100; )
						{
							OneTest task = new OneTest(divides == 1, squares == 1, best, minusDistance, false, maxPenalty == 1);
							futures.add(poolExecutor.submit(task, task));
							if (best == -1)
								best = 0;
							else
								best += 10;
						}
					}
				}
			}
		}
		long start = System.currentTimeMillis();
		for (Future<OneTest> future : futures)
		{
			OneTest test = future.get();
			
			if ((bestTest == null) || (test.getResult() > bestTest.getResult()))
			{
				bestTest = test;
			}
			if (current++ % 3 == 0)
				printCurrentResult(bestTest, current, total, start);
		}
		poolExecutor.shutdown();
		System.out.println("Done");
	}

	private static void printCurrentResult(OneTest pBestTest, int pCurrent, int pTotal, long pStart)
	{
		System.out.println(((System.currentTimeMillis() - pStart) / 1000) + "s, " + pCurrent + " / " + pTotal + ", currentBestHit: " + pBestTest.mResult + ", currentBestDivide = " + pBestTest.mDivideByNbElements + ", currentBestSquare: " + pBestTest.mSquareDistance + ", currentBestFor4: " + pBestTest.mBestFor4 + ", currentBestMinusDistance: " + pBestTest.mMinusDistance + ", currentUseMaxPenalty: " + pBestTest.mUseMaxPenalty);
	}

}
