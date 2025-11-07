import useEffectOnce from "../core/util/useEffectOnce";
import useInfos from "../core/viewModel/useInfos";
import AppLayout from "../layout/AppLayout"
import AdminDashboard from "./dashboard/AdminDashboard";

function Dashboard() {

  const { isAdmin } = useInfos();

  useEffectOnce( () => {
    if ( isAdmin() === false )
      window.location.href=`/dispositivos/${localStorage.getItem( 'empresaId' )}`
  } )

  return (
    <>
      <AppLayout>
        { isAdmin() === true && <AdminDashboard /> }
      </AppLayout>
    </>
  )
}

export default Dashboard;
