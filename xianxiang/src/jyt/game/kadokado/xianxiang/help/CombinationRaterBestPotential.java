/*
 * Created on 1 avr. 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import jyt.game.puzzle.solving.ActionManager;
import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.CollapserLazy;

public class CombinationRaterBestPotential implements ICombinationRater
{
	public enum DistanceFunction {LINEAR, SQUARE, SQRROOT, LOG, EXP};
	private double mMultiplyScoreFactor;
	private ScoreComputer mScoreComputer;
	private int[] mFitnesses;
	private DistanceFunction mDistanceFunction;
	private boolean mMinDistance;

	public CombinationRaterBestPotential(ScoreComputer pScoreComputer)
	{
		this(pScoreComputer, 1, new int[] {0, 1, 10, 1000}, DistanceFunction.SQRROOT, false);
	}

	/**
	 * Created on 1 avr. 2010 by jtoumit.<br>
	 * @param pScoreComputer
	 * @param pFitnesses 
	 * @param pDistanceFunction 
	 * @param pMinDistance 
	 */
	public CombinationRaterBestPotential(ScoreComputer pScoreComputer, double pMultiplyScoreFactor, int[] pFitnesses, DistanceFunction pDistanceFunction, boolean pMinDistance)
	{
		super();
		mScoreComputer = pScoreComputer;
		mMultiplyScoreFactor = pMultiplyScoreFactor;
		mFitnesses = pFitnesses;
		mDistanceFunction = pDistanceFunction;
		mMinDistance = pMinDistance;
	}

	@Override
	public RatedCombination[] getCombinations(Puzzle<Element> pPuzzle)
	{
		SortedSet<RatedCombination> ratedCombinations = new TreeSet<RatedCombination>();
		ActionManager<Element> actionManager = new ActionManager<Element>(pPuzzle, new CollapserLazy<Element>());
		for (Iterator<ActionMatch> actionIterator = new PossibleActionsComputer(pPuzzle, mScoreComputer); actionIterator.hasNext();)
		{
			ActionMatch action = actionIterator.next();
			int beforePotential = computePotential(pPuzzle);
			int beforeScore = mScoreComputer.getCurrentScore();
			actionManager.executeActionAndCollapse(action);
			int afterPotential = computePotential(pPuzzle);
			int afterScore = mScoreComputer.getCurrentScore();
			actionManager.rollBack();
			double rating = afterPotential - beforePotential + (afterScore - beforeScore) * mMultiplyScoreFactor;
			if (ratedCombinations.isEmpty() || (ratedCombinations.first().getRating() < rating))
			{
				if (ratedCombinations.size() > 10)
					ratedCombinations.remove(ratedCombinations.first());
				ratedCombinations.add(new RatedCombination(rating, action.getPoint1(), action.getPoint2()));
			}
		}
		return ratedCombinations.toArray(new RatedCombination[ratedCombinations.size()]);
	}

	private int computePotential(Puzzle<Element> pPuzzle)
	{
		int potential = 0;
		for (int x = 0; x < pPuzzle.getWidth(); x++)
		{
			for (int y = 0; y < pPuzzle.getHeight(); y++)
			{
				if (pPuzzle.get(x, y) != null)
					potential += computePointPotential(pPuzzle, x, y);
			}
		}
		return potential;
	}

	private int computePointPotential(Puzzle<Element> pPuzzle, int pOriginX, int pOriginY)
	{
		Element originalElement = pPuzzle.get(pOriginX, pOriginY);
		int potential = 0;
		for (int y = pOriginY; y < pPuzzle.getHeight(); y++)
		{
			for (int x = (y == pOriginY ? pOriginX : 0); x < pPuzzle.getWidth(); x++)
			{
				if (!((x == pOriginX) && (y == pOriginY)))
				{
					if (pPuzzle.get(x, y) != null)
					{
						Element otherElement = pPuzzle.get(x, y);
						int fitness = originalElement.fitness(otherElement);
						if (fitness >= 2)
						// Don't try anything if the fitness is not worth it
						{
							int bestDistance = giveBestDistance(pPuzzle, pOriginX, pOriginY, x, y);
							potential += givePotentialFor(fitness, bestDistance);
						}
					}
				}
			}
		}
		return potential;
	}

	private int giveBestDistance(Puzzle<Element> pPuzzle, int pSrcX, int pSrcY, int pDestX, int pDestY)
	{
		// Explore x then y
		int distanceXY = 0;
		int x = pSrcX;
		while (x != pDestX)
		{
			if (x > pDestX)
				x--;
			else
				x++;
			if (x != pDestX)
				if (pPuzzle.get(x, pSrcY) != null)
					distanceXY++;
		}
		int y = pSrcY;
		while (y != pDestY)
		{
			if (pPuzzle.get(pDestX, y) != null)
				distanceXY++;
			if (y > pDestY)
				y--;
			else
				y++;
		}
		// Explore y then x
		int distanceYX = 0;
		y = pSrcY;
		while (y != pDestY)
		{
			if (y > pDestY)
				y--;
			else
				y++;
			if (y != pDestY)
				if (pPuzzle.get(pSrcX, y) != null)
					distanceYX++;
		}
		x = pSrcX;
		while (x != pDestX)
		{
			if (pPuzzle.get(x, pDestY) != null)
				distanceYX++;
			if (x > pDestX)
				x--;
			else
				x++;
		}
		return mMinDistance ? Math.min(distanceXY, distanceYX) : distanceXY + distanceYX;
	}

	private int givePotentialFor(int pFitness, int pDistance)
	{
		int distance = pDistance + 1;
		switch (mDistanceFunction)
		{
			case EXP:
				distance = (int)Math.exp(distance);
				break;
			case LOG:
				distance = (int)(Math.log(distance) + 1);;
				break;
			case SQRROOT:
				distance = (int)Math.sqrt(distance);
				break;
			case SQUARE:
				distance = distance * distance;
				break;
			default:
				break;
		}
		if (distance == 0)
			distance = 1;
		int fitness = mFitnesses[pFitness];
		return fitness / distance;
	}
}
