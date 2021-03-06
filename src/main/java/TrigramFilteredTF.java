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
public class TrigramFilteredTF extends FeatureSelector {
    public TrigramFilteredTF(String trainTextsFolder,
                             String testTextsFolder,
                             String folderPath) {
        super(trainTextsFolder, testTextsFolder, folderPath);
    }

    public void run() {
        super.run();

        System.out.println("filtered trigram TF");
        try
        {
            ToArffFileCorpus afc9 = new ToArffFileCorpus(
                    super.trainTextsFolder,
                    super.testTextsFolder,
                    Language.TR);

            NGramCorpus filteredTriGramCorpus = afc9.getNGramCorpus(NgramType.GRAM_3).getNGramCorpusWithFiltering(1,50);

            FeatureMatrix ngramFeatureMatrix = NgramExtractor.extractFeatures(filteredTriGramCorpus, NgramType.GRAM_3, ValueType.TF).getFeatureMatrix();

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
