package com.dvdrental.com.dvdrental;

import com.dvdrental.com.dvdrental.data.Database;
import com.dvdrental.com.dvdrental.view.Main;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Database.getInstance();
                new Main();
            }
        });
    }
}
