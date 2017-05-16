import engine.extracting.NgramExtractor;
import engine.writing.WriterEngine;
import model.data.corpus.ToArffFileCorpus;
import model.feature.corpus.NGramFeatureCorpus;
import util.FeatureMatrix;
import util.Language;
import util.NgramType;
import util.ValueType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cant on 5/8/17.
 */
public class BigramTFIDF extends FeatureSelector {
    public BigramTFIDF(String trainTextsFolder,
                       String testTextsFolder,
                       String folderPath) {
        super(trainTextsFolder, testTextsFolder, folderPath);
    }

    public void run() {
        super.run();
        System.out.println("bigram TFIDF");
        try
        {
            ToArffFileCorpus biGramTfidfArffFileCorpus = new ToArffFileCorpus(
                    super.trainTextsFolder,
                    super.testTextsFolder,
                    Language.TR);

            NGramFeatureCorpus biGramTfidfFeatureCorpus = NgramExtractor.extractFeatures(biGramTfidfArffFileCorpus, NgramType.GRAM_2, ValueType.TFIDF);

            FeatureMatrix ngramFeatureMatrix = biGramTfidfFeatureCorpus.getFeatureMatrix();

            List<FeatureMatrix> featureMatrices = new ArrayList<FeatureMatrix>();
            featureMatrices.add(ngramFeatureMatrix);

            WriterEngine.createArffFiles(featureMatrices, super.folderPath);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
