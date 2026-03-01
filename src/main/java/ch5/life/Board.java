package ch5.life;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Artyom Zheltyshev on 15.02.2026
 */
public class Board {

    private final State[][] matrix;
    private final State[][] rawMatrix;
    private final int width;
    private final int height;

    public Board(int width, int height) {
        this.matrix = emptyMatrix(width, height);
        this.rawMatrix = emptyMatrix(width, height);
        this.width = width;
        this.height = height;
    }

    private static State[][] emptyMatrix(int width, int height) {
        State[][] m = new State[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(m[i], State.DEAD);
        }
        return m;
    }

    public boolean hasConverged() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (getValue(j, i) == State.ALIVE) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setNewValue(int x, int y, State value) {
        rawMatrix[y][x] = value;
    }

    public State getValue(int x, int y) {
        return matrix[y][x];
    }

    public List<State> getNeighbors(int x, int y) {
        List<State> neighbors = new ArrayList<>();
        if (x > 0) {
            neighbors.add(getValue(x - 1, y));
        }
        if (x < width - 1) {
            neighbors.add(getValue(x + 1, y));
        }
        if (y > 0) {
            neighbors.add(getValue(x, y - 1));
        }
        if (y < height - 1) {
            neighbors.add(getValue(x, y + 1));
        }
        if (x > 0 && y > 0) {
            neighbors.add(getValue(x - 1, y - 1));
        }
        if (x < width - 1 && y > 0) {
            neighbors.add(getValue(x + 1, y - 1));
        }
        if (x > 0 && y < height - 1) {
            neighbors.add(getValue(x - 1, y + 1));
        }
        if (x < width - 1 && y < height - 1) {
            neighbors.add(getValue(x + 1, y + 1));
        }
        return neighbors;
    }

    public void commitNewValues() {
        for (int i = 0; i < height; i++) {
            matrix[i] = Arrays.copyOf(rawMatrix[i], width);
        }
    }
}
