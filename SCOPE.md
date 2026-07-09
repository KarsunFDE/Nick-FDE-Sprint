# SCOPE.md — Phase 1 Explore Output

## Domain
Episode/watch tracker: shows, seasons, episodes; watch status and ratings; potentially
multiple franchises.

## Target state (by end of week)
Full-stack Java app (Spring Boot 3.x + React + Postgres), clean event-or-state-driven core,
well-modeled schema, request traceable end-to-end through every layer.

## Entity relationships (locked reasoning)
- `Show -> Season -> Episode` is one-to-many all the way down, regardless of how many shows
  exist. Adding more shows is more test data over the same shape, not a new relationship type.
- Genuine many-to-many requires an entity that spans shows on both sides — e.g. `Show <-> Genre`.
  A viewer/User entity (`User <-> Show`, `User <-> Episode` via `WatchEvent`) would be the other
  natural candidate, but is out of scope for this single-user MVP.
- Rewatch requires event-history modeling (a row per watch-through), not a status flag, to
  preserve chronology and distinguish first-watch from repeat-watch — deferred to a Day 4 spike
  (see below), not required for the week's target state.

## Non-CRUD operation (state transition)
`POST /episodes/{id}/mark-watched` / `POST /episodes/{id}/mark-unwatched`, action-style
endpoints rather than a generic `PATCH /status`, because the intent is clearer and the server
owns the transition rules rather than accepting arbitrary client-driven state.

Rules (target for Day 2 hardening, not required in the Day 1 skeleton):
- Enum-only status: `UNWATCHED`, `WATCHED` for MVP (no `DROPPED`/`REWATCHING` yet).
- Server validates the transition against current persisted state, not payload shape.
- Double `mark-watched` on an already-watched episode is an idempotent no-op (200/204), not
  an error — reserve rejection for genuinely illegal transitions.

## Day-by-day plan
- **Day 1 (this scaffold):** walking skeleton — single-user, no auth, one seeded show, episode
  list, mark-watched/unwatched, pessimistic frontend update, persisted round-trip.
- **Day 2:** harden transition validation + idempotency; add season/show rollups (e.g. "3/12
  watched") — proves read models work while the schema is still simple.
- **Day 3:** multi-show support (nav into show -> seasons -> episodes, watched state isolated
  per show) + ratings if the base model is stable (nullable numeric field, additive).
- **Day 4:** deliberate spike — decide keep/migrate/defer on `WatchEvent` (rewatch-as-history).
  Highest architectural risk in the project; optional deepening, not a requirement, since Day 2
  already satisfies "one non-CRUD operation with real state transitions."

## Frontend state (per episode row, Day 1 skeleton)
Pessimistic update, not optimistic — server-confirmed UI while still learning frontend state
boundaries:
```
status: "UNWATCHED" | "WATCHED"
isSaving: boolean
error: string | null
```
Flow: click disables the row's button and shows a pending state -> on success, update local
row status -> on failure, keep original state, show inline error, re-enable button.

## Stack
Spring Boot 3.x, React (Vite), Postgres (Docker Compose, Flyway migrations, seed script).
Single service, no Kafka, no auth for the MVP.
