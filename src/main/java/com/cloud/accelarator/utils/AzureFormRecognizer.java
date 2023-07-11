package com.cloud.accelarator.utils;

import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClient;
import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClientBuilder;
import com.azure.ai.formrecognizer.documentanalysis.models.AnalyzeResult;
import com.azure.ai.formrecognizer.documentanalysis.models.AnalyzedDocument;
import com.azure.ai.formrecognizer.documentanalysis.models.DocumentTable;
import com.azure.ai.formrecognizer.documentanalysis.models.OperationResult;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import com.azure.core.util.polling.SyncPoller;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class AzureFormRecognizer {

    public DocumentAnalysisClient getDocumentAnalysisClient(String key) {

        DocumentAnalysisClient documentAnalysisClient = new DocumentAnalysisClientBuilder()
                .credential(new AzureKeyCredential("{key}"))
                .endpoint("{endpoint}")
                .buildClient();

        return documentAnalysisClient;

    }

//    public static void main(String[] args) {
//
//        DocumentAnalysisClient documentAnalysisClient = new DocumentAnalysisClientBuilder()
//                .credential(new AzureKeyCredential("ae5533b2223c44bcbfa8079710f81840"))
//                .endpoint("https://my-first-cognitive-service-sri-001.cognitiveservices.azure.com/")
//                .buildClient();
//
//        // analyze document layout using file input stream
//        File layoutDocument = new File("/Users/srivutta/Documents/Sridhar/MyLearnings/Azure/Automation/azure-cloud-bdd-test/src/test/resources/data/downloads/20230608-201820_CreditReport.pdf");
//        Path filePath = layoutDocument.toPath();
//        BinaryData layoutDocumentData = BinaryData.fromFile(filePath);
//
//        SyncPoller<OperationResult, AnalyzeResult> analyzeLayoutResultPoller =
//                documentAnalysisClient.beginAnalyzeDocument("prebuilt-layout", layoutDocumentData);
//
//        AnalyzeResult analyzeLayoutResult = analyzeLayoutResultPoller.getFinalResult();
//
//// pages
//        analyzeLayoutResult.getPages().forEach(documentPage -> {
//            System.out.printf("Page has width: %.2f and height: %.2f, measured with unit: %s%n",
//                    documentPage.getWidth(),
//                    documentPage.getHeight(),
//                    documentPage.getUnit());
//
//            // lines
//            documentPage.getLines().forEach(documentLine ->
//                    System.out.printf("Line '%s' is within a bounding box %s.%n",
//                            documentLine.getContent(),
//                            documentLine.getBoundingPolygon().toString()));
//
//            // selection marks
//            documentPage.getSelectionMarks().forEach(documentSelectionMark ->
//                    System.out.printf("Selection mark is '%s' and is within a bounding box %s with confidence %.2f.%n",
//                            documentSelectionMark.getSelectionMarkState().toString(),
//                            documentSelectionMark.getBoundingPolygon().toString(),
//                            documentSelectionMark.getConfidence()));
//        });
//
//// tables
////        List<DocumentTable> tables = analyzeLayoutResult.getTables();
////        for (int i = 0; i < tables.size(); i++) {
////            DocumentTable documentTable = tables.get(i);
////            System.out.printf("Table %d has %d rows and %d columns.%n", i, documentTable.getRowCount(),
////                    documentTable.getColumnCount());
////            documentTable.getCells().forEach(documentTableCell -> {
////                System.out.printf("Cell '%s', has row index %d and column index %d.%n", documentTableCell.getContent(),
////                        documentTableCell.getRowIndex(), documentTableCell.getColumnIndex());
////            });
////            System.out.println();
//        }
//
//    }

    public static void main(String[] args) {
        DocumentAnalysisClient documentAnalysisClient = new DocumentAnalysisClientBuilder()
                .credential(new AzureKeyCredential("ae5533b2223c44bcbfa8079710f81840"))
                .endpoint("https://my-first-cognitive-service-sri-001.cognitiveservices.azure.com/")
                .buildClient();
        String documentUrl = "https://intellipaat.com/blog/interview-question/selenium-interview-questions/?US";
        String modelId = "prebuilt-document";
        SyncPoller<OperationResult, AnalyzeResult> analyzeDocumentPoller =
                documentAnalysisClient.beginAnalyzeDocumentFromUrl(modelId, documentUrl);

        AnalyzeResult analyzeResult = analyzeDocumentPoller.getFinalResult();

        for (int i = 0; i < analyzeResult.getDocuments().size(); i++) {
            final AnalyzedDocument analyzedDocument = analyzeResult.getDocuments().get(i);
            System.out.printf("----------- Analyzing document %d -----------%n", i);
            System.out.printf("Analyzed document has doc type %s with confidence : %.2f%n",
                    analyzedDocument.getDocType(), analyzedDocument.getConfidence());
        }

        analyzeResult.getPages().forEach(documentPage -> {
            System.out.printf("Page has width: %.2f and height: %.2f, measured with unit: %s%n",
                    documentPage.getWidth(),
                    documentPage.getHeight(),
                    documentPage.getUnit());

            // lines
            documentPage.getLines().forEach(documentLine ->
                    System.out.printf("Line '%s' is within a bounding box %s.%n",
                            documentLine.getContent(),
                            documentLine.getBoundingPolygon().toString()));

            // words
            documentPage.getWords().forEach(documentWord ->
                    System.out.printf("Word '%s' has a confidence score of %.2f.%n",
                            documentWord.getContent(),
                            documentWord.getConfidence()));
        });

// tables
        List<DocumentTable> tables = analyzeResult.getTables();
        for (int i = 0; i < tables.size(); i++) {
            DocumentTable documentTable = tables.get(i);
            System.out.printf("Table %d has %d rows and %d columns.%n", i, documentTable.getRowCount(),
                    documentTable.getColumnCount());
            documentTable.getCells().forEach(documentTableCell -> {
                System.out.printf("Cell '%s', has row index %d and column index %d.%n",
                        documentTableCell.getContent(),
                        documentTableCell.getRowIndex(), documentTableCell.getColumnIndex());
            });
            System.out.println();
        }

// Key-value
        analyzeResult.getKeyValuePairs().forEach(documentKeyValuePair -> {
            System.out.printf("Key content: %s%n", documentKeyValuePair.getKey().getContent());
            System.out.printf("Key content bounding region: %s%n",
                    documentKeyValuePair.getKey().getBoundingRegions().toString());

            System.out.printf("Value content: %s%n", documentKeyValuePair.getValue().getContent());
            System.out.printf("Value content bounding region: %s%n", documentKeyValuePair.getValue().getBoundingRegions().toString());
        });
    }


}
