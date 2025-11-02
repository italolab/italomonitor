import { createRoot } from 'react-dom/client'
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css'
import './colors.scss'

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
import VincularUsuarioGrupo from './pages/usuarios/VincularUsuarioGrupo.tsx';
import VincularRole from './pages/usuarioGrupos/VincularRole.tsx';
import ManterEmpresas from './pages/empresas/ManterEmpresas.tsx';
import CreateEmpresa from './pages/empresas/CreateEmpresa.tsx';
import UpdateEmpresa from './pages/empresas/UpdateEmpresa.tsx';
import DetalhesEmpresa from './pages/empresas/DetalhesEmpresa.tsx';
import ManterDispositivos from './pages/dispositivos/ManterDispositivos.tsx';
import CreateDispositivo from './pages/dispositivos/CreateDispositivo.tsx';
import UpdateDispositivo from './pages/dispositivos/UpdateDispositivo.tsx';
import DetalhesDispositivo from './pages/dispositivos/DetalhesDispositivo.tsx';
import { StrictMode } from 'react';
import InfosEventos from './pages/eventos/InfosEventos.tsx';
import DetalhesConfig from './pages/config/DetalhesConfig.tsx';
import UpdateConfig from './pages/config/UpdateConfig.tsx';
import ManterMonitorServers from './pages/monitorServers/ManterMonitorServers.tsx';
import CreateMonitorServer from './pages/monitorServers/CreateMonitorServer.tsx';
import UpdateMonitorServer from './pages/monitorServers/UpdateMonitorServer.tsx';
import DetalhesMonitorServer from './pages/monitorServers/DetalhesMonitorServer.tsx';

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
            <Route path='/vincular-usuario-grupo/:usuarioId' element={<VincularUsuarioGrupo />} />
            <Route path='/vincular-role/:usuarioGrupoId' element={<VincularRole />} />

            <Route path='/empresas' element={<ManterEmpresas />} />
            <Route path='/create-empresa' element={<CreateEmpresa />} />
            <Route path='/update-empresa/:empresaId' element={<UpdateEmpresa />} />
            <Route path='/detalhes-empresa/:empresaId' element={<DetalhesEmpresa />} />

            <Route path='/dispositivos/:empresaId' element={<ManterDispositivos />} />
            <Route path='/create-dispositivo' element={<CreateDispositivo />} />
            <Route path='/update-dispositivo/:dispositivoId' element={<UpdateDispositivo />} />
            <Route path='/detalhes-dispositivo/:dispositivoId' element={<DetalhesDispositivo />} />

            <Route path='/infos-eventos/:dispositivoId' element={<InfosEventos />} />

            <Route path='/detalhes-config' element={<DetalhesConfig />} />
            <Route path='/update-config' element={<UpdateConfig />} />

            <Route path='/monitor-servers' element={<ManterMonitorServers />} />
            <Route path='/create-monitor-server' element={<CreateMonitorServer />} />
            <Route path='/update-monitor-server/:monitorServerId' element={<UpdateMonitorServer />} />
            <Route path='/detalhes-monitor-server/:monitorServerId' element={<DetalhesMonitorServer />} />
        </Routes>
      </Router>
    </AuthProvider>
  </StrictMode>
)