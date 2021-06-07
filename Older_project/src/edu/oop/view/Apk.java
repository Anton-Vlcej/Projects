package edu.oop.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;


public class Apk extends JFrame {

	private JPanel contentPane;
	private JTextField tfSubjectMatter;
	private JTextField tfBook;
	private JTextField tfClass;
	private JTextField tfSubject;
	private JComboBox<String> cbClass;
	private JComboBox<String> cbSubject;
	private JComboBox<String> cbSubMatter;
	private JComboBox<String> cbBook;
	private JButton btnSubmit;
	private JComboBox<String> cbRemark;
	private JSpinner sToPage;
	private JSpinner sPageFrom;
	private JComboBox<String >cbClassSelection;
	private JComboBox<String> cbSubjectSelection;
	private JComboBox<String> cbSubMatterSelection;
	private JComboBox<String> cbBookSelection;
	private JComboBox<String> cbClassEdit;
	private JComboBox<String> cbSubjectEdit;
	private JComboBox<String> cbSubMatEdit;
	private JComboBox<String> cbBookEdit;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnAdd;
	private DefaultComboBoxModel<String> cbClassShared = new DefaultComboBoxModel<String>(new String[] {"None"});
	private DefaultComboBoxModel<String> cbSubjectShared = new DefaultComboBoxModel<String>(new String[] {"None"});
	private DefaultComboBoxModel<String> cbSubMatterShared = new DefaultComboBoxModel<String>(new String[] {"None"});
	private DefaultComboBoxModel<String> cbBookShared = new DefaultComboBoxModel<String>(new String[] {"None"});	
	private JButton btnSelect;
	private JTextArea txtrSelection;
	private JSpinner sID;
	private JMenuItem menuExit;
	
	static final String DB_URL = "jdbc:mysql://localhost/dbhomework?useSSL=false&serverTimezone=UTC";
	static final String USER = "root";
	static final String PASS = "Anton12345678";
	
