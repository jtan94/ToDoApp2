
//When adding,deleting,updating a task, a user must be in a group, and must have the dropdown menu of groups, not null.  After leaving a group, joining a group
//or adding a group, you must press refresh for the tasks to update, as well as the groups you are part of. to complete a task, enter the completion
//date, and the currently signed in user will be recorded as the user who completed the task. 
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.*;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


class ToDo {

    private String title;
    private String desc;
    private String dueDate;
    private String completionDate;

    public ToDo(String title, String desc, String completionDate) {
        this.title = title;
        this.desc = desc;
        this.completionDate = completionDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setcompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getcompletionDate() {
        return completionDate;
    }

    public String toString() {
        return title;
    }
}

public class ToDoApp extends JFrame {

    private Vector<String> myGroupIds = new Vector<String>();
    private JList<String> myGroupIdsjlist = new JList<String>(myGroupIds);
    private Vector<String> groupIds = new Vector<String>();
    private Vector<String> joinGroupModel = new Vector<String>();
    private JList<String> joinGroup1 = new JList<String>(joinGroupModel);
    private Vector<ToDo> myListModel = new Vector<ToDo>();
    private JList<ToDo> myList = new JList<ToDo>(myListModel);
    private Vector<String> userListModel = new Vector<String>();
    private JList<String> userList = new JList<String>(userListModel);
    private Vector<String> groupListModel = new Vector<String>();
    private Vector<String> groupJoinListModel = new Vector<String>();
    private JList<String> groupJoinList = new JList<String>(groupJoinListModel);
    private JList<String> groupList = new JList<String>(groupListModel);
    private JTextField todoField = new JTextField("Enter Task Here");
    private JTextArea descField = new JTextArea("", 10, 40);
    private JTextField CompletionDateField = new JTextField("");
    private JButton deleteButton = new JButton("Delete");
    private JButton updateButton = new JButton(" Update  ");
    private JButton userdeleteButton = new JButton("Delete");
    private JButton groupdeleteButton = new JButton("Delete");
    private JButton groupleaveButton = new JButton("Leave");
    private JButton groupjoinButton = new JButton("Join");
    private JButton loginButton = new JButton("Login");
    private JPanel buttons = new JPanel();
    private JPanel bottom = new JPanel();
    private JPanel groupMenu = new JPanel();
    private JButton refreshButton = new JButton("Refresh");
    private JButton completeButton = new JButton("Complete");
    private JTextField userField = new JTextField("enter here");
    private JTextField groupField = new JTextField("enter here");
    private JTextField lastName = new JTextField("enter here");
    private JTextField firstName = new JTextField("enter here");
    private JTextField email = new JTextField("enter here");
    private JTextField password = new JTextField("enter here");
    private JTextField groupName = new JTextField("enter here");
    private JTextField userdel = new JTextField("enter here");
    private JTextField loginUser = new JTextField("enter here");
    private JTextField login = new JTextField("enter here");
    private JButton loginAs = new JButton("Login as User");
    private JTextField groupdel = new JTextField("enter here");
    private JTextField groupleave = new JTextField("enter here");
    private JTextField groupjoin = new JTextField("enter here");
    JComboBox<String> cb = new JComboBox<String>(groupListModel);
  
    private String DB_URL = ""; //Database URL here
    private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private String MASTER_USER =""; //username to log into Database
    private String PASSWORD ="" ; //password to log into database
    
    public Vector<String> LoadGroups(String user) {
         Connection conn = null;
        ResultSet data = null;
        
         try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception ex) {
            System.out.println("Could not register driver.");
            System.exit(0);
        }
        try {
            groupListModel.clear();
      
            conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);

