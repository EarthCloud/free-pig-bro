package cloud.earth.app;

import cloud.earth.pojo.WordEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataAnalyzer {

    private TextHandler textHandler;
    private List<WordEntity> wordEntities;

    public DataAnalyzer(String jsonFileDir){
        this.textHandler=new TextHandler(jsonFileDir);
    }


    /**
     * 处理获得的词频数据
     */
    public void processWordFrequency(){
        final Map<String, Integer> countedWords = textHandler.countWord();

        // 使用Stream API
        this.wordEntities = countedWords.entrySet().stream()
                .map(entry -> new WordEntity(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        // 对List进行排序
        wordEntities.sort((w1,w2) ->
                w2.getWordCount() - w1.getWordCount());
    }

    /**
     * 获得前几名的词频
     * @param rank 要获取的前几名
     * @return 包含前几名的列表
     */
    public List<WordEntity> getWordRank(int rank){
        List<WordEntity> rankedWordList =new ArrayList<>(rank);
        for (int i=0;i<rank;i++){
            rankedWordList.add(wordEntities.get(i));
        }
        return rankedWordList;
    }
}
