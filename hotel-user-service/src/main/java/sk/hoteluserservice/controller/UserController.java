package sk.hoteluserservice.controller;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import sk.hoteluserservice.domain.Client;
import sk.hoteluserservice.dto.*;
import sk.hoteluserservice.listener.helper.MessageHelper;
import sk.hoteluserservice.mapper.UserMapper;
import sk.hoteluserservice.security.CheckSecurity;
import sk.hoteluserservice.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {


    private UserService userService;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;
    private String clientRegisterDestination;
    private UserMapper userMapper;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }


    public UserController(UserService userService, JmsTemplate jmsTemplate, MessageHelper messageHelper,
                          @Value("${destination.registerClient}") String clientRegisterDestination,
                          UserMapper userMapper) {
        this.userService = userService;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.clientRegisterDestination = clientRegisterDestination;
        this.userMapper = userMapper;
    }

    @ApiOperation(value = "Get all users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_CLIENT"})
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestHeader("Authorization") String authorization,
                                                     Pageable pageable) {

        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }


    @ApiOperation(value = "Register client")
    @PostMapping("/registration/client")
    public ResponseEntity<UserDto> saveClient(@RequestBody @Valid ClientCreateDto clientCreateDto) {
        return new ResponseEntity<>(userService.addClient(clientCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Register manager")
    @PostMapping("/registration/manager")
    public ResponseEntity<UserDto> saveManager(@RequestBody @Valid ManagerCreateDto managerCreateDto) {
        return new ResponseEntity<>(userService.addManager(managerCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Register client with notification")
    @PostMapping("/registration/activemq")
    public ResponseEntity<Void> registerClient(@RequestBody @Valid ClientCreateDto clientCreateDto) {
        Client client = userMapper.clientCreateDtoToClient(clientCreateDto);
        ClientDto clientDto = userMapper.clientToClientDto(client);
        userService.addClient(clientCreateDto);
        jmsTemplate.convertAndSend(clientRegisterDestination, messageHelper.createTextMessage(clientDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Login")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
            TokenResponseDto token=userService.login(tokenRequestDto);
            if (token.getToken()!=null) {
                return new ResponseEntity<>(token, HttpStatus.OK);
            }else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @ApiOperation(value = "Client Update")
    @PostMapping("/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable("id") Long id,
                                            @RequestBody @Valid ClientCreateDto clientCreateDto) {
        return new ResponseEntity<>(userService.update(id, clientCreateDto), HttpStatus.OK);
    }


    @ApiOperation(value = "Client Password Update")
    @PostMapping("/{id}/password")
    public ResponseEntity<ClientDto> updatePassword(@PathVariable("id") Long id,
                                            @RequestBody @Valid PasswordClientDto passwordClientDto) {
        return new ResponseEntity<>(userService.updatePass(id, passwordClientDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Ban user")
    @PostMapping("/{id}/ban")
   // @CheckSecurity(roles = {"ROLE_ADMIN"})
      public ResponseEntity<UserDto> banUser(@PathVariable("id") Long id,
                                                    @RequestBody @Valid BanUserDto banUserDto) {
        return new ResponseEntity<>(userService.banUser(id, banUserDto), HttpStatus.OK);
    }
    @ApiOperation(value = "Unban user")
    @PostMapping("/{id}/unban")
    //@CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<UserDto> unbanUser(@PathVariable("id") Long id,
                                           @RequestBody @Valid BanUserDto banUserDto) {
        return new ResponseEntity<>(userService.unbanUser(id, banUserDto), HttpStatus.OK);
    }



}
