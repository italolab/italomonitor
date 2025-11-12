
function useInfos() {

    const isAdmin = () => {
        return localStorage.getItem( 'perfil' ) == 'ADMIN';
    }

    const getUsuarioId = () => {
        return localStorage.getItem( 'usuarioId' );
    };

    const getEmpresaId = () => {
        return localStorage.getItem( 'empresaId' );
    };

    return {
        isAdmin,
        getUsuarioId,
        getEmpresaId
    }

}

export default useInfos;

