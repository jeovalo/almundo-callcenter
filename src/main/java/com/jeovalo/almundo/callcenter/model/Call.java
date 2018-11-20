package com.jeovalo.almundo.callcenter.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Call
 */
@Validated
public class Call   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("cliente")
  private String cliente = null;

  @JsonProperty("telefono")
  private String telefono = null;

  @JsonProperty("wait")
  private Boolean wait = false;

  @JsonProperty("mensaje")
  private String mensaje = null;
  
  /**
   * 
   * @param id id del cliente que llama
   * @param cliente nombre del cliente que llama
   * @param telefono nombre del cliente que llama
   * @param wait true si el cliente quiere esperar en caso que no existen operadores disponibles
   * @param mensaje asunto o incidencia de la llamada del cliente
   */
  public Call(final Long id, final String cliente, final String telefono, final Boolean wait, final String mensaje) {
    this.id = id;
    this.cliente = cliente;
    this.telefono = telefono;
    this.wait = wait;
    this.mensaje = mensaje;
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

  public Call cliente(String cliente) {
    this.cliente = cliente;
    return this;
  }

  /**
   * Get cliente
   * @return cliente
  **/
  @ApiModelProperty(value = "")


  public String getCliente() {
    return cliente;
  }

  public void setCliente(String cliente) {
    this.cliente = cliente;
  }

  public Call telefono(String telefono) {
    this.telefono = telefono;
    return this;
  }

  /**
   * Get telefono
   * @return telefono
  **/
  @ApiModelProperty(value = "")


  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  /**
   * Get wait
   * @return wait
  **/
  @ApiModelProperty(value = "")
  public Boolean isWait() {
    return wait;
  }

  public void setWait(Boolean wait) {
    this.wait = wait;
  }

  public Call mensaje(String mensaje) {
    this.mensaje = mensaje;
    return this;
  }

  /**
   * Get mensaje
   * @return mensaje
  **/
  @ApiModelProperty(value = "")


  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Call call = (Call) o;
    return Objects.equals(this.id, call.id) &&
        Objects.equals(this.cliente, call.cliente) &&
        Objects.equals(this.telefono, call.telefono) &&
        Objects.equals(this.wait, call.wait) &&
        Objects.equals(this.mensaje, call.mensaje);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, cliente, telefono, wait, mensaje);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Call {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    cliente: ").append(toIndentedString(cliente)).append("\n");
    sb.append("    telefono: ").append(toIndentedString(telefono)).append("\n");
    sb.append("    isWait: ").append(toIndentedString(wait)).append("\n");
    sb.append("    mensaje: ").append(toIndentedString(mensaje)).append("\n");
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

