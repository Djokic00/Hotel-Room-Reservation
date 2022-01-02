package sk.hoteluserservice.runner;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import sk.hoteluserservice.domain.Role;
import sk.hoteluserservice.domain.User;
import sk.hoteluserservice.repository.ClientRepository;
import sk.hoteluserservice.repository.ManagerRepository;
import sk.hoteluserservice.repository.RoleRepository;
import sk.hoteluserservice.repository.UserRepository;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private ClientRepository clientRepository;
    private ManagerRepository managerRepository;

//    public TestDataRunner(RoleRepository roleRepository, UserRepository userRepository) {
//        this.roleRepository = roleRepository;
//        this.userRepository = userRepository;
//    }

    public TestDataRunner(RoleRepository roleRepository, UserRepository userRepository, ClientRepository clientRepository, ManagerRepository managerRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.managerRepository = managerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Insert roles
        Role roleUser = new Role("ROLE_CLIENT", "Client role");
        Role roleManager = new Role("ROLE_MANAGER", "Manager role");
        Role roleAdmin = new Role("ROLE_ADMIN", "Admin role");

        roleRepository.save(roleUser);
        roleRepository.save(roleManager);
        roleRepository.save(roleAdmin);
        //Insert admin
        User admin = new User();
        admin.setEmail("admin@gmail.com");
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRole(roleAdmin);

        userRepository.save(admin);

    }
}