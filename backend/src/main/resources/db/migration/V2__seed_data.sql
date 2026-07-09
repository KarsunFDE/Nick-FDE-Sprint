INSERT INTO genres (name) VALUES
    ('Action'),
    ('Adventure'),
    ('Comedy'),
    ('Drama');

INSERT INTO shows (title, description, internal_notes) VALUES
    ('Dragon Ball', 'Son Goku''s journey from childhood to defender of Earth.', 'seed batch 1 - do not expose via API'),
    ('Dragon Ball Z', 'Continuation following the Saiyan invasion and beyond.', 'seed batch 1 - do not expose via API');

INSERT INTO show_genres (show_id, genre_id)
SELECT s.id, g.id FROM shows s, genres g
WHERE s.title = 'Dragon Ball' AND g.name IN ('Action', 'Adventure');

INSERT INTO show_genres (show_id, genre_id)
SELECT s.id, g.id FROM shows s, genres g
WHERE s.title = 'Dragon Ball Z' AND g.name IN ('Action', 'Drama');

INSERT INTO seasons (show_id, season_number, title)
SELECT id, 1, 'Emperor Pilaf Saga' FROM shows WHERE title = 'Dragon Ball';

INSERT INTO seasons (show_id, season_number, title)
SELECT id, 1, 'Saiyan Saga' FROM shows WHERE title = 'Dragon Ball Z';

INSERT INTO episodes (season_id, episode_number, title, status)
SELECT s.id, n, 'Episode ' || n, 'UNWATCHED'
FROM seasons s
CROSS JOIN generate_series(1, 5) AS n
WHERE s.title = 'Emperor Pilaf Saga';

INSERT INTO episodes (season_id, episode_number, title, status, watched_at)
SELECT s.id, n, 'Episode ' || n, 'WATCHED', now() - (n || ' days')::interval
FROM seasons s
CROSS JOIN generate_series(1, 2) AS n
WHERE s.title = 'Saiyan Saga';

INSERT INTO episodes (season_id, episode_number, title, status)
SELECT s.id, n, 'Episode ' || n, 'UNWATCHED'
FROM seasons s
CROSS JOIN generate_series(3, 6) AS n
WHERE s.title = 'Saiyan Saga';
