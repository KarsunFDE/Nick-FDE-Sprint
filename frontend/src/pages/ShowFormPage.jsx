import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { createShow, getGenres, getShow, updateShow } from '../api/client.js'

export default function ShowFormPage() {
  const { id } = useParams()
  const navigate = useNavigate()
  const isEditing = Boolean(id)

  const [title, setTitle] = useState('')
  const [description, setDescription] = useState('')
  const [genres, setGenres] = useState([])
  const [selectedGenreIds, setSelectedGenreIds] = useState([])
  const [error, setError] = useState(null)
  const [submitting, setSubmitting] = useState(false)

  useEffect(() => {
    getGenres()
      .then(setGenres)
      .catch((err) => setError(err.message))
  }, [])

  useEffect(() => {
    if (!isEditing) return
    getShow(id).then((show) => {
      setTitle(show.title)
      setDescription(show.description ?? '')
      setSelectedGenreIds((show.genres ?? []).map((g) => g.id))
    })
  }, [id, isEditing])

  function toggleGenre(genreId) {
    setSelectedGenreIds((prev) =>
      prev.includes(genreId) ? prev.filter((g) => g !== genreId) : [...prev, genreId],
    )
  }

  async function handleSubmit(event) {
    event.preventDefault()
    setSubmitting(true)
    setError(null)
    try {
      const payload = { title, description, genreIds: selectedGenreIds }
      const saved = isEditing ? await updateShow(id, payload) : await createShow(payload)
      navigate(`/shows/${saved.id}`)
    } catch (err) {
      setError(err.message)
    } finally {
      setSubmitting(false)
    }
  }

  return (
    <div>
      <h2>{isEditing ? 'Edit show' : 'Add show'}</h2>
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '0.75rem' }}>
          <label>
            Title
            <br />
            <input value={title} onChange={(e) => setTitle(e.target.value)} required />
          </label>
        </div>
        <div style={{ marginBottom: '0.75rem' }}>
          <label>
            Description
            <br />
            <textarea value={description} onChange={(e) => setDescription(e.target.value)} />
          </label>
        </div>
        <div style={{ marginBottom: '0.75rem' }}>
          <p style={{ marginBottom: '0.25rem' }}>Genres</p>
          {genres.map((genre) => (
            <label key={genre.id} style={{ marginRight: '1rem' }}>
              <input
                type="checkbox"
                checked={selectedGenreIds.includes(genre.id)}
                onChange={() => toggleGenre(genre.id)}
              />
              {' ' + genre.name}
            </label>
          ))}
        </div>
        {error && <p style={{ color: 'crimson' }}>{error}</p>}
        <button type="submit" disabled={submitting}>
          {submitting ? 'Saving...' : 'Save'}
        </button>
      </form>
    </div>
  )
}
