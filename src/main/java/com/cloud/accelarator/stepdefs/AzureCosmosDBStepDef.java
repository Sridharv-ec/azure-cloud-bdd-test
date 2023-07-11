package com.cloud.accelarator.stepdefs;

import com.cloud.accelarator.utils.AzureComosDBUtils;
import com.cloud.accelarator.utils.GenericUtils;
import com.cloud.accelarator.utils.cosmosutil.Student;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AzureCosmosDBStepDef {

    AzureComosDBUtils azureComosDBUtils = new AzureComosDBUtils();

    @Given("Create azure cosmos DB {string}")
    public void createCosmosDB(String databaseName) throws Exception {
        String host = GenericUtils.readProps("COSMOS_DB_HOST");
        String key = GenericUtils.readProps("COSMOS_DB_KEY");
        azureComosDBUtils.createDatabaseIfNotExists(host, key, databaseName);

    }

    @Given("Create azure cosmos container {string} in the cosmos DB {string}")
    public void createCosmosContainer(String containerName, String databaseName) throws Exception {
        String host = GenericUtils.readProps("COSMOS_DB_HOST");
        String key = GenericUtils.readProps("COSMOS_DB_KEY");
        azureComosDBUtils.createContainerIfNotExists(host, key,databaseName, containerName);

    }

    @Given("Write data into the cosmos db {string} of conainer {string}")
    public void createStudent(String databaseName, String containerName,DataTable studentDetails) throws Exception {
        String host = GenericUtils.readProps("COSMOS_DB_HOST");
        String key = GenericUtils.readProps("COSMOS_DB_KEY");
        List<Map<String, String>> rows = studentDetails.asMaps(String.class, String.class);
        List<Student> students = new ArrayList<>();
        for (Map<String, String> columns : rows) {
            Student student = new Student();
            student.setId(columns.get("id"));
            student.setPartitionKey(columns.get("partitionKey"));
            student.setFirstName(columns.get("firstName"));
            student.setLastName(columns.get("lastName"));
            student.setCity(columns.get("city"));
            student.setCountry(columns.get("country"));
            students.add(student);
        }
        System.out.println(students);
        azureComosDBUtils.createStudent(host,key,databaseName,containerName,students);

    }
    @Given("Read data for item {string} from azure cosmos DB {string} of cosmos container {string}")
    public void queryStudentIteam(String id, String databaseName, String containerName) throws Exception {
        String host = GenericUtils.readProps("COSMOS_DB_HOST");
        String key = GenericUtils.readProps("COSMOS_DB_KEY");
        azureComosDBUtils.queryStudentItems(host, key, databaseName,containerName,id);
    }

    @Given("Read the items from the container {string} in the cosmos DB {string}")
    public void readStudentItem(String containerName, String databaseName,DataTable studentDetails) throws Exception {
        String host = GenericUtils.readProps("COSMOS_DB_HOST");
        String key = GenericUtils.readProps("COSMOS_DB_KEY");
        List<Map<String, String>> rows = studentDetails.asMaps(String.class, String.class);
        List<Student> students = new ArrayList<>();
        for (Map<String, String> columns : rows) {
            Student student = new Student();
            student.setId(columns.get("id"));
            student.setPartitionKey(columns.get("partitionKey"));
            student.setFirstName(columns.get("firstName"));
            student.setLastName(columns.get("lastName"));
            student.setCity(columns.get("city"));
            student.setCountry(columns.get("country"));
            students.add(student);
        }
        System.out.println(students);
        azureComosDBUtils.readItems(host,key,databaseName,containerName,students);

    }



}
