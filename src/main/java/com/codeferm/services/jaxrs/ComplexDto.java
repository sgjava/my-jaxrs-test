package com.codeferm.services.jaxrs;

import java.beans.ConstructorProperties;
import javax.validation.constraints.NotNull;

/**
 * @author sgoldsmith
 */
public class ComplexDto {

    private static final long serialVersionUID = 3077868741267353356L;

    @NotNull
    private UserListDto userListDto;

    public ComplexDto() {
    }

    @ConstructorProperties({"userListDto"})
    public ComplexDto(final UserListDto userListDto) {
        this.userListDto = userListDto;
    }

    public UserListDto getUserListDto() {
        return userListDto;
    }

    public void setUserListDto(UserListDto userListDto) {
        this.userListDto = userListDto;
    }

    @Override
    public String toString() {
        return "ComplexDto{" + "userListDto=" + getUserListDto() + '}';
    }
}