	private void selectFromDb(String sql) {
		
		Connection conn = null;
		Statement stm = null;

		try {

			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stm = conn.createStatement();

		
		ResultSet rs = stm.executeQuery(sql);
		
			while(rs.next()) {
				int dbIdEntryRead = rs.getInt(1);
				String dbSubjectRead = rs.getString(2);
				String dbStudClassRead = rs.getString(3);
				String dbSubjMatterRead = rs.getString(4);
				String dbBookRead = rs.getString(5);
				int dbFromPageRead = rs.getInt(6);
				int dbEndPageRead = rs.getInt(7);
				String dbRemarkRead = rs.getString(8);
			
				txtrSelection.setText(txtrSelection.getText() + "ID: " + dbIdEntryRead + " || Class: " + dbStudClassRead + ", Subject: " + dbSubjectRead + ", Subject matter: " + dbSubjMatterRead + "\nBook: "
					+ dbBookRead + ", From page: " + dbFromPageRead + ", To page: " + dbEndPageRead + ", Remark: " + dbRemarkRead + "\n\n");
			}
		}catch (SQLException ce) {
			ce.printStackTrace();
		} catch (Exception ee) {
			ee.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ss) {
				ss.printStackTrace();
			}
			try {
				if (stm != null) {
					stm.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		
		}
	}
	
	
	private void addStudentClass(String studClass) {
		cbClassShared.addElement(studClass);
	}
	
	private void addSubject(String subject) {
		cbSubjectShared.addElement(subject);
	}
	
	private void addSubjectMatter(String subMatter) {
		cbSubMatterShared.addElement(subMatter);
	}
	
	private void addBook(String book) {
		cbBookShared.addElement(book);
	}
	
	public void setInvisible() {
		contentPane.setVisible(false);
	}
	
	/**
	 * Create the frame.
	 */
	
	public Apk() {
				
		initComponent();
		createEvents();
				
	}

	private void createEvents() {
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				addStudentClass(tfClass.getText());
				addSubject(tfSubject.getText());
				addSubjectMatter(tfSubjectMatter.getText());
				addBook(tfBook.getText());
				
			}
		});
		
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				String dbStudClass = (String) cbClassSelection.getSelectedItem();
				String dbSubject = (String) cbSubjectSelection.getSelectedItem();
				String dbSubjMatter = (String) cbSubMatterSelection.getSelectedItem();
				String dbBook = (String) cbBookSelection.getSelectedItem();
								
				Connection conn = null;
				Statement stm = null;

				try {

					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					stm = conn.createStatement();
					String sql;

					
					if(dbStudClass != "None" && dbSubject.equals("None") && dbSubjMatter.equals("None") && dbBook.equals("None")) {
						
						sql = "SELECT * FROM homework WHERE class = '" + dbStudClass + "'";
						selectFromDb(sql);
						
					} else if (dbStudClass.equals("None") && dbSubject != "None" && dbSubjMatter.equals("None") && dbBook.equals("None")) {
						
						sql = "SELECT * FROM homework WHERE subject = '" + dbSubject + "'";
						selectFromDb(sql);
						
					} else if (dbStudClass.equals("None") && dbSubject.equals("None") && dbSubjMatter != "None" && dbBook.equals("None")) {
						
						sql = "SELECT * FROM homework WHERE subjectMatter = '" + dbSubjMatter + "'";
						selectFromDb(sql);
						
					} else if (dbStudClass.equals("None") && dbSubject.equals("None") && dbSubjMatter.equals("None")  && dbBook != "None") {
						
						sql = "SELECT * FROM homework WHERE book = '" + dbBook + "'";
						selectFromDb(sql);
						
					} else if (dbStudClass != "None" && dbSubject != "None" && dbSubjMatter.equals("None")  && dbBook.equals("None")) {
						
						sql = "SELECT * FROM homework WHERE class = '" + dbStudClass + "' AND classSubject = '" + dbSubject + "'";
						selectFromDb(sql);
						
					} else if (dbStudClass != "None" && dbSubject.equals("None") && dbSubjMatter != "None"  && dbBook.equals("None")) {
						
						sql = "SELECT * FROM homework WHERE class = '" + dbStudClass + "' AND subjectMatter = '" + dbSubjMatter + "'";
						selectFromDb(sql);
						
					} else if (dbStudClass != "None" && dbSubject.equals("None") && dbSubjMatter.equals("None")  && dbBook != "None") {
						
						sql = "SELECT * FROM homework WHERE class = '" + dbStudClass + "' AND book = '" + dbBook + "'";
						selectFromDb(sql);
						
					} else if (dbStudClass.equals("None") && dbSubject != "None" && dbSubjMatter != "None"  && dbBook.equals("None")) {
						
						sql = "SELECT * FROM homework WHERE classSubject = '" + dbSubject + "' AND subjectMatter = '" + dbSubjMatter + "'";
						selectFromDb(sql);
						
					} else if (dbStudClass.equals("None") && dbSubject != "None" && dbSubjMatter.equals("None")  && dbBook != "None") {
						
						sql = "SELECT * FROM homework WHERE classSubject = '" + dbSubject + "' AND book = '" + dbBook + "'";
						selectFromDb(sql);
						
					} else if (dbStudClass.equals("None") && dbSubject.equals("None") && dbSubjMatter != "None"  && dbBook != "None") {
						
						sql = "SELECT * FROM homework WHERE subjectMatter = '" + dbSubjMatter + "' AND book = '" + dbBook + "'";
						selectFromDb(sql);
						
					} else if (dbStudClass != "None" && dbSubject != "None" && dbSubjMatter != "None"  && dbBook.equals("None") ) {
						
						sql = "SELECT * FROM homework WHERE class = '" + dbStudClass + "' AND classSubject = '" + dbSubject + "' AND subjectMatter = '" + dbSubjMatter + "'" ;
						selectFromDb(sql);
						
					} else if (dbStudClass != "None" && dbSubject != "None" && dbSubjMatter.equals("None")  && dbBook != "None" ) {
						
						sql = "SELECT * FROM homework WHERE class = '" + dbStudClass + "' AND classSubject = '" + dbSubject + "' AND book = '" + dbBook + "'" ;
						selectFromDb(sql);
						
					} else if (dbStudClass != "None" && dbSubject.equals("None") && dbSubjMatter != "None"  && dbBook != "None" ) {
						
						sql = "SELECT * FROM homework WHERE class = '" + dbStudClass + "' AND subjectMatter = '" + dbSubjMatter + "' AND book = '" + dbBook + "'" ;
						selectFromDb(sql);
						
					} else if (dbStudClass.equals("None") && dbSubject != "None" && dbSubjMatter != "None"  && dbBook != "None" ) {
						
						sql = "SELECT * FROM homework WHERE classSubject = '" + dbSubject + "' AND subjectMatter = '" + dbSubjMatter + "' AND book = '" + dbBook + "'" ;
						selectFromDb(sql);
						
					} else if (dbStudClass.equals("None") && dbSubject.equals("None") && dbSubjMatter.equals("None")  && dbBook.equals("None") ) {
						
						sql = "SELECT * FROM homework";
						selectFromDb(sql);
						
					} else if(dbStudClass != "None" && dbSubject != "None" && dbSubjMatter != "None"  && dbBook != "None") {
						
						sql = "SELECT * FROM homework WHERE class = '" + dbStudClass + "' AND classSubject = '" + dbSubject + "' AND subjectMatter = '" + dbSubjMatter + "'AND book = '" + dbBook + "'";
						selectFromDb(sql);
						
					}
					
				}catch (SQLException ce) {
					ce.printStackTrace();
				} catch (Exception ee) {
					ee.printStackTrace();
				} finally {
					try {
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException ss) {
						ss.printStackTrace();
					}
					try {
						if (stm != null) {
							stm.close();
						}
					} catch (SQLException se) {
						se.printStackTrace();
					}
				
				}

			}
		});
		
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				String dbStudClass = (String) cbClass.getSelectedItem();
				String dbSubject = (String) cbSubject.getSelectedItem();
				String dbSubjMatter = (String) cbSubMatter.getSelectedItem();
				String dbBook = (String) cbBook.getSelectedItem();
				int dbPageFrom = (int) sPageFrom.getValue();
				int dbToPage = (int) sToPage.getValue();
				String dbRemark = (String) cbRemark.getSelectedItem();
								
				Connection conn = null;
				Statement stm = null;

				try {

					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					stm = conn.createStatement();
					String sql = "INSERT INTO homework VALUES(NULL,"
							+ "'" + dbStudClass + "',"
							+ "'" + dbSubject + "',"
							+ "'" + dbSubjMatter + "',"
							+ "'" + dbBook + "',"
							+ dbPageFrom + ", "
							+ dbToPage + ", "
							+ "'" + dbRemark + "')";

					stm.executeUpdate(sql);
					
				} catch (SQLException ce) {
					ce.printStackTrace();
				} catch (Exception ee) {
					ee.printStackTrace();
				} finally {
					try {
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException ss) {
						ss.printStackTrace();
					}
					try {
						if (stm != null) {
							stm.close();
						}
					} catch (SQLException se) {
						se.printStackTrace();
					}
				
				}
			
			}
		});
		
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String dbStudClass = (String) cbClass.getSelectedItem();
				String dbSubject = (String) cbSubject.getSelectedItem();
				String dbSubjMatter = (String) cbSubMatter.getSelectedItem();
				String dbBook = (String) cbBook.getSelectedItem();
				
				Connection conn = null;
				Statement stm = null;

				try {

					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					stm = conn.createStatement();
					String sql = "UPDATE homework SET classsubject = 'Zemepis' WHERE ID =" + 5;

					stm.executeUpdate(sql);
					
				} catch (SQLException ce) {
					ce.printStackTrace();
				} catch (Exception ee) {
					ee.printStackTrace();
				} finally {
					try {
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException ss) {
						ss.printStackTrace();
					}
					try {
						if (stm != null) {
							stm.close();
						}
					} catch (SQLException se) {
						se.printStackTrace();
					}
				
				}

			
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				int idDelete = (int) sID.getValue();
				
				Connection conn = null;
				Statement stm = null;

				try {

					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					stm = conn.createStatement();
					String sql = "DELETE FROM homework WHERE id = " + idDelete;

					stm.executeUpdate(sql);
					
				} catch (SQLException ce) {
					ce.printStackTrace();
				} catch (Exception ee) {
					ee.printStackTrace();
				} finally {
					try {
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException ss) {
						ss.printStackTrace();
					}
					try {
						if (stm != null) {
							stm.close();
						}
					} catch (SQLException se) {
						se.printStackTrace();
					}
				
				}
			
			}
		});
		
		menuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				System.exit(0);
			
			}
		});
		
	}

	private void initComponent() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 655, 490);
		setTitle("Education");
		setVisible(false);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		menuExit = new JMenuItem("Exit");
	
		mnFile.add(menuExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 604, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 395, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JPanel tpAdd = new JPanel();
		tpAdd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tabbedPane.addTab("AddTo", new ImageIcon(Apk.class.getResource("/edu/oop/res/icons8_Add_New_16px.png")), tpAdd, null);
		tabbedPane.setEnabledAt(0, true);
		
		JLabel lblClass = new JLabel("Class:");
		lblClass.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblSubject = new JLabel("Subject:");
		lblSubject.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblSubjectMatter = new JLabel("Subject matter:");
		lblSubjectMatter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		tfSubjectMatter = new JTextField();
		tfSubjectMatter.setToolTipText("Write subject matter as Combinatorics");
		tfSubjectMatter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfSubjectMatter.setColumns(10);
		
		JLabel lblBook = new JLabel("Book:");
		lblBook.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		tfBook = new JTextField();
		tfBook.setToolTipText("Write name of book from wich students need to learn or have a homework");
		tfBook.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfBook.setColumns(10);
		
		tfClass = new JTextField();
		tfClass.setToolTipText("Write class you want to add as 1.A");
		tfClass.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfClass.setColumns(10);
		
		tfSubject = new JTextField();
		tfSubject.setToolTipText("Write subject as Mathemathics");
		tfSubject.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfSubject.setColumns(10);
		
		btnAdd = new JButton("Add");
		
		btnAdd.setIcon(new ImageIcon(Apk.class.getResource("/edu/oop/res/icons8_Add_Database_48px.png")));
		GroupLayout gl_tpAdd = new GroupLayout(tpAdd);
		gl_tpAdd.setHorizontalGroup(
			gl_tpAdd.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_tpAdd.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_tpAdd.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSubjectMatter)
						.addComponent(lblClass)
						.addComponent(lblSubject)
						.addComponent(lblBook))
					.addGap(29)
					.addGroup(gl_tpAdd.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tpAdd.createSequentialGroup()
							.addComponent(tfBook, GroupLayout.PREFERRED_SIZE, 315, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_tpAdd.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_tpAdd.createSequentialGroup()
								.addComponent(tfSubject, 315, 315, 315)
								.addContainerGap())
							.addGroup(gl_tpAdd.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_tpAdd.createSequentialGroup()
									.addComponent(tfClass, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
									.addGap(148))
								.addGroup(gl_tpAdd.createSequentialGroup()
									.addComponent(tfSubjectMatter, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
									.addGap(148))))))
				.addGroup(Alignment.LEADING, gl_tpAdd.createSequentialGroup()
					.addGap(241)
					.addComponent(btnAdd)
					.addContainerGap(269, Short.MAX_VALUE))
		);
		gl_tpAdd.setVerticalGroup(
			gl_tpAdd.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tpAdd.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_tpAdd.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfClass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblClass))
					.addGap(40)
					.addGroup(gl_tpAdd.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSubject)
						.addComponent(tfSubject, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(37)
					.addGroup(gl_tpAdd.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSubjectMatter)
						.addComponent(tfSubjectMatter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_tpAdd.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tpAdd.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
							.addComponent(btnAdd)
							.addGap(38))
						.addGroup(gl_tpAdd.createSequentialGroup()
							.addGap(35)
							.addGroup(gl_tpAdd.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblBook)
								.addComponent(tfBook, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())))
		);
		tpAdd.setLayout(gl_tpAdd);
		
		JPanel pHomework = new JPanel();
		tabbedPane.addTab("Homework", new ImageIcon(Apk.class.getResource("/edu/oop/res/icons8_Hand_With_Pen_16px.png")), pHomework, null);
		
		JLabel lblClassChoose = new JLabel("Class:");
		lblClassChoose.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		cbClass = new JComboBox();
		cbClass.setModel(cbClassShared);
		cbClass.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblSubjectChoose = new JLabel("Subject:");
		lblSubjectChoose.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		cbSubject = new JComboBox();
		cbSubject.setModel(cbSubjectShared);
		cbSubject.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblSubjectMatterChoose = new JLabel("Subject matter:");
		lblSubjectMatterChoose.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		cbSubMatter = new JComboBox();
		cbSubMatter.setModel(cbSubMatterShared);
		cbSubMatter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblBookChoose = new JLabel("Book:");
		lblBookChoose.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		cbBook = new JComboBox();
		cbBook.setModel(cbBookShared);
		cbBook.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblRemark = new JLabel("Remark:");
		lblRemark.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblPageFrom = new JLabel("From page:");
		lblPageFrom.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		sPageFrom = new JSpinner();
		sPageFrom.setModel(new SpinnerNumberModel(1, 1, 1000, 1));
		sPageFrom.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblToPage = new JLabel("To:");
		lblToPage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		sToPage = new JSpinner();
		sToPage.setModel(new SpinnerNumberModel(1, 1, 1000, 1));
		sToPage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		cbRemark = new JComboBox();
		cbRemark.setModel(new DefaultComboBoxModel(new String[] {"Homework", "For learning", "Reading"}));
		cbRemark.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		btnSubmit = new JButton("Submit");
		
		btnSubmit.setIcon(new ImageIcon(Apk.class.getResource("/edu/oop/res/icons8_Enter_Key_48px.png")));
		GroupLayout gl_pHomework = new GroupLayout(pHomework);
		gl_pHomework.setHorizontalGroup(
			gl_pHomework.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pHomework.createSequentialGroup()
					.addGap(24)
					.addGroup(gl_pHomework.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSubjectMatterChoose)
						.addComponent(lblClassChoose)
						.addComponent(lblSubjectChoose)
						.addComponent(lblBookChoose)
						.addComponent(lblPageFrom)
						.addComponent(lblRemark))
					.addGap(18)
					.addGroup(gl_pHomework.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pHomework.createSequentialGroup()
							.addComponent(cbRemark, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 219, Short.MAX_VALUE)
							.addComponent(btnSubmit)
							.addGap(25))
						.addGroup(gl_pHomework.createSequentialGroup()
							.addGroup(gl_pHomework.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pHomework.createSequentialGroup()
									.addComponent(sPageFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblToPage)
									.addGap(18)
									.addComponent(sToPage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(cbBook, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbSubject, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbClass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbSubMatter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(237, Short.MAX_VALUE))))
		);
		gl_pHomework.setVerticalGroup(
			gl_pHomework.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pHomework.createSequentialGroup()
					.addGap(33)
					.addGroup(gl_pHomework.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblClassChoose)
						.addComponent(cbClass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(29)
					.addGroup(gl_pHomework.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSubjectChoose)
						.addComponent(cbSubject, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addGroup(gl_pHomework.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSubjectMatterChoose)
						.addComponent(cbSubMatter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(gl_pHomework.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBookChoose)
						.addComponent(cbBook, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addGroup(gl_pHomework.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPageFrom)
						.addComponent(sPageFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblToPage)
						.addComponent(sToPage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(gl_pHomework.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRemark)
						.addComponent(cbRemark, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(57, Short.MAX_VALUE))
				.addGroup(gl_pHomework.createSequentialGroup()
					.addContainerGap(285, Short.MAX_VALUE)
					.addComponent(btnSubmit)
					.addGap(22))
		);
		pHomework.setLayout(gl_pHomework);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Show&Edit", new ImageIcon(Apk.class.getResource("/edu/oop/res/icons8_Edit_Property_16px.png")), panel_1, null);
		tabbedPane.setEnabledAt(2, true);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblClassSelect = new JLabel("Class:");
		lblClassSelect.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		cbClassSelection = new JComboBox();
		cbClassSelection.setModel(cbClassShared);
		cbClassSelection.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblSubject_1 = new JLabel("Subject:");
		lblSubject_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		cbSubjectSelection = new JComboBox();
		cbSubjectSelection.setModel(cbSubjectShared);
		cbSubjectSelection.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblBook_1 = new JLabel("Book:");
		lblBook_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		cbSubMatterSelection = new JComboBox();
		cbSubMatterSelection.setModel(cbSubMatterShared);
		cbSubMatterSelection.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblSubjecMatter = new JLabel("Subjec matter:");
		lblSubjecMatter.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		cbBookSelection = new JComboBox();
		cbBookSelection.setModel(cbBookShared);
		cbBookSelection.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		btnSelect = new JButton("Select");
		
		btnSelect.setIcon(new ImageIcon(Apk.class.getResource("/edu/oop/res/icons8_Select_Column_16px_1.png")));
		btnSelect.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 578, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_1.createSequentialGroup()
											.addComponent(lblSubjecMatter)
											.addGap(10)
											.addComponent(cbSubMatterSelection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel_1.createSequentialGroup()
											.addComponent(lblClassSelect)
											.addGap(18)
											.addComponent(cbClassSelection, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel_1.createSequentialGroup()
											.addComponent(lblSubject_1)
											.addGap(18)
											.addComponent(cbSubjectSelection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel_1.createSequentialGroup()
											.addComponent(lblBook_1)
											.addGap(18)
											.addComponent(cbBookSelection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.RELATED, 46, Short.MAX_VALUE))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(btnSelect)
									.addGap(18)))
							.addComponent(tabbedPane_1, GroupLayout.PREFERRED_SIZE, 371, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblClassSelect)
								.addComponent(cbClassSelection, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSubject_1)
								.addComponent(cbSubjectSelection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSubjecMatter)
								.addComponent(cbSubMatterSelection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblBook_1)
								.addComponent(cbBookSelection, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(btnSelect))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tabbedPane_1, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		JPanel pEdit = new JPanel();
		tabbedPane_1.addTab("Edit", new ImageIcon(Apk.class.getResource("/edu/oop/res/icons8_Pass_Fail_16px.png")), pEdit, null);
		
		JLabel lblClassEdit = new JLabel("Class:");
		lblClassEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		cbClassEdit = new JComboBox();
		cbClassEdit.setModel(cbClassShared);
		cbClassEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel label = new JLabel("Subject:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		cbSubjectEdit = new JComboBox();
		cbSubjectEdit.setModel(cbSubjectShared);
		cbSubjectEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel label_1 = new JLabel("Subjec matter:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		cbSubMatEdit = new JComboBox();
		cbSubMatEdit.setModel(cbSubMatterShared);
		cbSubMatEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel label_2 = new JLabel("Book:");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		cbBookEdit = new JComboBox();
		cbBookEdit.setModel(cbBookShared);
		cbBookEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		btnEdit = new JButton("Edit");
		
		btnEdit.setIcon(new ImageIcon(Apk.class.getResource("/edu/oop/res/icons8_Edit_Row_48px.png")));
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout gl_pEdit = new GroupLayout(pEdit);
		gl_pEdit.setHorizontalGroup(
			gl_pEdit.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pEdit.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pEdit.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pEdit.createSequentialGroup()
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(cbSubMatEdit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pEdit.createSequentialGroup()
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(cbBookEdit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pEdit.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(cbSubjectEdit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pEdit.createSequentialGroup()
							.addComponent(lblClassEdit, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(cbClassEdit, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(186, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_pEdit.createSequentialGroup()
					.addContainerGap(249, Short.MAX_VALUE)
					.addComponent(btnEdit)
					.addContainerGap())
		);
		gl_pEdit.setVerticalGroup(
			gl_pEdit.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pEdit.createSequentialGroup()
					.addGroup(gl_pEdit.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pEdit.createSequentialGroup()
							.addGap(9)
							.addGroup(gl_pEdit.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblClassEdit, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbClassEdit, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addGap(10)
							.addGroup(gl_pEdit.createParallelGroup(Alignment.BASELINE)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbSubjectEdit, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pEdit.createParallelGroup(Alignment.LEADING)
								.addComponent(cbSubMatEdit, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pEdit.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbBookEdit, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
						.addGroup(Alignment.TRAILING, gl_pEdit.createSequentialGroup()
							.addGap(134)
							.addComponent(btnEdit, 0, 0, Short.MAX_VALUE)))
					.addContainerGap())
		);
		pEdit.setLayout(gl_pEdit);
		
		JPanel pDelete = new JPanel();
		tabbedPane_1.addTab("Delete", new ImageIcon(Apk.class.getResource("/edu/oop/res/icons8_Trash_Can_16px.png")), pDelete, null);
		
		JLabel lblId = new JLabel("ID:");
		
		sID = new JSpinner();
		sID.setToolTipText("Set ID to ID of the record you want to delete");
		sID.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		sID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		btnDelete = new JButton("Delete");
		
		btnDelete.setIcon(new ImageIcon(Apk.class.getResource("/edu/oop/res/icons8_Delete_Document_48px.png")));
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout gl_pDelete = new GroupLayout(pDelete);
		gl_pDelete.setHorizontalGroup(
			gl_pDelete.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pDelete.createSequentialGroup()
					.addGroup(gl_pDelete.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pDelete.createSequentialGroup()
							.addGap(35)
							.addComponent(lblId)
							.addGap(27)
							.addComponent(sID, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pDelete.createSequentialGroup()
							.addGap(201)
							.addComponent(btnDelete)))
					.addContainerGap(76, Short.MAX_VALUE))
		);
		gl_pDelete.setVerticalGroup(
			gl_pDelete.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pDelete.createSequentialGroup()
					.addGap(40)
					.addGroup(gl_pDelete.createParallelGroup(Alignment.BASELINE)
						.addComponent(sID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblId))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(65, Short.MAX_VALUE))
		);
		pDelete.setLayout(gl_pDelete);
		
		txtrSelection = new JTextArea();
		txtrSelection.setEditable(false);
		scrollPane.setViewportView(txtrSelection);
		panel_1.setLayout(gl_panel_1);
		contentPane.setLayout(gl_contentPane);
		
	}
}
