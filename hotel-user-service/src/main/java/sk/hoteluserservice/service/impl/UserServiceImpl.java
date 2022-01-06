package sk.hoteluserservice.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.hoteluserservice.domain.Client;
import sk.hoteluserservice.domain.Manager;
import sk.hoteluserservice.domain.User;
import sk.hoteluserservice.dto.*;
import sk.hoteluserservice.exception.NotFoundException;
import sk.hoteluserservice.listener.helper.MessageHelper;
import sk.hoteluserservice.mapper.UserMapper;
import sk.hoteluserservice.repository.ClientRepository;
import sk.hoteluserservice.repository.ManagerRepository;
import sk.hoteluserservice.repository.UserRepository;
import sk.hoteluserservice.security.service.TokenService;
import sk.hoteluserservice.service.UserService;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private TokenService tokenService;
    private ClientRepository clientRepository;
    private ManagerRepository managerRepository;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;
    private String clientRegisterDestination;

//    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, TokenService tokenService) {
//        this.userRepository = userRepository;
//        this.userMapper = userMapper;
//        this.tokenService = tokenService;
//    }


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, TokenService tokenService,
                           ClientRepository clientRepository, ManagerRepository managerRepository,
                           JmsTemplate jmsTemplate,@Value("${destination.registerClient}") String clientRegisterDestination) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.clientRepository = clientRepository;
        this.managerRepository = managerRepository;
        this.jmsTemplate = jmsTemplate;
        this.clientRegisterDestination = clientRegisterDestination;
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::userToUserDto);
    }

    @Override
    public UserDto addManager(ManagerCreateDto managerCreateDto) {
        Manager manager = userMapper.managerCreateDtoToManager(managerCreateDto);
        manager.setBanned(false);
        userRepository.save(manager);
        return userMapper.userToUserDto(manager);
    }

    @Override
    public UserDto addClient(ClientCreateDto clientCreateDto) {
        Client client = userMapper.clientCreateDtoToClient(clientCreateDto);
        client.setBanned(false);
        userRepository.save(client);
        return userMapper.userToUserDto(client);
    }

    @Override
    public void registerClient(ClientCreateDto clientCreateDto) {
        jmsTemplate.convertAndSend(clientRegisterDestination, messageHelper.createTextMessage(clientCreateDto));
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        //Try to find active user for specified credentials
        User user = userRepository
                .findUserByUsernameAndPassword(tokenRequestDto.getUsername(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s and password: %s not found.", tokenRequestDto.getUsername(),
                                tokenRequestDto.getPassword())));
        //Create token payload
        if (user.getBanned()==false) {
            Claims claims = Jwts.claims();
            claims.put("id", user.getId());
            claims.put("role", user.getRole().getName());

            //Generate token
            return new TokenResponseDto(tokenService.generate(claims));
        } else return new TokenResponseDto();

    }

    @Override
    public ClientDto update(Long id, ClientCreateDto clientCreateDto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Client with id: %d not found.", id)));
        //Set values to product
        client.setUsername(clientCreateDto.getUsername());
        client.setEmail(clientCreateDto.getEmail());
        client.setFirstName(clientCreateDto.getFirstName());
        client.setLastName(clientCreateDto.getLastName());
        client.setPassword(clientCreateDto.getPassword());
        client.setPassportNumber(clientCreateDto.getPassportNumber());
        client.setContact(clientCreateDto.getContact());
        //Map product to DTO and return it
        return userMapper.clientToClientDto(userRepository.save(client));
    }

    @Override
    public ClientDto updatePass(Long id, PasswordClientDto passwordClientDto) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Client with id: %d not found.", id)));
        //Set values to product

        client.setPassword(passwordClientDto.getPassword());

        //Map product to DTO and return it
        return userMapper.clientToClientDto(userRepository.save(client));

    }

    @Override
    public UserDto banUser(Long id, BanUserDto banUserDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found.", id)));
        //Set values to product

        user.setBanned(true);

        //Map product to DTO and return it
        return userMapper.userToUserDto(userRepository.save(user));

    }

    @Override
    public UserDto unbanUser(Long id, BanUserDto banUserDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found.", id)));
        //Set values to product

        user.setBanned(false);

        //Map product to DTO and return it
        return userMapper.userToUserDto(userRepository.save(user));
    }

}
