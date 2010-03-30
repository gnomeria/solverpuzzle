/*
 * Created on 28 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;


public class TestPlayers
{
	public static void main(String[] args)
	{
		int bestHit = 0;
		boolean bestDivide = false;
		boolean bestSquare = false;
		int bestFor4 = -1;
		int bestMinusDistance = 0;
		int current = 1;
		int total = 2 * 2 * 2 * 11;
		for (int minusDistance = 0; minusDistance < 2; minusDistance++)
		{
			for (int divides = 0; divides < 2; divides++)
			{
				for (int squares = 0; squares < 2; squares++)
				{
					for (int best = -1; best <= 100; )
					{
						if (current++ % 3 == 0)
							System.out.println(current + " / " + total + ", currentBestHit: " + bestHit + ", currentBestDivide = " + bestDivide + ", currentBestSquare: " + bestSquare + ", currentBestFor4: " + bestFor4 + ", currentBestMinusDistance: " + bestMinusDistance);
						int result = fullEvaluate(divides == 1, squares == 1, best, minusDistance);
						if (result > bestHit)
						{
							bestHit = result;
							bestDivide = divides == 1;
							bestSquare = squares == 1;
							bestFor4 = best;
							bestMinusDistance = minusDistance;
						}
						if (best == -1)
							best = 0;
						else
							best += 10;
					}
				}
			}
		}
		System.out.println("Best: " + bestDivide + ", " + bestSquare + ", " + bestFor4 + ", " + bestMinusDistance + ", " + bestHit);
	}

	private static int fullEvaluate(boolean pDivideByNbElements, boolean pSquareDistance, int pBestFor4, int pMinusDistance)
	{
		int min = Integer.MAX_VALUE;
		int max = 0;
		int total = 0;
		int nbRun = 100;
		for (int i = 0; i < nbRun; i++)
		{
			int current = evaluate(pDivideByNbElements, pSquareDistance, pBestFor4, pMinusDistance);
			if (max < current)
				max = current;
			if (min > current)
				min = current;
			total += current;
		}
//		return (total + max + min) / nbRun;
		return total / nbRun;
	}

	private static int evaluate(boolean pDivideByNbElements, boolean pSquareDistance, int pBestFor4, int pMinusDistance)
	{
		ScoreComputer scoreComputer = new ScoreComputer();
		int evaluate = new Player(new CombinationSearcher(new PuzzleAnalyzerDistances(pDivideByNbElements, pSquareDistance, pBestFor4, pMinusDistance))).play(new PuzzleBuilderRandom().buildPuzzle(), new PuzzleRefill(scoreComputer), scoreComputer);
		return evaluate;
	}
}
