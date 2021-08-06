import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Assembler {

    public static void main(String[] args) throws IOException {

        // write your code here
        SymbolTable myVariable = new SymbolTable();
        File fileOFAsm = new File(args[0]);

        Code codeTable = new Code();
        Parser parser1 = new Parser(fileOFAsm);
        int lineInCode = 0;
        String curr;
        while (parser1.hasMoreCommands()) {
            parser1.advance();
            if (parser1.commandType() == Parser.CommandType.lCommand) {
                curr = parser1.symbol();
                try {
                    Integer.parseInt(curr);
                } catch (NumberFormatException e) {
                    myVariable.addEntry(curr, lineInCode);
                }
            } else lineInCode++;
        }
        Parser parser2 = new Parser(fileOFAsm);
        int RAMcounter = 16;
        //  fileOFAsm = fileOFAsm.replace(".asm", "");
        try {
            String sourceAbsolutePath = fileOFAsm.getAbsolutePath();
            String fileName = fileOFAsm.getName();
            int fileNameExtensionIndex = fileName.lastIndexOf(".");
            String fileNameNoExtension =
                    fileName.substring(0, fileNameExtensionIndex);
            int fileNameIndex =
                    fileOFAsm.getAbsolutePath().indexOf(fileOFAsm.getName());
            String sourceDirectory = sourceAbsolutePath.substring(0, fileNameIndex);
            String outputFilePath =
                    sourceDirectory + fileNameNoExtension + ".hack";
            File outputFile = new File(outputFilePath);
            BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
            while (parser2.hasMoreCommands()) {
                String lineInBinary = "";
                parser2.advance();
                if (parser2.commandType() == Parser.CommandType.aCommand) {
                    String symbol = parser2.symbol();
                    int symbolDecimal = 0;
                    try {
                        symbolDecimal = Integer.parseInt(symbol);
                    } catch (NumberFormatException e) {
                        if (myVariable.contains(symbol)) {
                            symbolDecimal = myVariable.getAddress(symbol);
                        } else {
                            myVariable.addEntry(symbol, RAMcounter);
                            symbolDecimal = RAMcounter;
                            RAMcounter++;
                        }
                    }
                    String binaryRep = Integer.toBinaryString(symbolDecimal);
                    for (int i = 1; i <= (16 - binaryRep.length()); i++) {
                        lineInBinary += "0";
                    }
                    lineInBinary += binaryRep;
                } else if (parser2.commandType() == Parser.CommandType.cCommand) {
                    lineInBinary += "111";
                    lineInBinary += codeTable.comp(parser2.comp());
                    lineInBinary += codeTable.dest(parser2.dest());
                    lineInBinary += codeTable.jump(parser2.jump());
                } else if (parser2.commandType() == Parser.CommandType.lCommand) {
                    continue;
                }
                out.write(lineInBinary);
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
            System.err.println("Error: I/O error");
            System.exit(1);

        }

    }
}