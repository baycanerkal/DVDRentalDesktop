package com.dvdrental.com.dvdrental.view;

import com.dvdrental.com.dvdrental.data.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class NewMusteriFrame extends JFrame{
    private JTextField customerNameTf, phoneNumberTf, emailTf;
    private JButton addCustomerBt;

    public NewMusteriFrame(){
        setTitle("Yeni Musteri Ekle");

        init();
        addListeners();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(500, 300));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init(){
        setLayout(null);

        JLabel customerNameLabel = new JLabel("Adı");
        customerNameLabel.setBounds(40,30,100,20);
        add(customerNameLabel);

        customerNameTf = new JTextField(15);
        customerNameTf.setBounds(150,30,100,25);
        add(customerNameTf);

        JLabel phoneNumberLabel = new JLabel("Tel No");
        phoneNumberLabel.setBounds(40,75,100,20);
        add(phoneNumberLabel);

        phoneNumberTf = new JTextField(15);
        phoneNumberTf.setBounds(150, 75, 100, 25);
        add(phoneNumberTf);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(40,120, 100, 20);
        add(emailLabel);

        emailTf = new JTextField(15);
        emailTf.setBounds(150, 120, 100, 25);
        add(emailTf);

        addCustomerBt = new JButton("Ekle");
        addCustomerBt.setBounds(30,165, 85, 35);
        add(addCustomerBt);
    }

    private void addListeners(){
        addCustomerBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Database db = Database.getInstance();

                try {
                    db.addCustomer(customerNameTf.getText(), phoneNumberTf.getText(), emailTf.getText());
                    JOptionPane.showMessageDialog(NewMusteriFrame.this, "Musteri Eklendi", "Basarili", JOptionPane.PLAIN_MESSAGE);
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(NewMusteriFrame.this,
                            "Musteri zaten kayitli","Basarisiz",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
