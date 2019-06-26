package graph;

import java.util.*;

public class SnakesLadders {
    // Complete the quickestWayUp function below.
    static int quickestWayUp(int[][] ladders, int[][] snakes) {
        int[] board = new int[101];
        for(int i=0; i < board.length; i++) {
            board[i] = i;
        }

        for(int i = 0; i < ladders.length; i++) {
            board[ladders[i][0]] = ladders[i][1];
        }
        for(int i = 0; i < snakes.length; i++) {
            board[snakes[i][0]] = snakes[i][1];
        }

        for(int i = 0; i < board.length; i++) {
            System.out.print(board[i] + " ");
        }

        boolean[] visited = new boolean[101];
        LinkedList<int[]> q = new LinkedList<int[]>();

        int roll = 0;
        int val = 0;
        int[] unit = {1, 0};

        q.add(unit);
        visited[1] = true;

        while (!q.isEmpty()) {
            unit = q.poll();
            roll = unit[1];
            val = unit[0];

            System.out.println("\tVal: " + val + " Roll:" + roll);

            for(int i=val+6; i > val; i--) {
                if(i > 100) {
                    continue;
                }
                System.out.print(board[i] + " ");
                if(board[i] == 100) {
                    return roll + 1;
                } else if(!visited[board[i]]) {
                    unit[0] = board[i];
                    unit[1] = roll + 1;
                    q.add(unit);
                    visited[val] = true;
                }
            }
            System.out.println("");
        }
        return -1;
    }

    public static void main(String[] args){
        int[][] snakes = {{32, 62},
                          {42, 68},
                          {12, 98}};

        int[][] ladders = {{95, 13},
                           {97, 25},
                           {93, 37},
                           {79, 27},
                           {75, 19},
                           {49, 47},
                           {67, 17}};

        System.out.println(quickestWayUp(snakes, ladders));
    }
}
