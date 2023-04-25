/*
Name: Justice Smith
Course: CNT 4714 Spring 2023
Assignment title: Project 3 – A Two-tier Client-Server Application
Date: March 9, 2023,
Class: Enterprise Computing
*/

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Properties;

public class DBConnector extends DefaultTableModel {
    private static Connection conn;
    private static ResultSet rs;
    private static boolean isConnected = false;
    private static int numRows;
    private static int numCol;
    private static Statement statement;

    public DBConnector(Properties props) {
        connect(props);
    }

    public void disconnect() {
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error closing connection");
        }
    }

    public String getConnectionStatus() {
        return isConnected ? "Connected" : "Disconnected";
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void connect(Properties props) {
        String MYSQL_DB_URL = "MYSQL_DB_URL";
        String MYSQL_DB_USERNAME = "MYSQL_DB_USERNAME";
        String MYSQL_DB_PASSWORD = "MYSQL_DB_PASSWORD";
        MysqlDataSource dataSource;

        dataSource = new MysqlDataSource();
        dataSource.setURL(props.getProperty(MYSQL_DB_URL));
        dataSource.setUser(props.getProperty(MYSQL_DB_USERNAME));
        dataSource.setPassword(props.getProperty(MYSQL_DB_PASSWORD));

        try {
            conn = dataSource.getConnection();

            isConnected = true;
            DatabaseMetaData dbmd = conn.getMetaData();
            System.out.println("driver name: " + dbmd.getDriverName());
            System.out.println("driver version: " + dbmd.getDriverVersion());
            System.out.println("driver major version: " + dbmd.getDriverMajorVersion());
            System.out.println("driver minor version: " + dbmd.getDriverMinorVersion());
        } catch (final SQLException sqlEx) {
            sqlEx.printStackTrace();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return conn;
    }

    // set new database query string
    public void select(String query) throws IllegalStateException, SQLException {
        if (!isConnected) throw new IllegalStateException("Not Connected to Database");

        try {
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(query);

            // Move to last row to get num rows from index
            rs.last();
            numRows = rs.getRow();
            numCol = rs.getMetaData().getColumnCount();
            // Reset cursor position
            rs.beforeFirst();
        } catch (final SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

    }

    // set new database update-query string
    public int update(String query) throws IllegalStateException {
        if (!isConnected) throw new IllegalStateException("Not Connected to Database");
        int numRowsUpdated = 0;
        try {
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            numRowsUpdated = statement.executeUpdate(query);
            return numRowsUpdated;
        } catch (final SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return numRowsUpdated;
    }

    public ResultSet getResultSet() {
        return rs;
    }

    @Override
    public int getRowCount() {
        return numRows;
    }

    @Override
    public int getColumnCount() {
        return numCol;
    }

    // get name of a particular column in ResultSet
    // note that columns are one-indexed
    public String getColumnName(int column) throws IllegalStateException {
        try {
            return rs.getMetaData().getColumnName(column);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
}
