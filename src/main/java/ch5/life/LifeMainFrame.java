package ch5.life;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Artyom Zheltyshev on 19.02.2026
 */
public class LifeMainFrame extends JFrame {

    public LifeMainFrame(Board board) throws HeadlessException, InterruptedException {
        super("Игра \"Жизнь\"");
        int workers = 16;
        BlockingQueue<BoardChangedEvent> eventQueue = new ArrayBlockingQueue<>(1);
        CellularAutomata cellularAutomata = new CellularAutomata(board, workers, 2000, eventQueue);
        LifeTableModel tableModel = new LifeTableModel(board);
        JTable table = new JTable(tableModel);
        table.setDefaultRenderer(State.class, new StateCellRenderer());
        table.setRowHeight(7);

        setContentPane(table);
        setSize(1000, 800);
        setVisible(true);

        cellularAutomata.start();
        while (true) {
            BoardChangedEvent event = eventQueue.take();
            table.updateUI();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Board board = randomBoard();
        new LifeMainFrame(board);
    }

    private static Board glider() {
        Board board = new Board(100, 100);
        board.setNewValue(2, 1, State.ALIVE);
        board.setNewValue(3, 2, State.ALIVE);
        board.setNewValue(4, 2, State.ALIVE);
        board.setNewValue(2, 3, State.ALIVE);
        board.setNewValue(3, 3, State.ALIVE);
        board.commitNewValues();
        return board;
    }

    private static Board randomBoard() {
        Board board = new Board(5000, 5000);
        Random random = new Random();
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                int randomInt = random.nextInt(3);
                if (randomInt == 2) {
                    board.setNewValue(x, y, State.ALIVE);
                }
            }
        }
        board.commitNewValues();
        return board;
    }
}
