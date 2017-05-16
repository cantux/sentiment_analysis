import engine.extracting.EtymonExtractor;
import engine.extracting.NgramExtractor;
import engine.writing.WriterEngine;
import model.data.corpus.ToArffFileCorpus;
import model.feature.corpus.EtymonFeatureCorpus;
import model.feature.corpus.NGramFeatureCorpus;
import util.FeatureMatrix;
import util.Language;
import util.NgramType;
import util.ValueType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cant on 5/13/17.
 */
public class StemmingWord2GramCombinedTFIDF extends FeatureSelector {
    public StemmingWord2GramCombinedTFIDF(String trainTextsFolder,
                                          String testTextsFolder,
                                          String folderPath) {
        super(trainTextsFolder, testTextsFolder, folderPath);
    }

    public void run() {
        super.run();
        System.out.println("Stemming Word2Gram Combined TFIDF");
        try
        {
            ToArffFileCorpus toArffFileCorpus = new ToArffFileCorpus(
                    super.trainTextsFolder,
                    super.testTextsFolder,
                    Language.TR);
//            FWords fWords = new FWords("./resources/fWordsTR.txt", Language.TR);
//            FWordCorpus fWordFilteredCorpus = toArffFileCorpus.getFWordCorpus(fWords).getFWordCorpusWithFiltering(1,2);

//
////            NGramCorpus biGramCorpus = toArffFileCorpus.getNGramCorpus(NgramType.GRAM_2).getNGramCorpusWithFiltering(5, 100);
////            NGramCorpus triGramCorpus = toArffFileCorpus.getNGramCorpus(NgramType.GRAM_3).getNGramCorpusWithFiltering(3, 50);
////            NGramCorpus word2GramCorpus = toArffFileCorpus.getNGramCorpus(NgramType.WORD_2_GRAM).getNGramCorpusWithFiltering(1, 25);
//
            System.out.println("Corpus creation start");
            EtymonFeatureCorpus etymonFeatureCorpus = EtymonExtractor.extactFeatures(toArffFileCorpus, ValueType.TFIDF);
//
//            NGramFeatureCorpus biGramTfidfFeatureCorpus = NgramExtractor.extractFeatures(toArffFileCorpus, NgramType.GRAM_2, ValueType.TF);
//            NGramFeatureCorpus triGramTfidfFeatureCorpus = NgramExtractor.extractFeatures(toArffFileCorpus, NgramType.GRAM_3, ValueType.TF);
            NGramFeatureCorpus word2GramTfidfFeatureCorpus = NgramExtractor.extractFeatures(toArffFileCorpus, NgramType.WORD_2_GRAM, ValueType.TFIDF);
//
            System.out.println("Feature matrix add");
////3
            List<FeatureMatrix> featureMatrices = new ArrayList<FeatureMatrix>();
//            featureMatrices.add(biGramTfidfFeatureCorpus.getFeatureMatrix());
            featureMatrices.add(etymonFeatureCorpus.getFeatureMatrix());
//            featureMatrices.add(triGramTfidfFeatureCorpus.getFeatureMatrix());
            featureMatrices.add(word2GramTfidfFeatureCorpus.getFeatureMatrix());
//
            System.out.println("Create arff files");
            WriterEngine.createArffFiles(featureMatrices, super.folderPath);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
