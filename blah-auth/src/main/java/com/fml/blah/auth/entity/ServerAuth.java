package com.fml.blah.auth.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author y
 * @since 2022-02-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ServerAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String serverId;

    private String serverPassword;


}
