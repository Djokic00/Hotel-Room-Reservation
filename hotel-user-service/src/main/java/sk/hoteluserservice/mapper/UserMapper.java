package sk.hoteluserservice.mapper;

import org.springframework.stereotype.Component;
import sk.hoteluserservice.domain.Client;
import sk.hoteluserservice.domain.Manager;
import sk.hoteluserservice.domain.User;
import sk.hoteluserservice.dto.*;
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
        client.setNumberOfReservations(8);

        return client;
    }

    public ManagerDto managerToManagerDto(Manager manager) {
        ManagerDto managerDto = new ManagerDto();
        managerDto.setContact(manager.getContact());
        managerDto.setEmail(manager.getEmail());
        managerDto.setFirstName(manager.getFirstName());
        managerDto.setUsername(manager.getUsername());
        managerDto.setLastName(manager.getLastName());
        managerDto.getHotelName(manager.getHotelName());

        return managerDto;
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

        return manager;
    }



}