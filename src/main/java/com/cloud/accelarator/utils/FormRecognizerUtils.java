package com.cloud.accelarator.utils;

import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClient;
import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClientBuilder;
import com.azure.ai.formrecognizer.documentanalysis.models.*;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import com.azure.core.util.polling.SyncPoller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class FormRecognizerUtils {

    public DocumentAnalysisClient getDocumentAnalysisClient() throws IOException {
        String key = GenericUtils.readProps("FORM_RECOGNIZER_KEY");
        String endpoint = GenericUtils.readProps("FORM_RECOGNIZER_ENDPOINT");
        return new DocumentAnalysisClientBuilder()
                .credential(new AzureKeyCredential(key))
                .endpoint(endpoint)
                .buildClient();
    }

    public void analyzeDocumentLayout(String document) throws IOException {

        DocumentAnalysisClient documentAnalysisClient = getDocumentAnalysisClient();
        // analyze document layout using file input stream
        File layoutDocument = new File("src/main/resources/"+document+"");
        Path filePath = layoutDocument.toPath();
        BinaryData layoutDocumentData = BinaryData.fromFile(filePath);

        SyncPoller<OperationResult, AnalyzeResult> analyzeLayoutResultPoller =
                documentAnalysisClient.beginAnalyzeDocument("prebuilt-layout", layoutDocumentData);

        AnalyzeResult analyzeLayoutResult = analyzeLayoutResultPoller.getFinalResult();

// pages
        try {
            analyzeLayoutResult.getPages().forEach(documentPage -> {
                System.out.printf("Page has width: %.2f and height: %.2f, measured with unit: %s%n",
                        documentPage.getWidth(),
                        documentPage.getHeight(),
                        documentPage.getUnit());

                // lines
                documentPage.getLines().forEach(documentLine ->
                        System.out.printf("Line '%s' is within a bounding box %s.%n",
                                documentLine.getContent(),
                                documentLine.getBoundingPolygon().toString()));

                // selection marks
                documentPage.getSelectionMarks().forEach(documentSelectionMark ->
                        System.out.printf("Selection mark is '%s' and is within a bounding box %s with confidence %.2f.%n",
                                documentSelectionMark.getSelectionMarkState().toString(),
                                documentSelectionMark.getBoundingPolygon().toString(),
                                documentSelectionMark.getConfidence()));
            });
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

// tables
        List<DocumentTable> tables = analyzeLayoutResult.getTables();
        for (int i = 0; i < tables.size(); i++) {
            DocumentTable documentTable = tables.get(i);
            System.out.printf("Table %d has %d rows and %d columns.%n", i, documentTable.getRowCount(),
                    documentTable.getColumnCount());
            documentTable.getCells().forEach(documentTableCell -> {
                System.out.printf("Cell '%s', has row index %d and column index %d.%n", documentTableCell.getContent(),
                        documentTableCell.getRowIndex(), documentTableCell.getColumnIndex());
            });
            System.out.println();
        }
    }

    public void analyseReceiptOrInvoice(String receipt) throws IOException {
        DocumentAnalysisClient documentAnalysisClient = getDocumentAnalysisClient();

//        String receiptUrl = "https://github.com/Azure/azure-sdk-for-java/blob/azure-ai-formrecognizer_4.1.0-beta.2/sdk/formrecognizer/azure-ai-formrecognizer/src/samples/resources/sample-forms/invoices/sample_invoice.jpg";
        String receiptUrl =
                "https://raw.githubusercontent.com/Azure/azure-sdk-for-java/main/sdk/formrecognizer"
                        + "/azure-ai-formrecognizer/src/samples/resources/sample-forms/receipts/"+receipt+"";

        SyncPoller<OperationResult, AnalyzeResult> analyzeReceiptPoller =
                documentAnalysisClient.beginAnalyzeDocumentFromUrl("prebuilt-receipt", receiptUrl);

        AnalyzeResult receiptResults = analyzeReceiptPoller.getFinalResult();

        for (int i = 0; i < receiptResults.getDocuments().size(); i++) {
            AnalyzedDocument analyzedReceipt = receiptResults.getDocuments().get(i);
            Map<String, DocumentField> receiptFields = analyzedReceipt.getFields();
            System.out.printf("----------- Analyzing receipt info %d -----------%n", i);
            DocumentField merchantNameField = receiptFields.get("MerchantName");
            if (merchantNameField != null) {
                if (DocumentFieldType.STRING == merchantNameField.getType()) {
                    String merchantName = merchantNameField.getValueAsString();
                    System.out.printf("Merchant Name: %s, confidence: %.2f%n",
                            merchantName, merchantNameField.getConfidence());
                }
            }

            DocumentField merchantPhoneNumberField = receiptFields.get("MerchantPhoneNumber");
            if (merchantPhoneNumberField != null) {
                if (DocumentFieldType.PHONE_NUMBER == merchantPhoneNumberField.getType()) {
                    String merchantAddress = merchantPhoneNumberField.getValueAsPhoneNumber();
                    System.out.printf("Merchant Phone number: %s, confidence: %.2f%n",
                            merchantAddress, merchantPhoneNumberField.getConfidence());
                }
            }

            DocumentField transactionDateField = receiptFields.get("TransactionDate");
            if (transactionDateField != null) {
                if (DocumentFieldType.DATE == transactionDateField.getType()) {
                    LocalDate transactionDate = transactionDateField.getValueAsDate();
                    System.out.printf("Transaction Date: %s, confidence: %.2f%n",
                            transactionDate, transactionDateField.getConfidence());
                }
            }

            DocumentField receiptItemsField = receiptFields.get("Items");
            if (receiptItemsField != null) {
                System.out.printf("Receipt Items: %n");
                if (DocumentFieldType.LIST == receiptItemsField.getType()) {
                    List<DocumentField> receiptItems = receiptItemsField.getValueAsList();
                    receiptItems.stream()
                            .filter(receiptItem -> DocumentFieldType.MAP == receiptItem.getType())
                            .map(documentField -> documentField.getValueAsMap())
                            .forEach(documentFieldMap -> documentFieldMap.forEach((key, documentField) -> {
                                if ("Name".equals(key)) {
                                    if (DocumentFieldType.STRING == documentField.getType()) {
                                        String name = documentField.getValueAsString();
                                        System.out.printf("Name: %s, confidence: %.2fs%n",
                                                name, documentField.getConfidence());
                                    }
                                }
                                if ("Quantity".equals(key)) {
                                    if (DocumentFieldType.DOUBLE == documentField.getType()) {
                                        Double quantity = documentField.getValueAsDouble();
                                        System.out.printf("Quantity: %f, confidence: %.2f%n",
                                                quantity, documentField.getConfidence());
                                    }
                                }
                            }));
                }
            }
        }
    }

}
