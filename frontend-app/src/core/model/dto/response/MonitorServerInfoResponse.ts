
export interface MonitorServerInfoResponse {
    totalMemory : number;
	freeMemory : number;
	maxMemory : number;
	usoCpu : number;
	availableProcessors : number;
    numThreadsAtivas : number;
}

export const DEFAULT_MONITOR_SERVER_INFO_OBJ : MonitorServerInfoResponse = {
	totalMemory: 0,
	freeMemory: 0,
	maxMemory: 0,
	usoCpu: 0,
	availableProcessors: 0,
	numThreadsAtivas: 0
};