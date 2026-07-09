const BASE_URL = import.meta.env.VITE_API_BASE_URL

export async function getShows() {
  const res = await fetch(`${BASE_URL}/shows`)
  if (!res.ok) {
    throw new Error(`Failed to load shows: ${res.status}`)
  }
  return res.json()
}

export async function getShow(id) {
  const res = await fetch(`${BASE_URL}/shows/${id}`)
  return res.json()
}

export async function createShow(payload) {
  const res = await fetch(`${BASE_URL}/shows`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  })
  if (!res.ok) {
    throw new Error(`Failed to create show: ${res.status}`)
  }
  return res.json()
}

export async function updateShow(id, payload) {
  const res = await fetch(`${BASE_URL}/shows/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  })
  if (!res.ok) {
    throw new Error(`Failed to update show: ${res.status}`)
  }
  return res.json()
}

export async function getGenres() {
  const res = await fetch(`${BASE_URL}/genres`)
  if (!res.ok) {
    throw new Error(`Failed to load genres: ${res.status}`)
  }
  return res.json()
}

export async function markWatched(episodeId) {
  const res = await fetch(`${BASE_URL}/episodes/${episodeId}/mark-watched`, { method: 'POST' })
  if (!res.ok) {
    throw new Error(`Failed to mark watched: ${res.status}`)
  }
  return res.json()
}

export async function markUnwatched(episodeId) {
  const res = await fetch(`${BASE_URL}/episodes/${episodeId}/mark-unwatched`, { method: 'POST' })
  if (!res.ok) {
    throw new Error(`Failed to mark unwatched: ${res.status}`)
  }
  return res.json()
}
