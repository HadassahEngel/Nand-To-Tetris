import java.util.HashMap;

public class SymbolTable {
    private HashMap<String, Integer> symbolTable;
    public HashMap<String, Integer> getSymbolTable() {
        return symbolTable;
    }
    public SymbolTable() {
        this.symbolTable  = new HashMap<String, Integer>();
        this.symbolTable.put("R0", 0);
        this.symbolTable.put("R1", 1);
        this.symbolTable.put("R2", 2);
        this.symbolTable.put("R3", 3);
        this.symbolTable.put("R4", 4);
        this.symbolTable.put("R5", 5);
        this.symbolTable.put("R6", 6);
        this.symbolTable.put("R7", 7);
        this.symbolTable.put("R8", 8);
        this.symbolTable.put("R9", 9);
        this.symbolTable.put("R10", 10);
        this.symbolTable.put("R11", 11);
        this.symbolTable.put("R12", 12);
        this.symbolTable.put("R13", 13);
        this.symbolTable.put("R14", 14);
        this.symbolTable.put("R15", 15);
        this.symbolTable.put("SP", 0);
        this.symbolTable.put("LCL", 1);
        this.symbolTable.put("ARG", 2);
        this.symbolTable.put("THIS", 3);
        this.symbolTable.put("THAT", 4);
        this.symbolTable.put("SCREEN", 16384);
        this.symbolTable.put("KBD", 24576);
    }
    public void addEntry(final String symbol, final int address) {
        this.symbolTable.put(symbol,address);
    }
    public boolean contains(String symbol) {
        return this.symbolTable.containsKey(symbol);
    }

    public int getAddress(final String symbol) {
        return symbolTable.get(symbol);
    }

}
