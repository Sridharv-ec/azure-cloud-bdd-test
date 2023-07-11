package com.cloud.accelarator.stepdefs;

import com.cloud.accelarator.utils.AzureComosDBUtils;
import com.cloud.accelarator.utils.AzureServiceBusUtils;
import com.cloud.accelarator.utils.GenericUtils;
import com.cloud.accelarator.utils.cosmosutil.Student;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AzureServiceBusStepDef {

    AzureServiceBusUtils serviceBusUtils = new AzureServiceBusUtils();

    @Given("Send the message in the queue {string}")
    public void createStudent(String queue, List<String> messages) throws Exception {
        //System.out.println(testData);
        serviceBusUtils.sendMessages(queue,messages);
    }

    @Then("receive message from the queue {string}")
    public void createStudent(String queue) throws Exception {
        //System.out.println(testData);
        serviceBusUtils.receiveMessages(queue);
    }






}
