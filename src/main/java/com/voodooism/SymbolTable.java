package com.voodooism;

import java.util.HashMap;

public class SymbolTable {
    private final HashMap<String, Integer> table;

    public SymbolTable() {
        this.table = new HashMap<>();

        int registersCounter = 0;
        while (registersCounter < 16) {
            this.table.put(String.format("R%d", registersCounter), registersCounter);
            registersCounter++;
        }

        this.table.put("SP", 0);
        this.table.put("LCL", 1);
        this.table.put("ARG", 2);
        this.table.put("THIS", 3);
        this.table.put("THAT", 4);
        this.table.put("SCREEN", 16384);
        this.table.put("KBD", 24576);
    }

    public void addEntry(String symbol, Integer address) {
        this.table.put(symbol, address);
    }

    public boolean contains(String symbol) {
        return this.table.containsKey(symbol);
    }

    public int getAddress(String symbol) {
        return this.table.get(symbol);
    }
}
