package cn.edu.nuc.onlinestore.frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import cn.edu.nuc.onlinestore.io.ObjectStream;
import cn.edu.nuc.onlinestore.model.Goods;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class AdminUpdate extends JFrame {

	/**
	 * 
	 */
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private AdminUpdate au=null;

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public AdminUpdate(Goods goods,DefaultTableModel dtm,JTable table) {
		this.au=this;
		setTitle("修改商品");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		JLabel label = new JLabel("商品名称:");
		textField.setText(goods.getGName());
		label.setBounds(23, 57, 66, 15);
		contentPane.add(label);
		
		textField.setBounds(99, 54, 269, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		JLabel label_1 = new JLabel("单价:");
		textField_1.setText(goods.getGPrice());
		label_1.setBounds(35, 100, 54, 15);
		contentPane.add(label_1);
		
		
		textField_1.setBounds(99, 97, 212, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel label_2 = new JLabel("单位:元");
		label_2.setBounds(321, 100, 54, 15);
		contentPane.add(label_2);
		
		textField_2 = new JTextField();
		JLabel label_3 = new JLabel("数量:");
		textField_2.setText(goods.getGCount());
		label_3.setBounds(35, 143, 54, 15);
		contentPane.add(label_3);
		
		
		textField_2.setBounds(99, 140, 212, 21);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel label_4 = new JLabel("简介:");
		label_4.setBounds(35, 188, 54, 15);
		contentPane.add(label_4);
		
		JTextArea textArea = new JTextArea();
		textArea.setText(goods.getGIntroduce());
		textArea.setBounds(99, 184, 269, 103);
		contentPane.add(textArea);
		
		JButton button = new JButton("确定修改");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
					List list1=new ArrayList();
					Goods goods1=new Goods(goods.getGId(),textField.getText(),textField_1.getText(),textField_2.getText(),textArea.getText());
					//应先删除原来文件，再把新文件进行序列化（写入操作）
					JOptionPane.showMessageDialog(null, "修改商品信息成功！");
					File file = new File("d:/store/goods/" +goods.getGName()+".txt");
					file.delete();
					//清除表格数据
					dtm.getDataVector().clear();   //清除表格数据
					dtm.fireTableDataChanged();//通知模型更新
					table.updateUI();
					ObjectStream.write("/goods/"+textField.getText()+".txt", goods1);
					//遍历并显示删除更新后的内容
					File f=new File("d:/store/goods/");
					String[] s=f.list();
					for(int i=0;i<s.length;i++){
						Goods goods=ObjectStream.read(Goods.class,"/goods/"+s[i] );
						dtm.addRow(new String[]{Integer.toString(goods.getGId()),goods.getGName(),goods.getGPrice(),goods.getGCount()});
					}
					list1.add(new String(new String(getGoodsPath2(textField.getText()))));
					/*for(Iterator i=list1.iterator();i.hasNext();){
						String s1=(String)i.next();
						Goods goods2=ObjectStream.read(Goods.class, s1);
						dtm.addRow(new String[]{Integer.toString(goods2.getGId()),goods2.getGName(),goods2.getGPrice(),goods2.getGCount()});
						AdminStore as = new AdminStore(au);
						as.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						as.setVisible(false);
					}*/
					AdminStore as = new AdminStore(au);
					as.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					as.setVisible(false);
				}
			}
		});
		
		button.setBounds(275, 310, 93, 23);
		contentPane.add(button);
		
		JLabel label_5 = new JLabel(">=0");
		label_5.setBounds(321, 143, 43, 15);
		contentPane.add(label_5);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				((AdminUpdate)e.getSource()).setVisible(false);
			}
		});
	}
	public static String getGoodsPath2( String gName){
		return "/goods/" + gName + ".txt";
	}
}
