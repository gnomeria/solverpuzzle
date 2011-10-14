/*
 * Created on 13 oct. 2011
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import jyt.game.puzzle.solving.impl.Point;

public class PointDouble
{
	private double x;
	private double y;

	public PointDouble(Point pPoint)
	{
		this(pPoint.getX(), pPoint.getY());
	}
	public PointDouble(double pX, double pY)
	{
		x = pX;
		y = pY;
	}
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
}