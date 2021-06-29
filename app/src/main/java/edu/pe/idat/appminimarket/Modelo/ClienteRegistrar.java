package edu.pe.idat.appminimarket.Modelo;

public class ClienteRegistrar {
    private int id_cliente;
    private String nombre;
    private String apellidos;
    private String usuario;
    private String password;
    private String telefono;
    private String direccion;
    private TipoDocumento tipodoc;

    private int id_tipo_doc;
    private String num_doc;

    public ClienteRegistrar() {
    }

    public ClienteRegistrar(int id_cliente, String nombre, String apellidos, String usuario, String password, String telefono, String direccion, TipoDocumento tipodoc, int id_tipo_doc, String num_doc) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.password = password;
        this.telefono = telefono;
        this.direccion = direccion;
        this.tipodoc = tipodoc;
        this.id_tipo_doc = id_tipo_doc;
        this.num_doc = num_doc;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public TipoDocumento getTipodoc() {
        return tipodoc;
    }

    public void setTipodoc(TipoDocumento tipodoc) {
        this.tipodoc = tipodoc;
    }

    public int getId_tipo_doc() {
        return id_tipo_doc;
    }

    public void setId_tipo_doc(int id_tipo_doc) {
        this.id_tipo_doc = id_tipo_doc;
    }

    public String getNum_doc() {
        return num_doc;
    }

    public void setNum_doc(String num_doc) {
        this.num_doc = num_doc;
    }
}

