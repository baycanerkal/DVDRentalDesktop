package com.dvdrental.com.dvdrental.view;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{

    public Main(){
        setTitle("DVD Rental App");

        init();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(500, 300));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init() {
        JPanel main = (JPanel) getContentPane();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        JComponent musteriPanel = new MusteriPanel();
        tabbedPane.add("Musteri", musteriPanel);

        JComponent dvdPanel = new DVDPanel();
        tabbedPane.add("DVD", dvdPanel);

        main.add(tabbedPane);

    }

}
