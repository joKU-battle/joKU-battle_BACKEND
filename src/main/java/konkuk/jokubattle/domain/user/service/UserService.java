package konkuk.jokubattle.domain.user.service;

import jakarta.transaction.Transactional;
import konkuk.jokubattle.domain.user.dto.request.UserRegisterReq;
import konkuk.jokubattle.domain.user.dto.response.UserTokenRes;
import konkuk.jokubattle.domain.user.entity.User;
import konkuk.jokubattle.domain.user.repository.UserRepository;
import konkuk.jokubattle.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public UserTokenRes register(UserRegisterReq req) {

        if (userRepository.existsById(req.getId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        User user = User.create(req.getId(), req.getPassword(), req.getName(), req.getDepartment());
        user = userRepository.save(user);

        String accessToken = jwtProvider.createAccessToken(user);
        return new UserTokenRes(accessToken);
    }
}
