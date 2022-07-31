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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import lombok.Value;

/**
 * Class representing a game in progress.
 */
public class Game {
    private short board[];
    private int boardSize;

    private int numColors;

    private int steps = 0;
    private int maxSteps;

    private String seed;
    private static final String SEED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
    private static final int SEED_LENGTH_LOWER = 5;
    private static final int SEED_LENGTH_UPPER = 15;

    public Game(int boardSize, int numColors) {
        // Initialize board
        this.boardSize = boardSize;
        this.numColors = numColors;
        this.seed = generateRandomSeed();
        initBoard();
        initMaxSteps();
    }

    public Game(int boardSize, int numColors, String seed) {
        // Initialize board
        this.boardSize = boardSize;
        this.numColors = numColors;
        this.seed = seed;
        initBoard();
        initMaxSteps();
    }


    private String generateRandomSeed() {
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
        return board[y*boardSize+x];
    }

    private short getColor(final BoardPoint currPoint) {
        return getColor(currPoint.getX(), currPoint.getY());
    }

    public void setColor(int x, int y, short color) {
        board[y*boardSize+x] = color;
    }


    private void setColor(final BoardPoint currPoint, final short replacementColor) {
        setColor(currPoint.getX(),currPoint.getY(),replacementColor);
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
        board = new short[boardSize*boardSize];
        Random r = new Random(seed.hashCode());
        final int[] ints = r.ints(boardSize * boardSize,0, numColors).toArray();
        for (int i = 0; i < boardSize*boardSize; i++) {
                board[i]  = (short) ints[i];
        }
    }

    private void initMaxSteps() {
        maxSteps = 30 * (boardSize * numColors) / (17 * 6);
    }

    public void flood(short replacementColor) {
        short targetColor = getColor(0,0);
        if (targetColor == replacementColor) {
            return;
        }

        Queue<BoardPoint> queue = new LinkedList<BoardPoint>();
        Set<BoardPoint> processed = new HashSet<>();

        queue.add(new BoardPoint(0, 0));

        BoardPoint currPoint;
        while (!queue.isEmpty()) {
            currPoint = queue.remove();
            if (getColor(currPoint) == targetColor) {
                setColor(currPoint, replacementColor);
                final BoardPoint left = new BoardPoint(currPoint.getX() - 1, currPoint.getY());
                if (currPoint.getX() != 0 &&
                        !processed.contains(left)) {
                    queue.add(left);
                }
                final BoardPoint right = new BoardPoint(currPoint.getX() + 1, currPoint.getY());
                if (currPoint.getX() != boardSize - 1 &&
                        !processed.contains(right)) {
                    queue.add(right);
                }
                final boolean below = currPoint.getY() != 0 &&
                        !processed.contains(new BoardPoint(currPoint.getX(), currPoint.getY() - 1));
                if (below) {
                    queue.add(new BoardPoint(currPoint.getX(), currPoint.getY() - 1));
                }
                final BoardPoint above = new BoardPoint(currPoint.getX(), currPoint.getY() + 1);
                if (currPoint.getY() != boardSize - 1 &&
                        !processed.contains(above)) {
                    queue.add(above);
                }
                processed.add(currPoint);
            }
        }
        steps++;
    }


    public boolean checkWin() {
        short lastColor = getColor(0,0);
        for (final short b : board)
                if (lastColor != b) {
                    return false;
                }

        return true;
    }

    @Value
    private static class BoardPoint {
        int x, y;

        public BoardPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

    }
}
