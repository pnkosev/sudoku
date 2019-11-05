package sudoku.service;

import java.util.*;

public class GridGen {

    private int[][] result = new int[9][9];

    public GridGen() {
        for (int x = 0; x < 9; x++) {
            Arrays.fill(result[x], 0);
        }
    }

    public int[][] getResult() {
        return result;
    }

    private ArrayList<Integer> getNumbers(int[] numbers) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] != 0) {
                result.add(numbers[i]);
            }
        }

        return result;

    }

    private ArrayList<Integer> getLineNumbers(int x) {
        return getNumbers(result[x]);
    }

    private ArrayList<Integer> getColumnNumbers(int y) {
        return getNumbers(result[y]);
    }

    private ArrayList<Integer> getBlocNumbers(int x, int y) {
        ArrayList<Integer> out = new ArrayList<>();
        int minX = x < 3 ? 0 : (x < 6 ? 3 : 6);
        int maxX = x < 3 ? 3 : (x < 6 ? 6 : 9);
        int minY = y < 3 ? 0 : (y < 6 ? 3 : 6);
        int maxY = y < 3 ? 3 : (y < 6 ? 6 : 9);


        for (int xx = minX; xx < maxX; xx++) {
            for (int yy = minY; yy < maxY; yy++) {
                if(result[xx][yy] != 0) {
                    out.add(result[xx][yy]);
                }
            }
        }
        return out;
    }

    public int[][] generate() {

        ArrayList<Integer> defaultChoice = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        Set<Integer> hash;
        Random rand = new Random();

//        HashSet<Integer> choice =  new HashSet<>();

        for (int x = 0; x < 9; x++) {
            result[0][x] = x+1;
        }
        for (int x = 1; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                System.out.println("getLineNumbers: " + getLineNumbers(x));
                System.out.println("getColumnNumbers" + getColumnNumbers(y));
                System.out.println("getBlocNumbers: " + getBlocNumbers(x,y));

                hash = new HashSet<Integer>();
                hash.addAll(getLineNumbers(x));
                hash.addAll(getColumnNumbers(y));
                hash.addAll(getBlocNumbers(x,y));
                Integer[] choice = hash.toArray(new Integer[0]);
                System.out.println(Arrays.toString(choice));
                System.exit(0);
//                choice = defaultChoice.stream()
//                        .filter( n -> !hash.contains(n))
//                        .collect(Collectors.toSet());

//                int index = rand.nextInt(choice.size()-1);
//                result[x][y] = (int)choice.toArray()[index];
            }
//            System.out.println(hash.toString());
//            System.exit(0);
        }

        return result;
    }

    public static void main(String[] args) {

        GridGen instance = new GridGen();
        instance.generate();
//        System.out.println(Arrays.deepToString(instance.getResult()));
    }
}
