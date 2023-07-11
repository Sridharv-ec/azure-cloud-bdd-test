@openai
Feature: Azure cogninitive service OpenAi feature file

  Scenario: Azure cloud open ai
    Given do text completions for the promt "Say this is a test" for deployment id "my-gpt-turbo-deployment-01"
    And do chat completions for the promt for deployment id "my-gpt-turbo-deployment-01"
      | You are a helpful assistant. You will talk like a pirate. |
      | Can you help me?"                                         |
      | Of course, me hearty! What can I do for ye?               |
      | What's the best way to train a parrot?                    |