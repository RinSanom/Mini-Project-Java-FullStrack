package mapper;

import model.dto.UserResponDto;
import model.entities.UserModel;

public class UserMapper {
    public static UserResponDto mapFromUserModelToUserResponDto(UserModel userModel) {
        return new UserResponDto(userModel.getUserId(), userModel.getUserName(), userModel.getEmail());
    }
}
