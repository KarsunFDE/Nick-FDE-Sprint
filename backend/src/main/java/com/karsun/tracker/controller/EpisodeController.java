package com.karsun.tracker.controller;

import com.karsun.tracker.entity.Episode;
import com.karsun.tracker.entity.EpisodeStatus;
import com.karsun.tracker.repository.EpisodeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/episodes")
public class EpisodeController {

    private final EpisodeRepository episodeRepository;

    public EpisodeController(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    @PostMapping("/{id}/mark-watched")
    public ResponseEntity<Episode> markWatched(@PathVariable Long id) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Episode not found: " + id));
        episode.setStatus(EpisodeStatus.WATCHED);
        episode.setWatchedAt(Instant.now());
        return ResponseEntity.ok(episodeRepository.save(episode));
    }

    @PostMapping("/{id}/mark-unwatched")
    public ResponseEntity<Episode> markUnwatched(@PathVariable Long id) {
        Episode episode = episodeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Episode not found: " + id));
        episode.setStatus(EpisodeStatus.UNWATCHED);
        episode.setWatchedAt(null);
        return ResponseEntity.ok(episodeRepository.save(episode));
    }
}
