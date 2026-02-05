package com.mvpyouedu.YouEdu_api.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;

@Service
public class UploadService implements WebMvcConfigurer {

    @Value("${upload.dir}")
    private String UPLOAD_DIR;

    @Transactional
    public String uploadFile(MultipartFile file) {

        String caminhoArquivo;
        try {
            File pasta = new File(UPLOAD_DIR);
            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            caminhoArquivo = UPLOAD_DIR + file.getOriginalFilename();

            file.transferTo(new File(caminhoArquivo));

        } catch (IOException e) {
            return "Erro ao fazer upload do arquivo: " + e.getMessage();
        }

        return "Caminho do arquivo: " + caminhoArquivo;
    }

    public File getFile() {
        if (UPLOAD_DIR.isEmpty()){
            return null;
        } else {
            return new File(UPLOAD_DIR );
        }
    }

}
