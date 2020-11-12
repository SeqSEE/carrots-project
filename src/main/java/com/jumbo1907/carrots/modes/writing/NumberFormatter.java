package com.jumbo1907.carrots.modes.writing;

import java.util.HashMap;

public class NumberFormatter {

    private final HashMap<Character, int[][]> dictionary;

    public NumberFormatter() {
        //Load every number & letter.
        dictionary = new HashMap<>();
        loadLibrary();
    }

    public int[][] translate(char i) {
        return dictionary.get(dictionary.containsKey(i) ? i : 'x');
    }

    public int[][][] translate(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("Input string is null or empty");
        }
        s = s.toLowerCase();

        final int[][][] matrix = new int[s.length()*2][][];
        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            matrix[i*2] = translate(c);
            matrix[i*2+1] = translate('|');
        }

        return matrix;
    }

    private void loadLibrary() {
        dictionary.put('?', new int[][]{
                {0,1,1,1,0},
                {1,0,0,0,1},
                {0,0,1,1,0},
                {0,0,0,0,0},
                {0,0,1,0,0},
        });
        dictionary.put('!', new int[][]{
                {1},
                {1},
                {1},
                {0},
                {1},
        });
        dictionary.put('|', new int[][]{
                {0},
                {0},
                {0},
                {0},
                {0},
        });
        dictionary.put('a', new int[][]{
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
        });
        dictionary.put('b', new int[][]{
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 0},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1},
        });
        dictionary.put('c', new int[][]{
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 1, 1, 1, 1},
        });
        dictionary.put('d', new int[][]{
                {1, 1, 1, 1, 0},
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1},
        });
        dictionary.put('e', new int[][]{
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0},
                {1, 1, 1, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 1, 1, 1, 1},
        });
        dictionary.put('f', new int[][]{
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0},
                {1, 1, 1, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
        });
        dictionary.put('g', new int[][]{
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0},
                {1, 0, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1},
        });
        dictionary.put('h', new int[][]{
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
        });
        dictionary.put('i', new int[][]{
                {1, 1, 1, 1, 1},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0},
                {1, 1, 1, 1, 1},
        });
        dictionary.put('j', new int[][]{
                {1, 1, 1, 1, 1},
                {0, 0, 0, 0, 1},
                {0, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1},
        });
        dictionary.put('k', new int[][]{
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 0},
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
        });
        dictionary.put('l', new int[][]{
                {1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0},
                {1, 1, 1, 1, 1},
        });
        dictionary.put('m', new int[][]{
                {1, 1, 0, 1, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
        });
        dictionary.put('n', new int[][]{
                {1, 1, 0, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 0, 1, 1},
        });
        dictionary.put('o', new int[][]{
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1},
        });
        dictionary.put('p', new int[][]{
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1},
                {1, 0, 0},
                {1, 0, 0},
        });
        dictionary.put('q', new int[][]{
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 1, 1, 1, 1},
        });
        dictionary.put('r', new int[][]{
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1},
                {1, 0, 1, 0, 0},
                {1, 0, 0, 1, 1},
        });
        dictionary.put('s', new int[][]{
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0},
                {1, 1, 1, 1, 1},
                {0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1},
        });
        dictionary.put('t', new int[][]{
                {1, 1, 1, 1, 1},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0},
        });
        dictionary.put('u', new int[][]{
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1},
        });
        dictionary.put('v', new int[][]{
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
                {0, 1, 0, 0, 1},
                {0, 0, 1, 0, 1},
                {0, 0, 0, 1, 1},
        });
        dictionary.put('w', new int[][]{
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 1, 0, 1, 1},
        });
        dictionary.put('x', new int[][]{
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
                {0, 1, 1, 1, 0},
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
        });
        dictionary.put('y', new int[][]{
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
                {0, 1, 1, 1, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0},
        });
        dictionary.put('z', new int[][]{
                {1, 1, 1, 1, 1},
                {0, 0, 0, 0, 1},
                {0, 1, 1, 1, 0},
                {1, 0, 0, 0, 0},
                {1, 1, 1, 1, 1},
        });
        dictionary.put(' ', new int[][]{
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0},
        });
        dictionary.put('0', new int[][]{
                {1, 1, 1},
                {1, 0, 1},
                {1, 0, 1},
                {1, 0, 1},
                {1, 1, 1},
        });
        dictionary.put('1', new int[][]{
                {0, 1, 0},
                {1, 1, 0},
                {0, 1, 0},
                {0, 1, 0},
                {1, 1, 1},
        });
        dictionary.put('2', new int[][]{
                {1, 1, 1},
                {0, 0, 1},
                {1, 1, 1},
                {1, 0, 0},
                {1, 1, 1},
        });
        dictionary.put('3', new int[][]{
                {1, 1, 1},
                {0, 0, 1},
                {1, 1, 1},
                {0, 0, 1},
                {1, 1, 1},
        });
        dictionary.put('4', new int[][]{
                {1, 0, 1},
                {1, 0, 1},
                {1, 1, 1},
                {0, 0, 1},
                {0, 0, 1},
        });
        dictionary.put('5', new int[][]{
                {1, 1, 1},
                {1, 0, 0},
                {1, 1, 1},
                {0, 0, 1},
                {1, 1, 1},
        });
        dictionary.put('6', new int[][]{
                {1, 1, 1},
                {1, 0, 0},
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1},
        });
        dictionary.put('7', new int[][]{
                {1, 1, 1},
                {0, 0, 1},
                {0, 0, 1},
                {0, 0, 1},
                {0, 0, 1},

        });
        dictionary.put('8', new int[][]{
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1},
        });
        dictionary.put('9', new int[][]{
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1},
                {0, 0, 1},
                {1, 1, 1},
        });
        dictionary.put(',', new int[][]{
                {0},
                {0},
                {0},
                {0},
                {1},
        });
        dictionary.put('.', dictionary.get(','));
        dictionary.put(':', new int[][]{
                {0},
                {1},
                {0},
                {1},
                {0},
        });
        dictionary.put('x', new int[][]{
                {1, 0, 0, 0, 1},
                {0, 1, 0, 1, 0},
                {0, 0, 1, 0, 0},
                {0, 1, 0, 1, 0},
                {1, 0, 0, 0, 1},
        });
    }

}
