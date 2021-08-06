import java.io.File;

public class VMTranslator {
    /**
     * @param args the path of the file vm that we want to translate to asm
     */
    public static void main(String[] args) {
        /**
         * check if send file name
         */
        if (args.length != 1) {
            System.out.println("Error: you need to bring 1 arg.");
            System.exit(1);
        }
        /**
         * open the file and a new file that change the type of file to asm
         */
        String fileName;
        File file = new File(args[0]);
        if (file.isDirectory()) {
            fileName = args[0] + "/" + file.getName() + ".asm";
        } else {
            fileName = args[0].substring(0, args[0].length() - 3) + ".asm";
        }
        /**
         *create a object of parser and code writer with the file that you gat in the command line
         * we run thue all the file at each line checks if the line is is of type of C_ARITHMETIC then send to
         * the CodeWriter the line to function writeArithmetic if push of pop send to writePushPop
         */
        Parser parser;
        CodeWriter writer = new CodeWriter(new File(fileName));
        ;
        File filename2 = new File(args[0]);
        parser = new Parser(filename2);
        while (parser.hasMoreCommands()) {
            parser.advance();
            if (parser.commandType() == Parser.comType.C_ARITHMETIC) {
                writer.writeArithmetic(parser.arg1());
            } else {
                writer.writePushPop(parser.commandType(), parser.arg1(), parser.arg2());
            }
        }
        if (writer != null) {
            writer.close();
        }
    }
}
