import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class Forgot extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField1;
	private JTextField textField2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Forgot frame = new Forgot();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	Connection connection=null;
	public Forgot() {
		connection=DbConnection.dbConnector();
		setResizable(false);
		setTitle("Forgot Password");
		setForeground(Color.PINK);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 690, 422);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(244, 164, 96));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter your AdminID");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel.setBounds(12, 176, 158, 45);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		textField.setBounds(197, 184, 132, 29);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Enter Security Answer");
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setBounds(12, 233, 221, 17);
		contentPane.add(lblNewLabel_1);
		
		textField1 = new JTextField();
		textField1.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		textField1.setBounds(197, 225, 132, 29);
		contentPane.add(textField1);
		textField1.setColumns(10);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query="Select * from Admin where AdminId=? and Security_ques=?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textField.getText());
					pst.setString(2, textField1.getText());
					ResultSet rs = pst.executeQuery();
					boolean result=rs.next();
					if(result) {
						textField2.setText(rs.getString("Password"));
					}else {
						JOptionPane.showMessageDialog(null, "Incorrect data");
					}
					pst.close();
					
				}catch(Exception c) {
					JOptionPane.showMessageDialog(null, "Incorrect data");
				}
			}
		});
		btnNewButton.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		btnNewButton.setBounds(119, 275, 114, 25);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Your Password");
		lblNewLabel_2.setForeground(new Color(0, 0, 0));
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_2.setBounds(27, 343, 117, 17);
		contentPane.add(lblNewLabel_2);
		
		textField2 = new JTextField();
		textField2.setEnabled(false);
		textField2.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		textField2.setForeground(new Color(0, 0, 0));
		textField2.setBounds(164, 337, 132, 29);
		contentPane.add(textField2);
		textField2.setColumns(10);
		
		JLabel lnmcbm = new JLabel("");
		Image logo = new ImageIcon(this.getClass().getResource("/lnmcbmlogo.png")).getImage();
		lnmcbm.setIcon(new ImageIcon(logo));
		lnmcbm.setBounds(0, 0, 690, 104);
		contentPane.add(lnmcbm);
		
		JLabel forget = new JLabel("");
		Image f = new ImageIcon(this.getClass().getResource("/forget.jpeg")).getImage();
		forget.setIcon(new ImageIcon(f));
		forget.setBounds(365, 101, 325, 291);
		contentPane.add(forget);
		
		JLabel back = new JLabel("");
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int action=JOptionPane.showConfirmDialog(null, "Do you really want to Go Back To Login Form?","Back",JOptionPane.YES_NO_OPTION);
				if(action==0) {
				
					try {
						dispose();
			Login window = new Login();
			window.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				window.frame.setVisible(true);
				
				
					} catch (Exception ca) {
						JOptionPane.showMessageDialog(null, ca);
					}
				
				}
		
			}
		});
		Image b = new ImageIcon(this.getClass().getResource("/logout.png")).getImage();
		back.setIcon(new ImageIcon(b));
		back.setBounds(291, 116, 81, 48);
		contentPane.add(back);
	}
}
