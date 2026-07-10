package com.karsun.tracker.dto;

import java.util.List;

public class SeasonResponse {

    private final Long id;
    private final Integer seasonNumber;
    private final String title;
    private final List<EpisodeResponse> episodes;

    public SeasonResponse(Long id, Integer seasonNumber, String title, List<EpisodeResponse> episodes) {
        this.id = id;
        this.seasonNumber = seasonNumber;
        this.title = title;
        this.episodes = episodes;
    }

    public Long getId() {
        return id;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public String getTitle() {
        return title;
    }

    public List<EpisodeResponse> getEpisodes() {
        return episodes;
    }
}
