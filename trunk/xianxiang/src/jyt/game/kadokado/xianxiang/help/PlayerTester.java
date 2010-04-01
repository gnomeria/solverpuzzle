/*
 * Created on 31 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help;

import java.util.Random;

import jyt.game.kadokado.xianxiang.help.CombinationRaterBestPotential.DistanceFunction;
import jyt.game.puzzle.solving.ActionManager;
import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.CollapserLazy;

public class PlayerTester
{
	public static void main(String[] args)
	{
		int bestDivide = -1;
		int[] bestFitnesses = new int[0];
		DistanceFunction bestDistanceFunction = DistanceFunction.LINEAR;
		boolean bestMin = false;
		int bestScore = 0;
		for (int divide = 1; divide < 20; divide += 3)
		{
			for (int fitnesses2 = 1; fitnesses2 < 20; fitnesses2 += 5)
			{
				for (int fitnesses3 = fitnesses2 * 2; fitnesses3 < fitnesses2 * 100; fitnesses3 *= 5)
				{
					for (DistanceFunction distanceFunction : DistanceFunction.values())
					{
						for (int minDistance = 0; minDistance < 2; minDistance++)
						{
							int currentScore = play(divide, new int[] {0, 0, fitnesses2, fitnesses3}, distanceFunction, minDistance == 1, 10);
							if (currentScore > bestScore)
							{
								bestDivide = divide;
								bestFitnesses = new int[] {0, 0, fitnesses2, fitnesses3};
								bestDistanceFunction = distanceFunction;
								bestMin = minDistance == 1;
								bestScore = currentScore;
								printCurrentState(bestDivide, bestFitnesses, bestDistanceFunction, bestMin, bestScore);
							}
						}
					}
				}
			}
		}
		printCurrentState(bestDivide, bestFitnesses, bestDistanceFunction, bestMin, bestScore);
	}

	private static void printCurrentState(int pBestDivide, int[] pBestFitnesses, DistanceFunction pBestDistanceFunction, boolean pBestMin, int pBestScore)
	{
		System.out.println(pBestDivide + ", " + pBestFitnesses[2] + ", " + pBestFitnesses[3] + ", " + pBestDistanceFunction + ", " + pBestMin + ", " + pBestScore);
	}

	private static int play(int pDivideScoreFactor, int[] pFitnesses, DistanceFunction pDistanceFunction, boolean pMinDistance, int pNbIterations)
	{
		PuzzleBuilderRandom puzzleBuilder = new PuzzleBuilderRandom(new Random(pNbIterations));
		int totalScore = 0;
		for (int i = 0; i < pNbIterations; i++)
		{
			Puzzle<Element> puzzle = puzzleBuilder.buildPuzzle();
			ActionManager<Element> actionManager = new ActionManager<Element>(puzzle, new CollapserLazy<Element>());
			ScoreComputer scoreComputer = new ScoreComputer();
	//		ICombinationRater combinationRater = new CombinationRaterBestCurrentMove(scoreComputer);
			ICombinationRater combinationRater = new CombinationRaterBestPotential(scoreComputer, pDivideScoreFactor, pFitnesses, pDistanceFunction, pMinDistance);
			IPlayer player = new PlayerWithRater(combinationRater, actionManager, scoreComputer);
			player.play(puzzle);
			int currentScore = scoreComputer.getCurrentScore();
			totalScore += currentScore;
		}
		return totalScore / pNbIterations;
	}
}
