package com.cloud.accelarator.utils;

import com.azure.messaging.servicebus.*;
import com.azure.messaging.servicebus.models.ServiceBusReceiveMode;
import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


public class AzureServiceBusUtils {

    public ServiceBusSenderClient getSendersServiceBusClient(String queueName) throws IOException {
        ServiceBusSenderClient sender = new ServiceBusClientBuilder()
                .connectionString(GenericUtils.readProps("SERVICE_BUS_CONNECTION_STRING"))
                .sender()
                .queueName(queueName)
                .buildClient();
        return sender;
    }

    public void sendMessages(String queueName, List<String> messages) throws IOException {
        ServiceBusSenderClient serviceBusSenderClient = getSendersServiceBusClient(queueName);
        List<ServiceBusMessage> serviceBusMessageList = new ArrayList<>();
        for (String message : messages) {
            serviceBusMessageList.add(new ServiceBusMessage(message));
        }
// Creates an ServiceBusMessageBatch where the ServiceBus.
        ServiceBusMessageBatch messageBatch = serviceBusSenderClient.createMessageBatch();
        for (ServiceBusMessage message : serviceBusMessageList) {
            if (messageBatch.tryAddMessage(message)) {
                continue;
            }

            // The batch is full, so we create a new batch and send the batch.
            serviceBusSenderClient.sendMessages(messageBatch);
            System.out.println("Sent a batch of messages to the queue: " + queueName);

            // create a new batch
            messageBatch = serviceBusSenderClient.createMessageBatch();

            // Add that message that we couldn't before.
            if (!messageBatch.tryAddMessage(message)) {
                System.err.printf("Message is too large for an empty batch. Skipping. Max size: %s.", messageBatch.getMaxSizeInBytes());
            }
        }

        if (messageBatch.getCount() > 0) {
            serviceBusSenderClient.sendMessages(serviceBusMessageList);
            System.out.println("Sent a batch of messages to the queue: " + queueName);
        }

        //close the client
        serviceBusSenderClient.close();

    }

    public static void receiveMessages(String queueName) throws InterruptedException, IOException {
        CountDownLatch countdownLatch = new CountDownLatch(1);
        ServiceBusReceivedMessageContext context1;
        // Create an instance of the processor through the ServiceBusClientBuilder
        ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
                .connectionString(GenericUtils.readProps("SERVICE_BUS_CONNECTION_STRING"))
                .processor()
                .queueName(queueName)
                .processMessage(AzureServiceBusUtils::processMessage)
                .processError(context -> processError(context, countdownLatch))
                .buildProcessorClient();

        System.out.println("Starting the processor");
        processorClient.start();

        TimeUnit.SECONDS.sleep(10);
        System.out.println("Stopping and closing the processor");
        processorClient.close();
    }

    private static void processMessage(ServiceBusReceivedMessageContext context) {
        ServiceBusReceivedMessage message = context.getMessage();
        System.out.printf("Processing message. Session: %s, Sequence #: %s. Contents: %s%n", message.getMessageId(),
                message.getSequenceNumber(), message.getBody());
    }

    private static void processError(ServiceBusErrorContext context, CountDownLatch countdownLatch) {
        System.out.printf("Error when receiving messages from namespace: '%s'. Entity: '%s'%n",
                context.getFullyQualifiedNamespace(), context.getEntityPath());

        if (!(context.getException() instanceof ServiceBusException)) {
            System.out.printf("Non-ServiceBusException occurred: %s%n", context.getException());
            return;
        }

        ServiceBusException exception = (ServiceBusException) context.getException();
        ServiceBusFailureReason reason = exception.getReason();

        if (reason == ServiceBusFailureReason.MESSAGING_ENTITY_DISABLED
                || reason == ServiceBusFailureReason.MESSAGING_ENTITY_NOT_FOUND
                || reason == ServiceBusFailureReason.UNAUTHORIZED) {
            System.out.printf("An unrecoverable error occurred. Stopping processing with reason %s: %s%n",
                    reason, exception.getMessage());

            countdownLatch.countDown();
        } else if (reason == ServiceBusFailureReason.MESSAGE_LOCK_LOST) {
            System.out.printf("Message lock lost for message: %s%n", context.getException());
        } else if (reason == ServiceBusFailureReason.SERVICE_BUSY) {
            try {
                // Choosing an arbitrary amount of time to wait until trying again.
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.err.println("Unable to sleep for period of time");
            }
        } else {
            System.out.printf("Error source %s, reason %s, message: %s%n", context.getErrorSource(),
                    reason, context.getException());
        }
    }

}
