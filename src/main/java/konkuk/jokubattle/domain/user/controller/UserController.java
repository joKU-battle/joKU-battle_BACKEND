package konkuk.jokubattle.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import konkuk.jokubattle.domain.user.dto.request.UserLoginReq;
import konkuk.jokubattle.domain.user.dto.request.UserRegisterReq;
import konkuk.jokubattle.domain.user.dto.response.UserMyPageRes;
import konkuk.jokubattle.domain.user.dto.response.UserRankingRes;
import konkuk.jokubattle.domain.user.dto.response.UserTokenRes;
import konkuk.jokubattle.domain.user.service.UserService;
import konkuk.jokubattle.global.annotation.UserIdx;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저", description = "유저 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "유저가 회원가입한다.")
    @PostMapping("signup")
    public ResponseEntity<UserTokenRes> register(
            @Validated @RequestBody UserRegisterReq req
    ) {
        return ResponseEntity.ok(userService.register(req));
    }

    @Operation(summary = "로그인", description = "유저가 로그인한다.")
    @PostMapping("login")
    public ResponseEntity<UserTokenRes> login(
            @Validated @RequestBody UserLoginReq req
    ) {
        return ResponseEntity.ok(userService.login(req));
    }

    @Operation(summary = "마이페이지", description = "자신의 정보를 조회한다.")
    @GetMapping("mypage")
    public ResponseEntity<UserMyPageRes> getMyPage(
            @Parameter(hidden = true) @UserIdx Long usIdx
    ) {
        return ResponseEntity.ok(userService.mypage(usIdx));
    }

    @Operation(summary = "랭킹", description = "전체 순위를 조회한다.")
    @GetMapping("ranking")
    public ResponseEntity<List<UserRankingRes>> ranking(
    ) {
        return ResponseEntity.ok(userService.ranking());
    }
}
