package com.cloud.accelarator.utils;

import com.azure.cosmos.*;
import com.azure.cosmos.implementation.RequestChargeTracker;
import com.azure.cosmos.models.*;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.cloud.accelarator.utils.cosmosutil.Student;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class AzureComosDBUtils {



  //  private final String databaseName = "ToDoList";
//    private final String containerName = "Items";

    private CosmosDatabase database;
    private CosmosContainer container;

//    public void close() {
//        client.close();
//    }

    public CosmosClient getCosmosConnection(String host, String key) {
        CosmosClient client = new CosmosClientBuilder()
                .endpoint(host)
                .key(key)
//                .preferredRegions("East US")
//                .userAgentSuffix("CosmosDBJavaQuickstart")
                .consistencyLevel(ConsistencyLevel.EVENTUAL)
                .buildClient();
        return client;
    }

    public CosmosDatabase createDatabaseIfNotExists(String host, String key, String databaseName) throws Exception {
        System.out.println("Create database " + databaseName + " if not exists.");
        CosmosClient client = getCosmosConnection(host, key);

        //  Create database if not exists
        CosmosDatabaseResponse databaseResponse = client.createDatabaseIfNotExists(databaseName);
        database = client.getDatabase(databaseResponse.getProperties().getId());

        System.out.println("Checking database " + database.getId() + " completed!\n");
        return database;
    }

    public CosmosContainer createContainerIfNotExists(String host, String key, String databaseName, String containerName) throws Exception {
        System.out.println("Create container " + containerName + " if not exists.");
        CosmosDatabase database = createDatabaseIfNotExists(host,key,databaseName);
        //  Create container if not exists
        CosmosContainerProperties containerProperties =
                new CosmosContainerProperties(containerName, "/partitionKey");

        CosmosContainerResponse containerResponse = database.createContainerIfNotExists(containerProperties);
        container = database.getContainer(containerResponse.getProperties().getId());

        System.out.println("Checking container " + container.getId() + " completed!\n");
        return container;
    }

    private void scaleContainer(String host, String key, String databaseName,String containerName) throws Exception {
        System.out.println("Scaling container " + containerName + ".");
        CosmosContainer container = createContainerIfNotExists(host, key,databaseName, containerName);

        try {
            // You can scale the throughput (RU/s) of your container up and down to meet the needs of the workload. Learn more: https://aka.ms/cosmos-request-units
            ThroughputProperties currentThroughput = container.readThroughput().getProperties();
            int newThroughput = currentThroughput.getManualThroughput() + 100;
            container.replaceThroughput(ThroughputProperties.createManualThroughput(newThroughput));
            System.out.println("Scaled container to " + newThroughput + " completed!\n");
        } catch (CosmosException e) {
            if (e.getStatusCode() == 400)
            {
                System.err.println("Cannot read container throuthput.");
                System.err.println(e.getMessage());
            }
            else
            {
                throw e;
            }
        }
    }

    public void createStudent(String host, String key, String databaseName, String containerName, List<Student> students) throws Exception {
        double totalRequestCharge = 0;
        CosmosContainer container = createContainerIfNotExists(host, key,databaseName,containerName);
        for (Student student : students) {

            //  Create item using container that we created using sync client

            //  Using appropriate partition key improves the performance of database operations
            CosmosItemResponse item = container.createItem(student, new PartitionKey(student.getPartitionKey()), new CosmosItemRequestOptions());

            //  Get request charge and other properties like latency, and diagnostics strings, etc.
            System.out.println(String.format("Created item with request charge of %.2f within" +
                            " duration %s",
                    item.getRequestCharge(), item.getDuration()));
            totalRequestCharge += item.getRequestCharge();
        }

        System.out.println(String.format("Created %d items with total request " +
                        "charge of %.2f",
                students.size(),
                totalRequestCharge));
    }


    public void readItems(String host, String key, String databaseName, String containerName, List<Student> students) throws Exception {
        //  Using partition key for point read scenarios.
        //  This will help fast look up of items because of partition key
        CosmosContainer container = createContainerIfNotExists(host, key,databaseName,containerName);
        students.forEach(student -> {
            try {
                CosmosItemResponse<Student> item = container.readItem(student.getId(), new PartitionKey(student.getPartitionKey()), Student.class);
                double requestCharge = item.getRequestCharge();
                String activityId = item.getSessionToken();
                Duration requestLatency = item.getDuration();
                System.out.println(String.format("Item successfully read with id %s with a charge of %.2f and within duration %s and status code %s",
                        item.getItem().getId(), requestCharge, requestLatency,activityId));
                System.out.println("First Name: "+item.getItem().getFirstName() + "Last Name: "+item.getItem().getLastName());
            } catch (CosmosException e) {
                e.printStackTrace();
                System.err.println(String.format("Read Item failed with %s", e));
            }
        });
    }

    public void queryStudentItems(String host, String key, String databaseName, String containerName, String id) throws Exception {
        // Set some common query options
        int preferredPageSize = 1;
        CosmosContainer container = createContainerIfNotExists(host, key,databaseName,containerName);

        CosmosQueryRequestOptions queryOptions = new CosmosQueryRequestOptions();
        //  Set populate query metrics to get metrics around query executions
        queryOptions.setQueryMetricsEnabled(true);

        CosmosPagedIterable<Student> studentsPagedIterable = container.queryItems(
                "SELECT * FROM c WHERE c.id = '"+id+"'", queryOptions, Student.class);

        studentsPagedIterable.iterableByPage(preferredPageSize).forEach(cosmosItemPropertiesFeedResponse -> {
            System.out.println("Got a page of query result with " +
                    cosmosItemPropertiesFeedResponse.getResults().size() + " items(s)"
                    + " and request charge of " + cosmosItemPropertiesFeedResponse.getRequestCharge());

            System.out.println("Item Ids " + cosmosItemPropertiesFeedResponse
                    .getResults()
                    .stream()
                    .map(Student::getId)
                    .collect(Collectors.toList()));

            System.out.println("First Name " + cosmosItemPropertiesFeedResponse
                    .getResults()
                    .stream()
                    .map(Student::getFirstName)
                    .collect(Collectors.toList()));

            System.out.println("Last Name " + cosmosItemPropertiesFeedResponse
                    .getResults()
                    .stream()
                    .map(Student::getLastName)
                    .collect(Collectors.toList()));

            System.out.println("Partition Key " + cosmosItemPropertiesFeedResponse
                    .getResults()
                    .stream()
                    .map(Student::getPartitionKey)
                    .collect(Collectors.toList()));

            System.out.println("Citi " + cosmosItemPropertiesFeedResponse
                    .getResults()
                    .stream()
                    .map(Student::getCity)
                    .collect(Collectors.toList()));

            System.out.println("Country " + cosmosItemPropertiesFeedResponse
                    .getResults()
                    .stream()
                    .map(Student::getCountry)
                    .collect(Collectors.toList()));
        });
    }

}
