package com.duoc.plataformatransportes.servicio.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.duoc.plataformatransportes.config.RabbitMQConfig;
import com.duoc.plataformatransportes.servicio.RabbitProducer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitProducerImpl implements RabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void enviarResumen(String resumen) {

        System.out.println("Mensaje que envío:");
        System.out.println(resumen);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.GUIA_EXCHANGE,
                RabbitMQConfig.GUIA_ROUTING_KEY,
                resumen
        );
    }
}