/*
 * Created on 24 janv. 2010
 * @author jtoumit
 */
package jyt.game.muxxu.kingdom.puzzle.help;

import java.util.ArrayList;
import java.util.Collection;

import jyt.game.puzzle.solving.ActionManager;
import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.Result;
import jyt.game.puzzle.solving.impl.actions.Invert;

public class HintDiscoverer
{
	private Puzzle<Element> mPuzzle;

	/**
	 * Created on 24 janv. 2010 by jtoumit.<br>
	 * @param pPuzzle
	 */
	public HintDiscoverer(Puzzle<Element> pPuzzle)
	{
		super();
		mPuzzle = pPuzzle;
	}

	public Collection<Hint> getHints()
	{
		ActionManager<Element> actionManager = new ActionManager<Element>(mPuzzle, new Collapser());
		Collection<Hint> hints = new ArrayList<Hint>();
		if (mPuzzle != null)
			for (int x = 0; x < mPuzzle.getWidth(); x++)
			{
				for (int y = 0; y < mPuzzle.getHeight(); y++)
				{
					if (x < mPuzzle.getWidth() - 1)
					// try right
						tryInvert(actionManager, hints, x, y, x + 1, y);
					if (y < mPuzzle.getHeight() - 1)
					// try down
						tryInvert(actionManager, hints, x, y, x, y + 1);
				}
			}
		return hints;
	}

	private void tryInvert(ActionManager<Element> pActionManager, Collection<Hint> pHints, int x1, int y1, int x2, int y2)
	{
		Invert<Element> action = new Invert<Element>(x1, y1, x2, y2);
		Result<Element> result = pActionManager.executeActionAndCollapse(action);
		if (result.nbReleased() > 0)
		{
			pHints.add(new Hint(x1, y1, x2, y2, result.nbReleased()));
		}
		pActionManager.rollBack();
	}

}
