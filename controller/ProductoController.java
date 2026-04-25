package controller;

import exceptions.*;
import repository.*;

public class ProductoController {

    private ProductoRepository repo = new ProductoRepository();

    public void getProducto(String inputId) throws ProductoNoEncotradoException{
        try {
            int id = Integer.parseInt(inputId);
            System.out.println("Status 200 OK: "+ repo.buscarPorId(id));
        } catch (NumberFormatException e){
            System.err.println("Status 400 Bad Request: el ID debe ser un número "+e.getMessage());
        } catch (ProductoNoEncotradoException e){
            System.err.println("Status 404 Not Found: "+e.getMessage());
        } finally {
            System.out.println("Log: Petición finalizada");
        }
    }
    
}
