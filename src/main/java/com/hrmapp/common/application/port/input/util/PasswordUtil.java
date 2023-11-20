package com.hrmapp.common.application.port.input.util;

import com.hrmapp.common.application.dto.UserDto;
import com.hrmapp.user.application.port.input.handler.query.PasswordPolicyQueryHandler;
import com.hrmapp.user.domain.exception.UserDomainException;
import org.passay.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class PasswordUtil {
    private final PasswordPolicyQueryHandler passwordPolicyQueryHandler;

    public PasswordUtil(PasswordPolicyQueryHandler passwordPolicyQueryHandler) {
        this.passwordPolicyQueryHandler = passwordPolicyQueryHandler;
    }

    public void validatePassword(UserDto user, String newPassword) {
        var passwordPolicy = passwordPolicyQueryHandler.findPasswordPolicyById(user.passwordPolicyId());
        var passwordValidator = new PasswordValidator(
                new LengthRule(passwordPolicy.numberOfCharacters(), passwordPolicy.numberOfCharacters()*2),
                new CharacterRule(EnglishCharacterData.Special, passwordPolicy.numberOfSpecialCharacters()),
                new CharacterRule(EnglishCharacterData.Digit, passwordPolicy.numberOfNumericCharacters()),
                new CharacterRule(EnglishCharacterData.LowerCase, passwordPolicy.numberOfLowercaseCharacters()),
                new CharacterRule(EnglishCharacterData.UpperCase, passwordPolicy.numberOfUppercaseCharacters())
        );
        var result = passwordValidator.validate(new PasswordData(newPassword));
        if (!result.isValid()){
            throw new UserDomainException(passwordValidator.getMessages(result).get(0));
        }
    }
    public String generateNewUserPassword(UUID passwordPolicyId) {
        var passwordPolicy = passwordPolicyQueryHandler.findPasswordPolicyById(passwordPolicyId);
        List<CharacterRule> passwordRules = Arrays.asList(
                new CharacterRule(EnglishCharacterData.Special, passwordPolicy.numberOfSpecialCharacters()),
                new CharacterRule(EnglishCharacterData.Digit, passwordPolicy.numberOfNumericCharacters()),
                new CharacterRule(EnglishCharacterData.LowerCase, passwordPolicy.numberOfLowercaseCharacters()),
                new CharacterRule(EnglishCharacterData.UpperCase, passwordPolicy.numberOfUppercaseCharacters())
        );
        var passwordGenerator = new PasswordGenerator();
        return passwordGenerator.generatePassword(passwordPolicy.numberOfCharacters(), passwordRules);
    }
}
