package com.dvdrental.com.dvdrental.view;

import com.dvdrental.com.dvdrental.data.Database;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DVDPanel extends JPanel{
    JTextField titleTf, yearTf, genreTf;
    JCheckBox dubbingCb, subtitleCb;
    JButton addDvdBt, rentDvdBt, allDvdsBt;

    public DVDPanel(){
        setLayout(null);

        JLabel customerNameLabel = new JLabel("Adı");
        customerNameLabel.setBounds(40,30,100,20);
        add(customerNameLabel);

        titleTf = new JTextField(15);
        titleTf.setBounds(150,30,100,25);
        add(titleTf);

        JLabel dubbingLabel = new JLabel("Dublaj");
        dubbingLabel.setBounds(300, 30, 100, 20);
        add(dubbingLabel);

        dubbingCb = new JCheckBox();
        dubbingCb.setBounds(350, 30, 20,20);
        add(dubbingCb);

        JLabel yearLabel = new JLabel("Yıl");
        yearLabel.setBounds(40,75,100,20);
        add(yearLabel);

        yearTf = new JTextField(15);
        yearTf.setBounds(150, 75, 100, 25);
        add(yearTf);

        JLabel subtitleLabel = new JLabel("Altyazi");
        subtitleLabel.setBounds(300, 75, 100, 20);
        add(subtitleLabel);

        subtitleCb = new JCheckBox();
        subtitleCb.setBounds(350, 75, 20,20);
        add(subtitleCb);

        JLabel genreLabel = new JLabel("Tür");
        genreLabel.setBounds(40,120,100,20);
        add(genreLabel);

        genreTf = new JTextField(15);
        genreTf.setBounds(150, 120, 100,25);
        add(genreTf);

        addDvdBt = new JButton("Ekle");
        addDvdBt.setBounds(40, 165, 60,35);
        add(addDvdBt);

        rentDvdBt = new JButton("Kiradaki DVDler");
        rentDvdBt.setBounds(120, 165, 150, 35);
        add(rentDvdBt);

        allDvdsBt = new JButton("Tüm DVDler");
        allDvdsBt.setBounds(290, 165, 120, 35);
        add(allDvdsBt);

        addListeners();
    }

    private void addListeners(){
        addDvdBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleTf.getText();
                String genre = genreTf.getText();
                int year = Integer.parseInt(yearTf.getText());
                int dubbing = dubbingCb.isSelected() ? 1 : 0;
                int subtitle = subtitleCb.isSelected() ? 1 : 0;

                try {
                    Database.getInstance().addDVD(title, year, genre, dubbing, subtitle);
                    JOptionPane.showMessageDialog(DVDPanel.this, "DVD Eklendi","Başarılı",JOptionPane.PLAIN_MESSAGE);
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(DVDPanel.this, "Zaten Var","Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        allDvdsBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AllDvdFrame();
            }
        });

        rentDvdBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AllRentedDvdFrame();
            }
        });
    }
}
