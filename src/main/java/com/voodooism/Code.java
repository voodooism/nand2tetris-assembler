package com.voodooism;

import java.util.HashMap;

public class Code {
    public HashMap<String, String> destMnemonicMap = new HashMap<>();
    public HashMap<String, String> compMnemonicMap = new HashMap<>();
    public HashMap<String, String> jumpMnemonicMap = new HashMap<>();

    public Code() {
        this.destMnemonicMap.put(null, "000");
        this.destMnemonicMap.put("M", "001");
        this.destMnemonicMap.put("D", "010");
        this.destMnemonicMap.put("MD", "011");
        this.destMnemonicMap.put("A", "100");
        this.destMnemonicMap.put("AM", "101");
        this.destMnemonicMap.put("AD", "110");
        this.destMnemonicMap.put("AMD", "111");

        this.jumpMnemonicMap.put(null, "000");
        this.jumpMnemonicMap.put("JGT", "001");
        this.jumpMnemonicMap.put("JEQ", "010");
        this.jumpMnemonicMap.put("JGE", "011");
        this.jumpMnemonicMap.put("JLT", "100");
        this.jumpMnemonicMap.put("JNE", "101");
        this.jumpMnemonicMap.put("JLE", "110");
        this.jumpMnemonicMap.put("JMP", "111");

        this.compMnemonicMap.put("0", "0101010");
        this.compMnemonicMap.put("1", "0111111");
        this.compMnemonicMap.put("-1", "0111010");
        this.compMnemonicMap.put("D", "0001100");
        this.compMnemonicMap.put("A", "0110000");
        this.compMnemonicMap.put("M", "1110000");
        this.compMnemonicMap.put("!D", "0001101");
        this.compMnemonicMap.put("!A", "0110001");
        this.compMnemonicMap.put("!M", "1110001");
        this.compMnemonicMap.put("-D", "0001111");
        this.compMnemonicMap.put("-A", "0110011");
        this.compMnemonicMap.put("-M", "1110011");
        this.compMnemonicMap.put("D+1", "0011111");
        this.compMnemonicMap.put("A+1", "0110111");
        this.compMnemonicMap.put("M+1", "1110111");
        this.compMnemonicMap.put("D-1", "0001110");
        this.compMnemonicMap.put("A-1", "0110010");
        this.compMnemonicMap.put("M-1", "1110010");
        this.compMnemonicMap.put("D+A", "0000010");
        this.compMnemonicMap.put("D+M", "1000010");
        this.compMnemonicMap.put("D-A", "0010011");
        this.compMnemonicMap.put("D-M", "1010011");
        this.compMnemonicMap.put("A-D", "0000111");
        this.compMnemonicMap.put("M-D", "1000111");
        this.compMnemonicMap.put("D&A", "0000000");
        this.compMnemonicMap.put("D&M", "1000000");
        this.compMnemonicMap.put("D|A", "0010101");
        this.compMnemonicMap.put("D|M", "1010101");
    }

    public String dest(String mnemonic) {
        return this.destMnemonicMap.get(mnemonic);
    }

    public String comp(String mnemonic) {
        return this.compMnemonicMap.get(mnemonic);
    }

    public String jump(String mnemonic) {
        return this.jumpMnemonicMap.get(mnemonic);
    }
}
