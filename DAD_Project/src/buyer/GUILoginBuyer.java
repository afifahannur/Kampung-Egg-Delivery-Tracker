package buyer;

import buyer.GUIHomePageBuyer;
import buyer.GUINewUserBuyer;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class GUILoginBuyer {

	private JFrame frame;
	private JTextField txtEmail;
	private JPasswordField txtPassword;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				GUILoginBuyer window = new GUILoginBuyer();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public GUILoginBuyer() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setSize(800, 500);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		ImageIcon bgIcon = new ImageIcon("C:\\personal\\UTeM\\y2 sem2\\App Dev\\proj\\cust login.png");
		Image img = bgIcon.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH);
		ImageIcon scaledBg = new ImageIcon(img);
		JLabel bgLabel = new JLabel(scaledBg);
		bgLabel.setBounds(0, 0, 800, 500);
		bgLabel.setLayout(null);
		frame.getContentPane().add(bgLabel);

		txtEmail = new JTextField();
		txtEmail.setBounds(306, 154, 300, 25);
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtEmail.setToolTipText("Email");
		bgLabel.add(txtEmail);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(306, 184, 300, 25);
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPassword.setToolTipText("Password");
		bgLabel.add(txtPassword);

		JButton btnLogin = new JButton("Login");
		btnLogin.setBackground(new Color(255, 255, 128));
		btnLogin.setBounds(221, 224, 140, 35);
		btnLogin.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		bgLabel.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = txtEmail.getText().trim();
				String password = new String(txtPassword.getPassword()).trim();

				if (email.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Please enter both email and password.");
					return;
				}

				try {
					URL url = new URL("https://69f17fdfc58c.ngrok-free.app/eggdelivery/login_buyer.php?email=...&password=...");
					//String urlStr = "https://69f17fdfc58c.ngrok-free.app/eggdelivery/login_buyer.php?email=...&password=...";
					//URL url = new URL(urlStr);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");

					BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					StringBuilder responseSB = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						responseSB.append(line);
					}
					reader.close();

					String response = responseSB.toString();
					JSONObject jsonResponse = new JSONObject(response);

					if (jsonResponse.has("success") && jsonResponse.getBoolean("success")) {
						int userId = jsonResponse.getInt("user_id");
						JOptionPane.showMessageDialog(frame, "Login successful!");
						frame.dispose();
						GUIHomePageBuyer homePage = new GUIHomePageBuyer(userId);
					} else {
						JOptionPane.showMessageDialog(frame, "Invalid login. Please try again.");
					}

				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
				}
			}
		});

		JButton btnNewUser = new JButton("New User");
		btnNewUser.setBackground(new Color(255, 255, 128));
		btnNewUser.setBounds(403, 224, 140, 35);
		btnNewUser.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		bgLabel.add(btnNewUser);
		btnNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				GUINewUserBuyer newUser = new GUINewUserBuyer();
			}
		});
	}
}