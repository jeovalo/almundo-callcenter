package com.jeovalo.almundo.callcenter.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Director
 */
@Validated

public class Director extends Empleado  {
  @JsonProperty("oficina")
  private String oficina = null;

  /**
   * Constructor por defecto 
   */
  public Director() {
   super("DIRECTOR", TipoPrioridad.DIRECTOR);
  }
  
  /**
   * 
   * @param nombre del director
   */
  public Director(final String nombre) {
   super(nombre, TipoPrioridad.DIRECTOR);
  }
  
  public Director oficina(String oficina) {
    this.oficina = oficina;
    return this;
  }

  /**
   * La oficina que dirige
   * @return oficina
  **/
  @ApiModelProperty(value = "La oficina que dirige")


  public String getOficina() {
    return oficina;
  }

  public void setOficina(String oficina) {
    this.oficina = oficina;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Director director = (Director) o;
    return Objects.equals(this.oficina, director.oficina) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(oficina, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Director {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    oficina: ").append(toIndentedString(oficina)).append("\n");
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

