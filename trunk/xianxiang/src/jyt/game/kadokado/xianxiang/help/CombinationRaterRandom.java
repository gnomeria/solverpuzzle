/*
 * Created on 31 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help;

import java.util.Iterator;
import java.util.Random;

import jyt.game.puzzle.solving.Puzzle;

public class CombinationRaterRandom implements ICombinationRater
{
	private ScoreComputer mScoreComputer;
	private final static Random sRandom = new Random(System.currentTimeMillis()); 

	/**
	 * Created on 31 mars 2010 by jtoumit.<br>
	 * @param pScoreComputer
	 */
	public CombinationRaterRandom(ScoreComputer pScoreComputer)
	{
		super();
		mScoreComputer = pScoreComputer;
	}

	@Override
	public RatedCombination[] getCombinations(Puzzle<Element> pPuzzle)
	{
		Iterator<ActionMatch> actions = new PossibleActionsComputer(pPuzzle, mScoreComputer);
		ActionMatch chosenAction = null;
		for (Iterator<ActionMatch> iterator = actions; iterator.hasNext();)
		{
			chosenAction = iterator.next();
			if (sRandom.nextInt(20) == 0)
				break;
		}
		return chosenAction == null ? new RatedCombination[0] : new RatedCombination[] {new RatedCombination(100, chosenAction.getPoint1(), chosenAction.getPoint2())};
	}

}
