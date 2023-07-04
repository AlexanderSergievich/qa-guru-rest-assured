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
    public static class Data {
        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public String getAvatar() {
            return avatar;
        }

        private String id;
        private String email;
        private String first_name;
        private String last_name;
        private String avatar;
}
}
