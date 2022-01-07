package sk.hoteluserservice.mapper;

import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Component;
import sk.hoteluserservice.domain.Client;
import sk.hoteluserservice.domain.Manager;
import sk.hoteluserservice.domain.User;
import sk.hoteluserservice.dto.ClientCreateDto;
import sk.hoteluserservice.dto.ClientDto;
import sk.hoteluserservice.dto.ManagerCreateDto;
import sk.hoteluserservice.dto.UserDto;
import sk.hoteluserservice.repository.RoleRepository;

@Component
public class UserMapper {

    private RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());

        return userDto;
    }

    public ClientDto clientToClientDto(Client client) {
        ClientDto clientDto = new ClientDto();
       // clientDto.setBirthday(client.getBirthday());
        clientDto.setContact(client.getContact());
        clientDto.setEmail(client.getEmail());
        clientDto.setFirstName(client.getFirstName());
        clientDto.setUsername(client.getUsername());
        clientDto.setLastName(client.getLastName());
        clientDto.setPassportNumber(client.getPassportNumber());
        clientDto.setNumberOfReservations(client.getNumberOfReservations());
        clientDto.setEnabled(client.isEnabled());
        clientDto.setVerificationCode(client.getVerificationCode());

        return clientDto;
    }

    public Client clientCreateDtoToClient(ClientCreateDto clientCreateDto) {
        Client client = new Client();
        client.setEmail(clientCreateDto.getEmail());
        client.setFirstName(clientCreateDto.getFirstName());
        client.setLastName(clientCreateDto.getLastName());
        client.setUsername(clientCreateDto.getUsername());
        client.setPassword(clientCreateDto.getPassword());
       // client.setBirthday(clientCreateDto.getBirthday());
        client.setContact(clientCreateDto.getContact());
        client.setPassportNumber(clientCreateDto.getPassportNumber());
        client.setRole(roleRepository.findRoleByName("ROLE_CLIENT").get());
        client.setNumberOfReservations(0);
        String randomCode = RandomString.make(64);
        client.setVerificationCode(randomCode);
        client.setEnabled(false);


        return client;
    }

    public Manager managerCreateDtoToManager(ManagerCreateDto managerCreateDto) {
        Manager manager = new Manager();
        manager.setEmail(managerCreateDto.getEmail());
        manager.setFirstName(managerCreateDto.getFirstName());
        manager.setLastName(managerCreateDto.getLastName());
        manager.setUsername(managerCreateDto.getUsername());
        manager.setPassword(managerCreateDto.getPassword());
       // manager.setBirthday(managerCreateDto.getBirthday());
        manager.setContact(managerCreateDto.getContact());
        manager.setHotelName(managerCreateDto.getHotelname());
//        manager.setHiringDate(managerCreateDto.getHiringDate());
        manager.setRole(roleRepository.findRoleByName("ROLE_MANAGER").get());
        String randomCode = RandomString.make(64);
        manager.setVerificationCode(randomCode);
        manager.setEnabled(false);

        return manager;
    }


}