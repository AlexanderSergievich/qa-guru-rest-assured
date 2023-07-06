package models;

import lombok.Data;
@Data
public class ExistingUserResponseModel {
    private Data data;
    private Support support;
    public static class Support {
        public String url;
        public String text;
    }
    @lombok.Data
    public class Data {
        private String id;
        private String email;
        private String first_name;
        private String last_name;
        private String avatar;
}
}
