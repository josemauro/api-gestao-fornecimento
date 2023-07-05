package com.accenture.gestaofornecimento.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class Parceria {
	@EmbeddedId
    private ParceriaKey parceriaId;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "fornecedor_id")
    Fornecedor fornecedor;

    @ManyToOne
    @MapsId("cnpj")
    @JoinColumn(name = "empresa_id")
    Empresa empresa;
    
    public ParceriaKey getParceria() {
        return parceriaId;
    }

    public void setParceria(ParceriaKey parceriaId) {
        this.parceriaId = parceriaId;
    }
}
