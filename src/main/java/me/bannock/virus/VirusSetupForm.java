package me.bannock.virus;

import com.google.gson.Gson;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import me.bannock.virus.images.ImageProviderService;
import me.bannock.virus.images.impl.TestImageProviderService;
import me.bannock.virus.images.impl.YiffProviderService;
import me.bannock.virus.utils.StartOnWindowsStartUtil;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class VirusSetupForm {

    private JPanel form;
    private JButton startButton;
    private JButton setupToRunOnButton;
    private JRadioButton changeBackgroundRadioButton;
    private JRadioButton changeFolderIconsRadioButton;
    private JRadioButton imageFloodRadioButton;
    private JSpinner imageAmount;
    private JCheckBox scatterAcrossDriveCheckBox;
    private JRadioButton setUserIconRadioButton;
    private JRadioButton repeatHourlyRadioButton;
    private JRadioButton showWindowPopupsRadioButton;
    private JComboBox imageProviderSelection;
    private JLabel imageProviderLabel;

    /**
     * @param config             The config object to link to this form
     * @param startVirusRunnable The runnable to execute when the user wants to run the virus
     */
    public VirusSetupForm(Config config, Runnable startVirusRunnable) {

        // Make image amount field only accept numbers
        $$$setupUI$$$();
        JFormattedTextField txt = ((JSpinner.NumberEditor) imageAmount.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);

        // Set default value for image amount field
        imageAmount.setValue(config.getImageAmount());

        // Add image providers to the provider selection box
        {
            DefaultComboBoxModel<Class<? extends ImageProviderService>> tmp =
                    new DefaultComboBoxModel<Class<? extends ImageProviderService>>();
            tmp.addElement(YiffProviderService.class);
            tmp.addElement(TestImageProviderService.class);
            imageProviderSelection.setModel(tmp);

            // Code to display the simple names of the classes rather than the full names
            imageProviderSelection.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    if (value instanceof Class) {
                        value = ((Class) value).getSimpleName();
                    }
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                }
            });
        }

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
        imageAmount.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println(imageAmount.getValue().toString());
                config.setImageAmount(Integer.parseInt(imageAmount.getValue().toString()));
            }

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
                config.setRunHourly(repeatHourlyRadioButton.isSelected());
            }
        });
        setupToRunOnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Setup to run the jar headless on system startup
                StartOnWindowsStartUtil.setupJarRunningOnSystemStart("-headless");

                // Save the current config to the default location
                try {
                    Files.write(YiffVirus.HEADLESS_CONFIG_FILE.toPath(), new Gson().toJson(config)
                            .getBytes(StandardCharsets.UTF_8));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startVirusRunnable.run();
            }
        });
        showWindowPopupsRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                config.setPopupImages(showWindowPopupsRadioButton.isSelected());
            }
        });
        imageProviderSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                config.setImageProvider((Class<? extends ImageProviderService>) imageProviderSelection.getModel().getSelectedItem());
            }
        });
    }

    public JPanel getForm() {
        return form;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        form = new JPanel();
        form.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(5, 5, 5, 5), -1, -1));
        form.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
        startButton = new JButton();
        startButton.setText("Start");
        startButton.setToolTipText("Runs the virus and closes the ui");
        panel1.add(startButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        setupToRunOnButton = new JButton();
        setupToRunOnButton.setText("Setup to run on startup");
        setupToRunOnButton.setToolTipText("Sets the virus to run on startup with the currently entered config");
        panel1.add(setupToRunOnButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(8, 2, new Insets(5, 5, 5, 5), -1, -1));
        form.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        changeBackgroundRadioButton = new JRadioButton();
        changeBackgroundRadioButton.setEnabled(true);
        changeBackgroundRadioButton.setSelected(true);
        changeBackgroundRadioButton.setText("Change background");
        changeBackgroundRadioButton.setToolTipText("Changes the user's background");
        panel2.add(changeBackgroundRadioButton, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        changeFolderIconsRadioButton = new JRadioButton();
        changeFolderIconsRadioButton.setSelected(true);
        changeFolderIconsRadioButton.setText("Change folder icons");
        panel2.add(changeFolderIconsRadioButton, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        imageFloodRadioButton = new JRadioButton();
        imageFloodRadioButton.setSelected(true);
        imageFloodRadioButton.setText("Image flood");
        imageFloodRadioButton.putClientProperty("html.disable", Boolean.FALSE);
        panel2.add(imageFloodRadioButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        imageAmount = new JSpinner();
        imageAmount.setEnabled(true);
        imageAmount.setToolTipText("The amount of images to download");
        panel2.add(imageAmount, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scatterAcrossDriveCheckBox = new JCheckBox();
        scatterAcrossDriveCheckBox.setEnabled(true);
        scatterAcrossDriveCheckBox.setSelected(true);
        scatterAcrossDriveCheckBox.setText("Scatter across drive");
        scatterAcrossDriveCheckBox.setToolTipText("Scatter the image across the drive, makes it nearly impossible to remove them unless you use specialized tools");
        panel2.add(scatterAcrossDriveCheckBox, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        setUserIconRadioButton = new JRadioButton();
        setUserIconRadioButton.setEnabled(false);
        setUserIconRadioButton.setSelected(false);
        setUserIconRadioButton.setText("Set user icon");
        setUserIconRadioButton.setToolTipText("Changes the user's profile icon");
        panel2.add(setUserIconRadioButton, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        repeatHourlyRadioButton = new JRadioButton();
        repeatHourlyRadioButton.setSelected(true);
        repeatHourlyRadioButton.setText("Run hourly");
        repeatHourlyRadioButton.setToolTipText("Runs the virus hourly");
        panel2.add(repeatHourlyRadioButton, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showWindowPopupsRadioButton = new JRadioButton();
        showWindowPopupsRadioButton.setSelected(true);
        showWindowPopupsRadioButton.setText("Show window popups");
        panel2.add(showWindowPopupsRadioButton, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        imageProviderSelection = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("");
        imageProviderSelection.setModel(defaultComboBoxModel1);
        panel2.add(imageProviderSelection, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        imageProviderLabel = new JLabel();
        imageProviderLabel.setText("Image Provider");
        panel2.add(imageProviderLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        imageProviderLabel.setLabelFor(imageProviderSelection);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return form;
    }

}
