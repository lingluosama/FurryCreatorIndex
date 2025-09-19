package org.rookie.data.utils;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.DeltaType;
import com.github.difflib.patch.Patch;

import java.util.Arrays;
import java.util.List;

public class TextDiffer {

    /**
     * 比较两个文本，并判断差异类型。
     * @param originalText 原始文本
     * @param newText 新文本
     * @return 0: 无差异; 1: 纯添加; 2: 纯删除; 3: 包含修改或混合类型差异
     */
    public static int compareTexts(String originalText, String newText) {
        if (originalText == null || newText == null) {
            return 3; // 至少一个文本为空，视为无法判断
        }

        // 将文本按行分割成列表
        List<String> originalLines = Arrays.asList(originalText.split("\\n"));
        List<String> newLines = Arrays.asList(newText.split("\\n"));

        // 获取差异补丁
        Patch<String> patch = DiffUtils.diff(originalLines, newLines);

        // 如果没有差异，直接返回
        if (patch.getDeltas().isEmpty()) {
            return 0;
        }

        // 统计添加和删除的次数
        long addCount = patch.getDeltas().stream()
                .filter(delta -> delta.getType() == DeltaType.INSERT)
                .count();

        long deleteCount = patch.getDeltas().stream()
                .filter(delta -> delta.getType() == DeltaType.DELETE)
                .count();

        // 获取所有差异的数量
        int totalDeltas = patch.getDeltas().size();

        // 判断是否为纯添加或纯删除
        if (addCount > 0 && deleteCount == 0 && addCount == totalDeltas) {
            return 1; // 纯添加
        } else if (deleteCount > 0 && addCount == 0 && deleteCount == totalDeltas) {
            return 2; // 纯删除
        } else {
            return 3; // 包含修改或混合类型
        }
    }
}