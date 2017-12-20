package ru.multicon.viberbot;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.multicon.viber4j.ViberBot;
import ru.multicon.viber4j.ViberBotManager;
import ru.multicon.viber4j.account.UserDetails;
import ru.multicon.viber4j.incoming.Incoming;
import ru.multicon.viber4j.incoming.IncomingImpl;
import ru.multicon.viber4j.keyboard.BtnActionType;
import ru.multicon.viber4j.keyboard.RichMedia;
import ru.multicon.viber4j.keyboard.ViberButton;
import ru.multicon.viber4j.keyboard.ViberKeyboard;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@SpringBootApplication
@RestController
public class Application implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    @Value("${viber.token}")
    private String botToken;
    @Value("${viber.web-hook}")
    private String webHookUrl;
    @Value("${viber.media-source-url}")
    private String mediaSourceUrl;

    private final String MESSAGE_EVENT = "message";
    private final String START_MSG_EVENT = "conversation_started";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(method = POST, path = "/viberbot")
    ResponseEntity<?> callbackHandle(@RequestBody String text) {
        logger.info("Received messageForUser {}", text);
        // Processing incoming messageForUser
        Incoming incoming = IncomingImpl.fromString(text);
        String eventType = incoming.getEvent();
        logger.info("Event type {}", eventType);
        if (!StringUtils.equals(eventType, MESSAGE_EVENT)
                && !StringUtils.equals(incoming.getEvent(), START_MSG_EVENT))
            return ResponseEntity.ok().build();

        String userName = incoming.getSenderName();
        String userAvatar = incoming.getSenderAvatar();
        String userId = incoming.getSenderId();
        String messageText = incoming.getMessageText();
        logger.info("Message text: {}", messageText);
        //
        // Viber bot instance
        //
        ViberBot bot = ViberBotManager.viberBot(botToken);

        //
        // getting info about user
        //
        UserDetails userDet = bot.getUserDetails(userId);
        logger.info("User country: {}", userDet.getCountry());
        logger.info("User device: {}", userDet.getDeviceType());

        // Sending answer example

        if (StringUtils.equals("1", messageText)) {
            //
            // Sending a text example
            //
            bot.messageForUser(userId).postPicture(userAvatar, "This is your avatar");
        } else if (StringUtils.equals("2", messageText)) {
            //
            // Sending an image example
            //
            bot.messageForUser(userId).postText("Your Id: " + userId);
        } else if (StringUtils.equals("3", messageText)) {
            //
            // Carousel Example
            //
            ViberButton.InternalBrowser ib = new ViberButton.InternalBrowser();
            ib.setTitleType("Yandex search");
            ib.setMode("fullscreen");
            RichMedia richMedia = new RichMedia("Carousel example");
            richMedia.setButtonsGroupColumns(6).setButtonsGroupRows(5).
                    addButton(
                            new ViberButton("1").setActionType(BtnActionType.NONE).
                                    setImage(mediaSourceUrl + "duck.png").
                                    setColumns(3).setRows(3)).
                    addButton(
                            new ViberButton("2").setActionType(BtnActionType.NONE).
                                    setImage(mediaSourceUrl + "google.png").
                                    setColumns(3).setRows(3)).
                    addButton(
                            new ViberButton("2").setText("DuckDuckGo").
                                    setActionType(BtnActionType.NONE).
                                    setColumns(3).setRows(1)).
                    addButton(
                            new ViberButton("2").setText("Google").
                                    setActionType(BtnActionType.NONE).
                                    setColumns(3).setRows(1)).
                    addButton(
                            ViberButton.createButtonForUrl("https://duckduckgo.com").setText("Search").
                                    setColumns(3).setRows(1).setBgColor("#E2211D")).
                    addButton(
                            ViberButton.createButtonForUrl("https://google.com").setText("Search").
                                    setColumns(3).setRows(1).setBgColor("#E2211D")
                    );

            bot.messageForUser(userId).postCarousel(richMedia);
        } else {
            ViberKeyboard keyboard = createStartKeyboard();
            bot.messageForUser(userId).postText("Hello " + userName, keyboard);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Creates simple Viber keyboard
     * @return created keyboard object
     */
    private ViberKeyboard createStartKeyboard() {
        return (ViberKeyboard) new ViberKeyboard().
                addButton(
                        new ViberButton("1").
                                setText("My avatar").setTextSize(ViberButton.TextSize.LARGE)).
                addButton(
                        new ViberButton("2").
                                setText("My Viber Id").setBgColor("#00FF00")).
                addButton(
                        new ViberButton("3").
                                setText("Carousel example").setBgColor("#00BFFF"));
    }

    /**
     * Method returns image by an URL
     *
     * @param imgName
     * @return
     */
    @RequestMapping(method = GET, path = "/images/{img}/**")
    public byte[] getImages(@PathVariable("img") String imgName) {
        InputStream in = getClass()
                .getResourceAsStream("/images/" + imgName);
        try {
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        logger.info("Web-hook registration for {}", webHookUrl);
        if (!ViberBotManager.viberBot(botToken).setWebHook(webHookUrl))
            logger.error("Web-hook registration failed!");
    }
}
