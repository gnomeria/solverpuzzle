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
		double bestMultiply = -1;
		int[] bestFitnesses = new int[0];
		DistanceFunction bestDistanceFunction = DistanceFunction.LINEAR;
		boolean bestMin = false;
		int bestScore = 0;
		for (double multiply = 0.1; multiply < 20; multiply *= 2)
		{
			for (int fitnesses2 = 2; fitnesses2 < 20; fitnesses2 += 5)
			{
				for (int fitnesses3 = fitnesses2 * 2; fitnesses3 < fitnesses2 * 100; fitnesses3 *= 5)
				{
					for (DistanceFunction distanceFunction : DistanceFunction.values())
					{
						for (int minDistance = 0; minDistance < 2; minDistance++)
						{
							int currentScore = play(multiply, new int[] {0, 1, fitnesses2, fitnesses3}, distanceFunction, minDistance == 1, 10);
							if (currentScore > bestScore)
							{
								bestMultiply = multiply;
								bestFitnesses = new int[] {0, 0, fitnesses2, fitnesses3};
								bestDistanceFunction = distanceFunction;
								bestMin = minDistance == 1;
								bestScore = currentScore;
								printCurrentState(bestMultiply, bestFitnesses, bestDistanceFunction, bestMin, bestScore);
							}
						}
					}
				}
			}
		}
		printCurrentState(bestMultiply, bestFitnesses, bestDistanceFunction, bestMin, bestScore);
	}

	private static void printCurrentState(double pBestDivide, int[] pBestFitnesses, DistanceFunction pBestDistanceFunction, boolean pBestMin, int pBestScore)
	{
		System.out.println(pBestDivide + ", " + pBestFitnesses[2] + ", " + pBestFitnesses[3] + ", " + pBestDistanceFunction + ", " + pBestMin + ", " + pBestScore);
	}

	private static int play(double pMultiplyScoreFactor, int[] pFitnesses, DistanceFunction pDistanceFunction, boolean pMinDistance, int pNbIterations)
	{
		PuzzleBuilderRandom puzzleBuilder = new PuzzleBuilderRandom(new Random(pNbIterations));
		int totalScore = 0;
		for (int i = 0; i < pNbIterations; i++)
		{
			Puzzle<Element> puzzle = puzzleBuilder.buildPuzzle();
			ActionManager<Element> actionManager = new ActionManager<Element>(puzzle, new CollapserLazy<Element>());
			ScoreComputer scoreComputer = new ScoreComputer();
	//		ICombinationRater combinationRater = new CombinationRaterBestCurrentMove(scoreComputer);
			ICombinationRater combinationRater = new CombinationRaterBestPotential(scoreComputer, pMultiplyScoreFactor, pFitnesses, pDistanceFunction, pMinDistance);
			IPlayer player = new PlayerWithRater(combinationRater, actionManager, scoreComputer);
			player.play(puzzle);
			int currentScore = scoreComputer.getCurrentScore();
			totalScore += currentScore;
		}
		return totalScore / pNbIterations;
	}
}
