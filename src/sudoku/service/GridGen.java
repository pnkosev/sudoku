package sudoku.service;

import java.util.*;
import java.util.stream.Collectors;

public class GridGen {

    private Integer[][] result = new Integer[9][9];

    private ArrayList<Integer> getLineNumbers(int x) {
        return (ArrayList<Integer>) Arrays.stream(result[x])
                .filter(v -> v != 0)
                .collect(Collectors.toList());
    }

    private ArrayList<Integer> getColumnNumbers(int y) {
        ArrayList<Integer> column = new ArrayList<>();
        for (Integer[] row : result) {
            if (row[y] != 0)
                column.add(row[y]);
        }

        return column;
    }

    private ArrayList<Integer> getBlocNumbers(int x, int y) {
        ArrayList<Integer> out = new ArrayList<>();
        Point point =  new Point(x, y);

        for (int xx = point.getMinX(); xx < point.getMaxX(); xx++) {
            for (int yy = point.getMinY(); yy < point.getMaxY(); yy++) {
                if (result[xx][yy] != 0) {
                    out.add(result[xx][yy]);
                }
            }
        }
        return out;
    }

    /**
     * Bloc id starts with 0 ans from left to right
     *
     * @param x
     * @param y
     * @param numbers
     */
    private void fillBloc( int x, int y,  ArrayList<Integer> numbers) {

        Point point =  new Point(x, y);
        int counter = 0;
        for (int xx = point.getMinX(); xx < point.getMaxX(); xx++) {
            for (int yy = point.getMinY(); yy < point.getMaxY(); yy++) {
                result[xx][yy] = numbers.get(counter);
                ++counter;
            }
        }
    }

    /**
     * Fill the grid
     */
    public boolean generate() {
        // fill in with 0 value
        for (int x = 0; x < 9; x++) {
            Arrays.fill(result[x], 0);
        }
        ArrayList<Integer> defaultChoice = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        // shuffle values and fill in the 1st row
        Collections.shuffle(defaultChoice);
        result[0] = defaultChoice.toArray(new Integer[0]);
        int counter = 0;
        for (int x = 1; x < 9; x++) {
            for (int y = 0; y < 9; y++) {

                Set<Integer> lineProbability = new HashSet<>();
                lineProbability.addAll(getLineNumbers(x));
                lineProbability.addAll(getColumnNumbers(y));
                lineProbability.addAll(getBlocNumbers(x, y));

                if (lineProbability.size() == 9) {
                    if(++counter > 100){
                        return false;
                    }
                    // we can't place a number
                    // retry the line
                    y = -1;
                    Arrays.fill(result[x], 0);
                } else {
                    // candidates for the cell by excluding already present numbers
                    List<Integer> choice = defaultChoice.stream()
                            .filter(i -> !lineProbability.contains(i))
                            .collect(Collectors.toList());
                    Collections.shuffle(choice);
                    result[x][y] = choice.get(0);
                }
            }
        }

        return true;
    }

    private void printGrid() {
        Arrays.stream(result).forEach(row -> {
            Arrays.stream(row).forEach(v -> System.out.print(v + " "));
            System.out.println(" ");
        });
    }

    public static void main(String[] args) {

        GridGen instance = new GridGen();
        int counter = 0;
        long startTime = System.currentTimeMillis();
        while(!instance.generate()){
            if(++counter > 1000){
                break;
            }
        }
        System.out.println(System.currentTimeMillis() - startTime);
        System.out.println("Counter:" + counter);
        instance.printGrid();
    }

    class Point {

        private int minX;
        private int maxX;
        private int minY;
        private int maxY;

        public Point(int x, int y) {
            init(x, y);
        }

        public void init(int x, int y) {
            minX = x < 3 ? 0 : (x < 6 ? 3 : 6);
            maxX = x < 3 ? 3 : (x < 6 ? 6 : 9);
            minY = y < 3 ? 0 : (y < 6 ? 3 : 6);
            maxY = y < 3 ? 3 : (y < 6 ? 6 : 9);
        }

        public int getMinX() {
            return minX;
        }

        public int getMaxX() {
            return maxX;
        }

        public int getMinY() {
            return minY;
        }

        public int getMaxY() {
            return maxY;
        }

    }
}
