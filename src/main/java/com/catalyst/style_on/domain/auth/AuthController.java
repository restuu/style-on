package com.catalyst.style_on.domain.auth;

import com.catalyst.style_on.domain.auth.dto.AuthLoginRequestDTO;
import com.catalyst.style_on.domain.auth.dto.AuthLoginResponseDTO;
import com.catalyst.style_on.domain.auth.dto.AuthRegisterRequestDTO;
import com.catalyst.style_on.domain.member.MemberMapper;
import com.catalyst.style_on.domain.member.MemberService;
import com.catalyst.style_on.domain.shared.api.ApiResponse;
import com.catalyst.style_on.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final AuthTokenProvider authTokenProvider;

    @PostMapping("/register")
    public Mono<ApiResponse<?>> register(@RequestBody @Valid AuthRegisterRequestDTO request) {
        return Mono.just(MemberMapper.authRegisterRequestDTOToMember(request))
                .flatMap(memberService::registerNewMember)
                .map(ApiResponse::ok);
    }

    @PostMapping("/login")
    public Mono<ApiResponse<AuthLoginResponseDTO>> login(@RequestBody @Valid AuthLoginRequestDTO request) {
        return memberService.findByEmail(request.email())
                .switchIfEmpty(Mono.defer(()-> Mono.error(new NotFoundException("Member not found"))))
                .map(member -> new AuthCustomClaim(member.id()))
                .flatMap(authTokenProvider::generateToken)
                .map(AuthLoginResponseDTO::new)
                .map(ApiResponse::ok);
    }

}
