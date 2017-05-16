import akka.japi.Pair;
import org.canova.api.records.reader.LibSvm;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.SimpleLogistic;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by cant on 5/6/17.
 */

public class testDemo {
    public static void main(String[] args)
    {
        try {

            NaiveBayesMultinomial naiveBayesMultinomial = new weka.classifiers.bayes.NaiveBayesMultinomial();

            ArrayList<Pair<Classifier,String>> classifierPairList = new ArrayList<Pair<Classifier,String>>();

            classifierPairList.add(new Pair(naiveBayesMultinomial, "naiveBayesMultinomial"));

            ArrayList<Pair<String, String>> trainTestPairList = new ArrayList<Pair<String, String>>();

            trainTestPairList.add(
                    new Pair(
                            NormalizationZemberek.preProcess("smaller_binary_train_texts", "_normalized", true),
                            NormalizationZemberek.preProcess("smaller_binary_test_texts","_normalized", true)));

            for(Pair<String, String> trainTestPair : trainTestPairList) {
                ArrayList<FeatureSelector> featureSelectors = new ArrayList<FeatureSelector>();

                featureSelectors.add(
                        new StemmingWord2GramCombinedTFIDF(
                                trainTestPair.first(),
                                trainTestPair.second(),
                                "./featureSelectorResults/" + trainTestPair.first() + "/StemmingWord2GramCombinedTFIDF"));

                for (FeatureSelector fs : featureSelectors) {
                    fs.run();
                    // loads data and set class index
                    Instances trainData = ConverterUtils.DataSource.read(fs.folderPath + "/train.arff");
                    trainData.setClassIndex(trainData.numAttributes() - 1);

                    for (Pair<Classifier,String> classifierPair : classifierPairList) {
                        // randomize data
                        int folds = 10;
                        int seed = 1;
                        Random rand = new Random(seed);
                        Instances randData = new Instances(trainData);
                        randData.randomize(rand);
                        if (randData.classAttribute().isNominal())
                            randData.stratify(folds);

                        Evaluation evalAll = new Evaluation(randData);
                        for (int n = 0; n < folds; n++) {
                            Evaluation eval = new Evaluation(randData);
                            Instances train = trainData.trainCV(folds, n);
                            Instances test = trainData.testCV(folds, n);

                            // build and evaluate classifier
                            Classifier copiedClassifier = AbstractClassifier.makeCopy(classifierPair.first());
                            copiedClassifier.buildClassifier(train);
                            eval.evaluateModel(copiedClassifier, test);
                            evalAll.evaluateModel(copiedClassifier, test);

                        }

                        // output evaluation
                        System.out.println(classifierPair.second());
                        System.out.println("Training dataset: " + trainTestPair.first());
                        System.out.println(evalAll.toSummaryString("=== " + folds + "-fold Cross-validation ===", true));
                        System.out.println(new WekaFeatureClassifierStatistics(classifierPair.first().getClass().getName(), fs.folderPath, evalAll));

                        // serialize the model
                        Classifier serializedCls = classifierPair.first();
                        serializedCls.buildClassifier(new Instances(trainData));
                        weka.core.SerializationHelper.write(fs.folderPath + "/" + classifierPair.second() + ".model", serializedCls);

                    }
                }
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }


    }
}
