import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main extends JFrame {

    private JTextField passwordField;
    private JTextField generatedPasswordField;
    private JTextArea resultArea;
    private JProgressBar strengthBar;
    private JLabel strengthLabel;
    private JButton themeButton;

    private boolean darkMode = true;

    public Main() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("SecurePass Pro - Password Security Analyzer");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JLabel title = new JLabel("SECUREPASS PRO", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JPanel passwordPanel = new JPanel(new FlowLayout());

        JLabel passwordLabel = new JLabel("Enter Password:");
        passwordField = new JTextField(20);

        JButton analyzeButton = new JButton("Analyze");

        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        passwordPanel.add(analyzeButton);

        JPanel meterPanel = new JPanel(new BorderLayout());

        strengthLabel = new JLabel("Strength: ");
        strengthBar = new JProgressBar(0, 100);
        strengthBar.setStringPainted(true);

        meterPanel.add(strengthLabel, BorderLayout.NORTH);
        meterPanel.add(strengthBar, BorderLayout.CENTER);

        resultArea = new JTextArea(12, 40);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        JPanel generatorPanel = new JPanel(new FlowLayout());

        generatedPasswordField = new JTextField(20);
        generatedPasswordField.setEditable(false);

        JButton generateButton = new JButton("Generate Password");

        generatorPanel.add(generatedPasswordField);
        generatorPanel.add(generateButton);

        themeButton = new JButton("Switch Theme");

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(themeButton);

        centerPanel.add(passwordPanel);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(meterPanel);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(scrollPane);
        centerPanel.add(Box.createVerticalStrut(15));
        centerPanel.add(generatorPanel);

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        analyzeButton.addActionListener(e -> analyzePassword());

        generateButton.addActionListener(e -> {
            PasswordGenerator generator = new PasswordGenerator();
            generatedPasswordField.setText(generator.generatePassword(12));
        });

        themeButton.addActionListener(e -> toggleTheme());

        applyDarkTheme(mainPanel);
    }

    private void analyzePassword() {

        String password = passwordField.getText();

        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a password.");
            return;
        }

        PasswordAnalyzer analyzer = new PasswordAnalyzer();
        SecurityReport report = analyzer.analyze(password);

        strengthBar.setValue(report.getScore());
        strengthLabel.setText("Strength: " + report.getStrength());

        resultArea.setText(report.toString());
    }

    private void toggleTheme() {

        Container content = getContentPane();

        if (darkMode) {
            applyLightTheme(content);
        } else {
            applyDarkTheme(content);
        }

        darkMode = !darkMode;
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void applyDarkTheme(Container container) {
        setColors(container,
                new Color(30, 30, 30),
                Color.WHITE);
    }

    private void applyLightTheme(Container container) {
        setColors(container,
                Color.WHITE,
                Color.BLACK);
    }

    private void setColors(Container container,
                           Color background,
                           Color foreground) {

        container.setBackground(background);
        container.setForeground(foreground);

        for (Component component : container.getComponents()) {

            component.setBackground(background);
            component.setForeground(foreground);

            if (component instanceof Container) {
                setColors((Container) component,
                        background,
                        foreground);
            }
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}

class Password {

    private final String value;

    public Password(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

class SecurityReport {

    private final int score;
    private final String strength;
    private final String details;

    public SecurityReport(int score,
                          String strength,
                          String details) {
        this.score = score;
        this.strength = strength;
        this.details = details;
    }

    public int getScore() {
        return score;
    }

    public String getStrength() {
        return strength;
    }

    @Override
    public String toString() {

        return "Security Score: "
                + score
                + "/100\n\n"
                + "Strength Level: "
                + strength
                + "\n\n"
                + details;
    }
}

class SecurityTool {

    public String getToolName() {
        return "Security Tool";
    }
}

class PasswordAnalyzer extends SecurityTool {

    private static final Set<String> COMMON_PASSWORDS =
            new HashSet<>();

    static {

        COMMON_PASSWORDS.add("password");
        COMMON_PASSWORDS.add("123456");
        COMMON_PASSWORDS.add("admin");
        COMMON_PASSWORDS.add("qwerty");
        COMMON_PASSWORDS.add("welcome");
    }

    public SecurityReport analyze(String passwordText) {

        Password password = new Password(passwordText);

        int score = 0;

        StringBuilder recommendations =
                new StringBuilder();

        if (password.getValue().length() >= 8) {
            score += 20;
        } else {
            recommendations.append(
                    "• Use at least 8 characters\n");
        }

        if (containsUppercase(password.getValue())) {
            score += 20;
        } else {
            recommendations.append(
                    "• Add uppercase letters\n");
        }

        if (containsLowercase(password.getValue())) {
            score += 20;
        } else {
            recommendations.append(
                    "• Add lowercase letters\n");
        }

        if (containsDigit(password.getValue())) {
            score += 20;
        } else {
            recommendations.append(
                    "• Add numbers\n");
        }

        if (containsSpecialCharacter(password.getValue())) {
            score += 20;
        } else {
            recommendations.append(
                    "• Add special symbols\n");
        }

        if (COMMON_PASSWORDS.contains(
                password.getValue().toLowerCase())) {

            score = Math.max(score - 40, 0);

            recommendations.append(
                    "\n⚠ Common Password Detected\n");
        }

        String strength;

        if (score < 40) {
            strength = "WEAK";
        } else if (score < 80) {
            strength = "MEDIUM";
        } else {
            strength = "STRONG";
        }

        if (recommendations.length() == 0) {
            recommendations.append(
                    "Excellent Password!\nNo recommendations.");
        }

        return new SecurityReport(
                score,
                strength,
                recommendations.toString());
    }

    private boolean containsUppercase(String text) {

        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }

        return false;
    }

    private boolean containsLowercase(String text) {

        for (char c : text.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }

        return false;
    }

    private boolean containsDigit(String text) {

        for (char c : text.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }

        return false;
    }

    private boolean containsSpecialCharacter(String text) {

        for (char c : text.toCharArray()) {

            if (!Character.isLetterOrDigit(c)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String getToolName() {
        return "Password Analyzer";
    }
}

class PasswordGenerator {

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "abcdefghijklmnopqrstuvwxyz"
                    + "0123456789"
                    + "!@#$%^&*()";

    private final Random random = new Random();

    public String generatePassword(int length) {

        StringBuilder password =
                new StringBuilder();

        for (int i = 0; i < length; i++) {

            password.append(
                    CHARACTERS.charAt(
                            random.nextInt(
                                    CHARACTERS.length())));
        }

        return password.toString();
    }
}