package cn.edu.nuc.onlinestore.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import cn.edu.nuc.onlinestore.io.ObjectStream;
import cn.edu.nuc.onlinestore.model.Guest;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class UserRegist extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private UserRegist ur=null;

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public UserRegist(UserLogin ul) {
		ul.setVisible(false);
		this.ur=this;
		setTitle("中北在线商场管理系统-用户注册");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel l1 = new JLabel("用户名：");
		l1.setBounds(72, 63, 54, 15);
		contentPane.add(l1);
		
		textField = new JTextField();
		textField.setBounds(124, 60, 232, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel l2 = new JLabel("密  码：");
		l2.setBounds(72, 131, 54, 15);
		contentPane.add(l2);
		
		JButton jbt = new JButton("注册");
		jbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((textField.getText().equals(""))&&(!new String(passwordField.getPassword()).equals(""))){
					JOptionPane.showMessageDialog(null,"用户名不能为空！");
					textField.setText(null);
					passwordField.setText(null);
				}else if((!textField.getText().equals(""))&&(new String(passwordField.getPassword()).equals(""))){
					JOptionPane.showMessageDialog(null,"密码不能为空！");
					textField.setText(null);
					passwordField.setText(null);
				}else if((textField.getText().equals(""))&&(new String(passwordField.getPassword()).equals(""))){
					JOptionPane.showMessageDialog(null,"用户名和密码不能为空！");
					textField.setText(null);
					passwordField.setText(null);
				}else{
					Guest g1=new Guest(textField.getText(),new String(passwordField.getPassword()));
					ObjectStream.write("/user/"+textField.getText()+".txt", g1);
					JOptionPane.showMessageDialog(null,"注册成功！");
					UserLogin ul=new UserLogin(ur);
					ul.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					ul.setVisible(true);
				}
			}
		});
		jbt.setBounds(263, 195, 93, 23);
		contentPane.add(jbt);
		
		JButton jbt1 = new JButton("返回");
		jbt1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserLogin ul=new UserLogin(ur);
				ul.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				ul.setVisible(true);
			}
		});
		jbt1.setBounds(124, 195, 93, 23);
		contentPane.add(jbt1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(124, 129, 232, 21);
		contentPane.add(passwordField);
	}
	public static String getUserPath( String username){
		return "d:/store/user/" + username + ".txt";
	}
}
