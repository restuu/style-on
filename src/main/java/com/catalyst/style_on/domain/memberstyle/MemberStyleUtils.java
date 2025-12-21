package com.catalyst.style_on.domain.memberstyle;

import com.catalyst.style_on.domain.memberstyle.dto.MemberStyleSummaryDTO;
import com.catalyst.style_on.domain.style.Style;
import com.catalyst.style_on.domain.style.enumeration.StyleEnum;
import com.catalyst.style_on.domain.style.enumeration.StyleMovementEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// DRES-AUTO	The Classic Curator	[Elegant & Legacy]. Menghargai warisan dan kualitas tak terhindarkan. Pilihan Anda adalah pernyataan status dan craftsmanship untuk profesionalitas tinggi.
// DIVE-AUTO	The Deep Executive	[Premium & Functional]. Gaya hidup menuntut keandalan absolut dan estetika premium. Anda mencari jam tangan tahan banting yang tetap berkelas.
// PILOT-AUTO	The Sky Pioneer	[Historical & Technical]. Menyukai kisah sejarah dan fungsi yang teruji. Menghargai kompleksitas mekanik dan aura petualang klasik.
// FIEL-AUTO	The Heritage Tracker	[Tangguh & Klasik]. Mencari durabilitas superior dengan sentuhan heritage mekanik. Fokus pada jam tangan yang andal dan teruji waktu.
// CSPT-AUTO	The Prestige Catalyst	[Energi & Prestise]. Menggabungkan fleksibilitas harian dengan prestise mekanik. Anda mencari jam tangan yang enerjik namun tetap berstatus tinggi.
// DRES-SOLR	The Modern Minimalist	[Sustainable & Refined]. Mencari keanggunan dengan zero-maintenance. Desain minimalis didukung oleh teknologi yang mandiri.
// DIVE-SOLR	The Eco Navigator	[Independent & Precise]. Mengutamakan keberlanjutan dan ketahanan air. Alat selam yang andal dan selalu aktif tanpa perlu ganti baterai.
// PILOT-SOLR	The Solar Navigator	[High-Viz & Efficient]. Menghargai legibility dengan efisiensi tinggi. Jam tangan yang selalu siap dan tidak merepotkan dalam perjalanan.
// FIEL-SOLR	The Rugged Sentinel	[Enduring & Sustainable]. Tangguh dan selalu aktif. Sempurna untuk eksplorasi tanpa batas, fokus pada durabilitas dan kemandirian teknologi.
// CSPT-SOLR	The Tech-Savvy Pro	[Flexible & Modern]. Mengutamakan kemudahan adaptasi dan efisiensi teknologi. Perpaduan desain modern dan fitur canggih yang selalu zero-maintenance.
// DRES-BATT	The Simple Curator	[Affordable & Refined]. Mencari estetika elegan dengan perawatan minimal. Cocok untuk gaya minimalis yang ingin terlihat rapi.
// DIVE-BATT	The Action Navigator	[Dynamic & Reliable]. Fokus pada jam tangan sporty yang andal dan akurat. Kepercayaan pada ketahanan dengan kemudahan movement baterai.
// PILOT-BATT	The Time Essential	[Practical & Readable]. Mencari akurasi dan visibilitas tinggi. Fungsi tracking waktu yang esensial dengan movement yang low-maintenance.
// FIEL-BATT	The Agile Sentinel	[Durable & Practical]. Jam tangan yang tahan banting dan mudah dipakai. Pilihan yang sangat praktis untuk gaya hidup aktif sehari-hari.
// CSPT-BATT	The Urban Essential	[Practical & Accurate]. Mengutamakan kenyamanan dan akurasi. Jam tangan yang siap pakai dan cocok dengan ritme cepat kehidupan perkotaan.

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MemberStyleUtils {

    private static final Map<String, String> nameMap;
    private static final Map<String, String> descMap;

    static {
        nameMap = new ConcurrentHashMap<>();
        nameMap.put(StyleEnum.DRESS.name() + "-" + StyleMovementEnum.AUTOMATIC.name(), "The Classic Curator");
        nameMap.put(StyleEnum.DIVER.name() + "-" + StyleMovementEnum.AUTOMATIC.name(), "The Deep Executive");
        nameMap.put(StyleEnum.PILOT.name() + "-" + StyleMovementEnum.AUTOMATIC.name(), "The Sky Pioneer");
        nameMap.put(StyleEnum.FIELD.name() + "-" + StyleMovementEnum.AUTOMATIC.name(), "The Heritage Tracker");
        nameMap.put(StyleEnum.CASUAL.name() + "-" + StyleMovementEnum.AUTOMATIC.name(), "The Prestige Catalyst");

        nameMap.put(StyleEnum.DRESS.name() + "-" + StyleMovementEnum.SOLAR_POWERED.name(), "The Modern Minimalist");
        nameMap.put(StyleEnum.DIVER.name() + "-" + StyleMovementEnum.SOLAR_POWERED.name(), "The Eco Navigator");
        nameMap.put(StyleEnum.PILOT.name() + "-" + StyleMovementEnum.SOLAR_POWERED.name(), "The Solar Navigator");
        nameMap.put(StyleEnum.FIELD.name() + "-" + StyleMovementEnum.SOLAR_POWERED.name(), "The Rugged Sentinel");
        nameMap.put(StyleEnum.CASUAL.name() + "-" + StyleMovementEnum.SOLAR_POWERED.name(), "The Tech-Savvy Pro");

        nameMap.put(StyleEnum.DRESS.name() + "-" + StyleMovementEnum.QUARTZ_BATTERY.name(), "The Simple Curator");
        nameMap.put(StyleEnum.DIVER.name() + "-" + StyleMovementEnum.QUARTZ_BATTERY.name(), "The Action Navigator");
        nameMap.put(StyleEnum.PILOT.name() + "-" + StyleMovementEnum.QUARTZ_BATTERY.name(), "The Time Essential");
        nameMap.put(StyleEnum.FIELD.name() + "-" + StyleMovementEnum.QUARTZ_BATTERY.name(), "The Agile Sentinel");
        nameMap.put(StyleEnum.CASUAL.name() + "-" + StyleMovementEnum.QUARTZ_BATTERY.name(), "The Urban Essential");

        descMap = new ConcurrentHashMap<>();
        descMap.put(StyleEnum.DRESS.name() + "-" + StyleMovementEnum.AUTOMATIC.name(), "[Elegant & Legacy]. Menghargai warisan dan kualitas tak terhindarkan. Pilihan Anda adalah pernyataan status dan craftsmanship untuk profesionalitas tinggi.");
        descMap.put(StyleEnum.DIVER.name() + "-" + StyleMovementEnum.AUTOMATIC.name(), "[Premium & Functional]. Gaya hidup menuntut keandalan absolut dan estetika premium. Anda mencari jam tangan tahan banting yang tetap berkelas.");
        descMap.put(StyleEnum.PILOT.name() + "-" + StyleMovementEnum.AUTOMATIC.name(), "[Historical & Technical]. Menyukai kisah sejarah dan fungsi yang teruji. Menghargai kompleksitas mekanik dan aura petualang klasik.");
        descMap.put(StyleEnum.FIELD.name() + "-" + StyleMovementEnum.AUTOMATIC.name(), "[Tangguh & Klasik]. Mencari durabilitas superior dengan sentuhan heritage mekanik. Fokus pada jam tangan yang andal dan teruji waktu.");
        descMap.put(StyleEnum.CASUAL.name() + "-" + StyleMovementEnum.AUTOMATIC.name(), "[Energi & Prestise]. Menggabungkan fleksibilitas harian dengan prestise mekanik. Anda mencari jam tangan yang enerjik namun tetap berstatus tinggi.");

        descMap.put(StyleEnum.DRESS.name() + "-" + StyleMovementEnum.SOLAR_POWERED.name(), "[Sustainable & Refined]. Mencari keanggunan dengan zero-maintenance. Desain minimalis didukung oleh teknologi yang mandiri.");
        descMap.put(StyleEnum.DIVER.name() + "-" + StyleMovementEnum.SOLAR_POWERED.name(), "[Independent & Precise]. Mengutamakan keberlanjutan dan ketahanan air. Alat selam yang andal dan selalu aktif tanpa perlu ganti baterai.");
        descMap.put(StyleEnum.PILOT.name() + "-" + StyleMovementEnum.SOLAR_POWERED.name(), "[High-Viz & Efficient]. Menghargai legibility dengan efisiensi tinggi. Jam tangan yang selalu siap dan tidak merepotkan dalam perjalanan.");
        descMap.put(StyleEnum.FIELD.name() + "-" + StyleMovementEnum.SOLAR_POWERED.name(), "[Enduring & Sustainable]. Tangguh dan selalu aktif. Sempurna untuk eksplorasi tanpa batas, fokus pada durabilitas dan kemandirian teknologi.");
        descMap.put(StyleEnum.CASUAL.name() + "-" + StyleMovementEnum.SOLAR_POWERED.name(), "[Flexible & Modern]. Mengutamakan kemudahan adaptasi dan efisiensi teknologi. Perpaduan desain modern dan fitur canggih yang selalu zero-maintenance.");

        descMap.put(StyleEnum.DRESS.name() + "-" + StyleMovementEnum.QUARTZ_BATTERY.name(), "[Affordable & Refined]. Mencari estetika elegan dengan perawatan minimal. Cocok untuk gaya minimalis yang ingin terlihat rapi.");
        descMap.put(StyleEnum.DIVER.name() + "-" + StyleMovementEnum.QUARTZ_BATTERY.name(), "[Dynamic & Reliable]. Fokus pada jam tangan sporty yang andal dan akurat. Kepercayaan pada ketahanan dengan kemudahan movement baterai.");
        descMap.put(StyleEnum.PILOT.name() + "-" + StyleMovementEnum.QUARTZ_BATTERY.name(), "[Practical & Readable]. Mencari akurasi dan visibilitas tinggi. Fungsi tracking waktu yang esensial dengan movement yang low-maintenance.");
        descMap.put(StyleEnum.FIELD.name() + "-" + StyleMovementEnum.QUARTZ_BATTERY.name(), "[Durable & Practical]. Jam tangan yang tahan banting dan mudah dipakai. Pilihan yang sangat praktis untuk gaya hidup aktif sehari-hari.");
        descMap.put(StyleEnum.CASUAL.name() + "-" + StyleMovementEnum.QUARTZ_BATTERY.name(), "[Practical & Accurate]. Mengutamakan kenyamanan dan akurasi. Jam tangan yang siap pakai dan cocok dengan ritme cepat kehidupan perkotaan.");
    }

    public static Optional<String> generateStyleName(List<Style> styles, MemberStyleSummaryDTO summary) {
        var topStyle = defineTopStyleDNA(styles, summary);
        var nameKey = topStyle.style.name()+"-"+topStyle.movement.name();

        return Optional.ofNullable(nameMap.get(nameKey));
    }

    public static Optional<String> generateStyleDescription(List<Style> styles, MemberStyleSummaryDTO summary) {
        var topStyle = defineTopStyleDNA(styles, summary);
        var nameKey = topStyle.style.name()+"-"+topStyle.movement.name();

        return Optional.ofNullable(descMap.get(nameKey));
    }

    public static List<String> listAvailableNames() {
        return List.of(nameMap.values().toArray(new String[0]));
    }

    public static List<String> listAvailableDescriptions() {
        return List.of(descMap.values().toArray(new String[0]));
    }

    public static void setName(StyleEnum style, StyleMovementEnum movement, String name) {
        var key = combineStyleMovementKey(style, movement);

        nameMap.putIfAbsent(key, name);
    }

    public static void setDescription(StyleEnum style, StyleMovementEnum movement, String description) {
        var key = combineStyleMovementKey(style, movement);

        descMap.putIfAbsent(key, description);
    }


    private static String combineStyleMovementKey(StyleEnum style, StyleMovementEnum movement) {
        return style.name() + "-" + movement.name();
    }

    private static TopStyle defineTopStyleDNA(List<Style> styles, MemberStyleSummaryDTO summary) {
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

        return new TopStyle(topKeyStyle, topMovement);
    }

    private record TopStyle(
            StyleEnum style,
            StyleMovementEnum movement
    ) {
    }
}
