package com.lucas.springsecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    private Optional<String> firstName;
    private Optional<String> lastName;
    private Optional<MultipartFile> file;
}