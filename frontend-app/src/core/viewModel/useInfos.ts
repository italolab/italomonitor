
function useInfos() {

    const isAdmin = () => {
        return localStorage.getItem( 'perfil' ) == 'ADMIN';
    }

    return {
        isAdmin
    }

}

export default useInfos;

