package com.jcf.service;


import com.jcf.mapper.CustomUserMapper;
import com.jcf.persistence.model.dto.request.UserAddDto;
import com.jcf.persistence.model.dto.response.ResponseDto;
import com.jcf.persistence.model.dto.response.UserResponseDto;
import com.jcf.persistence.model.entity.User;
import com.jcf.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseDto<UserResponseDto> createUser (UserAddDto dto){

        //User user = userRepository.save(CustomUserMapper.convertToEntity(dto));
        //UserResponseDto responseDto = CustomUserMapper.convertToDto(user);


        System.out.println(dto.getFirstName() + " " + dto.getLastName()+ " "
                + " " + dto.getEmail() + " " + dto.getPassword());

        //Mock

        return new ResponseDto<>(getMock());
    }

    public ResponseDto<UserResponseDto> getById(Long id){

        //UserResponseDto responseDto = CustomUserMapper.convertToDto(userRepository.findById(id)
        //.orElseThrow(RuntimeException::new));


        return new ResponseDto<>(getMock());
    }

    public UserResponseDto getMock(){
        UserResponseDto responseDto = new UserResponseDto();

        responseDto.setId(1L);
        responseDto.setFirstName("Anton");
        responseDto.setLastName("Frolov");
        responseDto.setEmail("avcd@gmail.com");
        responseDto.setPassword("1234");

        return  responseDto;
    }
}
