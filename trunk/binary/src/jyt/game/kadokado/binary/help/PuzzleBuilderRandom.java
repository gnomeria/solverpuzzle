/*
 * Created on 24 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.awt.Color;
import java.util.Random;

import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.Point;

public class PuzzleBuilderRandom implements IPuzzleBuilder
{
	private static final int NB_COLORS = 4;
	public static final Color[] COLORS = new Color[] {Color.red, Color.green, Color.orange, Color.blue, Color.yellow, Color.pink, new Color(150, 100, 200), Color.cyan, new Color(39, 100, 30), new Color(22, 73, 0)};
	public static final int[] POINTS = new int[] {100, 100, 100, 100, 150, 200, 300, 400, 500, 600};

	public Puzzle<Element> buildPuzzle()
	{
		Puzzle<Element> puzzle = null;
		puzzle = new Puzzle<Element>(5, 5);
		Random random = new Random(System.currentTimeMillis());
		for (int x = 0; x < puzzle.getWidth(); x++)
		{
			for (int y = 0; y < puzzle.getHeight(); y++)
			{
				puzzle.set(x, y, pickColor(random));
			}
		}
		Zone[] zones = new Zone[0];
		int iterations = 0;
		do
		{
			for (Zone zone : zones)
			{
				Point point = zone.getPoints()[random.nextInt(zone.getPoints().length)];
				puzzle.set(point.getX(), point.getY(), pickColor(random));
			}
			zones = PuzzleAnalyzerHelper.getZones(puzzle);
		}
		while ((zones.length > 0) && (iterations++ < 1000));

		return puzzle;
	}

	private Element pickColor(Random pRandom)
	{
		int nextInt = pRandom.nextInt(NB_COLORS);
		return new Element(COLORS[nextInt], POINTS[nextInt]);
	}
}
