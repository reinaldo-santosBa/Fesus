package com.example.fefsus.utils;

public interface ApiResponseListener {
    void onResponse(String responseBody);
    void onError(Throwable throwable);

}
