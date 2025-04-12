package TpIntegrador.dto;

public class ProductoDTO {
    private int idProducto;
    private String nombre;
    private float recaudacion;


    public ProductoDTO( ){}

    public ProductoDTO(int idProducto, String nombre, float recaudacion) {
        this.nombre = nombre;
        this.recaudacion = recaudacion;
    }


    public String getNombre() {
        return nombre;
    }

    public float getRecaudacion() {
        return recaudacion;
    }

    @Override
    public String toString() {
        return "PersonaDTO{" +
                "nombre='" + nombre + '\'' +
                ", recaudacion=" + recaudacion+
                '}';
    }



}
