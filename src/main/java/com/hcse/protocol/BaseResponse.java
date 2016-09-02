package com.hcse.protocol;

import java.util.List;

public interface BaseResponse {
    public List<? extends BaseResponseDoc> getDocs();
}
