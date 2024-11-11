package app;

import app.window.ChineseWordComparatorWindow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class ChineseWordComparatorApplication {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");

        final ConfigurableApplicationContext context = SpringApplication.run(ChineseWordComparatorApplication.class, args);

        EventQueue.invokeLater(() -> {
            final ChineseWordComparatorWindow window = new ChineseWordComparatorWindow();
            window.getJFrame().setVisible(true);
        });
    }
}
