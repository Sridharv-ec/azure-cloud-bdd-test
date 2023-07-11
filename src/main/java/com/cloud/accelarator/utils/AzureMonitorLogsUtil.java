package com.cloud.accelarator.utils;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.monitor.query.LogsQueryClient;
import com.azure.monitor.query.LogsQueryClientBuilder;
import com.azure.monitor.query.models.LogsQueryResult;
import com.azure.monitor.query.models.LogsTableRow;
import com.azure.monitor.query.models.QueryTimeInterval;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AzureMonitorLogsUtil {

    public LogsQueryClient getLogsQueryClient(){
        LogsQueryClient logsQueryClient = new LogsQueryClientBuilder()
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();

        return logsQueryClient;
    }

    public List<String> queryLogAnalyticsForContainerCpuUsage(String workspaceid, String query , String columnName){
        List<String> listOfColumnValues = new ArrayList<String>();
        LogsQueryClient logsQueryClient = getLogsQueryClient();
        LogsQueryResult queryResults = logsQueryClient.queryWorkspace("{workspace-id}", "{kusto-query}",
                new QueryTimeInterval(Duration.ofDays(2)));

        for (LogsTableRow row : queryResults.getTable().getRows()) {
            listOfColumnValues.add(String.valueOf(row.getColumnValue("columnName")));
        }
        return listOfColumnValues;
    }
}
