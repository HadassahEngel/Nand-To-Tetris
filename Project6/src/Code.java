import java.util.Hashtable;

public class Code {
    private Hashtable<String, String> destBits;
    private Hashtable<String, String> compBits;
    private Hashtable<String, String> jumpBits;
    public Code() {
        this.jumpBits = new Hashtable<String, String>();
        this.jumpBits.put("NULL", "000");
        this.jumpBits.put("JGT", "001");
        this.jumpBits.put("JEQ", "010");
        this.jumpBits.put("JGE", "011");
        this.jumpBits.put("JLT", "100");
        this.jumpBits.put("JNE", "101");
        this.jumpBits.put("JLE", "110");
        this.jumpBits.put("JMP", "111");

        this.compBits = new Hashtable<String, String>();
        this.compBits.put("0", "0101010");
        this.compBits.put("1", "0111111");
        this.compBits.put("-1", "0111010");
        this.compBits.put("D", "0001100");
        this.compBits.put("A", "0110000");
        this.compBits.put("M", "1110000");
        this.compBits.put("!D", "0001101");
        this.compBits.put("!A", "0110001");
        this.compBits.put("!M", "1110001");
        this.compBits.put("-D", "0001111");
        this.compBits.put("-A", "0110011");
        this.compBits.put("-M", "1110011");
        this.compBits.put("D+1", "0011111");
        this.compBits.put("A+1", "0110111");
        this.compBits.put("M+1", "1110111");
        this.compBits.put("D-1", "0001110");
        this.compBits.put("A-1", "0110010");
        this.compBits.put("M-1", "1110010");
        this.compBits.put("D+A", "0000010");
        this.compBits.put("D+M", "1000010");
        this.compBits.put("D-A", "0010011");
        this.compBits.put("D-M", "1010011");
        this.compBits.put("A-D", "0000111");
        this.compBits.put("M-D", "1000111");
        this.compBits.put("D&A", "0000000");
        this.compBits.put("D&M", "1000000");
        this.compBits.put("D|A", "0010101");
        this.compBits.put("D|M", "1010101");

        this.destBits = new Hashtable<String, String>();
        this.destBits.put("NULL", "000");
        this.destBits.put("M", "001");
        this.destBits.put("D", "010");
        this.destBits.put("MD", "011");
        this.destBits.put("A", "100");
        this.destBits.put("AM", "101");
        this.destBits.put("AD", "110");
        this.destBits.put("AMD", "111");

    }
    public String dest(String mnemonic) {
        if (mnemonic == null) {
            mnemonic = "NULL";
        }
        return this.destBits.get(mnemonic);
    }
    public String comp(String mnemonic) {
        return this.compBits.get(mnemonic);
    }
    public String jump(String mnemonic) {
        if (mnemonic == null || mnemonic.isEmpty()) {
            mnemonic = "NULL";
        }

        return this.jumpBits.get(mnemonic);
    }

}
