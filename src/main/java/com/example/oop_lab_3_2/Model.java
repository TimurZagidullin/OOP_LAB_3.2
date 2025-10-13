package com.example.oop_lab_3_2;

import java.io.*;
public class Model {

   private int a, b, c;

   public Model(){
       loadNumbers();
       if(a == 0 && b == 0 && c == 0){ a = 0; b = 50; c = 100; }
   }

    public int getA(){ return a; }
    public int getB(){ return b; }
    public int getC(){ return c; }

    public void setA(int a){
       if(0 <= a && a <= 100) {
           this.a = a;
           changeBC_A();
       }
    }
    public void setB(int b) {
        if (b < a) b = a;
        if (b > c) b = c;
        this.b = b;
    }
    public void setC(int c) {
        if (c >= 0 && c <= 100) {
            this.c = c;
            changeAB_C();
        }
    }

    private void changeBC_A(){
        if (a > b) b = a;
        if (b > c) c = b;
    }
    private void changeAB_C(){
        if (c < b) b = c;
        if (b < a) a = b;
    }

    public void saveNumbers(){
       try(BufferedWriter writeNums = new BufferedWriter(new FileWriter("savedNumbers.txt"))){
           writeNums.write(a + " " + b + " " + c);
       } catch (IOException e){
           throw new RuntimeException(e);
       }
    }

    public void loadNumbers(){
       File file =  new File("savedNumbers.txt");
       if(!file.exists()) return;
       try(BufferedReader readNums = new BufferedReader(new FileReader(file))){
           String[] values = readNums.readLine().split(" ");
           a = Integer.parseInt(values[0]);
           b = Integer.parseInt(values[1]);
           c = Integer.parseInt(values[2]);
       } catch (IOException e){
           throw new RuntimeException(e);
       }
    }
}
