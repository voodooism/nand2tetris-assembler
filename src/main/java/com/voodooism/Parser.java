package com.voodooism;

import java.io.BufferedReader;
import java.io.IOException;

public class Parser {
    private final BufferedReader reader;

    private String currentLine = null;

    private String cachedCommand = null;

    private CommandType commandType = null;

    private String symbol = null;

    private String dest = null;

    private String jump = null;

    private String comp = null;

    public Parser(BufferedReader reader) {
        this.reader = reader;
    }

    public boolean hasMoreCommands() {
        boolean result;

        if (this.hasCachedCommand()) {
            result = true;
        } else {
            try {
                String nextCommand = this.getNextCommand();
                this.cachedCommand = nextCommand;
                result = true;
            } catch (Throwable throwable) {
                result = false;
            }
        }

        return result;
    }

    public void advance() throws IOException {
        if (this.hasCachedCommand()) {
            this.currentLine = this.cachedCommand;
            this.cachedCommand = null;
        } else {
            this.currentLine = this.getNextCommand();
        }

        this.dest = null;
        this.comp = null;
        this.jump = null;

        this.parseCommand();
    }

    public CommandType commandType() {
        return this.commandType;
    }

    public String symbol() throws RuntimeException{
        CommandType currentType = this.commandType();

        if (currentType != CommandType.A_COMMAND && currentType != CommandType.L_COMMAND) {
            throw new RuntimeException("Should be called only when command type is A_COMMAND or L_COMMAND");
        }

        return this.symbol;
    }

    public String dest() throws RuntimeException{
        CommandType currentType = this.commandType();

        if (currentType != CommandType.C_COMMAND) {
            throw new RuntimeException("Should be called only when command type is C_COMMAND");
        }

        return this.dest;
    }

    public String comp() throws RuntimeException {
        CommandType currentType = this.commandType();

        if (currentType != CommandType.C_COMMAND) {
            throw new RuntimeException("Should be called only when command type is C_COMMAND");
        }

        return this.comp;
    }

    public String jump() throws RuntimeException {
        CommandType currentType = this.commandType();

        if (currentType != CommandType.C_COMMAND) {
            throw new RuntimeException("Should be called only when command type is C_COMMAND");
        }

        return this.jump;
    }

    private String getNextCommand() throws RuntimeException, IOException {
        String result = null;
        String currentLine;

        while((currentLine = this.reader.readLine()) != null) {
            String stripedLine = currentLine.strip();

            if(this.isLineCommand(stripedLine)) {
                result = stripedLine;
                break;
            }
        }

        if (result == null) {
            throw new RuntimeException("There is no command");
        }

        return result;
    }

    private boolean hasCachedCommand() {
        return this.cachedCommand != null;
    }

    private boolean isLineCommand(String line) {
        return !(line.isBlank() || line.startsWith("//"));
    }

    private void parseCommand() throws RuntimeException {
        if (this.currentLine == null) {
            throw new RuntimeException("Current line is null");
        }

        String currentLex = new String();

        loop:
        for (int i = 0; i < this.currentLine.length(); i++) {
            char curChar = this.currentLine.charAt(i);
            switch (curChar) {
                case '@':
                    this.commandType = CommandType.A_COMMAND;
                    break;
                case '=':
                    this.commandType = CommandType.C_COMMAND;
                    this.dest = currentLex;
                    currentLex = "";
                    break;
                case ';':
                    this.commandType = CommandType.C_COMMAND;
                    this.comp = currentLex;
                    currentLex = "";
                    break;
                case '(':
                    this.commandType = CommandType.L_COMMAND;
                    break;
                case ')':
                    this.commandType = CommandType.L_COMMAND;
                    this.symbol = currentLex;
                    break loop;
                case ' ':
                case '/':
                    break loop;
                default:
                    currentLex += curChar;
            }
        }

        if (!currentLex.isEmpty() && this.commandType == CommandType.A_COMMAND) {
            this.symbol = currentLex;
        } else if (!currentLex.isEmpty() && this.commandType == CommandType.C_COMMAND && this.comp != null) {
            this.jump = currentLex;
        } else if (!currentLex.isEmpty() && this.commandType == CommandType.C_COMMAND && this.dest != null) {
            this.comp = currentLex;
        }
    }
}
