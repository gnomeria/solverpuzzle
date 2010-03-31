/*
 * Created on 24 janv. 2010
 * @author jtoumit
 */
package jyt.game.muxxu.kingdom.puzzle.help.ui;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import jyt.game.muxxu.kingdom.puzzle.help.Element;

public class ImageHelper
{
	private static Map<Element, Image> sImages = new HashMap<Element, Image>();

	static
	{
		for (Element element : Element.values())
		{
			try
			{
				sImages.put(element, ImageIO.read(ImageHelper.class.getResourceAsStream("/jyt/game/muxxu/kingdom/puzzle/help/ui/img/" + element.toString().toLowerCase() + ".png")));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public static Image getImage(Element pElement)
	{
		return sImages.get(pElement);
	}
}
