package com.cloud.accelarator.stepdefs;

import com.cloud.accelarator.utils.AzureCloudSQLUtils;
import com.cloud.accelarator.utils.AzurePostgresSQLUtils;
import io.cucumber.java.en.Given;

import java.io.IOException;
import java.sql.SQLException;

public class AzurePostgresSqlStepDef {

    AzurePostgresSQLUtils azurePostgresSQLUtils = new AzurePostgresSQLUtils();
    @Given("Query postgresSql DB and fetch {string} using SQL {string}")
    public void queryPostgresSqlDb(String columnName, String sqlQuery) throws IOException, SQLException {
        String columnValue = azurePostgresSQLUtils.queryTable(sqlQuery,columnName);
        System.out.println(" Column name "+columnName + " Column value "+columnValue);
    }

    @Given("Insert into postgresSql DB using {string}")
    public void inserDataIntoPostgressSqlDb(String sqlQuery) throws IOException, SQLException {
        azurePostgresSQLUtils.insertOrUpdateOrDeleteData(sqlQuery);
    }

    @Given("Update data in postgresSql DB using {string}")
    public void updateDataIntoMySqlDb(String sqlQuery) throws IOException, SQLException {
        azurePostgresSQLUtils.insertOrUpdateOrDeleteData(sqlQuery);
    }

    @Given("Delete data from postgresSql DB using {string}")
    public void deleteDataFromMySqlDb(String sqlQuery) throws IOException, SQLException {
        azurePostgresSQLUtils.insertOrUpdateOrDeleteData(sqlQuery);
    }

}
