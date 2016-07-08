package cn.edu.nuc.onlinestore.frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import cn.edu.nuc.onlinestore.io.ObjectStream;
import cn.edu.nuc.onlinestore.model.Goods;
import cn.edu.nuc.onlinestore.model.GoodsIdFetch;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminAdd extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private AdminAdd aa=null;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public AdminAdd(final DefaultTableModel dtm) {
		this.aa=this;
		setTitle("添加商品");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("商品名称:");
		label.setBounds(35, 57, 67, 15);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(99, 54, 269, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel label_1 = new JLabel("单价:");
		label_1.setBounds(35, 100, 54, 15);
		contentPane.add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(99, 97, 212, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel label_2 = new JLabel("单位:元");
		label_2.setBounds(321, 100, 54, 15);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("数量:");
		label_3.setBounds(35, 143, 54, 15);
		contentPane.add(label_3);
		
		textField_2 = new JTextField();
		textField_2.setBounds(99, 140, 269, 21);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel label_4 = new JLabel("简介:");
		label_4.setBounds(35, 188, 54, 15);
		contentPane.add(label_4);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(99, 184, 269, 103);
		contentPane.add(textArea);
		
		JButton button = new JButton("确定添加");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ObjectStream.read(SelfIncreaseId.class,"/goodsId/"+"id.txt"); 
				List list1=new ArrayList();
				Goods goods1=new Goods(	textField.getText());
				File file=new File(getGoodsPath(textField.getText()));
				if(!file.exists()){
					if( textField.getText().equals("")  ){
						JOptionPane joption = new JOptionPane();
						joption.showMessageDialog(null, "商品名称不能为空");
					}else if( textField_1.getText().equals("")){
						JOptionPane joption = new JOptionPane();
						joption.showMessageDialog(null, "单价不能为空");
					}else if( textField_2.getText().equals("") ){
						JOptionPane joption = new JOptionPane();
						joption.showMessageDialog(null, "数量不能为空");
					}else{
						GoodsIdFetch gif=new GoodsIdFetch();
						Goods goods2=new Goods(gif.getGoodId(),textField.getText(),textField_1.getText(),textField_2.getText(),textArea.getText());
						Object obj=ObjectStream.write("/goods/"+textField.getText()+".txt", goods2);
						JOptionPane.showMessageDialog(null, "添加商品成功！");
						list1.add(new String(getGoodsPath1(textField.getText())));
						for(Iterator i=list1.iterator();i.hasNext();){
							String s1=(String)i.next();
							Goods goods3=ObjectStream.read(Goods.class, s1);
							dtm.addRow(new String[]{Integer.toString(goods3.getGId()),goods3.getGName(),goods3.getGPrice(),goods3.getGCount()});
							AdminStore as = new AdminStore(aa);
							as.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
							as.setVisible(false);
						}
					}			
				}else{
					JOptionPane.showMessageDialog(null, "该商品已存在，请您重新添加商品！");
					textField.setText(null);
					textField_1.setText(null);
					textField_2.setText(null);
					textArea.setText(null);
				}
			}
		});
		button.setBounds(275, 310, 93, 23);
		contentPane.add(button);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				((AdminAdd)e.getSource()).setVisible(false);
			}
		});
	}
	public static String getGoodsPath( String gName){
		return "d:/store/goods/" + gName + ".txt";
	}
	public static String getGoodsPath1( String gName){
		return "/goods/" + gName + ".txt";
	}
}
