package ch5.life;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by Artyom Zheltyshev on 22.02.2026
 */
public class StateCellRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {
        State state = (State) value;
        JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        if (state == State.ALIVE) {
            label.setText("A");
        }
        return label;
    }

}
