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
    @MapsId("fornecedorId")
    @JoinColumn(name = "fornecedor_id")
    Fornecedor fornecedor;

    @ManyToOne
    @MapsId("empresaId")
    @JoinColumn(name = "empresa_id")
    Empresa empresa;
}
