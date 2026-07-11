package com.duoc.plataformatransportes.modelo;

import java.net.URL;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Asset {

    String name;
    String key;
    URL url;
    String efsPath;

}