package com.codeferm.services.jaxrs;

import java.io.Serializable;

/**
 * @author sgoldsmith
 */
public class UserDto implements Serializable {

    private static final long serialVersionUID = 3077868741267353366L;

    private Integer id;
    private String userName;
    private String fullName;

    public UserDto() {
    }

    public UserDto(final Integer id, final String userName,
            final String fullName) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
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
        return "UserDto{" + "id=" + id + ", userName=" + userName
                + ", fullName=" + fullName + '}';
    }
}
