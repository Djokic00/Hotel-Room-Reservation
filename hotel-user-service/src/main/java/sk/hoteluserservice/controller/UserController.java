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
import sk.hoteluserservice.dto.*;
import sk.hoteluserservice.listener.helper.MessageHelper;
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
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }


    public UserController(UserService userService, JmsTemplate jmsTemplate, MessageHelper messageHelper,
                          @Value("${destination.registerClient}") String clientRegisterDestination) {
        this.userService = userService;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.clientRegisterDestination = clientRegisterDestination;
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

    @PostMapping("/registration/activemq")
    public ResponseEntity<Void> registerClient(@RequestBody @Valid ClientCreateDto clientCreateDto) {
        jmsTemplate.convertAndSend(clientRegisterDestination, messageHelper.createTextMessage(new ClientCreateDto()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Login")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(userService.login(tokenRequestDto), HttpStatus.OK);
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
}
