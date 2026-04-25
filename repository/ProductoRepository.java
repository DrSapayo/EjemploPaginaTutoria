package repository;

import exceptions.*;
import model.Producto;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepository {
    
    private List<Producto> db = new ArrayList<>();

    public void guardar(Producto p) {
        db.add(p);
    }

    public Producto buscarPorId(int id) throws ProductoNoEncotradoException {
        if (id < 0 || id >= db.size()) {
            throw new ProductoNoEncotradoException("ID " + id + " no existe en el sistema.");
        }
        return db.get(id);
    }

    public void eliminar(int id) throws ProductoNoEncotradoException {
        if (id < 0 || id >= db.size()) {
            throw new ProductoNoEncotradoException("No se puede eliminar: ID inexistente.");
        }
        db.remove(id);
    }

}
