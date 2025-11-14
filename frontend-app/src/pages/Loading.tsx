import AppSpinner from "../components/AppSpinner";

function Loading() {
    return (
        <div className="d-flex justify-content-center align-items-center vw-100 hw-100">
            <AppSpinner visible={true} showEsp={false} className="mx-auto" />
        </div>
    );
}

export default Loading;