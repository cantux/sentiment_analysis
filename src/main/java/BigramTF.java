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
 * Created by cant on 5/8/17.
 */
public class BigramTF extends FeatureSelector {
    public BigramTF(String trainTextsFolder,
                    String testTextsFolder,
                    String folderPath) {
        super(trainTextsFolder, testTextsFolder, folderPath);
    }

    public void run() {
        super.run();
        System.out.println("bigram TF");
        try
        {
            ToArffFileCorpus biGramTfArffFileCorpus = new ToArffFileCorpus(
                    super.trainTextsFolder,
                    super.testTextsFolder,
                    Language.TR);

            NGramFeatureCorpus nGramFeatureCorpus = NgramExtractor.extractFeatures(biGramTfArffFileCorpus, NgramType.GRAM_2, ValueType.TF);

            FeatureMatrix ngramFeatureMatrix = nGramFeatureCorpus.getFeatureMatrix();

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
