package com.cloud.accelarator.stepdefs;

import com.cloud.accelarator.utils.AzureStorageUtils;
import com.cloud.accelarator.utils.GenericUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AzureStorageAccountsStepDef {

    AzureStorageUtils azureStorageUtils = new AzureStorageUtils();
    @Given("Create azure storage container {string}")
    public void createBlobContainerStep(String containerName) throws IOException {
        String connectionString = GenericUtils.readProps("STORAGE_ACCOUNT_CONNECTION_STRING");
        azureStorageUtils.createBlobContainer(connectionString, containerName);
    }

//    @Then("Create bucket {string}")
//    public void createBucket(String bucketName) {
//        CloudStorageUtils.createBucket(projectId, bucketName);
//    }
//
//    @Then("Delete bucket {string}")
//    public void deleteBucket(String bucketName) {
//        CloudStorageUtils.deleteBucket(projectId, bucketName);
//    }
//
    @Then("Upload {string} blob Object into {string} container")
    public void uploadObjectIntoContainer(String objectName, String containerName) throws IOException {
        Path userDir = Paths.get(System.getProperty("user.dir"));
        String filePath = "src/test/resources/data/" + objectName;
        String connectionString = GenericUtils.readProps("STORAGE_ACCOUNT_CONNECTION_STRING");
        System.out.println( " bucketName: " + objectName + " filePath:  " + filePath);
        azureStorageUtils.uploadBlobObject( connectionString, containerName, filePath);
    }

    @Then("download blob object {string} from the {string} container")
    public void downLoadObjectIntoContainer(String objectName, String containerName) throws IOException {
        Path userDir = Paths.get(System.getProperty("user.dir"));
        String filePath = "src/test/resources/data/" + objectName;
        String connectionString = GenericUtils.readProps("STORAGE_ACCOUNT_CONNECTION_STRING");
        System.out.println( " Blob Object Name: " + objectName + " filePath:  " + filePath);
        azureStorageUtils.downlaodBlobObject( connectionString, containerName, filePath);
    }

    @Then("delete blob container {string}")
    public void deleteBlobContainer(String containerName) throws IOException {
        String connectionString = GenericUtils.readProps("STORAGE_ACCOUNT_CONNECTION_STRING");
        azureStorageUtils.deleteBlobContainer(connectionString,containerName);
    }

    @Given("Create azure file share {string}")
    public void createFileShare(String targetBucketName) throws IOException {
        String connectionString = GenericUtils.readProps("STORAGE_ACCOUNT_CONNECTION_STRING");
        azureStorageUtils.createFileShare(connectionString, targetBucketName);
    }

    @Then("Upload {string} file into {string} file share")
    public void uploadFileIntoFileShare(String fileName, String fileShare) throws IOException {
        Path userDir = Paths.get(System.getProperty("user.dir"));
        String directoryPath = "/src/test/resources/data/";
        String connectionString = GenericUtils.readProps("STORAGE_ACCOUNT_CONNECTION_STRING");
        System.out.println( " file name : " + fileName + " filePath:  " + directoryPath + " File share : "+fileShare);
        azureStorageUtils.uploadFile( connectionString,fileShare, "", userDir + directoryPath + fileName);
    }

    @Then("download file {string} from the {string} file share")
    public void downloadFileFromFileShare(String fileName, String fileShare) throws IOException {
        Path userDir = Paths.get(System.getProperty("user.dir"));
        //String directoryPath = userDir + "/src/test/resources/data";
        String connectionString = GenericUtils.readProps("STORAGE_ACCOUNT_CONNECTION_STRING");
        System.out.println( " file name : " + fileName + " filePath:  " + "" + " File share : "+fileShare);
        String destDir = userDir + "/src/test/resources/data/downloads";
        azureStorageUtils.downloadFile( connectionString,fileShare, "", destDir,fileName);
    }


    @Then("delete file {string} from {string} file share")
    public void deleteFileFromFileShare(String fileName, String fileShare) throws IOException {
        Path userDir = Paths.get(System.getProperty("user.dir"));
        String directoryPath = "src/test/resources/data/";
        String connectionString = GenericUtils.readProps("STORAGE_ACCOUNT_CONNECTION_STRING");
        System.out.println( " file name : " + fileName + " filePath:  " + directoryPath + " File share : "+fileShare);
        azureStorageUtils.deleteFile( connectionString,fileShare, "",  fileName);
    }

    @Then("create directory {string} in the {string} file share")
    public void createFileDirectory(String directoryPath, String fileShare) throws IOException {
        String connectionString = GenericUtils.readProps("STORAGE_ACCOUNT_CONNECTION_STRING");
        System.out.println(" Directory path:  " + directoryPath + " File share : "+fileShare);
        azureStorageUtils.createDirectory( connectionString,fileShare, directoryPath);
    }

    @Then("delete directory {string} in the {string} file share")
    public void deleteFileDirectory(String directoryPath, String fileShare) throws IOException {
        String connectionString = GenericUtils.readProps("STORAGE_ACCOUNT_CONNECTION_STRING");
        System.out.println(" Directory path:  " + directoryPath + " File share : "+fileShare);
        azureStorageUtils.deleteDirectory( connectionString,fileShare, directoryPath);
    }



}
