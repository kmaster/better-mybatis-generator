package cn.kt.ui;

import cn.kt.model.Config;
import cn.kt.setting.PersistentConfig;
import cn.kt.util.JTextFieldHintListener;
import com.intellij.ide.util.PackageChooserDialog;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.psi.PsiPackage;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * 设置界面
 * Created by kangtian on 2018/8/3.
 */
public class SettingUI extends JDialog {
    public JPanel mainPanel = new JBPanel<>(new GridLayout(2, 1));


    private JTextField tableNameField  = new JTextField(10);
    private JBTextField modelPackageField = new JBTextField(12);
    private JBTextField daoPackageField = new JBTextField(12);
    private JBTextField xmlPackageField = new JBTextField(12);
    private JTextField mapperNameField  = new JTextField(10);
    private JTextField domainNameField = new JTextField(10);
    private JTextField keyField = new JTextField(12);

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

    private PersistentConfig config;
    public SettingUI() {
        setContentPane(mainPanel);
    }



    public void createUI(Project project) {
        String projectFolder = project.getBasePath();
        mainPanel.setPreferredSize(new Dimension(0,0));
        JPanel paneMainTop = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel paneMainTop1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel paneMainTop2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel paneMainTop3 = new JPanel(new GridLayout(4, 1, 3, 3));
        paneMainTop.add(paneMainTop1);
        paneMainTop.add(paneMainTop2);
        paneMainTop.add(paneMainTop3);

        JPanel paneLeft1 = new JPanel();
        paneLeft1.setLayout(new FlowLayout(FlowLayout.CENTER));
        paneLeft1.add(new JLabel("table name:"));
        tableNameField.setText("example: db_table");
        tableNameField.setEnabled(false);
        paneLeft1.add(tableNameField);

        JPanel paneLeft2 = new JPanel();
        paneLeft2.setLayout(new FlowLayout(FlowLayout.CENTER));
        paneLeft2.add(new JLabel("primary key:"));
        keyField.setText("default primary key");
        keyField.setEnabled(false);
        paneLeft2.add(keyField);

        JPanel paneRight1 = new JPanel();
        paneRight1.setLayout(new FlowLayout(FlowLayout.CENTER));
        paneRight1.add(new JLabel("model name:"));
        domainNameField.setText("example: dbTable");
        domainNameField.setEnabled(false);
        paneRight1.add(domainNameField);
        JPanel paneRight2 = new JPanel();
        paneRight2.setLayout(new FlowLayout(FlowLayout.CENTER));
        paneRight2.add(new JLabel("dao postfix:"));
        mapperNameField.setText("Dao");
        paneRight2.add(mapperNameField);

        paneMainTop1.add(paneLeft1);
        paneMainTop1.add(paneLeft2);
        paneMainTop1.add(paneRight1);
        paneMainTop1.add(paneRight2);

        JPanel modelPackagePanel = new JPanel();
        modelPackagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JBLabel labelLeft4 = new JBLabel("model package:");
        modelPackagePanel.add(labelLeft4);
        modelPackagePanel.add(modelPackageField);
        JButton packageBtn1 = new JButton("...");
        packageBtn1.addActionListener(actionEvent -> {
            final PackageChooserDialog chooser = new PackageChooserDialog("chooser model package", project);
            chooser.selectPackage(modelPackageField.getText());
            chooser.show();
            final PsiPackage psiPackage = chooser.getSelectedPackage();
            String packageName = psiPackage == null ? null : psiPackage.getQualifiedName();
            modelPackageField.setText(packageName);
        });
        modelPackagePanel.add(packageBtn1);


        JPanel daoPackagePanel = new JPanel();
        daoPackagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelLeft5 = new JLabel("dao package:");
        daoPackagePanel.add(labelLeft5);
        daoPackagePanel.add(daoPackageField);

        JButton packageBtn2 = new JButton("...");
        packageBtn2.addActionListener(actionEvent -> {
            final PackageChooserDialog chooser = new PackageChooserDialog("choose dao package", project);
            chooser.selectPackage(daoPackageField.getText());
            chooser.show();
            final PsiPackage psiPackage = chooser.getSelectedPackage();
            String packageName = psiPackage == null ? null : psiPackage.getQualifiedName();
            daoPackageField.setText(packageName);
        });
        daoPackagePanel.add(packageBtn2);

        JPanel xmlPackagePanel = new JPanel();
        xmlPackagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelLeft6 = new JLabel("xml package:");
        xmlPackagePanel.add(labelLeft6);
        xmlPackagePanel.add(xmlPackageField);

        paneMainTop2.add(modelPackagePanel);
        paneMainTop2.add(daoPackagePanel);
        paneMainTop2.add(xmlPackagePanel);


        JPanel projectFolderPanel = new JPanel();
        projectFolderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel projectLabel = new JLabel("project folder:");
        projectFolderPanel.add(projectLabel);
        projectFolderBtn.setTextFieldPreferredWidth(45);
        projectFolderBtn.setText(projectFolder);
        projectFolderBtn.addBrowseFolderListener(new TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                super.actionPerformed(e);
                projectFolderBtn.setText(projectFolderBtn.getText().replaceAll("\\\\","/"));
            }
        });
        projectFolderPanel.add(projectFolderBtn);
        projectFolderPanel.add(setProjectBtn);


        JPanel modelFolderPanel = new JPanel();
        modelFolderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        modelFolderPanel.add(new JLabel("model  folder:"));

        modelFolderBtn.setTextFieldPreferredWidth(45);
        modelFolderBtn.setText(projectFolder);
        modelFolderBtn.addBrowseFolderListener(new TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                super.actionPerformed(e);
                modelFolderBtn.setText(modelFolderBtn.getText().replaceAll("\\\\","/"));
            }
        });
        modelFolderPanel.add(modelFolderBtn);
        modelFolderPanel.add(new JLabel("mvn path:"));
        modelMvnField.setText("src/main/java");
        modelFolderPanel.add(modelMvnField);


        JPanel daoFolderPanel = new JPanel();
        daoFolderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        daoFolderPanel.add(new JLabel("dao      folder:"));
        daoFolderBtn.setTextFieldPreferredWidth(45);
        daoFolderBtn.setText(projectFolder);
        daoFolderBtn.addBrowseFolderListener(new TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                super.actionPerformed(e);
                daoFolderBtn.setText(daoFolderBtn.getText().replaceAll("\\\\","/"));
            }
        });
        daoFolderPanel.add(daoFolderBtn);
        daoFolderPanel.add(new JLabel("mvn path:"));
        daoMvnField.setText("src/main/java");
        daoFolderPanel.add(daoMvnField);


        JPanel xmlFolderPanel = new JPanel();
        xmlFolderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        xmlFolderPanel.add(new JLabel("xml      folder:"));


        xmlFolderBtn.setTextFieldPreferredWidth(45);
        xmlFolderBtn.setText(projectFolder);
        xmlFolderBtn.addBrowseFolderListener(new TextBrowseFolderListener(FileChooserDescriptorFactory.createSingleFileOrFolderDescriptor()) {});
        xmlFolderPanel.add(xmlFolderBtn);
        xmlFolderPanel.add(new JLabel("mvn path:"));
        xmlMvnField.setText("src/main/resources");
        xmlFolderPanel.add(xmlMvnField);

        paneMainTop3.add(projectFolderPanel);
        paneMainTop3.add(modelFolderPanel);
        paneMainTop3.add(daoFolderPanel);
        paneMainTop3.add(xmlFolderPanel);


        offsetLimitBox.setSelected(true);
        commentBox.setSelected(true);
        overrideXMLBox.setSelected(true);
        needToStringHashcodeEqualsBox.setSelected(true);
        useSchemaPrefixBox.setSelected(true);
        useExampleBox.setSelected(true);


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

        mainPanel.add(paneMainTop);
        mainPanel.add(paneMainDown);


        config = PersistentConfig.getInstance(project);
        Map<String, Config> initConfig = config.getInitConfig();
        if (initConfig != null) {
            Config config = initConfig.get("initConfig");
            mapperNameField.setText(config.getDaoPostfix());
            modelPackageField.setText(config.getModelPackage());
            daoPackageField.setText(config.getDaoPackage());
            xmlPackageField.setText(config.getXmlPackage());

            projectFolderBtn.setText(config.getProjectFolder());
            modelFolderBtn.setText(config.getModelTargetFolder());
            daoFolderBtn.setText(config.getDaoTargetFolder());
            xmlFolderBtn.setText(config.getXmlTargetFolder());

            offsetLimitBox.setSelected(config.isOffsetLimit());
            commentBox.setSelected(config.isComment());
            overrideXMLBox.setSelected(config.isOverrideXML());
            needToStringHashcodeEqualsBox.setSelected(config.isNeedToStringHashcodeEquals());
            useSchemaPrefixBox.setSelected(config.isUseSchemaPrefix());
            needForUpdateBox.setSelected(config.isNeedForUpdate());
            annotationDAOBox.setSelected(config.isAnnotationDAO());
            useDAOExtendStyleBox.setSelected(config.isUseDAOExtendStyle());
            jsr310SupportBox.setSelected(config.isJsr310Support());
            annotationBox.setSelected(config.isAnnotation());
            useActualColumnNamesBox.setSelected(config.isUseActualColumnNames());
            useTableNameAliasBox.setSelected(config.isUseTableNameAlias());
            useExampleBox.setSelected(config.isUseExample());
            mysql_8Box.setSelected(config.isMysql_8());
        } else {
            modelPackageField.addFocusListener(new JTextFieldHintListener(modelPackageField, "generator"));
            daoPackageField.addFocusListener(new JTextFieldHintListener(daoPackageField, "generator"));
            xmlPackageField.addFocusListener(new JTextFieldHintListener(xmlPackageField, "generator"));
        }
    }

    public boolean isModified() {
        boolean modified = true;
//        modified |= !this.id.getText().equals(config.getId());
//        modified |= !this.entity.getText().equals(config.getEntity());
//        modified |= !this.project_directory.getText().equals(config.getProject_directory());
//        modified |= !this.dao_name.getText().equals(config.getDao_name());
//
//        modified |= !this.entity_package.getText().equals(config.getEntity_package());
//        modified |= !this.entity_directory.getText().equals(config.getEntity_directory());
//        modified |= !this.mapper_package.getText().equals(config.getMapper_package());
//        modified |= !this.mapper_directory.getText().equals(config.getMapper_directory());
//        modified |= !this.xml_package.getText().equals(config.getXml_package());
//        modified |= !this.xml_directory.getText().equals(config.getXml_directory());
//        modified |= !this.password.getPassword().equals(config.getPassword());
//        modified |= !this.username.getText().equals(config.getUsername());
        return modified;
    }

    public void apply() {
        HashMap<String, Config> initConfig = new HashMap<>();
        Config config = new Config();
        config.setName("initConfig");
        config.setDaoPostfix(mapperNameField.getText());
        config.setModelPackage(modelPackageField.getText());
        config.setDaoPackage(daoPackageField.getText());
        config.setXmlPackage(xmlPackageField.getText());
        config.setProjectFolder(projectFolderBtn.getText());
        config.setModelTargetFolder(modelFolderBtn.getText());
        config.setDaoTargetFolder(daoFolderBtn.getText());
        config.setXmlTargetFolder(xmlFolderBtn.getText());
        config.setOffsetLimit(offsetLimitBox.getSelectedObjects() != null);
        config.setComment(commentBox.getSelectedObjects() != null);
        config.setOverrideXML(overrideXMLBox.getSelectedObjects() != null);
        config.setNeedToStringHashcodeEquals(needToStringHashcodeEqualsBox.getSelectedObjects() != null);
        config.setUseSchemaPrefix(useSchemaPrefixBox.getSelectedObjects() != null);
        config.setNeedForUpdate(needForUpdateBox.getSelectedObjects() != null);
        config.setAnnotationDAO(annotationDAOBox.getSelectedObjects() != null);
        config.setUseDAOExtendStyle(useDAOExtendStyleBox.getSelectedObjects() != null);
        config.setJsr310Support(jsr310SupportBox.getSelectedObjects() != null);
        config.setAnnotation(annotationBox.getSelectedObjects() != null);
        config.setUseActualColumnNames(useActualColumnNamesBox.getSelectedObjects() != null);
        config.setUseTableNameAlias(useTableNameAliasBox.getSelectedObjects() != null);
        config.setUseExample(useExampleBox.getSelectedObjects() != null);
        config.setMysql_8(mysql_8Box.getSelectedObjects() != null);

        initConfig.put(config.getName(), config);
        this.config.setInitConfig(initConfig);


    }

    public void reset() {

    }

    @Override
    public JPanel getContentPane() {
        return mainPanel;
    }


}
