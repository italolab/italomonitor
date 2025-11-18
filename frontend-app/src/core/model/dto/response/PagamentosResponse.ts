import type { PagamentoResponse } from "./PagamentoResponse";

export interface PagamentosResponse {
    pagamentos : PagamentoResponse[];
    valorDebito : number;
}

export const DEFAULT_PAGS_OBJ : PagamentosResponse = {
    pagamentos: [],
    valorDebito: 0
};