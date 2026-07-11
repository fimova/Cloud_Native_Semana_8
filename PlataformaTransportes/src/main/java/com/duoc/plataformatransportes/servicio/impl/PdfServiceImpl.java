package com.duoc.plataformatransportes.servicio.impl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import com.duoc.plataformatransportes.servicio.PdfService;

import java.io.File;
import java.io.IOException;

import com.duoc.plataformatransportes.modelo.GuiaDespacho;

@Service
public class PdfServiceImpl
implements PdfService {

    @Override
    public File generarPdf(
            GuiaDespacho guia
    ) {

        try {

            File archivo =
                    File.createTempFile(
                            guia.getNumeroGuia(),
                            ".pdf"
                    );

            try (
                    PDDocument document =
                            new PDDocument()
            ) {

                PDPage page =
                        new PDPage();

                document.addPage(
                        page
                );

                try (
                        PDPageContentStream contenido =
                                new PDPageContentStream(
                                        document,
                                        page
                                )
                ) {

                    contenido.beginText();

                    contenido.setFont(
                            new PDType1Font(
                                    Standard14Fonts.FontName.HELVETICA
                            ),
                            12
                    );

                    contenido.newLineAtOffset(
                            50,
                            700
                    );

                    contenido.showText(
                            "GUIA DE DESPACHO"
                    );

                    contenido.newLineAtOffset(
                            0,
                            -30
                    );

                    contenido.showText(
                            "Numero: "
                            + guia.getNumeroGuia()
                    );

                    contenido.newLineAtOffset(
                            0,
                            -20
                    );

                    contenido.showText(
                            "Transportista: "
                            + guia.getTransportista()
                    );

                    contenido.newLineAtOffset(
                            0,
                            -20
                    );

                    contenido.showText(
                            "Fecha: "
                            + guia.getFecha()
                    );

                    contenido.newLineAtOffset(
                            0,
                            -20
                    );

                    contenido.showText(
                            "Descripcion: "
                            + guia.getDescripcion()
                    );

                    contenido.endText();

                }

                document.save(
                        archivo
                );

            }

            return archivo;

        } catch (
                IOException e
        ) {

            throw new RuntimeException(
                    "Error generando PDF",
                    e
            );

        }

    }

}
