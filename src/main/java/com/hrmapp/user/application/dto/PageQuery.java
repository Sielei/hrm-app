package com.hrmapp.user.application.dto;

public record PageQuery(int pageNo, int pageSize) {
    public static Builder builder() {
        return new Builder();
    }
    public static final class Builder {
        private int pageNo;
        private int pageSize;

        private Builder() {
        }

        public Builder pageNo(int val) {
            pageNo = val;
            return this;
        }

        public Builder pageSize(int val) {
            pageSize = val;
            return this;
        }

        public PageQuery build() {
            return new PageQuery(pageNo, pageSize);
        }
    }
}
