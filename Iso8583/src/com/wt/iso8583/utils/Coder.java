package com.wt.iso8583.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Coder {
    /**
     * ASCII码对应表
     */
    private static final String [] ASCII={
            "NUL",    /* ASCII  0   0x00 "Null" */
            "SOH",    /* ASCII  1   0x01 "Start of Heading" */
            "STX",    /* ASCII  2   0x02 "Start of Text " */
            "ETX",    /* ASCII  3   0x03 "End of Text" */
            "EOT",    /* ASCII  4   0x04 "End of Transmission" */
            "ENQ",    /* ASCII  5   0x05 "Enquiry" */
            "ACK",    /* ASCII  6   0x06 "Acknowledge" */
            "BEL",    /* ASCII  7   0x07 "Bell" */
            "BS",     /* ASCII  8   0x08 "Backspace" */
            "HT",     /* ASCII  9   0x09 "Horizontal Tabulation" */
            "LF",     /* ASCII  10  0x0A "Line Feed" */
            "VT",     /* ASCII  11  0x0B "Vertical Tabulation" */
            "FF",     /* ASCII  12  0x0C "Form Feed" */
            "CR",     /* ASCII  13  0x0D "Carriage Return" */
            "SO",     /* ASCII  14  0x0E "Shift Out" */
            "SI",     /* ASCII  15  0x0F "Shift In" */
            "DLE",    /* ASCII  16  0x10 "Data Link Escape" */
            "DC1",    /* ASCII  17  0x11 "Device Control 1" */
            "DC2",    /* ASCII  18  0x12 "Device Control 2" */
            "DC3",    /* ASCII  19  0x13 "Device Control 3" */
            "DC4",    /* ASCII  20  0x14 "Device Control 4" */
            "NAK",    /* ASCII  21  0x15 "Negative Acknowledge" */
            "SYN",    /* ASCII  22  0x16 "Synchronous Idle" */
            "ETB",    /* ASCII  23  0x17 "End of Transmission Block" */
            "CAN",    /* ASCII  24  0x18 "Cancel" */
            "EM",     /* ASCII  25  0x19 "End of Medium" */
            "SUB",    /* ASCII  26  0x1A "Substitute" */
            "ESC",    /* ASCII  27  0x1B "Escape" */
            "FS",     /* ASCII  28  0x1C "File Separator" */
            "GS",     /* ASCII  29  0x1D "Group Separator" */
            "RS",     /* ASCII  30  0x1E "Record Separator" */
            "US",     /* ASCII  31  0x1F "Unit Separator" */
            "SP",     /* ASCII  32  0x20 "Space" */
            "!",      /* ASCII  33  0x21 "Exclamation Point" */
            "\"",     /* ASCII  34  0x22 "Quotation Mark" */
            "#",      /* ASCII  35  0x23 "Number Sign, pound" */
            "$",      /* ASCII  36  0x24 "Dollar Sign" */
            "%",      /* ASCII  37  0x25 "Percent" */
            "&",      /* ASCII  38  0x26 "Ampersand" */
            "'",      /* ASCII  39  0x27 "Apostrophe, Prime" */
            "(",      /* ASCII  40  0x28 "Left Parenthesis" */
            ")",      /* ASCII  41  0x29 "Right Parenthesis" */
            "*",      /* ASCII  42  0x2A "Asterisk, star" */
            "+",      /* ASCII  43  0x2B "Plus Sign" */
            ",",      /* ASCII  44  0x2C "Comma" */
            "-",      /* ASCII  45  0x2D "Hyphen Minus Sign" */
            ".",      /* ASCII  46  0x2E "Period, Decimal Point, dot" */
            "/",      /* ASCII  47  0x2F "Slash, Virgule" */
            "0",      /* ASCII  48  0x30 "0" */
            "1",      /* ASCII  49  0x31 "1" */
            "2",      /* ASCII  50  0x32 "2" */
            "3",      /* ASCII  51  0x33 "3" */
            "4",      /* ASCII  52  0x34 "4" */
            "5",      /* ASCII  53  0x35 "5" */
            "6",      /* ASCII  54  0x36 "6" */
            "7",      /* ASCII  55  0x37 "7" */
            "8",      /* ASCII  56  0x38 "8" */
            "9",      /* ASCII  57  0x39 "9" */
            ":",      /* ASCII  58  0x3A "Colon" */
            ";",      /* ASCII  59  0x3B "Semicolon" */
            "<",      /* ASCII  60  0x3C "Less-than Sign" */
            "=",      /* ASCII  61  0x3D "Equal Sign" */
            ">",      /* ASCII  62  0x3E "Greater-than Sign" */
            "?",      /* ASCII  63  0x3F "Question Mark" */
            "@",      /* ASCII  64  0x40 "At Sign" */
            "A",      /* ASCII  65  0x41 "A" */
            "B",      /* ASCII  66  0x42 "B" */
            "C",      /* ASCII  67  0x43 "C" */
            "D",      /* ASCII  68  0x44 "D" */
            "E",      /* ASCII  69  0x45 "E" */
            "F",      /* ASCII  70  0x46 "F" */
            "G",      /* ASCII  71  0x47 "G" */
            "H",      /* ASCII  72  0x48 "H" */
            "I",      /* ASCII  73  0x49 "I" */
            "J",      /* ASCII  74  0x4A "J" */
            "K",      /* ASCII  75  0x4B "K" */
            "L",      /* ASCII  76  0x4C "L" */
            "M",      /* ASCII  77  0x4D "M" */
            "N",      /* ASCII  78  0x4E "N" */
            "O",      /* ASCII  79  0x4F "O" */
            "P",      /* ASCII  80  0x50 "P" */
            "Q",      /* ASCII  81  0x51 "Q" */
            "R",      /* ASCII  82  0x52 "R" */
            "S",      /* ASCII  83  0x53 "S" */
            "T",      /* ASCII  84  0x54 "T" */
            "U",      /* ASCII  85  0x55 "U" */
            "V",      /* ASCII  86  0x56 "V" */
            "W",      /* ASCII  87  0x57 "W" */
            "X",      /* ASCII  88  0x58 "X" */
            "Y",      /* ASCII  89  0x59 "Y" */
            "Z",      /* ASCII  90  0x5A "Z" */
            "[",      /* ASCII  91  0x5B "Opening Bracket" */
            "\\",     /* ASCII  92  0x5C "Reverse Slant" */
            "]",      /* ASCII  93  0x5D "Closing Bracket" */
            "^",      /* ASCII  94  0x5E "Circumflex, Caret" */
            "_",      /* ASCII  95  0x5F "Underline, Underscore" */
            "`",      /* ASCII  96  0x60 "Grave Accent" */
            "a",      /* ASCII  97  0x61 "a" */
            "b",      /* ASCII  98  0x62 "b" */
            "c",      /* ASCII  99  0x63 "c" */
            "d",      /* ASCII  100 0x64 "d" */
            "e",      /* ASCII  101 0x65 "e" */
            "f",      /* ASCII  102 0x66 "f" */
            "g",      /* ASCII  103 0x67 "g" */
            "h",      /* ASCII  104 0x68 "h" */
            "i",      /* ASCII  105 0x69 "i" */
            "j",      /* ASCII  106 0x6A "j" */
            "k",      /* ASCII  107 0x6B "k" */
            "l",      /* ASCII  108 0x6C "l" */
            "m",      /* ASCII  109 0x6D "m" */
            "n",      /* ASCII  110 0x6E "n" */
            "o",      /* ASCII  111 0x6F "o" */
            "p",      /* ASCII  112 0x70 "p" */
            "q",      /* ASCII  113 0x71 "q" */
            "r",      /* ASCII  114 0x72 "r" */
            "s",      /* ASCII  115 0x73 "s" */
            "t",      /* ASCII  116 0x74 "t" */
            "u",      /* ASCII  117 0x75 "u" */
            "v",      /* ASCII  118 0x76 "v" */
            "w",      /* ASCII  119 0x77 "w" */
            "x",      /* ASCII  120 0x78 "x" */
            "y",      /* ASCII  121 0x79 "y" */
            "z",      /* ASCII  122 0x7A "z" */
            "{",      /* ASCII  123 0x7B "Opening Brace" */
            "|",      /* ASCII  124 0x7C "Vertical Line" */
            "}",      /* ASCII  125 0x7D "Closing Brace" */
            "~",      /* ASCII  126 0x7E "Tilde" */
            "DEL",    /* ASCII  127 0x7F "Delete" */
            "",       /* ASCII  128 0x80 "Reserved" */
            "",       /* ASCII  129 0x81 "Reserved" */
            "",       /* ASCII  130 0x82 "Reserved" */
            "",       /* ASCII  131 0x83 "Reserved" */
            "IND",    /* ASCII  132 0x84 "Index" */
            "NEL",    /* ASCII  133 0x85 "Next Line" */
            "SSA",    /* ASCII  134 0x86 "Start of Selected Area" */
            "ESA",    /* ASCII  135 0x87 "End of Selected Area" */
            "HTS",    /* ASCII  136 0x88 "Horizontal Tab Set" */
            "HTJ",    /* ASCII  137 0x89 "Horizontal Tabwith Just" */
            "VTS",    /* ASCII  138 0x8A "Vertical Tab Set" */
            "PLD",    /* ASCII  139 0x8B "Partial Line Down" */
            "PLU",    /* ASCII  140 0x8C "Partial Line Up" */
            "RI",     /* ASCII  141 0x8D "Reverse Index" */
            "SS2",    /* ASCII  142 0x8E "Single Shift Two" */
            "SS3",    /* ASCII  143 0x8F "Single Shift Three" */
            "DCS",    /* ASCII  144 0x90 "Device Control String" */
            "PU1",    /* ASCII  145 0x91 "Private Use One" */
            "PU2",    /* ASCII  146 0x92 "Private Use Two" */
            "STS",    /* ASCII  147 0x93 "Set Transmit State" */
            "CCH",    /* ASCII  148 0x94 "Cancel Character" */
            "MW",     /* ASCII  149 0x95 "Message Waiting" */
            "SPA",    /* ASCII  150 0x96 "Start of Protected Area" */
            "EPA",    /* ASCII  151 0x97 "End of Protected Area" */
            "",       /* ASCII  152 0x98 "Reserved" */
            "",       /* ASCII  153 0x99 "Reserved" */
            "",       /* ASCII  154 0x9A "Reserved" */
            "CSI",    /* ASCII  155 0x9B "Control Sequence Introducer" */
            "ST",     /* ASCII  156 0x9C "String Terminator" */
            "OSC",    /* ASCII  157 0x9D "Operating System Command" */
            "PM",     /* ASCII  158 0x9E "Privacy Message" */
            "APC",    /* ASCII  159 0x9F "Application Program Command" */
            "nbsp",   /* ASCII  160 0xA0 "non breaking space" */
            "iexcl",  /* ASCII  161 0xA1 "inverted exclamation" */
            "cent",   /* ASCII  162 0xA2 "Cent Sign" */
            "pound",  /* ASCII  163 0xA3 "pound" */
            "curren", /* ASCII  164 0xA4 "currency" */
            "yen",    /* ASCII  165 0xA5 "yen" */
            "brvbar", /* ASCII  166 0xA6 "breve bar" */
            "sect",   /* ASCII  167 0xA7 "section" */
            "uml",    /* ASCII  168 0xA8 "umlaut" */
            "copy",   /* ASCII  169 0xA9 "copyright" */
            "ordf",   /* ASCII  170 0xAA "feminine ordinal" */
            "laquo",  /* ASCII  171 0xAB "left angle quote" */
            "not",    /* ASCII  172 0xAC "Logical NOT" */
            "shy",    /* ASCII  173 0xAD "soft hyphen" */
            "reg",    /* ASCII  174 0xAE "registered trademark" */
            "macr",   /* ASCII  175 0xAF "macron" */
            "deg",    /* ASCII  176 0xB0 "degree" */
            "plusmn", /* ASCII  177 0xB1 "plusminus" */
            "sup2",   /* ASCII  178 0xB2 "superscript 2" */
            "sup3",   /* ASCII  179 0xB3 "superscript 3" */
            "acute",  /* ASCII  180 0xB4 "acute" */
            "micro",  /* ASCII  181 0xB5 "micro" */
            "para",   /* ASCII  182 0xB6 "paragraph" */
            "middot", /* ASCII  183 0xB7 "mid dot" */
            "cedil",  /* ASCII  184 0xB8 "cedilla" */
            "sup1",   /* ASCII  185 0xB9 "superscript 1" */
            "ordm",   /* ASCII  186 0xBA "masculine ordinal" */
            "raquo",  /* ASCII  187 0xBB "right angle quote" */
            "frac14", /* ASCII  188 0xBC "1/4" */
            "frac12", /* ASCII  189 0xBD "1/2" */
            "frac34", /* ASCII  190 0xBE "3/4" */
            "iquest", /* ASCII  191 0xBF "inverted ?" */
            "Agrave", /* ASCII  192 0xC0 "Agrave"  */
            "Aacute", /* ASCII  193 0xC1 "Aacute"  */
            "Acirc",  /* ASCII  194 0xC2 "Acirc"   */
            "Atilde", /* ASCII  195 0xC3 "Atilde"  */
            "Auml",   /* ASCII  196 0xC4 "Auml"    */
            "Aring",  /* ASCII  197 0xC5 "Aring"   */
            "AElig",  /* ASCII  198 0xC6 "AElig"   */
            "Ccedil", /* ASCII  199 0xC7 "Ccedil"  */
            "Egrave", /* ASCII  200 0xC8 "Egrave"  */
            "Eacute", /* ASCII  201 0xC9 "Eacute"  */
            "Ecirc",  /* ASCII  202 0xCA "Ecirc"   */
            "Euml",   /* ASCII  203 0xCB "Euml"    */
            "Igrave", /* ASCII  204 0xCC "Igrave"  */
            "Iacute", /* ASCII  205 0xCD "Iacute"  */
            "Icirc",  /* ASCII  206 0xCE "Icirc"   */
            "Iuml",   /* ASCII  207 0xCF "Iuml"    */
            "ETH",    /* ASCII  208 0xD0 "ETH"     */
            "Ntilde", /* ASCII  209 0xD1 "Ntilde"  */
            "Ograve", /* ASCII  210 0xD2 "Ograve"  */
            "Oacute", /* ASCII  211 0xD3 "Oacute"  */
            "Ocirc",  /* ASCII  212 0xD4 "Ocirc"   */
            "Otilde", /* ASCII  213 0xD5 "Otilde"  */
            "Ouml",   /* ASCII  214 0xD6 "Ouml"    */
            "times",  /* ASCII  215 0xD7 "times"   */
            "Oslash", /* ASCII  216 0xD8 "Oslash"  */
            "Ugrave", /* ASCII  217 0xD9 "Ugrave"  */
            "Uacute", /* ASCII  218 0xDA "Uacute"  */
            "Ucirc",  /* ASCII  219 0xDB "Ucirc"   */
            "Uuml",   /* ASCII  220 0xDC "Uuml"    */
            "Yacute", /* ASCII  221 0xDD "Yacute"  */
            "THORN",  /* ASCII  222 0xDE "THORN"   */
            "szlig",  /* ASCII  223 0xDF "szlig"   */
            "agrave", /* ASCII  224 0xE0 "agrave"  */
            "aacute", /* ASCII  225 0xE1 "aacute"  */
            "acirc",  /* ASCII  226 0xE2 "acirc"   */
            "atilde", /* ASCII  227 0xE3 "atilde"  */
            "auml",   /* ASCII  228 0xE4 "auml"    */
            "aring",  /* ASCII  229 0xE5 "aring"   */
            "aelig",  /* ASCII  230 0xE6 "aelig"   */
            "ccedil", /* ASCII  231 0xE7 "ccedil"  */
            "egrave", /* ASCII  232 0xE8 "egrave"  */
            "eacute", /* ASCII  233 0xE9 "eacute"  */
            "ecirc",  /* ASCII  234 0xEA "ecirc"   */
            "euml",   /* ASCII  235 0xEB "euml"    */
            "igrave", /* ASCII  236 0xEC "igrave"  */
            "iacute", /* ASCII  237 0xED "iacute"  */
            "icirc",  /* ASCII  238 0xEE "icirc"   */
            "iuml",   /* ASCII  239 0xEF "iuml"    */
            "eth",    /* ASCII  240 0xF0 "eth"     */
            "ntilde", /* ASCII  241 0xF1 "ntilde"  */
            "ograve", /* ASCII  242 0xF2 "ograve"  */
            "oacute", /* ASCII  243 0xF3 "oacute"  */
            "ocirc",  /* ASCII  244 0xF4 "ocirc"   */
            "otilde", /* ASCII  245 0xF5 "otilde"  */
            "ouml",   /* ASCII  246 0xF6 "ouml"    */
            "divide", /* ASCII  247 0xF7 "divide"  */
            "oslash", /* ASCII  248 0xF8 "oslash"  */
            "ugrave", /* ASCII  249 0xF9 "ugrave"  */
            "uacute", /* ASCII  250 0xFA "uacute"  */
            "ucirc",  /* ASCII  251 0xFB "ucirc"   */
            "uuml",   /* ASCII  252 0xFC "uuml"    */
            "yacute", /* ASCII  253 0xFD "yacute"  */
            "thorn",  /* ASCII  254 0xFE "thorn"   */
            "yuml"    /* ASCII  255 0xFF "yuml"    */
    };

    /**
     * EBCDIC码对应表
     */
    public static final String [] EBCDIC={
            "NUL",    /* EBCDIC  0   0x00  "Null" */
            "SOH",    /* EBCDIC  1   0x01  "Start of Heading" */
            "STX",    /* EBCDIC  2   0x02  "Start of Text" */
            "ETX",    /* EBCDIC  3   0x03  "End of Text" */
            "PF",     /* EBCDIC  4   0x04  "Punch Off" */
            "HT",     /* EBCDIC  5   0x05  "Horizontal Tab" */
            "LC",     /* EBCDIC  6   0x06  "Lower Case" */
            "DEL",    /* EBCDIC  7   0x07  "Delete" */
            "",       /* EBCDIC  8   0x08  "" */
            "",       /* EBCDIC  9   0x09  "" */
            "SMM",    /* EBCDIC  10  0x0A  "Start of Manual Message" */
            "VT",     /* EBCDIC  11  0x0B  "Vertical Tab" */
            "FF",     /* EBCDIC  12  0x0C  "Form Feed" */
            "CR",     /* EBCDIC  13  0x0D  "Carriage Return" */
            "SO",     /* EBCDIC  14  0x0E  "Shift Out" */
            "SI",     /* EBCDIC  15  0x0F  "Shift In" */
            "DLE",    /* EBCDIC  16  0x10  "Data Link Escape" */
            "DC1",    /* EBCDIC  17  0x11  "Device Control 1" */
            "DC2",    /* EBCDIC  18  0x12  "Device Control 2" */
            "TM",     /* EBCDIC  19  0x13  "Tape Mark" */
            "RES",    /* EBCDIC  20  0x14  "Restore" */
            "NL",     /* EBCDIC  21  0x15  "New Line" */
            "BS",     /* EBCDIC  22  0x16  "Backspace" */
            "IL",     /* EBCDIC  23  0x17  "Idle" */
            "CAN",    /* EBCDIC  24  0x18  "Cancel" */
            "EM",     /* EBCDIC  25  0x19  "End of Medium" */
            "CC",     /* EBCDIC  26  0x1A  "Cursor Control" */
            "CU1",    /* EBCDIC  27  0x1B  "Customer Use 1" */
            "IFS",    /* EBCDIC  28  0x1C  "Interchange File Separator" */
            "IGS",    /* EBCDIC  29  0x1D  "Interchange Group Separator" */
            "IRS",    /* EBCDIC  30  0x1E  "Interchange Record Separator" */
            "IUS",    /* EBCDIC  31  0x1F  "Interchange Unit Separator" */
            "DS",     /* EBCDIC  32  0x20  "Digit Select" */
            "SOS",    /* EBCDIC  33  0x21  "Start of Significance" */
            "FS",     /* EBCDIC  34  0x22  "Field Separator" */
            "",       /* EBCDIC  35  0x23  "" */
            "BYP",    /* EBCDIC  36  0x24  "Bypass" */
            "LF",     /* EBCDIC  37  0x25  "Line Feed" */
            "ETB",    /* EBCDIC  38  0x26  "End of Transmission Block" */
            "ESC",    /* EBCDIC  39  0x27  "Escape" */
            "",       /* EBCDIC  40  0x28  "" */
            "",       /* EBCDIC  41  0x29  "" */
            "SM",     /* EBCDIC  42  0x2A  "Set Mode" */
            "CU2",    /* EBCDIC  43  0x2B  "Customer Use 2" */
            "",       /* EBCDIC  44  0x2C  "" */
            "ENQ",    /* EBCDIC  45  0x2D  "Enquiry" */
            "ACK",    /* EBCDIC  46  0x2E  "Acknowledge" */
            "BEL",    /* EBCDIC  47  0x2F  "Bell" */
            "",       /* EBCDIC  48  0x30  "" */
            "",       /* EBCDIC  49  0x31  "" */
            "SYN",    /* EBCDIC  50  0x32  "Synchronous Idle" */
            "",       /* EBCDIC  51  0x33  "" */
            "PN",     /* EBCDIC  52  0x34  "Punch On" */
            "RS",     /* EBCDIC  53  0x35  "Reader Stop" */
            "UC",     /* EBCDIC  54  0x36  "Upper Case" */
            "EOT",    /* EBCDIC  55  0x37  "End of Transmission" */
            "",       /* EBCDIC  56  0x38  "" */
            "",       /* EBCDIC  57  0x39  "" */
            "",       /* EBCDIC  58  0x3A  "" */
            "CU3",    /* EBCDIC  59  0x3B  "Customer Use 3" */
            "DC4",    /* EBCDIC  60  0x3C  "Device Control 4" */
            "NAK",    /* EBCDIC  61  0x3D  "Negative Acknowledge" */
            "",       /* EBCDIC  62  0x3E  "" */
            "SUB",    /* EBCDIC  63  0x3F  "Substitute" */
            "SP",     /* EBCDIC  64  0x40  "Space" */
            "",       /* EBCDIC  65  0x41  "" */
            "",       /* EBCDIC  66  0x42  "" */
            "",       /* EBCDIC  67  0x43  "" */
            "",       /* EBCDIC  68  0x44  "" */
            "",       /* EBCDIC  69  0x45  "" */
            "",       /* EBCDIC  70  0x46  "" */
            "",       /* EBCDIC  71  0x47  "" */
            "",       /* EBCDIC  72  0x48  "" */
            "",       /* EBCDIC  73  0x49  "" */
            "cent",   /* EBCDIC  74  0x4A  "Cent Sign" */
            ".",      /* EBCDIC  75  0x4B  "Period, Decimal Point, dot" */
            "<",      /* EBCDIC  76  0x4C  "Less-than Sign" */
            "(",      /* EBCDIC  77  0x4D  "Left Parenthesis" */
            "+",      /* EBCDIC  78  0x4E  "Plus Sign" */
            "|",      /* EBCDIC  79  0x4F  "Logical OR" */
            "&",      /* EBCDIC  80  0x50  "Ampersand" */
            "",       /* EBCDIC  81  0x51  "" */
            "",       /* EBCDIC  82  0x52  "" */
            "",       /* EBCDIC  83  0x53  "" */
            "",       /* EBCDIC  84  0x54  "" */
            "",       /* EBCDIC  85  0x55  "" */
            "",       /* EBCDIC  86  0x56  "" */
            "",       /* EBCDIC  87  0x57  "" */
            "",       /* EBCDIC  88  0x58  "" */
            "",       /* EBCDIC  89  0x59  "" */
            "!",      /* EBCDIC  90  0x5A  "Exclamation Point" */
            "$",      /* EBCDIC  91  0x5B  "Dollar Sign" */
            "*",      /* EBCDIC  92  0x5C  "Asterisk, star" */
            ")",      /* EBCDIC  93  0x5D  "Right Parenthesis" */
            ";",      /* EBCDIC  94  0x5E  "Semicolon" */
            "not",    /* EBCDIC  95  0x5F  "Logical NOT" */
            "-",      /* EBCDIC  96  0x60  "Hyphen, Minus Sign" */
            "/",      /* EBCDIC  97  0x61  "slash" */
            "",       /* EBCDIC  98  0x62  "" */
            "",       /* EBCDIC  99  0x63  "" */
            "",       /* EBCDIC  100 0x64  "" */
            "",       /* EBCDIC  101 0x65  "" */
            "",       /* EBCDIC  102 0x66  "" */
            "",       /* EBCDIC  103 0x67  "" */
            "",       /* EBCDIC  104 0x68  "" */
            "",       /* EBCDIC  105 0x69  "" */
            "",       /* EBCDIC  106 0x6A  "" */
            ",",      /* EBCDIC  107 0x6B  "Comma" */
            "%",      /* EBCDIC  108 0x6C  "Percent" */
            "_",      /* EBCDIC  109 0x6D  "Underline, Underscore" */
            ">",      /* EBCDIC  110 0x6E  "Greater-than Sign" */
            "?",      /* EBCDIC  111 0x6F  "Question Mark" */
            "",       /* EBCDIC  112 0x70  "" */
            "",       /* EBCDIC  113 0x71  "" */
            "",       /* EBCDIC  114 0x72  "" */
            "",       /* EBCDIC  115 0x73  "" */
            "",       /* EBCDIC  116 0x74  "" */
            "",       /* EBCDIC  117 0x75  "" */
            "",       /* EBCDIC  118 0x76  "" */
            "",       /* EBCDIC  119 0x77  "" */
            "",       /* EBCDIC  120 0x78  "" */
            "",       /* EBCDIC  121 0x79  "" */
            ":",      /* EBCDIC  122 0x7A  "Colon" */
            "#",      /* EBCDIC  123 0x7B  "Number Sign" */
            "@",      /* EBCDIC  124 0x7C  "At Sign" */
            "'",      /* EBCDIC  125 0x7D  "Apostrophe, Prime" */
            "=",      /* EBCDIC  126 0x7E  "Equal Sign" */
            "\"",     /* EBCDIC  127 0x7F  "Quotation Mark" */
            "",       /* EBCDIC  128 0x80  "" */
            "a",      /* EBCDIC  129 0x81  "a" */
            "b",      /* EBCDIC  130 0x82  "b" */
            "c",      /* EBCDIC  131 0x83  "c" */
            "d",      /* EBCDIC  132 0x84  "d" */
            "e",      /* EBCDIC  133 0x85  "e" */
            "f",      /* EBCDIC  134 0x86  "f" */
            "g",      /* EBCDIC  135 0x87  "g" */
            "h",      /* EBCDIC  136 0x88  "h" */
            "i",      /* EBCDIC  137 0x89  "i" */
            "",       /* EBCDIC  138 0x8A  "" */
            "",       /* EBCDIC  139 0x8B  "" */
            "",       /* EBCDIC  140 0x8C  "" */
            "",       /* EBCDIC  141 0x8D  "" */
            "",       /* EBCDIC  142 0x8E  "" */
            "",       /* EBCDIC  143 0x8F  "" */
            "",       /* EBCDIC  144 0x90  "" */
            "j",      /* EBCDIC  145 0x91  "j" */
            "k",      /* EBCDIC  146 0x92  "k" */
            "l",      /* EBCDIC  147 0x93  "l" */
            "m",      /* EBCDIC  148 0x94  "m" */
            "n",      /* EBCDIC  149 0x95  "n" */
            "o",      /* EBCDIC  150 0x96  "o" */
            "p",      /* EBCDIC  151 0x97  "p" */
            "q",      /* EBCDIC  152 0x98  "q" */
            "r",      /* EBCDIC  153 0x99  "r" */
            "",       /* EBCDIC  154 0x9A  "" */
            "",       /* EBCDIC  155 0x9B  "" */
            "",       /* EBCDIC  156 0x9C  "" */
            "",       /* EBCDIC  157 0x9D  "" */
            "",       /* EBCDIC  158 0x9E  "" */
            "",       /* EBCDIC  159 0x9F  "" */
            "",       /* EBCDIC  160 0xA0  "" */
            "",       /* EBCDIC  161 0xA1  "" */
            "s",      /* EBCDIC  162 0xA2  "s" */
            "t",      /* EBCDIC  163 0xA3  "t" */
            "u",      /* EBCDIC  164 0xA4  "u" */
            "v",      /* EBCDIC  165 0xA5  "v" */
            "w",      /* EBCDIC  166 0xA6  "w" */
            "x",      /* EBCDIC  167 0xA7  "x" */
            "y",      /* EBCDIC  168 0xA8  "y" */
            "z",      /* EBCDIC  169 0xA9  "z" */
            "",       /* EBCDIC  170 0xAA  "" */
            "",       /* EBCDIC  171 0xAB  "" */
            "",       /* EBCDIC  172 0xAC  "" */
            "",       /* EBCDIC  173 0xAD  "" */
            "",       /* EBCDIC  174 0xAE  "" */
            "",       /* EBCDIC  175 0xAF  "" */
            "",       /* EBCDIC  176 0xB0  "" */
            "",       /* EBCDIC  177 0xB1  "" */
            "",       /* EBCDIC  178 0xB2  "" */
            "",       /* EBCDIC  179 0xB3  "" */
            "",       /* EBCDIC  180 0xB4  "" */
            "",       /* EBCDIC  181 0xB5  "" */
            "",       /* EBCDIC  182 0xB6  "" */
            "",       /* EBCDIC  183 0xB7  "" */
            "",       /* EBCDIC  184 0xB8  "" */
            "`",      /* EBCDIC  185 0xB9  "Grave Accent" */
            "",       /* EBCDIC  186 0xBA  "" */
            "",       /* EBCDIC  187 0xBB  "" */
            "",       /* EBCDIC  188 0xBC  "" */
            "",       /* EBCDIC  189 0xBD  "" */
            "",       /* EBCDIC  190 0xBE  "" */
            "",       /* EBCDIC  191 0xBF  "" */
            "",       /* EBCDIC  192 0xC0  "" */
            "A",      /* EBCDIC  193 0xC1  "A" */
            "B",      /* EBCDIC  194 0xC2  "B" */
            "C",      /* EBCDIC  195 0xC3  "C" */
            "D",      /* EBCDIC  196 0xC4  "D" */
            "E",      /* EBCDIC  197 0xC5  "E" */
            "F",      /* EBCDIC  198 0xC6  "F" */
            "G",      /* EBCDIC  199 0xC7  "G" */
            "H",      /* EBCDIC  200 0xC8  "H" */
            "I",      /* EBCDIC  201 0xC9  "I" */
            "",       /* EBCDIC  202 0xCA  "" */
            "",       /* EBCDIC  203 0xCB  "" */
            "",       /* EBCDIC  204 0xCC  "" */
            "",       /* EBCDIC  205 0xCD  "" */
            "",       /* EBCDIC  206 0xCE  "" */
            "",       /* EBCDIC  207 0xCF  "" */
            "",       /* EBCDIC  208 0xD0  "" */
            "J",      /* EBCDIC  209 0xD1  "J" */
            "K",      /* EBCDIC  210 0xD2  "K" */
            "L",      /* EBCDIC  211 0xD3  "L" */
            "M",      /* EBCDIC  212 0xD4  "M" */
            "N",      /* EBCDIC  213 0xD5  "N" */
            "O",      /* EBCDIC  214 0xD6  "O" */
            "P",      /* EBCDIC  215 0xD7  "P" */
            "Q",      /* EBCDIC  216 0xD8  "Q" */
            "R",      /* EBCDIC  217 0xD9  "R" */
            "",       /* EBCDIC  218 0xDA  "" */
            "",       /* EBCDIC  219 0xDB  "" */
            "",       /* EBCDIC  220 0xDC  "" */
            "",       /* EBCDIC  221 0xDD  "" */
            "",       /* EBCDIC  222 0xDE  "" */
            "",       /* EBCDIC  223 0xDF  "" */
            "",       /* EBCDIC  224 0xE0  "" */
            "",       /* EBCDIC  225 0xE1  "" */
            "S",      /* EBCDIC  226 0xE2  "S" */
            "T",      /* EBCDIC  227 0xE3  "T" */
            "U",      /* EBCDIC  228 0xE4  "U" */
            "V",      /* EBCDIC  229 0xE5  "V" */
            "W",      /* EBCDIC  230 0xE6  "W" */
            "X",      /* EBCDIC  231 0xE7  "X" */
            "Y",      /* EBCDIC  232 0xE8  "Y" */
            "Z",      /* EBCDIC  233 0xE9  "Z" */
            "",       /* EBCDIC  234 0xEA  "" */
            "",       /* EBCDIC  235 0xEB  "" */
            "",       /* EBCDIC  236 0xEC  "" */
            "",       /* EBCDIC  237 0xED  "" */
            "",       /* EBCDIC  238 0xEE  "" */
            "",       /* EBCDIC  239 0xEF  "" */
            "0",      /* EBCDIC  240 0xF0  "0" */
            "1",      /* EBCDIC  241 0xF1  "1" */
            "2",      /* EBCDIC  242 0xF2  "2" */
            "3",      /* EBCDIC  243 0xF3  "3" */
            "4",      /* EBCDIC  244 0xF4  "4" */
            "5",      /* EBCDIC  245 0xF5  "5" */
            "6",      /* EBCDIC  246 0xF6  "6" */
            "7",      /* EBCDIC  247 0xF7  "7" */
            "8",      /* EBCDIC  248 0xF8  "8" */
            "9",      /* EBCDIC  249 0xF9  "9" */
            "",       /* EBCDIC  250 0xFA  "" */
            "",       /* EBCDIC  251 0xFB  "" */
            "",       /* EBCDIC  252 0xFC  "" */
            "",       /* EBCDIC  253 0xFD  "" */
            "",       /* EBCDIC  254 0xFE  "" */
            ""        /* EBCDIC  255 0xFF  "" */
    };

    /**
     * ASCII->EBCDIC转换表
     */
    private static byte[] AToE={
            /*0  */ 0, 1, 2, 3, 55, 45, 46, 47, 22, 5, 37, 11, 12, 13, 14, 15,
            /*16 */ 16, 17, 18, 63, 60, 61, 50, 38, 24, 25, 63, 39, 28, 29, 30, 31,
            /*32 */ 64, 90, 127, 123, 91, 108, 80, 125, 77, 93, 92, 78, 107, 96, 75, 97,
            /*48 */ -16, -15, -14, -13, -12, -11, -10, -9, -8, -7, 122, 94, 76, 126, 110, 111,
            /*64 */ 124, -63, -62, -61, -60, -59, -58, -57, -56, -55, -47, -46, -45, -44, -43, -42,
            /*80 */ -41, -40, -39, -30, -29, -28, -27, -26, -25, -24, -23, 63, 63, 63, 63, 109,
            /*96 */ -71, -127, -126, -125, -124, -123, -122, -121, -120, -119, -111, -110, -109, -108, -107, -106,
            /*112*/ -105, -104, -103, -94, -93, -92, -91, -90, -89, -88, -87, 63, 79, 63, 63, 7,
            /*128*/ 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63,
            /*144*/ 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63,
            /*160*/ 64, 63, 74, 123, 63, 63, 63, 63, 63, 63, 63, 63, 95, 96, 63, 63,
            /*176*/ 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63,
            /*192*/ 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63,
            /*208*/ 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63,
            /*224*/ 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63,
            /*240*/ 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63
    };

    /**
     * EBCDIC->ASCII转换表
     */
    private static byte[] EToA={
            /*0  */ 0, 1, 2, 3, 26, 9, 26, 127, 26, 26, 26, 11, 12, 13, 14, 15,
            /*16 */ 16, 17, 18, 26, 26, 10, 8, 26, 24, 25, 26, 26, 28, 29, 30, 31,
            /*32 */ 26, 26, 28, 26, 26, 10, 23, 27, 26, 26, 26, 26, 26, 5, 6, 7,
            /*48 */ 26, 26, 22, 26, 26, 30, 26, 4, 26, 26, 26, 26, 20, 21, 26, 26,
            /*64 */ 32, 26, 26, 26, 26, 26, 26, 26, 26, 26, -94, 46, 60, 40, 43, 124,
            /*80 */ 38, 26, 26, 26, 26, 26, 26, 26, 26, 26, 33, 36, 42, 41, 59, -84,
            /*96 */ 45, 47, 26, 26, 26, 26, 26, 26, 26, 26, 26, 44, 37, 95, 62, 63,
            /*112*/ 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 58, 35, 64, 39, 61, 34,
            /*128*/ 26, 97, 98, 99, 100, 101, 102, 103, 104, 105, 26, 26, 26, 26, 26, 26,
            /*144*/ 26, 106, 107, 108, 109, 110, 111, 112, 113, 114, 26, 26, 26, 26, 26, 26,
            /*160*/ 26, 26, 115, 116, 117, 118, 119, 120, 121, 122, 26, 26, 26, 26, 26, 26,
            /*176*/ 26, 26, 26, 26, 26, 26, 26, 26, 26, 96, 26, 26, 26, 26, 26, 26,
            /*192*/ 26, 65, 66, 67, 68, 69, 70, 71, 72, 73, 26, 26, 26, 26, 26, 26,
            /*208*/ 26, 74, 75, 76, 77, 78, 79, 80, 81, 82, 26, 26, 26, 26, 26, 26,
            /*224*/ 26, 26, 83, 84, 85, 86, 87, 88, 89, 90, 26, 26, 26, 26, 26, 26,
            /*240*/ 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 26, 26, 26, 26, 26, 26
    };

    /**
     * 字节的ASCII->EBCDIC转换函数
     */
    public static int ASCIIToEBCDIC(int ascii){
        return AToE[ascii&0xff]&0xff;
    }

    /**
     * 字节的EBCDIC->ASCII转换函数
     */
    public static int EBCDICToASCII(int ebcdic){
        return EToA[ebcdic&0xff]&0xff;
    }

    /**
     * 字节流的ASCII->EBCDIC转换函数
     */
    public static byte[] ASCIIToEBCDIC(byte[] ascii){
        byte[] tobytes=new byte[ascii.length];
        for(int i=0;i<ascii.length;i++) tobytes[i]=(byte)ASCIIToEBCDIC(ascii[i]);
        return tobytes;
    }

    /**
     * 字节流的EBCDIC->ASCII转换函数
     */
    public static byte[] EBCDICToASCII(byte[] ebcdic){
        byte[] tobytes=new byte[ebcdic.length];
        for(int i=0;i<ebcdic.length;i++) tobytes[i]=(byte)EBCDICToASCII(ebcdic[i]);
        return tobytes;
    }

    /**
     * 字符串的ASCII->EBCDIC转换函数
     */
    public static String ASCIIToEBCDIC(String  ascii){
        return new String(ASCIIToEBCDIC(ascii.getBytes()));
    }

    /**
     * 字符串的EBCDIC->ASCII转换函数
     */
    public static String EBCDICToASCII(String ebcdic){
        return new String(EBCDICToASCII(ebcdic.getBytes()));
    }

    /**
     * 文件的ASCII->EBCDIC转换函数
     */
    public static void ASCIIToEBCDIC(String fromfile,String tofile){
        try{
            FileInputStream in=new FileInputStream(new File(fromfile));
            FileOutputStream out=new FileOutputStream(new File(tofile));
            int tempint,i=0;
            byte[] tempbytes=new byte[in.available()];
            while((tempint=in.read())!=-1) tempbytes[i++]=(byte)tempint;
            out.write(ASCIIToEBCDIC(tempbytes));
            in.close();
            out.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static void ASCIIToEBCDIC( byte[] tempbytes,String tofile){
        try{
            FileOutputStream out=new FileOutputStream(new File(tofile));
            out.write(ASCIIToEBCDIC(tempbytes));
            out.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * 文件的EBCDIC->ASCII转换函数
     */
    public static void EBCDICToASCII(String fromfile,String tofile){
        try{
            FileInputStream in=new FileInputStream(new File(fromfile));
            FileOutputStream out=new FileOutputStream(new File(tofile));
            int tempint,i=0;
            byte[] tempbytes=new byte[in.available()];
            while((tempint=in.read())!=-1) tempbytes[i++]=(byte)tempint;
            out.write(EBCDICToASCII(tempbytes));
            in.close();
            out.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static void EBCDICToASCII( byte[] tempbytes,String tofile){
        try{
            FileOutputStream out=new FileOutputStream(new File(tofile));
            int tempint,i=0;
            out.write(EBCDICToASCII(tempbytes));
            out.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){
        //ASCIIToEBCDIC("in.txt","out.txt");
        String hex= Integer.toHexString(249);
        String i ="\0\0\0J";
        byte[] bytes = ByteBuffer.allocate(4).putInt(74).array();
        byte[] b = i.getBytes(StandardCharsets.UTF_8);

        EBCDICToASCII("D:\\work\\yufangwu\\R1190396435819-4bytesLength+IPM","D:\\work\\yufangwu\\out2.txt");
    }
}
