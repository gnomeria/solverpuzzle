/*
 * Created on 23 janv. 2010
 * @author jtoumit
 */
package jyt.game.muxxu.kingdom.puzzle.help;

import java.util.Arrays;

import jyt.game.puzzle.solving.ActionManager;
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
			oneCollapse = false;
			for (int x = 0; x < pPuzzle.getWidth(); x++)
			{
				for (int y = 0; y < pPuzzle.getHeight(); y++)
				{
					Element elt = pPuzzle.get(x, y);
					if (elt != null)
					{
						int nb = 1;
						// Check down
						for (int i = 1; y + i < pPuzzle.getHeight(); i++)
						{
							Element newElement = pPuzzle.get(x, y + i);
							if ((newElement != null) && (newElement.equals(elt)))
								nb++;
							else
								break;
						}
						if (nb >= 3)
						{
							oneCollapse = true;
							pCollapseListener.collapsing(elt, nb);
							Element[] fill = new Element[nb];
							Arrays.fill(fill, null);
							pActionManager.executeAction(new CollapseCol<Element>(x, y, fill));
						}
						nb = 1;
						// Check right
						for (int i = 1; x + i < pPuzzle.getWidth(); i++)
						{
							Element newElement = pPuzzle.get(x + i, y);
							if ((newElement != null) && (newElement.equals(elt)))
								nb++;
							else
								break;
						}
						if (nb >= 3)
						{
							oneCollapse = true;
							pCollapseListener.collapsing(elt, nb);
							Element[] fill = new Element[nb];
							Arrays.fill(fill, null);
							pActionManager.executeAction(new CollapseRow<Element>(x, y, fill));
						}
					}
				}
			}
		}
	}
}
