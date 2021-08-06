import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * this class writes to the new file the code that translate
 */
public class CodeWriter {

    private int jmpIdx = 0;
    private BufferedWriter writer;
    private HashMap<String, String> hackCommand;
    private String fileName;


    /**
     *
     * @param file the file that the program writes to
     *             the constructor Initializing a buffer to write to and a hashmap for the type of ARITHMETIC commands
     */
    public CodeWriter(File file){
        try {
            if (writer == null) {
                writer = new BufferedWriter(new FileWriter(file));
                setFileName(file.getName());
                hackCommand = new HashMap<String, String>();
                hackCommand.put("add", "D+M\n");
                hackCommand.put("sub", "M-D\n");
                hackCommand.put("and", "D&M\n");
                hackCommand.put("or", "D|M\n");
                hackCommand.put("not", "!M\n");
                hackCommand.put("neg", "-M\n");
                hackCommand.put("local", "LCL");
                hackCommand.put("argument", "ARG");
                hackCommand.put("this", "THIS");
                hackCommand.put("that", "THAT");
            }
        } catch (IOException e) {
            System.out.println("Error: I/O Excpetion");
        }
    }

    /**
     *the function returns the name with out the type (ending)
     * @param fileName the file to white to
     *
     */
    public void setFileName(String fileName){
        fileName = fileName.substring(0,fileName.length()-4);
    }

    /**
     *this function gets a command string and
     * white to the file.asm the translation of the command in asm
     * @param command
     */
    public void writeArithmetic(String command){

        try {
            writer.write("@SP\n");
            if (!(command.equals("neg") || command.equals("not"))) {
                writer.write("AM=M-1"+"\n" +
                        "D=M"+"\n"  +
                        "A=A-1"+"\n");
            }
            else {
                writer.write("A=M-1"+"\n");
            }

            if(command.equals("eq") || command.equals("gt") || command.equals("lt")) {

                String label =  "j_" + fileName+ "." + (++jmpIdx);


                writer.write("D=M-D"+"\n"    +
                        "M=-1"+"\n"       +
                        "@"+label+"\n"    +
                        "D;J" + command.toUpperCase()+"\n" +
                        "@SP"+"\n"     +
                        "A=M-1"+"\n"   +
                        "M=0"+"\n"     +
                        "("+label+")"+"\n");
            }
            else {
                writer.write("M="+hackCommand.get(command));
            }
        }
        catch (IOException e) {
            System.out.println("Error: I/O Excpetion");
        }
    }

    /**
     *
     * @param segment the type of the segment
     * @param index the num in the segment
     * @return the translation of the place in ram
     */
    private String getNameOfLabe(String segment, int index) {
        if (segment.equals("temp")) {
            return "R" + (5 + index)+"\n";
        }
        if (segment.equals("pointer")) {
            return "R" + (3 + index)+"\n";
        }

        return null;
    }

    /**
     *
     * @param command line that to be translate to asm
     * @param segment type of segment
     * @param index the place in that segment
     */
    public void writePushPop(Parser.comType command, String segment, int index){
        if (command.equals(Parser.comType.C_PUSH)) {
            try {
                if (segment.equals("constant")) {
                    writer.write("@" + index +"\n"+
                            "D=A"+"\n");
                }
                else if (hackCommand.containsKey(segment)) {

                    writer.write("@"+hackCommand.get(segment)+"\n" +
                            "D=M"+"\n"  +
                            "@"+index+"\n"  +
                            "A=D+A"+"\n"  +
                            "D=M"+"\n");

                }
                else {
                    String var = getNameOfLabe(segment, index);
                    if (var!=null) {
                        writer.write("@"+var+"D=M"+"\n");
                    }
                    else {
                        writer.write("@s_"+fileName+"."+ index +"\n"+
                                "D=M"+"\n");
                    }
                }

                writer.write("@SP"+"\n"+
                        "A=M"+"\n"+
                        "M=D"+"\n"+
                        "@SP"+"\n"+
                        "M=M+1"+"\n");

            }
            catch (IOException e) {
                System.out.println("Error: I/O Exception");
            }
        }
        else if(command.equals(Parser.comType.C_POP)) {
            try {
                if (hackCommand.containsKey(segment)) {
                    String var = hackCommand.get(segment);
                    if (index> 1) {
                        writer.write("@"+var+"\n"+
                                "D=M"+"\n"+
                                "@"+index+"\n"+
                                "D=D+A"+"\n"+
                                "@R15"+"\n"+
                                "M=D"+"\n"+
                                "@SP"+"\n"+
                                "AM=M-1"+"\n"+
                                "D=M"+"\n"+
                                "@R15"+"\n"+
                                "A=M"+"\n"+
                                "M=D"+"\n");
                    }
                    else {

                        writer.write("@SP"+"\n"+
                                "AM=M-1"+"\n"+
                                "D=M"+"\n"+
                                "@"+var+"\n"+
                                (index==1? "A=M+1"+"\n":"A=M"+"\n")+
                                "M=D"+"\n");
                    }

                }
                else {

                    String var = getNameOfLabe(segment, index);

                    if (var!=null) {
                        writer.write("@SP"+"\n"+
                                "AM=M-1"+"\n"+
                                "D=M"+"\n"+
                                "@"+var+"\n"+
                                "M=D"+"\n");
                    }
                    else {

                        writer.write("@SP"+"\n"+
                                "AM=M-1"+"\n"+
                                "D=M"+"\n"+
                                "@s_"+fileName+"."+index+""+"\n"+
                                "M=D"+"\n"+"");
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * closes the file buffer that write to the file.asm the file (that hase the translation)
     */
    public void close(){
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: Unable to close file (I/O Exception)");
        }
    }

}
