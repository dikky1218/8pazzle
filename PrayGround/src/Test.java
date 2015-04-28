/**
 * Created by daiki on 15/04/28.
 */
public class Test {
    public static void main(String[] args){
        new Test();
    }

    Test(){
        System.out.println("array copy test");
        int[] a = {0,1,2,3,4,5,6,7,8};
        int[] b = new int[9];


        System.out.println("clone() method.");
        b=a.clone();
        displayResult(a, b);
        clearArray(b);
        System.out.println("clear");
        displayResult(a, b);

        System.out.println("copy in original method");
        copyArray(a, b);
        displayResult(a, b);
    }
    private void copyArray(int[] source, int[] destination){
        //destination = source.clone();
        for(int i=0; i<9; i++){
            destination[i] = source[i];
        }
    }

    private void displayResult(int a[], int b[]){
        System.out.print("a : ");
        printArray(a);
        System.out.print("b : ");
        printArray(b);
    }

    private void clearArray(int[] tmp){
        for(int i=0; i<9; i++){
            tmp[i] = 0;
        }
    }

    private void printArray(int[] tmp){
        System.out.print("{");
        for(int i=0; i<9; i++){
            System.out.print(tmp[i] + ", ");
        }
        System.out.println("}");
    }
}
