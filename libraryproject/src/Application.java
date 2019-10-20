import java.awt.BorderLayout;
import java.util.*;

import java.sql.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Application extends JFrame {

	private JPanel contentPane;
	private JTextField txtSearch;
	private JTable table;
	private JTextField textField;
	private JTextField textField1;
	private JTextField textField3;
	private JTextField textField2;
	private JTextField textField4;
	private JLabel lblclock;
	private JComboBox comboBox;
	private String combo;
	private static Application frames;
	private JButton ok ;
	private JButton submit;
	private JButton withdraw1;
	private JButton withdraw2;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frames = new Application();
					frames.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection connection=null;
	public void clock() {
		Thread clock = new Thread() {
			public void run() {
				try {
					while(true) {
						Calendar cal = new GregorianCalendar();
						int day=cal.get(Calendar.DAY_OF_MONTH);
						int month=cal.get(Calendar.MONTH);
						int year=cal.get(Calendar.YEAR);
						int sec = cal.get(Calendar.SECOND);
						int min = cal.get(Calendar.MINUTE);
						int hour = cal.get(Calendar.HOUR);
						lblclock.setText("Time "+hour+":"+min+":"+sec+"  "+"Date "+ year+"/"+(month+1)+"/"+day);
						sleep(1000);
						
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		};clock.start();
	}
	public void refresh() {
		try {
		String query = "Select * from ses"+combo+" where Roll=?";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.setString(1, textField.getText());
		ResultSet rs = pst.executeQuery();
		table.setModel(DbUtils.resultSetToTableModel(rs));
		}catch(Exception ca) {
			JOptionPane.showMessageDialog(null, ca);
		}
	}
	public Application() {
		setTitle("Library Section");
		setIconImage(Toolkit.getDefaultToolkit().getImage("/home/abhishek/eclipse-workspace/Library/Img/lnmcbm.png"));
		connection=DbConnection.dbConnector();
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1206, 506);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(70, 130, 180));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSession = new JLabel("Session:");
		lblSession.setForeground(new Color(0, 0, 0));
		lblSession.setBackground(new Color(0, 0, 0));
		lblSession.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 15));
		lblSession.setBounds(22, 56, 71, 18);
		contentPane.add(lblSession);
		
		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				combo = (String)comboBox.getSelectedItem();
				if(combo.length()>0) {
				textField.setEnabled(true);
				submit.setEnabled(true);}
				
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "2017_20", "2018_21", "2019_22", "2020_23"}));
		comboBox.setBounds(100, 53, 155, 24);
		contentPane.add(comboBox);
		
		JButton btnLoadDataOf = new JButton("Load Data of All Students");
		btnLoadDataOf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query="Select * from ses"+combo;
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
				}catch(Exception ca) {
					JOptionPane.showMessageDialog(null, "Please select a session");
				}
			}
		});
		btnLoadDataOf.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 15));
		btnLoadDataOf.setBounds(282, 52, 298, 27);
		contentPane.add(btnLoadDataOf);
		
		txtSearch = new JTextField();
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
				String query = "Select * from ses"+combo+" where F_name=?";
				PreparedStatement pst = connection.prepareStatement(query);
				pst.setString(1, txtSearch.getText());
				ResultSet rs = pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				}catch(Exception ca) {
			JOptionPane.showMessageDialog(null, "Please Select Correct Session");
				}
			}
		});
		txtSearch.setFont(new Font("Dialog", Font.ITALIC, 12));
		txtSearch.setBounds(661, 53, 164, 25);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);
		
		JLabel lblSearch = new JLabel("Search:");
		lblSearch.setBackground(new Color(0, 0, 0));
		lblSearch.setForeground(new Color(0, 0, 0));
		lblSearch.setFont(new Font("Dialog", Font.ITALIC, 14));
		lblSearch.setBounds(598, 58, 66, 15);
		contentPane.add(lblSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(282, 91, 912, 373);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBackground(Color.LIGHT_GRAY);
		table.setEnabled(false);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setFont(new Font("Dialog", Font.BOLD, 12));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel lblNewLabel = new JLabel("Roll No.");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(22, 113, 71, 15);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		textField.setBounds(100, 109, 155, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		submit = new JButton("Submit");
		submit.setEnabled(false);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "Select * from ses"+combo+" where Roll=?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textField.getText());
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					table.setRowSelectionInterval(0, 0);
					//int row=table.getSelectedRow();
					String book1 = (String)(table.getModel().getValueAt(0, 3).toString());
					if(book1.equals("-")) {
						textField1.setEnabled(true);
						textField2.setEnabled(true);
						ok.setEnabled(true);
						
					}
					else {
						withdraw1.setEnabled(true);
					}
					String book2 = (String)(table.getModel().getValueAt(0, 5).toString());
					if(book2.equals("-")) {
						textField3.setEnabled(true);
						textField4.setEnabled(true);
						ok.setEnabled(true);}
					else {
						withdraw2.setEnabled(true);
					}
					}catch(Exception ca) {
				JOptionPane.showMessageDialog(null, "Please Enter a valid Roll Number");
					}
			}
		});
		submit.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 15));
		submit.setBounds(100, 140, 114, 25);
		contentPane.add(submit);
		
		JLabel lblNewLabel_1 = new JLabel("Book Issue1");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setBounds(12, 163, 206, 27);
		contentPane.add(lblNewLabel_1);
		
		textField1 = new JTextField();
		textField1.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		textField1.setEnabled(false);
		textField1.setBounds(22, 188, 233, 41);
		contentPane.add(textField1);
		textField1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Book Issue2");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel_2.setForeground(Color.BLACK);
		lblNewLabel_2.setBounds(49, 263, 134, 27);
		contentPane.add(lblNewLabel_2);
		
		textField3 = new JTextField();
		textField3.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		textField3.setEnabled(false);
		textField3.setBounds(12, 290, 233, 41);
		contentPane.add(textField3);
		textField3.setColumns(10);
		
		ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField1.getText().length()>0) {
						Calendar c=new GregorianCalendar();
						int d=c.get(Calendar.DAY_OF_MONTH);
						int m=c.get(Calendar.MONTH);
						int y=c.get(Calendar.YEAR);
						m++;
						String day=Integer.toString(d);
						String mon=Integer.toString(m);
						String yr=Integer.toString(y);
						String t=day+"/"+mon+"/"+yr;
						
						String query1="update ses"+combo+" set Book1=?,Book1_no=?,Book1_Date=? where Roll=? ";
						PreparedStatement pst = connection.prepareStatement(query1);
						pst.setString(1, textField1.getText());
						pst.setString(2, textField2.getText());
						pst.setString(3, t);
						pst.setString(4, textField.getText());
						pst.execute();
						textField1.setText("");
						textField2.setText("");
						withdraw1.setEnabled(true);
						textField1.setEnabled(false);
						textField2.setEnabled(false);
					}
					if(textField3.getText().length()>0) {
						Calendar c=new GregorianCalendar();
						int d=c.get(Calendar.DAY_OF_MONTH);
						int m=c.get(Calendar.MONTH);
						int y=c.get(Calendar.YEAR);
						m++;
						String day=Integer.toString(d);
						String mon=Integer.toString(m);
						String yr=Integer.toString(y);
						String t=day+"/"+mon+"/"+yr;
						String query2="update ses"+combo+" set Book2=?,Book2_no=?,Book2_Date=? where Roll=? ";
						PreparedStatement pst = connection.prepareStatement(query2);
						pst.setString(1, textField3.getText());
						pst.setString(2, textField4.getText());
						pst.setString(3, t);
						pst.setString(4, textField.getText());
						pst.execute();
						textField3.setText("");
						textField4.setText("");
						withdraw2.setEnabled(true);
						textField3.setEnabled(false);
						textField4.setEditable(false);
					}
					refresh();
					
				}catch(Exception ca) {
					JOptionPane.showMessageDialog(null, ca);
				}
			}
		});
		ok.setEnabled(false);
		ok.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 16));
		ok.setForeground(Color.BLACK);
		ok.setBounds(68, 375, 114, 25);
		contentPane.add(ok);
		
		withdraw1 = new JButton("Book1 Withdraw");
		withdraw1.setEnabled(false);
		withdraw1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int action=JOptionPane.showConfirmDialog(null, "Do you really want to withdraw book1 ?","Withdraw Book1",JOptionPane.YES_NO_OPTION);
					if(action==0) {
						String query="update ses"+combo+" set Book1=?,Book1_no=?,Book1_Date=? where Roll="+textField.getText()+" ";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, "-");
						pst.setString(2, "-");
						pst.setString(3, "-");
						pst.execute();
						refresh();
						JOptionPane.showMessageDialog(null, "Book1 withdrawn");
						textField1.setEnabled(true);
						textField2.setEnabled(true);
						ok.setEnabled(true);
						withdraw1.setEnabled(false);
					}
					
				}catch(Exception ca) {
					JOptionPane.showMessageDialog(null, ca);
				}
			}
		});
		withdraw1.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		withdraw1.setBounds(12, 412, 233, 25);
		contentPane.add(withdraw1);
		
		withdraw2 = new JButton("Book2 Withdraw");
		withdraw2.setEnabled(false);
		withdraw2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int action=JOptionPane.showConfirmDialog(null, "Do you really want to withdraw book2 ?","Withdraw Book2",JOptionPane.YES_NO_OPTION);
					if(action==0) {
						String query="update ses"+combo+" set Book2=?,Book2_no=?,Book2_Date=? where Roll="+textField.getText()+" ";
						PreparedStatement pst = connection.prepareStatement(query);
						pst.setString(1, "-");
						pst.setString(2, "-");
						pst.setString(3, "-");
						pst.execute();
						refresh();
						JOptionPane.showMessageDialog(null, "Book2 withdrawn");
						textField3.setEnabled(true);
						textField4.setEnabled(true);
						ok.setEnabled(true);
						withdraw2.setEnabled(false);
					}
					
				}catch(Exception ca) {
					JOptionPane.showMessageDialog(null, ca);
				}
			}
		});
		withdraw2.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		withdraw2.setBounds(12, 439, 233, 25);
		contentPane.add(withdraw2);
		
		JLabel lblNewLabel_3 = new JLabel("Book1 No");
		lblNewLabel_3.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_3.setForeground(Color.BLACK);
		lblNewLabel_3.setBounds(12, 241, 77, 17);
		contentPane.add(lblNewLabel_3);
		
		textField2 = new JTextField();
		textField2.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		textField2.setEnabled(false);
		textField2.setBounds(94, 238, 124, 24);
		contentPane.add(textField2);
		textField2.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Book2 No");
		lblNewLabel_4.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_4.setForeground(Color.BLACK);
		lblNewLabel_4.setBounds(12, 346, 77, 17);
		contentPane.add(lblNewLabel_4);
		
		textField4 = new JTextField();
		textField4.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		textField4.setEnabled(false);
		textField4.setBounds(94, 339, 124, 24);
		contentPane.add(textField4);
		textField4.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("L.N. Mishra College Of Business Management,Bhagwanpur Muzaffarpur-842001");
		lblNewLabel_5.setBackground(new Color(0, 0, 0));
		lblNewLabel_5.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 22));
		lblNewLabel_5.setForeground(new Color(165, 42, 42));
		lblNewLabel_5.setBounds(96, -3, 1001, 44);
		contentPane.add(lblNewLabel_5);
		
		JLabel logo = new JLabel("");
		logo.setFont(new Font("Dialog", Font.PLAIN, 10));
		Image img = new ImageIcon(this.getClass().getResource("/lnmcbm.png")).getImage();
		logo.setIcon(new ImageIcon(img));
		//frame.getContentPane().add(img);
		logo.setBounds(47, 3, 75, 41);
		contentPane.add(logo);
		
		lblclock = new JLabel("clock");
		lblclock.setForeground(Color.ORANGE);
		lblclock.setHorizontalAlignment(SwingConstants.CENTER);
		lblclock.setFont(new Font("Dialog", Font.BOLD, 16));
		lblclock.setBounds(825, 52, 298, 27);
		contentPane.add(lblclock);
		
		JLabel logout = new JLabel("");
		logout.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int action=JOptionPane.showConfirmDialog(null, "Do you really want to Logout ?","Logout",JOptionPane.YES_NO_OPTION);
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
		logout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int action=JOptionPane.showConfirmDialog(null, "Do you really want to Logout ?","Logout",JOptionPane.YES_NO_OPTION);
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
		logout.setBounds(1136, 26, 58, 48);
		Image logimg = new ImageIcon(this.getClass().getResource("/logout.png")).getImage();
		logout.setIcon(new ImageIcon(logimg));
		contentPane.add(logout);
		clock();
	}
}
