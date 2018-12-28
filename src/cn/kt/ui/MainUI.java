package cn.kt.ui;

import cn.kt.generate.Generate;
import cn.kt.model.Config;
import cn.kt.model.TableInfo;
import cn.kt.setting.PersistentConfig;
import cn.kt.util.JTextFieldHintListener;
import cn.kt.util.StringUtils;
import com.intellij.database.psi.DbTable;
import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPackage;
import com.intellij.ui.components.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 插件主界面
 * Created by kangtian on 2018/8/1.
 */
public class MainUI extends JFrame {


    private AnActionEvent anActionEvent;
    private Project project;
    private PersistentConfig persistentConfig;
    private PsiElement[] psiElements;
    private Map<String, Config> initConfigMap;
    private Map<String, Config> historyConfigList;
    private Config config;


    private JPanel contentPane = new JBPanel<>();
    private JButton buttonOK = new JButton("ok");
    private JButton buttonCancel = new JButton("cancle");
    private JButton selectConfigBtn = new JButton("SELECT");
    private JButton deleteConfigBtn = new JButton("DELETE");


    private JTextField tableNameField = new JTextField(10);
    private JBTextField modelPackageField = new JBTextField(12);
    private JBTextField daoPackageField = new JBTextField(12);
    private JBTextField xmlPackageField = new JBTextField(12);
    private JTextField mapperNameField = new JTextField(10);
    private JTextField modelNameField = new JTextField(10);
    private JTextField keyField = new JTextField(10);

    private TextFieldWithBrowseButton projectFolderBtn = new TextFieldWithBrowseButton();
    private TextFieldWithBrowseButton modelFolderBtn = new TextFieldWithBrowseButton();
    private TextFieldWithBrowseButton daoFolderBtn = new TextFieldWithBrowseButton();
    private TextFieldWithBrowseButton xmlFolderBtn = new TextFieldWithBrowseButton();
    private JTextField modelMvnField = new JBTextField(15);
    private JTextField daoMvnField = new JBTextField(15);
    private JTextField xmlMvnField = new JBTextField(15);
    private JButton setProjectBtn = new JButton("Set-Project-Path");

    private JCheckBox offsetLimitBox = new JCheckBox("Page(分页)");
    private JCheckBox commentBox = new JCheckBox("comment(实体注释)");
    private JCheckBox overrideXMLBox = new JCheckBox("Overwrite-Xml");
    private JCheckBox needToStringHashcodeEqualsBox = new JCheckBox("toString/hashCode/equals");
    private JCheckBox useSchemaPrefixBox = new JCheckBox("Use-Schema(使用Schema前缀)");
    private JCheckBox needForUpdateBox = new JCheckBox("Add-ForUpdate(select增加ForUpdate)");
    private JCheckBox annotationDAOBox = new JCheckBox("Repository-Annotation(Repository注解)");
    private JCheckBox useDAOExtendStyleBox = new JCheckBox("Parent-Interface(公共父接口)");
    private JCheckBox jsr310SupportBox = new JCheckBox("JSR310: Date and Time API");
    private JCheckBox annotationBox = new JCheckBox("JPA-Annotation(JPA注解)");
    private JCheckBox useActualColumnNamesBox = new JCheckBox("Actual-Column(实际的列名)");
    private JCheckBox useTableNameAliasBox = new JCheckBox("Use-Alias(启用别名查询)");
    private JCheckBox useExampleBox = new JCheckBox("Use-Example");
    private JCheckBox mysql_8Box = new JCheckBox("mysql_8");



    public MainUI(AnActionEvent anActionEvent) throws HeadlessException {
        this.anActionEvent = anActionEvent;
        this.project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        this.persistentConfig = PersistentConfig.getInstance(project);
        this.psiElements = anActionEvent.getData(LangDataKeys.PSI_ELEMENT_ARRAY);

        initConfigMap = persistentConfig.getInitConfig();
        historyConfigList = persistentConfig.getHistoryConfigList();



        setTitle("mybatis generate tool");
        setPreferredSize(new Dimension(1200, 700));//设置大小
        setLocation(120, 100);
        pack();
        setVisible(true);
        getRootPane().setDefaultButton(buttonOK);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        PsiElement psiElement = psiElements[0];
        TableInfo tableInfo = new TableInfo((DbTable) psiElement);
        String tableName = tableInfo.getTableName();
        String modelName = StringUtils.dbStringToCamelStyle(tableName);
        String primaryKey = "";
        if(tableInfo.getPrimaryKeys().size()>0){
            primaryKey = tableInfo.getPrimaryKeys().get(0);
        }
        String projectFolder = project.getBasePath();


        if (psiElements.length > 1) {//多表时，只使用默认配置
            if (initConfigMap != null) {
                config = initConfigMap.get("initConfig");
            }
        } else {
            if (initConfigMap != null) {//单表时，优先使用已经存在的配置
                config = initConfigMap.get("initConfig");
                ;
            }
            if (historyConfigList == null) {
                historyConfigList = new HashMap<>();
            } else {
                if (historyConfigList.containsKey(tableName)) {
                    config = historyConfigList.get(tableName);
                }
            }
        }


        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());

