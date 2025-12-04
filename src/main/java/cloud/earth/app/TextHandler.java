package cloud.earth.app;

import cloud.earth.pojo.TextEntity;
import cloud.earth.util.StringUtil;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TextHandler {
    private MessageHandler messageHandler = new MessageHandler();
    private List<String> texts;

    /**
     * 构造函数，必须传入路径
     * @param fileDir 文件路径
     */
    public TextHandler(String fileDir) {
        // 处理信息
        messageHandler.setJsonFileDir(fileDir);
        List<TextEntity> textEntities = messageHandler.readJson();

        // 对信息进行处理，得到包含所有信息的list
        this.texts = textEntities.stream().map(textEntity -> {
            return StringUtil.trim(textEntity.getContent());
        }).collect(Collectors.toList());
    }

    /**
     * 对消息进行处理
     * @return 存储词语和频数的Map
     */
    public Map<String, Integer> countWord() {
        if (texts == null) return null;

        // 用HashMap便于快速查找
        Map<String, Integer> wordFrequency = new HashMap<>();
        for (String text : texts) {
            final List<String> words = divideText(text);

            // 放入Map中进行统计
            words.forEach(word -> {
                wordFrequency.putIfAbsent(word, 0);
                wordFrequency.put(word, wordFrequency.get(word) + 1);
            });
        }

        return wordFrequency;
    }

    /**
     * 调用HanLP进行分词
     * @param rawText 原始文本
     * @return 获得的词列表
     */
    private List<String> divideText(String rawText) {
        List<Term> terms = HanLP.segment(rawText);
        CoreStopWordDictionary.apply(terms);
        List<Term> filteredTerms=terms.stream().
                filter(term -> term.word.length()>=2)
                .toList();

        List<String> words = new ArrayList<>(terms.size());
        filteredTerms.forEach(term -> {
            words.add(term.word);
        });
        return words;
    }
}
