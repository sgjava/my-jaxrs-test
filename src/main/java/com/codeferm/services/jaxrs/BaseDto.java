package com.codeferm.services.jaxrs;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import javax.validation.constraints.NotNull;

/**
 * @author sgoldsmith
 */
public class BaseDto implements Serializable {

    private static final long serialVersionUID = 3077868741267353369L;

    @NotNull
    private Integer id;

    public BaseDto() {
    }

    @ConstructorProperties({"id"})
    public BaseDto(final Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }
}