        JPanel paneMain = new JPanel(new GridLayout(2, 1, 3, 3));//主要设置显示在这里
        JPanel paneMainTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paneMainTop.setBorder(new EmptyBorder(10, 30, 5, 40));

        JPanel paneMainTop1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel paneMainTop2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel paneMainTop3 = new JPanel(new GridLayout(4, 1, 3, 3));
        paneMainTop.add(paneMainTop1);
        paneMainTop.add(paneMainTop2);
        paneMainTop.add(paneMainTop3);


        JPanel paneLeft1 = new JPanel();
        paneLeft1.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel tablejLabel = new JLabel("table  name:");
        tablejLabel.setSize(new Dimension(20, 30));
        paneLeft1.add(tablejLabel);
        if (psiElements.length > 1) {
            tableNameField.addFocusListener(new JTextFieldHintListener(tableNameField, "eg:db_table"));
        } else {
            tableNameField.setText(tableName);
        }
        paneLeft1.add(tableNameField);

        JPanel paneLeft2 = new JPanel();
        paneLeft2.setLayout(new FlowLayout(FlowLayout.CENTER));
        paneLeft2.add(new JLabel("主键（选填）:"));
        if (psiElements.length > 1) {
            keyField.addFocusListener(new JTextFieldHintListener(keyField, "eg:primary key"));
        } else {
            keyField.setText(primaryKey);
        }
        paneLeft2.add(keyField);

        JPanel paneRight1 = new JPanel();
        paneRight1.setLayout(new FlowLayout(FlowLayout.CENTER));
        paneRight1.add(new JLabel("model   :"));
        if (psiElements.length > 1) {
            modelNameField.addFocusListener(new JTextFieldHintListener(modelNameField, "eg:DbTable"));
        } else {
            modelNameField.setText(modelName);
        }
        paneRight1.add(modelNameField);

        JPanel paneRight2 = new JPanel();
        paneRight2.setLayout(new FlowLayout(FlowLayout.CENTER));
        paneRight2.add(new JLabel("dao name:"));
        if (psiElements.length > 1) {
            if (config != null && !StringUtils.isEmpty(config.getDaoPostfix())) {
                mapperNameField.addFocusListener(new JTextFieldHintListener(mapperNameField, "eg:DbTable" + config.getDaoPostfix()));
            } else {
                mapperNameField.addFocusListener(new JTextFieldHintListener(mapperNameField, "eg:DbTable" + "Dao"));
            }
        } else {
            if (config != null && !StringUtils.isEmpty(config.getDaoPostfix())) {
                mapperNameField.setText(modelName + config.getDaoPostfix());
            } else {
                mapperNameField.setText(modelName + "Dao");
            }
        }
        paneRight2.add(mapperNameField);

        paneMainTop1.add(paneLeft1);
        paneMainTop1.add(paneLeft2);
        paneMainTop1.add(paneRight1);
        paneMainTop1.add(paneRight2);

        JPanel modelPackagePanel = new JPanel();
        modelPackagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JBLabel labelLeft4 = new JBLabel("model package:");
        modelPackagePanel.add(labelLeft4);
        if (config != null && !StringUtils.isEmpty(config.getModelPackage())) {
            modelPackageField.setText(config.getModelPackage());
        } else {
            modelPackageField.setText("generator");
        }
        modelPackagePanel.add(modelPackageField);
        JButton packageBtn1 = new JButton("...");
        packageBtn1.addActionListener(actionEvent -> {
            final PackageChooserDialog chooser = new PackageChooserDialog("chooser model package", project);
            chooser.selectPackage(modelPackageField.getText());
            chooser.show();
            final PsiPackage psiPackage = chooser.getSelectedPackage();
            String packageName = psiPackage == null ? null : psiPackage.getQualifiedName();
            modelPackageField.setText(packageName);
            MainUI.this.toFront();
        });
        modelPackagePanel.add(packageBtn1);