            Statement stmt = conn.createStatement();
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT groupId from GroupAssignment where userId='" + user + "'";
            System.out.println(sql);
            data = stmt.executeQuery(sql);
            while (data.next()) {
                String groupId = data.getString("groupId");
                myGroupIds.add(groupId);
                groupListModel.add(groupId);
                System.out.println(groupId);
            }

        } catch (Exception ex) {
            System.out.println("Couldn't log in");
            System.exit(0);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("Connection closed");
                }
                if (data != null) {
                    data.close();
                }
            } catch (SQLException exx) {
                exx.printStackTrace();
            }
        }
        return groupListModel;
    }
    public Vector<String> LoadUsers() {
        Connection conn = null;
        ResultSet data = null;
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception ex) {
            System.out.println("Could not register driver.");
            System.exit(0);
        }
        try {
            userListModel.clear();
            conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);
            Statement stmt = conn.createStatement();
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT userId from User";
            System.out.println(sql);
            data = stmt.executeQuery(sql);
            while (data.next()) {
                String userId = data.getString("userid");
                userListModel.add(userId);
                userList.setListData(userListModel);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            System.exit(0);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("Connection closed");
                }
                if (data != null) {
                    data.close();
                }
            } catch (SQLException exx) {
                exx.printStackTrace();
            }
        }
        return userListModel;
    }

    public Vector<ToDo> LoadList(String user) {
        Connection conn = null;
        ResultSet data = null;
     

        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception ex) {
            System.out.println("Could not register driver.");
            System.exit(0);
        }
        try {

            conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);
            Statement stmt = conn.createStatement();

            stmt = conn.createStatement();
            String sql;

            sql = "SELECT * from GroupTask,GroupAssignment where GroupAssignment.userid='" + user + "' and GroupTask.groupId = GroupAssignment.groupId";
  
            System.out.println(sql);
            System.out.println("Hi");
            myListModel.clear();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //
                String title = rs.getString("title");
                String desc = rs.getString("description");
                String completionDate = rs.getString("completion_date");
                myListModel.add(new ToDo(title, desc, completionDate));
                myList.setListData(myListModel);

            }

        } catch (Exception ex) {
            System.out.println("Couldn't log in");
            System.exit(0);
        } finally {
            try {

                if (conn != null) {
                    conn.close();
                }
                if (data != null) {
                    data.close();
                }
            } catch (SQLException ex) {
                System.out.println("whoops");
            }

        }
        return myListModel;
    }

    public ToDoApp() {
        super();
        Connection conn = null;
        ResultSet data = null;
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception ex) {
            System.out.println("Could not register driver.");
            System.exit(0);
        }
        try {
            userListModel.clear();

            conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);

            Statement stmt = conn.createStatement();
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT userId from User";
            System.out.println(sql);
            data = stmt.executeQuery(sql);
            while (data.next()) {
                String userId = data.getString("userid");
                userListModel.add(userId);
                userList.setListData(userListModel);
            }
        } catch (Exception ex) {
            System.out.println("Couldn't log in");
            System.exit(0);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("Connection closed");
                }
                if (data != null) {
                    data.close();
                }
            } catch (SQLException exx) {
                exx.printStackTrace();
            }
        }
        String[] users = new String[userListModel.size()];
        userListModel.copyInto(users);


        final String userChosen = (String) JOptionPane.showInputDialog(null, "Choose Wisely", "User menu", JOptionPane.QUESTION_MESSAGE, null, users, users[0]);
        JOptionPane.showMessageDialog(null, userChosen);
        System.out.println(userChosen);

        //load the groups
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception ex) {
            System.out.println("Could not register driver.");
            System.exit(0);
        }
        try {
            userListModel.clear();
   
            conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);

            Statement stmt = conn.createStatement();
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT groupId from GroupAssignment where userId='" + userChosen + "'";
            System.out.println(sql);
            data = stmt.executeQuery(sql);
            while (data.next()) {
                String groupId = data.getString("groupId");
                myGroupIds.add(groupId);
                groupListModel.add(groupId);
                System.out.println(groupId);
            }

        } catch (Exception ex) {
            System.out.println("Couldn't log in");
            System.exit(0);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("Connection closed");
                }
                if (data != null) {
                    data.close();
                }
            } catch (SQLException exx) {
                exx.printStackTrace();
            }
        }
        // load the tasks    

        myListModel = LoadList(userChosen);
        userListModel = LoadUsers();

        this.setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        menuBar.add(menu);

        JMenuItem addUser = new JMenuItem("Add User");
        JMenuItem deleteUser = new JMenuItem("Delete User");
        JMenuItem addGroup = new JMenuItem("Add Group");
        JMenuItem deleteGroup = new JMenuItem("Delete Group");
        JMenuItem joinGroup = new JMenuItem("Join Group");
        JMenuItem leaveGroup = new JMenuItem("Leave Group");

        menu.add(addUser);
        menu.add(deleteUser);
        menu.add(addGroup);
        menu.add(deleteGroup);
        menu.add(joinGroup);
        menu.add(leaveGroup);

        leaveGroup.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                Connection conn = null;
                ResultSet data = null;
                try {
                    Class.forName(JDBC_DRIVER);
                } catch (Exception ex) {
                    System.out.println("Could not register driver.");
                    System.exit(0);
                }
                try {
                    System.out.println("HERE");
                    conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);

                    Statement stmt = conn.createStatement();
                    System.out.println("Creating statement...");
                    stmt = conn.createStatement();
                    groupListModel.clear();
                    String sql;

                    sql = "SELECT * from GroupAssignment where userid='" + userChosen + "'";
                    System.out.println(sql);
                    data = stmt.executeQuery(sql);
                    while (data.next()) {

                        Integer groupId = data.getInt("groupId");
                        groupListModel.add(groupId.toString());
                        groupList.setListData(groupListModel);
                        //System.out.println(groupId);
                    }

                } catch (Exception ex) {
                    System.out.println(ex.toString());
                    System.exit(0);
                } finally {
                    try {
                        if (conn != null) {
                            conn.close();
                            System.out.println("Connection closed");
                        }
                        if (data != null) {
                            data.close();
                        }
                    } catch (SQLException exx) {
                        exx.printStackTrace();
                    }
                }
                JFrame f = new JFrame();
                f.setLayout(new BorderLayout());
                JPanel top = new JPanel();
                top.setBackground(Color.cyan);
                f.add(top, BorderLayout.NORTH);

                JPanel bottom = new JPanel();
                bottom.setBackground(Color.cyan);
                f.add(bottom, BorderLayout.SOUTH);
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                c.anchor = GridBagConstraints.WEST;
                top.add(new JLabel("Group"), c);

                c = new GridBagConstraints();
                c.gridx = 1;
                c.gridy = 0;
                c.anchor = GridBagConstraints.WEST;
                groupleave.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        groupleave.setText("");
                    }
                });
                top.add(groupleave, c);

                c = new GridBagConstraints();
                c.gridx = 11;
                c.gridy = 11;
                top.add(groupleaveButton, c);
                JScrollPane scroller = new JScrollPane(groupList);
                scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scroller.setPreferredSize(new Dimension(150, 200));
                bottom.add(scroller, BorderLayout.CENTER);

                groupList.addListSelectionListener(new ListSelectionListener() {

                    public void valueChanged(ListSelectionEvent lse) {
                        try {
                            String groupSelected = groupList.getSelectedValue();
                            groupleave.setText(groupSelected);
                        } catch (Exception e) {
                        }
                    }
                });
                groupleaveButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent ae) {

                        Connection conn = null;
                        ResultSet data = null;

                        if (groupList.getSelectedIndex() >= 0) {
                            try {
                                Class.forName(JDBC_DRIVER);
                            } catch (Exception ex) {
                                System.out.println("Could not register driver.");
                                System.exit(0);
                            }
                            try {
                                conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);
                                conn.setAutoCommit(false);
                                Statement stmt = conn.createStatement();

                                String sql = "SELECT userid,count(*),groupId from GroupAssignment group by groupId having count(*) =1 and userid='" + userChosen + "'and groupId='" + groupleave.getText() + "'";
                                String sql2 = "DELETE from GroupAssignment where userid='" + userChosen + "' and groupId='" + groupleave.getText() + "'";

                                System.out.println(sql);

                                data = stmt.executeQuery(sql);

                                while (data.next()) {
                                    String groupId = data.getString("groupId");
                                    groupIds.add(groupId);
                                }

                                for (String groupid : groupIds) {
                                    System.out.println("DELETE from GroupTask where groupId='" + groupid + "'");
                                    stmt.executeUpdate("DELETE from GroupTask where groupId='" + groupid + "'");

                                }
                                stmt.executeUpdate(sql2);
                                for (String groupid : groupIds) {

                                    System.out.println("DELETE from TGroup where groupId='" + groupid + "'");
                                    stmt.executeUpdate("DELETE from TGroup where groupId='" + groupid + "'");

                                }

                                conn.commit();
                                groupListModel.remove(groupList.getSelectedIndex());
                                groupList.setListData(groupListModel);
                                groupleave.setText("");
                            } catch (SQLException ex) {
                                try {
                                    ex.printStackTrace();
                                    conn.rollback();
                                } catch (SQLException e2) {
                                    System.out.println("Could not roll back");
                                } finally {
                                    try {
                                        if (conn != null) {
                                            conn.close();
                                        }
                                        if (data != null) {
                                            data.close();
                                        }
                                    } catch (SQLException exx) {
                                        exx.printStackTrace();
                                    }
                                }
                            }

                        }
                    }
                });

