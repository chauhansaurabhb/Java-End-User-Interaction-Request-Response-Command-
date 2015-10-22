package deviceImpl;

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

import logic.*;
import framework.*;
import android.content.Context;

public class JavaSESmartHomeApp implements ISmartHomeApp {

	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;

	private ListenerSmartHomeApp listenerOffCommand = null;

	private ListenerSmartHomeApp listenerSetTempCommand = null;

	private ListenerSmartHomeApp listenerProfileRequest = null;

	public JavaSESmartHomeApp(Context context, LogicSmartHomeApp obj) {
		prepareGUI();
		showEventDemo();
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

	public void ProfileResponseReceived(TempStruct response) {

	}

	public void OffCommandInterface() {
		listenerOffCommand.onNewOffCommand();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Simulated BadgeReader");
		mainFrame.setSize(200, 200);
		mainFrame.setLayout(new GridLayout(3, 1));

		headerLabel = new JLabel("", JLabel.CENTER);
		statusLabel = new JLabel("", JLabel.CENTER);

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
		// mainFrame.add(jtfTextField);

		mainFrame.setVisible(true);
	}

	public void showEventDemo() {

		JButton Off = new JButton("Off Command Fired");

		Off.setActionCommand("Off Command Fired");

		Off.addActionListener(new ButtonClickListener());

		controlPanel.add(Off);

		mainFrame.setVisible(true);
	}

	private class ButtonClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			OffCommandInterface();

		}
	}

}