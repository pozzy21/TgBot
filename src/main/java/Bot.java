import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {



    public void sendMsg(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try{
                setButton(sendMessage);
                sendMessage(sendMessage);

        }catch(TelegramApiException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
       /* if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText(message_text);
            try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }*/
       Model model = new Model();
        long chat_id = update.getMessage().getChatId();
        Message message = update.getMessage();
            if(message!= null && message.hasText()){
                switch (message.getText()){
                    case "/button": {
                        SendPhoto msg = new SendPhoto()
                                .setChatId(chat_id)
                                .setPhoto("AgADAgAEqjEbSZ_ZSDhZTtVYkhO31z45DwAEyFEieTk_D-zBvQACAg")
                                .setCaption("Вот тебе расписание 28-го басика, братишка");
                        try {
                            sendPhoto(msg); // Call method to send the photo
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    }


                    case "/help":
                        sendMsg(message, "Пока что, я ничего не умею кроме того как показывать погоду и расписание 28 автобуса" + "\n" +
                                "Для того чтобы узнать погоду достаточно просто ввести название требуемого города");
                        break;

                    default:
                        try {
                            sendMsg(message, Weather.getWeather(message.getText(), model));
                        } catch (IOException e) {
                            sendMsg(message, "Город не найден");
                        }

            }
        }

    }

    public void setButton(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);


        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/button"));

        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);



    }



    @Override
    public String getBotUsername() {
        return "WowItsBot";
    }

    @Override
    public String getBotToken() {
        return "764826776:AAG4K5u-o35WzxJrDGYUrnb3YY9xLL4sJSs";
    }
}