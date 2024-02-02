package org.wsd.app.bootloader;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.wsd.app.domain.RoleEntity;
import org.wsd.app.domain.UserEntity;
import org.wsd.app.repository.UserRepository;

import java.util.Set;
import java.util.UUID;


@Component
public class BootLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void run(String... args) throws Exception {
        UserEntity user = new UserEntity();
        user.setUserId(UUID.randomUUID());
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("123456"));
        RoleEntity role = new RoleEntity();
        role.setName("USER");
        user.setRoleEntities(Set.of(role));
        userRepository.save(user);
    }
}
