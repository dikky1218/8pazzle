/**
 * Created by daiki on 2015/04/28.
 */
public class HashTest {
    public static void main(String[] args){
        new HashTest();
    }

    int[] keyArray = new int[9];

    HashTest(){




    }

    private int getHash(int[] r_n){
        int n = r_n.length;
        int top;
        int order;
        int hash=0;

        for(int k=0; k<n; k++){
            top = r_n[k];
            order=0;
            for(int j=k+1; j<n; j++){
                if(top > r_n[j])
                    order++;
            }
            hash += order * factrial(n-k-1);
        }

        return hash;
    }

    private int factrial(int n){
        if(n<=1)return 1;
        return n * factrial(n-1);
    }

}
