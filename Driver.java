package p2;

import java.io.File;

/**
 *
 * @author jacobson
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File dictionaryFile = new File("words.txt");
        File processingFile = new File("test.txt");
        DocCheck.spellCheck(processingFile, dictionaryFile);
        DocCheck.wordCount(processingFile);
    }
}
