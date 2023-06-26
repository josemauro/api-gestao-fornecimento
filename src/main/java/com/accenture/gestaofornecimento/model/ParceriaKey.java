package com.accenture.gestaofornecimento.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
class ParceriaKey implements Serializable {

    private static final long serialVersionUID = 1L;

	@Column(name = "fornecedor_id")
    String fornecedorId;

    @Column(name = "empresa_id")
    String empresaId;
    
//    public void setFornecedor(String fornecedorId) {
//        this.fornecedorId = fornecedorId;
//    }
//
//    public String getEmpresaId() {
//        return empresaId;
//    }
//
//    public void setEmpresaId(String empresaId) {
//        this.empresaId = empresaId;
//    }
}