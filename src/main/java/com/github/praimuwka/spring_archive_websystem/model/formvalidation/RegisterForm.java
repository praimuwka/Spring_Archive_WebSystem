package com.github.praimuwka.spring_archive_websystem.model.formvalidation;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterForm {
    @NotNull(message = "выберите вариант из списка")
    private Long docId;
    @NotNull(message = "поле не должно быть пустым")
    @Size(min = 5, message = "введите ПОЛНОЕ ФИО")
    private String employee;
    private Long depId;
}
