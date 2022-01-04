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
        userRepository.save(manager);
        return userMapper.userToUserDto(manager);
    }

    @Override
    public UserDto addClient(ClientCreateDto clientCreateDto) {
        Client client = userMapper.clientCreateDtoToClient(clientCreateDto);
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
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().getName());
        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }
}
