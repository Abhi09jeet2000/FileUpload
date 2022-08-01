package com.code.filemngt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.code.filemngt.entity.Attachment;
import com.code.filemngt.model.ResponseData;
import com.code.filemngt.service.AttachmentService;

@RestController
public class AttachmentController {
    
    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/upload")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file) throws Exception{
        Attachment attachment = null;
        String downloadUrl="";
        attachment = attachmentService.saveAttachment(file);
        downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/download/")
                        .path(attachment.getId())
                        .toUriString();

        return new ResponseData(attachment.getFileName(),
            downloadUrl,
            file.getContentType(),
            file.getSize());
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception{
        Attachment attachment = null;
        attachment = attachmentService.getAttachment(fileId);
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(attachment.getFileType()))
            .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\""+attachment.getFileName()
            +"\"")
            .body(new ByteArrayResource(attachment.getData()));
    }


}
