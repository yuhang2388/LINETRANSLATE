// import demo.src.main.java.processLineMessages;
// import demo.src.main.java.messageInfo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class line extends Application {
    private boolean isStageSizeAdjusted = false;
    private boolean isLoggedIn = false; // 新增一个标志，表示是否已经登录

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("全屏Web应用");

        // 创建WebView和WebEngine
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // 设置 WebView 的用户代理标识
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36";
        webEngine.setUserAgent(userAgent);

        // 设置舞台初始大小为400x400像素
        primaryStage.setWidth(400);
        primaryStage.setHeight(400);

        // 在WebEngine中加载指定的网页链接
        String url = "https://chat.line.biz";
        webEngine.load(url);

        // 创建场景
        Scene scene = new Scene(webView, primaryStage.getWidth(), primaryStage.getHeight());

        // 监听舞台大小变化，以便动态调整WebView大小
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (!isStageSizeAdjusted) {
                webView.setPrefWidth(newValue.doubleValue());
            }
        });

        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (!isStageSizeAdjusted) {
                webView.setPrefHeight(newValue.doubleValue());
            }
        });

        // 监听 WebView 页面加载完成事件
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == javafx.concurrent.Worker.State.SUCCEEDED) {
                if (!isStageSizeAdjusted) {
                    // 调整舞台大小
        
                    // 在这里添加检查是否已经登录的逻辑
                    if (isLoggedIn) {
                        // 在这里添加导航到新页面的逻辑
                        String newURL = "https://manager.line.biz/";
                        webEngine.load(newURL);
                    }
                }
        
                isStageSizeAdjusted = true; // 设置标志，表示已经调整过大小
            }
        });

        // 设置舞台
        primaryStage.setScene(scene);
        primaryStage.show();

        // // 创建 MessageInfo 对象并设置必要信息
        // messageInfo messageInfo = new messageInfo("你发送的文本", "回复令牌");

        // // 实例化 processLineMessages 类
        // processLineMessages messageHandler = new processLineMessages();

        // // 调用 processLineMessages 中的方法，传递 MessageInfo 对象
        // messageHandler.handleTextMessageEvent(messageInfo);
    }
}