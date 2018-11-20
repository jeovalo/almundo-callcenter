package com.jeovalo.almundo.callcenter.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Operador
 */
@Validated

public class Operador extends Empleado  {
  @JsonProperty("sala")
  private String sala = null;

  /**
   * Constructor por defecto 
   */
  public Operador() {
   super("OPERADOR", TipoPrioridad.OPERADOR);
  }
  
  /**
   * 
   * @param nombre del operador
   */
  public Operador(final String nombre) {
   super(nombre, TipoPrioridad.OPERADOR);
  }
  
  public Operador sala(String sala) {
    this.sala = sala;
    return this;
  }

  /**
   * La sala de operadores a la que pertenece
   * @return sala
  **/
  @ApiModelProperty(value = "La sala de operadores a la que pertenece")


  public String getSala() {
    return sala;
  }

  public void setSala(String sala) {
    this.sala = sala;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Operador operador = (Operador) o;
    return Objects.equals(this.sala, operador.sala) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sala, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Operador {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    sala: ").append(toIndentedString(sala)).append("\n");
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

