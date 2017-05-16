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

public class main {
    public static void main(String[] args)
    {
        try {

            NaiveBayes naiveBayes = new weka.classifiers.bayes.NaiveBayes();

            NaiveBayes naiveBayesWithKernelEstimator = new weka.classifiers.bayes.NaiveBayes();
            naiveBayesWithKernelEstimator.setUseKernelEstimator(true);

            NaiveBayes naiveBayesWithBoth = new weka.classifiers.bayes.NaiveBayes();
            naiveBayesWithBoth.setUseSupervisedDiscretization(true);
            naiveBayesWithBoth.setUseKernelEstimator(true);

            NaiveBayesMultinomial naiveBayesMultinomial = new weka.classifiers.bayes.NaiveBayesMultinomial();

            SimpleLogistic sl = new weka.classifiers.functions.SimpleLogistic();

            // Maximum entropy
            Logistic logisticCDG = new weka.classifiers.functions.Logistic(); // Platt's sequential minimal optimization algorithm to learn a support vector machine
            logisticCDG.setUseConjugateGradientDescent(true);

            Logistic logistic = new weka.classifiers.functions.Logistic();
            logistic.setMaxIts(10);

            // Support Vector Machine
            SMO smo = new weka.classifiers.functions.SMO(); //  multinomial logistic regression by maximizing conditional likelihood

            ArrayList<Pair<Classifier,String>> classifierPairList = new ArrayList<Pair<Classifier,String>>();

            classifierPairList.add(new Pair(naiveBayes, "naiveBayes"));
            classifierPairList.add(new Pair(naiveBayesWithKernelEstimator, "naiveBayesWithKernelEstimator"));
            classifierPairList.add(new Pair(naiveBayesWithBoth, "naiveBayesWithBoth"));
            classifierPairList.add(new Pair(naiveBayesMultinomial, "naiveBayesMultinomial"));

            classifierPairList.add(new Pair(sl, "simpleLogistic"));
            classifierPairList.add(new Pair(logisticCDG, "logisticCDG"));
            classifierPairList.add(new Pair(logistic, "logistic"));
            classifierPairList.add(new Pair(smo, "smo"));


            ArrayList<Pair<String, String>> trainTestPairList = new ArrayList<Pair<String, String>>();

            trainTestPairList.add(
                    new Pair(
                            NormalizationZemberek.preProcess("smaller_binary_train_texts", "_encoding_corrected", false),
                            NormalizationZemberek.preProcess("smaller_binary_test_texts","_encoding_corrected", false)));
            trainTestPairList.add(
                    new Pair(
                        NormalizationZemberek.preProcess("smaller_binary_train_texts", "_normalized", true),
                        NormalizationZemberek.preProcess("smaller_binary_test_texts","_normalized", true)));


            trainTestPairList.add(
                    new Pair(
                            NormalizationZemberek.preProcess("main_binary_train_texts", "_encoding_corrected", false),
                            NormalizationZemberek.preProcess("main_binary_test_texts","_encoding_corrected", false)));
            trainTestPairList.add(
                    new Pair(
                            NormalizationZemberek.preProcess("main_binary_train_texts", "_normalized", true),
                            NormalizationZemberek.preProcess("main_binary_test_texts","_normalized", true)));


            trainTestPairList.add(
                    new Pair(
                            NormalizationZemberek.preProcess("smaller_three_class_train_texts", "_encoding_corrected", true),
                            NormalizationZemberek.preProcess("smaller_three_class_test_texts","_encoding_corrected", true)));
            trainTestPairList.add(
                    new Pair(
                            NormalizationZemberek.preProcess("smaller_three_class_train_texts", "_normalized", true),
                            NormalizationZemberek.preProcess("smaller_three_class_test_texts","_normalized", true)));



            trainTestPairList.add(
                    new Pair(
                            NormalizationZemberek.preProcess("main_three_class_train_texts", "_encoding_corrected", false),
                            NormalizationZemberek.preProcess("main_three_class_test_texts","_encoding_corrected", false)));

            trainTestPairList.add(
                    new Pair(
                            NormalizationZemberek.preProcess("main_three_class_train_texts", "_normalized", false),
                            NormalizationZemberek.preProcess("main_three_class_test_texts","_normalized", false)));

            for(Pair<String, String> trainTestPair : trainTestPairList) {
                ArrayList<FeatureSelector> featureSelectors = new ArrayList<FeatureSelector>();
                featureSelectors.add(new Stemming_Unfiltered_TF(trainTestPair.first(), trainTestPair.second(), "./featureSelectorResults/" + trainTestPair.first() + "/Stemming_Unfiltered_TF"));
                featureSelectors.add(new Stemming_Unfiltered_TFIDF(trainTestPair.first(), trainTestPair.second(),"./featureSelectorResults/" + trainTestPair.first() + "/Stemming_Unfiltered_TFIDF"));

                featureSelectors.add(new StemmingWord2GramCombinedTF(trainTestPair.first(), trainTestPair.second(),"./featureSelectorResults/" + trainTestPair.first() + "/StemmingWord2GramCombinedTF"));
                featureSelectors.add(new StemmingWord2GramCombinedTFIDF(trainTestPair.first(), trainTestPair.second(),"./featureSelectorResults/" + trainTestPair.first() + "/StemmingWord2GramCombinedTFIDF"));

                featureSelectors.add(new BigramTFIDF(trainTestPair.first(), trainTestPair.second(),"./featureSelectorResults/" + trainTestPair.first() + "/BigramTFIDF"));
                featureSelectors.add(new BigramFilteredTF(trainTestPair.first(), trainTestPair.second(),"./featureSelectorResults/" + trainTestPair.first() + "/BigramFilteredTF"));
                featureSelectors.add(new TrigramTF(trainTestPair.first(), trainTestPair.second(),"./featureSelectorResults/" + trainTestPair.first() + "/TrigramTF"));
                featureSelectors.add(new TrigramFilteredTF(trainTestPair.first(), trainTestPair.second(),"./featureSelectorResults/" + trainTestPair.first() + "/TrigramFilteredTF"));

                featureSelectors.add(new Word2GramTF(trainTestPair.first(), trainTestPair.second(),"./featureSelectorResults/" + trainTestPair.first() + "/Word2GramTF"));
                featureSelectors.add(new Word2GramFilteredTF(trainTestPair.first(), trainTestPair.second(),"./featureSelectorResults/" + trainTestPair.first() + "/Word2GramFilteredTF"));

                for (FeatureSelector fs : featureSelectors) {
                    fs.run();
                    // loads data and set class index
                    Instances trainData = DataSource.read(fs.folderPath + "/train.arff");
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
