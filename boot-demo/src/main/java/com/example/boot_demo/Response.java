package com.example.boot_demo;

public class Response <T>{
    private T date;
    private boolean success;
    private String errorMsg;

    public static <K> Response<K> newSuccess(K data){
        Response<K> response =new Response<>();
        response.setDate(data);
        response.setSuccess(true);
        return response;
    }


    public static Response<Void> newFail(String errorMsg){
        Response <Void> response=new Response<>();
        response.setErrorMsg(errorMsg);
        response.setSuccess(false);
        return  response;
    }





    public T getDate() {

        return date;
    }

    public void setDate(T date) {
        this.date = date;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
