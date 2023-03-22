package me.bannock.virus;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.text.NumberFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

public class VirusSetupForm {

    private final Config config;

    private JPanel form;
    private JButton startButton;
    private JButton setupToRunOnButton;
    private JRadioButton changeBackgroundRadioButton;
    private JRadioButton changeFolderIconsRadioButton;
    private JRadioButton imageFloodRadioButton;
    private JSpinner imageAmount;
    private JCheckBox scatterAcrossDriveCheckBox;
    private JRadioButton setUserIconRadioButton;
    private JRadioButton changePreLoginBackgroundRadioButton;
    private JRadioButton repeatHourlyRadioButton;

    /**
     * @param config The config object to link to this form
     */
    public VirusSetupForm(Config config) {
        this.config = config; // Set config

        // Make image amount field only accept numbers
        JFormattedTextField txt = ((JSpinner.NumberEditor) imageAmount.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);

        // Set default value for image amount field
        imageAmount.setValue(config.getImageAmount());

        // Default config object linking listeners
        setUserIconRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                config.setSetUserIcon(setUserIconRadioButton.isSelected());
            }
        });
        imageFloodRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                config.setImageFlood(imageFloodRadioButton.isSelected());
                imageAmount.setEnabled(imageFloodRadioButton.isSelected());
                scatterAcrossDriveCheckBox.setEnabled(imageFloodRadioButton.isSelected());
            }
        });
        scatterAcrossDriveCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                config.setScatterAcrossDrive(scatterAcrossDriveCheckBox.isSelected());
            }
        });
        imageAmount.addInputMethodListener(new InputMethodListener() {
            @Override
            public void inputMethodTextChanged(InputMethodEvent event) {
                config.setImageAmount(Integer.valueOf(imageAmount.getValue().toString()));
            }

            @Override
            public void caretPositionChanged(InputMethodEvent event) { }
        });
        changeFolderIconsRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                config.setChangeFolderIcons(changeFolderIconsRadioButton.isSelected());
            }
        });
        changeBackgroundRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                config.setChangeBackground(changeBackgroundRadioButton.isSelected());
            }
        });
        repeatHourlyRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                config.setRepeatHourly(repeatHourlyRadioButton.isSelected());
            }
        });
        changePreLoginBackgroundRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                config.setSetPreLoginBackground(changePreLoginBackgroundRadioButton.isSelected());
            }
        });
    }

    public JPanel getForm() {
        return form;
    }

    private void createUIComponents() {
        System.out.println("test");
    }
}
