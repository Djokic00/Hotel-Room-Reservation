package sk.hoteluserservice.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.hoteluserservice.domain.*;
import sk.hoteluserservice.dto.*;
import sk.hoteluserservice.exception.NotFoundException;
import sk.hoteluserservice.listener.helper.MessageHelper;
import sk.hoteluserservice.mapper.UserMapper;
import sk.hoteluserservice.repository.ClientRepository;
import sk.hoteluserservice.repository.ClientStatusRepository;
import sk.hoteluserservice.repository.ManagerRepository;
import sk.hoteluserservice.repository.UserRepository;
import sk.hoteluserservice.security.service.TokenService;
import sk.hoteluserservice.service.UserService;

import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private TokenService tokenService;
    private ClientRepository clientRepository;
    private ClientStatusRepository clientStatusRepository;
    private ManagerRepository managerRepository;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;
    private String clientRegisterDestination;
    private String findEmailDestination;
    private String resetPasswordDestination;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, TokenService tokenService,
                           ClientRepository clientRepository, ManagerRepository managerRepository,
                           ClientStatusRepository clientStatusRepository,
                           JmsTemplate jmsTemplate,@Value("${destination.registerClient}") String clientRegisterDestination,
                           @Value("${destination.findEmail}") String findEmailDestination, MessageHelper messageHelper,
                           @Value("${destination.resetPassword}") String resetPasswordDestination) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.clientRepository = clientRepository;
        this.managerRepository = managerRepository;
        this.jmsTemplate = jmsTemplate;
        this.clientRegisterDestination = clientRegisterDestination;
        this.clientStatusRepository = clientStatusRepository;
        this.findEmailDestination = findEmailDestination;
        this.messageHelper = messageHelper;
        this.resetPasswordDestination = resetPasswordDestination;
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
        if (user.getBanned()==false && user.isEnabled()==true) {
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

    public ClientDto updatePassportNumber(Long id, PassportClientDto passportClientDto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Client with id: %d not found.", id)));
        client.setPassportNumber(passportClientDto.getPassportNumber());

        return userMapper.clientToClientDto(userRepository.save(client));
    }

    @Override
    public UserDto updatePassword(Long id, PasswordUserDto passwordUserDto) {

        User user = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Client with id: %d not found.", id)));
        //Set values to product

        user.setPassword(passwordUserDto.getPassword());

        //Map product to DTO and return it
        return userMapper.userToUserDto(userRepository.save(user));
    }

    @Override
    public ManagerDto updateHotelName(Long id, HotelNameManagerDto hotelNameManagerDto) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Client with id: %d not found.", id)));

        manager.setHotelName(hotelNameManagerDto.getHotelName());
        return userMapper.managerToManagerDto(userRepository.save(manager));
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

    @Override
    public Boolean verify(String token) {
        User user = userRepository.findUserByVerificationCode(token)
                .orElseThrow(() -> new NotFoundException("not found"));
        //Set values to product

        user.setEnabled(true);
        user.setVerificationCode(null);

        //Map product to DTO and return it
        UserDto userDto = userMapper.userToUserDto(userRepository.save(user));

        // OVO TREBA PROMENITI
        if(!userDto.equals(null)){
            return true;
        } else return false;
    }


    @Override
    public ClientStatusDto findDiscount(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String
                .format("User with id: %d not found.", id)));
        List<ClientStatus> clientStatusList = clientStatusRepository.findAll();
        //get discount
        ClientStatus status = clientStatusList.stream()
                .filter(clientStatus -> clientStatus.getMaxNumberOfReservations() >= client.getNumberOfReservations()
                        && clientStatus.getMinNumberOfReservations() <= client.getNumberOfReservations())
                .findAny()
                .get();
        return new ClientStatusDto(status.getDiscount(), status.getRank());

        //return new ClientStatusDto(0, "regular"); // ovo stoji ako hocemo retry da probamo
    }

    @Override
    public ClientStatusDto updateRankingSystem(Long id, ClientStatusCreateDto clientStatusCreateDto) {
        ClientStatus clientStatus = clientStatusRepository.getById(id);
        clientStatus.setRank(clientStatusCreateDto.getRank());
        clientStatus.setDiscount(clientStatusCreateDto.getDiscount());
        clientStatus.setMinNumberOfReservations(clientStatusCreateDto.getMinNumberOfReservations());
        clientStatus.setMaxNumberOfReservations(clientStatusCreateDto.getMaxNumberOfReservations());
        return userMapper.clientStatusToClientStatusDto(clientStatusRepository.save(clientStatus));
    }

    @Override
    public void changeNumberOfReservations(ClientQueueDto clientQueueDto) {
        Client client = clientRepository.findById(clientQueueDto.getUserId())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with that id is not found")));

        if (clientQueueDto.getIncrement()) client.setNumberOfReservations(client.getNumberOfReservations() + 1);
        else client.setNumberOfReservations(client.getNumberOfReservations() - 1);
        clientRepository.save(client);

        String hotelName = clientQueueDto.getHotelName();
        String city = clientQueueDto.getCity();
        Manager manager = managerRepository.findManagerByHotelNameAndCity(hotelName, city);

        ClientQueueDto newClientQueueDto = userMapper.clientToClientQueueDto(client);
        newClientQueueDto.setIncrement(clientQueueDto.getIncrement());
        newClientQueueDto.setBookingId(clientQueueDto.getBookingId());
        newClientQueueDto.setHotelName(clientQueueDto.getHotelName());
        newClientQueueDto.setCity(clientQueueDto.getCity());
        newClientQueueDto.setManagerEmail(manager.getEmail());
        newClientQueueDto.setManagerId(manager.getId());
        // messageQueueDto
        jmsTemplate.convertAndSend(findEmailDestination, messageHelper.createTextMessage(newClientQueueDto));
    }


    @Override
    public ClientStatusDto updateDiscount(Long id, DiscountCreateDto discountCreateDto) {
        ClientStatus clientStatus = clientStatusRepository.getById(id);
        clientStatus.setRank(discountCreateDto.getRank());
        clientStatus.setDiscount(discountCreateDto.getDiscount());
        return userMapper.clientStatusToClientStatusDto(clientStatusRepository.save(clientStatus));
    }

    @Override
    public void resetPassword(String email) {
//        ClientQueueDto clientQueueDto = new ClientQueueDto();
//        clientQueueDto.setEmail(email);
//        clientQueueDto.setUsername(clientRepository.findUserByEmail(email).getUsername());
//        jmsTemplate.convertAndSend(resetPasswordDestination, messageHelper.createTextMessage(clientQueueDto));
    }
}
