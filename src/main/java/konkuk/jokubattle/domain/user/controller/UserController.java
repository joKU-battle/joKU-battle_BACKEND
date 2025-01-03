package konkuk.jokubattle.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import konkuk.jokubattle.domain.user.dto.request.UserRegisterReq;
import konkuk.jokubattle.domain.user.dto.response.UserTokenRes;
import konkuk.jokubattle.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저", description = "유저 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "유저가 회원가입한다.")
    @PostMapping("register")
    public ResponseEntity<UserTokenRes> register(
            @Validated @RequestBody UserRegisterReq req
    ) {
        return ResponseEntity.ok(userService.register(req));
    }
}
