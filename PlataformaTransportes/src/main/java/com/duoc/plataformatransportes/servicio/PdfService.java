package com.duoc.plataformatransportes.servicio;

import com.duoc.plataformatransportes.modelo.GuiaDespacho;
import java.io.File;

public interface PdfService {

    File generarPdf(
            GuiaDespacho guia
    );

}