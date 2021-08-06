import java.io.*;
import java.util.ArrayList;

public class Parser {


    enum comType{C_ARITHMETIC, C_PUSH, C_POP, C_LABEL, C_GOTO, C_IF, C_FUNCTION, C_RETURN, C_CALL}

    private ArrayList<String> code;
    private String arg1;
    private int arg2;
    int pc;

    /**
     *the constotor read lines that are not with len of 0 of with // and put them in a array list
     * @param file the file to translate
     */
    public Parser(File file){
        code = new ArrayList<String>();
        FileInputStream fs;
        try {
            fs = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fs);
            BufferedReader read = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = read.readLine()) != null){
                if (!line.startsWith("//") && line.trim().length()!=0) {
                    code.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error: I/O Exception");
            e.printStackTrace();
        }

        pc = -1;
    }

    /**
     *
     * @return false if finish to read the file else true
     */
    public boolean hasMoreCommands(){
        return (pc < code.size()-1);
    }

    /**
     * add the place that we read
     */
    public void advance(){
        ++pc;
    }

    /**
     *
     * @return the type of the command
     */
    public comType commandType(){

        if (pc<0) {
            return null;
        }

        String[] args = code.get(pc).split(" ");
        if (args.length > 1) {
            arg1 = args[1];
        }
        if (args.length > 2) {
            arg2 = Integer.parseInt(args[2]);
        }
        String command = args[0];

        if (command.equals("call")) {
            return comType.C_CALL;
        }
        if (command.equals("push")) {
            return comType.C_PUSH;
        }
        if (command.equals("pop")) {
            return comType.C_POP;
        }
        if (command.equals("function")) {
            return comType.C_FUNCTION;
        }
        if (command.equals("if-goto")) {
            return comType.C_IF;
        }
        if (command.equals("return")) {
            return comType.C_RETURN;
        }
        if (command.equals("label")) {
            return comType.C_LABEL;
        }
        if (command.equals("pop")) {
            return comType.C_POP;
        }


        arg1 = args[0];
        return comType.C_ARITHMETIC;

    }

    public String arg1() {
        return arg1;
    }

    public int arg2(){
        return arg2;
    }
}
