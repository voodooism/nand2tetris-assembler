## Nand2Tetris Project 6: The assembler
This is my implementation of an Assembler program that translates programs written in the symbolic Hack assembly language into binary code.

An assembly language file is composed of text lines, each representing either an instruction or a symbol declaration.
There are two types of instructions: an A-instruction and a C-instruction. 
Symbol - is a pseudo-command that binds the symbol to the memory location into which the next command in the program will be stored.
There is also comments - the text beginning with two slashes and ending at the end of the line.
Space characters and empty lines are ignored.

The API of implementation is proposed by the course instructions.
More information about Hack language you can find in the nand2tetris [site](https://www.nand2tetris.org/project06)


*Usage:*
```
java -jar ./path/to/jar -p /path/to/asmfile
```
**-p** *Path to asm file*

As a result, the program will generate **\*.hack** file that contains binary code in the same directory as the **\*.asm** file
