package com.ivo.lab5_1_ai;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class Hopfield {

    private Renderer renderer = new Renderer();
    private JTable table;
    private TableModel tm;
    private TableColumn tc;
    private DefaultTableModel dtm;
    private JPanel panel;
    private JScrollPane pane;
    private JFrame frame;
    private JPanel panelBackground;
    private JTextArea area;
    private String image[][];
    private LinkedList<int[][]> forms;
    private int sizeListForms = -1;
    private int numOfForms = 0;
    private int countOfForms = 0;

    private static final int sizeX = 7;
    private static final int sizeY = 7;

    public Hopfield() {
        image = new String[sizeX][sizeY];
        forms = new LinkedList<>();

        // the window
        frame = new JFrame("Neural Network of Hopfield");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(553, 470);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setBackground(Color.GRAY);

        // create back panel
        panelBackground = new JPanel();
        panelBackground.setSize(553, 470);
        panelBackground.setLayout(null);
        frame.add(panelBackground);

        // table
        JTable table = new JTable();
        // create table
        createDefaultTableModel();
        // set size height/width for cells table
        setSizeTable();
        // create panel
        createPanel(new Rectangle(20, 20, 295, 295));
        // create panel for buttons
        JPanel panel1 = new JPanel();
        panel1.setBounds(330, 20, 200, 200);
        panel1.setLayout(null);

        // create buttons
        JButton buttonAdd = new JButton("Add image");
        buttonAdd.setBackground(Color.LIGHT_GRAY);
        buttonAdd.setBounds(15, 5, 180, 25);

        JButton buttonCount = new JButton("Look all images");
        buttonCount.setBackground(Color.LIGHT_GRAY);
        buttonCount.setBounds(15, 35, 180, 25);

        JButton buttonDelete = new JButton("Delete all images");
        buttonDelete.setBounds(15,65,180,25);
        buttonDelete.setBackground(Color.LIGHT_GRAY);

        JButton buttonFind = new JButton("Identify image");
        buttonFind.setBounds(15,95,180,25);
        buttonFind.setBackground(Color.LIGHT_GRAY);

        JButton buttonClean = new JButton("Clear field");
        buttonClean.setBounds(15,125,180,25);
        buttonClean.setBackground(Color.LIGHT_GRAY);

        // add listeners for button
        buttonAdd.addActionListener(new addButton());
        panel1.add(buttonAdd);
        buttonCount.addActionListener(new countButton());
        panel1.add(buttonCount);
        buttonDelete.addActionListener(new deleteButton());
        panel1.add(buttonDelete);
        buttonFind.addActionListener(new checkButton());
        panel1.add(buttonFind);
        buttonClean.addActionListener(new cleanButton());
        panel1.add(buttonClean);

        panelBackground.add(panel1);
        area = new JTextArea();
        area.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1, false));
        area.setBounds(20, 320, 295, 80);
        area.setText("Neural Network of Hopfield" + "\n"
            + "Operation mode: synchronous" + "\n"
            + "Size image: " + sizeX + "x" + sizeY + "\n"
            + "Activation function: hyperbolic tangent");
        area.setEditable(false);
        panelBackground.add(area);
        frame.setVisible(true);
    }

    // button add new image
    private class addButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int matr[][] = new int[sizeX][sizeY];
            sizeListForms++;
            numOfForms++;

            for (int i = 0; i < sizeX; i++) {
                for (int j = 0; j < sizeY; j++) {
                    if (table.getValueAt(i, j).toString().equals("  ")) {
                        matr[i][j] = 1;
                    } else {
                        matr[i][j] = -1;
                    }
                }
            }
            forms.add(sizeListForms, matr);
            area.setText("");
            area.setText("Image was added" + "\n"
                + "Total images: " + numOfForms);
        }
    }

    // button view total image
    private class countButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (forms.isEmpty()) {
                area.setText("List of images empty, add images");
            }
            else {
                int[][] matr;
                if (countOfForms == numOfForms) {
                    countOfForms = 0;
                }
                matr = forms.get(countOfForms);
                for (int i = 0; i < sizeX; i++) {
                    for (int j = 0; j < sizeY; j++) {
                        if (matr[i][j] == 1) {
                            table.setValueAt("  ", i, j);
                        } else {
                            table.setValueAt(" ", i, j);
                        }
                    }
                }
                // drawing
                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setCellRenderer(renderer);
                }
                countOfForms++;
                area.setText("Image № " + countOfForms + " (of " + numOfForms + ")");
            }
        }
    }

    // button clear field
    private class cleanButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            area.setText("");
            for (int i = 0; i < sizeX; i++) {
                for (int j = 0; j < sizeY; j++) {
                    image[i][j] = " ";
                    table.setValueAt(image[i][j], i, j);
                }
            }
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(renderer);
            }
        }
    }

    // button delete all images
    private class deleteButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            forms.clear();
            sizeListForms = -1;
            countOfForms = 0;
            numOfForms = 0;

            for (int i = 0; i < sizeX; i++) {
                for (int j = 0; j < sizeY; j++) {
                    image[i][j] = " ";
                    table.setValueAt(image[i][j], i, j);
                }
            }
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(renderer);
            }
            area.setText("All images have been deleted");
        }
    }

    // button find image
    private class checkButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (numOfForms == 0) {
                area.setText("List of images empty. Add image.");
            }
            else {
                area.setText("Checking...");
                // translating arrays to vectors
                LinkedList<int[][]> listVectors = new LinkedList<>();
                for (int i = 0; i < numOfForms; i++) {
                    listVectors.add(i, toVector(forms.get(i)));
                }
                // transpose matrix
                LinkedList<int[][]> listTransposeVectors = new LinkedList<>();
                for (int i = 0; i < numOfForms; i++) {
                    listTransposeVectors.add(i, transpose(listVectors.get(i)));
                }
                // multiply transpose vector x vector
                LinkedList<int[][]> listProduct = new LinkedList<>();
                for (int i = 0; i < numOfForms; i++) {
                    listProduct.add(i, product(listTransposeVectors.get(i), listVectors.get(i)));
                }
                // summarize all multiplication results
                int[][] W = new int[sizeX * sizeY][sizeX * sizeY];
                for (int i = 0; i < sizeX * sizeY; i++) {
                    for (int j = 0; j < sizeX * sizeY; j++) {
                        W[i][j] = 0;
                    }
                }
                for (int i = 0; i < numOfForms; i++) {
                    W = sum(W, listProduct.get(i));
                }
                // Reset the diagonal (or set zerow in the diagonal)
                for (int i = 0; i < sizeX * sizeY; i++) {
                    for (int j = 0; j < sizeX * sizeY; j++) {
                        if (i == j) W[i][j] = 0;
                    }
                }
                // check the vector
                int[][] matr = new int[sizeX][sizeY];

                for (int i = 0; i < sizeX; i++) {
                    for (int j = 0; j < sizeY; j++) {
                        if (table.getValueAt(i, j).toString().equals("  ")) {
                            matr[i][j] = 1;
                        } else {
                            matr[i][j] = -1;
                        }
                    }
                }
                int[][] vect = toVector(matr);
                // transpose the vector
                int[][] transpVector = transpose(vect);
                // multiply the matrix of weights with the trans vector
                int[][] y = product(W, transpVector);
                // use the activation function
                int[][] res;

                res = activity(y);
                // check if the final vector matches the standards
                boolean isTrue = false;
                int toPercent = 0;
                for (int i = 0; i < numOfForms; i++) {
                    if (toEquals(res, listTransposeVectors.get(i))) {
                        isTrue = true;
                        toPercent = inPercentages(listTransposeVectors.get(i), transpVector);
                        area.setText("The distorted image is similar to " + (i+1)
                                + " image" + "\n"
                                + "The distortion level: " + (toPercent) + "%" + "\n");
                        break;
                    }
                }
                if (!isTrue) {
                    while (toEquals(y, res)) {
                        y = product(W, res);
                        res = activity(y);
                    }
                    // when the input and output match, check the result with the standards
                    for (int i = 0; i < numOfForms; i++) {
                        if (toEquals(res, listTransposeVectors.get(i))) {
                            isTrue = true;
                            toPercent = inPercentages(listTransposeVectors.get(i), transpVector);
                            area.setText("The distorted image is similar to " + (i+1)
                                    + " image" + "\n"
                                    + "The distortion level: " + (toPercent) + "%" + "\n");
                            break;
                        } else {
                            area.setText("The similiar isn't found!");
                        }
                    }
                }
            }
        }
    }

    private boolean toEquals(int[][] mas1, int[][] mas2){
        for (int i = 0; i < sizeX * sizeY; i++) {
            if (mas1[i][0] != mas2[i][0]) {
                return false;
            }
        }
        return true;
    }

    // Compare the distorted image and the found reference image in percent
    private int inPercentages(int[][] cool, int[][] distorted){
        int count = 0;
        for (int i = 0; i < sizeX * sizeY; i++) {
            if (cool[i][0] != distorted[i][0]) {
                count++;
            }
        }
        double res = (100 * count) / (double)(sizeX * sizeY);
        int result = (int) Math.round(res);
        return result;
    }

    // activation function
    private int[][] activity(int[][] mas) {
        int[][] res = new int[sizeX * sizeY][1];
        for(int i = 0; i < sizeX * sizeY; i++){
            double d= (( Math.pow(Math.E,Double.parseDouble(String.valueOf(mas[i][0]))) -
                    Math.pow(Math.E,-Double.parseDouble(String.valueOf(mas[i][0]))) ) /
                    ( Math.pow(Math.E,Double.parseDouble(String.valueOf(mas[i][0]))) +
                            Math.pow(Math.E,-Double.parseDouble(String.valueOf(mas[i][0])))));
            d = Math.round(d);
            res[i][0] = (int)d;
        }
        return res;
    }

    // summarize matrix
    private int[][] sum(int[][] matr1, int[][] matr2) {
        int[][] res = new int[sizeX * sizeY][sizeX * sizeY];
        for (int i = 0; i < sizeX * sizeY; i++) {
            for (int j = 0; j < sizeX * sizeY; j++) {
                res[i][j] = matr1[i][j] + matr2[i][j];
            }
        }
        return res;
    }

    // multiply two arrays (or vectors)
    private int[][] product(int[][] matr1, int[][] matr2) {
        int[][] prod = new int[matr1.length][matr2[0].length];
        for (int i = 0; i < matr1.length; i++) {
            for (int j = 0; j < matr2[0].length; j++) {
                for (int k = 0; k < matr2.length; k++) {
                    prod[i][j] += matr1[i][k] * matr2[k][j];
                }
            }
        }
        return prod;
    }

    // translate a duma array into a vector
    private int[][] toVector(int mas[][]) {
        int[][] vector = new int[1][sizeX * sizeY];
        int count = 0;
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                vector[0][count] = mas[i][j];
                count++;
            }
        }
        return vector;
    }

    // transposition of a vector
    private int[][] transpose(int vector[][]) {
        int[][] transp = new int[sizeX * sizeY][1];
        for (int i = 0; i < sizeX * sizeY; i++) {
            transp[i][0] = vector[0][i];
        }
        return transp;
    }


    private void createPanel(Rectangle rectangle) {
        panel = new JPanel();
        pane = new JScrollPane(panel);
        createPane();
        pane.setBounds(rectangle);
        panelBackground.add(pane);
    }

    // this method contain rendering
    private void createPane() {
        // create panel with scroll
        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1, false));
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                image[i][j] = " ";
                table.setValueAt(image[i][j], i, j);
            }
        }
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = table.getSelectionModel().getLeadSelectionIndex();
                int y = table.getColumnModel().getSelectionModel().getLeadSelectionIndex();

                if (image[x][y].equals(" ")) {
                    image[x][y] = "  ";
                } else {
                    image[x][y] = " ";
                }

                for (int i = 0; i < sizeX; i++) {
                    for (int j = 0; j < sizeY; j++) {
                        table.setValueAt(image[i][j], i, j);
                    }
                }
                for (int i = 0; i < table.getColumnCount(); i++) {
                    table.getColumnModel().getColumn(i).setCellRenderer(renderer);
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        panel.add(table);
    }

    // settings table
    private void setSizeTable() {
        table.setRowHeight(40);
        // sizeX x sizeY
        for (int i = 0; i < tm.getColumnCount(); i++) {
            tc = table.getColumnModel().getColumn(i);
            tc.setPreferredWidth(40);
        }
    }

    private void createDefaultTableModel() {
        dtm = new DefaultTableModel(sizeX, sizeY);
        table = new JTable(dtm) {
            @Override
            public boolean isCellEditable(int arg0, int arg1) {
                return false;
            }
        };
        table.isCellEditable(sizeX, sizeY);
        table.setCellSelectionEnabled(false);
        tm = table.getModel();
    }
}
