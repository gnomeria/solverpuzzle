/*
 * Created on 24 janv. 2010
 * @author jtoumit
 */
package jyt.game.muxxu.kingdom.puzzle.help;

public class ElementRecognitionData
{
	public static int sFirstX = 21;
	public static int sFirstY = 11;
	public static int secondX = 15;
	public static int secondY = 20;

	public Element mElement;
	public int[] mFirstRGB;
	public int[] mSecondRGB;

	/**
	 * Created on 24 janv. 2010 by jtoumit.<br>
	 * @param pElement
	 * @param pFirstRGB
	 * @param pSecondRGB
	 */
	public ElementRecognitionData(Element pElement, int[] pFirstRGB, int[] pSecondRGB)
	{
		super();
		mElement = pElement;
		mFirstRGB = pFirstRGB;
		mSecondRGB = pSecondRGB;
	}
}
