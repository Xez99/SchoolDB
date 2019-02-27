package com.xez99;

/**
 * This class exist only because JavaFX jar issue: when jar application starts,
 * sun.launcher.LauncherHelper in the java.base module checks for the javafx.graphics module
 * to be present as a named module (if that module is not present, the launch is aborted).
 * So, this class launch com.xez99.Start class which extends javafx.application.Application to avoid that
 * issue in jar file. In IDE you still can run project from com.xez99.Start class.
 * More about issue: https://stackoverflow.com/questions/52569724/javafx-11-create-a-jar-file-with-gradle
 * @author https://github.com/Xez99
 * @version final
 */
public final class Main {
    public static void main(String[] args) {Start.main(args);}
}
