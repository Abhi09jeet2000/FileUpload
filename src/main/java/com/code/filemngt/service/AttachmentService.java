package com.code.filemngt.service;

import org.springframework.web.multipart.MultipartFile;

import com.code.filemngt.entity.Attachment;

public interface AttachmentService {

    Attachment saveAttachment(MultipartFile file) throws Exception;

    Attachment getAttachment(String fileId) throws Exception;
    
}
