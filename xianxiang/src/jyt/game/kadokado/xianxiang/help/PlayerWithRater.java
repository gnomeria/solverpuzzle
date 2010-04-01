/*
 * Created on 30 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help;

import java.util.Arrays;

import jyt.game.puzzle.solving.ActionManager;
import jyt.game.puzzle.solving.Puzzle;

public class PlayerWithRater implements IPlayer
{
	private ICombinationRater mCombinationRater;
	private ActionManager<Element> mActionManager;
	private ScoreComputer mScoreComputer;

	/**
	 * Created on 30 mars 2010 by jtoumit.<br>
	 * @param pCombinationRater
	 * @param pActionManager
	 * @param pScoreComputer
	 */
	public PlayerWithRater(ICombinationRater pCombinationRater, ActionManager<Element> pActionManager, ScoreComputer pScoreComputer)
	{
		super();
		mCombinationRater = pCombinationRater;
		mActionManager = pActionManager;
		mScoreComputer = pScoreComputer;
	}

	/**
	 * TODO How does this method <code>play</code> override its parent?<br>
	 * Created on 31 mars 2010
	 * @author jtoumit
	 * @see jyt.game.kadokado.xianxiang.help.IPlayer#play(jyt.game.puzzle.solving.Puzzle)
	 */
	public void play(Puzzle<Element> pPuzzle)
	{
		RatedCombination[] combinations;
		while ((combinations = mCombinationRater.getCombinations(pPuzzle)).length > 0)
		{
			Arrays.sort(combinations);
			RatedCombination combination = combinations[combinations.length - 1];
			mActionManager.executeActionAndCollapse(new ActionMatch(combination.getPoint1(), combination.getPoint2(), mScoreComputer));
		}
	}
}
