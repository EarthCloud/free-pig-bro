package cloud.earth.app;

import cloud.earth.pojo.WordEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class ConsoleFrame {

    private DataAnalyzer dataAnalyzer;
    private Scanner scanner = new Scanner(System.in);

    public void init() {
        System.out.println("正在加载文件...");
        dataAnalyzer = new DataAnalyzer(loadFile());
        System.out.println("数据初始化完毕");
        System.out.println("正在处理数据...");
        dataAnalyzer.processWordFrequency();
        System.out.println("数据处理完毕...");
    }

    public void accessData() {
        System.out.println("请输入希望获取前多少名的数据");
        int rank = scanner.nextInt();
        System.out.println("获取到的数据如下：");
        final List<WordEntity> wordRank = dataAnalyzer.getWordRank(rank);
        for (int i = 0; i < rank; i++) {
            System.out.printf("排名第%d的单词是“%s”，出现了%d次！\n",
                    i + 1, wordRank.get(i).getWord(), wordRank.get(i).getWordCount());
        }
    }

    public void close() {
        System.out.println("欢迎再次使用");
    }

    public String loadFile() {

        Path start = Paths.get("data");

        // try-with-resources 自动关闭流
        try (Stream<Path> stream = Files.walk(start)) {
            final List<Path> paths = stream.filter(p -> !Files.isDirectory(p)) // 可选：只筛选文件
                    .filter(p -> p.toString().endsWith(".json")) // 可选：只筛选特定后缀
                    .toList();

            System.out.println("读取完毕，我需要nx！！！");

            // 输出文件名让用户选择
            System.out.println("请键入开头的数字以选择json文件：");
            int index = 1;
            for (Path path : paths) {
                System.out.println(index + "->" + path.getFileName().toString());
                index++;
            }
            final int i = scanner.nextInt();
            return "data/" + paths.get(i - 1).getFileName().toString();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("很遗憾，读取失败。");
        }
        return "";
    }
}
