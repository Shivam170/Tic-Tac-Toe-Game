package sample;


import java.util.ArrayList;
import java.util.Random;

public class TicTacToe {

    //Declaring the global variable for the list of human, computer and the vector
    public ArrayList<Integer> vectors, humanList, computerList;

    //Declaring the global variable for the 2-d array of magic square
    private int[][] magicSquare;

    //Declaring the global variable for the list of tagged value in the magic square
    ArrayList<Integer> tagList =new ArrayList<Integer>();

    //Declaring the global variable for the list of winningMoves
    ArrayList<Integer> winningMoves =new ArrayList<Integer>();

    //Declaring the variable for the possible winning move
    public int target;


    //Constructor for the class initializing the list variables
    public TicTacToe(){

        vectors=new ArrayList<>();
        humanList =new ArrayList<>();
        computerList =new ArrayList<>();
        magicSquare=generateMagicSquare();

        for(int i=1;i<=9;i++){
            vectors.add(i);
        }

    }


    //Method to pick the element by user and to print the list content of both
    public boolean humanPicks(int element){

        if(vectors.contains(element)){
            //Removing the chosen element from the vector and adding it in the human list
        vectors.remove(vectors.indexOf(element));
        humanList.add(element);

            //Printing the list content of both
            System.out.println("===========================");
            System.out.println("Human List Contents are : ");
            System.out.println(humanList);
            System.out.println("===========================");
            System.out.println("Computer List Contents are : ");
            System.out.println(computerList);
            System.out.println("===========================");
            System.out.println();
            return true;
        }else return false;

    }

    //Method to pick the element by pc and to print the list content of both
    public int computerPicks(int element){

        //Removing the chosen element from the vector and adding it in the human list
        vectors.remove(vectors.indexOf(element));
        computerList.add(element);

        //Printing the list content of both
        System.out.println("===========================");
        System.out.println("Human List Contents are : ");
        System.out.println(humanList);
        System.out.println("===========================");
        System.out.println("Computer List Contents are : ");
        System.out.println(computerList);
        System.out.println("===========================");
        System.out.println();
        return element;
    }

    //Method to generate the magic square
    private int[][] generateMagicSquare(){

        int n=3;
        int[][] magicSquare = new int[n][n];


        // Initialize position for 1
        int i = n/2;
        int j = n-1;

        // putting all values in magic square one by one
        for (int num=1; num <= n*n; )
        {
            if (i==-1 && j==n)
            {
                j = n-2;
                i = 0;
            }
            else
            {
                //1st condition helper if next number
                // goes to out of square's right side
                if (j == n)
                    j = 0;

                //1st condition helper if next number is
                // goes to out of square's upper side
                if (i < 0)
                    i=n-1;
            }

            //2nd condition
            if (magicSquare[i][j] != 0)
            {
                j -= 2;
                i++;
                continue;
            }
            else {
                //set number
                magicSquare[i][j] = num++;

                //Tagging number in the corresponding cell
                if(i==0){
                    tagList.add(i+j);
                }else if(i==1){
                    tagList.add((2*i)+j+1);
                }else{
                    tagList.add((3*i)+j);
                }
            }


            //1st condition
            j++;  i--;
        }

        // printing magic square in console
        System.out.println("The Magic Square for "+n+":");
        for(i=0; i<n; i++)        {
            for(j=0; j<n; j++)
                System.out.print(magicSquare[i][j]+" ");
            System.out.println();
        }
        return magicSquare;
    }

    //Method to check if computer has won
    boolean comHasWon(){
        if(hasWon(computerList))
            return true;
        else
            return false;
    }

    //Method to check if user has won
    boolean humHasWon(){
        if(hasWon(humanList))
            return true;
        else
            return false;
    }

    //Method to check if anyone has won
    boolean hasWon(ArrayList<Integer> ls){

        for(int i=0;i<ls.size();i++){
            for(int j=i+1;j<ls.size();j++){
                for (int k=j+1;k<ls.size();k++){
                    //Checking whether the list content has the sum of 15
                    if(ls.get(i)+ls.get(j)+ls.get(k)==15){
                        winningMoves.clear();
                        winningMoves.add(ls.get(i));
                        winningMoves.add(ls.get(j));
                        winningMoves.add(ls.get(k));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //Method to check if computer is winning in the next move
    boolean isComputerWinning(){
        if(isWinning(computerList))
            return true;
        else return false;
    }

    //Method to check if human is winning in the next move
    boolean isHumanWinning(){
        if(isWinning(humanList))
            return true;
        else return false;
    }

    //Method to check if anyone is winning in the next move
    boolean isWinning(ArrayList<Integer> rList){

        for(int i=0;i<rList.size();i++){

            for (int j=i+1;j<rList.size();j++)
            {
                int num = 15-rList.get(i)-rList.get(j);
                if((num>0 && num<10) && vectors.contains(num))
                {
                    target=num;
                    return true;
                }
            }
        }
        return false;
    }

    //Method to pick element 5
    boolean goFive(){
        if(vectors.contains(5)){
            computerPicks(5);
            return true;
        }else return false;
    }

    //Method to pick random element
    int goRandom(){
        Random ran = new Random();
        int rand = ran.nextInt(vectors.size());
        int n=vectors.get(rand);
        computerPicks(vectors.get(rand));
        return n;
    }

    //Method to decide where to draw the line
    public boolean lineDetection (int a, int b){
        if(winningMoves.contains(a) && winningMoves.contains(b))
            return true;
        else return false;
    }

}
