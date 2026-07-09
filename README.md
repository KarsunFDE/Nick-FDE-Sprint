# Episode Tracker

Full-stack episode/watch tracker. Spring Boot 3.x + Postgres backend, React (Vite) frontend.
See `SCOPE.md` for the product scope and design reasoning behind this skeleton.

## Stack
- Backend: Spring Boot 3.x, Spring Data JPA, Flyway, Postgres
- Frontend: React + Vite, react-router-dom
- Postgres 16 via Docker Compose

## Running locally

```
docker compose up --build
```

- Backend: http://localhost:8080/api
- Frontend: http://localhost:5173
- Postgres: localhost:5432 (db `tracker`, user/password `tracker`)

The database is seeded on first boot via Flyway migrations (`backend/src/main/resources/db/migration`)
with two shows, a season each, and a handful of episodes.

## What's here

- `GET /api/shows`, `GET /api/shows/{id}`, `POST /api/shows`, `PUT /api/shows/{id}`,
  `DELETE /api/shows/{id}` — CRUD over shows (with genres, seasons, episodes nested).
- `GET /api/genres` — for the show form's genre picker.
- `POST /api/episodes/{id}/mark-watched`, `POST /api/episodes/{id}/mark-unwatched` — the
  non-CRUD state-transition operation.
- Frontend: a shows list, a show detail page (episodes + mark-watched/unwatched), and a
  show create/edit form.

This is a skeleton, not a finished app — it's meant to be extended, not shipped as-is.
