import engine.writing.WriterEngine;
import model.data.corpus.EtymonCorpus;
import model.data.corpus.ToArffFileCorpus;
import util.FeatureMatrix;
import util.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cant on 5/8/17.
 */
public class Stemming_Filtered_3_100_LSI_TF extends FeatureSelector {
    public Stemming_Filtered_3_100_LSI_TF(String trainTextsFolder,
                                          String testTextsFolder,
                                          String folderPath) {
        super(trainTextsFolder, testTextsFolder, folderPath);
    }

    public void run() {
        super.run();
        System.out.println("Filtered Stemming LSI TF");
        try
        {
            ToArffFileCorpus filteredLsiFeatArffFileCorpus = new ToArffFileCorpus(
                    super.trainTextsFolder,
                    super.testTextsFolder,
                    Language.TR);

            // Filtered Stemming
            EtymonCorpus etymonCorpus1 = filteredLsiFeatArffFileCorpus.getEtymonCorpus().getEtymonCorpusWithFiltering(3,100);

            FeatureMatrix lsiFeatureMatrix = etymonCorpus1.getLSIFeatureMatrix(10); // always TF

            List<FeatureMatrix> featureMatrices = new ArrayList<FeatureMatrix>();
            featureMatrices.add(lsiFeatureMatrix);

            WriterEngine.createArffFiles(featureMatrices, super.folderPath);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
