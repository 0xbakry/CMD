package os.assigment1;

/**
 * @author obakry
 */
class Parser {

    String commandName;
    String[] args;
    int countSpaces = 0; // a variable to count in it the spaces in a string  
    String[] allComandNames = {"echo", "pwd", "cd", "ls", "ls -r", "mkdir", "rmdir", "touch", "cp", "cp -r", "rm", "cat"};
    boolean flag = true;
//This method will divide the input into commandName and args
//where "input" is the string command entered by the user 

    public boolean parse(String input) {
        int index = 0;
        for (int i = 0; i < input.length(); i++) // for loop to count the number of spaces in the input String
        {
            if (input.charAt(i) == ' ')
                countSpaces++;
        }
        
        String[] splittingarray = new String[countSpaces + 1];
        if (countSpaces == 0) // in case the input contains no spaces
        {
            commandName = input;
            checkCommandName(commandName);
        } else {
            splittingarray = input.split(" ");  // spliting the input string into words depending on spaces
            // between the input
            commandName = splittingarray[0];
            if (splittingarray[1].charAt(0) == '-') // checking if the word in index no.1 in the splittingarray contains
            {
                commandName = commandName + ' ';    // " - " for commands like "cp -r" that contains a space.
                commandName = commandName + splittingarray[1];
                index = 1;
                countSpaces--;
            }
            // end of setting the command name 
            if (!this.checkCommandName(commandName)) // checking if the commandName is not in the allComandarray
            {
                flag = false;                                       // " wrong command name "
                return false;   //means that the command name  is wrong 
            } else // if the commandname is in allComandArray "right command Name"
            // so we are setting the arguments int the Arg string 
            {
                args = new String[countSpaces];
                for (int i = 0; i < countSpaces; i++, index++) // foor loop to set the arguments
                {
                    args[i] = splittingarray[index + 1];
                }

            }
        }
        return true;
    }

    public String getCommandName() {
        return commandName;
    }

    public boolean checkCommandName(String input) // function to check if commandName given by the user 
    {                                         //is in array all CommandNames 
        for (int i = 0; i < 12; i++) {
            if (input.equals(allComandNames[i])) {
                return true;
            }

        }
        System.out.println("please type a commandName from below");
        for (int x = 0; x < 12; x++) {
            System.out.print((x + 1) + "-" + allComandNames[x] + " ");
        }
        System.out.println();
        commandName = " there is no commandName";
        return false;
    }

    public String[] getArgs() {
        return args;
    }

    public String returnArgument(int i) {
        return args[i];

    }

    public int getArgumentSize() {
        return countSpaces;
    }

    public void printArguments() {
        if (flag) {
            for (int i = 0; i < countSpaces; i++) {
                System.out.println("argument " + (i + 1) + ' ' + args[i]);
            }
        }
    }
}
