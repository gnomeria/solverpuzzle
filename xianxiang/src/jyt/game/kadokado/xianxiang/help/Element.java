/*
 * Created on 29 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help;

import java.awt.Color;

public class Element
{
	public enum Geometry {RECTANGLE, TRIANGLE, ROUND, };
	public enum InsidePattern {CROSS, RECTANGLE, DOTS, TRIANGLE, WAVES};

	private Color mColor;
	private Geometry mGeometry;
	private InsidePattern mInsidePattern;

	/**
	 * Created on 29 mars 2010 by jtoumit.<br>
	 * @param pColor
	 * @param pGeometry
	 * @param pInsidePattern
	 */
	public Element(Color pColor, Geometry pGeometry, InsidePattern pInsidePattern)
	{
		super();
		mColor = pColor;
		mGeometry = pGeometry;
		mInsidePattern = pInsidePattern;
	}

	public Color getColor()
	{
		return mColor;
	}
	public Geometry getGeometry()
	{
		return mGeometry;
	}
	public InsidePattern getInsidePattern()
	{
		return mInsidePattern;
	}
}
