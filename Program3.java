import java.util.*;

public class Program3 {

    public int maxFoodCount (int[] plots) {

        //first in preprocessing do a O(n) to fill out amount from 0 to x
        int size = plots.length;
        int[][] optGraph = new int[size][size];
        int amounts[] = new int[size];

        amounts[0] = plots[0];
//        System.out.println(amounts[0]);
        for (int i = 1; i < plots.length; i++){
            amounts[i] += plots[i] + amounts[i-1];
//            System.out.println(amounts[i]);
        }
//        for (int i = 0; i < size; i++){
//            optGraph[i][i] = 0;
//        }

        for (int i = 0; i < size; i++){
            for (int j = 0; j+i < size; j++){
                optGraph[j][i+j] = OPTCalcBinary2(j, i+j, optGraph, plots, amounts);

            }
        }

//        for (int i = size-1; i >= 0; i--) {
//            for (int j = 0; j < size; j++) {
//                if (j > i){
//                    System.out.print("X ");
//                }else {
//                    System.out.print(optGraph[j][i] + " ");
//                }
//            }
//            System.out.println();
//        }
        // Implement your dynamic programming algorithm here
        // You may use helper functions as needed

        //max( min (OPT(start, mid) + sum from start to middle), OPT(mid+1, end) + sum from mid+1 to end))

        return optGraph[0][size-1];
    }

    public int OPTCalc(int start, int end, int[][] optGraph, int[] plots, int[] amounts){
        if (start == end){
            return 0;
        }
        else if (start == end-1) {
            return Math.min(plots[start], plots[end]);

        } else {
            int max = 0;
            int left = 0;
            int right = 0;
            for (int i = start; i < end; i++) {
                if (i == start) {
                    left = plots[i];
                    right = amounts[end] + optGraph[i + 1][end] - amounts[i];
                }
                else if (start == 0){
                    left = optGraph[start][i] + amounts[i];
                    right = optGraph[i+1][end] + amounts[end] - amounts[i];
                }
//                else if (i == 0){
//                    left = amounts[i];
//                    right = amounts[end] + optGraph[i+1][end] - amounts[i];
//                } else if (i == 1) {
//                    left = optGraph[start][i] + amounts[i] - amounts[i-1];
//                    right = optGraph[i+1][end] + amounts[end] - amounts[i];
//            }
                else {
                    left = optGraph[start][i] + amounts[i] - amounts[start-1];
                    right = optGraph[i+1][end] + amounts[end] - amounts[i];
                }

                int currCal = Math.min(left, right);
                if (currCal > max){
                    max = currCal;
                }
            }


            return max;

//            int left = optGraph[start][end/2] + amounts[end/2];
//            int right = optGraph[end/2 + 1][end] + amounts[end]-amounts[end/2];
//            return Math.min(left, right);

        }
    }

