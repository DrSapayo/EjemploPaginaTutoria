import java.util.logging.*;
import java.io.IOException;

class SimuladorCajero{

    public static final Logger LOGGER = Logger.getLogger("CajeroLog");

    public static void main(String[] args) {

        //1. usen FileHandler para crear un archivo .log
        //denle formato a ese .log con SimpleFormatter()
        //agreguen ese handler al Logger
        
        try{
            FileHandler fh = new FileHandler("app.log", true);

            fh.setFormatter(new SimpleFormatter());
            
            LOGGER.addHandler(fh);

            retiros(1000, 300);
            retiros(10, 300);
            retiros(1000, 0);
            retiros(1000, "a");

        } catch (IOException e){

            LOGGER.severe("ERROR!" + e.getMessage());
            

        }
        //3. usen el metodo para realizar retiros y prueben los casos

    }
    //saldo = dinero en la cuenta y monto, lo que se va a retirar
    public static void retiros(int saldo, int monto){

        try {
            if (monto <= 0 ) {
                throw new IOException("el monto debe ser mayor que 0");
            }

            else if(saldo >= monto){
                saldo -= monto;

                LOGGER.info("RETIRO EXITOSO: "+ monto );
            }

            else if(monto > saldo){
                LOGGER.warning("No hay fondos suficientes: "+saldo);
            }
        } catch (Exception e) {
            LOGGER.severe("error en el retiro "+ e.getMessage());
        }
        
    }

    //2. creen un metodo para realizar los retiros
    //si el retiro sale correcto, deberá mostrar la informacion de lo que paso
    //si no tiene saldo suficiente, deberá mostrar el error
    //y asegurarse de que si algo pasa con diferencia entre las otras dos, que tire un SEVERE
}