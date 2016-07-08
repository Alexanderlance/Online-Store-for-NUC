package cn.edu.nuc.onlinestore.frame;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import cn.edu.nuc.onlinestore.io.ObjectStream;
import cn.edu.nuc.onlinestore.model.Guest;
import cn.edu.nuc.onlinestore.network.TCPClient;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserLogin extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private UserLogin ul=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserLogin frame = new UserLogin();
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
	public UserLogin() {
		this.ul=this;
		setTitle("中北线在商场-登录");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 461, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("用户名:");
		label.setBounds(87, 146, 54, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("密  码:");
		label_1.setBounds(87, 185, 54, 15);
		contentPane.add(label_1);
		
		textField = new JTextField();
		textField.setBounds(151, 146, 197, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(151, 185, 197, 21);
		contentPane.add(passwordField);
		
		JButton button = new JButton("登录系统");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username=textField.getText();
				String password=new String(passwordField.getPassword());
				String msg="login@@"+username+"@@"+password;
				String result=new TCPClient().send(msg);
				if("NoUsername".equals(result)){
					JOptionPane.showMessageDialog(null, "用户名不能为空！");
					textField.setText(null);
					passwordField.setText(null);
				}else if("NoPassword".equals(result)){
					JOptionPane.showMessageDialog(null, "密码不能为空！");
					textField.setText(null);
					passwordField.setText(null);
				}else if("NoUserPass".equals(result)){
					JOptionPane.showMessageDialog(null, "用户名和密码不能为空！！");
					textField.setText(null);
					passwordField.setText(null);
				}else{
					if("NoRegistInfo".equals(result)){
						JOptionPane.showMessageDialog(null, "您还没有注册，请您注册后登录！");
						UserRegist ur=new UserRegist(ul);
						ur.setVisible(true);
					}else if("LoginCheck".equals(result)){
						Guest g1=ObjectStream.read(Guest.class, "/user/"+username+".txt");
						if(password.equals(g1.getGPassword())){
							UserStore us=new UserStore(ul);
							us.setVisible(true);
						}else{
							JOptionPane.showMessageDialog(null, "您输入的用户名账号不符，请您重新输入！");
							textField.setText(null);
							passwordField.setText(null);
						}
					}
				}
			}
		});			
		button.setBounds(255, 216, 93, 23);
		contentPane.add(button);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(UserLogin.class.getResource("/image/admin.jpg")));
		lblNewLabel.setBounds(208, 54, 86, 82);
		contentPane.add(lblNewLabel);
}

	//注册页注册成功后返回登录页
	public UserLogin(UserRegist ur) {
		this.ul=this;
		ur.setVisible(false);
		setTitle("中北线在商场-登录");
		setBounds(100, 100, 461, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("用户名:");
		label.setBounds(87, 146, 54, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("密  码:");
		label_1.setBounds(87, 185, 54, 15);
		contentPane.add(label_1);
		
		textField = new JTextField();
		textField.setBounds(151, 146, 197, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(151, 185, 197, 21);
		contentPane.add(passwordField);
		
		JButton button = new JButton("登录系统");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username=textField.getText();
				String password=new String(passwordField.getPassword());
				String msg="login@@"+username+"@@"+password;
				String result=new TCPClient().send(msg);
				if("NoUsername".equals(result)){
					JOptionPane.showMessageDialog(null, "用户名不能为空！");
					textField.setText(null);
					passwordField.setText(null);
				}else if("NoPassword".equals(result)){
					JOptionPane.showMessageDialog(null, "密码不能为空！");
					textField.setText(null);
					passwordField.setText(null);
				}else if("NoUserPass".equals(result)){
					JOptionPane.showMessageDialog(null, "用户名和密码不能为空！！");
					textField.setText(null);
					passwordField.setText(null);
				}else{
					if("NoRegistInfo".equals(result)){
						JOptionPane.showMessageDialog(null, "您还没有注册，请您注册后登录！");
						UserRegist ur=new UserRegist(ul);
						ur.setVisible(true);
					}else if("LoginCheck".equals(result)){
						Guest g1=ObjectStream.read(Guest.class, "/user/"+username+".txt");
						if(password.equals(g1.getGPassword())){
							UserStore us=new UserStore(ul);
							us.setVisible(true);
						}else{
							JOptionPane.showMessageDialog(null, "您输入的用户名账号不符，请您重新输入！");
							textField.setText(null);
							passwordField.setText(null);
						}
					}
				}
			}
		});			
		button.setBounds(255, 216, 93, 23);
		contentPane.add(button);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(UserLogin.class.getResource("/image/admin.jpg")));
		lblNewLabel.setBounds(208, 54, 86, 82);
		contentPane.add(lblNewLabel);
	}
	//点击退出系统按钮时返回登录页
		public UserLogin(UserStore us) {
			this.ul=this;
			us.setVisible(false);
			setTitle("中北线在商场-登录");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 461, 349);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel label = new JLabel("用户名:");
			label.setBounds(87, 146, 54, 15);
			contentPane.add(label);
			
			JLabel label_1 = new JLabel("密  码:");
			label_1.setBounds(87, 185, 54, 15);
			contentPane.add(label_1);
			
			textField = new JTextField();
			textField.setBounds(151, 146, 197, 21);
			contentPane.add(textField);
			textField.setColumns(10);
			
			passwordField = new JPasswordField();
			passwordField.setBounds(151, 185, 197, 21);
			contentPane.add(passwordField);
			
			JButton button = new JButton("登录系统");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String username=textField.getText();
					String password=new String(passwordField.getPassword());
					String msg="login@@"+username+"@@"+password;
					String result=new TCPClient().send(msg);
					if("NoUsername".equals(result)){
						JOptionPane.showMessageDialog(null, "用户名不能为空！");
						textField.setText(null);
						passwordField.setText(null);
					}else if("NoPassword".equals(result)){
						JOptionPane.showMessageDialog(null, "密码不能为空！");
						textField.setText(null);
						passwordField.setText(null);
					}else if("NoUserPass".equals(result)){
						JOptionPane.showMessageDialog(null, "用户名和密码不能为空！！");
						textField.setText(null);
						passwordField.setText(null);
					}else{
						if("NoRegistInfo".equals(result)){
							JOptionPane.showMessageDialog(null, "您还没有注册，请您注册后登录！");
							UserRegist ur=new UserRegist(ul);
							ur.setVisible(true);
						}else if("LoginCheck".equals(result)){
							Guest g1=ObjectStream.read(Guest.class, "/user/"+username+".txt");
							if(password.equals(g1.getGPassword())){
								UserStore us=new UserStore(ul);
								us.setVisible(true);
							}else{
								JOptionPane.showMessageDialog(null, "您输入的用户名账号不符，请您重新输入！");
								textField.setText(null);
								passwordField.setText(null);
							}
						}
					}
				}
			});			
			button.setBounds(255, 216, 93, 23);
			contentPane.add(button);
			
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(UserLogin.class.getResource("/image/admin.jpg")));
			lblNewLabel.setBounds(208, 54, 86, 82);
			contentPane.add(lblNewLabel);
		}
}