//                
//            
//                deleting a user deletes a group if he is the only one in there
//                it also deletes the tasks assigned to that group
                f.pack();
                f.setVisible(true);

            }

        });

        deleteGroup.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                Connection conn = null;
                ResultSet data = null;
                try {
                    Class.forName(JDBC_DRIVER);
                } catch (Exception ex) {
                    System.out.println("Could not register driver.");
                    System.exit(0);
                }
                try {
    
                    conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);

                    Statement stmt = conn.createStatement();
                    System.out.println("Creating statement...");
                    stmt = conn.createStatement();
                    groupListModel.clear();
                    String sql;
                    sql = "SELECT * from GroupAssignment where userid='" + userChosen + "'";
                    System.out.println(sql);
                    data = stmt.executeQuery(sql);
                    while (data.next()) {

                        Integer groupId = data.getInt("groupId");
                        groupListModel.add(groupId.toString());
                        groupList.setListData(groupListModel);

                    }

                } catch (Exception ex) {
                    System.out.println(ex.toString());
                    System.exit(0);
                } finally {
                    try {
                        if (conn != null) {
                            conn.close();
                            System.out.println("Connection closed");
                        }
                        if (data != null) {
                            data.close();
                        }
                    } catch (SQLException exx) {
                        exx.printStackTrace();
                    }
                }
                JFrame f = new JFrame();
                f.setLayout(new BorderLayout());
                JPanel top = new JPanel();
                top.setBackground(Color.cyan);
                f.add(top, BorderLayout.NORTH);

                JPanel bottom = new JPanel();
                bottom.setBackground(Color.cyan);
                f.add(bottom, BorderLayout.SOUTH);
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                c.anchor = GridBagConstraints.WEST;
                top.add(new JLabel("Group"), c);

                c = new GridBagConstraints();
                c.gridx = 1;
                c.gridy = 0;
                c.anchor = GridBagConstraints.WEST;
                groupdel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        groupdel.setText("");
                    }
                });
                top.add(groupdel, c);

                c = new GridBagConstraints();
                c.gridx = 11;
                c.gridy = 11;
                top.add(groupdeleteButton, c);
                JScrollPane scroller = new JScrollPane(groupList);
                scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scroller.setPreferredSize(new Dimension(150, 200));
                bottom.add(scroller, BorderLayout.CENTER);

                groupList.addListSelectionListener(new ListSelectionListener() {

                    public void valueChanged(ListSelectionEvent lse) {
                        try {
                            String groupSelected = groupList.getSelectedValue();
                            groupdel.setText(groupSelected);
                        } catch (Exception e) {
                        }
                    }
                });
                groupdeleteButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent ae) {

                        Connection conn = null;
                        ResultSet data = null;

                        if (groupList.getSelectedIndex() >= 0) {
                            try {
                                Class.forName(JDBC_DRIVER);
                            } catch (Exception ex) {
                                System.out.println("Could not register driver.");
                                System.exit(0);
                            }
                            try {

                                conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);
                                conn.setAutoCommit(false);
                                Statement stmt = conn.createStatement();

                                String sql = "DELETE from GroupTask where groupId='" + groupdel.getText() + "'";
                                String sql2 = "DELETE from GroupAssignment where groupId='" + groupdel.getText() + "'";
                                String sql3 = "DELETE from TGroup where groupId='" + groupdel.getText() + "'";
                                System.out.println(sql);
                                System.out.println(sql2);
                                System.out.println(sql3);

                                stmt.executeUpdate(sql);
                                stmt.executeUpdate(sql2);
                                stmt.executeUpdate(sql3);

                                conn.commit();
                                groupListModel.remove(groupList.getSelectedIndex());
                                groupList.setListData(groupListModel);
                                groupdel.setText("");
                            } catch (SQLException ex) {
                                try {
                                    ex.printStackTrace();
                                    conn.rollback();
                                } catch (SQLException e2) {
                                    System.out.println("Could not roll back");
                                } finally {
                                    try {
                                        if (conn != null) {
                                            conn.close();
                                        }
                                        if (data != null) {
                                            data.close();
                                        }
                                    } catch (SQLException exx) {
                                        exx.printStackTrace();
                                    }
                                }
                            }

                        }

                    }

                });

