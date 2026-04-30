import com.sun.net.httpserver.HttpServer;
import exceptions.*;
import repository.*;
import model.Producto;
import java.util.logging.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {

    public static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) throws IOException {

        LOGGER.setUseParentHandlers(false);

        //LOGGER.setLevel(Level.OFF); mata completamente los logs

        
        /*Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        for (Handler h : handlers) {
            rootLogger.removeHandler(h);
        }*/
        
        //Usen FileHandler para crear un archivo .log
        //denle formato a ese .log con SimpleFormatter()
        //agreguen ese handler al Logger
        try {
            FileHandler fh = new FileHandler("app.log", true);
            fh.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fh);
        } catch (Exception e) {
            LOGGER.severe("Error al configurar el logger: " + e.getMessage());
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0); //creamos un servidor que escuche al puerto 8080

        //creamos un repositorio (lista de productos) en el que vamos a generar los productos y almacenarlos
        ProductoRepository repo = new ProductoRepository(); 
        repo.guardar(new Producto(0, "Yogurt de Mora"));
        repo.guardar(new Producto(1, "Leche Entera"));

        //CreateContext establece los puntos, en este caso es /buscar, pero si tuvieramos /borrar, la logica sería distinta por ejemplo
        //http://localhost:8080/buscar?id=0
        server.createContext("/buscar", (exchange) -> {
            String query = exchange.getRequestURI().getQuery(); // Obtiene el ID de la URL
            String response;
            int statusCode;

            try {
/*verifica que la query no esté vacia o que tenga el "id=", hace esa excepcion solo para protegerse en caso el usuario decida que ahora él crea
                                                        las rutas del aire*/
                if (query == null || !query.contains("id=")) throw new IllegalArgumentException();
                
                int id = Integer.parseInt(query.split("id=")[1]);
                Producto p = repo.buscarPorId(id);
                
                response = "<h1>Status 200: OK</h1><p>Producto encontrado: <b>" + p.getNombre() + "</b></p>";
                statusCode = 200;
                LOGGER.info("Producto con ID " + id + "encontrado:" + p.getNombre());

            } catch (Exception e) {
                //Verificamos si esa excepcion generica es una de las nuestras con el instanceof
                if (e instanceof ProductoNoEncotradoException) {
                    response = "<h1>Error 404 Not Found</h1><p>" + e.getMessage() + "</p>";
                    statusCode = 404;
                    LOGGER.warning("Producto no encontrado ID: " + e.getMessage());
                } else {
                    response = "<h1>Error 500 Internal Server Error</h1>";
                    statusCode = 500;
                    LOGGER.severe("Error inesperado");
                }
                //codigo 500 Internal Server Error

                //cambiar el else a 500
                //hacer un else if de alguna manera
                //hacer las validaciones de error en el try
            }

            exchange.sendResponseHeaders(statusCode, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        server.setExecutor(null);
        server.start();
        System.out.println("🚀 Servidor lanzado en http://localhost:8080/buscar?id=0");

    }

}
