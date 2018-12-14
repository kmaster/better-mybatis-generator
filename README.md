better-mybatis-generator idea plugin
====
插件功能：在idea的database工具中使用，选择表（一或n,建议n小于10），生成mybatis相关的代码，(包括:dao、example、domain、xml)。<br>
Use in idea database tool，right click table(one or more) to generate mybatis files (include:dao、example、domain、xml).<br>

Plugin Installation：
-------
- 在idea插件系统里安装 | Using IDE built-in plugin system on Windows:
  - <kbd>File</kbd> > <kbd>Settings</kbd> > <kbd>Plugins</kbd> > <kbd>Browse repositories...</kbd> > <kbd>Search for "better-mybatis-generator"</kbd> > <kbd>Install Plugin</kbd>
- 手动zip安装 | Manually:
  - Download the [latest release](https://plugins.jetbrains.com/plugin/11021-better-mybatis-generator) and install it manually using <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Install plugin from disk...</kbd>

Using sample screenshots：
-------
#1、设置自定义默认配置，若不设置，则使用程序默认配置。 | Set custom default configuration，If not, use the program default configuration.<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/1.png)<br>

#2、配置数据库 | Connecting to Your Database.<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/2.png)<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/3.png)<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/4.png)<br>

#3、在需要生成代码的表上右键，选择mybatis generate，打开预览界面。 | Select one or more tables,right click and select <kbd>mybatis generate</kbd> to open generatoe main UI. <br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/5.png)<br>

#4、设置确认完成后，点击ok，开始生产代码。 | Check configuration in main ui,click ok.<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/6.png)<br>

#5、首次使用此插件，需要为插件提供数据库账号密码。 | Provide account and password for the first time. <br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/7.png)<br>

#6、检查生成的代码文件 | Generate work finish,Check your files.<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/8.png)<br>


如何在本地运行/调试此插件 | How to run/debug plugin ：
-------
#1、创建工程  File -> New -> Project from Version Control -> Git  填写github地址：https://github.com/kmaster/better-mybatis-generator.git<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/从github创建工程.png)<br>
#2、修改sdk
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/修改插件sdk.png)<br>
#3、运行配置无误则直接run/debug。若因idea版本导致提供的配置不对，请考虑本地创建空的插件工程然后参考其配置。<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/run.png)<br>
此时打上断点就可以一步步调试，修改代码后点击 Build->prepare plugin modle "xxx" For Deployment生成插件安装包再本地安装使用，
如果想优化此插件代码，比如其他数据库或运行环境，可以修改后在github上发起pull request。能点个star就更好了，哈哈。

注意事项 | notice：
-------
#1、当数据库用mysql8，在URL上定义时区，推荐使用'?serverTimezone=GMT'，配置中勾选上mysql8选项。 | If your database is mysql8，please add  '?serverTimezone=GMT' and select mysql8 option<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/mysql8-config.png)<br>
![image](https://github.com/kmaster/better-mybatis-generator/blob/master/image/mysql选项.png)<br>


