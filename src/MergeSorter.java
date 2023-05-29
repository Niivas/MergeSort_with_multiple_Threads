import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MergeSorter implements Callable<List<Integer>>{

    List<Integer>numbersToSort;
    public MergeSorter(List<Integer>arr){
        this.numbersToSort = arr;
    }

    public List<Integer>call() throws ExecutionException, InterruptedException {
        if (numbersToSort.size() <= 1){
            return numbersToSort;
        }
        int mid = numbersToSort.size()/2;
        List<Integer>leftArr = new ArrayList<>();
        List<Integer>rightArr = new ArrayList<>();

        for(int i = 0; i<=mid;i++){
            leftArr.add(numbersToSort.get(i));
        }
        for(int i = mid+1; i<numbersToSort.size();i++){
            rightArr.add(numbersToSort.get(i));
        }

        MergeSorter leftSortedArr = new MergeSorter(leftArr);
        MergeSorter rightSortedArr = new MergeSorter(rightArr);

        Future<List<Integer>> futureLeftArr = service.submit(leftSortedArr);
        Future<List<Integer>> futureRightArr = service.submit(rightSortedArr);

        leftArr = futureLeftArr.get();
        rightArr = futureRightArr.get();

        ArrayList<Integer>merged = new ArrayList<>();
        int i=0,j=0;

        while (i<leftArr.size() && j<rightArr.size()){
            if (leftArr.get(i)<=rightArr.get(j)){
                merged.add(leftArr.get(i));
                i++;
            }
            else{
                merged.add(rightArr.get(j));
                    j++;
            }
        }

        while (i<leftArr.size()){
            merged.add(leftArr.get(i));
            i++;
        }
        while (j<rightArr.size()){
            merged.add(rightArr.get(i));
            j++;
        }
        return merged;

    }
}
