package com.github.plugin.setoperation;

import org.springframework.util.FileCopyUtils;

import java.io.*;

/**
 * @author duhongming
 * @version 1.0
 * @description TODO
 * @date 2020/3/26 11:15
 */
public class ZsetCreateCommand {
    public static void main(String[] args) throws IOException {
        StringBuilder tagId1 = new StringBuilder(" zadd tag_id1 ");
        for (int i = 1; i <= 30_0000; i++) {
            tagId1.append(" 1 company_id" + i);
        }
        FileCopyUtils.copy(tagId1.toString(), new FileWriter(new File("tag_id1.txt")));
    }
}