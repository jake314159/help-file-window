help-file-window
================

Help window for Java applications

Example usage
-------------


```Java
String programName = "MyProgram";
File helpFileDir = new File("exampleHelpFiles");
HelpWindow myHelpWindow = new HelpWindow(programName, helpFileDir);
myHelpWindow.popup();
```

Note that *helpFileDir* is a File object for the directory containing your help files.

Help file formatting
------------------

Help files should be named with the initial 3 characters being the digits 0-9 and should specifiy the order in which they are going to apear in the list. 

Following the 3 digit number should be the title that will apear in the side bar of the help window. The file should also have a .md (markdown) extension. 

For example:

```
"034Some topic.md"
```

Supported markdown
-----------------

Not all types of markdown are supported. The things which are supported are:

+ Headings up to h6
+ Bullet points
+ Ordered lists
+ Italics
+ Bold
+ Monospace
+ Code (without highlighting)
+ Block quotes

This is not a compleate list and some other markdown syntax not on the list may work but hasn't been tested.

Sample image
-----------

![Creating a wireframe](/exampleImages/ubuntu.png)

For more example images see */exampleImages*

Licence
-------

Released with an Apache License V2 
