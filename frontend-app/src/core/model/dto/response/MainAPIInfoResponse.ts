
export interface MainAPIInfoResponse {
    totalMemory : number;
	freeMemory : number;
	maxMemory : number;
	usoCpu : number;
	availableProcessors : number;
}

export const DEFAULT_MAIN_API_INFO_OBJ : MainAPIInfoResponse = {
	totalMemory: 0,
	freeMemory: 0,
	maxMemory: 0,
	usoCpu: 0,
	availableProcessors: 0
};