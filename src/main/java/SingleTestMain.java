import engine.extracting.EtymonExtractor;
import engine.extracting.NgramExtractor;
import engine.writing.WriterEngine;
import model.data.corpus.ToArffFileCorpus;
import model.feature.corpus.EtymonFeatureCorpus;
import model.feature.corpus.NGramFeatureCorpus;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import util.FeatureMatrix;
import util.Language;
import util.NgramType;
import util.ValueType;
import weka.classifiers.Classifier;
import weka.core.Instances;

import weka.core.converters.ConverterUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by cant on 5/11/17.
 */
public class SingleTestMain {

    public static void main(String[] args)
    {
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String s = "";
            String datasetName = "";
            do {
                System.out.print("Enter test string: ");
                s = br.readLine();
                if(StringUtils.isEmpty(s) || StringUtil.isBlank(s))
                {
                    break;
                }
                System.out.print("Enter dataset prefix string: ");
                datasetName  = br.readLine();
                File tempFile = new File("./resources/input_texts/1/input.txt");
                tempFile.createNewFile();

                org.apache.commons.io.FileUtils.writeStringToFile(tempFile, s, Charset.forName("ISO-8859-9"), false);
                NormalizationZemberek.preProcess("input_texts", "_normalized", true);

                ToArffFileCorpus t2af = new ToArffFileCorpus("./resources/"+ datasetName + "_binary_train_texts_normalized",
                        "./resources/input_texts_normalized",
                        Language.TR);
                EtymonFeatureCorpus etymonFeatureCorpus = EtymonExtractor.extactFeatures(t2af, ValueType.TFIDF);
                NGramFeatureCorpus word2GramTfidfFeatureCorpus = NgramExtractor.extractFeatures(t2af, NgramType.WORD_2_GRAM, ValueType.TFIDF);

                List<FeatureMatrix> featureMatrices = new ArrayList<FeatureMatrix>();
                featureMatrices.add(etymonFeatureCorpus.getFeatureMatrix());
                featureMatrices.add(word2GramTfidfFeatureCorpus.getFeatureMatrix());
                WriterEngine.createArffFiles(featureMatrices, "./");

                // deserialize model
                Classifier deserializedCls = (Classifier) weka.core.SerializationHelper.read("./featureSelectorResults/resources/" + datasetName + "_binary_train_texts_normalized/StemmingWord2GramCombinedTFIDF/naiveBayesMultinomial.model");

                Instances testData = ConverterUtils.DataSource.read("./test.arff");
                testData.setClassIndex(testData.numAttributes() - 1);

                for (int i = 0; i < testData.numInstances() - 1; i++) {
                    double clsLabel = deserializedCls.classifyInstance(testData.instance(i));

                    System.out.println("Prediction: " + testData.classAttribute().value((int) clsLabel));
                }

                tempFile.delete();
            }while(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
