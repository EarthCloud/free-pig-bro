package cloud.earth.app;

import cloud.earth.pojo.TextEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MessageHandler {
    @Setter
    private String jsonFileDir;
    private final String NORMAL_CONTENT_PATH = "/content/text";
    private final String REPLY_CONTENT_PATH = "/rawMessage/elements/2/textElement/content";
    private final String DATE_PATH = "/timestamp";
    private final String SENDER_PATH = "/sender/uin";

    public List<TextEntity> readJson() {
        ObjectMapper mapper = new ObjectMapper();
        List<TextEntity> texts = new ArrayList<>();
        // 判断路径是否为空
        try {
            if (jsonFileDir.isBlank()) return null;
            JsonNode node = mapper.readTree(new File(jsonFileDir));

            for (int i = 0; !node.at(getMessagePath(i)).isMissingNode(); i++) {
                // 判断消息类型
                JsonNode messageNode = node.at(getMessagePath(i));
                int messageType = node.at(getMessageTypePath(i)).asInt();

                if (messageType == 2) texts.add(processNormalMessage(messageNode));
                if (messageType == 9) texts.add(processReplyMessage(messageNode));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件读取失败，请检查路径是否正确");
        }

        return texts;
    }

    // 传入消息索引，封装成字符串
    private String getMessagePath(int index) {
        return String.format("/messages/%d", index);
    }

    private String getMessageTypePath(int index) {
        return String.format("/messages/%d/messageType", index);
    }

    private TextEntity processNormalMessage(JsonNode messageNode) {
        TextEntity text = new TextEntity();
        text.setContent(messageNode.at(NORMAL_CONTENT_PATH).asText());
        text.setSender(messageNode.at(SENDER_PATH).asText());
        text.setTimestamp(messageNode.at(DATE_PATH).asText());
        return text;
    }

    private TextEntity processReplyMessage(JsonNode messageNode) {
        TextEntity text = new TextEntity();
        text.setContent(messageNode.at(REPLY_CONTENT_PATH).asText());
        text.setSender(messageNode.at(SENDER_PATH).asText());
        text.setTimestamp(messageNode.at(DATE_PATH).asText());
        return text;
    }

}
