package com.catalyst.style_on.domain.member;

import com.catalyst.style_on.domain.member.dto.MemberResponseDTO;
import com.catalyst.style_on.domain.shared.api.ApiResponse;
import com.catalyst.style_on.domain.shared.gender.Gender;
import com.catalyst.style_on.util.SecurityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.catalyst.style_on.domain.shared.constant.SecurityConstant.JWT_BEARER_AUTH;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@SecurityRequirement(name = JWT_BEARER_AUTH)
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public Mono<ApiResponse<MemberResponseDTO>> getCurrentMember() {
        return SecurityUtils.getCurrentMemberId()
                .flatMap(memberService::findById)
                .map(ApiResponse::ok);
    }
}
