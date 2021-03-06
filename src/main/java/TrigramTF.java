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
 * Created by cant on 5/8/17.
 */
public class TrigramTF extends FeatureSelector {
    public TrigramTF(String trainTextsFolder,
                     String testTextsFolder,
                     String folderPath) {
        super(trainTextsFolder, testTextsFolder, folderPath);
    }

    public void run() {
        super.run();

        System.out.println("Trigram TF");
        try
        {
            ToArffFileCorpus afc5 = new ToArffFileCorpus(
                    super.trainTextsFolder,
                    super.testTextsFolder,
                    Language.TR);
            NGramFeatureCorpus nGramFeatureCorpus = NgramExtractor.extractFeatures(afc5, NgramType.GRAM_3, ValueType.TF);
            FeatureMatrix etymonFeatureMatrix = nGramFeatureCorpus.getFeatureMatrix();

            List<FeatureMatrix> featureMatrices = new ArrayList<FeatureMatrix>();
            featureMatrices.add(etymonFeatureMatrix);

            WriterEngine.createArffFiles(featureMatrices, super.folderPath);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