    public int OPTCalcBinary2(int start, int end, int[][] optGraph, int[] plots, int[] amounts){
        int foundPeakFlag = 0;
        int mid = 0;
        int max = 0;
        int calcStart = start;
        int calcEnd = end;
        int left = 0;
        int right = 0;
        if (start == end){
            return 0;
        }
        else if (start == end-1) {
            return Math.min(plots[start], plots[end]);
        }
        else {
            while(foundPeakFlag == 0){
                mid = (calcStart+calcEnd)/2;
                if (mid == start) {
                    left = plots[mid];
                    right = amounts[end] + optGraph[mid + 1][end] - amounts[mid];
                }
                else if (start == 0){
                    left = optGraph[start][mid] + amounts[mid];
                    right = optGraph[mid+1][end] + amounts[end] - amounts[mid];
                }
                else {
                    left = optGraph[start][mid] + amounts[mid] - amounts[start-1];
                    right = optGraph[mid+1][end] + amounts[end] - amounts[mid];
                }
                int tempMin = Math.min(left, right);
                if (start ==  mid){
                    if (tempMin < Math.min(optGraph[start][mid + 1] + amounts[mid + 1], optGraph[mid + 2][end] + amounts[end] - amounts[mid + 1])) {
                        calcStart = mid;
                    } else if (tempMin < Math.min(plots[start], optGraph[mid][end] + amounts[end] - amounts[start])) {
                    calcEnd = mid;
                    } else {
                    max = tempMin;
                    foundPeakFlag = 1;
                    }
                } else if (start == 0) {


                    if (tempMin < Math.min(optGraph[start][mid + 1] + amounts[mid + 1], optGraph[mid + 2][end] + amounts[end] - amounts[mid + 1])) {
                        calcStart = mid;
                    } else if (tempMin < Math.min(optGraph[start][mid - 1] + amounts[mid - 1], optGraph[mid][end] + amounts[end] - amounts[mid - 1])) {
                        calcEnd = mid;
                    } else {
                        max = tempMin;
                        foundPeakFlag = 1;
                    }
                } else if (mid == 1) {
                    if (tempMin < Math.min(plots[mid-1], amounts[end] + optGraph[mid][end] - amounts[mid-1] )){
                        calcEnd = mid;
                    } else {
                        max = tempMin;
                        foundPeakFlag = 1;
                    }
                }else if (mid == end - 1){
                    if (tempMin < Math.min(amounts[mid-1] - amounts[mid-2] + optGraph[start][mid-1], amounts[end]-amounts[mid-1])){
                        calcEnd = mid;
                    }else {
                        max = tempMin;
                        foundPeakFlag = 1;
                    }
                } else if (tempMin < Math.min(optGraph[start][mid+1] + amounts[mid+1] - amounts[start-1], optGraph[mid+2][end] + amounts[end] - amounts[mid+1])) {
                    calcStart = mid;
                } else if (tempMin < Math.min(optGraph[start][mid-1] + amounts[mid-1] - amounts[start-1], optGraph[mid][end] + amounts[end] - amounts[mid-1])){
                    calcEnd = mid;
                } else {
                    max = tempMin;
                    foundPeakFlag = 1;
                }
            }
        }
        return max;
    }



    public int OPTCalcBinary(int start, int end, int[][] optGraph, int[] plots, int[] amounts){
        if (start == end){
            return 0;
        }
        else if (start == end-1) {
            return Math.min(plots[start], plots[end]);

        } else {
            int max = 0;
            int maxLeft = 0;
            int maxRight = 0;
            int calcStart = start;
            int calcEnd = end;
            int left = 0;
            int right = 0;
            int mid = 0;
            int delta = Integer.MAX_VALUE ; //need to change this to max java value
            int prevMid = -1;
            int farthestRight = end+1;
            int farthestLeft = -1;
            int tempDelta = 0;
            int foundMidFlag = 0;
            while (foundMidFlag == 0){
                mid = (calcStart+calcEnd)/2;
                if (mid == start) {
                    left = plots[mid];
                    right = amounts[end] + optGraph[mid + 1][end] - amounts[mid];
                }
                else if (start == 0){
                    left = optGraph[start][mid] + amounts[mid];
                    right = optGraph[mid+1][end] + amounts[end] - amounts[mid];
                }
                else {
                    left = optGraph[start][mid] + amounts[mid] - amounts[start-1];
                    right = optGraph[mid+1][end] + amounts[end] - amounts[mid];
                }
                tempDelta = Math.abs(right - left);

                //if (((mid+1 == prevMid) || (mid-1 == prevMid) || (mid == prevMid))) {
                if (((mid+1 == farthestRight) && (mid-1 == farthestLeft)) || (mid == prevMid)){
                    if (tempDelta <= delta && (Math.min(maxLeft, maxRight) < Math.min(left, right))) {
                        foundMidFlag = 1;
                        max = Math.min(left, right);
                    } else {
                        foundMidFlag = 1;
                        max = Math.min(maxLeft, maxRight);
                    }

                }
                else if (left > right){
                    farthestRight = mid;
                    if (tempDelta < delta){
                        delta = tempDelta;
                        if (Math.min(maxLeft, maxRight) < Math.min(left, right)){
                            maxLeft = left;
                            maxRight = right;
                        }
                    }
                    calcEnd = mid;
                    prevMid = mid;
                }
                else {
                    farthestLeft = mid;
                    if (tempDelta < delta){
                        delta = tempDelta;
                        if (Math.min(maxLeft, maxRight) < Math.min(left, right)){
                            maxLeft = left;
                            maxRight = right;
                        }

                    }
                    calcStart = mid;
                    prevMid = mid;
                }


            }
//
            return max;

        }




//            int left = optGraph[start][end/2] + amounts[end/2];
//            int right = optGraph[end/2 + 1][end] + amounts[end]-amounts[end/2];
//            return Math.min(left, right);

    }
}

