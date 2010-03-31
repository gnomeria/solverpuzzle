/*
 * Created on 30 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help;

import jyt.game.puzzle.solving.Puzzle;

public interface ICombinationRater
{
	RatedCombination[] getCombinations(Puzzle<Element> pPuzzle);
}
