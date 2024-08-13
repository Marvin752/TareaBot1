package umg.programacion2;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import umg.programacion2.BotTelegram.TareaBot;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            TareaBot Mash = new TareaBot();
            botsApi.registerBot(Mash);
            System.out.println("El bot esta funcionando");
        }catch (TelegramApiException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}