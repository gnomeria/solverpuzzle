/*
 * Created on 26 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.Point;

public class PuzzleAnalyzerHelper
{
	static Zone[] getZones(Puzzle<Element> pPuzzle)
	{
		Puzzle<Element> puzzle = new Puzzle<Element>(pPuzzle);
		List<Zone> zones = new ArrayList<Zone>();
		for (int x = 0; x < puzzle.getWidth(); x++)
		{
			for (int y = 0; y < puzzle.getHeight(); y++)
			{
				if (puzzle.get(x, y) != null)
				{
					List<Point> points = new ArrayList<Point>();
					aggregate(puzzle, points, x, y, puzzle.get(x, y).getColor());
					if (points.size() > 3)
						zones.add(new Zone(points.toArray(new Point[points.size()])));
				}
			}
		}
		return zones.toArray(new Zone[zones.size()]);
	}

	static void aggregate(Puzzle<Element> pPuzzle, List<Point> pPoints, int pX, int pY, Color pColor)
	{
		if ((pX >= pPuzzle.getWidth()) || (pX < 0) || (pY >= pPuzzle.getHeight()) || (pY < 0) || (pPuzzle.get(pX, pY) == null) || (!pPuzzle.get(pX, pY).getColor().equals(pColor)))
			return;
		pPoints.add(new Point(pX, pY));
		pPuzzle.set(pX, pY, null);
		aggregate(pPuzzle, pPoints, pX + 1, pY, pColor);
		aggregate(pPuzzle, pPoints, pX, pY + 1, pColor);
		aggregate(pPuzzle, pPoints, pX - 1, pY, pColor);
		aggregate(pPuzzle, pPoints, pX, pY - 1, pColor);
	}
}
