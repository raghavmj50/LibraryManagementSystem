package com.avaali.library.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

    @Getter
    @Setter
    public class MemberResponse {

        private Integer id;
        private String name;
        private String email;
        private String phone;
        private Long activeLoanCount;
        private LocalDate registeredOn;
    }

