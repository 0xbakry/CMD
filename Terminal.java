/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.assigment1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;


/**
 *
 * @author Reem
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

        public boolean checkValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }
        
        
        
    public void echo(String input) {
        System.out.println(input);
    }

    public void pwd() {
        System.out.println("Working Directory = " + currentPath);
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
    
    public static void cp(String sourcePath, String destinationPath) throws IOException {

        File f1 = new File(sourcePath);
        String absolute1 = f1.getAbsolutePath();

         File f2 = new File(destinationPath);
        String absolute2 = f2.getAbsolutePath();

          try {
              FileReader reader = new FileReader(absolute1);

              try (BufferedReader bufferedReader = new BufferedReader(reader)) 
              {
                  FileWriter bufferedWriter = new FileWriter(absolute2, false);
                  String line;
                  while ((line = bufferedReader.readLine()) != null) {
                      bufferedWriter.write(line);
                      bufferedWriter.write("\n");
                  }
                  bufferedWriter.close();
              }
              //  Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
              System.out.println("File copied");
          } catch (FileNotFoundException ex) {
              System.out.println("File not Found");
          } catch (IOException ex) {
              System.out.println("cp takes two parameters \n 1: File location \t  2: Destination path ");
          }

      }
    
    
      public static void cp_r(String sourcePath, String destinationPath) throws IOException {
            
    try {
        Files.walk(Paths.get(sourcePath)).forEach(a -> {
            Path b = Paths.get(destinationPath, a.toString().substring(sourcePath.length()));
            try {
                if (!a.toString().equals(sourcePath))
                    Files.copy(a, b, true ? new CopyOption[]{StandardCopyOption.REPLACE_EXISTING} : new CopyOption[]{});
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Your Directory has been copied!");
    } catch (IOException e) {
       
        e.printStackTrace();
    }
          
      }   
    
    
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
    
    public void rmdir(String[] paths) {

        for (int i = 0; i < paths.length; i++) {
            File file = new File(paths[i]);
            if (!(file.isAbsolute())) 
                file = new File(currentPath + "\\" + paths[i] + ".txt");
            
            if (file.length() == 0)
            {
                if (file.exists())
                {

                    if (file.isFile()) 
                        file.delete();

                     else 
                        System.out.println("ERROR: only files can be deleted!");
                } 
                else 
                    System.out.println("ERROR: no such file or directory!");
            
            }
            else 
               System.out.println("Your File must be empty");

        }
    }
         
    public static void cat(String path) throws FileNotFoundException {

        try {
            try (FileReader fileReader = new FileReader(path)) {
                BufferedReader in = new BufferedReader(fileReader);
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println(path + ", Your File is Not Found");
        } catch (IOException e) {
            System.out.println(path + ", I/O Error.");
        }

    }
    
    public static void cat(String path1, String path2) throws FileNotFoundException {

        try {
            try (FileReader fileReader = new FileReader(path1)) {
                BufferedReader in = new BufferedReader(fileReader);
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println(path1 + ", Your File is Not Found");
        } catch (IOException e) {
            System.out.println(path1 + ", I/O Error.");
        }
        
        /***File 1**/
        
        try {
            try (FileReader fileReader = new FileReader(path2)) {
                BufferedReader in = new BufferedReader(fileReader);
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println(path2 + ", Your File is Not Found");
        } catch (IOException e) {
            System.out.println(path2 + ", I/O Error.");
        }

    }
       
    public void cd(String path) throws IOException {
      
	File file = new File(currentPath + "\\" + path);
        
	if(file.exists()) 
           currentPath = file.getCanonicalPath();		
	 else 
	   System.out.println("no file or directory!");
        			
    }
    
      public void cd() throws IOException {
      
           currentPath = System.getProperty("user.home");
           System.out.println("Working Directory = " + currentPath);
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
        } else if (parser.getCommandName().equals("cp")) {
            if (parser.getArgumentSize() != 2) {
                System.out.println(" you must add 2 argument with cp command ");
            } else {
                    this.cp(parser.returnArgument(0),parser.returnArgument(1));
                }
        } else if (parser.getCommandName().equals("touch")) {
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
        } else if (parser.getCommandName().equals("rmdir")) {
            if (parser.getArgumentSize() != 1) {
                System.out.println(" you must add only 1 argument with rm command ");
            } else {
                this.rmdir(parser.getArgs());
            }
        } else if (parser.getCommandName().equals("cat")) {
            if (parser.getArgumentSize() == 0) {
                System.out.println(" you must add one or more argument with mkdir command ");
            } else {
                for (int i = 0; i < parser.getArgumentSize(); i++) {
                    this.cat(parser.returnArgument(i));
                }
            }
        } else if (parser.getCommandName().equals("cat")) {
            if (parser.getArgumentSize() != 2) {
                System.out.println(" you must add 2 argument with cp command ");
            } else {
                    this.cat(parser.returnArgument(0),parser.returnArgument(1));
                }
        } else if (parser.getCommandName().equals("cd")) {
            if (parser.getArgumentSize() == 0) {
              this.cd();
            } else {
                for (int i = 0; i < parser.getArgumentSize(); i++) {
                    this.cd(parser.returnArgument(i));
                }
            }

        } else if (parser.getCommandName().equals("cp -r")) {
            if (parser.getArgumentSize() != 2) {
                System.out.println(" you must add 2 argument with cp command ");
            } else {
                    this.cp_r(parser.returnArgument(0),parser.returnArgument(1));
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
