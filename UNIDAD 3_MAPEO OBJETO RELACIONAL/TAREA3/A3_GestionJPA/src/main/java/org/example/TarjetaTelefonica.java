package org.example;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "TARJETAS_TELEFONICAS")
public class TarjetaTelefonica {
    @Id
    @Column(name = "NUMERO_SIM", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CODIGO_AGENTE_ASOCIADO", nullable = false)
    private Agente codigoAgenteAsociado;

    @OneToMany(mappedBy = "simLlamante")
    private Set<LlamadaEmitida> llamadasEmitidas = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Agente getCodigoAgenteAsociado() {
        return codigoAgenteAsociado;
    }

    public void setCodigoAgenteAsociado(Agente codigoAgenteAsociado) {
        this.codigoAgenteAsociado = codigoAgenteAsociado;
    }

    public Set<LlamadaEmitida> getLlamadasEmitidas() {
        return llamadasEmitidas;
    }

    public void setLlamadasEmitidas(Set<LlamadaEmitida> llamadasEmitidas) {
        this.llamadasEmitidas = llamadasEmitidas;
    }

}