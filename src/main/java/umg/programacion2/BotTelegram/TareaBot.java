package umg.programacion2.BotTelegram;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TareaBot extends TelegramLongPollingBot {

    //Funcion para el cambio

    public double cambio (double x)
    {
        x = x*8.51f;
        return x;
    }
    @Override
    public String getBotUsername() {
        return "Mash707_bot";
    }

    @Override
    public String getBotToken() {
        return "7321007724:AAHW-ULH1vn24my8PBW69ragjdYkREZScSo";
    }

    //El método onUpdateReceived(Update update) de la clase Bot se usa para manejar todas las actualizaciones que el
    // bot recibe.
    // Dependiendo del tipo de actualización, se toman diferentes acciones.

    @Override
    public void onUpdateReceived(Update update) {

        //Obtener informacion de la persona que manda los mensajes

        String nombre = update.getMessage().getFrom().getFirstName();
        String apellido = update.getMessage().getFrom().getLastName();
        String nickName = update.getMessage().getFrom().getUserName();

        //Se verifica si la actualización contiene un mensaje y si ese mensaje tiene texto.
        //Luego se procesa el contenido del mensaje y se responde según el caso.

        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println("Hola " + nickName + " Tu nombre es: " + nombre + " y tu apellido es: " + apellido);
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            //manejo de mensajes

            if (message_text.toLowerCase().equals("hola")) {
                sendText(chat_id, "👋  Hola " + nombre + " gusto de saludarte  ✅");
            }

            //Para cuando el usuario inicie el bot

            if (message_text.toLowerCase().equals("/start")) {
                String mensaje = "Hola " + nombre + " 👋,  ¿Qué deseas hacer hoy?  ✅\n\n";
                mensaje += "/info\n";
                mensaje += "/progra\n";
                mensaje += "/hola\n";
                mensaje += "/cambio (cantidad en euros)\n";
                sendText(chat_id, mensaje);
            }

            //Para el caso del hola

            if (message_text.toLowerCase().equals("/hola")) {
                // Obtener la fecha y hora actuales

                LocalDateTime fechaHoraActual = LocalDateTime.now();

                // Formatear solo la fecha

                DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String fechaFormateada = fechaHoraActual.format(formatoFecha);

                // Formatear solo la hora

                DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
                String horaFormateada = fechaHoraActual.format(formatoHora);
                String mensaje = "Hola " + nombre +" hoy es " + fechaFormateada + "  🗓️  a las " + horaFormateada + "  🕜";
                sendText(chat_id, mensaje);
            }

            //El comentario del comando progra

            if(message_text.toLowerCase().equals("/progra")) {
                String mensaje = "El curso de programación es una clase muy interesante, me parece increíble la ";
                mensaje += "manera en la que hay muchas formas para resolver un mismo problema, como también la ";
                mensaje += "capacidad de los dispositivos para realizar de forma lógica y correcta las instrucciones ";
                mensaje += "que el programador les da. Siento que puedo aprender mucho de este curso para convertirme ";
                mensaje += "en un gran profesional, con ayuda del ingeniero y mis compañeros. ☝️🤓";
                sendText(chat_id, mensaje);
            }

            //Informacion personal

            if(message_text.toLowerCase().equals("/info")) {
                String mensaje = "Nombre: Marvin Cámbara\n";
                mensaje += "Carnet: 0905-23-17848\n";
                mensaje += "Semestre: Cuarto\n";
                mensaje += "Sección: A";
                sendText(chat_id, mensaje);
            }

            //Comando para el cambio

            if(message_text.toLowerCase().startsWith("/cambio")) {
                //Aca extraigo solo la cantidad
                String[] cantidad = message_text.split(" ");
                if(cantidad.length == 2) {
                    try{

                        //Separo el /cambio y el numero

                        double dinero = Double.parseDouble(cantidad[1]);


                        //Uso mi funcion para convertir

                        double x = cambio(dinero);

                        //Ajusto a dos decimales

                        DecimalFormat formato = new DecimalFormat("#.00");
                        String DineroFormateado = formato.format(dinero);
                        String XFormateado = formato.format(x);

                        //Hago el mensaje

                        String mensaje = "La cantidad ingresada fue €" + DineroFormateado + "\n";
                        mensaje += "En valor en Quetzales es de: Q" + XFormateado;

                        //Mando la conversion al usuario

                        sendText(chat_id, mensaje);

                    }catch (NumberFormatException e){
                        // Manejo de error en caso de que el parámetro no sea un número válido
                        sendText(chat_id, "Error: La cantidad ingresada no es válida. Por favor, ingresa un número.");
                    }
                } else {
                sendText(chat_id, "Uso incorrecto del comando. Debes ingresar la cantidad después de /cambio.");
            }
            }

            System.out.println("User id: " + chat_id + " Message: " + message_text);

        }
    }


    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .text(what).build();    //Message content
        try {
            execute(sm);                        //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }
}