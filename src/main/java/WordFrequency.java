import engine.extracting.CountsExtractor;
import engine.writing.WriterEngine;
import model.data.corpus.ToArffFileCorpus;
import model.feature.corpus.CountFeatureCorpus;
import util.FeatureMatrix;
import util.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cant on 5/11/17.
 */
public class WordFrequency extends FeatureSelector {
    public WordFrequency(String trainTextsFolder,
                         String testTextsFolder,
                         String folderPath) {
        super(trainTextsFolder, testTextsFolder, folderPath);
        }

    public void run() {
        super.run();

        System.out.println("Word Frequency");
        try
        {
            ToArffFileCorpus toArffFileCorpus = new ToArffFileCorpus(
                    super.trainTextsFolder,
                    super.testTextsFolder,
                    Language.TR);
            CountFeatureCorpus cfc = CountsExtractor.getCountFeatures(toArffFileCorpus);

            FeatureMatrix fm = cfc.getFeatureMatrix();

            List<FeatureMatrix> featureMatrices = new ArrayList<FeatureMatrix>();
            featureMatrices.add(fm);

            WriterEngine.createArffFiles(featureMatrices, super.folderPath);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
