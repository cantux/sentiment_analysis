import weka.classifiers.Evaluation;

/**
 * Created by cant on 5/8/17.
 */
public class WekaEvaluationClassStatistics
{
    private int m_classIndex;
    private double m_precision; // positive predictive value (PPV)
    private double m_fMeasure;
    private double m_recall;
    private double m_trueNegativeRate; //specificity
    private double m_matthewsCorrelationCoefficient;
    private double m_areaUnderPRC;
    private double m_areaUnderROC;

    public WekaEvaluationClassStatistics(int classIndex,
                                         Evaluation eval)
    {
        this.m_classIndex = classIndex;
        this.m_precision = eval.precision(classIndex);
        this.m_fMeasure = eval.fMeasure(classIndex);
        this.m_recall = eval.recall(classIndex);
        this.m_trueNegativeRate = eval.trueNegativeRate(classIndex);
        m_matthewsCorrelationCoefficient = eval.matthewsCorrelationCoefficient(classIndex);
        m_areaUnderPRC = eval.areaUnderPRC(classIndex);
        m_areaUnderROC = eval.areaUnderROC(classIndex);

    }

    public String toString()
    {
        return "\tClass Index      : " + String.valueOf(m_classIndex) + "\n" +
               "\tF-Measure        : " + String.valueOf(m_fMeasure) + "\n" +
               "\tPrecision        : " + String.valueOf(m_precision) + "\n" +
               "\tRecall           : " + String.valueOf(m_recall) + "\n" +
               "\tSpecificity(TNR) : " + String.valueOf(m_trueNegativeRate) + "\n" +
               "\tMCC              : " + String.valueOf(m_matthewsCorrelationCoefficient) + "\n" +
               "\tArea Under PRC   : " + String.valueOf(m_areaUnderPRC) + "\n" +
               "\tArea Under ROC   : " + String.valueOf(m_areaUnderROC) + "\n";
    }
}
