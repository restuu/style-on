package com.catalyst.style_on.domain.memberstyle;

import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleResponseDTO;
import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleSubmitRequestDTO;
import com.catalyst.style_on.domain.shared.api.ApiResponse;
import com.catalyst.style_on.util.SecurityUtils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.catalyst.style_on.domain.shared.constant.SecurityConstant.JWT_BEARER_AUTH;

@RestController
@RequestMapping("api/v1/member-styles")
@SecurityRequirement(name = JWT_BEARER_AUTH)
@RequiredArgsConstructor
@Slf4j
public class MemberStyleController {
    private final MemberStyleService memberStyleService;

    @PostMapping("")
    public Mono<ApiResponse<MemberStyleResponseDTO>> submitMemberStyles(@RequestBody @Valid MemberStyleSubmitRequestDTO req) {
        return SecurityUtils.getCurrentMemberId()
                .flatMap(memberId -> memberStyleService.submitMemberStyle(memberId, req))
                .map(ApiResponse::ok);
    }
}
