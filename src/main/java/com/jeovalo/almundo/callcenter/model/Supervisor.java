package com.jeovalo.almundo.callcenter.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Supervisor
 */
@Validated

public class Supervisor extends Empleado  {
  @JsonProperty("oficinas")
  @Valid
  private List<String> oficinas = null;

  /**
   * Constructor por defecto 
   */
  public Supervisor() {
   super("SUPERVISOR", TipoPrioridad.SUPERVISOR);
  }
  
  /**
   * 
   * @param nombre del supervisor
   */
  public Supervisor(final String nombre) {
   super(nombre, TipoPrioridad.SUPERVISOR);
  }
  
  public Supervisor oficinas(List<String> oficinas) {
    this.oficinas = oficinas;
    return this;
  }

  public Supervisor addOficinasItem(String oficinasItem) {
    if (this.oficinas == null) {
      this.oficinas = new ArrayList<String>();
    }
    this.oficinas.add(oficinasItem);
    return this;
  }

  /**
   * Las oficinas que supervisa
   * @return oficinas
  **/
  @ApiModelProperty(value = "Las oficinas que supervisa")


  public List<String> getOficinas() {
    return oficinas;
  }

  public void setOficinas(List<String> oficinas) {
    this.oficinas = oficinas;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Supervisor supervisor = (Supervisor) o;
    return Objects.equals(this.oficinas, supervisor.oficinas) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(oficinas, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Supervisor {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    oficinas: ").append(toIndentedString(oficinas)).append("\n");
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

