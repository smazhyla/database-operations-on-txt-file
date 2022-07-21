package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Readtheinput {
    private File file = new File("input.txt");
    private ArrayList<String> tableBid  = new ArrayList<String>();
    private ArrayList<String> tableAsk  = new ArrayList<String>();
    private ArrayList<Double> bestCount = new ArrayList<Double>();

    private String buffer=null;
    private String lastBestAsk="";
    private String lastBestBid="";
    private FileWriter myWriter = null;

    public ArrayList<String> getTableBid() {
        return tableBid;
    }

    public ArrayList<String> getTableAsk() {
        return tableAsk;
    }

    public void readFile() throws Exception {
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            buffer = sc.nextLine();
            if (buffer.startsWith("u") && buffer.endsWith("bid")) {
                tableBid.add(regex());

            } else if (buffer.startsWith("u") && buffer.endsWith("ask")) {
                tableAsk.add(regex());

            }
            if(buffer.startsWith("q")){
                if(buffer.contains("best_bid")){
                    bestBid();

                }
                if(buffer.contains("best_ask")){
                    bestAsk();

                }
                if(buffer.contains("size")){
                    size(Integer.parseInt(regex()));

                }

            }
            if (buffer.startsWith("o")){
                if (buffer.contains("buy")){
                    buy(Integer.parseInt(regex()));
                }
                if (buffer.contains("sell")){
                    sell(Integer.parseInt(regex()));
                }
            }
        }
    }
    public void bestBid(){
        bestCount.clear();
         for (int i = 0; i < tableBid.size(); i++) {
             String secondNumber="";
            String firstnumber="";

            for (int j = 0; j < tableBid.get(i).length(); j=j+1) {
                if (isNumeric(String.valueOf(tableBid.get(i).charAt(j)))){
                    secondNumber=secondNumber+ String.valueOf(tableBid.get(i).charAt(j));
                }else {
                    firstnumber=secondNumber;
                    secondNumber="";
                }

            }
            double sum=Integer.parseInt(firstnumber)+Integer.parseInt(secondNumber);
            sum=sum/2;
            bestCount.add(sum);
         }
        System.out.println(tableBid.get(bestCount.indexOf(Collections.max(bestCount))));
         lastBestBid=tableBid.get(bestCount.indexOf(Collections.max(bestCount)));

    }
    public void bestAsk(){
        bestCount.clear();
        for (int i = 0; i < tableAsk.size(); i++) {
            String secondNumber="";
            String firstnumber="";

            for (int j = 0; j < tableAsk.get(i).length(); j=j+1) {
                if (isNumeric(String.valueOf(tableAsk.get(i).charAt(j)))){
                    secondNumber=secondNumber+ String.valueOf(tableAsk.get(i).charAt(j));
                }else {
                    firstnumber=secondNumber;
                    secondNumber="";
                }

            }
            double sum=Integer.parseInt(firstnumber)+Integer.parseInt(secondNumber);
            sum=sum/2;
            bestCount.add(sum);
        }
        System.out.println(tableAsk.get(bestCount.indexOf(Collections.min(bestCount))));
        lastBestAsk=tableAsk.get(bestCount.indexOf(Collections.min(bestCount)));

    }
    public void size(int price){
        bestCount.clear();
        String secondNumber="";
        String firstnumber="";
        for (int i = 0; i < tableBid.size(); i++) {

            for (int j = 0; j < tableBid.get(i).length(); j=j+1) {
                if (isNumeric(String.valueOf(tableBid.get(i).charAt(j)))){
                    secondNumber=secondNumber+ String.valueOf(tableBid.get(i).charAt(j));
                }else {
                    firstnumber=secondNumber;
                    secondNumber="";
                }
            }

        }
        System.out.println(secondNumber);
    }

    public void sell(int buffer){
        String secondNumber="";
        for (int j = 0; j < lastBestBid.length(); j=j+1) {
            if (isNumeric(String.valueOf(lastBestBid.charAt(j)))){
                secondNumber=secondNumber+ String.valueOf(lastBestBid.charAt(j));
            }else {
                secondNumber="";
            }
        }
        int number =Integer.parseInt(secondNumber)-buffer;
      String change=lastBestBid;
      change=change.replace(secondNumber,String.valueOf(number));
      tableBid.set(tableBid.indexOf(lastBestBid),change);

    }
        public void buy(int buffer){
            String secondNumber="";
            for (int j = 0; j < lastBestAsk.length(); j=j+1) {
                if (isNumeric(String.valueOf(lastBestAsk.charAt(j)))){
                    secondNumber=secondNumber+ String.valueOf(lastBestAsk.charAt(j));
                }else {
                    secondNumber="";
                }
            }
             int number =Integer.parseInt(secondNumber)-buffer;
            String change=lastBestAsk;
            change=change.replace(secondNumber,String.valueOf(number));
            tableAsk.set(tableAsk.indexOf(lastBestAsk),change);
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    public String regex(){
        buffer = buffer.replaceAll("[^\\d]", " ");
        buffer = buffer.trim();
        buffer = buffer.replaceAll(" +", " ");
        return buffer;
    }
}