/*
 * Created on 23 janv. 2010
 * @author jtoumit
 */
package jyt.game.muxxu.kingdom.puzzle.help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jyt.game.puzzle.solving.ActionManager;
import jyt.game.puzzle.solving.IAction;
import jyt.game.puzzle.solving.ICollapseListener;
import jyt.game.puzzle.solving.ICollapser;
import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.actions.CollapseCol;
import jyt.game.puzzle.solving.impl.actions.CollapseRow;

public class Collapser implements ICollapser<Element>
{
	@Override
	public void collapse(Puzzle<Element> pPuzzle, ActionManager<Element> pActionManager, ICollapseListener<Element> pCollapseListener)
	{
		boolean oneCollapse = true;
		while (oneCollapse)
		{
			List<IAction<Element>> actions = new ArrayList<IAction<Element>>();
			boolean[][] used = new boolean[pPuzzle.getWidth()][pPuzzle.getHeight()];
			for (int i = 0; i < used.length; i++)
				Arrays.fill(used[i], false);

			oneCollapse = false;
			for (int x = 0; x < pPuzzle.getWidth(); x++)
			{
				for (int y = 0; y < pPuzzle.getHeight(); y++)
				{
					Element elt = pPuzzle.get(x, y);
					if ((elt != null) && (!used[x][y]))
					{
						int nb = 1;
						// Check right
						for (int i = 1; x + i < pPuzzle.getWidth(); i++)
						{
							Element newElement = pPuzzle.get(x + i, y);
							if ((newElement != null) && newElement.equals(elt) && (!used[x + i][y]))
								nb++;
							else
								break;
						}
						if (nb >= 3)
						{
							oneCollapse = true;
							for (int i = 0; i < nb; i++)
								used[x + i][y] = true;
							Element[] fill = new Element[nb];
							Arrays.fill(fill, null);
							actions.add(new CollapseRow<Element>(x, y, fill));
						}
						else
						{
							nb = 1;
							// Check down
							for (int i = 1; y + i < pPuzzle.getHeight(); i++)
							{
								Element newElement = pPuzzle.get(x, y + i);
								if ((newElement != null) && newElement.equals(elt) && (!used[x][y + i]))
									nb++;
								else
									break;
							}
							if (nb >= 3)
							{
								oneCollapse = true;
								for (int i = 0; i < nb; i++)
									used[x][y + i] = true;
								Element[] fill = new Element[nb];
								Arrays.fill(fill, null);
								actions.add(new CollapseCol<Element>(x, y, fill));
							}
						}
					}
				}
			}
			for (IAction<Element> action : actions)
			{
				if (action instanceof CollapseCol<?>)
				{
					CollapseCol<Element> collapse = (CollapseCol<Element>)action;
					pCollapseListener.collapsing(pPuzzle.get(collapse.getX(), collapse.getY()), collapse.getSize());
				}
				else if (action instanceof CollapseRow<?>)
				{
					CollapseRow<Element> collapse = (CollapseRow<Element>)action;
					pCollapseListener.collapsing(pPuzzle.get(collapse.getX(), collapse.getY()), collapse.getSize());
				}
				else
					throw new RuntimeException("Should not occur");
				pActionManager.executeAction(action);
			}
		}
	}
}
