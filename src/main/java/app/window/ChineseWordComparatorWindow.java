package app.window;

import app.comparator.ChineseComparator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ChineseWordComparatorWindow {

    private static final float FONT_SIZE = 14f;

    @Getter
    private JFrame jFrame;

    private final JTabbedPane tabbedPane = new JTabbedPane();

    private JTextArea inputTextArea;

    private JTextArea outputTextArea;

    private JButton button;

    public ChineseWordComparatorWindow() {
        this.setupJFrame();
        this.createTab1();

        this.setFont(tabbedPane);

        final Container container = this.jFrame.getContentPane();
        container.setLayout(new FlowLayout(FlowLayout.LEFT));
        container.add(tabbedPane);

        this.jFrame.pack();
    }

    private void setupJFrame() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        // 獲取螢幕解析度
        this.jFrame = new JFrame("ChineseWordComparatorWindow");
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 設定視窗大小佔螢幕四分之一
        this.jFrame.setSize(dimension.width / 2, dimension.height / 2);
        this.jFrame.setResizable(false);

        final int x = (int) ((dimension.getWidth() - this.jFrame.getWidth()) / 2);
        final int y = (int) ((dimension.getHeight() - this.jFrame.getHeight()) / 2);
        this.jFrame.setLocation(x, y);
    }

    private void createTab1() {
        final List<JPanel> panels = new ArrayList<JPanel>();
        this.createComponent(panels);

        final JPanel jp = new JPanel(new GridLayout(panels.size(), 1));
        for (final JPanel panel : panels) {
            jp.add(panel);
        }

        final JScrollPane jScrollPane = new JScrollPane(jp);
        tabbedPane.add("sort", jScrollPane);
    }

    private void createComponent(final List<JPanel> panels) {
        final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        final JLabel label = new JLabel("text：");
        this.setFont(label);
        panel.add(label);

        inputTextArea = new JTextArea();
        inputTextArea.setRows(20);
        inputTextArea.setColumns(40);
        inputTextArea.setLineWrap(true);
        this.setFont(inputTextArea);

        final JScrollPane jScrollPane = new JScrollPane(inputTextArea);
        panel.add(jScrollPane);

        this.createExecuteButton();
        panel.add(this.button);

        outputTextArea = new JTextArea();
        outputTextArea.setRows(20);
        outputTextArea.setColumns(40);
        outputTextArea.setLineWrap(true);
        outputTextArea.setEditable(false);
        this.setFont(outputTextArea);
        final JScrollPane jScrollPane2 = new JScrollPane(outputTextArea);
        panel.add(jScrollPane2);

        panels.add(panel);
    }

    private void createExecuteButton() {
        this.button = new JButton("排序");
        this.setFont(this.button);
        this.button.addActionListener(event -> {
            try {
                final String text = this.inputTextArea.getText();
                final List<String> list = Arrays.asList(StringUtils.split(text, "\t\r\n"));
                final ChineseComparator comparator = ChineseComparator.asc();
                list.sort(comparator);
                final String result = String.join("\n", list);
                this.outputTextArea.setText(result);

                log.info("done");
                JOptionPane.showMessageDialog(ChineseWordComparatorWindow.this.jFrame, "處理成功");
            } catch (final Exception e) {
                JOptionPane.showMessageDialog(ChineseWordComparatorWindow.this.jFrame, e.getMessage());
            }
        });
        this.addEnterKeyListener(this.button);
    }

    private void addEnterKeyListener(final JButton button) {
        button.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    button.doClick();
                }
            }
        });
    }

    private void setFont(final Component c) {
        c.setFont(c.getFont().deriveFont(FONT_SIZE));
    }
}
