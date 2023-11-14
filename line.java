import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.concurrent.Worker.State;

public class line extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX with Line Bot");

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // 设置 WebView 的用户代理标识
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36";
        webEngine.setUserAgent(userAgent);

        // 监听 WebView 加载状态
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            if (newState == State.SUCCEEDED) {
                checkWebSockets(webEngine);
            }
        });

        // 设置舞台初始大小
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);

        // 在 WebEngine 中加载 Line Bot 的聊天窗口网页
        String url = "https://chat.line.biz"; // 替换为 Line Bot 聊天窗口的 URL
        webEngine.load(url);

        // 创建场景
        Scene scene = new Scene(webView, primaryStage.getWidth(), primaryStage.getHeight());

        // 监听舞台大小变化，以便动态调整 WebView 大小
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> webView.setPrefWidth(newValue.doubleValue()));
        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> webView.setPrefHeight(newValue.doubleValue()));

        // 设置舞台
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void checkWebSockets(WebEngine webEngine) {
        webEngine.executeScript(
            "var sockets = [];" +
            "if (window.WebSocket) {" +
            "    var open = WebSocket.prototype.send;" +
            "    WebSocket.prototype.send = function(data) {" +
            "        sockets.push(this.url);" +
            "        return open.apply(this, arguments);" +
            "    };" +
            "    var originalOnMessage = WebSocket.prototype.onmessage;" +
            "    WebSocket.prototype.onmessage = function(event) {" +
            "        // Call the original onmessage function" +
            "        originalOnMessage.call(this, event);" +
            "        // Add your custom logic to handle the received message" +
            "        var message = event.data;" +
            "        // You can process the 'message' variable or trigger actions as needed" +
            "    };" +
            "}" +
            "sockets;"
        );
    
        Object result = webEngine.executeScript("sockets;");
        
        if (result instanceof String[]) {
            String[] webSocketURLs = (String[]) result;
    
            if (webSocketURLs.length == 0) {
                System.out.println("No WebSocket connections found on the page.");
            } else {
                System.out.println("WebSocket connections found on the page:");
                for (String url : webSocketURLs) {
                    System.out.println(url);
                }
            }
        }
    }
}
