package com.ivo.lab5_1_ai;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class Renderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row, int column)
    {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        String cellValue = value.toString();

        if (cellValue.equals("  ")) {
            cell.setBackground(Color.GREEN);
        } else {
            cell.setBackground(table.getBackground());
        }

        return cell;
    }
}
