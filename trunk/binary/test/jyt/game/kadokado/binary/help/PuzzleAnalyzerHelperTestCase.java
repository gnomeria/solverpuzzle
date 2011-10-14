/*
 * Created on 13 oct. 2011
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import jyt.game.puzzle.solving.impl.Point;

public class PuzzleAnalyzerHelperTestCase extends TestCase
{
	public void testAggregate()
	{
		List<Point> points = new ArrayList<Point>();
		points.add(new Point(0, 0));
		points.add(new Point(1, 1));
		points.add(new Point(10, 10));
		List<List<Point>> aggregates = PuzzleAnalyzerHelper.createAggregates(points, 4);
		assertEquals(2, aggregates.size());
		assertEquals(2, aggregates.get(0).size());
		assertEquals(1, aggregates.get(1).size());
	}
}
