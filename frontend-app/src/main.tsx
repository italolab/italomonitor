import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css'

import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './pages/Login';
import App from './pages/App.tsx';
import FilterUsuarios from './pages/usuarios/ManterUsuarios.tsx';
import { AuthProvider } from './context/AuthProvider.tsx';
import UpdateUsuario from './pages/usuarios/UpdateUsuario.tsx';
import CreateUsuario from './pages/usuarios/CreateUsuario.tsx';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <AuthProvider>
      <Router>
        <Routes>
            <Route path='/' element={<Login />} index />
            <Route path='/home' element={<App />} />
            <Route path='/filter-usuarios' element={<FilterUsuarios />} />
            <Route path='/create-usuario' element={<CreateUsuario />} />
            <Route path='/update-usuario/:usuarioId' element={<UpdateUsuario />} />
        </Routes>
      </Router>
    </AuthProvider>
  </StrictMode>
)
