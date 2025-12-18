package com.catalyst.style_on.domain.memberstyle.dto;


import org.springframework.core.io.buffer.DataBuffer;

public record MemberStyleImagineStyleDTO(
        Long memberId,
        Long memberStyleId,
        DataBuffer image,
        String modelNumber
) {
}
