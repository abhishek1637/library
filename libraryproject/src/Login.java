import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login {

	public JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	public static Login window;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection connection=null;
	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		connection=DbConnection.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Library Section");
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 683, 451);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel Icon1 = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/Book2.png")).getImage();
		Icon1.setIcon(new ImageIcon(img));
		//Icon1.setIcon(new ImageIcon("/home/abhishek/eclipse-workspace/Library/Image/Book.png"));
		Icon1.setBounds(23, 129, 232, 256);
		frame.getContentPane().add(Icon1);
		
		JLabel LabelAdmin = new JLabel("Admin ID");
		LabelAdmin.setFont(new Font("Dialog", Font.BOLD, 14));
		LabelAdmin.setBounds(365, 169, 79, 22);
		frame.getContentPane().add(LabelAdmin);
		
		textField = new JTextField();
		textField.setFont(new Font("Dialog", Font.BOLD, 12));
		textField.setBounds(462, 168, 129, 25);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPassword.setBounds(365, 216, 79, 36);
		frame.getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		passwordField.setFont(new Font("Dialog", Font.BOLD, 12));
		passwordField.setBounds(462, 222, 129, 25);
		frame.getContentPane().add(passwordField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				try {
					String query="select * from Admin where AdminID=? and Password=?"; //parameter index of first ? is 1 and second ? is 2
					PreparedStatement pst=connection.prepareStatement(query);
					pst.setString(1, textField.getText());
					pst.setString(2, passwordField.getText());
					ResultSet rs=pst.executeQuery();
					int count=0;
					while(rs.next()) {
						count +=1;
					}
					if(count==1) {
						JOptionPane.showMessageDialog(null, "Admin ID and Password Matched");
						frame.dispose();
						Application app = new Application();
						app.setVisible(true);
					}
					else {
						JOptionPane.showMessageDialog(null, "Incorrect Admin ID or Password...Try Again!!!");
					}
					}catch(Exception ca) {
						JOptionPane.showMessageDialog(null, ca);
					}
			}
		});
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				String query="select * from Admin where AdminID=? and Password=?"; //parameter index of first ? is 1 and second ? is 2
				PreparedStatement pst=connection.prepareStatement(query);
				pst.setString(1, textField.getText());
				pst.setString(2, passwordField.getText());
				ResultSet rs=pst.executeQuery();
				int count=0;
				while(rs.next()) {
					count +=1;
				}
				if(count==1) {
					JOptionPane.showMessageDialog(null, "Admin ID and Password Matched");
					frame.dispose();
					Application app = new Application();
					app.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "Incorrect Admin ID or Password...Try Again!!!");
				}
				}catch(Exception ca) {
					JOptionPane.showMessageDialog(null, ca);
				}
			}
			
		});
		btnLogin.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		btnLogin.setBounds(407, 278, 114, 25);
		frame.getContentPane().add(btnLogin);
		
		JLabel lnmcbm = new JLabel("");
		Image logo = new ImageIcon(this.getClass().getResource("/lnmcbmlogo.png")).getImage();
		lnmcbm.setIcon(new ImageIcon(logo));
		lnmcbm.setBounds(0, -14, 815, 132);
		frame.getContentPane().add(lnmcbm);
		
		JLabel forgot = new JLabel("forgot password ? Click Here");
		forgot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		forgot.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					JOptionPane.showConfirmDialog(null, "Have you really forgot your password ? ","Forget Password",JOptionPane.YES_NO_OPTION);
				frame.dispose();
				Forgot f = new Forgot();
				f.setVisible(true);
				}catch(Exception ca){
					JOptionPane.showMessageDialog(null, ca);
				}
				
				
				
			}
		});
		forgot.setFont(new Font("Dialog", Font.BOLD, 10));
		forgot.setForeground(Color.RED);
		forgot.setBounds(365, 315, 194, 15);
		frame.getContentPane().add(forgot);
			}
}
