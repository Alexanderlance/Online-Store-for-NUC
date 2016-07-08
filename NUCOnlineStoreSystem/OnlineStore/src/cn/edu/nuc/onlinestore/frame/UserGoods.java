package cn.edu.nuc.onlinestore.frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import cn.edu.nuc.onlinestore.io.ObjectStream;
import cn.edu.nuc.onlinestore.model.Cart;
import cn.edu.nuc.onlinestore.model.Goods;
import cn.edu.nuc.onlinestore.network.TCPClient;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserGoods extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private UserGoods ug=null;

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public UserGoods(Goods goods,DefaultTableModel dtm,JTable table) {
		this.ug=this;
		setTitle("商品详情");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("商品名称:");
		label.setBounds(23, 57, 66, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("单价:");
		label_1.setBounds(35, 100, 54, 15);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("单位:元");
		label_2.setBounds(321, 100, 54, 15);
		contentPane.add(label_2);
		
		JLabel label_4 = new JLabel("简介:");
		label_4.setBounds(35, 141, 44, 46);
		contentPane.add(label_4);
		
		JLabel l1 = new JLabel(goods.getGName());
		l1.setBounds(99, 57, 162, 15);
		contentPane.add(l1);
		
		JLabel l2 = new JLabel(goods.getGPrice());
		l2.setBounds(99, 100, 54, 15);
		contentPane.add(l2);
		
		JLabel l3 = new JLabel(goods.getGIntroduce());
		l3.setBounds(99, 141, 251, 46);
		contentPane.add(l3);
		
		textField = new JTextField();
		textField.setText("1");
		textField.setBounds(99, 244, 66, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("购买数量:");
		lblNewLabel_2.setBounds(35, 247, 71, 15);
		contentPane.add(lblNewLabel_2);
		
		JLabel l4 = new JLabel(goods.getGCount());
		l4.setBounds(140, 276, 54, 15);
		contentPane.add(l4);
		
		JLabel lblNewLabel = new JLabel("库存：");
		lblNewLabel.setBounds(99, 276, 44, 15);
		contentPane.add(lblNewLabel);
		JButton button = new JButton("加入购物车");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg="addcart@@";
				String result=new TCPClient().send(msg);
				if("addgoods".equals(result)){
					if(Integer.parseInt(textField.getText())>Integer.parseInt(l4.getText())){
						JOptionPane.showMessageDialog(null, "库存不足！请您重新输入要购买此商品的数量！");
						textField.setText(null);
					}else{
						Goods goods=ObjectStream.read(Goods.class, "/goods/"+l1.getText()+".txt");
						Cart c=new Cart();
						int i=c.add(goods,Integer.parseInt(textField.getText()));
						UserStore us=new UserStore(ug,i);
						us.setVisible(true);
					}
				}
			}
		});
		button.setBounds(175, 243, 126, 23);
		contentPane.add(button);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				((UserGoods)e.getSource()).setVisible(false);
			}
		});
	}
}
