/*
 * Created on 28 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import jyt.game.puzzle.solving.ICollapseListener;

public class ScoreComputer implements ICollapseListener<Element>
{
	private int mCurrentScore = 0;

	@Override
	public void collapsing(Element pType, int pNb)
	{
		mCurrentScore += pType.getPoints() * pNb;
	}
	public int getCurrentScore()
	{
		return mCurrentScore;
	}
}
