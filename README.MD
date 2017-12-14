# Viber4j

Java library for working with Viber REST API.

## Requirements
* Java 8

## Creating Viber Bot

1) For creating Viber bot you need registration an account in Viber and get auth token.
2) Also, you need an endpoint URL with HTTPS support. Don't use a self-signed certificate.
You can use LetsEncrypt certificates.

## Examples

Registration a web-hook (for callback messages from Viber)
```
String webHookUrl = "url for web-hook";
String botToken = "Viber auth token";
ViberBotManager.viberBot(botToken).setWebHook(webHookUrl);
```

Removing a web-hook
```
String botToken = "Viber auth token";
ViberBotManager.viberBot(botToken).removeWebHook();
```
Sending a text to a Viber client:
```
String botToken = "Viber auth token";
ViberBot bot  = ViberBotManager.viberBot(botToken);
String userId = "Viber client ID";
bot.messageForUser(userId).postText("Text");
```
Sending an image to a Viber client:
```
String botToken = "Viber auth token";
ViberBot bot  = ViberBotManager.viberBot(botToken);
String userId = "Viber client ID";
bot.messageForUser(userId).postPicture("Picture Url", "Description");
```
Sending a keyboard to a client:
```
String botToken = "Viber auth token";

ViberBot bot  = ViberBotManager.viberBot(botToken);

ViberKeyboard keyboard = new ViberKeyboard().
    addButton(
        new ViberButton("1").setActionType("reply").
        setText("Button1 cupture").setBgColor("#D26464")).
    addButton(
        new ViberButton("2").setActionType("reply").
        setText("Button2 cupture").setTextSize(ViberButton.TextSize.large)).
    addButton(
        ViberButton.createButtonForUrl("https://google.ru").
        setImage("http://host/your/image"));
        
bot.messageForUser(userId).postText("Please, select one", keyboard);
``` 
Posting public message:
```
String botToken = "Viber auth token";

ViberBot bot  = ViberBotManager.viberBot(botToken);

bot.publicMessage("UserIs").postText("Hello all");
```
## License

Distributed under the Apache 2.0 license