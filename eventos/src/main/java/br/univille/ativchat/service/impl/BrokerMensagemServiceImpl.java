package br.univille.ativchat.service.impl;

import com.azure.core.amqp.AmqpTransportType;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.azure.messaging.servicebus.models.ServiceBusReceiveMode;

import br.univille.ativchat.model.Mensagem;
import br.univille.ativchat.service.BrokerMensagemService;
import br.univille.ativchat.view.Form;

public class BrokerMensagemServiceImpl implements BrokerMensagemService {
    String topicName = "topic-chat";
    String serviceBus = "sb-das12025-test-brazilsouth.servicebus.windows.net";
    String subscription = "subscription-lucas-pacifico"; // +  System.getenv("USERNAME"); // precisei remover pois isso retorna "lucas" pra mim e no Azure está "subscription-lucas-pacifico"

    @Override
    public void enviarMensagem(Mensagem mensagem) {
     
        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder().build();
        ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
        .fullyQualifiedNamespace(serviceBus)
        .credential(credential)
        .transportType(AmqpTransportType.AMQP_WEB_SOCKETS)
        .sender()
        .topicName(topicName)
        .buildClient();

        senderClient.sendMessage(new ServiceBusMessage(mensagem.texto()));
    }

    @Override
    public void buscarMensagens(Form form) {
        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder().build();

        ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
            .fullyQualifiedNamespace(serviceBus)
            .credential(credential)
            .transportType(AmqpTransportType.AMQP_WEB_SOCKETS)
            .processor()
            .topicName(topicName)
            .subscriptionName(subscription)
            .receiveMode(ServiceBusReceiveMode.PEEK_LOCK)
            .processMessage(context -> {
            form.adicionarMensagemNoChat("EXTERNO: " + context.getMessage().getBody().toString());
            context.complete();
            })
            .processError(context -> {
                System.out.println("Erro: " + context.getException().getMessage());
            })
            .buildProcessorClient();

        processorClient.start();
    
        try {
            Thread.sleep(7000); // System.in.read() estava bloqueando a thread da interface fiz assim pra simplicar, mas 5 segundos pode não ser tempo suficiente
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            form.adicionarMensagemNoChat("\nTodas as mensagens disponíveis foram exibidas ✅");
            processorClient.close();
        } 
    }
    
}
