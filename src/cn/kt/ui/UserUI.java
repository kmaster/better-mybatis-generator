package cn.kt.ui;

import cn.kt.model.Config;
import cn.kt.model.DbType;
import cn.kt.model.User;
import cn.kt.setting.PersistentConfig;
import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.credentialStore.Credentials;
import com.intellij.ide.passwordSafe.PasswordSafe;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

/**
 * 账号密码输入界面
 * Created by kangtian on 2018/8/3.
 */
public class UserUI extends JFrame {

    private AnActionEvent anActionEvent;
    private Project project;
    private PersistentConfig persistentConfig;
    private Config config;
    private JPanel contentPanel = new JBPanel<>();
    private JPanel btnPanel = new JBPanel<>();
    private JPanel filedPanel = new JBPanel<>();
    private JButton buttonOK = new JButton("ok");
    private JButton buttonCancel = new JButton("cancle");

    public JTextField usernameField = new JBTextField(20);
    public JTextField passwordField = new JBTextField(20);


    public UserUI(String driverClass, String address, AnActionEvent anActionEvent, Config config) throws HeadlessException {
        this.anActionEvent = anActionEvent;
        this.project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        this.persistentConfig = PersistentConfig.getInstance(project);
        this.config = config;
        setTitle("set username and password");
        setPreferredSize(new Dimension(400, 180));//设置大小
        setLocation(550, 350);
        pack();
        setVisible(true);
        getRootPane().setDefaultButton(buttonOK);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setContentPane(contentPanel);

        filedPanel.setLayout(new GridLayout(2, 1));
        filedPanel.setBorder(new EmptyBorder(8, 2, 2, 2));

        JPanel panel1 = new JBPanel<>();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
        panel1.setBorder(new EmptyBorder(2, 2, 2, 2));

        JPanel panel2 = new JBPanel<>();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
        panel2.setBorder(new EmptyBorder(2, 2, 2, 2));

        panel1.add(new JLabel("username:"));
        panel1.add(usernameField);
        panel2.add(new JLabel("password:"));
        panel2.add(passwordField);

        filedPanel.add(panel1);
        filedPanel.add(panel2);
        contentPanel.add(filedPanel);

        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.setBorder(new EmptyBorder(1, 5, 1, 5));
        btnPanel.add(buttonOK);
        btnPanel.add(buttonCancel);
        contentPanel.add(btnPanel);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(driverClass, address, persistentConfig, project);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK(String driverClass, String address, PersistentConfig persistentConfig, Project project) {
        try {
            String originAddress = address;
            Connection conn = null;
            String DbTypeName = "";
            Boolean isMySQL_8 = config.isMysql_8();
            try {
                if (driverClass.contains("oracle")) {
                    DbTypeName = "oracle";
                    Class.forName(DbType.Oracle.getDriverClass());
                } else if (driverClass.contains("mysql")) {
                    DbTypeName = "mysql";
                    if (!isMySQL_8) {
                        Class.forName(DbType.MySQL.getDriverClass());
                    } else {
                        Class.forName(DbType.MySQL_8.getDriverClass());
                    }
                } else if (driverClass.contains("postgresql")) {
                    DbTypeName = "postgresql";
                    Class.forName(DbType.PostgreSQL.getDriverClass());
                } else if (driverClass.contains("sqlserver")) {
                    DbTypeName = "sqlserver";
                    Class.forName(DbType.SqlServer.getDriverClass());
                } else if (driverClass.contains("sqlite")) {
                    DbTypeName = "sqlite";
                    Class.forName(DbType.Sqlite.getDriverClass());
                } else if (driverClass.contains("mariadb")) {
                    DbTypeName = "mariadb";
                    Class.forName(DbType.MariaDB.getDriverClass());
                }

                conn = DriverManager.getConnection(address, usernameField.getText(), passwordField.getText());

            } catch (Exception ex) {
                Messages.showMessageDialog(project, "Failed to connect to " + DbTypeName + " database,please check username and password,or mysql is version 8?" + isMySQL_8, "Test connection", Messages.getInformationIcon());
//                new UserUI(driverClass, address, anActionEvent, config);
                return;
            } finally {
                if (conn != null) {
                    conn.close();
                }
                dispose();
            }

            Map<String, User> users = persistentConfig.getUsers();
            if (users == null) {
                users = new HashMap<>();
            }
            users.put(originAddress, new User(usernameField.getText()));
            CredentialAttributes attributes = new CredentialAttributes("better-mybatis-generator-" + address, usernameField.getText(), this.getClass(), false);
            Credentials saveCredentials = new Credentials(attributes.getUserName(), passwordField.getText());
            PasswordSafe.getInstance().set(attributes, saveCredentials);

            persistentConfig.setUsers(users);


            VirtualFile baseDir = project.getBaseDir();
            baseDir.refresh(false, true);
            new MainUI(anActionEvent);

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            dispose();
        }
    }

    private void onCancel() {
        dispose();
    }
}