        JPanel daoPackagePanel = new JPanel();
        daoPackagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelLeft5 = new JLabel("dao package:");
        daoPackagePanel.add(labelLeft5);


        if (config != null && !StringUtils.isEmpty(config.getDaoPackage())) {
            daoPackageField.setText(config.getDaoPackage());
        } else {
            daoPackageField.setText("generator");
        }
        daoPackagePanel.add(daoPackageField);

        JButton packageBtn2 = new JButton("...");
        packageBtn2.addActionListener(actionEvent -> {
            final PackageChooserDialog chooser = new PackageChooserDialog("choose dao package", project);
            chooser.selectPackage(daoPackageField.getText());
            chooser.show();
            final PsiPackage psiPackage = chooser.getSelectedPackage();
            String packageName = psiPackage == null ? null : psiPackage.getQualifiedName();
            daoPackageField.setText(packageName);
            MainUI.this.toFront();
        });
        daoPackagePanel.add(packageBtn2);

        JPanel xmlPackagePanel = new JPanel();
        xmlPackagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelLeft6 = new JLabel("xml package:");
        xmlPackagePanel.add(labelLeft6);
        if (config != null && !StringUtils.isEmpty(config.getXmlPackage())) {
            xmlPackageField.setText(config.getXmlPackage());
        } else {
            xmlPackageField.setText("generator");
        }
        xmlPackagePanel.add(xmlPackageField);

        paneMainTop2.add(modelPackagePanel);
        paneMainTop2.add(daoPackagePanel);
        paneMainTop2.add(xmlPackagePanel);


