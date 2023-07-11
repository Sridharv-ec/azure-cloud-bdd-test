package com.cloud.accelarator.stepdefs;

import com.cloud.accelarator.utils.AzureMonitorLogsUtil;
import com.cloud.accelarator.utils.GenericUtils;
import io.cucumber.java.en.Given;

public class AzureMonitorLogsStepDef {

    AzureMonitorLogsUtil azureMonitorLogsUtil = new AzureMonitorLogsUtil();


    @Given("Query container CPU for column {string}")
    public void createCosmosDB(String columnName) throws Exception {
        String workspaceId = GenericUtils.readProps("WORKSPACE_ID");
        String containerCpuQuery = GenericUtils.readProps("CONTAINER_CPU_QUERY");
        azureMonitorLogsUtil.queryLogAnalyticsForContainerCpuUsage(workspaceId,containerCpuQuery,columnName);

    }


}
