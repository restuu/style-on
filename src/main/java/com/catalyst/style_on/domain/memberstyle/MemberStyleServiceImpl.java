package com.catalyst.style_on.domain.memberstyle;

import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleResponseDTO;
import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleSubmitRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberStyleServiceImpl implements MemberStyleService {

    private final MemberStyleRepository memberStyleRepository;

    @Override
    public Mono<MemberStyleResponseDTO> submitMemberStyle(Long memberId, MemberStyleSubmitRequestDTO req) {
        ZonedDateTime now = ZonedDateTime.now();

        String styleName = "test";

        Set<MemberStyleItem> items = req.styleIds()
                .stream()
                .map(styleId -> new MemberStyleItem(
                        null,
                        null,
                        styleId,
                        now,
                        now
                ))
                .collect(Collectors.toSet());

        MemberStyle memberStyle = new MemberStyle(
                null,
                memberId,
                styleName,
                now,
                now,
                items
        );

        return memberStyleRepository.save(memberStyle)
                .thenReturn(new MemberStyleResponseDTO(styleName));
    }
}
