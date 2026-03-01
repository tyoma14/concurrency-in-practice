package ch5.life;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static ch5.life.State.ALIVE;
import static ch5.life.State.DEAD;

/**
 * Created by Artyom Zheltyshev on 14.02.2026
 */
public class CellularAutomata {

    private final Board mainBoard;
    private final CyclicBarrier barrier;
    private final List<Worker> workers;
    private final int updateRate;
    private final BlockingQueue<BoardChangedEvent> eventQueue;

    public CellularAutomata(Board initialBoard, int maxWorkersCount, int updateRate, BlockingQueue<BoardChangedEvent> eventQueue) {    // Runtime.getRuntime().availableProcessors()
        this.mainBoard = initialBoard;
        this.barrier = new CyclicBarrier(maxWorkersCount, this::commitNewValues);
        this.workers = createWorkers(maxWorkersCount);
        this.updateRate = updateRate;
        this.eventQueue = eventQueue;
    }

    public void start() {
        for (Worker worker : workers) {
            new Thread(worker).start();
        }
//        try {
//            mainBoard.waitForConvergence();
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
    }

    private void commitNewValues() {
        try {
            Thread.sleep(updateRate);
            mainBoard.commitNewValues();
            eventQueue.put(new BoardChangedEvent());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private class Worker implements Runnable {
        private final int xShift;
        private final int yShift;
        private final int width;
        private final int height;

        public Worker(int xShift, int yShift, int width, int height) {
            this.xShift = xShift;
            this.yShift = yShift;
            this.width = width;
            this.height = height;
        }

        @Override
        public void run() {
            while (!mainBoard.hasConverged()) {
                for (int x = xShift; x < xShift + width; x++) {
                    for (int y = yShift; y < yShift + height; y++) {
                        mainBoard.setNewValue(x, y, computeValue(x, y));
                    }
                }
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        private State computeValue(int x, int y) {
            State value = mainBoard.getValue(x, y);
            List<State> neighbors = mainBoard.getNeighbors(x, y);
            long aliveNeighborsCount = neighbors.stream()
                    .filter(state -> state.equals(ALIVE))
                    .count();
            if (value == ALIVE && aliveNeighborsCount < 2) {
                return DEAD;
            } else if (value == ALIVE && aliveNeighborsCount == 2) {
                return ALIVE;
            } else if (value == ALIVE && aliveNeighborsCount == 3) {
                return ALIVE;
            } else if (value == ALIVE) {
                return DEAD;
            } else if (value == DEAD && aliveNeighborsCount == 3) {
                return ALIVE;
            } else {
                return DEAD;
            }
        }

    }

    private List<Worker> createWorkers(int maxCount) {
        if (mainBoard.getWidth() >= mainBoard.getHeight()) {
            return createWorkersAlong(maxCount);
        } else {
            return createWorkersAcross(maxCount);
        }
    }

    private List<Worker> createWorkersAlong(int maxCount) {
        int count = Integer.min(mainBoard.getWidth(), maxCount);
        int baseWidth = mainBoard.getWidth() / count;
        int remainderWidth = mainBoard.getWidth() % count;
        int[] widths = new int[count];
        for (int i = 0; i < count; i++) {
            if (remainderWidth > 0) {
                widths[i] = baseWidth + 1;
                remainderWidth--;
            } else {
                widths[i] = baseWidth;
            }
        }
        List<Worker> result = new ArrayList<>();
        int xShift = 0;
        for (int width : widths) {
            Worker worker = new Worker(xShift, 0, width, mainBoard.getHeight());
            result.add(worker);
            xShift += width;
        }
        return result;
    }

    private List<Worker> createWorkersAcross(int maxCount) {
        int count = Integer.min(mainBoard.getHeight(), maxCount);
        int baseHeight = mainBoard.getHeight() / count;
        int remainderHeight = mainBoard.getHeight() % count;
        int[] heights = new int[count];
        for (int i = 0; i < count; i++) {
            if (remainderHeight > 0) {
                heights[i] = baseHeight + 1;
                remainderHeight--;
            } else {
                heights[i] = baseHeight;
            }
        }
        List<Worker> result = new ArrayList<>();
        int yShift = 0;
        for (int height : heights) {
            Worker worker = new Worker(0, yShift, mainBoard.getWidth(), height);
            result.add(worker);
            yShift += height;
        }
        return result;
    }

}
