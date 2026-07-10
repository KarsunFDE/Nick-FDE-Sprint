package com.karsun.tracker.service;

import com.karsun.tracker.dto.EpisodeResponse;
import com.karsun.tracker.dto.SeasonResponse;
import com.karsun.tracker.dto.ShowRequest;
import com.karsun.tracker.dto.ShowResponse;
import com.karsun.tracker.entity.Episode;
import com.karsun.tracker.entity.Genre;
import com.karsun.tracker.entity.Season;
import com.karsun.tracker.entity.Show;
import com.karsun.tracker.repository.GenreRepository;
import com.karsun.tracker.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShowService {

    private final ShowRepository showRepository;
    private final GenreRepository genreRepository;

    public ShowService(ShowRepository showRepository, GenreRepository genreRepository) {
        this.showRepository = showRepository;
        this.genreRepository = genreRepository;
    }

    public List<ShowResponse> getAllShows() {
        return showRepository.findAll()
                .stream()
                .map(this::toShowResponse)
                .toList();
    }

    public ShowResponse getShow(Long id) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Show not found: " + id));
        return toShowResponse(show);
    }

    public ShowResponse createShow(ShowRequest request) {
        Show show = new Show();
        show.setTitle(request.getTitle());
        show.setDescription(request.getDescription());
        show.setCreatedAt(Instant.now());
        show.setGenres(resolveGenres(request.getGenreIds()));
        Show saved = showRepository.save(show);
        return toShowResponse(saved);
    }

    public ShowResponse updateShow(Long id, ShowRequest request) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Show not found: " + id));
        show.setTitle(request.getTitle());
        show.setDescription(request.getDescription());
        show.setGenres(resolveGenres(request.getGenreIds()));
        Show saved = showRepository.save(show);
        return toShowResponse(saved);
    }

    public void deleteShow(Long id) {
        showRepository.deleteById(id);
    }

    private Set<Genre> resolveGenres(List<Long> genreIds) {
        if (genreIds == null || genreIds.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(genreRepository.findByIdIn(genreIds));
    }

    private ShowResponse toShowResponse(Show show) {
        return new ShowResponse(
                show.getId(),
                show.getTitle(),
                show.getDescription(),
                show.getCreatedAt(),
                show.getGenres().stream().toList(),
                show.getSeasons().stream().map(this::toSeasonResponse).toList()
        );
    }

    private SeasonResponse toSeasonResponse(Season season) {
        return new SeasonResponse(
                season.getId(),
                season.getSeasonNumber(),
                season.getTitle(),
                season.getEpisodes().stream().map(this::toEpisodeResponse).toList()
        );
    }

    private EpisodeResponse toEpisodeResponse(Episode episode) {
        return new EpisodeResponse(
                episode.getId(),
                episode.getEpisodeNumber(),
                episode.getTitle(),
                episode.getStatus().name(),
                episode.getWatchedAt()
        );
    }
}
