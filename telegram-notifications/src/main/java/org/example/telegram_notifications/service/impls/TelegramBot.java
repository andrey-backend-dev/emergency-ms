package org.example.telegram_notifications.service.impls;

import org.example.telegram_notifications.config.TelegramBotConfig;
import lombok.RequiredArgsConstructor;
import org.example.telegram_notifications.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramBotConfig config;
    private final TelegramUserService service;
    @Value("${bot.msg.accept}")
    private String acceptMsg;
    @Value("${bot.msg.deny}")
    private String denyMsg;
    @Value("${bot.msg.start}")
    private String startMsg;

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            Message msg = update.getMessage();

            switch (msg.getText()) {
                case "/start" -> startCommandReceived(msg.getChatId(), msg.getChat().getUserName());
                case "/join" -> joinCommandReceived(msg.getChatId());
                case "/leave" -> leaveCommandReceived(msg.getChatId());
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackData = update.getCallbackQuery();

            switch (callbackData.getData()) {
                case "ENS-ACCEPT" -> joinCommandReceived(callbackData.getMessage().getChatId());
                case "ENS-DENY" -> sendMessage(callbackData.getMessage().getChatId(), denyMsg);
            }
        }

    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    private void setCommands() {
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand("/start", "Запросить стартовое сообщение."));
        commands.add(new BotCommand("/join", "Стать участником ENS."));
        commands.add(new BotCommand("/leave", "Перестать быть участником ENS."));
        try {
            this.execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void startCommandReceived(Long chatId, String username) {

        service.create(chatId, username);

        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), startMsg);

        InlineKeyboardMarkup inlineMarkup = getStartInlineKeyboardMarkup();

        sendMessage.setReplyMarkup(inlineMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void joinCommandReceived(Long chatId) {

        service.changeStatus(chatId, true);
        sendMessage(chatId, acceptMsg);
    }

    private void leaveCommandReceived(Long chatId) {

        service.changeStatus(chatId, false);
        sendMessage(chatId, denyMsg);
    }

    private InlineKeyboardMarkup getStartInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton keyboardButton1 = new InlineKeyboardButton();
        keyboardButton1.setText("Хочу быть участником ENS!");
        keyboardButton1.setCallbackData("ENS-ACCEPT");
        row1.add(keyboardButton1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton keyboardButton2 = new InlineKeyboardButton();
        keyboardButton2.setText("Я просто зашел посмотреть..");
        keyboardButton2.setCallbackData("ENS-DENY");
        row2.add(keyboardButton2);

        rows.add(row1);
        rows.add(row2);

        inlineMarkup.setKeyboard(rows);
        return inlineMarkup;
    }
}
