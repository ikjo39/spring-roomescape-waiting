package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.dto.request.ThemeRequest;
import roomescape.dto.response.ThemeResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ThemeRepository;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final ReservationRepository reservationRepository;

    public ThemeService(ThemeRepository themeRepository, ReservationRepository reservationRepository) {
        this.themeRepository = themeRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<ThemeResponse> findAll() {
        return themeRepository.findAll()
                .stream()
                .map(ThemeResponse::from)
                .toList();
    }

    public ThemeResponse create(ThemeRequest themeRequest) {
        Theme theme = themeRequest.toEntity();
        Theme createdTheme = themeRepository.save(theme);
        return ThemeResponse.from(createdTheme);
    }

    public void delete(Long id) {
        validateExistReservation(id);
        themeRepository.deleteById(id);
    }

    private void validateExistReservation(Long id) {
        Theme theme = getThemeById(id);
        if (reservationRepository.existsByTheme(theme)) {
            throw new IllegalArgumentException("예약이 등록된 테마는 제거할 수 없습니다");
        }
    }

    private Theme getThemeById(long id) {
        return themeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 테마 입니다"));
    }
}
