package com.codeferm.services.jaxrs;

import java.beans.ConstructorProperties;
import javax.validation.constraints.NotNull;

/**
 * @author sgoldsmith
 */
public class UserDto extends BaseDto {

    private static final long serialVersionUID = 3077868741267353366L;

    @NotNull
    private String userName;
    @NotNull
    private String fullName;

    public UserDto() {
    }

    @ConstructorProperties({"id", "userName", "fullName"})
    public UserDto(final Integer id, final String userName,
            final String fullName) {
        super(id);
        this.userName = userName;
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "UserDto{" + "id=" + getId() + ", userName=" + userName
                + ", fullName=" + fullName + '}';
    }
}
