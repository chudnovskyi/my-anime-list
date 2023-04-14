package com.myanimelist.view;

import com.myanimelist.validation.Email;
import com.myanimelist.validation.FieldMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * I use a separate entity class for
 * validation to avoid annotation buildup
 * and make the code more maintainable.
 * <p>
 * Initially, instances of this object
 * will be transferred into html form,
 * and after validation, at the service
 * layer, all fields will be transferred
 * to the "real" User object.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldMatch.List({
        @FieldMatch(firstField = "password", secondField = "matchingPassword", message = "{user.confirmPassword.match}")
})
public class UserView {

    @NotNull(message = "{user.username.required}")
    @Size(min = 3, message = "{user.username.minLength}")
    private String username;

    @NotNull(message = "{user.password.required}")
    @Size(min = 3, message = "{user.password.minLength}")
    private String password;

    @NotNull(message = "{user.confirmPassword.required}")
    private String matchingPassword;

    @NotNull(message = "{user.email.required}")
    @Email(domains = {"gmail.com", "karazin.ua"}, message = "{user.email.invalidDomain}")
    private String email;
}
