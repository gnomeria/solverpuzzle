/*
 * Created on 24 janv. 2010
 * @author jtoumit
 */
package jyt.game.muxxu.kingdom.puzzle.help;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import jyt.game.puzzle.solving.Puzzle;

public class PuzzleCanvas extends Canvas
{
	private Puzzle<Element> mPuzzle;
	private List<Hint> mHints = new ArrayList<Hint>();
	private Map<Element, Image> mImages = new HashMap<Element, Image>();

	/**
	 * Created on 24 janv. 2010 by jtoumit.<br>
	 * @param pPuzzle
	 * @throws IOException 
	 */
	public PuzzleCanvas(Puzzle<Element> pPuzzle) throws IOException
	{
		super();
		for (Element element : Element.values())
		{
			mImages.put(element, getImage(element.toString().toLowerCase() + ".png"));
		}
		setSize(PuzzleBuilder.RECTANGLE_SIZE, PuzzleBuilder.RECTANGLE_SIZE);
		setPuzzle(pPuzzle);
	}

	private Image getImage(String pName) throws IOException
	{
		return ImageIO.read(getClass().getResourceAsStream("/jyt/game/muxxu/kingdom/puzzle/help/img/" + pName));
	}

	@Override
	public void paint(Graphics pG)
	{
		super.paint(pG);
		if (mPuzzle != null)
		{
			for (int x = 0; x < mPuzzle.getWidth(); x++)
			{
				for (int y = 0; y < mPuzzle.getHeight(); y++)
				{
					pG.drawImage(mImages.get(mPuzzle.get(x, y)), x * PuzzleBuilder.SQUARE_SIZE, y * PuzzleBuilder.SQUARE_SIZE, this);
				}
			}
			for (Hint hint : mHints)
			{
				pG.setColor(new Color(Math.min(255, hint.getNbReleased() * 50), 0, 0));
				pG.drawLine(hint.getX1() * PuzzleBuilder.SQUARE_SIZE + PuzzleBuilder.SQUARE_SIZE / 2, hint.getY1() * PuzzleBuilder.SQUARE_SIZE + PuzzleBuilder.SQUARE_SIZE / 2, hint.getX2() * PuzzleBuilder.SQUARE_SIZE + PuzzleBuilder.SQUARE_SIZE / 2, hint.getY2() * PuzzleBuilder.SQUARE_SIZE + PuzzleBuilder.SQUARE_SIZE / 2);
			}
		}
	}

	public void setPuzzle(Puzzle<Element> pPuzzle)
	{
		mPuzzle = pPuzzle;
		mHints.clear();
		HintDiscoverer discoverer = new HintDiscoverer(mPuzzle);
		mHints.addAll(discoverer.getHints());
		repaint();
	}
}
