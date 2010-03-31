/*
 * Created on 1 avr. 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help;

import java.util.Iterator;
import java.util.Random;

import jyt.game.puzzle.solving.Puzzle;

public class CombinationRaterBestCurrentMove implements ICombinationRater
{
	private ScoreComputer mScoreComputer;
	private final static Random sRandom = new Random(System.currentTimeMillis()); 

	/**
	 * Created on 31 mars 2010 by jtoumit.<br>
	 * @param pScoreComputer
	 */
	public CombinationRaterBestCurrentMove(ScoreComputer pScoreComputer)
	{
		super();
		mScoreComputer = pScoreComputer;
	}

	@Override
	public RatedCombination[] getCombinations(Puzzle<Element> pPuzzle)
	{
		Iterator<ActionMatch> actions = new PossibleActionsComputer(pPuzzle, mScoreComputer);
		ActionMatch chosenAction = null;
		int bestMatch = -1;
		for (Iterator<ActionMatch> iterator = actions; iterator.hasNext();)
		{
			ActionMatch action = iterator.next();
			int fitness = pPuzzle.get(action.getPoint1()).fitness(pPuzzle.get(action.getPoint2()));
			if (fitness > bestMatch)
			{
				bestMatch = fitness;
				chosenAction = action;
			}
		}
		return chosenAction == null ? new RatedCombination[0] : new RatedCombination[] {new RatedCombination(100, chosenAction.getPoint1(), chosenAction.getPoint2())};
	}
}
