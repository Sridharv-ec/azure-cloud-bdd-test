package com.cloud.accelarator.stepdefs;

import com.cloud.accelarator.utils.OpenAIUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import java.io.IOException;
import java.util.List;

public class OpenAIStepDef {

    OpenAIUtil openAIUtil = new OpenAIUtil();

    @Given("do text completions for the promt {string} for deployment id {string}")
    public void doTextCompletionForPrompt(String prompt, String deploymentId) throws IOException {
        openAIUtil.doTextCompletion(prompt, deploymentId);
    }

    @And("do chat completions for the promt for deployment id {string}")
    public void doChatCompletionForPrompt(String deploymentId, List<String> prompt) throws IOException {
        openAIUtil.chatCompletions(prompt, deploymentId);
    }

}
