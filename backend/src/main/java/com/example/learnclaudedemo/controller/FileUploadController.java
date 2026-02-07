package com.example.learnclaudedemo.controller;

import com.example.learnclaudedemo.config.FileUploadConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
@Tag(name = "文件上传", description = "图片和文件上传相关接口")
public class FileUploadController {

    private final FileUploadConfig fileUploadConfig;

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${server.domain:http://localhost}")
    private String serverDomain;

    @PostMapping("/avatar")
    @Operation(summary = "上传头像")
    public Map<String, Object> uploadAvatar(@RequestParam("file") MultipartFile file) {
        return uploadFile(file, "avatars");
    }

    @PostMapping("/image")
    @Operation(summary = "上传图片")
    public Map<String, Object> uploadImage(@RequestParam("file") MultipartFile file) {
        return uploadFile(file, "images");
    }

    @PostMapping("/images")
    @Operation(summary = "批量上传图片")
    public Map<String, Object> uploadImages(@RequestParam("files") MultipartFile[] files) {
        Map<String, Object> result = new HashMap<>();
        try {
            String[] urls = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                Map<String, Object> singleResult = uploadFile(files[i], "images");
                urls[i] = (String) singleResult.get("url");
            }
            result.put("code", 200);
            result.put("message", "success");
            result.put("data", urls);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", e.getMessage());
        }
        return result;
    }

    private Map<String, Object> uploadFile(MultipartFile file, String subDir) {
        Map<String, Object> result = new HashMap<>();

        if (file.isEmpty()) {
            result.put("code", 400);
            result.put("message", "文件为空");
            return result;
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".")
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : "";

            String newFilename = UUID.randomUUID().toString() + extension;

            String uploadPath = fileUploadConfig.getUploadDir();
            Path targetDir = Paths.get(uploadPath, subDir);
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
            }

            Path targetPath = targetDir.resolve(newFilename);
            Files.copy(file.getInputStream(), targetPath);

            String fileUrl = serverDomain + ":" + serverPort + "/uploads/" + subDir + "/" + newFilename;

            result.put("code", 200);
            result.put("message", "上传成功");
            result.put("url", fileUrl);
            result.put("filename", newFilename);

        } catch (IOException e) {
            result.put("code", 500);
            result.put("message", "上传失败: " + e.getMessage());
        }

        return result;
    }
}
