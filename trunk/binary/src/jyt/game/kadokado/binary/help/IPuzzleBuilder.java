/*
 * Created on 28 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import jyt.game.puzzle.solving.Puzzle;

public interface IPuzzleBuilder
{
	Puzzle<Element> buildPuzzle();
}
