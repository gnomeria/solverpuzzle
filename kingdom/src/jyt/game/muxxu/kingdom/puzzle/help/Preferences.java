package jyt.game.muxxu.kingdom.puzzle.help;

import java.util.HashMap;
import java.util.Map;

public class Preferences
{
	private Map<Element, Integer> mPrefs = new HashMap<Element, Integer>();

	public Preferences()
	{
		super();
	}

	public void setPreference(Element pElement, int pValue)
	{
		mPrefs.put(pElement, pValue);
	}

	public int getPreference(Element pElement)
	{
		return mPrefs.containsKey(pElement) ? mPrefs.get(pElement) : 1;
	}
}
