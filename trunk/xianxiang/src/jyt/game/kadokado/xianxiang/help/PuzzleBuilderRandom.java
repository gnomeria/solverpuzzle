/*
 * Created on 30 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help;

import java.util.Random;

import jyt.game.kadokado.xianxiang.help.Element.Colour;
import jyt.game.kadokado.xianxiang.help.Element.Geometry;
import jyt.game.kadokado.xianxiang.help.Element.InsidePattern;
import jyt.game.puzzle.solving.Puzzle;

public class PuzzleBuilderRandom implements IPuzzleBuilder
{
	private static final InsidePattern[] INSIDE_PATTERN_VALUES = InsidePattern.values();
	private static final Geometry[] GEOM_VALUES = Geometry.values();
	private static final Colour[] COLOUR_VALUES = Colour.values();
	private static final Random sRandom = new Random(System.currentTimeMillis());

	@Override
	public Puzzle<Element> buildPuzzle()
	{
		Puzzle<Element> puzzle = new Puzzle<Element>(6, 7);
		for (int x = 0; x < puzzle.getWidth(); x++)
		{
			for (int y = 0; y < puzzle.getHeight(); y++)
			{
				puzzle.set(x, y, new Element(COLOUR_VALUES[sRandom.nextInt(COLOUR_VALUES.length)], GEOM_VALUES[sRandom.nextInt(GEOM_VALUES.length)], INSIDE_PATTERN_VALUES[sRandom.nextInt(INSIDE_PATTERN_VALUES.length)]));
			}
		}
		return puzzle;
	}
}
