package cn.kt.util;

import com.intellij.openapi.ui.Messages;
import org.mybatis.generator.api.ProgressCallback;

/**
 * mybatis generator进度回调
 * Created by kangtian on 2018/7/17.
 */
public class GeneratorCallback implements ProgressCallback {
    @Override
    public void introspectionStarted(int i) {
    }

    @Override
    public void generationStarted(int i) {

    }

    @Override
    public void saveStarted(int i) {

    }

    @Override
    public void startTask(String s) {
        System.out.println("startTask" + s);
    }

    @Override
    public void done() {
        Messages.showMessageDialog("代码生成完成", "生成进度", Messages.getInformationIcon());
    }

    @Override
    public void checkCancel() throws InterruptedException {

    }
}
