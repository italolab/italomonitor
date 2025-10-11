import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css'

import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './pages/Login';
import App from './pages/App.tsx';
import FilterUsuarios from './pages/usuarios/FilterUsuarios.tsx';
import { AuthProvider } from './context/AuthProvider.tsx';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <AuthProvider>
      <Router>
        <Routes>
            <Route path='/' Component={Login} index />
            <Route path='/home' Component={App} />
            <Route path='/filter-usuarios' Component={FilterUsuarios} />
        </Routes>
      </Router>
    </AuthProvider>
  </StrictMode>
)
