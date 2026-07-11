package com.duoc.consumidor.servicio;

public interface RabbitListenerControlService {

    void pausarListener(String id);

    void reanudarListener(String id);

    boolean isListenerRunning(String id);
}
