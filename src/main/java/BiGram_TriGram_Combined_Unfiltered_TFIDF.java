import engine.extracting.NgramExtractor;
import engine.writing.WriterEngine;
import model.data.corpus.NGramCorpus;
import model.data.corpus.ToArffFileCorpus;
import model.feature.corpus.NGramFeatureCorpus;
import util.FeatureMatrix;
import util.Language;
import util.NgramType;
import util.ValueType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cant on 5/11/17.
 */
public class BiGram_TriGram_Combined_Unfiltered_TFIDF extends FeatureSelector {
    public BiGram_TriGram_Combined_Unfiltered_TFIDF(String trainTextsFolder,
                                                 String testTextsFolder,
                                                 String folderPath) {
        super(trainTextsFolder, testTextsFolder, folderPath);
    }

    public void run() {
        super.run();
        System.out.println("bigram trigram unfiltered TF");
        try
        {
            ToArffFileCorpus biGramTriGramTfArffFileCorpus = new ToArffFileCorpus(
                    super.trainTextsFolder,
                    super.testTextsFolder,
                    Language.TR);
            NGramCorpus biGramCorpus = biGramTriGramTfArffFileCorpus.getNGramCorpus(NgramType.GRAM_2).getNGramCorpusWithFiltering(5, 100);
            NGramFeatureCorpus biGramTfidfFeatureCorpus = NgramExtractor.extractFeatures(biGramCorpus, NgramType.GRAM_2, ValueType.TFIDF);

            NGramCorpus triGramCorpus = biGramTriGramTfArffFileCorpus.getNGramCorpus(NgramType.GRAM_3).getNGramCorpusWithFiltering(3, 50);
            NGramFeatureCorpus triGramFeatureCorpus = NgramExtractor.extractFeatures(triGramCorpus, NgramType.GRAM_3, ValueType.TFIDF);


            FeatureMatrix biGramFeatureMatrix = biGramTfidfFeatureCorpus.getFeatureMatrix();
            FeatureMatrix triGramFeatureMatrix = triGramFeatureCorpus.getFeatureMatrix();

            List<FeatureMatrix> featureMatrices = new ArrayList<FeatureMatrix>();
            featureMatrices.add(biGramFeatureMatrix);
            featureMatrices.add(triGramFeatureMatrix);

            WriterEngine.createArffFiles(featureMatrices, super.folderPath);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
