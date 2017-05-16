import engine.extracting.EtymonExtractor;
import engine.writing.WriterEngine;
import model.data.corpus.ToArffFileCorpus;
import model.feature.corpus.EtymonFeatureCorpus;
import util.FeatureMatrix;
import util.Language;
import util.ValueType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cant on 5/8/17.
 */
public class Stemming_Unfiltered_TF extends FeatureSelector {

    public Stemming_Unfiltered_TF(String trainTextsFolder,
                                  String testTextsFolder,
                                  String folderPath) {
        super(trainTextsFolder, testTextsFolder, folderPath);
    }

    public void run() {
        super.run();

        System.out.println("UnFiltered Stemming TF");
        try {
            ToArffFileCorpus unfilteredTfArffFileCorpusfil = new ToArffFileCorpus(
                    super.trainTextsFolder,
                    super.testTextsFolder,
                    Language.TR);

            EtymonFeatureCorpus etymonFeatureCorpus = EtymonExtractor.extactFeatures(unfilteredTfArffFileCorpusfil, ValueType.TF);
            FeatureMatrix etymonFeatureMatrix = etymonFeatureCorpus.getFeatureMatrix();

            List<FeatureMatrix> featureMatrices = new ArrayList<FeatureMatrix>();
            featureMatrices.add(etymonFeatureMatrix);

            WriterEngine.createArffFiles(featureMatrices, super.folderPath);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
