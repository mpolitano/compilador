/* The following code was generated by JFlex 1.4.3 on 8/27/14 12:31 PM */

/* --------------------------Codigo de Usuario----------------------- */
package compilador;

import java_cup.runtime.*;
import java.io.Reader;
import static compilador.token.*;
      

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 8/27/14 12:31 PM from the specification file
 * <tt>lexer.flex</tt>
 */
class AnalizadorLexico implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\1\1\1\25\0\1\1\1\37\3\0\1\33\1\40\1\0"+
    "\1\42\1\43\1\32\1\30\1\45\1\31\1\52\1\4\12\2\1\0"+
    "\1\44\1\34\1\36\1\35\2\0\32\3\1\51\1\0\1\50\1\0"+
    "\1\3\1\0\1\10\1\5\1\12\1\24\1\7\1\22\1\3\1\26"+
    "\1\20\1\3\1\11\1\13\1\3\1\16\1\15\2\3\1\6\1\14"+
    "\1\17\1\21\1\23\1\25\1\27\2\3\1\46\1\41\1\47\uff82\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\1\1\2\1\3\1\4\1\5\1\6\11\5\1\7"+
    "\1\10\1\11\1\12\1\13\1\14\1\15\1\16\2\2"+
    "\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26"+
    "\1\0\11\5\1\27\5\5\1\30\1\31\1\32\1\33"+
    "\1\34\1\35\1\36\1\37\1\40\10\5\1\41\2\5"+
    "\1\42\5\5\1\43\3\5\1\44\2\5\1\45\1\5"+
    "\1\46\3\5\1\47\1\5\1\50\1\51\1\52\1\5"+
    "\1\53\2\5\1\54\3\5\1\55\1\5\1\56";

  private static int [] zzUnpackAction() {
    int [] result = new int[104];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\53\0\53\0\126\0\201\0\254\0\327\0\u0102"+
    "\0\u012d\0\u0158\0\u0183\0\u01ae\0\u01d9\0\u0204\0\u022f\0\53"+
    "\0\53\0\53\0\53\0\u025a\0\u0285\0\u02b0\0\u02db\0\u0306"+
    "\0\u0331\0\53\0\53\0\53\0\53\0\53\0\53\0\53"+
    "\0\53\0\u035c\0\u0387\0\u03b2\0\u03dd\0\u0408\0\u0433\0\u045e"+
    "\0\u0489\0\u04b4\0\u04df\0\201\0\u050a\0\u0535\0\u0560\0\u058b"+
    "\0\u05b6\0\53\0\53\0\53\0\53\0\53\0\53\0\53"+
    "\0\53\0\u035c\0\u05e1\0\u060c\0\u0637\0\u0662\0\u068d\0\u06b8"+
    "\0\u06e3\0\u070e\0\201\0\u0739\0\u0764\0\201\0\u078f\0\u07ba"+
    "\0\u07e5\0\u0810\0\u083b\0\201\0\u0866\0\u0891\0\u08bc\0\201"+
    "\0\u08e7\0\u0912\0\201\0\u093d\0\201\0\u0968\0\u0993\0\u09be"+
    "\0\201\0\u09e9\0\201\0\201\0\201\0\u0a14\0\201\0\u0a3f"+
    "\0\u0a6a\0\201\0\u0a95\0\u0ac0\0\u0aeb\0\201\0\u0b16\0\201";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[104];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11"+
    "\2\5\1\12\4\5\1\13\1\14\1\5\1\15\1\16"+
    "\1\5\1\17\2\5\1\20\1\21\1\22\1\23\1\24"+
    "\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34"+
    "\1\35\1\36\1\37\1\40\1\41\1\2\55\0\1\4"+
    "\47\0\1\42\2\0\2\5\1\0\23\5\27\0\1\3"+
    "\50\0\2\5\1\0\1\5\1\43\6\5\1\44\12\5"+
    "\25\0\2\5\1\0\2\5\1\45\20\5\25\0\2\5"+
    "\1\0\6\5\1\46\13\5\1\47\25\0\2\5\1\0"+
    "\6\5\1\50\1\5\1\51\12\5\25\0\2\5\1\0"+
    "\1\5\1\52\21\5\25\0\2\5\1\0\11\5\1\53"+
    "\3\5\1\54\5\5\25\0\2\5\1\0\3\5\1\55"+
    "\2\5\1\56\1\5\1\57\12\5\25\0\2\5\1\0"+
    "\10\5\1\60\12\5\25\0\2\5\1\0\21\5\1\61"+
    "\1\5\61\0\1\62\52\0\1\63\44\0\1\64\1\65"+
    "\4\0\1\66\52\0\1\67\54\0\1\70\53\0\1\71"+
    "\13\0\1\72\52\0\2\5\1\0\2\5\1\73\20\5"+
    "\25\0\2\5\1\0\10\5\1\74\12\5\25\0\2\5"+
    "\1\0\12\5\1\75\10\5\25\0\2\5\1\0\7\5"+
    "\1\76\13\5\25\0\2\5\1\0\12\5\1\77\10\5"+
    "\25\0\2\5\1\0\3\5\1\100\17\5\25\0\2\5"+
    "\1\0\11\5\1\101\11\5\25\0\2\5\1\0\14\5"+
    "\1\102\6\5\25\0\2\5\1\0\12\5\1\103\10\5"+
    "\25\0\2\5\1\0\6\5\1\104\14\5\25\0\2\5"+
    "\1\0\10\5\1\105\12\5\25\0\2\5\1\0\1\5"+
    "\1\106\21\5\25\0\2\5\1\0\13\5\1\107\7\5"+
    "\25\0\2\5\1\0\13\5\1\110\7\5\25\0\2\5"+
    "\1\0\3\5\1\111\17\5\25\0\2\5\1\0\6\5"+
    "\1\112\14\5\25\0\2\5\1\0\14\5\1\113\6\5"+
    "\25\0\2\5\1\0\2\5\1\114\20\5\25\0\2\5"+
    "\1\0\2\5\1\115\20\5\25\0\2\5\1\0\7\5"+
    "\1\116\13\5\25\0\2\5\1\0\12\5\1\117\10\5"+
    "\25\0\2\5\1\0\2\5\1\120\20\5\25\0\2\5"+
    "\1\0\7\5\1\121\13\5\25\0\2\5\1\0\3\5"+
    "\1\122\17\5\25\0\2\5\1\0\17\5\1\123\3\5"+
    "\25\0\2\5\1\0\6\5\1\124\14\5\25\0\2\5"+
    "\1\0\4\5\1\125\16\5\25\0\2\5\1\0\2\5"+
    "\1\126\20\5\25\0\2\5\1\0\1\5\1\127\21\5"+
    "\25\0\2\5\1\0\1\5\1\130\21\5\25\0\2\5"+
    "\1\0\7\5\1\131\13\5\25\0\2\5\1\0\13\5"+
    "\1\132\7\5\25\0\2\5\1\0\2\5\1\133\20\5"+
    "\25\0\2\5\1\0\12\5\1\134\10\5\25\0\2\5"+
    "\1\0\2\5\1\135\20\5\25\0\2\5\1\0\3\5"+
    "\1\136\17\5\25\0\2\5\1\0\11\5\1\137\11\5"+
    "\25\0\2\5\1\0\11\5\1\140\11\5\25\0\2\5"+
    "\1\0\11\5\1\141\11\5\25\0\2\5\1\0\11\5"+
    "\1\142\11\5\25\0\2\5\1\0\13\5\1\143\7\5"+
    "\25\0\2\5\1\0\14\5\1\144\6\5\25\0\2\5"+
    "\1\0\11\5\1\145\11\5\25\0\2\5\1\0\2\5"+
    "\1\146\20\5\25\0\2\5\1\0\16\5\1\147\4\5"+
    "\25\0\2\5\1\0\4\5\1\150\16\5\23\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[2881];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\1\2\11\14\1\4\11\6\1\10\11\1\0\17\1"+
    "\10\11\57\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[104];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
    /*  Generamos un java_cup.Symbol para guardar el tipo de token 
        encontrado */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* Generamos un Symbol para el tipo de token encontrado 
       junto con su valor */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  AnalizadorLexico(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  AnalizadorLexico(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 120) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 29: 
          { return symbol(sym.DIFFERENT);  System.out.println (yytext());
          }
        case 47: break;
        case 41: 
          { return symbol(sym.RESERV_FLOAT);  System.out.println (yytext());
          }
        case 48: break;
        case 43: 
          { return symbol(sym.RETURN);  System.out.println (yytext());
          }
        case 49: break;
        case 6: 
          { return symbol(sym.DIV);  System.out.println (yytext());
          }
        case 50: break;
        case 40: 
          { return symbol(sym.FALSE);  System.out.println (yytext());
          }
        case 51: break;
        case 30: 
          { return symbol(sym.AND);  System.out.println (yytext());
          }
        case 52: break;
        case 34: 
          { return symbol(sym.FOR);  System.out.println (yytext());
          }
        case 53: break;
        case 36: 
          { return symbol(sym.TRUE);  System.out.println (yytext());
          }
        case 54: break;
        case 23: 
          { return symbol(sym.IF);  System.out.println (yytext());
          }
        case 55: break;
        case 25: 
          { return symbol(sym.HIGHEREQUAL);  System.out.println (yytext());
          }
        case 56: break;
        case 21: 
          { return symbol(sym.CORAB);  System.out.println (yytext());
          }
        case 57: break;
        case 39: 
          { return symbol(sym.CLASS);  System.out.println (yytext());
          }
        case 58: break;
        case 9: 
          { return symbol(sym.MULT);  System.out.println (yytext());
          }
        case 59: break;
        case 28: 
          { return symbol(sym.EQUAL);  System.out.println (yytext());
          }
        case 60: break;
        case 45: 
          { return symbol(sym.CONTINUE);  System.out.println (yytext());
          }
        case 61: break;
        case 3: 
          { System.out.println (yytext());
          }
        case 62: break;
        case 10: 
          { return symbol(sym.MOD);  System.out.println (yytext());
          }
        case 63: break;
        case 4: 
          { return symbol(sym.TYPE_INT);  System.out.println (yytext());
          }
        case 64: break;
        case 46: 
          { return symbol(sym.EXTERNINVK);  System.out.println (yytext());
          }
        case 65: break;
        case 20: 
          { return symbol(sym.LLCE);  System.out.println (yytext());
          }
        case 66: break;
        case 32: 
          { return symbol(sym.FLOAT);  System.out.println (yytext());
          }
        case 67: break;
        case 35: 
          { return symbol(sym.ELSE);  System.out.println (yytext());
          }
        case 68: break;
        case 11: 
          { return symbol(sym.LOWER);  System.out.println (yytext());
          }
        case 69: break;
        case 42: 
          { return symbol(sym.WHILE);  System.out.println (yytext());
          }
        case 70: break;
        case 19: 
          { return symbol(sym.LLAB);  System.out.println (yytext());
          }
        case 71: break;
        case 22: 
          { return symbol(sym.CORCER);  System.out.println (yytext());
          }
        case 72: break;
        case 2: 
          { System.out.println ("ERROR");
          }
        case 73: break;
        case 27: 
          { return symbol(sym.ASSIGPLUS);  System.out.println (yytext());
          }
        case 74: break;
        case 13: 
          { return symbol(sym.ASSIG);  System.out.println (yytext());
          }
        case 75: break;
        case 15: 
          { return symbol(sym.PARENIZQ);  System.out.println (yytext());
          }
        case 76: break;
        case 16: 
          { return symbol(sym.PARENDER);  System.out.println (yytext());
          }
        case 77: break;
        case 37: 
          { return symbol(sym.VOID);  System.out.println (yytext());
          }
        case 78: break;
        case 33: 
          { return symbol(sym.RESERV_INT);  System.out.println (yytext());
          }
        case 79: break;
        case 12: 
          { return symbol(sym.HIGHER);  System.out.println (yytext());
          }
        case 80: break;
        case 31: 
          { return symbol(sym.OR);  System.out.println (yytext());
          }
        case 81: break;
        case 24: 
          { return symbol(sym.LOWEREQUAL);  System.out.println (yytext());
          }
        case 82: break;
        case 5: 
          { return symbol(sym.ID);  System.out.println (yytext());
          }
        case 83: break;
        case 7: 
          { return symbol(sym.PLUS);  System.out.println (yytext());;
          }
        case 84: break;
        case 8: 
          { return symbol(sym.SUB);  System.out.println (yytext());
          }
        case 85: break;
        case 26: 
          { return symbol(sym.ASSIGSUB);  System.out.println (yytext());
          }
        case 86: break;
        case 17: 
          { return symbol(sym.PUNTOCOMA);  System.out.println (yytext());
          }
        case 87: break;
        case 44: 
          { return symbol(sym.RESERV_BOOLEAN);  System.out.println (yytext());
          }
        case 88: break;
        case 38: 
          { return symbol(sym.BREAK);  System.out.println (yytext());
          }
        case 89: break;
        case 14: 
          { return symbol(sym.NEG);  System.out.println (yytext());
          }
        case 90: break;
        case 18: 
          { return symbol(sym.COMA);  System.out.println (yytext());
          }
        case 91: break;
        case 1: 
          { return symbol(sym.STRING);  System.out.println (yytext());
          }
        case 92: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { return new java_cup.runtime.Symbol(sym.EOF); }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