//                
//            
//                deleting a user deletes a group if he is the only one in there
//                it also deletes the tasks assigned to that group
                f.pack();
                f.setVisible(true);

            }

        });

        deleteUser.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                Connection conn = null;
                ResultSet data = null;
                try {
                    Class.forName(JDBC_DRIVER);
                } catch (Exception ex) {
                    System.out.println("Could not register driver.");
                    System.exit(0);
                }
                try {
                    System.out.println("HERE");
                    conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);

                    Statement stmt = conn.createStatement();
                    System.out.println("Creating statement...");
                    userListModel.clear();
                    stmt = conn.createStatement();
                    String sql;
                    sql = "SELECT userId from User";
                    System.out.println(sql);
                    data = stmt.executeQuery(sql);
                    while (data.next()) {
                        String userId = data.getString("userid");
                        userListModel.add(userId);
                        userList.setListData(userListModel);
                    }
                } catch (Exception ex) {
                    System.out.println("Couldn't log in");
                    System.exit(0);
                } finally {
                    try {
                        if (conn != null) {
                            conn.close();
                            System.out.println("Connection closed");
                        }
                        if (data != null) {
                            data.close();
                        }
                    } catch (SQLException exx) {
                        exx.printStackTrace();
                    }
                }
                //setting up the popup window
                JFrame f = new JFrame();
                f.setLayout(new BorderLayout());
                JPanel top = new JPanel();
                top.setBackground(Color.cyan);
                f.add(top, BorderLayout.NORTH);

                GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                c.anchor = GridBagConstraints.WEST;
                top.add(new JLabel("userID:"), c);

                c = new GridBagConstraints();
                c.gridx = 1;
                c.gridy = 0;
                c.anchor = GridBagConstraints.WEST;
                userdel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        userdel.setText("");
                    }
                });
                top.add(userdel, c);

                c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 11;
                top.add(userdeleteButton, c);

                JPanel bottom = new JPanel();
                bottom.setBackground(Color.cyan);
                f.add(bottom, BorderLayout.SOUTH);

                JScrollPane scroller = new JScrollPane(userList);
                scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scroller.setPreferredSize(new Dimension(150, 200));
                bottom.add(scroller, BorderLayout.CENTER);
                f.pack();
                f.setVisible(true);
                //deleting the user
                userList.addListSelectionListener(new ListSelectionListener() {

                    public void valueChanged(ListSelectionEvent lse) {
                        try {
                            String userSelected = userList.getSelectedValue();
                            userdel.setText(userSelected);
                        } catch (Exception e) {
                        }
                    }
                });
                userdeleteButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent ae) {

                        Connection conn = null;
                        ResultSet data = null;

                        if (userList.getSelectedIndex() >= 0) {
                            try {
                                Class.forName(JDBC_DRIVER);
                            } catch (Exception ex) {
                                System.out.println("Could not register driver.");
                                System.exit(0);
                            }
                            try {

                                conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);
                                conn.setAutoCommit(false);
                                Statement stmt = conn.createStatement();

                                String sql = "SELECT userid,count(*),groupId from GroupAssignment group by groupid having count(*) =1 and userid='" + userdel.getText() + "'";
                                String sql2 = "DELETE from GroupAssignment where userid='" + userdel.getText() + "'";
                                String sql3 = "DELETE from User where userId='" + userdel.getText() + "'";
                                System.out.println(sql);

                                data = stmt.executeQuery(sql);
                                while (data.next()) {
                                    String groupId = data.getString("groupId");
                                    groupIds.add(groupId);
                                }

                                for (String groupid : groupIds) {
                                    System.out.println("DELETE from GroupTask where groupId='" + groupid + "'");
                                    stmt.executeUpdate("DELETE from GroupTask where groupId='" + groupid + "'");

                                }
                                stmt.executeUpdate(sql2);
                                for (String groupid : groupIds) {

                                    System.out.println("DELETE from TGroup where groupId='" + groupid + "'");
                                    stmt.executeUpdate("DELETE from TGroup where groupId='" + groupid + "'");

                                }
                                stmt.executeUpdate(sql3);
                                conn.commit();
                                userListModel.remove(userList.getSelectedIndex());
                                userList.setListData(userListModel);
                                userdel.setText("");
                            } catch (SQLException ex) {
                                try {
                                    ex.printStackTrace();
                                    conn.rollback();
                                } catch (SQLException e2) {
                                    System.out.println("Could not roll back");
                                } finally {
                                    try {
                                        if (conn != null) {
                                            conn.close();
                                        }
                                        if (data != null) {
                                            data.close();
                                        }
                                    } catch (SQLException exx) {
                                        exx.printStackTrace();
                                    }
                                }
                            }

                        }

                    }

                });

