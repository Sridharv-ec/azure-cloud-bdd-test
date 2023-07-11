package com.cloud.accelarator.stepdefs;

import com.cloud.accelarator.utils.SpeechToTextUtil;
import io.cucumber.java.en.Given;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class SpeechToTextStepDef {
    SpeechToTextUtil speechToTextUtil = new SpeechToTextUtil();

    @Given("convert speech to text")
    public void convert_speech_to_text() throws IOException, ExecutionException, InterruptedException {
        speechToTextUtil.convertSpeechToText();
    }

    @Given("convert text {string} to speech")
    public void convert_text_to_speech(String text) throws IOException, ExecutionException, InterruptedException {
        speechToTextUtil.convertTexToSpeech(text);
    }
}
