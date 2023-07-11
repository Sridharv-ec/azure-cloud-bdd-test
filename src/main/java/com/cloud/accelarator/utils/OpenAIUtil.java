package com.cloud.accelarator.utils;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.ai.openai.models.*;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.IterableStream;
import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class OpenAIUtil {

    public static OpenAIClient getOpenAIClient() throws IOException {
        String key = GenericUtils.readProps("OPENAI_KEY");
        String endpoint = GenericUtils.readProps("OPENAI_ENDPOINT");

        return new OpenAIClientBuilder()
                .credential(new AzureKeyCredential(key))
                .endpoint(endpoint)
                .buildClient();
    }

    public void doTextCompletion(String promptStrig, String deploymentId) throws IOException {
        List<String> prompt = new ArrayList<>();
        prompt.add(promptStrig);
        OpenAIClient client = getOpenAIClient();
        Completions completions = client.getCompletions(deploymentId, new CompletionsOptions(prompt));

        System.out.printf("Model ID=%s is created at %d.%n", completions.getId(), completions.getCreated());
        for (Choice choice : completions.getChoices()) {
            System.out.printf("Index: %d, Text: %s.%n", choice.getIndex(), choice.getText());
        }
    }

    public void chatCompletions(List<String> promptString, String deploymentId) throws IOException {
        OpenAIClient client = getOpenAIClient();
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(ChatRole.SYSTEM).setContent(promptString.get(0)));
        chatMessages.add(new ChatMessage(ChatRole.USER).setContent(promptString.get(1)));
        chatMessages.add(new ChatMessage(ChatRole.ASSISTANT).setContent(promptString.get(2)));
        chatMessages.add(new ChatMessage(ChatRole.USER).setContent(promptString.get(3)));

        ChatCompletions chatCompletions = client.getChatCompletions(deploymentId,
                new ChatCompletionsOptions(chatMessages));

        System.out.printf("Model ID=%s is created at %d.%n", chatCompletions.getId(), chatCompletions.getCreated());
        for (ChatChoice choice : chatCompletions.getChoices()) {
            ChatMessage message = choice.getMessage();
            System.out.printf("Index: %d, Chat Role: %s.%n", choice.getIndex(), message.getRole());
            System.out.println("Message:");
            System.out.println(message.getContent());
        }

    }

    private static String speechKey = "84bb655ddd6b442cba3b96c98ab3bec3";
    private static String speechRegion = "eastus";


}
