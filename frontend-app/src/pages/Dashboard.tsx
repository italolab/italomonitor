import AppLayout from "../layout/AppLayout"
import AdminDashboard from "./dashboard/AdminDashboard";
import UsuarioDashboard from "./dashboard/UsuarioDashboard";

function Dashboard() {

  return (
    <>
      <AppLayout>
        { localStorage.getItem( 'perfil' ) == 'ADMIN' ?
          <AdminDashboard />
        :
          <UsuarioDashboard />
        }
      </AppLayout>
    </>
  )
}

export default Dashboard;
