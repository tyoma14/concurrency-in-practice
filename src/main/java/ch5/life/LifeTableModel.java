package ch5.life;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Created by Artyom Zheltyshev on 22.02.2026
 */
public class LifeTableModel implements TableModel {

    private final Board board;

    public LifeTableModel(Board board) {
        this.board = board;
    }

    @Override
    public int getRowCount() {
        return board.getHeight();
    }

    @Override
    public int getColumnCount() {
        return board.getWidth();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return State.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public State getValueAt(int rowIndex, int columnIndex) {
        return board.getValue(columnIndex, rowIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }
}
