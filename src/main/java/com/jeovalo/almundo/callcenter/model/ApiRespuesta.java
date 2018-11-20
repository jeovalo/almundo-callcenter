package com.jeovalo.almundo.callcenter.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * ApiRespuesta
 */
@Validated
@javax.xml.bind.annotation.XmlRootElement
public class ApiRespuesta   {
  public static final int ERROR = 1;
  public static final int WARNING = 2;
  public static final int INFO = 3;
  public static final int OK = 4;
  public static final int TOO_BUSY = 5;

  @JsonProperty("codigo")
  private Integer codigo = null;

  @JsonProperty("tipo")
  private String tipo = null;

  @JsonProperty("mensaje")
  private String mensaje = null;

  public ApiRespuesta(){}

  public ApiRespuesta(int code, String mensaje){
      this.codigo = code;
      switch(code){
      case ERROR:
        setTipo("error");
          break;
      case WARNING:
        setTipo("warning");
          break;
      case INFO:
        setTipo("info");
          break;
      case OK:
        setTipo("ok");
          break;
      case TOO_BUSY:
        setTipo("too busy");
          break;
      default:
        setTipo("unknown");
          break;
      }
      this.mensaje = mensaje;
  }
  
  
  public ApiRespuesta codigo(Integer codigo) {
    this.codigo = codigo;
    return this;
  }

  /**
   * Get codigo
   * @return codigo
  **/
  @ApiModelProperty(value = "")


  public Integer getCodigo() {
    return codigo;
  }

  public void setCodigo(Integer codigo) {
    this.codigo = codigo;
  }

  public ApiRespuesta tipo(String tipo) {
    this.tipo = tipo;
    return this;
  }

  /**
   * Get tipo
   * @return tipo
  **/
  @ApiModelProperty(value = "")
  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public ApiRespuesta mensaje(String mensaje) {
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
    ApiRespuesta apiRespuesta = (ApiRespuesta) o;
    return Objects.equals(this.codigo, apiRespuesta.codigo) &&
        Objects.equals(this.tipo, apiRespuesta.tipo) &&
        Objects.equals(this.mensaje, apiRespuesta.mensaje);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigo, tipo, mensaje);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiRespuesta {\n");
    
    sb.append("    codigo: ").append(toIndentedString(codigo)).append("\n");
    sb.append("    tipo: ").append(toIndentedString(tipo)).append("\n");
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

