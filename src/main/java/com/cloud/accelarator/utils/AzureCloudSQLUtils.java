package com.cloud.accelarator.utils;

import java.io.IOException;
import java.sql.*;

public class AzureCloudSQLUtils {


    public static Connection getMySqlConnection() throws IOException, SQLException {
        String url = GenericUtils.readProps("MYSQL_DB_URL");
        String userName = GenericUtils.readProps("MYSQL_USERNAME");
        String password = GenericUtils.readProps("MYSQL_PASSWORD");
        return DriverManager.getConnection(url, userName, password);
    }

    public String queryTable(String sql, String fieldName) throws IOException, SQLException {
        Connection conn = getMySqlConnection();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()) {
            return resultSet.getString(fieldName);
        }else {
            return null;
        }
    }

    public void insertOrUpdateOrDeleteData(String sql) throws SQLException, IOException {
        Connection conn = getMySqlConnection();
        Statement statement = conn.createStatement();
        int row = statement.executeUpdate(sql);
        if(row > 0) {
            System.out.println("Row inserted / updated");
        }else {
            System.out.println("Row not inserted / updated");
        }
    }

}
