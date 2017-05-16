import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils;

import java.util.Random;

/**
 * Created by cant on 5/11/17.
 */
public class TestAgainstSet {
    public static void main(String[] args) {
//        System.out.println("Create test instances");
//        Instances testData = ConverterUtils.DataSource.read(fs.folderPath + "/test.arff"); //"./results/train.arff"
//        testData.setClassIndex(testData.numAttributes() - 1);
//
//        System.out.println("Train classifiers");
//
//        for (String classifierName : classifiers) {
//            // build and evaluate classifier
//            System.out.println("Classifier: " + classifierName);
//            Classifier cls = (Classifier) Utils.forName(Classifier.class, classifierName, null);
//            Evaluation eval = new Evaluation(trainData);
//            Classifier copiedClassifier = AbstractClassifier.makeCopy(cls);
//            copiedClassifier.buildClassifier(trainData);
//
////                eval.evaluateModel(cls, testData);
//            eval.crossValidateModel(cls, trainData, 10, new Random());
//
//            System.out.println(eval.toSummaryString("=== Test Set 10 folds ===", false));
//            System.out.println();
//            System.out.println(new WekaFeatureClassifierStatistics(cls.getClass().getName(), fs.folderPath, eval));

//        }
    }
}
