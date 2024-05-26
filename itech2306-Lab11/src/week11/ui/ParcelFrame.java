package week11.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import week11.domain.Country;
import week11.domain.Parcel;
public class ParcelFrame extends JFrame {
	private ArrayList<Country> countries;
	 private JTextField weightField;
	 private JRadioButton flatButton, smallButton, mediumButton, largeButton;
	 private JTextArea addressArea;
	 private JComboBox<String> countryBox;
	 private JCheckBox fragileCheckBox;  
	 private JLabel outputLabel;
	 private JButton calculateButton, clearButton;
	 
	public ParcelFrame(ArrayList<Country> countries) {
        this.countries = countries;
        setLayout(new GridLayout(0, 1));
        JLabel titleLabel = new JLabel("Parcel Delivery Calculator", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(46, 139, 87));
        JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel);
        
        JPanel weightPanel = new JPanel();
        weightPanel.add(new JLabel("Weight (kg):"));
        weightField = new JTextField(5); 
        weightPanel.add(weightField);
        
        JPanel sizePanel = new JPanel();
        sizePanel.add(new JLabel("Package size:"));
        flatButton = new JRadioButton("Flat");
        smallButton = new JRadioButton("Small");
        mediumButton = new JRadioButton("Medium");
        largeButton = new JRadioButton("Large");
        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(flatButton);
        sizeGroup.add(smallButton);
        sizeGroup.add(mediumButton);
        sizeGroup.add(largeButton);
        flatButton.setSelected(true);
        
        sizePanel.add(flatButton);
        sizePanel.add(smallButton);
        sizePanel.add(mediumButton);
        sizePanel.add(largeButton);
        
        JPanel addressPanel = new JPanel();
        addressPanel.add(new JLabel("Deliver to:"));
        addressArea = new JTextArea(3, 20);  
        addressPanel.add(addressArea);
        
        JPanel deliverycountryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        countryBox = new JComboBox<>();  // Initialize the JComboBox
        for (Country country : countries) {
            countryBox.addItem(country.getName());
        }
        fragileCheckBox = new JCheckBox("Fragile Item?");
       fragileCheckBox.setForeground(new Color(235,51,36));
        deliverycountryPanel.add(new JLabel("Country:"));
        deliverycountryPanel.add(countryBox);
        deliverycountryPanel.add(fragileCheckBox);
        
        JPanel outputPanel = new JPanel();
        outputPanel.setBackground(new Color(255, 239, 213));
        outputLabel = new JLabel("Enter details above.");
        outputPanel.add(outputLabel);
        
        JPanel buttonPanel = new JPanel();
        calculateButton = new JButton("Calculate Cost");
        clearButton = new JButton("Clear All");
        ButtonResponder buttonResponder = new ButtonResponder();
        calculateButton.addActionListener(buttonResponder);
        clearButton.addActionListener(buttonResponder);
        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);
        
        
        add(titlePanel);
        add(weightPanel);
        add(sizePanel);
        add(addressPanel);
        add(deliverycountryPanel);
        add(outputPanel);
        add(buttonPanel);
        pack();
        setVisible(true);
    }
	public Parcel processParcel() {
       
        String weightText = weightField.getText();
        double weight = Double.parseDouble(weightText);
        int size;
        if (flatButton.isSelected()) {
            size = Parcel.FLAT;
        } else if (smallButton.isSelected()) {
            size = Parcel.SMALL_BOX;
        } else if (mediumButton.isSelected()) {
            size = Parcel.MEDIUM_BOX;
        } else {  // Assume largeButton is selected if none of the others are
            size = Parcel.LARGE_BOX;
        }
        int selectedIndex = countryBox.getSelectedIndex();
        Country selectedCountry = countries.get(selectedIndex);
        boolean isFragile = fragileCheckBox.isSelected();
        String deliveryAddress = addressArea.getText();
        Parcel parcel = new Parcel(deliveryAddress, selectedCountry, weight, isFragile, size);
        return parcel;

	}
	 private class ButtonResponder implements ActionListener {
	        public void actionPerformed(ActionEvent e) {
	            if (e.getSource() == calculateButton) {
	               
	                Parcel parcel = processParcel();
	                double cost = parcel.calculateCost();
	                outputLabel.setText("The cost will be $" + cost);
	            }else if (e.getSource() == clearButton) {
	            	weightField.setText("");
	                addressArea.setText("");
	                fragileCheckBox.setSelected(false);
	                flatButton.setSelected(true);
	                countryBox.setSelectedIndex(0);
	                outputLabel.setText("Enter details above.");
	            }
	        }
	 }
}
