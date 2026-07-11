package com.duoc.consumidor.servicio.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.duoc.consumidor.config.RabbitMQConfig;
import com.duoc.consumidor.entidad.ResumenGuia;
import com.duoc.consumidor.repositorio.ResumenGuiaRepository;
import com.duoc.consumidor.servicio.RabbitConsumer;

import org.springframework.amqp.core.Message;
import com.rabbitmq.client.Channel;

import java.io.IOException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitConsumerImpl implements RabbitConsumer {

    private final ResumenGuiaRepository repository;

    @Override
    @RabbitListener(
            id = "guiaListener",
            queues = RabbitMQConfig.GUIA_QUEUE,
            ackMode = "MANUAL"
    )
    public void recibirGuia(
            Message mensaje,
            Channel canal
    ) throws IOException {

        try {

            String guia = new String(mensaje.getBody());

            System.out.println("Guía recibida:");
            System.out.println(guia);

            // FORZAR ERROR PARA PROBAR LA DLQ
            if (guia.contains("ERROR")) {

                throw new RuntimeException(
                        "Error de prueba"
                );
            }

            ResumenGuia entidad =
                    new ResumenGuia();

            entidad.setResumen(guia);

            repository.save(entidad);

            canal.basicAck(
                    mensaje.getMessageProperties()
                           .getDeliveryTag(),
                    false
            );

            System.out.println("ACK enviado");

        } catch (Exception e) {

            canal.basicNack(
                    mensaje.getMessageProperties()
                           .getDeliveryTag(),
                    false,
                    false
            );

            System.out.println("NACK enviado");
        }
    }
}
