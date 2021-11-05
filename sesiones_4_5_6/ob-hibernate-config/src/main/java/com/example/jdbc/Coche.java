package com.example.jdbc;

public class Coche {

    private Integer id;
    private String modelo;
    private String fabricante;
    private Integer numCilindros;
    private Double numCv;
    public Coche(Integer id, String modelo, String fabricante, Integer numCilindros, Double numCv) {
        this.id = id;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.numCilindros = numCilindros;
        this.numCv = numCv;
    }
    public Integer getId() {
        return id;
    }
    public String getModelo() {
        return modelo;
    }
    public String getFabricante() {
        return fabricante;
    }
    public Integer getNumCilindros() {
        return numCilindros;
    }
    public Double getNumCv() {
        return numCv;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
    public void setNumCilindros(Integer numCilindros) {
        this.numCilindros = numCilindros;
    }
    public void setNumCv(Double numCv) {
        this.numCv = numCv;
    }

}
