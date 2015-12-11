package com.chanjet.ccs.ccp.base.util;

import java.util.ArrayList;
import java.util.List;

public class DFA {
    
    private String[] words;                     // 黑关键词数组
    private Node rootNode = new Node('R');      // DFA树

    public DFA(String[] words) {
        this.words = words;
    }

    public DFA(List<String> words) {
        Object[] temp = words.toArray();
        this.words = new String[temp.length];
        for(int i=0; i<temp.length; i++){
            this.words[i] = (String)temp[i];
        }
    }

    // 创建树
    public void createTree() {
        for (String str : words) {
            char[] chars = str.toCharArray();
            if (chars.length > 0)
                insertNode(rootNode, chars, 0);
        }
    }

    // 向树中插入节点
    private void insertNode(Node node, char[] cs, int index) {
        Node n = findNode(node, cs[index]);
        if (n == null) {
            n = new Node(cs[index]);
            node.nodes.add(n);
        }
        if (index == (cs.length - 1))
            n.flag = 1;
        index++;
        if (index < cs.length)
            insertNode(n, cs, index);
    }

    // 查询子节点
    private Node findNode(Node node, char c) {
        List<Node> nodes = node.nodes;
        Node rn = null;
        for (Node n : nodes) {
            if (n.c == c) {
                rn = n;
                break;
            }
        }
        return rn;
    }

    // 过滤内容，并返回过滤出的黑关键词
    public List<String> searchWord(String content) {
        List<String> foundWords = new ArrayList<String>();
        List<String> word = new ArrayList<String>();
        char[] chars = content.toCharArray();
        int a = 0;
        Node node = rootNode;
        while (a < chars.length) {
            node = findNode(node, chars[a]);
            if (node == null) {
                node = rootNode;
                a = a - word.size();
                word.clear();
            } else if (node.flag == 1) {
                word.add(String.valueOf(chars[a]));
                StringBuffer sb = new StringBuffer();
                for (String str : word) {
                    sb.append(str);
                }
                foundWords.add(sb.toString());
                a = a - word.size() + 1;
                word.clear();
                node = rootNode;
            } else {
                word.add(String.valueOf(chars[a]));
            }
            a++;
        }
        return foundWords;
    }
    
    // 测试
    public static void main(String[] args) {
        String[] arr = { "毒品", "宗教迫害", "宗教压迫", "法论功", "法轮功", "发轮大法"};
        DFA dfa = new DFA(arr);
        dfa.createTree();
        String content = "反对进行宗教迫害";
        System.out.println(dfa.searchWord(content));
    }
    
}

// 节点类
class Node {
    
    public char c;
    public int flag;    // 1：表示终结，0：延续
    public List<Node> nodes = new ArrayList<Node>();

    public Node(char c) {
        this.c = c;
        this.flag = 0;
    }

    public Node(char c, int flag) {
        this.c = c;
        this.flag = flag;
    }
}
