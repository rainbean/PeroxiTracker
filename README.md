Peroxi Tracker
============

Build & Pack
--------------
- Mouse double-click BuildJar.ant 
- Select menu > Run > Run

Debug 
--------------
- Launch Eclipse with PeroDriver as main class
  
Run as Hadoop task
--------------
- upload Peroxitracker.jar and Peroxitracker_lib to <Hadoop-NameNode>
- ` $ ssh <Hadoop-NameNode>`
- load MapReducer job with external libraries, with argument -libjars dir/A.jar,dir/B.jar,dir/C.jar
- Syntax: hadoop jar <Main Jar> -libjars <External Libraries> <HDFS_Input_Dir> <HDFS_Output_Dir>
- ` $ hadoop jar Peroxitracker.jar -libjars Peroxitracker_lib/ij.jar input output`

Done
--------------
- Pipeline HDFS file system to ImageJ, through InputStream objects


ToDo:
--------------
- [ ] Break X11 requirements by rewriting code
- # IJ.run() requires X11 environment, not feasible for Azure environment
- # See Well.java contrastEnhance() function.
- # Refer - http://imagejdocu.tudor.lu/doku.php?id=faq:technical:how_do_i_run_imagej_without_a_graphics_environment_headless
- # Tried headless.jar patch from http://sourceforge.net/projects/imageja/files/headless/experimental/
- # Unfortunately it's not okay due to invalid signature.
  # Error code 
```
  No X11 DISPLAY variable was set, but this program performed an operation which requires it.
	at java.awt.GraphicsEnvironment.checkHeadless(GraphicsEnvironment.java:159)
	at java.awt.MenuComponent.<init>(MenuComponent.java:145)
	at java.awt.MenuItem.<init>(MenuItem.java:174)
	at java.awt.MenuItem.<init>(MenuItem.java:158)
	at java.awt.Menu.<init>(Menu.java:132)
	at java.awt.Menu.<init>(Menu.java:112)
	at ij.Menus.getMenu(Menus.java:690)
	at ij.Menus.getMenu(Menus.java:680)
	at ij.Menus.addMenuBar(Menus.java:105)
	at ij.IJ.init(IJ.java:325)
	at ij.IJ.run(IJ.java:252)
	at ij.IJ.run(IJ.java:316)
	at peroJava.Well.contrastEnhance(Well.java:24)
```	