//                
//            
//                deleting a user deletes a group if he is the only one in there
//                it also deletes the tasks assigned to that group
            }

        });
        addUser.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                JFrame f = new JFrame();
                f.setLayout(new BorderLayout());

                JPanel top = new JPanel();

                f.add(top, BorderLayout.NORTH);

                JPanel bottom = new JPanel();
                f.add(bottom, BorderLayout.CENTER);

                top.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                c.insets = (new Insets(5, 0, 0, 10));
                top.add(new JLabel("UserId", JLabel.RIGHT), c);

                c = new GridBagConstraints();
                c.gridx = 1;
                c.gridy = 0;
                c.insets = (new Insets(5, 0, 0, 10));
                c.anchor = GridBagConstraints.WEST;
                c.fill = GridBagConstraints.HORIZONTAL;
                userField.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        userField.setText("");
                    }
                });
                top.add(userField, c);

                c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 1;
                c.insets = (new Insets(5, 0, 0, 10));
                top.add(new JLabel("First Name", JLabel.RIGHT), c);

                c = new GridBagConstraints();
                c.gridx = 1;
                c.gridy = 1;
                c.anchor = GridBagConstraints.WEST;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = (new Insets(5, 0, 0, 10));
                firstName.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        firstName.setText("");
                    }
                });
                top.add(firstName, c);

                c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 2;
                c.insets = (new Insets(5, 0, 0, 10));
                top.add(new JLabel("Last Name", JLabel.RIGHT), c);

                c = new GridBagConstraints();
                c.gridx = 1;
                c.gridy = 2;
                c.anchor = GridBagConstraints.WEST;
                c.insets = (new Insets(5, 0, 0, 10));
                c.fill = GridBagConstraints.HORIZONTAL;
                lastName.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        lastName.setText("");
                    }
                });
                top.add(lastName, c);

                c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 3;
                c.insets = (new Insets(5, 0, 0, 10));
                top.add(new JLabel("Email Address", JLabel.RIGHT), c);

                c = new GridBagConstraints();
                c.gridx = 1;
                c.gridy = 3;
                c.anchor = GridBagConstraints.WEST;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = (new Insets(5, 0, 0, 10));
                email.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        email.setText("");
                    }
                });
                top.add(email, c);

                c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 4;
                c.insets = (new Insets(5, 0, 0, 10));
                top.add(new JLabel("Password", JLabel.RIGHT), c);

                c = new GridBagConstraints();
                c.gridx = 1;
                c.gridy = 4;
                c.anchor = GridBagConstraints.WEST;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = (new Insets(5, 0, 0, 10));
                password.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        password.setText("");
                    }
                });
                top.add(password, c);

                c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 5;
                c.insets = (new Insets(5, 0, 0, 10));
                top.add(new JLabel("Group name:", JLabel.RIGHT), c);

                c = new GridBagConstraints();
                c.gridx = 1;
                c.gridy = 5;
                c.anchor = GridBagConstraints.WEST;
                c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = (new Insets(5, 0, 0, 10));
                groupName.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        groupName.setText("");
                    }
                });
                top.add(groupName, c);
                c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                c.anchor = GridBagConstraints.EAST;
                //c.insets = (new Insets(5,0,0,10));
                bottom.add(new JButton() {
                    {
                        this.setText("Add user");
                        this.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                Connection conn = null;
                                ResultSet data = null;
                                try {
                                    Class.forName(JDBC_DRIVER);
                                } catch (Exception ex) {
                                    System.out.println("Could not register driver.");
                                    System.exit(0);
                                }
                                try {

                                    conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);
                                    conn.setAutoCommit(false);
                                    Statement stmt = conn.createStatement();
                                    System.out.println("Creating statement...");                   
                                    int greatestid = 0;
                                    ResultSet greatest = stmt.executeQuery("SELECT MAX(groupId) AS max FROM TGroup;");
                                    while (greatest.next()) {
                                        greatestid = greatest.getInt("max");
                                        greatestid = greatestid + 1;

                                    }
                                    System.out.println("Creating a single user");
                                    ///use a transaction!
                                    String sql, sql2, sql3;

                                    sql = "insert into User (userid,last,first,email,password) VALUES ('" + userField.getText() + "','" + lastName.getText() + "','" + firstName.getText() + "','" + email.getText() + "','" + password.getText() + "')";
                                    sql2 = "insert into TGroup (groupId,name) VALUES (" + String.valueOf(greatestid) + ",'" + groupName.getText() + "')";
                                    sql3 = "insert into GroupAssignment (userid,groupId) VALUES ('" + userField.getText() + "'," + String.valueOf(greatestid) + ")";
                                    System.out.println(sql);
                                    System.out.println(sql2);
                                    System.out.println(sql3);
                                    stmt.executeUpdate(sql);
                                    System.out.println("Successfully added user");
                                    stmt.executeUpdate(sql2);
                                    System.out.println("Successfully added the group");
                                    stmt.executeUpdate(sql3);
                                    System.out.println("Successfully assigned new user to new group");
                                    System.out.println(greatestid);

                                    conn.commit();
                                } catch (SQLException ex) {
                                    try {
                                        ex.printStackTrace();
                                        conn.rollback();
                                    } catch (SQLException e2) {
                                        System.out.println("Could not roll back");
                                    } finally {
                                        try {
                                            if (conn != null) {
                                                conn.close();
                                            }
                                            if (data != null) {
                                                data.close();
                                            }
                                        } catch (SQLException exx) {
                                            exx.printStackTrace();
                                        }
                                    }
                                }

                            }
                        });
                    }
                }, c);

                top.setBackground(Color.cyan);
                f.pack();
                f.setVisible(true);

            }

        });
        joinGroup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Connection conn = null;
                ResultSet data = null;
                try {
                    Class.forName(JDBC_DRIVER);
                } catch (Exception ex) {
                    System.out.println("Could not register driver.");
                    System.exit(0);
                }
                try {
      
                    conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);
                    conn.setAutoCommit(false);
                    Statement stmt = conn.createStatement();
                    System.out.println("Creating statement...");
                    stmt = conn.createStatement();
                    String sql;
                    sql = "select distinct TGroup.groupId from TGroup,GroupAssignment where NOT (TGroup.groupId in (select GroupAssignment.groupId from GroupAssignment where userid='" + userChosen + "')) and TGroup.groupId=GroupAssignment.groupId";
                    System.out.println(sql);
                    data = stmt.executeQuery(sql);
                    while (data.next()) {

                        Integer groupId = data.getInt("groupId");
                        joinGroupModel.add(groupId.toString());
                        joinGroup1.setListData(joinGroupModel);
                        System.out.println(groupId);
                    }

                } catch (Exception ex) {
                    System.out.println(ex.toString());
                    System.exit(0);
                } finally {
                    try {
                        if (conn != null) {
                            conn.close();
                            System.out.println("Connection closed");
                        }
                        if (data != null) {
                            data.close();
                        }
                    } catch (SQLException exx) {
                        exx.printStackTrace();
                    }

                }
                JFrame f = new JFrame();
                f.setLayout(new BorderLayout());
                JPanel top = new JPanel();
                top.setBackground(Color.cyan);
                f.add(top, BorderLayout.NORTH);

                JPanel bottom = new JPanel();
                bottom.setBackground(Color.cyan);
                f.add(bottom, BorderLayout.SOUTH);
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                c.anchor = GridBagConstraints.WEST;
                top.add(new JLabel("add me to:"), c);

                c = new GridBagConstraints();
                c.gridx = 1;
                c.gridy = 0;
                c.anchor = GridBagConstraints.WEST;
                groupjoin.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        groupjoin.setText("");
                    }
                });
                top.add(groupjoin, c);

                c = new GridBagConstraints();
                c.gridx = 11;
                c.gridy = 11;
                top.add(groupjoinButton, c);
                JScrollPane scroller = new JScrollPane(joinGroup1);
                scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scroller.setPreferredSize(new Dimension(150, 200));
                bottom.add(scroller, BorderLayout.CENTER);
                f.pack();
                f.setVisible(true);

                joinGroup1.addListSelectionListener(new ListSelectionListener() {

                    public void valueChanged(ListSelectionEvent lse) {
                        try {
                            String groupSelected = joinGroup1.getSelectedValue();
                            groupjoin.setText(groupSelected);
                        } catch (Exception e) {
                        }
                    }
                });
                groupjoinButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent ae) {

                        Connection conn = null;
                        ResultSet data = null;

                        if (joinGroup1.getSelectedIndex() >= 0) {
                            try {
                                Class.forName(JDBC_DRIVER);
                            } catch (Exception ex) {
                                System.out.println("Could not register driver.");
                                System.exit(0);
                            }
                            try {

                                conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);
                                conn.setAutoCommit(false);
                                Statement stmt = conn.createStatement();

                                String sql = "INSERT into GroupAssignment (userid,groupId) VALUES ('" + userChosen + "','" + groupjoin.getText() + "')";

                                System.out.println(sql);

                                stmt.executeUpdate(sql);

                                conn.commit();
                                joinGroupModel.remove(joinGroup1.getSelectedIndex());
                                joinGroup1.setListData(joinGroupModel);
       
                                groupjoin.setText("");


                            } catch (SQLException ex) {
                                try {
                                    ex.printStackTrace();
                                    conn.rollback();
                                } catch (SQLException e2) {
                                    System.out.println("Could not roll back");
                                } finally {
                                    try {
                                        if (conn != null) {
                                            conn.close();
                                        }
                                        if (data != null) {
                                            data.close();
                                        }
                                    } catch (SQLException exx) {
                                        System.out.println(exx.toString());
                                    }
                                }
                            }

                        }

                    }

                });

            }

        });
        addGroup.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                JFrame f = new JFrame();
                f.setLayout(new BorderLayout());

                JPanel top = new JPanel();

                f.add(top, BorderLayout.NORTH);

                JPanel bottom = new JPanel();
                f.add(bottom, BorderLayout.CENTER);

                top.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = 0;
                c.gridy = 0;
                c.insets = (new Insets(5, 0, 0, 10));
                top.add(new JLabel("Group Name", JLabel.RIGHT), c);

                c = new GridBagConstraints();
                c.gridx = 1;
                c.gridy = 0;
                c.insets = (new Insets(5, 0, 0, 10));
                c.anchor = GridBagConstraints.WEST;
                c.fill = GridBagConstraints.HORIZONTAL;
                groupField.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        groupField.setText("");
                    }
                });
                top.add(groupField, c);
                top.setBackground(Color.cyan);
                bottom.add(new JButton() {
                    {
                        this.setText("Add group");
                        this.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                Connection conn = null;
                                ResultSet data = null;
                                try {
                                    Class.forName(JDBC_DRIVER);
                                } catch (Exception ex) {
                                    System.out.println("Could not register driver.");
                                    System.exit(0);
                                }
                                try {

                                    conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);
                                    conn.setAutoCommit(false);
                                    Statement stmt = conn.createStatement();

                                    System.out.println("Creating statement...");

                                    int greatestid = 0;
                                    ResultSet greatest = stmt.executeQuery("SELECT MAX(groupId) AS max FROM TGroup;");
                                    while (greatest.next()) {
                                        greatestid = greatest.getInt("max");
                                        greatestid = greatestid + 1;
                                    }

                                    ///use a transaction!
                                    String sql;
                                    String sql2;
                                    sql = "insert into TGroup (groupId,name) VALUES (" + String.valueOf(greatestid) + ",'" + groupField.getText() + "')";
                                    sql2 = "insert into GroupAssignment (groupId,userid) VALUES (" + String.valueOf(greatestid) + ",'" + userChosen +"')";
                                    System.out.println(sql);

                                    stmt.executeUpdate(sql);
                                    stmt.executeUpdate(sql2);

                                    System.out.println("Successfully added the group");

                                    conn.commit();
                                    groupListModel.add(String.valueOf(greatestid));
                                    groupList.setListData(groupListModel);
                                } catch (SQLException ex) {
                                    try {
                                        ex.printStackTrace();
                                        conn.rollback();
                                    } catch (SQLException e2) {
                                        System.out.println("Could not roll back");
                                    } finally {
                                        try {
                                            if (conn != null) {
                                                conn.close();
                                            }
                                            if (data != null) {
                                                data.close();
                                            }
                                        } catch (SQLException exx) {
                                            System.out.println(exx.toString());
                                        }
                                    }
                                }

                            }
                        });
                    }
                }, c);

                //f.setPreferredSize(new Dimension(200,1));
                bottom.setBackground(Color.cyan);
                f.pack();
                f.setVisible(true);

            }

        });

        this.setJMenuBar(menuBar);

        JPanel topp = new JPanel();
        this.add(topp, BorderLayout.NORTH);
        topp.setBackground(Color.cyan);

        this.add(buttons, BorderLayout.WEST);
        this.add(groupMenu, BorderLayout.EAST);
        this.add(bottom, BorderLayout.SOUTH);

        //To Do Label and box         
        topp.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        JLabel toDo = new JLabel("To Do ", JLabel.RIGHT);
        toDo.setFont(new Font("Monotype Corsiva", Font.BOLD, 35));
        topp.add(toDo, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = (new Insets(5, 0, 0, 10));
        todoField.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                todoField.setText("");
            }
        });
        topp.add(todoField, c);

        //Details label and box
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        JLabel description = new JLabel("Description:", JLabel.CENTER);
        description.setFont(new Font("Monotype Corsiva", Font.BOLD, 25));
        topp.add(description, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = (new Insets(5, 0, 0, 10));

        descField.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                descField.setText("");
            }
        });
        topp.add(descField, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;

        JLabel completionDate = new JLabel("Completion Date:(yyyy-mm-dd)", JLabel.RIGHT);
        completionDate.setFont(new Font("Monotype Corsiva", Font.BOLD, 10));
        topp.add(completionDate, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = (new Insets(5, 0, 0, 10));

        topp.add(CompletionDateField, c);
        CompletionDateField.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                CompletionDateField.setText("");
            }
        });



        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 4;
        JLabel groups = new JLabel("Groups I'm in", JLabel.RIGHT);

        topp.add(groups, c);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 4;
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = (new Insets(5, 0, 0, 10));

        topp.add(cb, c);
        cb.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                Connection conn = null;
                ResultSet data = null;
                System.out.println("NO");

                try {
                    Class.forName(JDBC_DRIVER);
                } catch (Exception ex) {
                    System.out.println("Could not register driver.");
                    System.exit(0);
                }
                try {
                    conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);
    
                    Statement stmt = conn.createStatement();
                    System.out.println("Creating statement...");
                    stmt = conn.createStatement();
                    String sql;
                    myListModel.clear();
                    String sql1;
                    String value = cb.getSelectedItem().toString();
                    sql = "SELECT * from GroupTask,GroupAssignment where GroupAssignment.userid='" + userChosen + "'and GroupTask.groupId='" + value + "' and GroupTask.groupId = GroupAssignment.groupId";
      
                    System.out.println(sql);
    
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        //
                        String title = rs.getString("title");
                        String desc = rs.getString("description");
                        String completionDate = rs.getString("completion_date");
                        myListModel.add(new ToDo(title, desc, completionDate));
                        myList.setListData(myListModel);

                    }

                } catch (Exception ex) {
                    System.out.println("Couldn't log in");
                    System.exit(0);
                } finally {
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                        if (data != null) {
                            data.close();
                        }
                    } catch (SQLException ex) {
                        System.out.println("Couldn't log in");
                    }

                }

            }
        }
        );

        //"Add" Button
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 4;
        c.anchor = GridBagConstraints.EAST;

        buttons.add(new JButton() {
            {
                this.setText("Add item");
                this.addActionListener(new ActionListener() {
                    
                    public void actionPerformed(ActionEvent e) {
                        myListModel.add(new ToDo(todoField.getText(), descField.getText(), CompletionDateField.getText()));
                        myList.setListData(myListModel);
                        int listLength = myList.getModel().getSize();
                        try {
                            Class.forName(JDBC_DRIVER);
                        } catch (Exception ex) {
                            System.out.println("Could not register driver.");
                            System.exit(0);
                        }
                        try {
                            Connection conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);
                            Statement stmt = conn.createStatement();
                            ResultSet data = stmt.executeQuery("Select * from User");
                            System.out.println("Successful");

                            System.out.println("Creating statement...");
                            stmt = conn.createStatement();
                            //get the fields
                            long greatestTask = 0;
                            ResultSet greatest = stmt.executeQuery("SELECT MAX(taskid) AS maxVal FROM GroupTask;");
                            while (greatest.next()) {
                                greatestTask = greatest.getInt("maxVal");
                                greatestTask = greatestTask + 1;

                            }
                            System.out.println(greatestTask);
                            String value = cb.getSelectedItem().toString();
                            String str = "INSERT INTO GroupTask(taskId,title, description,groupId) VALUES ('" + String.valueOf(greatestTask) + "','" + todoField.getText() + "','" + descField.getText() + "', '" + value + "')";
                            System.out.println(str);
                            stmt.executeUpdate(str);

                            stmt.close();
                            conn.close();
                        } catch (Exception ex) {
                            System.out.println("Couldn't log in");
                            System.exit(0);
                        }

                    }
                });
            }
        }, c);
        //delete button
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 4;


        buttons.add(deleteButton, c);

        c = new GridBagConstraints();
        c.gridx = 3;
        c.gridy = 4;


        buttons.add(updateButton, c);
        myList.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent lse) {
                try {
                    ToDo toDoSelected = myList.getSelectedValue();
                    todoField.setText(toDoSelected.getTitle());
                    descField.setText(toDoSelected.getDesc());
                    CompletionDateField.setText(toDoSelected.getcompletionDate());
                } catch (Exception e) {
                }
                updateButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent ae) {
                        if (myList.getSelectedIndex() >= 0) {
                            try {
                                Class.forName(JDBC_DRIVER);
                            } catch (Exception ex) {
                                System.out.println("Could not register driver.");
                                System.exit(0);
                            }
                            try {
                                Connection conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);
                                Statement stmt = conn.createStatement();
                                ResultSet data = stmt.executeQuery("Select * from User");
                                System.out.println("successful");
                                System.out.println("Creating the statement..");
                                stmt = conn.createStatement();
                                String value = cb.getSelectedItem().toString();
                                String str = "UPDATE GroupTask set description='" + descField.getText() + " ',title='" + todoField.getText() + "' where title='" + myList.getSelectedValue().getTitle() + "' and description='" + myList.getSelectedValue().getDesc() + "' and groupId='" + value + "'";

                                stmt.executeUpdate(str);
                                myListModel.remove(myList.getSelectedIndex());
                                myListModel.add(new ToDo(todoField.getText(), descField.getText(), CompletionDateField.getText()));
                                myList.setListData(myListModel);
                                todoField.setText("");
                                descField.setText("");
                                CompletionDateField.setText("");

                            } catch (Exception ex) {
                                System.out.println(ex.toString());
                                System.exit(0);
                            }
                        }

                    }
                });

                deleteButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent ae) {
                        if (myList.getSelectedIndex() >= 0) {

                            try {
                                Class.forName(JDBC_DRIVER);
                            } catch (Exception ex) {
                                System.out.println("Could not register driver.");
                                System.exit(0);
                            }
                            try {
                                Connection conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);

                                Statement stmt = conn.createStatement();
                                ResultSet data = stmt.executeQuery("Select * from User");
                                System.out.println("Successful");

                                System.out.println("Creating statement...");
                                stmt = conn.createStatement();
                               
                                String value = cb.getSelectedItem().toString();
                                String str = "DELETE FROM GroupTask where title='" + todoField.getText() + "' and description='" + descField.getText() + "' and groupId='" + value + "'";
                                System.out.println(str);
                                stmt.executeUpdate(str);
                                myListModel.remove(myList.getSelectedIndex());
                                myList.setListData(myListModel);
                                todoField.setText("");
                                descField.setText("");
                                CompletionDateField.setText("");
                            } catch (Exception ex) {
                                System.out.println("Couldn't log in");
                                System.exit(0);
                            }
                        }

                    }
                });
            }

        });

        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 4;
        buttons.add(refreshButton, c);
        refreshButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {

                myListModel = LoadList(userChosen);
                userListModel = LoadUsers();
                groupListModel = LoadGroups(userChosen);
                String[] array = new String[groupListModel.size()];
                String[] array2 = new String[userListModel.size()];
                userListModel.copyInto(array2);
                groupListModel.copyInto(array);
                cb.setModel(new DefaultComboBoxModel(array));
                groupList.setListData(groupListModel);
                userList.setListData(userListModel);

            }

        });
        c = new GridBagConstraints();
        c.gridx = 4;
        c.gridy = 4;
        buttons.add(completeButton, c);
        completeButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                if (myList.getSelectedIndex() >= 0) {
                    try {
                        Class.forName(JDBC_DRIVER);
                    } catch (Exception ex) {
                        System.out.println("Could not register driver.");
                        System.exit(0);
                    }
                    try {
                        Connection conn = DriverManager.getConnection(DB_URL, MASTER_USER, PASSWORD);

                        Statement stmt = conn.createStatement();
                        ResultSet data = stmt.executeQuery("Select * from User");
                        System.out.println("successful");
                        System.out.println("Creating the statement..");
                        stmt = conn.createStatement();
                        String value2 = cb.getSelectedItem().toString();
                        String str = "UPDATE GroupTask set description='" + descField.getText() + "',completion_date='" + CompletionDateField.getText() + "',title='" + todoField.getText() + "',userid='" + userChosen + "' where title='" + myList.getSelectedValue().getTitle() + "' and description='" + myList.getSelectedValue().getDesc() + "' and groupId='" + value2 + "'";
                        System.out.println(str);
                        stmt.executeUpdate(str);
                        myListModel.remove(myList.getSelectedIndex());
                        myListModel.add(new ToDo(todoField.getText(), descField.getText(), CompletionDateField.getText()));
                        myList.setListData(myListModel);
                        todoField.setText("");
                        descField.setText("");
                        CompletionDateField.setText("");

                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                        System.exit(0);
                    }
                }
            }
        });

        bottom.setLayout(new BorderLayout());
        JScrollPane scrollers = new JScrollPane(myList);
        scrollers.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollers.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        bottom.add(scrollers, BorderLayout.CENTER);
        this.setVisible(true);

    }

    public static void main(String[] args) {
        // TODO code application logic here
        ToDoApp todo = new ToDoApp();
        todo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        todo.pack();
        todo.setVisible(true);
    }

}
