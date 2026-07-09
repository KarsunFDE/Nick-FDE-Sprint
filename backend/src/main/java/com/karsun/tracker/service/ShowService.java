package com.karsun.tracker.service;

import com.karsun.tracker.dto.ShowRequest;
import com.karsun.tracker.entity.Genre;
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

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public Show getShow(Long id) {
        return showRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Show not found: " + id));
    }

    public Show createShow(ShowRequest request) {
        Show show = new Show();
        show.setTitle(request.getTitle());
        show.setDescription(request.getDescription());
        show.setCreatedAt(Instant.now());
        show.setGenres(resolveGenres(request.getGenreIds()));
        return showRepository.save(show);
    }

    public Show updateShow(Long id, ShowRequest request) {
        Show show = getShow(id);
        show.setTitle(request.getTitle());
        show.setDescription(request.getDescription());
        show.setGenres(resolveGenres(request.getGenreIds()));
        return showRepository.save(show);
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
}
