package roomescape.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.response.ThemeResponse;
import roomescape.service.RankService;

@RestController
@RequestMapping("/ranks")
public class RankingController {

    private final RankService rankService;

    public RankingController(RankService rankService) {
        this.rankService = rankService;
    }

    @GetMapping
    public ResponseEntity<List<ThemeResponse>> read() {
        LocalDate now = LocalDate.now();
        LocalDate dateFrom = now.minusWeeks(1);
        LocalDate dateTo = now.minusDays(1);
        return ResponseEntity.ok(rankService.getPopularThemeList(dateFrom, dateTo));
    }
}
