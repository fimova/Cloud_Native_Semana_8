package com.duoc.consumidor.controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duoc.consumidor.servicio.RabbitListenerControlService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rabbit-listener")
@RequiredArgsConstructor
public class RabbitListenerAdminController {


    private final RabbitListenerControlService service;


    @PostMapping("/pausar/{id}")
    public String pausar(
            @PathVariable String id
    ) {

        service.pausarListener(id);

        return "Listener pausado: " + id;
    }


    @PostMapping("/reanudar/{id}")
    public String reanudar(
            @PathVariable String id
    ) {

        service.reanudarListener(id);

        return "Listener reanudado: " + id;
    }


    @GetMapping("/status/{id}")
    public String status(
            @PathVariable String id
    ) {

        return "Listener "
                + id
                + " está "
                + (
                    service.isListenerRunning(id)
                    ? "activo"
                    : "pausado"
                  );
    }
}
