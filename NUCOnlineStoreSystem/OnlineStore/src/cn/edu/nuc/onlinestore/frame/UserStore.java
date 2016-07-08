package cn.edu.nuc.onlinestore.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import cn.edu.nuc.onlinestore.io.ObjectStream;
import cn.edu.nuc.onlinestore.model.AdminSearch;
import cn.edu.nuc.onlinestore.model.Cart;
import cn.edu.nuc.onlinestore.model.Goods;
import cn.edu.nuc.onlinestore.network.TCPClient;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class UserStore extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private UserStore us=null;
	private UserLogin ul1=null;
//	private Cart cart = new Cart();

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 * @wbp.parser.constructor
	 */
	public UserStore(UserLogin ul) {
		ul.setVisible(false);
		this.us=this;
		setTitle("中北在线商场");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 732, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 78, 696, 341);
		panel.setLayout(new GridLayout(1, 1, 0, 0));
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("商品编号");
		model.addColumn("名称");
		model.addColumn("单价(人民币)");
		model.addColumn("库存");
		String msg="list@@";
		String result=new TCPClient().send(msg);
		if("ListAllGoodsInfo".equals(result)){
			File f=new File("d:/store/goods/");
			String[] s=f.list();
			for(int i=0;i<s.length;i++){
				Goods goods=ObjectStream.read(Goods.class,"/goods/"+s[i] );
				model.addRow(new String[]{Integer.toString(goods.getGId()),goods.getGName(),goods.getGPrice(),goods.getGCount()});
			}
		}
		JTable table = new JTable( model );
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //单选
		Goods goods = new Goods();
		//int selectedRow = table.getSelectedRow(); //获得选中行索引
        table.addMouseListener(new MouseAdapter(){    //设置鼠标事件
            public void mouseClicked(MouseEvent e){
            	int selectedRow = table.getSelectedRow(); //获得选中行索引
	                String id = (String)model.getValueAt(selectedRow, 0);
	                String name = (String)model.getValueAt(selectedRow, 1);
	                String price = (String)model.getValueAt(selectedRow, 2);
	                String count = (String)model.getValueAt(selectedRow, 3);
	                Goods	goods1= ObjectStream.read(Goods.class, "/goods/"+name+".txt");
	                goods.setGId(Integer.parseInt(id));
	                goods.setGname(name);
	                goods.setGPrice(price);
	                goods.setGCount(count);
	                goods.setGIntroduce(goods1.getGIntroduce());         
                }
        });
		JScrollPane pane = new JScrollPane( table );
		panel.add(pane);
		contentPane.add(panel);
		
		JButton button_2 = new JButton("查看商品详细信息");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg="searchgoodsall@@";
				String result=new TCPClient().send(msg);
				if("ShowGoodsText".equals(result)){
					int row = table.getSelectedRow();
					if(row == -1){
						JOptionPane.showMessageDialog(null, "没有选中任何商品！");
					}else{
						UserGoods d = new UserGoods(goods,model,table);
						d.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						d.setVisible(true);
					}
				}
			}
		});
		button_2.setBounds(407, 45, 299, 23);
		contentPane.add(button_2);
		
		JButton button_3 = new JButton("退出登录");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserLogin ul=new UserLogin(us);
				ul.setVisible(true);
			}
		});
		button_3.setBounds(613, 6, 93, 23);
		contentPane.add(button_3);
		
		JLabel lblid = new JLabel("商品名称:");
		lblid.setBounds(10, 53, 65, 15);
		contentPane.add(lblid);
		
		textField = new JTextField();
		textField.setBounds(85, 46, 104, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton button_4 = new JButton("搜索");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					String msg="search@@"+textField.getText();
					String result=new TCPClient().send(msg);
				if("NoSearchInfo".equals(result)){
					JOptionPane.showMessageDialog(null, "输入的商品名称不能为空！");
					textField.setText(null);
				}else if("ShowSearchInfo".equals(result)){
					Goods goods1=null;
					if((goods1=(new AdminSearch(textField.getText()).findByName()))==null){
						JOptionPane jOptionPan=new JOptionPane();
						jOptionPan.showMessageDialog(null, "您要找的商品不存在");
					}else{
						//清除表格内容
						model.getDataVector().clear();   //清除表格数据
						 model.fireTableDataChanged();//通知模型更新
						table.updateUI();
						model.addRow(new String[]{Integer.toString(goods1.getGId()),goods1.getGName(),goods1.getGPrice(),goods1.getGCount()});
					}
				}
			}
		});
		button_4.setBounds(197, 45, 93, 23);
		contentPane.add(button_4);
		
		JLabel label = new JLabel("购物车:");
		label.setBounds(10, 10, 42, 15);
		contentPane.add(label);
		
		JButton button_5 = new JButton("查看购物车");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*UserCartFrame cf = new UserCartFrame();
				cf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				cf.setVisible(true);*/
				JOptionPane.showMessageDialog(null, "购物车商品为空，请先添加购物车！");
			}
		});
		button_5.setBounds(197, 6, 110, 23);
		contentPane.add(button_5);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(52, 10, 54, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("件商品");
		lblNewLabel_1.setBounds(116, 10, 73, 15);
		contentPane.add(lblNewLabel_1);
	}
	//点击添加购物车按钮跳到的页面
	public UserStore(UserGoods ug,int h) {
		/*UserStore us1=new UserStore(ul1);
		us1.setVisible(false);*/
		ug.setVisible(false);
		this.us=this;
		setTitle("中北在线商场--当前用户:李四");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 732, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 78, 696, 341);
		panel.setLayout(new GridLayout(1, 1, 0, 0));
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("商品编号");
		model.addColumn("名称");
		model.addColumn("单价(人民币)");
		model.addColumn("库存");
		String msg="list@@";
		String result=new TCPClient().send(msg);
		if("ListAllGoodsInfo".equals(result)){
			File f=new File("d:/store/goods/");
			String[] s=f.list();
			for(int i=0;i<s.length;i++){
				Goods goods=ObjectStream.read(Goods.class,"/goods/"+s[i] );
				model.addRow(new String[]{Integer.toString(goods.getGId()),goods.getGName(),goods.getGPrice(),goods.getGCount()});
			}
		}
		JTable table = new JTable( model );
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  //单选
		Goods goods = new Goods();
		//int selectedRow = table.getSelectedRow(); //获得选中行索引
        table.addMouseListener(new MouseAdapter(){    //设置鼠标事件
            public void mouseClicked(MouseEvent e){
            	int selectedRow = table.getSelectedRow(); //获得选中行索引
	                String id = (String)model.getValueAt(selectedRow, 0);
	                String name = (String)model.getValueAt(selectedRow, 1);
	                String price = (String)model.getValueAt(selectedRow, 2);
	                String count = (String)model.getValueAt(selectedRow, 3);
	                Goods	goods1= ObjectStream.read(Goods.class, "/goods/"+name+".txt");
	                goods.setGId(Integer.parseInt(id));
	                goods.setGname(name);
	                goods.setGPrice(price);
	                goods.setGCount(count);
	                goods.setGIntroduce(goods1.getGIntroduce());   
                }
        });
		JScrollPane pane = new JScrollPane( table );
		panel.add(pane);
		contentPane.add(panel);
		
		JButton button_2 = new JButton("查看商品详细信息");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg="searchgoodsall@@";
				String result=new TCPClient().send(msg);
				if("ShowGoodsText".equals(result)){
					int row = table.getSelectedRow();
					if(row == -1){
						JOptionPane.showMessageDialog(null, "没有选中任何商品！");
					}else{
						UserGoods d = new UserGoods(goods,model,table);
						d.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						d.setVisible(true);
					}
				}
			}
		});
		button_2.setBounds(407, 45, 299, 23);
		contentPane.add(button_2);
		
		JButton button_3 = new JButton("退出登录");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserLogin ul=new UserLogin(us);
				ul.setVisible(true);
			}
		});
		button_3.setBounds(613, 6, 93, 23);
		contentPane.add(button_3);
		
		JLabel lblid = new JLabel("商品名称:");
		lblid.setBounds(10, 53, 65, 15);
		contentPane.add(lblid);
		
		textField = new JTextField();
		textField.setBounds(85, 46, 104, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton button_4 = new JButton("搜索");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String msg="search@@"+textField.getText();
				String result=new TCPClient().send(msg);
				if("NoSearchInfo".equals(result)){
					JOptionPane.showMessageDialog(null, "输入的商品名称不能为空！");
					textField.setText(null);
				}else if("ShowSearchInfo".equals(result)){
					Goods goods1=null;
					if((goods1=(new AdminSearch(textField.getText()).findByName()))==null){
						JOptionPane jOptionPan=new JOptionPane();
						jOptionPan.showMessageDialog(null, "您要找的商品不存在");
					}else{
						//清除表格内容
						model.getDataVector().clear();   //清除表格数据
						 model.fireTableDataChanged();//通知模型更新
						table.updateUI();
						model.addRow(new String[]{Integer.toString(goods1.getGId()),goods1.getGName(),goods1.getGPrice(),goods1.getGCount()});
					}
				}
			}
		});
		button_4.setBounds(197, 45, 93, 23);
		contentPane.add(button_4);
		
		JLabel label = new JLabel("购物车:");
		label.setBounds(10, 10, 42, 15);
		contentPane.add(label);
		
		JButton button_5 = new JButton("查看购物车");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserCartFrame cf = new UserCartFrame(h);
				cf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				cf.setVisible(true);
			}
		});
		button_5.setBounds(197, 6, 110, 23);
		contentPane.add(button_5);
		JLabel lblNewLabel = new JLabel(Integer.toString(h));
//		JLabel lblNewLabel = new JLabel(Integer.toString(Integer.parseInt(lblNewLabel.getText())+h));
		lblNewLabel.setBounds(52, 10, 54, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("件商品");
		lblNewLabel_1.setBounds(116, 10, 73, 15);
		contentPane.add(lblNewLabel_1);
	}
}
