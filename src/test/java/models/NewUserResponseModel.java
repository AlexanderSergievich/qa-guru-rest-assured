package models;

import lombok.Data;

@Data
public class NewUserResponseModel {
    public String name;
    public String job;
    public String id;
    public String createdAt;
    public String email;
}
