package com.mvpyouedu.YouEdu_api.controller.authUser;


import com.mvpyouedu.YouEdu_api.domain.dto.DadodParaUpload;
import com.mvpyouedu.YouEdu_api.service.UploadService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/video")
public class PublicarVideoController {

    @Autowired
    private UploadService uploadService;

    @Value("${upload.dir}")
    private String UPLOAD_DIR;


    @PostMapping("/upload")
    @Transactional
    public ResponseEntity publicarVideo(@RequestParam("file") MultipartFile file, @RequestParam(required = false) @Valid DadodParaUpload dadosUpload){



        String respostaUpload = uploadService.uploadFile(file);

        return ResponseEntity.ok(respostaUpload);

    }
    @GetMapping("/uploads")
    public List<String> listarVideos(){

        Path path = Path.of(UPLOAD_DIR);
        if (!Files.exists(path)) {
            return List.of();
        }

        try (Stream<Path> paths = Files.list(path)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".mp4"))
                    .map(p -> "http://localhost:8080/uploads/" + p.getFileName())
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao listar vídeos", e);
        }
    }

}