        JPanel projectFolderPanel = new JPanel();
        projectFolderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel projectLabel = new JLabel("project folder:");
        projectFolderPanel.add(projectLabel);
        projectFolderBtn.setTextFieldPreferredWidth(45);
        if (config != null && !StringUtils.isEmpty(config.getProjectFolder())) {
            projectFolderBtn.setText(config.getProjectFolder());
        } else {
            projectFolderBtn.setText(projectFolder);
        }
        projectFolderBtn.addBrowseFolderListener(new TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                super.actionPerformed(e);
                projectFolderBtn.setText(projectFolderBtn.getText().replaceAll("\\\\", "/"));
            }
        });
        projectFolderPanel.add(projectFolderBtn);
        projectFolderPanel.add(setProjectBtn);


        JPanel modelFolderPanel = new JPanel();
        modelFolderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        modelFolderPanel.add(new JLabel("model  folder:"));

        modelFolderBtn.setTextFieldPreferredWidth(45);
        if (config != null && !StringUtils.isEmpty(config.getModelTargetFolder())) {
            modelFolderBtn.setText(config.getModelTargetFolder());
        } else {
            modelFolderBtn.setText(projectFolder);
        }
        modelFolderBtn.addBrowseFolderListener(new TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                super.actionPerformed(e);
                modelFolderBtn.setText(modelFolderBtn.getText().replaceAll("\\\\", "/"));
            }
        });
        modelFolderPanel.add(modelFolderBtn);
        modelFolderPanel.add(new JLabel("mvn path:"));
        modelMvnField.setText("src/main/java");
        modelFolderPanel.add(modelMvnField);


        JPanel daoFolderPanel = new JPanel();
        daoFolderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        daoFolderPanel.add(new JLabel("dao     folder:"));
        daoFolderBtn.setTextFieldPreferredWidth(45);
        if (config != null && !StringUtils.isEmpty(config.getDaoTargetFolder())) {
            daoFolderBtn.setText(config.getDaoTargetFolder());
        } else {
            daoFolderBtn.setText(projectFolder);
        }
        daoFolderBtn.addBrowseFolderListener(new TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                super.actionPerformed(e);
                daoFolderBtn.setText(daoFolderBtn.getText().replaceAll("\\\\", "/"));
            }
        });
        daoFolderPanel.add(daoFolderBtn);
        daoFolderPanel.add(new JLabel("mvn path:"));
        daoMvnField.setText("src/main/java");
        daoFolderPanel.add(daoMvnField);


        JPanel xmlFolderPanel = new JPanel();
        xmlFolderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        xmlFolderPanel.add(new JLabel("xml     folder:"));

        xmlFolderBtn.setTextFieldPreferredWidth(45);
        if (config != null && !StringUtils.isEmpty(config.getXmlTargetFolder())) {
            xmlFolderBtn.setText(config.getXmlTargetFolder());
        } else {
            xmlFolderBtn.setText(projectFolder);
        }
        xmlFolderBtn.addBrowseFolderListener(new TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor()) {
        });
        xmlFolderPanel.add(xmlFolderBtn);
        xmlFolderPanel.add(new JLabel("mvn path:"));
        xmlMvnField.setText("src/main/resources");
        xmlFolderPanel.add(xmlMvnField);

        paneMainTop3.add(projectFolderPanel);
        paneMainTop3.add(modelFolderPanel);
        paneMainTop3.add(daoFolderPanel);
        paneMainTop3.add(xmlFolderPanel);

        if (config == null) {
            offsetLimitBox.setSelected(true);
            commentBox.setSelected(true);
            overrideXMLBox.setSelected(true);
            needToStringHashcodeEqualsBox.setSelected(true);
            useSchemaPrefixBox.setSelected(true);
            useExampleBox.setSelected(true);

        } else {
            if (config.isOffsetLimit()) {
                offsetLimitBox.setSelected(true);
            }
            if (config.isComment()) {
                commentBox.setSelected(true);
            }

            if (config.isOverrideXML()) {
                overrideXMLBox.setSelected(true);
            }
            if (config.isNeedToStringHashcodeEquals()) {
                needToStringHashcodeEqualsBox.setSelected(true);
            }
            if (config.isUseSchemaPrefix()) {
                useSchemaPrefixBox.setSelected(true);
            }
            if (config.isNeedForUpdate()) {
                needForUpdateBox.setSelected(true);
            }
            if (config.isAnnotationDAO()) {
                annotationDAOBox.setSelected(true);
            }
            if (config.isUseDAOExtendStyle()) {
                useDAOExtendStyleBox.setSelected(true);
            }
            if (config.isJsr310Support()) {
                jsr310SupportBox.setSelected(true);
            }
            if (config.isAnnotation()) {
                annotationBox.setSelected(true);
            }
            if (config.isUseActualColumnNames()) {
                useActualColumnNamesBox.setSelected(true);
            }
            if (config.isUseTableNameAlias()) {
                useTableNameAliasBox.setSelected(true);
            }
            if (config.isUseExample()) {
                useExampleBox.setSelected(true);
            }
            if (config.isMysql_8()) {
                mysql_8Box.setSelected(true);
            }
        }


        JBPanel paneMainDown = new JBPanel(new GridLayout(5, 5, 5, 5));
        paneMainDown.setBorder(new EmptyBorder(2, 80, 100, 40));

        paneMainDown.add(offsetLimitBox);
        paneMainDown.add(commentBox);
        paneMainDown.add(overrideXMLBox);
        paneMainDown.add(needToStringHashcodeEqualsBox);
        paneMainDown.add(useSchemaPrefixBox);
        paneMainDown.add(needForUpdateBox);
        paneMainDown.add(annotationDAOBox);
        paneMainDown.add(useDAOExtendStyleBox);
        paneMainDown.add(jsr310SupportBox);
        paneMainDown.add(annotationBox);
        paneMainDown.add(useActualColumnNamesBox);
        paneMainDown.add(useTableNameAliasBox);
        paneMainDown.add(useExampleBox);
        paneMainDown.add(mysql_8Box);

        paneMain.add(paneMainTop);
        paneMain.add(paneMainDown);


        JPanel paneBottom = new JPanel();//确认和取消按钮
        paneBottom.setLayout(new FlowLayout(2));
        paneBottom.add(buttonOK);
        paneBottom.add(buttonCancel);


        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        this.getContentPane().add(Box.createVerticalStrut(10)); //采用x布局时，添加固定宽度组件隔开
        final DefaultListModel defaultListModel = new DefaultListModel();

        Border historyBorder = BorderFactory.createTitledBorder("history config:");
        panelLeft.setBorder(historyBorder);


        if (historyConfigList == null) {
            historyConfigList = new HashMap<>();
        }
        for (String historyConfigName : historyConfigList.keySet()) {
            defaultListModel.addElement(historyConfigName);
        }
        Map<String, Config> finalHistoryConfigList = historyConfigList;

        final JBList fruitList = new JBList(defaultListModel);
        fruitList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fruitList.setSelectedIndex(0);
        fruitList.setVisibleRowCount(25);
        JBScrollPane ScrollPane = new JBScrollPane(fruitList);
        panelLeft.add(ScrollPane);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));

        btnPanel.add(selectConfigBtn);
        btnPanel.add(deleteConfigBtn);
        selectConfigBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String configName = (String) fruitList.getSelectedValue();
                Config selectedConfig = finalHistoryConfigList.get(configName);
                modelPackageField.setText(selectedConfig.getModelPackage());
                daoPackageField.setText(selectedConfig.getDaoPackage());
                xmlPackageField.setText(selectedConfig.getXmlPackage());
                projectFolderBtn.setText(selectedConfig.getProjectFolder());
                modelFolderBtn.setText(selectedConfig.getModelTargetFolder());
                daoFolderBtn.setText(selectedConfig.getDaoTargetFolder());
                xmlFolderBtn.setText(selectedConfig.getXmlTargetFolder());

                offsetLimitBox.setSelected(selectedConfig.isOffsetLimit());
                commentBox.setSelected(selectedConfig.isComment());
                overrideXMLBox.setSelected(selectedConfig.isOverrideXML());
                needToStringHashcodeEqualsBox.setSelected(selectedConfig.isNeedToStringHashcodeEquals());
                useSchemaPrefixBox.setSelected(selectedConfig.isUseSchemaPrefix());
                needForUpdateBox.setSelected(selectedConfig.isNeedForUpdate());
                annotationDAOBox.setSelected(selectedConfig.isAnnotationDAO());
                useDAOExtendStyleBox.setSelected(selectedConfig.isUseDAOExtendStyle());
                jsr310SupportBox.setSelected(selectedConfig.isJsr310Support());
                annotationBox.setSelected(selectedConfig.isAnnotation());
                useActualColumnNamesBox.setSelected(selectedConfig.isUseActualColumnNames());
                useTableNameAliasBox.setSelected(selectedConfig.isUseTableNameAlias());
                useExampleBox.setSelected(selectedConfig.isUseExample());
                mysql_8Box.setSelected(selectedConfig.isMysql_8());

            }
        });
        deleteConfigBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalHistoryConfigList.remove(fruitList.getSelectedValue());
                defaultListModel.removeAllElements();
                for (String historyConfigName : finalHistoryConfigList.keySet()) {
                    defaultListModel.addElement(historyConfigName);
                }
            }
        });
        panelLeft.add(btnPanel);


        contentPane.add(paneMain, BorderLayout.CENTER);
        contentPane.add(paneBottom, BorderLayout.SOUTH);
        contentPane.add(panelLeft, BorderLayout.WEST);

        setContentPane(contentPane);

        setProjectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelFolderBtn.setText(projectFolderBtn.getText());
                daoFolderBtn.setText(projectFolderBtn.getText());
                xmlFolderBtn.setText(projectFolderBtn.getText());
            }
        });

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        try {
            dispose();

            if (psiElements.length == 1) {
                Config generator_config = new Config();
                generator_config.setName(tableNameField.getText());
                generator_config.setTableName(tableNameField.getText());
                generator_config.setProjectFolder(projectFolderBtn.getText());

                generator_config.setModelPackage(modelPackageField.getText());
                generator_config.setModelTargetFolder(modelFolderBtn.getText());
                generator_config.setDaoPackage(daoPackageField.getText());
                generator_config.setDaoTargetFolder(daoFolderBtn.getText());
                generator_config.setXmlPackage(xmlPackageField.getText());
                generator_config.setXmlTargetFolder(xmlFolderBtn.getText());
                generator_config.setDaoName(mapperNameField.getText());
                generator_config.setModelName(modelNameField.getText());
                generator_config.setPrimaryKey(keyField.getText());

                generator_config.setOffsetLimit(offsetLimitBox.getSelectedObjects() != null);
                generator_config.setComment(commentBox.getSelectedObjects() != null);
                generator_config.setOverrideXML(overrideXMLBox.getSelectedObjects() != null);
                generator_config.setNeedToStringHashcodeEquals(needToStringHashcodeEqualsBox.getSelectedObjects() != null);
                generator_config.setUseSchemaPrefix(useSchemaPrefixBox.getSelectedObjects() != null);
                generator_config.setNeedForUpdate(needForUpdateBox.getSelectedObjects() != null);
                generator_config.setAnnotationDAO(annotationDAOBox.getSelectedObjects() != null);
                generator_config.setUseDAOExtendStyle(useDAOExtendStyleBox.getSelectedObjects() != null);
                generator_config.setJsr310Support(jsr310SupportBox.getSelectedObjects() != null);
                generator_config.setAnnotation(annotationBox.getSelectedObjects() != null);
                generator_config.setUseActualColumnNames(useActualColumnNamesBox.getSelectedObjects() != null);
                generator_config.setUseTableNameAlias(useTableNameAliasBox.getSelectedObjects() != null);
                generator_config.setUseExample(useExampleBox.getSelectedObjects() != null);
                generator_config.setMysql_8(mysql_8Box.getSelectedObjects() != null);

                generator_config.setModelMvnPath(modelMvnField.getText());
                generator_config.setDaoMvnPath(daoMvnField.getText());
                generator_config.setXmlMvnPath(xmlMvnField.getText());


                new Generate(generator_config).execute(anActionEvent);
            } else {
                for (PsiElement psiElement : psiElements) {
                    TableInfo tableInfo = new TableInfo((DbTable) psiElement);
                    String tableName = tableInfo.getTableName();
                    String modelName = StringUtils.dbStringToCamelStyle(tableName);
                    String primaryKey = "";
                    if (tableInfo.getPrimaryKeys() != null && tableInfo.getPrimaryKeys().size() != 0) {
                        primaryKey = tableInfo.getPrimaryKeys().get(0);
                    }
                    Config generator_config = new Config();
                    generator_config.setName(tableName);
                    generator_config.setTableName(tableName);
                    generator_config.setProjectFolder(projectFolderBtn.getText());

                    generator_config.setModelPackage(modelPackageField.getText());
                    generator_config.setModelTargetFolder(modelFolderBtn.getText());
                    generator_config.setDaoPackage(daoPackageField.getText());
                    generator_config.setDaoTargetFolder(daoFolderBtn.getText());
                    generator_config.setXmlPackage(xmlPackageField.getText());
                    generator_config.setXmlTargetFolder(xmlFolderBtn.getText());

                    if (this.config != null) {
                        generator_config.setDaoName(modelName + this.config.getDaoPostfix());
                    } else {
                        generator_config.setDaoName(modelName + "Dao");
                    }
                    generator_config.setModelName(modelName);
                    generator_config.setPrimaryKey(primaryKey);

                    generator_config.setOffsetLimit(offsetLimitBox.getSelectedObjects() != null);
                    generator_config.setComment(commentBox.getSelectedObjects() != null);
                    generator_config.setOverrideXML(overrideXMLBox.getSelectedObjects() != null);
                    generator_config.setNeedToStringHashcodeEquals(needToStringHashcodeEqualsBox.getSelectedObjects() != null);
                    generator_config.setUseSchemaPrefix(useSchemaPrefixBox.getSelectedObjects() != null);
                    generator_config.setNeedForUpdate(needForUpdateBox.getSelectedObjects() != null);
                    generator_config.setAnnotationDAO(annotationDAOBox.getSelectedObjects() != null);
                    generator_config.setUseDAOExtendStyle(useDAOExtendStyleBox.getSelectedObjects() != null);
                    generator_config.setJsr310Support(jsr310SupportBox.getSelectedObjects() != null);
                    generator_config.setAnnotation(annotationBox.getSelectedObjects() != null);
                    generator_config.setUseActualColumnNames(useActualColumnNamesBox.getSelectedObjects() != null);
                    generator_config.setUseTableNameAlias(useTableNameAliasBox.getSelectedObjects() != null);
                    generator_config.setUseExample(useExampleBox.getSelectedObjects() != null);
                    generator_config.setMysql_8(mysql_8Box.getSelectedObjects() != null);

                    generator_config.setModelMvnPath(modelMvnField.getText());
                    generator_config.setDaoMvnPath(daoMvnField.getText());
                    generator_config.setXmlMvnPath(xmlMvnField.getText());


                    new Generate(generator_config).execute(anActionEvent);
                }

            }


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
