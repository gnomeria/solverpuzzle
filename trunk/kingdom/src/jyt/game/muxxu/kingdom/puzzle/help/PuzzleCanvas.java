/*
 * Created on 24 janv. 2010
 * @author jtoumit
 */
package jyt.game.muxxu.kingdom.puzzle.help;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import jyt.game.puzzle.solving.Puzzle;

public class PuzzleCanvas extends Canvas
{
	private Puzzle<Element> mPuzzle;
	private Map<Element, Image> mImages = new HashMap<Element, Image>();

	/**
	 * Created on 24 janv. 2010 by jtoumit.<br>
	 * @param pPuzzle
	 * @throws IOException 
	 */
	public PuzzleCanvas(Puzzle<Element> pPuzzle) throws IOException
	{
		super();
		mPuzzle = pPuzzle;
		for (Element element : Element.values())
		{
			mImages.put(element, getImage(element.toString().toLowerCase() + ".png"));
		}
		setSize(PuzzleBuilder.RECTANGLE_SIZE, PuzzleBuilder.RECTANGLE_SIZE);
	}

	private Image getImage(String pName) throws IOException
	{
		// Read from a URL
		InputStream is = getClass().getResourceAsStream("/jyt/game/muxxu/kingdom/puzzle/help/img/" + pName);
//		URL url = new URL("/jyt/game/mukku/kingdom/puzzle/help/img/" + pName);
		return ImageIO.read(is);
	}

	@Override
	public void paint(Graphics pG)
	{
		super.paint(pG);
//		pG.setColor(Color.BLACK);
//		for (int x = 0; x < PuzzleBuilder.RECTANGLE_SIZE; x++)
//		{
//			for (int y = 0 ; y < PuzzleBuilder.RECTANGLE_SIZE; y++)
//			{
//				if (PuzzleBuilder.zob[x + y * PuzzleBuilder.RECTANGLE_SIZE] == 1)
//					pG.drawRect(x+5, y+30, 1, 1);
//			}
//		}
		if (mPuzzle != null)
		{
			for (int x = 0; x < mPuzzle.getWidth(); x++)
			{
				for (int y = 0; y < mPuzzle.getHeight(); y++)
				{
					pG.drawImage(mImages.get(mPuzzle.get(x, y)), x * PuzzleBuilder.SQUARE_SIZE, y * PuzzleBuilder.SQUARE_SIZE, this);
				}
			}
		}
	}
}
