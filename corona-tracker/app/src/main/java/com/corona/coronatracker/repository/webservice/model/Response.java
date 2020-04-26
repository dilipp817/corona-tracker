package com.corona.coronatracker.repository.webservice.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;

public class Response<T> {

    public final Status status;

    @Nullable
    public final T data;

    @NonNull
    public final Throwable e;

    private Response(Status status, @Nullable T data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        if (error == null)
            error = new Throwable("No error message");
        if(error instanceof NullPointerException || error instanceof JSONException)
            error = new Throwable("An unexpected error occurred.");
        this.e = error;
    }

    public static <T> Response<T> loading() {
        return new Response<>(Status.LOADING, null, null);
    }

    public static <T> Response<T> success(@NonNull T data) {
        return new Response<>(Status.SUCCESS, data, null);
    }

    public static <T> Response<T> error(@NonNull Throwable error) {
        return new Response<>(Status.ERROR, null, error);
    }

    public enum Status {SUCCESS, ERROR, LOADING}
}
