import java.io.File;

/**
 * Created by cant on 5/8/17.
 */
public class FeatureSelector {
    public String folderPath;
    public String trainTextsFolder;
    public String testTextsFolder;
    public FeatureSelector(String trainTextsFolder,
                           String testTextsFolder,
                           String folderPath)
    {
        this.trainTextsFolder = trainTextsFolder;
        this.testTextsFolder = testTextsFolder;
        this.folderPath = folderPath;
    }

    private void initializeResultsFolder()
    {
        File featureSelectorFolder = new File(this.folderPath);
        featureSelectorFolder.delete();
        featureSelectorFolder.mkdirs();
    }

    public void run(){
        this.initializeResultsFolder();
    }
}
