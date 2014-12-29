package bingbot;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class MainFrame extends JFrame {
    private boolean initialized = false;
    private final Actions actions = new Actions();
    
    private JTextField searches;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField usersField;
    
    private JButton submit;
    private JButton newUser;
    
    private JLabel label;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel noUsersLabel;
    
    private ActionEvent searchEvent;
    private ActionEvent newUserEvent;
    
    private JPanel userPanel;
    
    public void initialize() {
        initializeGui();
        initializeEvents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void initializeGui() {
        if (initialized)
            return;
        initialized = true;
        this.setSize(500, 150);
        this.setTitle("Dave's Bing Rewards Bot");
        Dimension windowSize = this.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width/2 - windowSize.width/2, screenSize.height/2 - windowSize.height/2);
        Container pane = this.getContentPane();
        pane.setLayout(new FlowLayout());
        userPanel = new JPanel();
        userPanel.setBounds(0,0, 200,400);
        label = new JLabel("# of Searches");
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        noUsersLabel = new JLabel("Number of Users");
        searches = new JTextField(5);
        usernameField = new JTextField(10);
        passwordField = new JTextField(10);
        usersField = new JTextField(5);
        submit = new JButton("Submit");
        newUser = new JButton("New User");
        submit.addActionListener(new Actions());
        newUser.addActionListener(new Actions());
        userPanel.add(usernameLabel);
        userPanel.add(usernameField);
        userPanel.add(passwordLabel);
        userPanel.add(passwordField);
        userPanel.add(newUser);
        pane.add(userPanel);
        pane.add(noUsersLabel);
        pane.add(usersField);
        pane.add(label);
        pane.add(searches);
        pane.add(submit);
    }
    
    private void initializeEvents() {
        searchEvent = new ActionEvent(submit, ActionEvent.ACTION_FIRST, "search");
        newUserEvent = new ActionEvent(newUser, ActionEvent.ACTION_FIRST+1, "newUser");
    }
    
    public class Actions implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Event Fired...");
            String command = e.getActionCommand();
            System.out.println("Command equals: " + command);
            command = command == null ? "" : command;
            if (command.equals("Submit")) {
                System.out.println("Searching...");
                try {
                    new BingBot().search(Integer.parseInt(usersField.getText()),Integer.parseInt(searches.getText()),new File("./files/users"));
                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (command.equals("New User")) {
                System.out.println("Creating New User...");
                try {
                    createNewUser(usernameField.getText(),passwordField.getText());
                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void createNewUser(String name, String password) throws FileNotFoundException, IOException {
        try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./files/users", true)))) {
            out.println(name);
            out.println(password);
            out.println("END");
        }catch (IOException e) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, e);
        }        
    }

    public void dispose() {
        // TODO: Save settings
        //super.dispose();
        System.exit(0);
    }
    
    public void setVisible(boolean b) {
        initialize();
        super.setVisible(b);
    }

    
	public static void main(String[] args) {
        new MainFrame().setVisible(true);
	}
}
