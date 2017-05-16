import com.google.common.base.Joiner;
import org.antlr.v4.runtime.Token;
import org.apache.commons.io.FileUtils;
import sun.nio.cs.StandardCharsets;
import zemberek.morphology.analysis.tr.TurkishMorphology;
import zemberek.normalization.TurkishSpellChecker;
import zemberek.tokenization.TurkishTokenizer;
import zemberek.tokenization.antlr.TurkishLexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cant on 5/11/17.
 */
public class NormalizationZemberek {
    public static void main(String[] args) {
        String newResourceDirectory = preProcess("main_binary_test_texts", "_normalized", true);
        System.out.println("Preprocessed directory is: " + newResourceDirectory);
    }

    public static String preProcess(String targetDirectory, String newDirectorySuffix, boolean normalize){
        TurkishMorphology morphology = null;
        String preProcessedDirectoryPath = "";
        try {
            TurkishTokenizer tokenizer = TurkishTokenizer
                    .builder()
                    .ignoreTypes(TurkishLexer.Punctuation,
                            TurkishLexer.NewLine,
                            TurkishLexer.SpaceTab,
                            TurkishLexer.Time,
                            TurkishLexer.Date,
                            TurkishLexer.URL ,
                            TurkishLexer.Email,
                            TurkishLexer.Mention,
                            TurkishLexer.RomanNumeral,
                            TurkishLexer.UnknownWord,
                            TurkishLexer.Unknown,
                            TurkishLexer.HashTag)
                    .build();

            morphology = TurkishMorphology.createWithDefaults();
            TurkishSpellChecker spellChecker = new TurkishSpellChecker(morphology);

            // This function assumes that we have a list of resources under the "resources" directory
            String resourcesDirectoryName = "resources";
            File resourcesDirectoryFile = new File(resourcesDirectoryName);

            // Find the data directory
            String dataDirectory = resourcesDirectoryName + "/" + targetDirectory;
            File dataDirectoryFile = new File(dataDirectory);

            // Create new data directory
            String newDataDirectoryPath = dataDirectory + newDirectorySuffix;
            preProcessedDirectoryPath = newDataDirectoryPath;

            String[] classesDirectoryList = dataDirectoryFile.list();
            for(String classDirectoryName: classesDirectoryList)
            {
                //Find class directory
                String classesDirectoryFileAbsolutePath = dataDirectory + "/" + classDirectoryName;
                File classDirectoryFile = new File(classesDirectoryFileAbsolutePath);

                //Create new class directory
                String newClassDirectory = preProcessedDirectoryPath + "/" + classDirectoryName;
                File preProcessedFile = new File(newClassDirectory);
                preProcessedFile.mkdirs();

                for(String textFileName : classDirectoryFile.list())
                {
                    Charset charset = Charset.forName("ISO-8859-9");
                    String wholeText = FileUtils.readFileToString(new File(classesDirectoryFileAbsolutePath + "/" + textFileName), charset);

                    String preProcessedText = "";
                    if(normalize)
                    {
                        List<Token> tokens = tokenizer.tokenize(wholeText);

                        ArrayList<String> normalizedStringsList = new ArrayList<String>();
                        for (int i = 0; i< tokens.size(); i++) {
//                        System.out.println("Content = " + tokens.get(i).getText());
//                        System.out.println("Type = " + TurkishLexer.VOCABULARY.getDisplayName(token.getType()));
//                        System.out.println();
                            if(!spellChecker.check(tokens.get(i).getText()))
                            {
                                String foundToken = tokens.get(i).getText();
                                List<String> suggestionsList = spellChecker.suggestForWord(foundToken);
                                if(suggestionsList.size() == 0){
                                    normalizedStringsList.add(foundToken);
                                }
                                else {
                                    normalizedStringsList.add(suggestionsList.get(0));
                                }
                            }
                            else
                            {
                                normalizedStringsList.add(tokens.get(i).getText());
                            }
                        }

                        preProcessedText = Joiner.on(' ').join(normalizedStringsList);
                    }
                    else
                    {
                        preProcessedText = wholeText;
                    }

                    File normalizedFile = new File(newClassDirectory + "/" + textFileName);
                    FileUtils.writeStringToFile(normalizedFile, preProcessedText, Charset.forName("UTF-8"), false);
                }
            }

            return preProcessedDirectoryPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return preProcessedDirectoryPath;
    }
}
