package com.catalyst.style_on.domain.memberstyle;

import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleSummaryDTO;
import com.catalyst.style_on.domain.style.Style;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MemberStyleUtils {
    public static String generateStyleName(List<Style> styles, MemberStyleSummaryDTO summary) {
        var topKeyStyleWeight = summary.getKeyStyle().keySet()
                .stream()
                .max(Comparator.comparingInt(key -> summary.getKeyStyle().get(key)))
                .map(styleEnum -> summary.getKeyStyle().get(styleEnum))
                .orElse(1);


        var topKeyStyle = styles.stream()
                .flatMap(style -> Arrays.stream(style.keyStyle()))
                .filter(keyStyle -> topKeyStyleWeight.equals(summary.getKeyStyle().get(keyStyle)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("missing top key style"));

        var topMovementWeight = summary.getMovement().keySet()
                .stream()
                .max(Comparator.comparingInt(key -> summary.getMovement().get(key)))
                .map(movementEnum -> summary.getMovement().get(movementEnum))
                .orElse(1);

        var topMovement = styles.stream()
                .map(Style::movement)
                .filter(movement -> Objects.equals(summary.getMovement().get(movement), topMovementWeight))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("missing top movement"));


        return topKeyStyle.name() + " " + topMovement.name();
    }
}
