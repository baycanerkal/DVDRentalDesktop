package com.dvdrental.com.dvdrental.data;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Database {
    private static Database instance = new Database();

    private String jdbcUrl = "jdbc:mysql://localhost/dvdrentalstore";
    private String username = "wissen";
    private String password = "akademie";

    private Connection conn;
    private Statement stmt;

    private Database(){
        String driver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connection Established");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Database getInstance(){
        return instance;
    }

    public boolean getCustomer(String name, String phone_number){
        boolean result = false;

        try {
            stmt = conn.createStatement();
            String sql = String.format("{CALL getCustomerByNamePhoneNumberProc(\"%s\", \"%s\")}",
                    name, phone_number);
            ResultSet resultSet = stmt.executeQuery(sql);
            result = resultSet.next();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void addCustomer(String name, String phone_number, String email) throws SQLException {

        Statement stmt = conn.createStatement();
        String sql = String.format("{CALL addCustomerProc(\"%s\", \"%s\", \"%s\")}", name, phone_number, email);
        stmt.execute(sql);

        stmt.close();
    }

    public void addDVD(String title, int year, String genre, int dubbing, int subtitle) throws SQLException {
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = String.format("{CALL addDVDProc(\"%s\",%d,\"%s\",%d,%d)}",
                title, year, genre, dubbing, subtitle);

        stmt.execute(sql);
        stmt.close();
    }

    public ArrayList<String> getDVDTitles(String word){
        ArrayList<String> titles = new ArrayList<>();

        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = String.format("{CALL getDVDTitleByWord(\"%s\")}", word);

        try {
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()){
                titles.add(resultSet.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  titles;
    }

    public void addRentDvd(String title, String phone_number){
        try {
            stmt = conn.createStatement();
            String sql = String.format("{CALL addDVDRentedProc(\"%s\", \"%s\")}", title, phone_number);
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DefaultTableModel getCustomerDeliveredDVDs(String phone_number){
        String sql = String.format("{CALL getCustomerDeliverDVDByPhoneNumberProc(\"%s\")}", phone_number);
        DefaultTableModel defaultTableModel = getTable(sql);

        return defaultTableModel;
    }

    public DefaultTableModel getAllDvds(){
        String sql = String.format("{CALL getAllDVDProc()}");
        DefaultTableModel defaultTableModel = getTable(sql);

        return defaultTableModel;
    }

    public DefaultTableModel getRentedDvds(){
        String sql = String.format("{CALL getDVDRentedProc()}");
        DefaultTableModel defaultTableModel = getTable(sql);

        return defaultTableModel;
    }

    public DefaultTableModel getTable(String sql) {
        DefaultTableModel dtm = null;

        try
        {
            Statement sorgu = conn.createStatement();
            ResultSet rs = sorgu.executeQuery(sql);

            ResultSetMetaData md = rs.getMetaData();

            String kolonAdlari[] = new String[md.getColumnCount()];

            for (int i = 0; i<kolonAdlari.length; i++)
                kolonAdlari[i] = md.getColumnLabel(i+1);

            dtm = new DefaultTableModel(kolonAdlari, 0);

            while (rs.next())
            {
                Object row[] = new Object[kolonAdlari.length];
                for (int i = 0; i<row.length; i++)
                    row[i] = rs.getString(i+1);

                dtm.addRow(row);
            }

            rs.close();
            sorgu.close();
        } catch (Exception e) { e.printStackTrace(); }

        return dtm;
    }

    public DefaultTableModel getDVDRented(String phone_number){
        DefaultTableModel defaultTableModel = null;

        try {
            stmt = conn.createStatement();
            String sql = String.format("{CALL getDVDRentedByCustomerPhoneNumberProc(\"%s\")}", phone_number);
            ResultSet resultSet = stmt.executeQuery(sql);

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            String[] columnNames = new String[resultSetMetaData.getColumnCount()+2];

            int i=0;
            for (; i<columnNames.length - 2; i++){
                columnNames[i] = resultSetMetaData.getColumnLabel(i+1);
            }

            columnNames[i++] = "Ucret";
            columnNames[i] = "Odendi";

            defaultTableModel = new DefaultTableModel(columnNames, 0);

            Date rentedDate = null;
            long diff, days, currentDate = Calendar.getInstance().getTime().getTime();
            while (resultSet.next()){
                Object[] data = new Object[4];
                data[0] = resultSet.getString(1);
                data[1] = resultSet.getTimestamp(2);
                rentedDate =(Date) data[1];
                diff = currentDate - rentedDate.getTime();
                days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                days = days == 0 ? 1 : days;
                System.out.println(days);
                data[2] = (5 * days) + " TL";
                data[3] = false;
                defaultTableModel.addRow(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return defaultTableModel;
    }
}
