package com.dvdrental.com.dvdrental.view;

import com.dvdrental.com.dvdrental.data.Database;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MusteriPanel extends JPanel {
    private JTextField customerNameTf;
    private JTextField customerPhoneNumberTf;
    private JButton sendBt, newCustomerBt;

    public MusteriPanel(){
        setLayout(null);

        JLabel customerNameLabel = new JLabel("Adı");
        customerNameLabel.setBounds(40,30,100,20);
        add(customerNameLabel);

        customerNameTf = new JTextField(15);
        customerNameTf.setBounds(150,30,100,25);
        add(customerNameTf);

        JLabel customerPhoneNumberLabel = new JLabel("Tel No");
        customerPhoneNumberLabel.setBounds(40,75,100,20);
        add(customerPhoneNumberLabel);

        customerPhoneNumberTf = new JTextField(10);
        customerPhoneNumberTf.setBounds(150,75,100,25);
        add(customerPhoneNumberTf);

        sendBt = new JButton("Giris");
        sendBt.setBounds(30,130, 85, 35);
        add(sendBt);

        newCustomerBt = new JButton("Yeni Musteri");
        newCustomerBt.setBounds(140, 130, 115, 35);
        add(newCustomerBt);

        addListeners();
    }

    private void addListeners(){
        sendBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customerName = customerNameTf.getText();
                String customerPhoneNumber = customerPhoneNumberTf.getText();

                Database database = Database.getInstance();
                if(database.getCustomer(customerName, customerPhoneNumber)){
                    new MusteriFrame(customerName, customerPhoneNumber);
                }else {
                    JOptionPane.showMessageDialog(MusteriPanel.this,
                            "Kayıt Bulunamadı","Kayıt Yok",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        newCustomerBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewMusteriFrame();
            }
        });
    }
}
