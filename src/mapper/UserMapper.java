package mapper;

import model.dto.UserResponDto;

public class UserMapper {
    public static UserResponDto mapFromUserModelToUserResponDto(UserResponDto userModel) {
        return new UserResponDto(userModel.userId(), userModel.userName(), userModel.email());
    }
}
