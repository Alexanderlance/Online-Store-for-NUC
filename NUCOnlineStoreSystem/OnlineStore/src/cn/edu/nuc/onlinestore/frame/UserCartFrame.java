package cn.edu.nuc.onlinestore.frame;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import cn.edu.nuc.onlinestore.io.ObjectStream;
import cn.edu.nuc.onlinestore.model.Goods;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserCartFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private String username;
	private JLabel label2;


	/**
	 * Create the frame.
	 * @param username 
	 * @param label2 
	 */
	public UserCartFrame(int n) {
		this.username=username;
		this.label2=label2;
		
		setTitle("购物车详情");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 591, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 78, 540, 200);
		panel.setLayout(new GridLayout(1, 1, 0, 0));
		
		final JLabel lblNewLabel = new JLabel("总金额: ");
		lblNewLabel.setBounds(300, 290, 211, 15);
		contentPane.add(lblNewLabel);
		
		JButton button = new JButton("结账");
		button.setBounds(456, 283, 93, 23);
		contentPane.add(button);
		
		final JLabel label = new JLabel("总商品数量: ");
		label.setBounds(194, 290, 116, 15);
		contentPane.add(label);
		
		
		final DefaultTableModel model = new DefaultTableModel();
		model.addColumn("商品编号");
		model.addColumn("名称");
		model.addColumn("单价(￥)");
		model.addColumn("数量");
		model.addColumn("库存");
		table = new JTable( model );
		table.setBounds(10, 27, 543, 180);
		File f=new File("d:/store/mapgoods/");
		String[] s=f.list();
		for(int i=0;i<s.length;i++){
			Goods goods=ObjectStream.read(Goods.class,"/mapgoods/"+s[i] );
			model.addRow(new String[]{Integer.toString(goods.getGId()),goods.getGName(),goods.getGPrice(),Integer.toString(goods.getGQuantity()),goods.getGCount()});
		}
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f=new File("d:/store/mapgoods/");
				String[] s=f.list();
				if(s.length==0){
					JOptionPane.showMessageDialog(null, "购物车已清空，没有要购买的商品！");
				}else{
					//清除表格数据
					model.getDataVector().clear();   //清除表格数据
					model.fireTableDataChanged();//通知模型更新
					table.updateUI();
					for(int i=0;i<s.length;i++){
						Goods goods=ObjectStream.read(Goods.class,"/mapgoods/"+s[i] );
	//					model.addRow(new String[]{Integer.toString(goods.getGId()),goods.getGName(),goods.getGPrice(),Integer.toString(goods.getGQuantity()),goods.getGCount()});
						int t=Integer.parseInt(goods.getGCount())-goods.getGQuantity();
						model.addRow(new String[]{Integer.toString(goods.getGId()),goods.getGName(),goods.getGPrice(),Integer.toString(goods.getGQuantity()),Integer.toString(t)});
					}
				}
			}
		});
		JScrollPane pane = new JScrollPane( table );
		panel.add(pane);
		contentPane.add( panel );
		
		JButton button_1 = new JButton("清空购物车");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f=new File("d:/store/mapgoods/");
				String[] s=f.list();
				for(int i=0;i<s.length;i++){
					File file = new File("d:/store/mapgoods/" +s[i]);
					file.delete();
				}
				//清除表格数据
				model.getDataVector().clear();   //清除表格数据
				model.fireTableDataChanged();//通知模型更新
				table.updateUI();
				/*File file = new File("d:/store/mapgoods/" ++".txt");
				file.delete();*/
			}
		});

		button_1.setBounds(23, 43, 129, 23);
		contentPane.add(button_1);
	}
}
