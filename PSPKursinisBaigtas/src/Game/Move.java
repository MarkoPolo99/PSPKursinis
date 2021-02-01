package Game;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Move {
    public final Reversi reversi;

    public Move(Reversi reversi) {
        this.reversi = reversi;
    }

    boolean isThereValidMove(int color) {
        if (IntStream.range(0, 8).anyMatch(i -> IntStream.range(0, 8).anyMatch(j -> isMoveValidInCoordinates(color, i, j))))
            return true;
        else return false;
    }

    public boolean isMoveValidInCoordinates(int color, int x, int y) {
        int opponent = 3 - color;
        if (coordinatesOutOfBounds(x, y)) return false;
        return checkCoordinatesInAllDirections(color, x, y, opponent);
    }

    public boolean checkCoordinatesInAllDirections(int color, int x, int y, int opponent) {
        if (reversi.board[x][y] == 0) {
            for (int directionX = -1; directionX < 2; directionX++) {
                for (int directionY = -1; directionY < 2; directionY++) {
                    if (isMoveOutOfBoundsAfterFindingOpponent(x, y, directionX, directionY)) continue;
                    if (opponentPiecesFound(color, x, y, opponent, directionX, directionY)) return true;
                }
            }
        }
        return false;
    }

    public boolean opponentPiecesFound(int color, int x, int y, int opponent, int directionX, int directionY) {
        if (reversi.board[x + directionX][y + directionY] == opponent) {
            int tempX = x;
            int tempY = y;
            while (true) {
                tempX += directionX;
                tempY += directionY;
                if (isPieceOutOfBounds(tempX, tempY)) break;
                if (reversi.board[tempX][tempY] == color) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPieceOutOfBounds(int tempX, int tempY) {
        if (reversi.board[tempX][tempY] == 0 || tempX > 7 || tempY > 7) return true;
        else return false;
    }

    public boolean isMoveOutOfBoundsAfterFindingOpponent(int x, int y, int directionX, int directionY) {
        if (x + directionX < 0 || x + directionX > 7 || y + directionY < 0 || y + directionY > 7)
            return true;
        else return false;
    }

    public boolean coordinatesOutOfBounds(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7)
            return true;
        else return false;
    }

    void getMoveFromPlayer(int color) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        if (isThereValidMove(color)) {
            whichPlayerMoves(color);
            int x, y;
            System.out.print("Iveskite x koordinate: ");
            x = Integer.parseInt(bufferedReader.readLine());
            System.out.print("Iveskite y koordinate: ");
            y = Integer.parseInt(bufferedReader.readLine());
            checkPlayerGivenMove(color, x, y);
        } else {
            System.out.println(color == 1 ? "baltas zaidejas neturi galimu ejimu, praleidziama." : "juodas zaidejas neturi galimu ejimu, praleidziama.");
        }
    }

    public void checkPlayerGivenMove(int color, int x, int y) throws Exception {
        if (!isMoveValidInCoordinates(color, x, y)) {
            throw new Exception();
        } else {
            addMoveOnBoard(x, y, color);
        }
    }

    public void whichPlayerMoves(int color) {
        if (color == 1) System.out.println("Dabar yra  balto zaidejo ejimas");
        else System.out.println("Dabar yra juodo zaidejo ejimas");
    }

    void addMoveOnBoard(int x, int y, int color) {
        List<Point> endPoints = new ArrayList<>();
        reversi.board[x][y] = color;
        int opponent = 3 - color;
        checkAllDirections(x, y, color, endPoints, opponent);
        addPiecesOnBoard(x, y, color, endPoints);
    }

    public void checkAllDirections(int x, int y, int color, List<Point> endPoints, int opponent) {
        for (int directionX = -1; directionX < 2; directionX++) {
            for (int directionY = -1; directionY < 2; directionY++) {
                if (isMoveOutOfBoundsAfterFindingOpponent(x, y, directionX, directionY)) continue;
                opponentPieceWasFound(x, y, color, endPoints, opponent, directionX, directionY);
            }
        }
    }

    public void addPiecesOnBoard(int x, int y, int color, List<Point> endPoints) {
        for (Point point : endPoints) {
            int directionX = point.x - x;
            int directionY = point.y - y;
            directionX = getDirection(x, directionX, point.x);
            directionY = getDirection(y, directionY, point.y);
            flipPieces(color, point, directionX, directionY, x, y);
        }
    }

    public void flipPieces(int color, Point point, int directionX, int directionY, int tempX, int tempY) {
        while (tempX != point.x || tempY != point.y) {
            tempX += directionX;
            tempY += directionY;
            reversi.board[tempX][tempY] = color;
        }
    }

    public int getDirection(int x, int direction, int y) {
        if (direction != 0) {
            if (y - x > 0) direction = 1;
            else direction = -1;
        }
        return direction;
    }

    public void opponentPieceWasFound(int x, int y, int color, List<Point> endPoints, int opponent, int directionX, int directionY) {
        if (reversi.board[x + directionX][y + directionY] == opponent) {
            addToBoard(color, endPoints, directionX, directionY, x, y);
        }
    }

    public void addToBoard(int color, List<Point> endPoints, int directionX, int directionY, int tempX, int tempY) {
        while (true) {
            tempX += directionX;
            tempY += directionY;
            if (isPieceOutOfBounds(tempX, tempY)) break;
            if (reversi.board[tempX][tempY] == color) {
                endPoints.add(new Point(tempX, tempY));
            }
        }
    }
}