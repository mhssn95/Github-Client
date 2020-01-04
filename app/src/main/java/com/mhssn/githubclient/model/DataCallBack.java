package com.mhssn.githubclient.model;

public interface DataCallBack<T> {
    void onSuccess(T data);
    void onError(String message);
}
