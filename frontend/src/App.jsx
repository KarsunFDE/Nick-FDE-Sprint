import { Routes, Route, Link } from 'react-router-dom'
import ShowsListPage from './pages/ShowsListPage.jsx'
import ShowDetailPage from './pages/ShowDetailPage.jsx'
import ShowFormPage from './pages/ShowFormPage.jsx'

export default function App() {
  return (
    <div style={{ maxWidth: 720, margin: '0 auto', padding: '1rem', fontFamily: 'sans-serif' }}>
      <header style={{ marginBottom: '1.5rem' }}>
        <Link to="/" style={{ fontSize: '1.25rem', fontWeight: 'bold', textDecoration: 'none' }}>
          Episode Tracker
        </Link>
      </header>
      <Routes>
        <Route path="/" element={<ShowsListPage />} />
        <Route path="/shows/new" element={<ShowFormPage />} />
        <Route path="/shows/:id" element={<ShowDetailPage />} />
        <Route path="/shows/:id/edit" element={<ShowFormPage />} />
      </Routes>
    </div>
  )
}
