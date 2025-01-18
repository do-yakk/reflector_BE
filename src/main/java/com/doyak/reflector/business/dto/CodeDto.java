package com.doyak.reflector.business.dto;

import lombok.Getter;

public class CodeDto {

    @Getter
    public static class Code {
        private String code;
        private String review;
        private Float performanceTime;
        private Float performanceMem;
    }

}
