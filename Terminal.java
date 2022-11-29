/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.assigment1;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author obakry
 */
public class Terminal {

    Parser parser;
    String currentPath = System.getProperty("user.dir");

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public void echo(String input) {
        System.out.println(input);
    }

    public void pwd() {
        System.out.println("Working Directory = " + currentPath);
    }

    public boolean checkValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }

    public void mkdir(String input) {
        if (checkValidPath(input)) {
            new File(input).mkdirs();
        } else {
            new File(currentPath + "\\" + input).mkdirs();
        }

    }

    public void ls() {
        File[] files = new File(currentPath).listFiles();

        for (int i = 0; i < files.length; i++) {
            if (files[i].isHidden()) {
                continue;
            }
            System.out.print(files[i].getName());
            if (files[i].isDirectory()) {
                System.out.print("\\");
            }
            System.out.println("");
        }
    }

    public void lsr() {
        File[] files = new File(currentPath).listFiles();
        for (int i = files.length - 1; i > 0; i--) {
            if (files[i].isHidden()) {
                continue;
            }
            System.out.print(files[i].getName());
            if (files[i].isDirectory()) {
                System.out.print("\\");//if it is a file 
            }
            System.out.println("");
        }
    }

    /*public  void cp(String sourcePath, String destinationPath) {
		File Source = new File(sourcePath);
		File Destination = new File(destinationPath);
		
		if( !(Source.isAbsolute())) {
                    Source = new File(currentPath + "\\" + sourcePath );
                } else {
                }
	
		if( !(Destination.isAbsolute()) )
			Destination = new File(currentPath + "\\" + destinationPath);	

		   if(Source.exists())
		   {
			   try {
					Files.copy(Source.toPath(),Destination.toPath());
				} catch (IOException e) {
					System.out.println("Error: file/directory already exists!");
				}	 
			   
		   }					
	}*/
    public void Touch(String input) throws IOException {

        int index = input.lastIndexOf("\\");
        String path = input.substring(0, index);
        String filename = input.substring(index + 1, input.length());
        try {
            if (checkValidPath(path)) {
                File file = new File(path + "\\" + filename + ".txt");
                file.createNewFile();
            } else {
                System.out.println("there is no such a path in your PC");
            }
        } catch (IOException e) {
            System.out.println("Invalid path or something went wrong");
        }
    }

    public void rm(String[] paths) {

        for (int i = 0; i < paths.length; i++) {
            File file = new File(paths[i]);
            if (!(file.isAbsolute())) {
                file = new File(currentPath + "\\" + paths[i] + ".txt");
            }

            if (file.exists()) {

                if (file.isFile()) {
                    file.delete();

                } else {
                    System.out.println("ERROR: only files can be deleted!");
                }

            } else {
                System.out.println("ERROR: no such file or directory!");
            }
        }
    }

//This method will choose the suitable command method to be called
    public void chooseCommandAction() throws IOException {
        if (parser.getCommandName().equals("echo")) {
            if (parser.getArgumentSize() == 1) {
                this.echo(parser.returnArgument(0));
            } else {
                System.out.println("there is more than one argument with echo command and this is not allowed ");
            }

        } else if (parser.getCommandName().equals("pwd")) {

            if (parser.getArgumentSize() == 0) {
                this.pwd();
            } else {
                System.out.println("pwd command don't take arguments ");
            }
        } else if (parser.getCommandName().equals("mkdir")) {
            if (parser.getArgumentSize() == 0) {
                System.out.println(" you must add one or more argument with mkdir command ");
            } else {
                for (int i = 0; i < parser.getArgumentSize(); i++) {
                    this.mkdir(parser.returnArgument(i));
                }
            }
        } else if (parser.getCommandName().equals("ls")) {
            if (parser.getArgumentSize() != 0) {
                System.out.println(" you mustn't add arguments with ls command ");
            } else {
                this.ls();
            }
        } else if (parser.getCommandName().equals("ls -r")) {
            if (parser.getArgumentSize() != 0) {
                System.out.println(" you mustn't add arguments with lsr command ");
            } else {
                this.lsr();
            }
        } /*else if (parser.getCommandName().equals("cp")) {
            if (parser.getArgumentSize() != 2) {
                System.out.println(" you must add 2 argument with cp command ");
            } else {
                    this.cp(parser.returnArgument(0),parser.returnArgument(1));
                }
        }*/ else if (parser.getCommandName().equals("touch")) {
            if (parser.getArgumentSize() != 1) {
                System.out.println(" touch command takes only one argument");
            } else {
                this.Touch(parser.returnArgument(0));
            }
        } else if (parser.getCommandName().equals("rm")) {
            if (parser.getArgumentSize() != 1) {
                System.out.println(" you must add only 1 argument with rm command ");
            } else {
                this.rm(parser.getArgs());
            }
        } else {
            System.out.println("this command isn't implemented yet ");
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("type an input");
            String input = scan.nextLine();
            if (input.equals("exit")) {
                break;
            }
            Parser p = new Parser();
            p.parse(input);
            System.out.println("command name is " + p.getCommandName());
            p.printArguments();
            Terminal T = new Terminal();
            T.setParser(p);
            T.chooseCommandAction();
            System.out.println("_________");

        }
    }
}
