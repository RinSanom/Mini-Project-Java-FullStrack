package model.antities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserModel {
    private Integer userId;
    private String UUID;
    private String userName;
    private String email;
    private String password;
    private boolean isDeleted;

    //
//    private String name;
//    private String category;
//    private double price;
//    private int stock;
}
