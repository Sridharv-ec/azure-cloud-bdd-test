@servicebus
Feature: Azure service bus feature

  Scenario: Azure service bus send message and receive message
#    When Send the message in the queue "myfirstservicebusqueue"
#     |first message|
#     |second message|
#     |third message|
    Then receive message from the queue "myfirstservicebusqueue"