
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;

public class Main extends JFrame {

    
    private JTextField passwordField, generatedField;
    private JTextArea resultArea;
    private JProgressBar strengthBar;
    private JLabel strengthLabel;
    private boolean darkMode = true;

    private static final Color DARK_BG = new Color(17,24,39);
    private static final Color DARK_CARD = new Color(31,41,55);
    private static final Color ACCENT = new Color(0,255,170);

    public Main() {
        setTitle("SecurePass Pro");
        setSize(900,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout(15,15));
        main.setBorder(new EmptyBorder(15,15,15,15));

        JPanel header = new JPanel(new BorderLayout());
        JLabel title = new JLabel("🔒 SECUREPASS PRO", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        JLabel sub = new JLabel("Cyber Security Password Analyzer", SwingConstants.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        header.add(title, BorderLayout.CENTER);
        header.add(sub, BorderLayout.SOUTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        passwordField = new JTextField(25);
        generatedField = new JTextField(25);
        generatedField.setEditable(false);

        JButton analyzeBtn = createButton("Analyze Password");
        JButton generateBtn = createButton("Generate Password");
        JButton themeBtn = createButton("Switch Theme");

        JPanel passPanel = cardPanel();
        passPanel.add(new JLabel("Password:"));
        passPanel.add(passwordField);
        passPanel.add(analyzeBtn);

        strengthLabel = new JLabel("Strength: -");
        strengthBar = new JProgressBar(0,100);
        strengthBar.setStringPainted(true);

        JPanel meter = cardPanel();
        meter.setLayout(new BorderLayout(10,10));
        meter.add(strengthLabel, BorderLayout.NORTH);
        meter.add(strengthBar, BorderLayout.CENTER);

        resultArea = new JTextArea(12,50);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 14));

        JPanel report = cardPanel();
        report.setLayout(new BorderLayout());
        report.add(new JScrollPane(resultArea));

        JPanel generator = cardPanel();
        generator.add(generatedField);
        generator.add(generateBtn);

        JPanel bottom = new JPanel();
        bottom.add(themeBtn);

        center.add(passPanel);
        center.add(Box.createVerticalStrut(10));
        center.add(meter);
        center.add(Box.createVerticalStrut(10));
        center.add(report);
        center.add(Box.createVerticalStrut(10));
        center.add(generator);

        main.add(header, BorderLayout.NORTH);
        main.add(center, BorderLayout.CENTER);
        main.add(bottom, BorderLayout.SOUTH);
        add(main);

        analyzeBtn.addActionListener(e -> analyze());
        generateBtn.addActionListener(e -> generatedField.setText(new PasswordGenerator().generatePassword(12)));
        themeBtn.addActionListener(e -> {
            darkMode = !darkMode;
            applyTheme(main);
        });

        applyTheme(main);
    }

    private JPanel cardPanel() {
        JPanel p = new JPanel(new FlowLayout());
        p.setBorder(new CompoundBorder(new LineBorder(ACCENT,1), new EmptyBorder(10,10,10,10)));
        return p;
    }

    private JButton createButton(String text){
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        return b;
    }

    private void analyze() {
        String pwd = passwordField.getText().trim();
        if(pwd.isEmpty()){
            JOptionPane.showMessageDialog(this,"Enter a password");
            return;
        }

        SecurityReport report = new PasswordAnalyzer().analyze(pwd);

        strengthBar.setValue(report.getScore());
        strengthBar.setString(report.getScore()+"%");

        if(report.getScore() < 40){
            strengthBar.setForeground(Color.RED);
        }else if(report.getScore() < 80){
            strengthBar.setForeground(Color.ORANGE);
        }else{
            strengthBar.setForeground(Color.GREEN);
        }

        strengthLabel.setText("Strength: " + report.getStrength());
        resultArea.setText(report.toString());
    }

    private void applyTheme(Container c){
        Color bg = darkMode ? DARK_BG : Color.WHITE;
        Color fg = darkMode ? Color.WHITE : Color.BLACK;
        apply(c,bg,fg);
        repaint();
    }

    private void apply(Container c, Color bg, Color fg){
        c.setBackground(bg);
        c.setForeground(fg);
        for(Component comp : c.getComponents()){
            comp.setBackground(comp instanceof JTextField || comp instanceof JTextArea ? (darkMode?DARK_CARD:Color.WHITE):bg);
            comp.setForeground(fg);
            if(comp instanceof Container) apply((Container)comp,bg,fg);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}

class Password {
    private final String value;
    public Password(String value){ this.value = value; }
    public String getValue(){ return value; }
}

class SecurityReport {
    private final int score;
    private final String strength;
    private final String details;

    public SecurityReport(int score, String strength, String details){
        this.score = score;
        this.strength = strength;
        this.details = details;
    }

    public int getScore(){ return score; }
    public String getStrength(){ return strength; }

    public String toString(){
        return "Score: " + score + "/100\n\nStrength: " + strength + "\n\n" + details;
    }
}

class SecurityTool {
    public String getToolName(){ return "Security Tool"; }
}

class PasswordAnalyzer extends SecurityTool {

    private static final Set<String> COMMON = new HashSet<>(Arrays.asList(
            "password","123456","admin","qwerty","welcome"
    ));

    public SecurityReport analyze(String text){

        Password p = new Password(text);
        int score = 0;
        StringBuilder sb = new StringBuilder();

        if(p.getValue().length() >= 8) score += 20;
        else sb.append("• Use at least 8 characters\n");

        if(p.getValue().matches(".*[A-Z].*")) score += 20;
        else sb.append("• Add uppercase letters\n");

        if(p.getValue().matches(".*[a-z].*")) score += 20;
        else sb.append("• Add lowercase letters\n");

        if(p.getValue().matches(".*\\d.*")) score += 20;
        else sb.append("• Add numbers\n");

        if(p.getValue().matches(".*[^a-zA-Z0-9].*")) score += 20;
        else sb.append("• Add special symbols\n");

        if(COMMON.contains(p.getValue().toLowerCase())){
            score = Math.max(0, score - 40);
            sb.append("• Common password detected\n");
        }

        String strength = score < 40 ? "WEAK" : score < 80 ? "MEDIUM" : "STRONG";

        if(sb.length()==0) sb.append("Excellent password. No recommendations.");

        return new SecurityReport(score, strength, sb.toString());
    }
}

class PasswordGenerator {

    private static final String CHARS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";

    public String generatePassword(int length){
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<length;i++){
            sb.append(CHARS.charAt(r.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}
