package cn.edu.nuc.onlinestore.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import cn.edu.nuc.onlinestore.io.ObjectStream;
import cn.edu.nuc.onlinestore.model.AdminSearch;
import cn.edu.nuc.onlinestore.model.Goods;
import cn.edu.nuc.onlinestore.network.TCPServer;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.ActionEvent;

public class AdminStore extends JFrame {

	/**
	 * 
	 */
	 
	private JPanel contentPane;
	private JTextField textField;
	private AdminStore as=null;

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 * @wbp.parser.constructor
	 */
	
	public AdminStore(AdminLogin al) {
		
		/*addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent w) {
				int option=JOptionPane.showConfirmDialog(null, "您要退出系统吗？");
				if(option==JOptionPane.YES_OPTION){
					al.setVisible(true);
					textField.setText("");
					passwordField.setText(null);
				}
			}
		});*/
		al.setVisible(false);
		this.as=this;
		setTitle("中北商场后台管理系统--当前用户:ysn");
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
		//model.addColumn("操作");
		
	/*	model.addRow(new String[]{"1","水杯","15.00","200"});
		model.addRow(new String[]{"2","水瓶","35.00","200"});
		model.addRow(new String[]{"3","天堂伞","55.00","200"});
		model.addRow(new String[]{"4","男袜","8.00","200"});
		model.addRow(new String[]{"5","农夫山泉","2.00","200"});
		model.addRow(new String[]{"6","毛巾","9.90","200"});
		model.addRow(new String[]{"7","牙刷","15.00","200"});
		model.addRow(new String[]{"8","洗发水","15.00","200"});
		model.addRow(new String[]{"9","牙膏","15.00","200"});
		model.addRow(new String[]{"10","海尔全自动洗衣机","2,699.00","200"});*/
		
		File f=new File("d:/store/goods/");
		String[] s=f.list();
		for(int i=0;i<s.length;i++){
			Goods goods=ObjectStream.read(Goods.class,"/goods/"+s[i] );
			model.addRow(new String[]{Integer.toString(goods.getGId()),goods.getGName(),goods.getGPrice(),goods.getGCount()});
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
		
		JButton button = new JButton("添加商品");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminAdd add = new AdminAdd(model);
				add.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				add.setVisible(true);
			}
		});
		button.setBounds(386, 45, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("修改商品");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				
				if(row == -1){
					JOptionPane.showMessageDialog(null, "没有选中任何商品！");
				}else{
					AdminUpdate u = new AdminUpdate(goods,model,table);
					u.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					u.setVisible(true);
				}
			}
		});
		button_1.setBounds(489, 45, 93, 23);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("删除选中商品");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					//int option=JOptionPane.showConfirmDialog(null, "确定要删除"+goods.getGName()+"么?" );
					int row = table.getSelectedRow();
					
					if(row == -1){
						JOptionPane.showMessageDialog(null, "没有选中任何商品！");
					}else{	
						int option=JOptionPane.showConfirmDialog(null, "确定要删除"+goods.getGName()+"么?" );
						//得到当前选中商品项
						if(option==JOptionPane.YES_OPTION){
							File file = new File("d:/store/goods/" +goods.getGName()+".txt");
							file.delete();
							//清除表格数据
							model.getDataVector().clear();   //清除表格数据
							model.fireTableDataChanged();//通知模型更新
							table.updateUI();
							//遍历并显示删除更新后的内容
							File f=new File("d:/store/goods/");
							String[] s=f.list();
							for(int i=0;i<s.length;i++){
								Goods goods=ObjectStream.read(Goods.class,"/goods/"+s[i] );
								model.addRow(new String[]{Integer.toString(goods.getGId()),goods.getGName(),goods.getGPrice(),goods.getGCount()});
							}
						}
					}
			}	
		});
		button_2.setBounds(587, 45, 119, 23);
		contentPane.add(button_2);
		
		JButton button_3 = new JButton("退出登录");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminLogin al=new AdminLogin(as);
				al.setVisible(true);
			}
		});
		button_3.setBounds(613, 6, 93, 23);
		contentPane.add(button_3);
		
		JLabel label = new JLabel("当前在线用户数: ");
		label.setBounds(10, 10, 162, 15);
		contentPane.add(label);
		
		JLabel lblid = new JLabel("商品名称:");
		lblid.setBounds(10, 53, 59, 15);
		contentPane.add(lblid);
		
		textField = new JTextField();
		textField.setBounds(68, 50, 104, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton button_4 = new JButton("搜索");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text =textField.getText();
				Goods goods=null;
				if( text.equals("")){
					JOptionPane jOptionPan=new JOptionPane();
					jOptionPan.showMessageDialog(null, "输入的产品名称不能为空！");
				}else if((goods=(new AdminSearch(text).findByName()))==null){
					JOptionPane jOptionPan=new JOptionPane();
					jOptionPan.showMessageDialog(null, "您要找的商品不存在");
				}else{
					//清除表格内容
					model.getDataVector().clear();   //清除表格数据
					 model.fireTableDataChanged();//通知模型更新
					table.updateUI();
					model.addRow(new String[]{Integer.toString(goods.getGId()),goods.getGName(),goods.getGPrice(),goods.getGCount()});
				}
			}
			
		});
		button_4.setBounds(180, 49, 93, 23);
		contentPane.add(button_4);
		//启动监听
		new TCPServer().start();
	}
	
	//显示添加成功后的商品信息页
	public AdminStore(AdminAdd aa) {
	/*	addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent w) {
				int option=JOptionPane.showConfirmDialog(null, "您要退出系统吗？");
				if(option==JOptionPane.YES_OPTION){
					al.setVisible(true);
					textField.setText(null);
	//				passwordField.setText(null);
				}
			}
		});*/
		this.as=this;
		aa.setVisible(false);
		setTitle("中北商场后台管理系统--当前用户:ysn");
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
		
		JTable table = new JTable( model );
		Goods goods = new Goods();
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
		
		JButton button = new JButton("添加商品");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminAdd add = new AdminAdd(model);
				add.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				add.setVisible(true);
			}
		});
		button.setBounds(386, 45, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("修改商品");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row == -1){
					JOptionPane.showMessageDialog(null, "没有选中任何商品！");
				}else{
					AdminUpdate u = new AdminUpdate(goods,model,table);
					u.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					u.setVisible(true);
				}
			}
		});
		button_1.setBounds(489, 45, 93, 23);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("删除选中商品");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					int row = table.getSelectedRow();
					if(row == -1){
						JOptionPane.showMessageDialog(null, "没有选中任何商品！");
					}else{	
						int option=JOptionPane.showConfirmDialog(null, "确定要删除"+goods.getGName()+"么?" );
						//得到当前选中商品项
						if(option==JOptionPane.YES_OPTION){
							File file = new File("d:/store/goods/" +goods.getGName()+".txt");
							file.delete();
							//清除表格数据
							model.getDataVector().clear();   //清除表格数据
							model.fireTableDataChanged();//通知模型更新
							table.updateUI();
							//遍历并显示删除更新后的内容
							File f=new File("d:/store/goods/");
							String[] s=f.list();
							for(int i=0;i<s.length;i++){
								Goods goods=ObjectStream.read(Goods.class,"/goods/"+s[i] );
								model.addRow(new String[]{Integer.toString(goods.getGId()),goods.getGName(),goods.getGPrice(),goods.getGCount()});
							}
						}
					}
			}	
		});
		button_2.setBounds(587, 45, 119, 23);
		contentPane.add(button_2);
		
		JButton button_3 = new JButton("退出登录");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminLogin al=new AdminLogin(as);
				al.setVisible(true);
			}
		});
		button_3.setBounds(613, 6, 93, 23);
		contentPane.add(button_3);
		
		JLabel label = new JLabel("当前在线用户数: 5");
		label.setBounds(10, 10, 162, 15);
		contentPane.add(label);
		
		JLabel lblid = new JLabel("商品编号:");
		lblid.setBounds(10, 53, 59, 15);
		contentPane.add(lblid);
		
		textField = new JTextField();
		textField.setBounds(68, 50, 104, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton button_4 = new JButton("搜索");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text =textField.getText();
				Goods goods=null;
				if( text.equals("")){
					JOptionPane jOptionPan=new JOptionPane();
					jOptionPan.showMessageDialog(null, "输入的产品名称不能为空！");
				}else if((goods=(new AdminSearch(text).findByName()))==null){
					JOptionPane jOptionPan=new JOptionPane();
					jOptionPan.showMessageDialog(null, "您要找的商品不存在");
				}else{
					//清除表格内容
					model.getDataVector().clear();   //清除表格数据
					 model.fireTableDataChanged();//通知模型更新
					table.updateUI();
					model.addRow(new String[]{Integer.toString(goods.getGId()),goods.getGName(),goods.getGPrice(),goods.getGCount()});
				}
			}
			
		});
		button_4.setBounds(180, 49, 93, 23);
		contentPane.add(button_4);
		//启动监听
				new TCPServer().start();
	}
	
	//显示修改成功后的商品信息页
		public AdminStore(AdminUpdate au) {
	/*		addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent w) {
					int option=JOptionPane.showConfirmDialog(null, "您要退出系统吗？");
					if(option==JOptionPane.YES_OPTION){
						al.setVisible(true);
						textField.setText(null);
		//				passwordField.setText(null);
					}
				}
			});*/
			this.as=this;
			au.setVisible(false);
			setTitle("中北商场后台管理系统--当前用户:ysn");
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
			
			JTable table = new JTable( model );
			Goods goods = new Goods();
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
			JButton button = new JButton("添加商品");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AdminAdd add = new AdminAdd(model);
					add.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					add.setVisible(true);
				}
			});
			button.setBounds(386, 45, 93, 23);
			contentPane.add(button);
			
			JButton button_1 = new JButton("修改商品");
			button_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int row = table.getSelectedRow();
					if(row == -1){
						JOptionPane.showMessageDialog(null, "没有选中任何商品！");
					}else{
						AdminUpdate u = new AdminUpdate(goods,model,table);
						u.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						u.setVisible(true);
					}
				}
			});
			button_1.setBounds(489, 45, 93, 23);
			contentPane.add(button_1);
			
			JButton button_2 = new JButton("删除选中商品");
			button_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
						int row = table.getSelectedRow();
						if(row == -1){
							JOptionPane.showMessageDialog(null, "没有选中任何商品！");
						}else{	
							int option=JOptionPane.showConfirmDialog(null, "确定要删除"+goods.getGName()+"么?" );
							//得到当前选中商品项
							if(option==JOptionPane.YES_OPTION){
								File file = new File("d:/store/goods/" +goods.getGName()+".txt");
								file.delete();
								//清除表格数据
								model.getDataVector().clear();   //清除表格数据
								model.fireTableDataChanged();//通知模型更新
								table.updateUI();
								//遍历并显示删除更新后的内容
								File f=new File("d:/store/goods/");
								String[] s=f.list();
								for(int i=0;i<s.length;i++){
									Goods goods=ObjectStream.read(Goods.class,"/goods/"+s[i] );
									model.addRow(new String[]{Integer.toString(goods.getGId()),goods.getGName(),goods.getGPrice(),goods.getGCount()});
								}
							}
						}
				}	
			});
			button_2.setBounds(587, 45, 119, 23);
			contentPane.add(button_2);
			
			JButton button_3 = new JButton("退出登录");
			button_3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AdminLogin al=new AdminLogin(as);
					al.setVisible(true);
				}
			});
			button_3.setBounds(613, 6, 93, 23);
			contentPane.add(button_3);
			
			JLabel label = new JLabel("当前在线用户数: 5");
			label.setBounds(10, 10, 162, 15);
			contentPane.add(label);
			
			JLabel lblid = new JLabel("商品编号:");
			lblid.setBounds(10, 53, 59, 15);
			contentPane.add(lblid);
			
			textField = new JTextField();
			textField.setBounds(68, 50, 104, 21);
			contentPane.add(textField);
			textField.setColumns(10);
			
			JButton button_4 = new JButton("搜索");
			button_4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String text =textField.getText();
					Goods goods=null;
					if( text.equals("")){
						JOptionPane jOptionPan=new JOptionPane();
						jOptionPan.showMessageDialog(null, "输入的产品名称不能为空！");
					}else if((goods=(new AdminSearch(text).findByName()))==null){
						JOptionPane jOptionPan=new JOptionPane();
						jOptionPan.showMessageDialog(null, "您要找的商品不存在");
					}else{
						//清除表格内容
						model.getDataVector().clear();   //清除表格数据
						 model.fireTableDataChanged();//通知模型更新
						table.updateUI();
						model.addRow(new String[]{Integer.toString(goods.getGId()),goods.getGName(),goods.getGPrice(),goods.getGCount()});
					}
				}
				
			});
			button_4.setBounds(180, 49, 93, 23);
			contentPane.add(button_4);
			//启动监听
			new TCPServer().start();
		}
}
