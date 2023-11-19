package com.hrmapp.user.infrastructure.adapter;

import com.hrmapp.user.application.port.output.PasswordResetRepository;
import com.hrmapp.user.domain.entity.PasswordReset;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class FakePasswordResetDB implements PasswordResetRepository {
    private final List<PasswordReset> passwordResets = new ArrayList<>();
    public FakePasswordResetDB(){
        var expiry = LocalDate.now().plusDays(1);
        var reset = PasswordReset.builder()
                .id(UUID.fromString("440f23d1-a4fc-43cb-a5b1-c5b18c830db7"))
                .userId(UUID.fromString("03cc5361-b27c-47d9-af59-ddecd384ed93"))
                .token(UUID.fromString("de8e4d56-46e4-4610-ab9b-bef4f86ad945").toString())
                .expiry(Date.from(expiry.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
        var reset1 = PasswordReset.builder()
                .id(UUID.fromString("702807ad-a9b4-4187-ae31-e9eeb3ee480c"))
                .userId(UUID.fromString("2886e348-812a-407b-9a5a-eeac22b38b36"))
                .token(UUID.fromString("d8730010-a2e3-4a6c-9084-0b3c984d0bab").toString())
                .expiry(Date.from(expiry.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .build();
        passwordResets.addAll(List.of(reset, reset1));
    }
    @Override
    public PasswordReset save(PasswordReset passwordReset) {
        var existingReset = passwordResets.stream()
                .filter(reset -> reset.getId().equals(passwordReset.getId()))
                .findFirst();
        if (existingReset.isEmpty()){
            passwordResets.add(passwordReset);
        }
        else {
            var index = passwordResets.indexOf(existingReset.get());
            passwordResets.set(index, passwordReset);
        }
        return passwordReset;
    }

    @Override
    public Optional<PasswordReset> findPasswordResetToken(String token) {
        return passwordResets.stream()
                .filter(passwordReset -> passwordReset.getToken().equals(token)).findFirst();
    }
}
