package com.ufrpe.bsi.mpoo.wallotapp.estatistica.negocio;

import com.ufrpe.bsi.mpoo.wallotapp.estatistica.dominio.Orcamento;
import com.ufrpe.bsi.mpoo.wallotapp.estatistica.persistencia.OrcamentoDAO;

import java.util.ArrayList;

public class OrcamentoServices {
    OrcamentoDAO orcamentoDAO = new OrcamentoDAO();

    public long cadastrarOrcamento(Orcamento orcamento) {
        return  orcamentoDAO.cadastrarOrcamento(orcamento);
    }

    public ArrayList<Orcamento> listarOrcamentosPorData(long idUsuario) {
        return orcamentoDAO.getOrcamentosPorData(idUsuario);
    }
}
