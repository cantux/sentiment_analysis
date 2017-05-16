import weka.classifiers.Evaluation;

import java.util.ArrayList;

/**
 * Created by cant on 5/8/17.
 */
public class WekaFeatureClassifierStatistics {

    private String m_classifierName;
    private String m_featureSelectorName;
    private double m_weightedFMeasure;
    private double m_weightedPrecision;
    private double m_weightedRecall;
    private double m_weightedTrueNegativeRate;
    private double m_weightedMatthewsCorrelation;
    private double m_weightedAreaUnderPRC;
    private double m_weightedAreaUnderROC;
    private ArrayList<WekaEvaluationClassStatistics> m_evaluationClassStatistics;

    public WekaFeatureClassifierStatistics(String classifierName, String featureSelectorName, Evaluation eval)
    {
        m_classifierName = classifierName;
        m_featureSelectorName = featureSelectorName;
        m_weightedFMeasure = eval.weightedFMeasure();
        m_weightedPrecision = eval.weightedPrecision();
        m_weightedRecall = eval.weightedRecall();
        m_weightedTrueNegativeRate = eval.weightedTrueNegativeRate(); // whole specificty
        m_weightedMatthewsCorrelation = eval.weightedMatthewsCorrelation();
        m_weightedAreaUnderPRC = eval.weightedAreaUnderPRC(); // precision recall curve
        m_weightedAreaUnderROC = eval.weightedAreaUnderROC();

        m_evaluationClassStatistics = new ArrayList<WekaEvaluationClassStatistics>();
        for(int i = 0; i < eval.confusionMatrix().length; i++)
        {
            m_evaluationClassStatistics.add(new WekaEvaluationClassStatistics(i, eval));
        }
    }

    public String toString() {
        return "Weighted Classifier Name : " + String.valueOf(m_classifierName) + "\n" +
               "Weighted FS Name         : " + String.valueOf(m_featureSelectorName) + "\n" +
               "Weighted F-Measure       : " + String.valueOf(m_weightedFMeasure) + "\n" +
               "Weighted Precision       : " + String.valueOf(m_weightedPrecision) + "\n" +
               "Weighted Recall          : " + String.valueOf(m_weightedRecall) + "\n" +
               "Weighted Specificity(TNR): " + String.valueOf(m_weightedTrueNegativeRate) + "\n" +
               "Weighted MCC             : " + String.valueOf(m_weightedMatthewsCorrelation) + "\n" +
               "Weighted Area Under PRC  : " + String.valueOf(m_weightedAreaUnderPRC) + "\n" +
               "Weighted Area Under ROC  : " + String.valueOf(m_weightedAreaUnderROC) + "\n";
                // + "---Class Specific Info---\n" + m_evaluationClassStatistics.toString();
    }
}
