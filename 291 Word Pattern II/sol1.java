import java.util.*;

class sol1 {
    /*
        Given a pattern and a string s, return true if s matches the pattern.
        A string s matches a pattern if there is some bijective mapping of single characters to strings such that 
        if each character in pattern is replaced by the string it maps to, then the resulting string is s. 
        A bijective mapping means that no two characters map to the same string, and no character maps to two different strings.
        
        E.g pattern = "abab", s = "redblueredblue" --> true

        Backtrack + HashSet + HashMap: Recursively find the potential substring for each element in pattern.
    */
    public static boolean wordPatternMatch(String pattern, String s) {
        Map<Character, String> map = new HashMap<>();
        Set<String> set = new HashSet<>();
        return wordPatternMatch(pattern, s, map, set, 0, s.length(), 0, pattern.length());
    }
    
    private static boolean wordPatternMatch(String p, String s, Map<Character, String> map, Set<String> set, int start, int end, int pStart, int pEnd) {
        if (start == end && pStart == pEnd) return true;
        if (pStart < pEnd && start >= end || pStart >= pEnd && start < end) return false;
        
        char curP = p.charAt(pStart);
        String matched = map.get(curP);
        if (matched != null) {
            int count = matched.length();
            return start + count <= end && matched.equals(s.substring(start, start + count)) && wordPatternMatch(p, s, map, set, start + count, end, pStart + 1, pEnd);
        } else {
            int endPoint = end;
            for (int i = pEnd - 1; i > pStart; i--) {
                endPoint -= !map.containsKey(p.charAt(i) - 'a') ? 1 : map.get(p.charAt(i) - 'a').length();
            }
            for (int i = start + 1; i <= endPoint; i++) {
                matched = s.substring(start, i);
                if (set.contains(matched)) continue;
                map.put(curP, matched);
                set.add(matched);
                if (wordPatternMatch(p, s, map, set, i, end, pStart + 1, pEnd)) return true;
                else {
                    map.remove(curP);
                    set.remove(matched);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String pattern = "abab", s = "redblueredblue";
        System.out.println(wordPatternMatch(pattern, s));
        assert wordPatternMatch(pattern, s);
    }
}