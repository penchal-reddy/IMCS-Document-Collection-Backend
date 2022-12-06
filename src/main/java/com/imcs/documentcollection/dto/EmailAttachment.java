package com.imcs.documentcollection.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmailAttachment {
    @NotNull
    private String base64String;
    @NotNull
    private String fileName;

}
