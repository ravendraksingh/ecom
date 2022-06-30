package com.rks.mcommon.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.rks.mcommon.constants.CommonConstants.RESPONSE_CODE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
    private String status;
    private String message;
    private Integer code;

    public BaseResponse(String status, String message) {
        this.message = message;
        this.status = status;
    }
}
