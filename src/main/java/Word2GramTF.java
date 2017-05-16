import engine.extracting.NgramExtractor;
import engine.writing.WriterEngine;
import model.data.corpus.NGramCorpus;
import model.data.corpus.ToArffFileCorpus;
import util.FeatureMatrix;
import util.Language;
import util.NgramType;
import util.ValueType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cant on 5/8/17.
 */
public class Word2GramTF extends FeatureSelector {
    public Word2GramTF(String trainTextsFolder,
                       String testTextsFolder,
                       String folderPath) {
        super(trainTextsFolder, testTextsFolder, folderPath);
    }

    public void run() {
        super.run();

        System.out.println("Kelime2Gram");
        try
        {
            ToArffFileCorpus afc9 = new ToArffFileCorpus(
                    super.trainTextsFolder,
                    super.testTextsFolder,
                    Language.TR);

            NGramCorpus word2GramCorpus = afc9.getNGramCorpus(NgramType.WORD_2_GRAM);

            FeatureMatrix ngramFeatureMatrix = NgramExtractor.extractFeatures(word2GramCorpus, NgramType.WORD_2_GRAM, ValueType.TF).getFeatureMatrix();

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
