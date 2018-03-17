package com.dvdrental.com.dvdrental.view;

import com.dvdrental.com.dvdrental.data.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MusteriFrame extends JFrame{
    private String customerPhoneNumber;
    private JTabbedPane tabbedPane;
    private JComponent kiraladigiDvdlerPanel;
    private JComponent teslimEttigiDvdlerPanel;

    public MusteriFrame(String customerName, String customerPhoneNumber){
        this.customerPhoneNumber = customerPhoneNumber;

        setTitle("Musteri: " +customerName);

        init();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(500,300));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init(){
        JPanel main = (JPanel) getContentPane();

        tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        JComponent dvdKiralaPanel = new DVDRent();
        tabbedPane.add("Dvd Kirala", dvdKiralaPanel);

        kiraladigiDvdlerPanel = getCustomerRentedDvdPanel();
        tabbedPane.add("Kiraladığı DVDler", kiraladigiDvdlerPanel);

        teslimEttigiDvdlerPanel = getCustomerDeliverDvdPanel();
        tabbedPane.add("Teslim Ettigi DVDler", teslimEttigiDvdlerPanel);

        main.add(tabbedPane);
    }

    private JPanel getCustomerRentedDvdPanel(){
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());

        JTable table = new JTable(Database.getInstance().getDVDRented(customerPhoneNumber)){
            private static final long serialVersionUID = 1L;
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };

        JScrollPane sp = new JScrollPane(table);
        main.add(sp, BorderLayout.CENTER);

        main.add(new JButton("Ode"), BorderLayout.PAGE_END);

        return main;
    }

    private JPanel getCustomerDeliverDvdPanel(){
        JPanel main = new JPanel();

        DefaultTableModel defaultTableModel = Database.getInstance().getCustomerDeliveredDVDs(customerPhoneNumber);
        JTable table = new JTable(defaultTableModel);
        JScrollPane sp = new JScrollPane(table);
        main.add(sp);

        return  main;
    }

    private void refreshKiraladigiDVDlerPanel(){
        kiraladigiDvdlerPanel = getCustomerRentedDvdPanel();
        teslimEttigiDvdlerPanel = getCustomerDeliverDvdPanel();

        tabbedPane.remove(1);
        tabbedPane.remove(1);
        tabbedPane.add("Kiraladığı DVDler", kiraladigiDvdlerPanel);
        tabbedPane.add("Teslim Ettigi DVDler", teslimEttigiDvdlerPanel);
    }

    class DVDRent extends JPanel{
        private JTextField movieSearchTf;
        private JComboBox<String> movie;
        private JButton searchBt, rentBt;

        public DVDRent(){
            setLayout(null);

            JLabel movieSearchLabel = new JLabel("Film Adı");
            movieSearchLabel.setBounds(40,30,100,20);
            add(movieSearchLabel);

            movieSearchTf = new JTextField(15);
            movieSearchTf.setBounds(150,30,100,25);
            add(movieSearchTf);

            searchBt = new JButton("Ara");
            searchBt.setBounds(300, 30, 100, 20);
            add(searchBt);

            JLabel movieLabel = new JLabel("Film");
            movieLabel.setBounds(40,75,100,20);
            add(movieLabel);

            movie = new JComboBox<>();
            movie.setBounds(150, 75, 100, 25);
            add(movie);

            rentBt = new JButton("Kirala");
            rentBt.setBounds(300, 75, 100, 20);
            add(rentBt);

            addListeners();
        }

        private void addListeners(){
            searchBt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<String> titles = Database.getInstance().getDVDTitles(movieSearchTf.getText());

                    movie.removeAllItems();

                    for (String title : titles){
                        movie.addItem(title);
                    }
                }
            });

            rentBt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Database.getInstance().addRentDvd((String)movie.getSelectedItem(), customerPhoneNumber);
                    ((MusteriFrame)getTopLevelAncestor()).refreshKiraladigiDVDlerPanel();
                }
            });

        }
    }


}
