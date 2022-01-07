package sk.hoteluserservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sk.hoteluserservice.dto.*;

public interface UserService {

    Page<UserDto> findAll(Pageable pageable);


    UserDto addManager(ManagerCreateDto managerCreateDto);
    UserDto addClient(ClientCreateDto clientCreateDto);

    void registerClient(ClientCreateDto clientCreateDto);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);
    ClientDto update(Long id, ClientCreateDto clientCreateDto);
    ClientDto updatePass(Long id, PasswordClientDto passwordClientDto);
    UserDto banUser(Long id, BanUserDto banUserDto);
    UserDto unbanUser(Long id, BanUserDto banUserDto);
    Boolean verify(String token);


}
