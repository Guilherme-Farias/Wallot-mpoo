package com.ufrpe.bsi.mpoo.wallotapp.orcamento.negocio;

import com.ufrpe.bsi.mpoo.wallotapp.orcamento.dominio.Orcamento;
import com.ufrpe.bsi.mpoo.wallotapp.orcamento.persistencia.OrcamentoDAO;

import java.util.ArrayList;

public class OrcamentoServices {
    OrcamentoDAO orcamentoDAO = new OrcamentoDAO();

    public long cadastrarOrcamento(Orcamento orcamento) {
        return  orcamentoDAO.cadastrarOrcamento(orcamento);
    }

    public ArrayList<Orcamento> listarOrcamentos(long idUsuario) {
        return orcamentoDAO.getOrcamentos(idUsuario);
    }
}
