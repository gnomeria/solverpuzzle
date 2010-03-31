/*
 * Created on 27 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jyt.game.puzzle.solving.ActionManager;
import jyt.game.puzzle.solving.ICollapseListener;
import jyt.game.puzzle.solving.ICollapser;
import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.Point;
import jyt.game.puzzle.solving.impl.actions.CollapseOne;

public class Collapser implements ICollapser<Element>
{
	@Override
	public void collapse(Puzzle<Element> pPuzzle, ActionManager<Element> pActionManager, ICollapseListener<Element> pCollapseListener)
	{
		Zone[] zones;
		do
		{
			zones = PuzzleAnalyzerHelper.getZones(pPuzzle);
			List<Point> orderedPoints = new ArrayList<Point>();
			for (Zone zone : zones)
				orderedPoints.addAll(Arrays.asList(zone.getPoints()));
			Collections.sort(orderedPoints, new Comparator<Point>()
			{
				@Override
				public int compare(Point p1, Point p2)
				{
					return new Integer(p1.getY()).compareTo(new Integer(p2.getY()));
				}
			});
			for (Point point : orderedPoints)
			{
				pCollapseListener.collapsing(pPuzzle.get(point.getX(), point.getY()), 1);
				pActionManager.executeAction(new CollapseOne<Element>(point.getX(), point.getY(), null));
			}
		}
		while (zones.length > 0);
	}
}
