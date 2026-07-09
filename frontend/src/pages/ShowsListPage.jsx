import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { getShows } from '../api/client.js'

export default function ShowsListPage() {
  const [shows, setShows] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    getShows()
      .then((data) => setShows(data))
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false))
  }, [])

  if (loading) return <p>Loading shows...</p>
  if (error) return <p style={{ color: 'crimson' }}>Could not load shows: {error}</p>

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h2>Shows</h2>
        <Link to="/shows/new">+ Add show</Link>
      </div>
      <ul style={{ listStyle: 'none', padding: 0 }}>
        {shows.map((show) => (
          <li key={show.id} style={{ padding: '0.75rem 0', borderBottom: '1px solid #ddd' }}>
            <Link to={`/shows/${show.id}`} style={{ fontWeight: 'bold' }}>
              {show.title}
            </Link>
            <div style={{ fontSize: '0.85rem', color: '#666' }}>
              {show.genres?.map((g) => g.name).join(', ')}
            </div>
          </li>
        ))}
      </ul>
    </div>
  )
}
