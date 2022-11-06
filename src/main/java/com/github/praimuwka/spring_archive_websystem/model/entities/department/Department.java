package com.github.praimuwka.spring_archive_websystem.model.entities.department;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_department")
    private Long id;
    @Size(min = 1, message = "поле не должно быть пустым")
    private String name;
    @NotNull(message = "поле не должно быть пустым")
    @Size(min = 2, max = 255)
    private String boss;
    @NotNull(message = "поле не должно быть пустым")
    @Size(min = 11, max = 12, message = "длина телефона 11-12 символов")
    @Pattern(regexp = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$",
            message = "допустимы лишь корректные российские номера")
    private String phone;
}
