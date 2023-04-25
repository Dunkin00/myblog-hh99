package com.sparta.myblog.service;

import com.sparta.myblog.dto.LoginRequestDto;
import com.sparta.myblog.dto.MessageDto;
import com.sparta.myblog.dto.SignupRequestDto;
import com.sparta.myblog.entity.StatusEnum;
import com.sparta.myblog.entity.UserRoleEnum;
import com.sparta.myblog.entity.Users;
import com.sparta.myblog.jwt.JwtUtil;
import com.sparta.myblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    //회원가입
    @Transactional
    public ResponseEntity<MessageDto> signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();

        // 회원 중복 확인
        Optional<Users> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        // 회원 Role 확인
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            signupRequestDto.setRole(UserRoleEnum.ADMIN);
        }
        Users user = new Users(signupRequestDto);
        userRepository.save(user);
        MessageDto messageDto = MessageDto.setSuccess(StatusEnum.OK.getStatusCode(), "회원가입 완료", null);
        return new ResponseEntity(messageDto, HttpStatus.OK);
    }

    //로그인
    @Transactional
    public ResponseEntity<MessageDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        Users user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if(!user.getPassword().equals(password)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        MessageDto messageDto = MessageDto.setSuccess(StatusEnum.OK.getStatusCode(), "사용자 로그인 완료", null);
        if(user.getRole().equals(UserRoleEnum.ADMIN)){
            messageDto = MessageDto.setSuccess(StatusEnum.OK.getStatusCode(), "관리자 로그인 완료", null);
        }
        return new ResponseEntity(messageDto, HttpStatus.OK);
    }
}