/*
 * Created on 31 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import jyt.game.kadokado.xianxiang.help.CombinationRaterBestPotential.DistanceFunction;
import jyt.game.kadokado.xianxiang.help.CombinationRaterBestPotential.ScoreFactorEffect;
import jyt.game.kadokado.xianxiang.help.CombinationRaterBestPotential.ScoreFactorFunction;
import jyt.game.puzzle.solving.ActionManager;
import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.CollapserLazy;

public class PlayerTester
{
	private static class OneTest implements Runnable
	{
		private double mMultiply = -1;
		private int[] mFitnesses = new int[0];
		private DistanceFunction mDistanceFunction = null;
		private ScoreFactorEffect mScoreFactorEffect = null;
		private ScoreFactorFunction mScoreFactorFunction = null;
		private boolean mMin = false;
		private int mScore = 0;

		public OneTest(double pMultiply, int[] pFitnesses, DistanceFunction pDistanceFunction, ScoreFactorEffect pScoreFactorEffect, ScoreFactorFunction pScoreFactorFunction, boolean pMin)
		{
			super();
			mMultiply = pMultiply;
			mFitnesses = pFitnesses;
			mDistanceFunction = pDistanceFunction;
			mScoreFactorEffect = pScoreFactorEffect;
			mScoreFactorFunction = pScoreFactorFunction;
			mMin = pMin;
		}

		@Override
		public void run()
		{
			mScore = play(mMultiply, mFitnesses, mDistanceFunction, mMin, mScoreFactorEffect, mScoreFactorFunction, 100);
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException
	{
		ExecutorService poolExecutor = Executors.newFixedThreadPool(4);
		List<Future<OneTest>> futures = new ArrayList<Future<OneTest>>();
		for (double multiply = 0.01; multiply < 20; multiply *= 2)
		{
			for (int fitnesses2 = 2; fitnesses2 < 20; fitnesses2 *= 1.5)
			{
				for (int fitnesses3 = fitnesses2 * 2; fitnesses3 < fitnesses2 * 100; fitnesses3 *= 2)
				{
					for (DistanceFunction distanceFunction : DistanceFunction.values())
					{
						for (ScoreFactorEffect scoreFactorEffect : ScoreFactorEffect.values())
						{
							for (ScoreFactorFunction scoreFactorFunction : ScoreFactorFunction.values())
							{
								for (int minDistance = 0; minDistance < 2; minDistance++)
								{
									OneTest task = new OneTest(multiply, new int[] {0, 1, fitnesses2, fitnesses3}, distanceFunction, scoreFactorEffect, scoreFactorFunction, minDistance == 1);
									futures.add(poolExecutor.submit(task, task));
								}
							}
						}
					}
				}
			}
		}
		long start = System.currentTimeMillis();
		OneTest bestTest = null;
		for (Future<OneTest> future : futures)
		{
			OneTest test = future.get();
			if ((bestTest == null) || (bestTest.mScore < test.mScore))
			{
				bestTest = test;
				printCurrentState(start, bestTest);
			}
		}
		poolExecutor.shutdown();
		printCurrentState(start, bestTest);
	}

	private static void printCurrentState(long pStart, OneTest pTest)
	{
		System.out.println(((System.currentTimeMillis() - pStart) / 1000) + "s, " + pTest.mMultiply + ", " + pTest.mFitnesses[2] + ", " + pTest.mFitnesses[3] + ", " + pTest.mDistanceFunction + ", " + pTest.mMin + ", " + pTest.mScoreFactorEffect + ", " + pTest.mScoreFactorFunction + ", " + pTest.mScore);
	}

	private static int play(double pMultiplyScoreFactor, int[] pFitnesses, DistanceFunction pDistanceFunction, boolean pMinDistance, ScoreFactorEffect pScoreFactorEffect, ScoreFactorFunction pScoreFactorFunction, int pNbIterations)
	{
		PuzzleBuilderRandom puzzleBuilder = new PuzzleBuilderRandom(new Random(pNbIterations));
		int totalScore = 0;
		for (int i = 0; i < pNbIterations; i++)
		{
			Puzzle<Element> puzzle = puzzleBuilder.buildPuzzle();
			ActionManager<Element> actionManager = new ActionManager<Element>(puzzle, new CollapserLazy<Element>());
			ScoreComputer scoreComputer = new ScoreComputer();
			ICombinationRater combinationRater = new CombinationRaterBestPotential(scoreComputer, pMultiplyScoreFactor, pFitnesses, pDistanceFunction, pMinDistance, pScoreFactorFunction, pScoreFactorEffect);
			IPlayer player = new PlayerWithRater(combinationRater, actionManager, scoreComputer);
			player.play(puzzle);
			int currentScore = scoreComputer.getCurrentScore();
			totalScore += currentScore;
		}
		return totalScore / pNbIterations;
	}
}
