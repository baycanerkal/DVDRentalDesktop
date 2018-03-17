package com.dvdrental.com.dvdrental.view;

import com.dvdrental.com.dvdrental.data.Database;

import javax.swing.*;
import java.awt.*;

public class AllRentedDvdFrame extends JFrame {

    public AllRentedDvdFrame(){
        setTitle("Kiradaki DVDler");

        init();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(500, 300));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init(){
        JPanel main = (JPanel)getContentPane();

        JTable table = new JTable(Database.getInstance().getRentedDvds());
        JScrollPane sp = new JScrollPane(table);

        main.add(sp);
    }
}
