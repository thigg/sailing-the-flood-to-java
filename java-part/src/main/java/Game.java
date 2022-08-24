/*
 * © by Rhys Rustad-Elliott
 * The MIT License (MIT)

Copyright (c) 2016-2018 Rhys Rustad-Elliott

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import lombok.Getter;

/**
 * Class representing a game in progress.
 */
public class Game {
    private short board[];
    private int boardSize;

    private int numColors;

    private int steps = 0;
    private int maxSteps;

    record Score(int player, int steps, int comboScore) {
    }

    @Getter
    private final HashMap<Integer, Score> scores = new HashMap<>();

    private String seed;
    private static final String SEED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
    private static final int SEED_LENGTH_LOWER = 5;
    private static final int SEED_LENGTH_UPPER = 15;

    public Game(int boardSize, int numColors) {
        this(boardSize, numColors, generateRandomSeed());
    }

    public Game(int boardSize, int numColors, short[] _board) {
        this(boardSize, numColors);
        if (board.length != boardSize * boardSize) {
            throw new IllegalArgumentException("invalid boardsize");
        }
        for (final short i : _board) {
            if (i >= numColors || i < 0) {
                throw new IllegalArgumentException("invalid color: " + i);
            }
        }
        board = _board;
    }


    public Game(int boardSize, int numColors, String seed) {
        this.boardSize = boardSize;
        this.numColors = numColors;
        this.seed = seed;
        initBoard();
        initMaxSteps();
    }


    private static String generateRandomSeed() {
        Random rand = new Random(System.currentTimeMillis());
        String currSeed = "";
        for (int i = 0; i < rand.nextInt((SEED_LENGTH_UPPER - SEED_LENGTH_LOWER) + 1) + SEED_LENGTH_LOWER; i++) {
            currSeed += SEED_CHARS.charAt(rand.nextInt(SEED_CHARS.length()));
        }
        return currSeed;
    }

    public short[] getBoard() {
        return board;
    }

    public short getColor(int x, int y) {
        return board[y * boardSize + x];
    }

    private short getColor(final BoardPoint currPoint) {
        return getColor(currPoint.x(), currPoint.y());
    }

    public void setColor(int x, int y, short color) {
        board[y * boardSize + x] = color;
    }


    private void setColor(final BoardPoint currPoint, final short replacementColor) {
        setColor(currPoint.x(), currPoint.y(), replacementColor);
    }


    public int getBoardDimensions() {
        return boardSize;
    }

    public String getSeed() {
        return seed;
    }

    public int getSteps() {
        return steps;
    }

    public int getMaxSteps() {
        return maxSteps;
    }

    private void initBoard() {
        board = new short[boardSize * boardSize];
        Random r = new Random(seed.hashCode());
        final int[] ints = r.ints(boardSize * boardSize, 0, numColors).toArray();
        for (int i = 0; i < boardSize * boardSize; i++) {
            board[i] = (short) ints[i];
        }
    }

    private void initMaxSteps() {
        maxSteps = 30 * (boardSize * numColors) / (17 * 6);
    }


    private boolean pointInField(BoardPoint point) {
        return point.x >= 0 && point.x < boardSize && point.y >= 0 && point.y < boardSize;
    }


    public void flood(short replacementColor, int player) {
        final BoardPoint startField = player == 0 ? new BoardPoint(0, 0) : new BoardPoint(boardSize - 1, boardSize - 1);
        final short targetColor = getColor(startField);
        if (targetColor == replacementColor) {
            return;
        }

        int newAcquired = 0; //make this exponential so higher combos give a higher score

        record QueueItem(BoardPoint point, short color) {
        }
        final Queue<QueueItem> queue = new LinkedList<>();
        final boolean[] seen = new boolean[board.length]; //default false
        seen[startField.toArrayCoord(boardSize)] = true;

        queue.add(new QueueItem(startField, getColor(startField)));

        while (!queue.isEmpty()) {
            final QueueItem currItem = queue.remove();
            final BoardPoint currPoint = currItem.point;
            boolean replaced = false;

            if (currItem.color == replacementColor) {
                newAcquired++;
            } else if (currItem.color == targetColor) {
                replaced = true;
                setColor(currPoint, replacementColor);
            }
            final boolean finalReplaced = replaced;
            final BoardPoint left = new BoardPoint(currPoint.x() - 1, currPoint.y());
            final BoardPoint right = new BoardPoint(currPoint.x() + 1, currPoint.y());
            final BoardPoint below = new BoardPoint(currPoint.x(), currPoint.y() - 1);
            final BoardPoint above = new BoardPoint(currPoint.x(), currPoint.y() + 1);
            for (BoardPoint p : Arrays.asList(left, right, above, below)) {
                if (pointInField(p)) {
                    if (!seen[p.toArrayCoord(boardSize)]) {
                        final short color = getColor(p);
                        if (finalReplaced && color == targetColor || color == replacementColor) {
                            queue.add(new QueueItem(p, color));
                            seen[p.toArrayCoord(boardSize)] = true;
                        }
                    }
                }
            }
        }
        scores.putIfAbsent(player, new Score(player, 0, 0));
        //hillariously complicating way of handling scores
        scores.merge(player, new Score(player, 1, newAcquired * newAcquired),
                (a, b) -> new Score(a.player, a.steps + b.steps, a.comboScore + b.comboScore));
        steps++;
    }


    public boolean checkWin() {
        short lastColor = getColor(0, 0);
        for (final short b : board)
            if (lastColor != b) {
                return false;
            }

        return true;
    }

    public void flood(final int replacementColor, final int player) {
        flood((short) replacementColor, player);
    }

    record BoardPoint(int x, int y) {
        public int toArrayCoord(int boardSize) {
            return x * boardSize + y;
        }
    }

}
