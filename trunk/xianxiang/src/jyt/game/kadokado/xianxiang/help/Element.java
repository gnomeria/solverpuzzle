/*
 * Created on 29 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help;

import java.awt.Color;

public class Element
{
	public enum Colour
	{
		GREEN(Color.green, "green"),
		BLUE(Color.blue, "blue"),
		RED(Color.red, "red"),
		YELLOW(Color.orange, "yellow")
		;
		Color mColor;
		String mName;
		Colour(Color pColor, String pName)
		{
			mColor = pColor;
			mName = pName;
		}
		public Color getColor()
		{
			return mColor;
		}
		public String getName()
		{
			return mName;
		}
	};
	public enum Geometry {RECTANGLE, TRIANGLE, ROUND};
	public enum InsidePattern {CROSS, RECTANGLE, DOTS, TRIANGLE, WAVES};

	private Colour mColour;
	private Geometry mGeometry;
	private InsidePattern mInsidePattern;

	/**
	 * Created on 29 mars 2010 by jtoumit.<br>
	 * @param pColour
	 * @param pGeometry
	 * @param pInsidePattern
	 */
	public Element(Colour pColour, Geometry pGeometry, InsidePattern pInsidePattern)
	{
		super();
		mColour = pColour;
		mGeometry = pGeometry;
		mInsidePattern = pInsidePattern;
	}

	public Colour getColour()
	{
		return mColour;
	}
	public Geometry getGeometry()
	{
		return mGeometry;
	}
	public InsidePattern getInsidePattern()
	{
		return mInsidePattern;
	}
	@Override
	public boolean equals(Object pObj)
	{
		if (pObj == null)
			return false;
		if (pObj == this)
			return true;
		if (pObj instanceof Element)
		{
			Element element = (Element)pObj;
			return mColour.equals(element.getColour()) && mGeometry.equals(element.getGeometry()) && mInsidePattern.equals(element.getInsidePattern());
		}
		else
			return false;
	}
	@Override
	public int hashCode()
	{
		return mColour.hashCode() | mGeometry.hashCode() | mInsidePattern.hashCode();
	}

	/**
	 * Returns how much similar the current element is with the element passed.<br>
	 * 0 = no similarities<br>
	 * 3 = elements are equal<br>
	 * Created by jtoumit on 31 mars 2010.<br>
	 * @param pOtherElement
	 * @return the fitness between these elements
	 */
	public int fitness(Element pOtherElement)
	{
		int nbMatch = 0;
		if (mColour.equals(pOtherElement.mColour))
			nbMatch++;
		if (mGeometry.equals(pOtherElement.mGeometry))
			nbMatch++;
		if (mInsidePattern.equals(pOtherElement.mInsidePattern))
			nbMatch++;
		return nbMatch;
	}
}
