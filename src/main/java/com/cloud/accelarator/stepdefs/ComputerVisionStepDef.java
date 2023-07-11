package com.cloud.accelarator.stepdefs;

import com.cloud.accelarator.utils.AzureStorageUtils;
import com.cloud.accelarator.utils.ComputerVisionUtil;
import com.cloud.accelarator.utils.GenericUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ComputerVisionStepDef {

    ComputerVisionUtil computerVisionUtil = new ComputerVisionUtil();
    @Given("retrieve the information about the image {string}")
    public void createBlobContainerStep(String imageName) throws IOException {
        computerVisionUtil.AnalyzeLocalImage(imageName);
    }


}
