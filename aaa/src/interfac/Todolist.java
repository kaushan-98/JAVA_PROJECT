package interfac;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import codes.databaseconnect;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Todolist {

	private JFrame frmTodoListApplication;
	private JTextField search_textbox;
	private JTable table;
	private JTextField taskNoBox;
	private JTextField dueDateBox;
	Connection conn = null;
	Statement stmt = null;
	

	// Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Todolist window = new Todolist();
					window.frmTodoListApplication.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	  //Create the application.
	 
	public Todolist() {
		initialize();
		try {
			conn = databaseconnect.connect();
			showtable();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
		

	
	public void showtable() {
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM todolist ORDER BY taskNo");
			ResultSetMetaData rsmd = rs.getMetaData();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			
			int cols = rsmd.getColumnCount();
			String[] colName = new String[cols];
			for(int i = 0;i<cols;i++) {
				colName[i] = rsmd.getColumnName(i+1);
			}
			model.setColumnIdentifiers(colName);
			
			String task_no,task,due_date;
			
			while(rs.next()) {
				task_no = rs.getString(1);
				task = rs.getString(2);
				due_date = rs.getString(3);
				String[] row = {task_no,task,due_date};
				model.addRow(row);
			}
				
		} catch (SQLException er) {
			JOptionPane.showMessageDialog(null,er);
		}
	}
		

	//Initialize the contents of the frame. 
	private void initialize() {
		frmTodoListApplication = new JFrame();
		frmTodoListApplication.setResizable(false);
		frmTodoListApplication.getContentPane().setBackground(new Color(255, 115, 128));
		frmTodoListApplication.setBounds(100, 100, 993, 628);
		frmTodoListApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTodoListApplication.getContentPane().setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("TO DO LIST ! ");
		lblNewLabel_2.setBackground(new Color(240, 240, 240));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 50));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(114, 10, 760, 76);
		frmTodoListApplication.getContentPane().add(lblNewLabel_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(389, 120, 580, 403);
		frmTodoListApplication.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 115, 128));
		panel.setBounds(10, 189, 369, 334);
		frmTodoListApplication.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Number :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(10, 10, 129, 39);
		panel.add(lblNewLabel);
		
		JLabel lblTask = new JLabel("Task :");
		lblTask.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTask.setBounds(10, 156, 129, 39);
		panel.add(lblTask);
		
		JLabel lblDueDate = new JLabel("Date :");
		lblDueDate.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDueDate.setBounds(10, 73, 129, 39);
		panel.add(lblDueDate);
		
		taskNoBox = new JTextField();
		taskNoBox.setFont(new Font("Tahoma", Font.BOLD, 20));
		taskNoBox.setBounds(126, 10, 233, 39);
		panel.add(taskNoBox);
		taskNoBox.setColumns(10);
		
		dueDateBox = new JTextField();
		dueDateBox.setFont(new Font("Tahoma", Font.BOLD, 20));
		dueDateBox.setColumns(10);
		dueDateBox.setBounds(126, 73, 233, 39);
		panel.add(dueDateBox);
		
		final JTextArea taskBox = new JTextArea();
		taskBox.setLineWrap(true);
		taskBox.setFont(new Font("Tahoma", Font.BOLD, 20));
		taskBox.setBounds(126, 142, 233, 167);
		panel.add(taskBox);
		
		JLabel lblNewLabel_1 = new JLabel("Find :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(20, 118, 129, 39);
		frmTodoListApplication.getContentPane().add(lblNewLabel_1);
		
		search_textbox = new JTextField();
		search_textbox.setFont(new Font("Tahoma", Font.BOLD, 20));
		search_textbox.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String searchid = search_textbox.getText();
				String sqlUpdate = "SELECT * FROM todolist WHERE taskNo = '"+searchid+"' ";
				try {
					stmt = conn.createStatement();
					ResultSet rs = (ResultSet) stmt.executeQuery(sqlUpdate);
					
					if (rs.next()) {
						taskNoBox.setText(rs.getString("taskNo"));
						taskBox.setText(rs.getString("task"));
						dueDateBox.setText(rs.getString("dueDate"));
						
					} else {
						taskNoBox.setText("");
						taskBox.setText("");
						dueDateBox.setText("");
						
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		search_textbox.setBounds(137, 120, 242, 35);
		frmTodoListApplication.getContentPane().add(search_textbox);
		search_textbox.setColumns(10);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String taskNo,task,dueDate;
				
				taskNo = taskNoBox.getText();
				task = taskBox.getText();
				dueDate = dueDateBox.getText();
				
				
				String sql = "SELECT * FROM todolist WHERE taskNo = '"+taskNo+"'";
				
				try {
					stmt = conn.createStatement();
					java.sql.ResultSet rs = stmt.executeQuery(sql);
					
					if (rs.next()) {
						JOptionPane.showMessageDialog(null, "This number already added !");
					} else {
						if (taskNo.trim().length() != 0 & task.trim().length() != 0) {
							String sql1 = "INSERT INTO todolist(taskNo,task,dueDate) VALUES ('" + taskNo+ "','" + task+ "','"+dueDate+"') ";
							stmt = conn.createStatement();
							stmt.executeUpdate(sql1);
							JOptionPane.showMessageDialog(null, "successfully added  !");
							taskBox.setText("");
							taskNoBox.setText("");
							dueDateBox.setText("");
							taskNoBox.requestFocus();
							table.setModel(new DefaultTableModel());				
							showtable();											
						} else if (taskNo.trim().length() == 0) {
							JOptionPane.showMessageDialog(null, "type task number !");
						} else {
							JOptionPane.showMessageDialog(null, "type task !");
						}
						
						
					}  
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,e1);
				}
			}
		});
		btnNewButton.setForeground(new Color(0, 0, 255));
		btnNewButton.setBackground(new Color(0, 0, 0));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton.setBounds(10, 546, 163, 35);
		frmTodoListApplication.getContentPane().add(btnNewButton);
		
		JButton btnUpdate = new JButton("UPDATE");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String taskNo,task,dueDate;
				
				taskNo = taskNoBox.getText();
				task = taskBox.getText();
				dueDate = dueDateBox.getText();
				
				
				String sql = "SELECT * FROM todolist WHERE taskNo = '"+taskNo+"'";
				
				try {
					stmt = conn.createStatement();
					java.sql.ResultSet rs = stmt.executeQuery(sql);
					
					if (rs.next()) {
						if (taskNo.trim().length() != 0 & task.trim().length() != 0) {
							String sql1 = "UPDATE todolist SET task = '"+task+"',dueDate = '"+dueDate+"' WHERE taskNo = '"+taskNo+"'";
							stmt = conn.createStatement();
							stmt.executeUpdate(sql1);
							JOptionPane.showMessageDialog(null, "successfully updated !");
							taskBox.setText("");
							taskNoBox.setText("");
							dueDateBox.setText("");
							taskNoBox.requestFocus();
							table.setModel(new DefaultTableModel());				//remove the table (default mood)
							showtable();											//show table
						} else if (taskNo.trim().length() == 0) {
							JOptionPane.showMessageDialog(null, "type task number !");
						} else {
							JOptionPane.showMessageDialog(null, "type task !");
						}
						
					} else {
						JOptionPane.showMessageDialog(null, "task number not in dataset !");	
					}  
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,e1);
				}
			}
		});
		btnUpdate.setForeground(new Color(0, 255, 0));
		btnUpdate.setBackground(new Color(0, 0, 0));
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnUpdate.setBounds(248, 546, 163, 35);
		frmTodoListApplication.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchid = search_textbox.getText();
	
				try {
					if (searchid.trim().length() == 0) {
						JOptionPane.showMessageDialog(null, "type task number!");
					} else {
						int searchidn = Integer.parseInt(searchid);
						String sql = "DELETE FROM todolist WHERE taskNo='"+searchidn+"'";
						stmt = conn.createStatement();
						stmt.executeUpdate(sql);
						JOptionPane.showMessageDialog(null, "Deleted !");
						search_textbox.setText("");
						table.setModel(new DefaultTableModel());				
						showtable();
						taskBox.setText("");
						taskNoBox.setText("");
						dueDateBox.setText("");
						taskNoBox.requestFocus();
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,e1);
					search_textbox.setText("");
					table.setModel(new DefaultTableModel());				
					showtable();
				}
				
			}
		});
		btnDelete.setForeground(new Color(255, 0, 0));
		btnDelete.setBackground(new Color(0, 0, 0));
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnDelete.setBounds(511, 546, 163, 35);
		frmTodoListApplication.getContentPane().add(btnDelete);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.setBackground(new Color(0, 0, 0));
		btnExit.setForeground(new Color(255, 128, 0));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmTodoListApplication.dispose();
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnExit.setBounds(766, 546, 163, 35);
		frmTodoListApplication.getContentPane().add(btnExit);
		
		JLabel lblNewLabel_3 = new JLabel("Developed by :");
		lblNewLabel_3.setForeground(new Color(255, 0, 0));
		lblNewLabel_3.setBounds(488, 523, 85, 13);
		frmTodoListApplication.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Vihanga Kaushan");
		lblNewLabel_3_1.setBounds(583, 523, 108, 13);
		frmTodoListApplication.getContentPane().add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("s14927");
		lblNewLabel_3_1_1.setBounds(701, 523, 85, 13);
		frmTodoListApplication.getContentPane().add(lblNewLabel_3_1_1);
	}
}
