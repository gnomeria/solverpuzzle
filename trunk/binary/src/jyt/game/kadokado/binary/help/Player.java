/*
 * Created on 28 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import jyt.game.puzzle.solving.ActionManager;
import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.actions.RotationClockWise;

public class Player
{
	private CombinationSearcher mCombinationSearcher;

	/**
	 * Created on 28 mars 2010 by jtoumit.<br>
	 * @param pCombinationSearcher
	 */
	public Player(CombinationSearcher pCombinationSearcher)
	{
		super();
		mCombinationSearcher = pCombinationSearcher;
	}

	public int play(Puzzle<Element> pPuzzle, PuzzleRefill pPuzzleRefill, ScoreComputer pScoreComputer)
	{
		Combination[] combinations;
		ActionManager<Element> actionManager = new ActionManager<Element>(pPuzzle, new Collapser());
		actionManager.addCollapseListener(pScoreComputer);
		while ((combinations = mCombinationSearcher.search(pPuzzle)).length > 0)
		{
			for (RotationClockWise<Element> rotation : combinations[0].getRotations())
			{
				actionManager.executeActionAndCollapse(rotation);
			}
			pPuzzleRefill.refill(pPuzzle);
		}
		return pScoreComputer.getCurrentScore();
	}
}
