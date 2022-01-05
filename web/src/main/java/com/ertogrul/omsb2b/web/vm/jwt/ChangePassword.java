package com.ertogrul.omsb2b.web.vm.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ertogrul Selimli on 11/7/2021
 * @project IntelliJ IDEA
 */
@Getter
@Setter
@NoArgsConstructor
public class ChangePassword {

    private String newPassword;

    private String confirmPassword;

}
