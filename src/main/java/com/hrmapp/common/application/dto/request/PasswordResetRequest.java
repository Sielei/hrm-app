package com.hrmapp.common.application.dto.request;

public record PasswordResetRequest(String emailAddress) {
    public static Builder builder(){
        return new Builder();
    }
    public static class Builder{
        private String emailAddress;
        public Builder emailAddress(String val){
            emailAddress = val;
            return this;
        }
        public PasswordResetRequest build(){
            return new PasswordResetRequest(emailAddress);
        }
    }
}
