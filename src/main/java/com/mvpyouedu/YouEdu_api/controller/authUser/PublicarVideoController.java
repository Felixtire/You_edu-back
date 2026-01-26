package com.mvpyouedu.YouEdu_api.controller.authUser;


import com.mvpyouedu.YouEdu_api.domain.dto.DadodParaUpload;
import com.mvpyouedu.YouEdu_api.service.UploadService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/video")
public class PublicarVideoController {

    @Autowired
    private UploadService uploadService;


    @PostMapping("/upload")
    @Transactional
    public ResponseEntity publicarVideo(@RequestParam("file") MultipartFile file, @RequestParam(required = false) @Valid DadodParaUpload dadosUpload){



        String respostaUpload = uploadService.uploadFile(file);

        return ResponseEntity.ok(respostaUpload);

    }
    @GetMapping("/uploads")
    public List<String> listarVideos(){

        File folder = new File("uploads");

        return Arrays.stream(folder.listFiles())
                .filter(file -> file.getName().endsWith(".mp4"))
                .map(file -> "http://localhost:8080/uploads/" + file.getName())
                .collect(Collectors.toList());

    }

}
