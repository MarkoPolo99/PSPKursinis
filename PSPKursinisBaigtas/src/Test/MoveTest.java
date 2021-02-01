package Test;

import Game.Move;
import Game.Reversi;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveTest {

    @Test
    void testIsMoveValidInCoordinates() {
        assertEquals(false, (new Move(new Reversi())).isMoveValidInCoordinates(1, -1, 3));
        assertEquals(false, (new Move(new Reversi())).isMoveValidInCoordinates(2, 2, -1));
        assertEquals(false, (new Move(new Reversi())).isMoveValidInCoordinates(1, 8, -1));
    }

    @Test
    void testIsMoveOutOfBoundsAfterFindingOpponent() {
        assertEquals(false, (new Move(new Reversi())).isMoveOutOfBoundsAfterFindingOpponent(2, 3, 1, 1));
        assertEquals(true, (new Move(new Reversi())).isMoveOutOfBoundsAfterFindingOpponent(7, 3, 1, 1));
        assertEquals(true, (new Move(new Reversi())).isMoveOutOfBoundsAfterFindingOpponent(-2, 3, 1, 1));
    }

    @Test
    void testCoordinatesOutOfBounds() {
        assertEquals(false, (new Move(new Reversi())).coordinatesOutOfBounds(2, 3));
        assertEquals(true, (new Move(new Reversi())).coordinatesOutOfBounds(-1, 3));
        assertEquals(true, (new Move(new Reversi())).coordinatesOutOfBounds(2, -1));
    }
}