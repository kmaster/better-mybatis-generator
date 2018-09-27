better-mybatis-generator idea plugin
====
Use in idea database tool，right click table(one or more) to generate mybatis files (include:dao、example、domain、xml)<br>

Plugin Installation：
-------
- Using IDE built-in plugin system on Windows:
  - <kbd>File</kbd> > <kbd>Settings</kbd> > <kbd>Plugins</kbd> > <kbd>Browse repositories...</kbd> > <kbd>Search for "better-mybatis-generator"</kbd> > <kbd>Install Plugin</kbd>
- Manually:
  - Download the [latest release](https://plugins.jetbrains.com/plugin/11021-better-mybatis-generator) and install it manually using <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Install plugin from disk...</kbd>

Using sample screenshots：
-------
#1、Set custom default configuration，If not, use the program default configuration. | 设置自定义默认配置，若不设置，则使用程序默认配置。<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/1.png)<br>

#2、Connecting to Your Database | 配置数据库<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/2.png)<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/3.png)<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/4.png)<br>

#3、Select one or more tables,right click and select <kbd>mybatis generate</kbd> to open generatoe main UI. | 在需要生成代码的表上右键，选择mybatis generate，打开预览界面。<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/5.png)<br>

#4、Check configuration in main ui,click ok | 设置确认完成后，点击ok，开始生产代码。<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/6.png)<br>

#5、Provide account and password for the first time. | 首次使用此插件，需要为插件提供数据库账号密码。<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/7.png)<br>

#6、Generate work finish,Check your files | 检查生成的代码文件<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/8.png)<br>


notice：
-------
1、set mysql8 time_zone | mysql8已经在url上添加?serverTimezone=UTC，如果仍不能连接，考虑设置数据库的时区（正式的生成库请谨慎设置）。<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/修改mysql8时区.png)<br>

