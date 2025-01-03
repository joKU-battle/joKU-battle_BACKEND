package konkuk.jokubattle.domain.user.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import konkuk.jokubattle.domain.title.dto.response.TitleDetailRes;
import konkuk.jokubattle.domain.title.entity.Title;
import konkuk.jokubattle.domain.title.repository.TitleRepository;
import konkuk.jokubattle.domain.user.dto.request.UserLoginReq;
import konkuk.jokubattle.domain.user.dto.request.UserRegisterReq;
import konkuk.jokubattle.domain.user.dto.response.UserMyPageRes;
import konkuk.jokubattle.domain.user.dto.response.UserRankingRes;
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
    private final TitleRepository titleRepository;
    private final Random random = new Random();

    public UserTokenRes register(UserRegisterReq req) {
        if (userRepository.existsById(req.getId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        User user = User.create(req.getId(), req.getPassword(), req.getName(), req.getDepartment(), getImage());
        user = userRepository.save(user);

        String accessToken = jwtProvider.createAccessToken(user);
        return new UserTokenRes(accessToken);
    }

    private String getImage() {
        switch (random.nextInt(5)) {
            case 0 -> {
                return "https://c.files.bbci.co.uk/DCE1/production/_104454565_mary-mcgowan_caught-in-the-act_00001294.jpg";
            }
            case 1 -> {
                return "https://mediahub.seoul.go.kr/uploads/2020/03/8670a29a6ad475f65ebf6c3b5aefc050.jpg";
            }
            case 2 -> {
                return "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRC3FaZbxSuJZ4V2-1bUq5_-eBTBg6bxjBesQ&s";
            }
            case 3 -> {
                return "https://png.pngtree.com/thumb_back/fh260/background/20220314/pngtree-hd-family-cute-pet-puppet-cat-cute-cat-material-picture-image_1046515.jpg";
            }
            case 4 -> {
                return null;
            }
            default -> {
                return null;
            }
        }
    }

    public UserTokenRes login(UserLoginReq req) {
        User user = userRepository.findById(req.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        if (!req.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtProvider.createAccessToken(user);
        return new UserTokenRes(accessToken);
    }

    public UserMyPageRes mypage(Long usIdx) {
        User user = userRepository.findById(usIdx)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Optional<Title> title = titleRepository.findRecentTitleByUsIdx(usIdx);
        if (title.isEmpty()) {
            return new UserMyPageRes(user.getImage(), user.getName(), user.getDepartment(), null);
        }
        LocalDateTime createdAt = title.get().getCreatedAt();
        TitleDetailRes titleDetailRes = getTitleDetailRes(createdAt, title.get().getName());

        return new UserMyPageRes(user.getImage(), user.getName(), user.getDepartment(), titleDetailRes);
    }

    private TitleDetailRes getTitleDetailRes(LocalDateTime createdAt, String titleName) {
        int monthValue = createdAt.getMonthValue();
        WeekFields weekFields = WeekFields.of(Locale.KOREA);
        int weekValue = createdAt.get(weekFields.weekOfMonth());
        return new TitleDetailRes(titleName, monthValue, weekValue);
    }

    public List<UserRankingRes> ranking() {
        List<User> users = userRepository.findAllByOrderByScoreDescCreatedAtAsc();

        return users.stream()
                .map(user -> new UserRankingRes(user.getName(), user.getDepartment(), user.getScore(), user.getImage()))
                .toList();
    }
}
