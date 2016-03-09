package com.codeferm.services.jaxrs;

import java.beans.ConstructorProperties;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 * @author sgoldsmith
 */
public class UserListDto {

    private static final long serialVersionUID = 3077868741267353368L;

    @NotNull
    private List<UserDto> userDtoList;

    public UserListDto() {
    }

    @ConstructorProperties({"userDtoList"})
    public UserListDto(final List<UserDto> userDtoList) {
        this.userDtoList = userDtoList;
    }

    public List<UserDto> getUserDtoList() {
        return userDtoList;
    }

    public void setUserDtoList(List<UserDto> userDtoList) {
        this.userDtoList = userDtoList;
    }

    @Override
    public String toString() {
        return "UserListDto{" + "userDtoList=" + getUserDtoList() + '}';
    }
}
