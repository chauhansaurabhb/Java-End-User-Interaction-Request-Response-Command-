package deviceImpl;

import iotsuite.localmiddleware.IDataListener;
import iotsuite.localmiddleware.PubSubsSensingFramework;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.google.gson.JsonObject;

import logic.*;
import framework.*;
import android.content.Context;

public class JavaSESmartHomeApp implements ISmartHomeApp,IDataListener {

	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private JTextField jtfTextField = new JTextField("Enter Temperature");
	private JTextField jtfTextField1 = new JTextField("Enter badgeID");
	public static double tempValue;
	public static int badgeID;
	public static PubSubsSensingFramework pubSubSensingFramework;

	private ListenerSmartHomeApp listenerOffCommand = null;

	private ListenerSmartHomeApp listenerSetTempCommand = null;

	private ListenerSmartHomeApp listenerProfileRequest = null;
	

	public JavaSESmartHomeApp(Context context, LogicSmartHomeApp obj) {
		prepareGUI();
		showEventDemo();
		
		pubSubSensingFramework = PubSubsSensingFramework.getInstance();
		pubSubSensingFramework.registerForSensorData(this, "profileResponse");
		
	}

	
	@Override
	public void OffGUI(ListenerSmartHomeApp handler) {
		listenerOffCommand = handler;
	}

	@Override
	public void SetTempGUI(ListenerSmartHomeApp handler) {
		listenerSetTempCommand = handler;
	}

	@Override
	public void ProfileGUI(ListenerSmartHomeApp handler) {
		listenerProfileRequest = handler;
	}

	/*public void ProfileResponseReceived(TempStruct response) {
		
	
		jtfTextField.setText(Double.toString(response.gettempValue()));
	}*/
	
	@Override
	public void onDataReceived(String eventName, JsonObject data) {
		// TODO Auto-generated method stub
		
		//TempStruct data = new TempStruct(tempValue, unitOfMeasurement)
		double tempValue=data.get("tempValue").getAsDouble();
		String unitOfMeasurement=data.get("unitOfMeasurement").getAsString();
		jtfTextField.setText(Double.toString(tempValue));
	}

	public void OffCommandInterface() {
		listenerOffCommand.onNewOffCommand();
	}

	public void setTemmpCommandInterface() {
		listenerOffCommand.onNewSetTempCommand(new TempStruct(tempValue, "C"));
	}
	
	public void ProfileRequestInterface(){
		listenerProfileRequest.onNewProfileRequest(String.valueOf(badgeID));
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Simulated BadgeReader");
		mainFrame.setSize(200, 200);
		mainFrame.setLayout(new GridLayout(3, 1));

		headerLabel = new JLabel("", JLabel.CENTER);
		statusLabel = new JLabel("", JLabel.CENTER);
		jtfTextField = new JTextField(2);
		jtfTextField1 = new JTextField(2);

		// jtfTextField = new JTextField(5);
		statusLabel.setSize(350, 100);

		mainFrame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		mainFrame.add(jtfTextField);
		mainFrame.add(jtfTextField1);

		mainFrame.setVisible(true);
	}

	public void showEventDemo() {

		// Off Command...
		JButton off = new JButton("Off Command");
		off.setActionCommand("Off Command");
		off.addActionListener(new ButtonClickListener());

		// SetTemp Command
		JButton setTemp = new JButton("Set Temperature Command");
		setTemp.setActionCommand("SetTemp Command");
		setTemp.addActionListener(new setTempButtonClickListener());
		
		
		//request Temp
		JButton requestTemp = new JButton("Request Temp Command");
		requestTemp.setActionCommand("Request Temp Command");
		requestTemp.addActionListener(new requestTempButtonClickListener());
		
		
		

		controlPanel.add(off);
		controlPanel.add(setTemp);
		controlPanel.add(requestTemp);

		mainFrame.setVisible(true);
	}

	private class ButtonClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			OffCommandInterface();

		}
	}

	private class setTempButtonClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			tempValue = Double.parseDouble(jtfTextField.getText());
			setTemmpCommandInterface();
		}
	}
	
	
	private class requestTempButtonClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			badgeID = Integer.parseInt(jtfTextField1.getText());
			ProfileRequestInterface();
		}
	}


	

}