package com.cloud.accelarator.utils;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.file.share.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AzureStorageUtils {


    public BlobContainerClient createBlobContainer(String connectionString, String containerName){
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
        BlobContainerClient blobContainerClient = blobServiceClient.createBlobContainerIfNotExists(containerName);
        return blobContainerClient;

    }

    public void uploadBlobObject(String connectionString, String containerName, String blobObject){
        BlobContainerClient blobContainerClient = createBlobContainer(connectionString, containerName);
        BlobClient blobClient = blobContainerClient.getBlobClient(blobObject);
        System.out.println("\nUploading to Blob storage as blob:\n\t" + blobClient.getBlobUrl());
        blobClient.uploadFromFile(blobObject);
    }

    public void downlaodBlobObject(String connectionString, String containerName, String blobObject){
        BlobContainerClient blobContainerClient = createBlobContainer(connectionString, containerName);
        BlobClient blobClient = blobContainerClient.getBlobClient(blobObject);
        System.out.println("\ndownloading fron Blob storage :\n\t" + blobClient.getBlobUrl());
        blobClient.downloadToFile(blobObject);
    }

    public void deleteBlobContainer(String connectionString, String containerName){
        BlobContainerClient blobContainerClient = createBlobContainer(connectionString, containerName);
        blobContainerClient.delete();
    }

    public void listBlobContainer(String connectionString, String containerName){
        BlobContainerClient blobContainerClient = createBlobContainer(connectionString, containerName);
        System.out.println("\nListing blobs...");
        for (BlobItem blobItem : blobContainerClient.listBlobs()) {
            System.out.println("\t" + blobItem.getName());
        }
    }

    public static Boolean createFileShare(String connectStr, String shareName)
    {
        try
        {
            ShareClient shareClient = new ShareClientBuilder()
                    .connectionString(connectStr).shareName(shareName)
                    .buildClient();

            shareClient.create();
            return true;
        }
        catch (Exception e)
        {
            System.out.println("createFileShare exception: " + e.getMessage());
            return false;
        }
    }

    public static Boolean deleteFileShare(String connectStr, String shareName)
    {
        try
        {
            ShareClient shareClient = new ShareClientBuilder()
                    .connectionString(connectStr).shareName(shareName)
                    .buildClient();

            shareClient.delete();
            return true;
        }
        catch (Exception e)
        {
            System.out.println("deleteFileShare exception: " + e.getMessage());
            return false;
        }
    }

    public void uploadFile(String connectStr, String shareName,
                                     String dirName, String fileName)
    {
        try
        {

            ShareDirectoryClient dirClient = new ShareFileClientBuilder()
                    .connectionString(connectStr).shareName(shareName)
                    .resourcePath(dirName)
                    .buildDirectoryClient();

            ShareFileClient fileClient = dirClient.getFileClient(fileName);
            fileClient.create(1024);
            fileClient.uploadFromFile(fileName);



        }
        catch (Exception e)
        {
            System.out.println("uploadFile exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Boolean downloadFile(String connectStr, String shareName,
                                       String dirName, String destDir,
                                       String fileName)
    {
        try
        {
            ShareDirectoryClient dirClient = new ShareFileClientBuilder()
                    .connectionString(connectStr).shareName(shareName)
                    .resourcePath(dirName)
                    .buildDirectoryClient();

            ShareFileClient fileClient = dirClient.getFileClient(fileName);

            // Create a unique file name
            String date = new java.text.SimpleDateFormat("yyyyMMdd-HHmmss").format(new java.util.Date());
            String destPath = destDir + "/"+ date + "_" + fileName;

            fileClient.downloadToFile(destPath);
            return true;
        }
        catch (Exception e)
        {
            System.out.println("downloadFile exception: " + e.getMessage());
            return false;
        }
    }

    public Boolean deleteFile(String connectStr, String shareName,
                                     String dirName, String fileName)
    {
        try
        {
            ShareDirectoryClient dirClient = new ShareFileClientBuilder()
                    .connectionString(connectStr).shareName(shareName)
                    .resourcePath(dirName)
                    .buildDirectoryClient();

            ShareFileClient fileClient = dirClient.getFileClient(fileName);
            fileClient.delete();
            return true;
        }
        catch (Exception e)
        {
            System.out.println("deleteFile exception: " + e.getMessage());
            return false;
        }
    }

    public void createDirectory(String connectStr, String shareName,
                                          String dirName)
    {
        try
        {
            ShareDirectoryClient dirClient = new ShareFileClientBuilder()
                    .connectionString(connectStr).shareName(shareName)
                    .resourcePath(dirName)
                    .buildDirectoryClient();

            dirClient.create();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("createDirectory exception: " + e.getMessage());
        }
    }

    public void deleteDirectory(String connectStr, String shareName,
                                          String dirName)
    {
        try
        {
            ShareDirectoryClient dirClient = new ShareFileClientBuilder()
                    .connectionString(connectStr).shareName(shareName)
                    .resourcePath(dirName)
                    .buildDirectoryClient();

            dirClient.delete();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("deleteDirectory exception: " + e.getMessage());
        }
    }


}
