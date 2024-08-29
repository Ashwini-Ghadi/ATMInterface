package com.atm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import com.account.UserBankAccount;

public class ATMFeatures {
    private static final int MAX_ATTEMPTS = 3;
    private Map<String, UserBankAccount> accounts = new HashMap<>();
    private UserBankAccount currentAccount;
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JTextField pinField;
    private JTextField depositField;
    private JTextField withdrawField;
    private JLabel welcomeLabel;
    private int attempts = 0;

    public ATMFeatures(Map<String, UserBankAccount> accounts) {
        this.accounts = accounts;
        frame = new JFrame("ATM");
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Initialize all panels
        initPinPanel();
        initMainPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300); 
        frame.setLayout(new BorderLayout());
        frame.add(cardPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void initPinPanel() {
        // Create the panel and set background color
        JPanel pinPanel = new JPanel(new GridBagLayout());
        pinPanel.setBackground(Color.LIGHT_GRAY); // Set the desired background color
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Add the "Enter your PIN" label
        gbc.gridx = 0;
        gbc.gridy = 0;
        pinPanel.add(new JLabel("Enter your PIN:"), gbc);
        
        // Add the input field
        gbc.gridx = 1;
        gbc.gridy = 0;
        pinField = new JTextField(10);
        pinPanel.add(pinField, gbc);
        
        // Add the submit button
        gbc.gridx = 1;
        gbc.gridy = 1;
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });
        pinPanel.add(submitButton, gbc);
        
        // Add the panel to the CardLayout
        cardPanel.add(pinPanel, "PIN Panel");
    }


    private void initMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Welcome Label
        welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(welcomeLabel, gbc);

        // Deposit Button
        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(depositButton, gbc);

        // Withdraw Button
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(withdrawButton, gbc);

        // Check Balance
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });
        mainPanel.add(checkBalanceButton, gbc);

        // Logout
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "PIN Panel");
                pinField.setText(""); 
                currentAccount = null;
            }
        });
        mainPanel.add(exitButton, gbc);

        cardPanel.add(mainPanel, "Main Panel");
    }

    private void authenticateUser() {
        String inputPin = pinField.getText();
        UserBankAccount account = accounts.get(inputPin);

        if (account != null) {
            currentAccount = account;
            cardLayout.show(cardPanel, "Main Panel");
            attempts = 0;
            updateBalanceLabel();
        } else {
            attempts++;
            if (attempts >= MAX_ATTEMPTS) {
                JOptionPane.showMessageDialog(frame, "Too many failed attempts. Exiting.");
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(frame, "Incorrect PIN. Please try again.");
            }
        }
    }

    private void checkBalance() {
        if (currentAccount != null) {
            JOptionPane.showMessageDialog(frame, "Current balance: " + currentAccount.getBalance());
        }
    }

    private void deposit() {
        String amountStr = JOptionPane.showInputDialog(frame, "Enter amount to deposit:");
        if (amountStr != null && !amountStr.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr);
                currentAccount.deposit(amount);
                updateBalanceLabel();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid deposit amount.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Deposit amount cannot be empty.");
        }
    }

    private void withdraw() {
        String amountStr = JOptionPane.showInputDialog(frame, "Enter amount to withdraw:");
        if (amountStr != null && !amountStr.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(amountStr);
                if (currentAccount.withdraw(amount)) {
                    updateBalanceLabel();
                } else {
                    JOptionPane.showMessageDialog(frame, "Insufficient funds for withdrawal.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid withdrawal amount.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Withdrawal amount cannot be empty.");
        }
    }

    private void updateBalanceLabel() {
        welcomeLabel.setText("Welcome " + currentAccount.getName() + " !");
    }

}
