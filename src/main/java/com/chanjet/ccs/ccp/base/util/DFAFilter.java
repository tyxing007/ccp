package com.chanjet.ccs.ccp.base.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chanjet.ccs.ccp.base.basic.EnumValues;
import com.chanjet.ccs.ccp.base.entity.BlackKey;

public class DFAFilter {
    
    private static Map<Integer, Map<Integer, DFA>> dfaMap;
    private static int startNum = 1;
    
    static {
        dfaMap = new HashMap<Integer, Map<Integer, DFA>>();
        dfaMap.put(EnumValues.LEVEL_FIRST, new HashMap<Integer, DFA>());
        dfaMap.put(EnumValues.LEVEL_SECOND, new HashMap<Integer, DFA>());
        dfaMap.put(EnumValues.LEVEL_THIRD, new HashMap<Integer, DFA>());
        dfaMap.put(EnumValues.LEVEL_FOUTH, new HashMap<Integer, DFA>());
    }
    
    private DFAFilter() { }
    
    public static DFAFilter instance = new DFAFilter();
    
    public static synchronized void initDFAFilter(List<BlackKey> blackKeys) {
        List<String> first = new ArrayList<String>();
        List<String> second = new ArrayList<String>();
        List<String> third = new ArrayList<String>();
        List<String> fouth = new ArrayList<String>();
        
        for(BlackKey k : blackKeys) {
            switch (k.getLevel()) {
                case EnumValues.LEVEL_FIRST :
                    first.add(k.getBlackKey());
                    break;
                case EnumValues.LEVEL_SECOND :
                    second.add(k.getBlackKey());
                    break;
                case EnumValues.LEVEL_THIRD :
                    third.add(k.getBlackKey());
                    break;
                case EnumValues.LEVEL_FOUTH :
                    fouth.add(k.getBlackKey());
                    break;
            
            }
        }
        
        DFA dfa1 = createDFA(first);
        DFA dfa2 = createDFA(second);
        DFA dfa3 = createDFA(third);
        DFA dfa4 = createDFA(fouth);
        
        dfaMap.get(EnumValues.LEVEL_FIRST).put(startNum, dfa1);
        dfaMap.get(EnumValues.LEVEL_SECOND).put(startNum, dfa2);
        dfaMap.get(EnumValues.LEVEL_THIRD).put(startNum, dfa3);
        dfaMap.get(EnumValues.LEVEL_FOUTH).put(startNum, dfa4);
        
    }
    
    
    private static DFA createDFA(List<String> words) {
        DFA dfa = new DFA(words);
        dfa.createTree();
        return dfa;
    }
    
    
    public static List<String> filterBlackKey(Integer level, String content) {
        List<String> foundWords = new ArrayList<String>();
        Map<Integer, DFA> dfas = dfaMap.get(level);
        if(dfas == null){
          return foundWords;
        }
        Integer key= Collections.max(dfas.keySet());
        if (dfas.get(key) != null)
            return dfas.get(key).searchWord(content);
        return foundWords;
    }
    
    //TODO xx
    public static void changeTree(Integer level, String[] newWords) {
        
    }
    
    
    public static void main(String[] args) {
        BlackKey b1 = new BlackKey("黑幕", 1);
        BlackKey b2 = new BlackKey("黑怪", 1);
        BlackKey b3 = new BlackKey("黑屏", 1);
        BlackKey b4 = new BlackKey("黑哈哈", 1);
        BlackKey b5 = new BlackKey("黑一黑", 1);
        
        List<BlackKey> b = new ArrayList<BlackKey>();
        b.add(b1);
        b.add(b2);
        b.add(b3);
        b.add(b4);
        b.add(b5);
        
        DFAFilter.initDFAFilter(b);
        System.out.println(DFAFilter.filterBlackKey(1, "哈哈 黑黑 黑怪哈"));
        
        Map<Integer, String> a = new HashMap<Integer, String>();
        a.put(1, "a");
        a.put(Collections.max(a.keySet()) + 1, "b");
        a.put(Collections.max(a.keySet()) + 1, "c");
        System.out.println(a);
        System.out.println(Collections.max(a.keySet()));
        System.out.println(a.get(Collections.max(a.keySet())));
    }
}
