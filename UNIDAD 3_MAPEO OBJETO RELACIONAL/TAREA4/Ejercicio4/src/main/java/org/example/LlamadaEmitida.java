package org.example;

import jakarta.persistence.*;

/**
 * @author Juan Gabriel Galarza Claros
 */

@Entity
@Table(name = "LLAMADAS_EMITIDAS")
public class LlamadaEmitida {
    @Id
    @Column(name = "CODIGO_LLAMADA", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SIM_LLAMANTE", nullable = false)
    private TarjetaTelefonica simLlamante;

    @Column(name = "NUMERO_LLAMADO", nullable = false)
    private Integer numeroLlamado;

    @Column(name = "DURACION_LLAMADA", nullable = false)
    private Integer duracionLlamada;

    @Column(name = "IMPORTE_LLAMADA", nullable = false)
    private Float importeLlamada;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TarjetaTelefonica getSimLlamante() {
        return simLlamante;
    }

    public void setSimLlamante(TarjetaTelefonica simLlamante) {
        this.simLlamante = simLlamante;
    }

    public Integer getNumeroLlamado() {
        return numeroLlamado;
    }

    public void setNumeroLlamado(Integer numeroLlamado) {
        this.numeroLlamado = numeroLlamado;
    }

    public Integer getDuracionLlamada() {
        return duracionLlamada;
    }

    public void setDuracionLlamada(Integer duracionLlamada) {
        this.duracionLlamada = duracionLlamada;
    }

    public Float getImporteLlamada() {
        return importeLlamada;
    }

    public void setImporteLlamada(Float importeLlamada) {
        this.importeLlamada = importeLlamada;
    }

    @Override
    public String toString() {
        return "LlamadaEmitida{id=" + id
                + ", sim=" + (simLlamante != null ? simLlamante.getId() : null)
                + ", destino=" + numeroLlamado
                + ", duracion=" + duracionLlamada + "s"
                + ", importe=" + importeLlamada + " €}";
    }
}