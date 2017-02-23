package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary
{

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    HashSet<String> wordSet;
    HashMap<Integer ,ArrayList<String>> sizeToWord;
    ArrayList<String> wordList;
    HashMap<String,ArrayList<String>> lettersToWord;
    String key;
    private int wordlength;

    public AnagramDictionary(Reader reader) throws IOException {

        BufferedReader in = new BufferedReader(reader);
        String line;
        wordSet = new HashSet<>();
        wordList = new ArrayList<>();
        lettersToWord = new HashMap<>();
        sizeToWord = new HashMap<>();
        wordlength = 3;

        while ((line = in.readLine()) != null) {
            String word = line.trim();


            wordList.add(word);
            wordSet.add(word);


            String Key = getSortedString(word);

            ArrayList<String> anagrams = lettersToWord.get(Key);
            if (anagrams == null) {
                anagrams = new ArrayList<String>();
                lettersToWord.put(Key, anagrams);
            }
            anagrams.add(word);


            ArrayList<String> sameSized = sizeToWord.get(word.length());
            if (sameSized == null) {
                sameSized = new ArrayList<String>();
                sizeToWord.put(word.length(), sameSized);

            }
            sameSized.add(word);

        }
    }
    public String getSortedString(String word)
    {

        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);

    }
    public boolean isGoodWord(String word, String base)
    {
        boolean check = true;
        if (wordSet.contains(word) && !word.contains(base))
            return true;
        else
            return false;
    }
    public List<String> getAnagrams(String targetWord)
    {
        ArrayList<String> result = new ArrayList<String>();
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word)
    {
        ArrayList<String> result = new ArrayList<String>();
        for(char aplha = 'a'; aplha <= 'z'; aplha++){
            String testString = word + aplha;
            String key = getSortedString(testString);
            if (lettersToWord.containsKey(key))
            {
                ArrayList<String> anagrams = lettersToWord.get(key);
                result.addAll(anagrams);
            }

        }
        ArrayList<String> solution = (ArrayList<String>) result.clone();
        for(String anagramword:solution)
        {
            if(!isGoodWord(anagramword, word))
                result.remove(anagramword);
        }
        return result;
    }

    public String pickGoodStarterWord()
    {


        String starterWord = null;

        ArrayList<String> curr = sizeToWord.get(wordlength);
        if (wordlength < MAX_WORD_LENGTH)
            wordlength++;
        int index = random.nextInt(curr.size());
        for (int i = index % curr.size(); ; i++)
        {
            if (getAnagramsWithOneMoreLetter(curr.get(i)).size() >= MIN_NUM_ANAGRAMS)
            {
                starterWord = curr.get(index);
                break;
            }

        }
        return starterWord;
    }
}
