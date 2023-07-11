package com.cloud.accelarator.utils;

import java.io.IOException;
import java.sql.*;

public class AzurePostgresSQLUtils {

    public static Connection getPostGresSqlConnection() throws IOException, SQLException {
        String url = GenericUtils.readProps("POSTGRES_SQL_DB_URL");
        return DriverManager.getConnection(url);
    }

    public void insertOrUpdateOrDeleteData(String sql) throws SQLException, IOException {
        Connection conn = getPostGresSqlConnection();
        Statement statement = conn.createStatement();
        int row = statement.executeUpdate(sql);
        if(row > 0) {
            System.out.println("Row inserted / updated");
        }else {
            System.out.println("Row not inserted / updated");
        }
    }

    public String queryTable(String sql, String fieldName) throws IOException, SQLException {
        Connection conn = getPostGresSqlConnection();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()) {
            return resultSet.getString(fieldName);
        }else {
            return null;
        }
    }
}
