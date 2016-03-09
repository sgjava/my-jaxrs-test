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
    private UserListDto retUserListDto;

    public ComplexDto() {
    }

    @ConstructorProperties({"userListDto"})
    public ComplexDto(final UserListDto userListDto) {
        this.userListDto = userListDto;
        this.retUserListDto = userListDto;
    }

    public final UserListDto getUserListDto() {
        return userListDto;
    }

    public final void setUserListDto(final UserListDto userListDto) {
        this.userListDto = userListDto;
    }

    public final UserListDto getRetUserListDto() {
        return retUserListDto;
    }

    public final void setRetUserListDto(final UserListDto retUserListDto) {
        this.retUserListDto = retUserListDto;
    }

    @Override
    public String toString() {
        return "ComplexDto{" + "userListDto=" + getUserListDto()
                + ", retUserListDto=" + getRetUserListDto() + '}';
    }
}
