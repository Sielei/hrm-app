package com.hrmapp.user.infrastructure.adapter;

import com.hrmapp.user.application.port.output.UserRepository;
import com.hrmapp.user.domain.entity.User;
import com.hrmapp.user.domain.valueobject.UserStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FakeUserDB implements UserRepository {
    private final List<User> users = new ArrayList<>();
    public FakeUserDB(){
        var user1 = User.builder()
                .id(UUID.fromString("03cc5361-b27c-47d9-af59-ddecd384ed93"))
                .username("JUN001")
                .emailAddress("tjun@gmail.com")
                .employeeId(UUID.fromString("6b853a7d-244b-4633-adf7-5c60d1788fff"))
                .status(UserStatus.ACTIVE)
                .passwordPolicyId(UUID.fromString("76b3e3ff-aa47-42d0-92d2-d7793371c5fc"))
                .createdBy(UUID.fromString("03cc5361-b27c-47d9-af59-ddecd384ed93"))
                .changePasswordNextLogin(false)
                .build();
        var user2 = User.builder()
                .id(UUID.fromString("2886e348-812a-407b-9a5a-eeac22b38b36"))
                .username("MEL001")
                .emailAddress("mmonroe@gmail.com")
                .employeeId(UUID.fromString("347b48c1-3e38-4bd1-bcb2-7617f930a84c"))
                .status(UserStatus.ACTIVE)
                .passwordPolicyId(UUID.fromString("76b3e3ff-aa47-42d0-92d2-d7793371c5fc"))
                .createdBy(UUID.fromString("03cc5361-b27c-47d9-af59-ddecd384ed93"))
                .changePasswordNextLogin(false)
                .build();
        var user3 = User.builder()
                .id(UUID.fromString("320c9635-c767-411b-af91-73685ea77490"))
                .username("BEL001")
                .emailAddress("bbel@gmail.com")
                .employeeId(UUID.fromString("218ca56c-f228-4ee5-aee2-5aa97fb9daa1"))
                .status(UserStatus.ACTIVE)
                .passwordPolicyId(UUID.fromString("76b3e3ff-aa47-42d0-92d2-d7793371c5fc"))
                .createdBy(UUID.fromString("03cc5361-b27c-47d9-af59-ddecd384ed93"))
                .changePasswordNextLogin(false)
                .build();
        users.addAll(List.of(user1, user2, user3));
    }
    @Override
    public Optional<User> findByUsername(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username))
                .findFirst();
    }
}
