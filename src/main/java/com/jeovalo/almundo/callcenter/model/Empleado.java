package com.jeovalo.almundo.callcenter.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Empleado
 */
@Validated
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipoPrioridad", visible = true )
@JsonSubTypes({
  @JsonSubTypes.Type(value = Operador.class, name = "1"),
  @JsonSubTypes.Type(value = Supervisor.class, name = "2"),
  @JsonSubTypes.Type(value = Director.class, name = "3"),
})

public abstract class Empleado implements Comparable<Empleado> {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("nombre")
  private String nombre = null;

  // @JsonProperty("tipoPrioridad")
  // @JsonIgnore
  private TipoPrioridad tipoPrioridad = null;

  @JsonProperty("ocupado")
  private Boolean ocupado = false;

  /**
   * 
   * @param nombre de empleado
   * @param tipoPrioridad del empleado 
   * @param prioridad
   */
  public Empleado(final String nombre, final TipoPrioridad tipoPrioridad ) {
   this.nombre = nombre;
   this.tipoPrioridad = tipoPrioridad;
  }
  
  public Empleado id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Empleado nombre(String nombre) {
    this.nombre = nombre;
    return this;
  }

  /**
   * Get nombre
   * @return nombre
  **/
  @ApiModelProperty(value = "")
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Empleado tipoPrioridad(TipoPrioridad tipoPrioridad) {
    this.tipoPrioridad = tipoPrioridad;
    return this;
  }

  /**
   * Tipo Prioridad del Empleado
   * @return tipoPrioridad
  **/
  // @ApiModelProperty(required = true, value = "Tipo Prioridad del Empleado")
//  @NotNull
  public TipoPrioridad getTipoPrioridad() {
    return tipoPrioridad;
  }

  public void setTipoPrioridad(TipoPrioridad tipoPrioridad) {
    this.tipoPrioridad = tipoPrioridad;
  }

  public Empleado ocupado(Boolean ocupado) {
    this.ocupado = ocupado;
    return this;
  }

  /**
   * Get ocupado
   * @return ocupado
  **/
  @ApiModelProperty(value = "")
  public Boolean isOcupado() {
    return ocupado;
  }

  public void setOcupado(Boolean ocupado) {
    this.ocupado = ocupado;
  }

  
  /**
   *  Se utiliza en la Cola de Prioridad (1 > 2 > 3)
   */
  @Override
  public int compareTo(final Empleado empleado) {
   return this.getTipoPrioridad().compareTo(empleado.getTipoPrioridad());
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Empleado empleado = (Empleado) o;
    return Objects.equals(this.id, empleado.id) &&
        Objects.equals(this.nombre, empleado.nombre) &&
        Objects.equals(this.tipoPrioridad, empleado.tipoPrioridad) &&
        Objects.equals(this.ocupado, empleado.ocupado);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nombre, tipoPrioridad, ocupado);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Empleado {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nombre: ").append(toIndentedString(nombre)).append("\n");
    sb.append("    tipoPrioridad: ").append(toIndentedString(tipoPrioridad)).append("\n");
    sb.append("    ocupado: ").append(toIndentedString(ocupado)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

