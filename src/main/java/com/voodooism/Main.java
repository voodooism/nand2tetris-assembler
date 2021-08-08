package com.voodooism;

import org.apache.commons.cli.*;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String OPTION_PATH = "path";

    private static final int FIRST_VARIABLE_ADDRESS = 16;

    private File asm;

    private Code code;

    private SymbolTable symbolTable;

    public Main(String pathToAsm, Code code) throws IOException {
        this.setPathToAsm(pathToAsm);
        this.code = code;
        this.initSymbolTable();
    }

    public List parse() throws IOException {
        Parser parser = this.getParser();

        int variableCounter = FIRST_VARIABLE_ADDRESS;
        List<String> result = new ArrayList<>();

        while (parser.hasMoreCommands()) {
            parser.advance();

            String binaryString;
            switch (parser.commandType()) {
                case A_COMMAND:
                    int address;
                    String symbol = parser.symbol();

                    try {
                        address = Integer.parseInt(symbol);
                    } catch (NumberFormatException exception) {
                        if (this.symbolTable.contains(symbol)) {
                            address = this.symbolTable.getAddress(symbol);
                        } else {
                            address = variableCounter;
                            this.symbolTable.addEntry(symbol, address);
                            variableCounter++;
                        }
                    }

                    binaryString = String.format("%16s", Integer.toBinaryString(address)).replace(' ', '0');
                    result.add(binaryString);

                    break;
                case C_COMMAND:
                    String compMnemonic = parser.comp();
                    String destMnemonic = parser.dest();
                    String jumpMnemonic = parser.jump();

                    String compCode = this.code.comp(compMnemonic);
                    String destCode = this.code.dest(destMnemonic);
                    String jumpCode = this.code.jump(jumpMnemonic);

                    binaryString = String.format("111%s%s%s", compCode, destCode, jumpCode);
                    result.add(binaryString);
                    break;
                case L_COMMAND:
                    //do nothing
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected command type");
            }
        }

        return result;
    }

    public static void main(String[] args) throws IOException, ParseException {
        CommandLine cmd = parseArguments(args);
        String filePath = cmd.getOptionValue(Main.OPTION_PATH);

        Main application = new Main(filePath, new Code());
        List<String> result = application.parse();

        String hackFilePath = String.format(
            "%s/%s.hack",
            application.asm.getParent(),
            FilenameUtils.getBaseName(application.asm.getPath())
        );

        BufferedWriter outputWriter = new BufferedWriter(
            new FileWriter(hackFilePath)
        );

        for (int i = 0; i < result.size(); i++) {
            outputWriter.write(result.get(i));
            outputWriter.newLine();
        }

        outputWriter.flush();
        outputWriter.close();
    }

    private static CommandLine parseArguments(String[] args) throws ParseException {
        Options options = new Options();

        options.addRequiredOption(
            "p",
            "path",
            true,
            "Path to asm file"
        );

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("nand2tetris-asm", options);

            System.exit(1);
            throw e;
        }

        return cmd;
    }

    private void initSymbolTable() throws IOException {
        SymbolTable symbolTable = new SymbolTable();
        Parser parser = this.getParser();

        int counter = 0;
        while (parser.hasMoreCommands()) {
            parser.advance();

            if (parser.commandType() == CommandType.L_COMMAND) {
                symbolTable.addEntry(parser.symbol(), counter);
            } else {
                counter++;
            }
        }

        this.symbolTable = symbolTable;
    }

    private void setPathToAsm(String path) {
        File file = new File(path);
        if(!file.exists() || file.isDirectory() && !FilenameUtils.getExtension(path).equals("asm")) {
            throw new RuntimeException("The \"path\" argument should contain a valid path to *.asm file");
        }

        this.asm = file;
    }

    private Parser getParser() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(this.asm));
        Parser parser = new Parser(reader);

        return parser;
    }
}
