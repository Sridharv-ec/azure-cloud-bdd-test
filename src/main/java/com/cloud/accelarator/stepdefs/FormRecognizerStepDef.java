package com.cloud.accelarator.stepdefs;

import com.cloud.accelarator.utils.FormRecognizerUtils;
import io.cucumber.java.en.Given;

import java.io.IOException;

public class FormRecognizerStepDef {

    FormRecognizerUtils formRecognizerUtils = new FormRecognizerUtils();

    @Given("analyze document layout for the file {string}")
    public void analyseDocumentLayout(String document) throws IOException {
        formRecognizerUtils.analyzeDocumentLayout(document);
    }

    @Given("analyze invoice details for the reciept {string}")
    public void analyseInvoiceDetails(String receipt) throws IOException {
        formRecognizerUtils.analyseReceiptOrInvoice(receipt);
    }

}
