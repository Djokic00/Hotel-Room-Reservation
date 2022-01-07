package sk.hoteluserservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sk.hoteluserservice.dto.*;

public interface UserService {

    Page<UserDto> findAll(Pageable pageable);
    UserDto addManager(ManagerCreateDto managerCreateDto);
    UserDto addClient(ClientCreateDto clientCreateDto);
    void registerClient(ClientCreateDto clientCreateDto);
    TokenResponseDto login(TokenRequestDto tokenRequestDto);
    ClientDto update(Long id, ClientCreateDto clientCreateDto);
    ClientDto updatePassportNumber(Long id, PassportClientDto passportClientDto);
    UserDto updatePassword(Long id, PasswordUserDto passwordClientDto);
    ManagerDto updateHotelName(Long id, HotelNameManagerDto hotelNameManagerDto);
    UserDto banUser(Long id, BanUserDto banUserDto);
    UserDto unbanUser(Long id, BanUserDto banUserDto);
    DiscountDto findDiscount(Long id);

}
