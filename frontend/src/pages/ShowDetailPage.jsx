import { useEffect, useState } from 'react'
import { useParams, Link } from 'react-router-dom'
import { getShow, markWatched, markUnwatched } from '../api/client.js'

export default function ShowDetailPage() {
  const { id } = useParams()
  const [show, setShow] = useState(null)

  useEffect(() => {
    getShow(id).then((data) => setShow(data))
  }, [id])

  if (!show) return <p>Loading show...</p>

  function findEpisode(episodeId) {
    for (const season of show.seasons) {
      const episode = season.episodes.find((e) => e.id === episodeId)
      if (episode) return episode
    }
    return null
  }

  async function handleToggle(episode) {
    const updated =
      episode.status === 'WATCHED' ? await markUnwatched(episode.id) : await markWatched(episode.id)

    const target = findEpisode(episode.id)
    target.status = updated.status
    target.watchedAt = updated.watchedAt
    setShow(show)
  }

  return (
    <div>
      <p>
        <Link to="/">&larr; Back to shows</Link>
      </p>
      <h2>{show.title}</h2>
      <p>{show.description}</p>
      {show.seasons.map((season) => (
        <div key={season.id} style={{ marginBottom: '1rem' }}>
          <h3>{season.title || `Season ${season.seasonNumber}`}</h3>
          <ul style={{ listStyle: 'none', padding: 0 }}>
            {season.episodes.map((episode) => (
              <li
                key={episode.id}
                style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', padding: '0.25rem 0' }}
              >
                <span style={{ flex: 1 }}>{episode.title}</span>
                <span>{episode.status}</span>
                <button onClick={() => handleToggle(episode)}>
                  {episode.status === 'WATCHED' ? 'Mark unwatched' : 'Mark watched'}
                </button>
              </li>
            ))}
          </ul>
        </div>
      ))}
    </div>
  )
}
