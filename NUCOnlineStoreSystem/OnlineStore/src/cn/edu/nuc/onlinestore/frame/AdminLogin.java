package cn.edu.nuc.onlinestore.frame;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import cn.edu.nuc.onlinestore.io.ObjectStream;
import cn.edu.nuc.onlinestore.model.Administrator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class AdminLogin extends JFrame {

	/**
	 * 
	 */

	/**
	 * 
	 */
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private  AdminLogin jframe=null;
	private String username="ysn";
	private String password="123456";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminLogin frame = new AdminLogin();
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
	public AdminLogin(){
		this.jframe=this;
		setTitle("中北线在商场管理系统-管理员登录");
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
		//管理员登录验证
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//简单验证
				/*if((!"ysn".equals(textField.getText()))&&("123456".equals(passwordField.getText()))){
					JOptionPane.showMessageDialog(null,"用户名输入错误或不存在！请您重新输入");
					textField.setText(null);
					passwordField.setText(null);
				}else if(("ysn".equals(textField.getText()))&&(!"123456".equals(passwordField.getText()))){
					JOptionPane.showMessageDialog(null,"密码输入错误！请您重新输入");
					textField.setText(null);
					passwordField.setText(null);
				}else if((!"ysn".equals(textField.getText()))||(!"123456".equals(passwordField.getText()))){
					JOptionPane.showMessageDialog(null,"您输入的用户名或密码错误，请您重新输入");
					textField.setText(null);
					passwordField.setText(null);
				}else if(("ysn".equals(textField.getText()))&&("123456".equals(passwordField.getText()))){
					AdminStore as=new AdminStore(jframe);
					as.setVisible(true);
				}*/
				//Administrator a1=new Administrator(textField.setText("ysn"),passwordField.setText("123456"));
				//本地文件验证
				Administrator a1=new Administrator(username,password);
				File file=new File(getAdminInfo(username));
				if(!file.exists()){
					ObjectStream.write("/admin/"+username+".txt", a1);
				}
//				Administrator a2=new Administrator(textField.getText(),new String(passwordField.getPassword()));
				File file1=new File(getAdminInfo(textField.getText()));
				if(file1.exists()){
					Administrator a3=ObjectStream.read(Administrator.class,"/admin/"+ textField.getText()+".txt");
					if(a3.getAUsername().equals(textField.getText())){
						if(a3.getAPassword().equals(new String(passwordField.getPassword()))){
							AdminStore as=new AdminStore(jframe);
							as.setVisible(true);
						}else{
							JOptionPane.showMessageDialog(null,"您输入的用户名或密码错误，请您重新输入");
							textField.setText(null);
							passwordField.setText(null);
						}
					}else{
							JOptionPane.showMessageDialog(null,"您输入的用户名或密码错误，请您重新输入");
							textField.setText(null);
							passwordField.setText(null);
					}
				}else{
							JOptionPane.showMessageDialog(null,"您输入的用户名或密码错误，请您重新输入");
							textField.setText(null);
							passwordField.setText(null);
				}
			}
		});
		
		button.setBounds(255, 216, 93, 23);
		contentPane.add(button);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(AdminLogin.class.getResource("/image/admin.jpg")));
		lblNewLabel.setBounds(208, 54, 86, 82);
		contentPane.add(lblNewLabel);
	
	}
	public static String getAdminInfo( String username){
		return "d:/store/admin/" +username+ ".txt";
	}
	//点AdminStore的退出系统时所执行的方法
	@SuppressWarnings("deprecation")
	public AdminLogin(AdminStore as) {
		this.jframe=this;
		as.setVisible(false);
		setTitle("中北线在商场管理系统-管理员登录");
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
				//本地文件验证
				Administrator a1=new Administrator(username,password);
				File file=new File(getAdminInfo(username));
				if(!file.exists()){
					ObjectStream.write("/"+username+".txt", a1);
				}
				Administrator a2=new Administrator(textField.getText(),new String(passwordField.getPassword()));
				File file1=new File(getAdminInfo(textField.getText()));
				if(file1.exists()){
					Administrator a3=ObjectStream.read(Administrator.class,"/admin/"+ textField.getText()+".txt");
					if(a3.getAUsername().equals(textField.getText())){
						if(a3.getAPassword().equals(new String(passwordField.getPassword()))){
							AdminStore as=new AdminStore(jframe );
							as.setVisible(true);
						}else{
							JOptionPane.showMessageDialog(null,"您输入的用户名或密码错误，请您重新输入");
							textField.setText(null);
							passwordField.setText(null);
						}
					}else{
							JOptionPane.showMessageDialog(null,"您输入的用户名或密码错误，请您重新输入");
							textField.setText(null);
							passwordField.setText(null);
					}
				}else{
							JOptionPane.showMessageDialog(null,"您输入的用户名或密码错误，请您重新输入");
							textField.setText(null);
							passwordField.setText(null);
				}
			}
		});
		
		button.setBounds(255, 216, 93, 23);
		contentPane.add(button);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(AdminLogin.class.getResource("/image/admin.jpg")));
		lblNewLabel.setBounds(208, 54, 86, 82);
		contentPane.add(lblNewLabel);
	}
}
