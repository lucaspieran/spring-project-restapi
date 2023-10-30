package com.lucas.springsecurity.dto;

import com.lucas.springsecurity.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleDTO {
    private Role role;
}
