import java.io.*;

public class Parser {
    public enum CommandType {
        lCommand,aCommand,cCommand;
    }
    private BufferedReader buffReader;
    private String currentLine;
    private String nextLine;
    public Parser(File fileAsm) throws IOException {
        if (fileAsm == null) {
            throw new NullPointerException("source");
        }
        if (!fileAsm.exists()) {
            throw new FileNotFoundException(fileAsm.getAbsolutePath());
        }

        this.buffReader = new BufferedReader(new FileReader(fileAsm));
        this.currentLine = null;
        this.nextLine = this.getNextLine();
    }


    private String getNextLine() throws IOException {
        String nextLine=null;
        boolean readLine=false;
        int comment=-1;
        while (!readLine) {
            nextLine = this.buffReader.readLine();
            if (nextLine == null) {
                return null;
            } else {
                comment = nextLine.indexOf("//");
                if (comment != -1 && comment != 0) {
                    nextLine = nextLine.substring(0, comment - 1);
                }
            }
            if (nextLine.trim().isEmpty() || comment == 0) {//return input.trim().startsWith("//")
               // readLine = true;
                comment = -1;
            }else{
                readLine = true;
            }
        }
        return nextLine;
    }

    public boolean hasMoreCommands() {
        return (this.nextLine != null);
    }

    public void advance() throws IOException {
        this.currentLine = this.nextLine;
        this.nextLine = this.getNextLine();
    }

    public CommandType commandType() {
        String trimmedLine = this.currentLine.trim();

        if (trimmedLine.startsWith("(") && trimmedLine.endsWith(")")) {
            return CommandType.lCommand;
        } else if (trimmedLine.startsWith("@")) {
            return CommandType.aCommand;
        } else {
            return CommandType.cCommand;
        }
    }
    public String symbol() {
        String trimmedLine = this.currentLine.trim();

        if (this.commandType().equals(CommandType.lCommand)) {
            return trimmedLine.substring(1, this.currentLine.length() - 1);
        } else if (this.commandType().equals(CommandType.aCommand)) {
            return trimmedLine.substring(1);
        } else {
            return null;
        }
    }
    public String dest() {
        String trimmedLine = this.currentLine.trim();
        int destIndex = trimmedLine.indexOf("=");
        if (destIndex == -1) {
            return null;
        } else {
            return trimmedLine.substring(0, destIndex);
        }
    }
    public String comp() {
        String trimmedLine = this.currentLine.trim();
        int destIndex = trimmedLine.indexOf("=");
        if (destIndex != -1) {
            trimmedLine = trimmedLine.substring(destIndex + 1);
        }
        int compIndex = trimmedLine.indexOf(";");

        if (compIndex == -1) {
            return trimmedLine;
        } else {
            return trimmedLine.substring(0, compIndex);
        }
    }

    public String jump() {
        String trimmedLine = this.currentLine.trim();
        int compIndex = trimmedLine.indexOf(";");

        if (compIndex == -1) {
            return null;
        } else {
            return trimmedLine.substring(compIndex + 1);
        }
    }

    }


