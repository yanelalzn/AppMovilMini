package edu.pe.idat.appminimarket.Modelo;

public class CategoriaCV {

    private int id_categoria;
    private String descripcion;
    private String imagenproducto;

    public CategoriaCV() {
    }

    public CategoriaCV(int id_categoria, String descripcion, String imagenproducto) {
        this.id_categoria = id_categoria;
        this.descripcion = descripcion;
        this.imagenproducto = imagenproducto;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenproducto() {
        return imagenproducto;
    }

    public void setImagenproducto(String imagenproducto) {
        this.imagenproducto = imagenproducto;
    }
}
