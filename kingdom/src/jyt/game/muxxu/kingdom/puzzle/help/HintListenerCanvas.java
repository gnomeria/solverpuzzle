/*
 * Created on 24 janv. 2010
 * @author jtoumit
 */
package jyt.game.muxxu.kingdom.puzzle.help;

import java.awt.Canvas;
import java.awt.Graphics;

public class HintListenerCanvas extends Canvas implements IHintListener
{
	private Hint mCurrentHint = null;

	public HintListenerCanvas()
	{
		super();
		setSize(PuzzleBuilder.SQUARE_SIZE * 6, PuzzleBuilder.RECTANGLE_SIZE);
	}

	@Override
	public void setHint(Hint pHint)
	{
		if (pHint != mCurrentHint)
		{
			mCurrentHint = pHint;
			repaint();
		}
	}

	@Override
	public void paint(Graphics pG)
	{
		super.paint(pG);
		if (mCurrentHint != null)
		{
			for (int i = 0; i < mCurrentHint.getNbReleased(); i++)
			{
				for (int j = 0; j < mCurrentHint.getResult().getBlockSize(i); j++)
				{
					pG.drawImage(ImageHelper.getImage(mCurrentHint.getResult().getElement(i)), j * PuzzleBuilder.SQUARE_SIZE, i * PuzzleBuilder.SQUARE_SIZE, this);
				}
			}
		}
	}
}
