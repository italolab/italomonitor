import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css'

import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './pages/Login';
import App from './pages/App.tsx';
import ManterUsuarios from './pages/usuarios/ManterUsuarios.tsx';
import { AuthProvider } from './context/AuthProvider.tsx';
import UpdateUsuario from './pages/usuarios/UpdateUsuario.tsx';
import CreateUsuario from './pages/usuarios/CreateUsuario.tsx';
import DetalhesUsuario from './pages/usuarios/DetalhesUsuario.tsx';
import ManterUsuarioGrupos from './pages/usuarioGrupos/ManterUsuarioGrupos.tsx';
import CreateUsuarioGrupo from './pages/usuarioGrupos/CreateUsuarioGrupo.tsx';
import UpdateUsuarioGrupo from './pages/usuarioGrupos/UpdateUsuarioGrupo.tsx';
import DetalhesUsuarioGrupo from './pages/usuarioGrupos/DetalhesUsuarioGrupo.tsx';
import ManterRoles from './pages/roles/ManterRoles.tsx';
import UpdateRole from './pages/roles/UpdateRole.tsx';
import CreateRole from './pages/roles/CreateRole.tsx';
import DetalhesRole from './pages/roles/DetalhesRole.tsx';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <AuthProvider>
      <Router>
        <Routes>
            <Route path='/' element={<Login />} index />
            <Route path='/home' element={<App />} />
            <Route path='/usuarios' element={<ManterUsuarios />} />
            <Route path='/create-usuario' element={<CreateUsuario />} />
            <Route path='/update-usuario/:usuarioId' element={<UpdateUsuario />} />
            <Route path='/detalhes-usuario/:usuarioId' element={<DetalhesUsuario />} />
            <Route path='/usuario-grupos' element={<ManterUsuarioGrupos /> } />
            <Route path='/create-usuario-grupo' element={<CreateUsuarioGrupo />} />
            <Route path='/update-usuario-grupo/:usuarioGrupoId' element={<UpdateUsuarioGrupo />} />
            <Route path='/detalhes-usuario-grupo/:usuarioGrupoId' element={<DetalhesUsuarioGrupo />} />
            <Route path='/roles' element={<ManterRoles /> } />
            <Route path='/create-role' element={<CreateRole />} />
            <Route path='/update-role/:roleId' element={<UpdateRole />} />
            <Route path='/detalhes-role/:roleId' element={<DetalhesRole />} />
        </Routes>
      </Router>
    </AuthProvider>
  </StrictMode>
)
