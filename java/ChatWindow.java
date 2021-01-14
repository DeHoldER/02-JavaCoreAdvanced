import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ChatWindow extends JFrame implements ChatApiHandler.Callback {


    interface Callback {
        void sendMessage(String text);
    }

    // Настройки
    private static final Dimension DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 650;

    private static final int SCREEN_CENTER_X = (DIMENSION.width / 2) - WINDOW_WIDTH / 2;
    private static final int SCREEN_CENTER_Y = (DIMENSION.height / 2) - WINDOW_HEIGHT / 2;

    private static final Color TEXT_BLOCK_COLOR = new Color(235, 255, 235);

    // ELEMENTS
    private static final JTextPane messageList = new JTextPane();
    private static final JTextPane messageTextField = new JTextPane();
    private static final JScrollPane jScrollPaneTop = new JScrollPane(messageList);
    private static final JScrollPane jScrollPaneBottom = new JScrollPane(messageTextField);
    private static final JPanel panelTop = new JPanel(new GridLayout(1, 1));
    private static final JPanel panelBottom = new JPanel(new BorderLayout());
    private static final JButton sendButton = new JButton("Отправить");

    private static final ArrayList<String> textArrayList = new ArrayList<>();


    private static String userName = "Вы";

    private static int messageCount = 0;

    public ChatWindow(Callback callback) {
        setTitle("Чат");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(SCREEN_CENTER_X, SCREEN_CENTER_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(null);

        setResizable(false);

        // Подгонка элементов при ресайзе окна
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                Component c = (Component) evt.getSource();
                resize();
            }
        });

        messageList.setBackground(TEXT_BLOCK_COLOR);
        messageTextField.setBackground(Color.WHITE);
        messageList.setEditable(false);
        panelTop.add(jScrollPaneTop);
        panelBottom.add(jScrollPaneBottom);
        add(sendButton);


        JMenuBar mainMenu = new JMenuBar();
        JMenu mFile = new JMenu("File");
        JMenuItem miFileOptions = new JMenuItem("Options");
        JMenuItem miFileExit = new JMenuItem("Exit");
        setJMenuBar(mainMenu);
        mainMenu.add(mFile);
        mFile.add(miFileOptions);
        mFile.addSeparator();
        mFile.add(miFileExit);

        miFileExit.addActionListener(e -> System.exit(0));
        miFileOptions.addActionListener(e -> new OptionsWindow());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            }
        });

        add(panelBottom);
        add(panelTop);

        sendButton.addActionListener(e -> callback.sendMessage(messageTextField.getText()));

        messageTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    callback.sendMessage(messageTextField.getText());
                    e.consume();
                }
            }
        });


        messageTextField.requestFocus();
    }

    @Override
    public synchronized void addNewMessage(String text) {
        textArrayList.add(text);
        messageList.setText(messageList.getText() + "\n" + textArrayList.get(messageCount) + "\n");
        messageCount++;
        messageList.setCaretPosition(messageList.getDocument().getLength());
    }

    public void msgSend(String text) {
        textArrayList.add(userName + ": " + text);
        messageList.setText(messageList.getText() + "\n" + textArrayList.get(messageCount) + "\n");
        messageCount++;
        messageTextField.setText(null);
        messageTextField.requestFocus();
        messageTextField.resetKeyboardActions();
    }

    public void resize() {
        panelTop.setBounds(5, 5, ChatWindow.super.getWidth() - 25, ChatWindow.super.getHeight() - 130);
        panelBottom.setBounds(5, panelTop.getY() + panelTop.getHeight() + 5, ChatWindow.super.getWidth() - 130, ChatWindow.super.getHeight() - panelTop.getHeight() - 75);
        sendButton.setBounds(panelBottom.getWidth() + 10, panelBottom.getY(), 100, 55);
        JScrollBar vertical = jScrollPaneTop.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());

    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String name) {
        userName = name;
    }

    public void txtFieldSetFocus() {
        messageTextField.requestFocus();
    }

}