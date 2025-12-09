import { createRoot } from 'react-dom/client'
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css'
import './colors.scss'

import { StrictMode, Suspense, lazy } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import { AuthProvider } from './context/AuthProvider.tsx';
import Loading from './pages/Loading.tsx';
import EfetuarPagamento from './pages/pagamentos/EfetuarPagamento.tsx';
import Home from './pages/Home.tsx';

const Login = lazy( () => import( './pages/Login.tsx' ) );
const Dashboard = lazy( () => import( './pages/Dashboard.tsx' ) );

const ManterUsuarios = lazy( () => import( './pages/usuarios/ManterUsuarios.tsx' ) );
const UpdateUsuario = lazy( () => import( './pages/usuarios/UpdateUsuario.tsx' ) );
const CreateUsuario = lazy( () => import( './pages/usuarios/CreateUsuario.tsx' ) );
const DetalhesUsuario = lazy( () => import( './pages/usuarios/DetalhesUsuario.tsx' ) );

const ManterUsuarioGrupos = lazy( () => import( './pages/usuarioGrupos/ManterUsuarioGrupos.tsx' ) );
const CreateUsuarioGrupo = lazy( () => import( './pages/usuarioGrupos/CreateUsuarioGrupo.tsx' ) );
const UpdateUsuarioGrupo = lazy( () => import( './pages/usuarioGrupos/UpdateUsuarioGrupo.tsx' ) );
const DetalhesUsuarioGrupo = lazy( () => import( './pages/usuarioGrupos/DetalhesUsuarioGrupo.tsx' ) );

const ManterRoles = lazy( () => import( './pages/roles/ManterRoles.tsx' ) );
const UpdateRole = lazy( () => import( './pages/roles/UpdateRole.tsx' ) );
const CreateRole = lazy( () => import( './pages/roles/CreateRole.tsx' ) );
const DetalhesRole = lazy( () => import( './pages/roles/DetalhesRole.tsx' ) );

const VincularUsuarioGrupo = lazy( () => import( './pages/usuarios/VincularUsuarioGrupo.tsx' ) );
const VincularRole = lazy( () => import( './pages/usuarioGrupos/VincularRole.tsx' ) );

const ManterEmpresas = lazy( () => import( './pages/empresas/ManterEmpresas.tsx' ) );
const CreateEmpresa = lazy( () => import( './pages/empresas/CreateEmpresa.tsx' ) );
const UpdateEmpresa = lazy( () => import( './pages/empresas/UpdateEmpresa.tsx' ) );
const DetalhesEmpresa = lazy( () => import( './pages/empresas/DetalhesEmpresa.tsx' ) );

const ShowDispositivos = lazy( () => import( './pages/dispositivos/ShowDispositivos.tsx' ) );
const CreateDispositivo = lazy( () => import( './pages/dispositivos/CreateDispositivo.tsx' ) );
const UpdateDispositivo = lazy( () => import( './pages/dispositivos/UpdateDispositivo.tsx' ) );
const DetalhesDispositivo = lazy( () => import( './pages/dispositivos/DetalhesDispositivo.tsx' ) );

const ManterAgentes = lazy( () => import( './pages/agentes/ManterAgentes.tsx' ) );
const CreateAgente = lazy( () => import( './pages/agentes/CreateAgente.tsx' ) );
const UpdateAgente = lazy( () => import( './pages/agentes/UpdateAgente.tsx' ) );
const DetalhesAgente = lazy( () => import( './pages/agentes/DetalhesAgente.tsx' ) );

const InfosEventos = lazy( () => import( './pages/eventos/InfosEventos.tsx' ) );
const DetalhesConfig = lazy( () => import( './pages/config/DetalhesConfig.tsx' ) );
const UpdateConfig = lazy( () => import( './pages/config/UpdateConfig.tsx' ) );

const ManterMonitorServers = lazy( () => import( './pages/monitorServers/ManterMonitorServers.tsx' ) );
const CreateMonitorServer = lazy( () => import( './pages/monitorServers/CreateMonitorServer.tsx' ) );
const UpdateMonitorServer = lazy( () => import( './pages/monitorServers/UpdateMonitorServer.tsx' ) );
const DetalhesMonitorServer = lazy( () => import( './pages/monitorServers/DetalhesMonitorServer.tsx' ) );

const NoAdminUpdateEmpresa = lazy( () => import( './pages/empresas/NoAdminUpdateEmpresa.tsx' ) );
const NoAdminDetalhesEmpresa = lazy( () => import( './pages/empresas/NoAdminDetalhesEmpresa.tsx' ) );

const AlterSenha = lazy( () => import( './pages/usuarios/AlterSenha.tsx' ) );

const ShowPagamentos = lazy( () => import( './pages/pagamentos/ShowPagamentos.tsx' ) );

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <AuthProvider>
      <Router>
        <Suspense fallback={<Loading />}>
          <Routes>
              <Route path='/' element={<Home />} />
              <Route path='/login/:redirectTo' element={<Login />} index />
              <Route path='/dashboard' element={<Dashboard />} />
              
              <Route path='/usuarios' element={<ManterUsuarios />} />
              <Route path='/create-usuario' element={<CreateUsuario />} />
              <Route path='/update-usuario/:usuarioId' element={<UpdateUsuario />} />
              <Route path='/detalhes-usuario/:usuarioId' element={<DetalhesUsuario />} />
              <Route path='/alter-senha/:usuarioId' element={<AlterSenha /> } />
              
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
              <Route path='/no-admin-update-empresa/:empresaId' element={<NoAdminUpdateEmpresa />} />
              <Route path='/no-admin-detalhes-empresa/:empresaId' element={<NoAdminDetalhesEmpresa />} />

              <Route path='/dispositivos/:empresaId' element={<ShowDispositivos />} />
              <Route path='/create-dispositivo/:empresaId' element={<CreateDispositivo />} />
              <Route path='/update-dispositivo/:dispositivoId' element={<UpdateDispositivo />} />
              <Route path='/detalhes-dispositivo/:dispositivoId' element={<DetalhesDispositivo />} />

              <Route path='/agentes/:empresaId' element={<ManterAgentes />} />
              <Route path='/create-agente/:empresaId' element={<CreateAgente />} />
              <Route path='/update-agente/:agenteId/:empresaId' element={<UpdateAgente />} />
              <Route path='/detalhes-agente/:agenteId/:empresaId' element={<DetalhesAgente />} />

              <Route path='/infos-eventos/:dispositivoId' element={<InfosEventos />} />

              <Route path='/detalhes-config' element={<DetalhesConfig />} />
              <Route path='/update-config' element={<UpdateConfig />} />

              <Route path='/monitor-servers' element={<ManterMonitorServers />} />
              <Route path='/create-monitor-server' element={<CreateMonitorServer />} />
              <Route path='/update-monitor-server/:monitorServerId' element={<UpdateMonitorServer />} />
              <Route path='/detalhes-monitor-server/:monitorServerId' element={<DetalhesMonitorServer />} />

              <Route path='/pagamentos/:empresaId' element={<ShowPagamentos />} />
              <Route path='/efetuar-pagamento/:empresaId' element={<EfetuarPagamento />} />
          </Routes>
        </Suspense>
      </Router>
    </AuthProvider>
  </StrictMode>
)