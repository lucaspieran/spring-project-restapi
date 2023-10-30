package com.lucas.springsecurity.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class FileUpload {
    private static Storage storage = StorageOptions.getDefaultInstance().getService();

    public String upload(MultipartFile file) {
        try {
            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder("spring_bucket_lucas", file.getOriginalFilename()).build(),
                    file.getBytes(),
                    Storage.BlobTargetOption.predefinedAcl(Storage.PredefinedAcl.PUBLIC_READ)
           );

            return blobInfo.getMediaLink();
        }catch(IllegalStateException | IOException e){
            throw new RuntimeException("Error uploading file to GCS", e);
        }
    }
}
