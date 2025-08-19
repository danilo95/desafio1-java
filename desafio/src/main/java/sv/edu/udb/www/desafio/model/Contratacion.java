package sv.edu.udb.www.desafio.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Contratacion {
    private int idContratacion;
    private int idDepartamento;
    private int idEmpleado;
    private int idCargo;
    private int idTipoContratacion;
    private Date fechaContratacion;
    private BigDecimal salario;
    private boolean estado;

    // !IMPORTANT: Campos de solo visualización porque son joins no puedo modificarlos directamente
    private String nombreDepartamento;
    private String nombreEmpleado;
    private String nombreCargo;
    private String nombreTipoContratacion;

    public int getIdContratacion() { return idContratacion; }
    public void setIdContratacion(int idContratacion) { this.idContratacion = idContratacion; }

    public int getIdDepartamento() { return idDepartamento; }
    public void setIdDepartamento(int idDepartamento) { this.idDepartamento = idDepartamento; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public int getIdCargo() { return idCargo; }
    public void setIdCargo(int idCargo) { this.idCargo = idCargo; }

    public int getIdTipoContratacion() { return idTipoContratacion; }
    public void setIdTipoContratacion(int idTipoContratacion) { this.idTipoContratacion = idTipoContratacion; }

    public Date getFechaContratacion() { return fechaContratacion; }
    public void setFechaContratacion(Date fechaContratacion) { this.fechaContratacion = fechaContratacion; }

    public BigDecimal getSalario() { return salario; }
    public void setSalario(BigDecimal salario) { this.salario = salario; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    public String getNombreDepartamento() { return nombreDepartamento; }
    public void setNombreDepartamento(String nombreDepartamento) { this.nombreDepartamento = nombreDepartamento; }

    public String getNombreEmpleado() { return nombreEmpleado; }
    public void setNombreEmpleado(String nombreEmpleado) { this.nombreEmpleado = nombreEmpleado; }

    public String getNombreCargo() { return nombreCargo; }
    public void setNombreCargo(String nombreCargo) { this.nombreCargo = nombreCargo; }

    public String getNombreTipoContratacion() { return nombreTipoContratacion; }
    public void setNombreTipoContratacion(String nombreTipoContratacion) { this.nombreTipoContratacion = nombreTipoContratacion; }
}