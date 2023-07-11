package com.cloud.accelarator.stepdefs;

import com.cloud.accelarator.utils.AzureCloudSQLUtils;
import com.cloud.accelarator.utils.AzureStorageUtils;
import com.cloud.accelarator.utils.GenericUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class AzureMySqlStepDef {

    AzureCloudSQLUtils azureCloudSQLUtils = new AzureCloudSQLUtils();
    @Given("Query MySql DB and fetch {string} using SQL {string}")
    public void queryMySqlDb(String columnName, String sqlQuery) throws IOException, SQLException {
        String columnValue = azureCloudSQLUtils.queryTable(sqlQuery,columnName);
        System.out.println(" Column name "+columnName + " Column value "+columnValue);
    }

    @Given("Insert into MySql DB using {string}")
    public void inserDataIntoMySqlDb(String sqlQuery) throws IOException, SQLException {
        azureCloudSQLUtils.insertOrUpdateOrDeleteData(sqlQuery);
    }

    @Given("Update data in MySql DB using {string}")
    public void updateDataIntoMySqlDb(String sqlQuery) throws IOException, SQLException {
        azureCloudSQLUtils.insertOrUpdateOrDeleteData(sqlQuery);
    }

    @Given("Delete data from MySql DB using {string}")
    public void deleteDataFromMySqlDb(String sqlQuery) throws IOException, SQLException {
        azureCloudSQLUtils.insertOrUpdateOrDeleteData(sqlQuery);
    }

}
