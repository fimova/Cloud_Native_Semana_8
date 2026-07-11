package com.duoc.consumidor.servicio;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import com.rabbitmq.client.Channel;

public interface RabbitConsumer {

    void recibirGuia(
            Message mensaje,
            Channel canal
    ) throws IOException;

}