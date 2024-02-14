package org.wsd.app.bootloader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.wsd.app.domain.RoleEntity;
import org.wsd.app.domain.UserEntity;
import org.wsd.app.dto.User;
import org.wsd.app.dto.UserDTO;
import org.wsd.app.mapper.UserMapper;
import org.wsd.app.repository.UserRepository;

import java.util.Set;
import java.util.UUID;


@Component
public class BootLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public BootLoader(UserRepository userRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

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

        User userEntity = new User();
        userEntity.setUsername("Partha Sutradhar");
        userEntity.setEmail("partharaj.dev@gmail.com");


        UserDTO userDTO = UserMapper.INSTANCE.toDTO(userEntity);
        System.out.println(userDTO);

        User userMapper = UserMapper.INSTANCE.toEmployee(userDTO);
        System.out.println(userMapper);


    }
}
