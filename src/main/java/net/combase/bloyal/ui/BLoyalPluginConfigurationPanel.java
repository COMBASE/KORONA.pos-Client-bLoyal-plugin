package net.combase.bloyal.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.combase.bloyal.settings.Settings;

public abstract class BLoyalPluginConfigurationPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 189059774606715184L;
	private JTextField bLoyalUrlField;
	private JTextField storeCodeField;
	private JTextField deviceCodeField;
	private JTextField koronaAccountField;
	private JLabel lblKoronaGiftAccount;
	private JTextField koronaPaymentMethodField;
	private JLabel lblKoronaGiftPayment;
	private JLabel lblLoadGiftCard;
	private JTextField loadUrlField;
	private JLabel lblUseGiftCard;
	private JTextField useUrlFiel;
	private JTextField checkUrlField;
	private JLabel lblNewLabel_1;
	private JButton btnSaveStart;
	private JTextField deviceKeyField;
	private JLabel lblDeviceKey;
	private JSpinner applicationPortField;
	private JCheckBox chckbxAutostart;
	private TimerTask task;

	/**
	 * Create the panel.
	 */
	public BLoyalPluginConfigurationPanel()
	{
		setLayout(null);

		JLabel lblBloyalUrl = new JLabel("BLoyal URL");
		lblBloyalUrl.setBounds(12, 12, 129, 15);
		add(lblBloyalUrl);

		bLoyalUrlField = new JTextField();
		bLoyalUrlField.setBounds(159, 10, 373, 19);
		add(bLoyalUrlField);
		bLoyalUrlField.setColumns(10);

		JLabel lblStoreCode = new JLabel("Store Code");
		lblStoreCode.setBounds(12, 43, 114, 15);
		add(lblStoreCode);

		storeCodeField = new JTextField();
		storeCodeField.setBounds(159, 41, 373, 19);
		add(storeCodeField);
		storeCodeField.setColumns(10);

		JLabel lblDeviceCode = new JLabel("Device Code");
		lblDeviceCode.setBounds(12, 77, 114, 15);
		add(lblDeviceCode);

		deviceCodeField = new JTextField();
		deviceCodeField.setBounds(159, 75, 373, 19);
		add(deviceCodeField);
		deviceCodeField.setColumns(10);

		koronaAccountField = new JTextField();
		koronaAccountField.setBounds(334, 165, 114, 19);
		add(koronaAccountField);
		koronaAccountField.setColumns(10);

		lblKoronaGiftAccount = new JLabel("KORONA Gift Account Number");
		lblKoronaGiftAccount.setBounds(12, 167, 227, 15);
		add(lblKoronaGiftAccount);

		koronaPaymentMethodField = new JTextField();
		koronaPaymentMethodField.setBounds(334, 196, 114, 19);
		add(koronaPaymentMethodField);
		koronaPaymentMethodField.setColumns(10);

		lblKoronaGiftPayment = new JLabel("KORONA Gift Payment Method Number");
		lblKoronaGiftPayment.setBounds(12, 194, 286, 15);
		add(lblKoronaGiftPayment);

		applicationPortField = new JSpinner();
		applicationPortField.setBounds(334, 248, 114, 20);
		applicationPortField.setEditor(new JSpinner.NumberEditor(applicationPortField, "#"));
		add(applicationPortField);

		JLabel lblNewLabel = new JLabel("Application Port Number");
		lblNewLabel.setBounds(12, 250, 273, 15);
		add(lblNewLabel);

		lblLoadGiftCard = new JLabel("Load Gift Card");
		lblLoadGiftCard.setBounds(12, 308, 114, 15);
		add(lblLoadGiftCard);

		loadUrlField = new JTextField();
		loadUrlField.setBounds(159, 306, 373, 19);
		add(loadUrlField);
		loadUrlField.setColumns(10);

		lblUseGiftCard = new JLabel("Use Gift Card");
		lblUseGiftCard.setBounds(12, 339, 114, 15);
		add(lblUseGiftCard);

		useUrlFiel = new JTextField();
		useUrlFiel.setBounds(159, 337, 373, 19);
		add(useUrlFiel);
		useUrlFiel.setColumns(10);

		checkUrlField = new JTextField();
		checkUrlField.setBounds(159, 368, 373, 19);
		add(checkUrlField);
		checkUrlField.setColumns(10);

		lblNewLabel_1 = new JLabel("Check Gift Card");
		lblNewLabel_1.setBounds(12, 370, 114, 15);
		add(lblNewLabel_1);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(122, 416, 117, 25);
		btnCancel.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				onCancel();
			}
		});
		add(btnCancel);

		btnSaveStart = new JButton("Save & Start");
		btnSaveStart.setBounds(334, 416, 145, 25);
		btnSaveStart.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				performStart();
			}


		});
		add(btnSaveStart);

		deviceKeyField = new JTextField();
		deviceKeyField.setBounds(159, 106, 373, 19);
		add(deviceKeyField);
		deviceKeyField.setColumns(10);

		lblDeviceKey = new JLabel("Device Key");
		lblDeviceKey.setBounds(12, 108, 98, 15);
		add(lblDeviceKey);

		chckbxAutostart = new JCheckBox("Autostart");
		chckbxAutostart.setBounds(334, 449, 129, 23);
		chckbxAutostart.addChangeListener(new ChangeListener()
		{

			public void stateChanged(ChangeEvent e)
			{
				updateTimer();
			}
		});
		add(chckbxAutostart);
	}

	/**
	 * 
	 */
	private void performStart()
	{
		onSaveStart();

		bLoyalUrlField.setEnabled(false);
		storeCodeField.setEnabled(false);
		deviceCodeField.setEnabled(false);
		deviceKeyField.setEnabled(false);
		koronaAccountField.setEnabled(false);
		koronaPaymentMethodField.setEnabled(false);
		applicationPortField.setEnabled(false);
		chckbxAutostart.setEnabled(false);


		btnSaveStart.setText("Running");
		btnSaveStart.setEnabled(false);
		chckbxAutostart.setEnabled(false);
	}

	protected void updateTimer()
	{
		if (chckbxAutostart.isSelected())
		{
			Timer t = new Timer();
			if (task != null)
				task.cancel();
			task = new TimerTask()
			{
				int sec = 10;

				@Override
				public void run()
				{
					if (!btnSaveStart.isEnabled())
					{
						cancel();
						return;
					}

					sec--;
					SwingUtilities.invokeLater(new Runnable()
					{

						public void run()
						{
							btnSaveStart.setText("Starting (" + sec + ")");
						}
					});
					if (sec == 0)
					{
						cancel();

						performStart();
					}

				}
			};
			t.scheduleAtFixedRate(task, 1000, 1000);
		}
		else if (task != null)
		{
			task.cancel();
		}
	}

	protected abstract void onCancel();

	protected abstract void onSaveStart();

	public void readSettings(Settings s)
	{
		bLoyalUrlField.setText(s.getLoyaltyEngineServiceUrl());
		storeCodeField.setText(s.getStoreCode());
		deviceCodeField.setText(s.getDeviceCode());
		deviceKeyField.setText(s.getDeviceKey());
		koronaAccountField.setText(s.getKoronaGiftCardAccountNumber());
		koronaPaymentMethodField.setText(s.getKoronaGiftCardPaymentMethodNumber());
		applicationPortField.setValue(Integer.valueOf(s.getPort()));
		chckbxAutostart.setSelected(s.isAutostart());
	}

	public void writeSettings(Settings s)
	{
		s.setLoyaltyEngineServiceUrl(bLoyalUrlField.getText());
		s.setStoreCode(storeCodeField.getText());
		s.setDeviceCode(deviceCodeField.getText());
		s.setDeviceKey(deviceKeyField.getText());
		s.setKoronaGiftCardAccountNumber(koronaAccountField.getText());
		s.setKoronaGiftCardPaymentMethodNumber(koronaPaymentMethodField.getText());
		s.setPort((Integer)applicationPortField.getValue());
		s.setAutostart(chckbxAutostart.isSelected());
	}


	public void updateUrls(String prefix)
	{
		loadUrlField.setText(prefix + "/load.html");
		useUrlFiel.setText(prefix + "/redeem.html");
		checkUrlField.setText(prefix + "/check.html");

		loadUrlField.setEditable(false);
		useUrlFiel.setEditable(false);
		checkUrlField.setEditable(false);
	}
}
