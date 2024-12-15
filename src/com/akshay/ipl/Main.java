package com.akshay.ipl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static com.akshay.ipl.Constants.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Matches> matchList = matchesData();
        List<Deliveries> deliveriesList = deliveriesData();
        HashMap<Integer, Integer> matchesPerYear = totalMatchesPlayedPerYear(matchList);
        HashMap<String, Integer> winPerTeam = totalMatchesWonPerTeam(matchList);
        HashMap<String, Integer> extraRuns = extraRunsConcedePerTeam2016(deliveriesList, matchList);
        TreeMap<String, Double> economicalBowler = topEconomicalBowler2015(deliveriesList, matchList);
        HashMap<String, Integer> topBatsmanOf2016 = batsmanRun2016(deliveriesList, matchList);

        System.out.println("1. Total matches played per year:");
        matchesPerYear.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(System.out::println);
        System.out.println();

        System.out.println("2. Total matches won per team from 2008 to 2017:");
        winPerTeam.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(System.out::println);
        System.out.println();

        System.out.println("3. Extra runs conceded per team in 2016: ");
        extraRuns.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(System.out::println);
        System.out.println();

        System.out.println("4. Top 5 economical bowlers for year 2015: ");
        economicalBowler.entrySet().stream().sorted(Map.Entry.comparingByValue()).limit(5).forEach(System.out::println);
        System.out.println();

        System.out.println("5. Top 5 run scorer batsman for year 2016: ");
        topBatsmanOf2016.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(5).forEach(System.out::println);
    }

    private static List<Matches> matchesData() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/archive/matches.csv"));
        List<Matches> matchList = new ArrayList<>();
        String line = br.readLine();
        int skip = 0;
        while(line != null){
            if(skip == 0){
                skip++;
                line = br.readLine();
                continue;
            }
            String[] data = line.split(",");
            Matches match = new Matches(data);
            matchList.add(match);
            line = br.readLine();
        }
        return matchList;
    }

    public static HashMap<Integer, Integer> totalMatchesPlayedPerYear(List<Matches> matchList){
        HashMap<Integer, Integer> matchesPlayedPerYear = new HashMap<>();
        for(Matches tempMatchList : matchList){
            int year = tempMatchList.getMatchYear();
            if(matchesPlayedPerYear.containsKey(year)){
                matchesPlayedPerYear.put(year, matchesPlayedPerYear.get(year)+1);
            }
            else matchesPlayedPerYear.put(year, 1);
        }
        return matchesPlayedPerYear;
    }

    public static HashMap<String, Integer> totalMatchesWonPerTeam(List<Matches> matchesList){
        HashMap<String, Integer> matchesWonPerTeam = new HashMap<>();
        for(Matches tempMatchList : matchesList){
            String winner = tempMatchList.getWinner();
            if(matchesWonPerTeam.containsKey(winner)){
                matchesWonPerTeam.put(winner, matchesWonPerTeam.get(winner)+1);
            }
            else matchesWonPerTeam.put(winner, 1);
        }
        return matchesWonPerTeam;
    }

    private static List<Deliveries> deliveriesData() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/archive/deliveries.csv"));
        List<Deliveries> deliveryList = new ArrayList<>();
        String line = br.readLine();
        int skip = 0;
        while(line != null){
            if(skip == 0){
                skip++;
                line = br.readLine();
                continue;
            }
            String[] deliveryData = line.split(",");
            Deliveries delivery = new Deliveries(deliveryData);
            deliveryList.add(delivery);
            line = br.readLine();
        }
        return deliveryList;
    }
    public static HashMap<String, Integer> extraRunsConcedePerTeam2016(List<Deliveries> deliveriesList, List<Matches> matchesList){
        HashMap<String, Integer> extraRunsConcedePerTeam = new HashMap<>();
        List<Integer> id = new ArrayList<>();
        for(Matches tempMatchList : matchesList){
            int year = tempMatchList.getMatchYear();
            if(year == YEAR){
                id.add(tempMatchList.getMatchId());
            }
        }
        for(Deliveries tempDeliveriesList : deliveriesList){
            int curId = tempDeliveriesList.getMatchId();
            if(id.contains(curId)){
                String bowler = tempDeliveriesList.getBowlingTeam();
                int extra = tempDeliveriesList.getExtraRuns();
                if(extraRunsConcedePerTeam.containsKey(bowler)){
                    extraRunsConcedePerTeam.put(bowler, extraRunsConcedePerTeam.get(bowler) + extra);
                }
                else extraRunsConcedePerTeam.put(bowler, extra);
            }
        }
        return extraRunsConcedePerTeam;
    }

    public static TreeMap<String, Double> topEconomicalBowler2015(List<Deliveries> deliveriesList, List<Matches> matchesList){
        List<Integer> matchesPlayedIn2015 = new ArrayList<>();
        for(Matches tempMatchList : matchesList){
            int year = tempMatchList.getMatchYear();
            if(year == PREVIOUS_YEAR){
                matchesPlayedIn2015.add(tempMatchList.getMatchId());
            }
        }
        TreeMap<String, Integer> bowlerAndRuns = new TreeMap<>();
        TreeMap<String, Integer> bowlerAndFrequency = new TreeMap<>();
        for(Deliveries tempDeliveriesList : deliveriesList){
            if(matchesPlayedIn2015.contains(tempDeliveriesList.getMatchId())){
                String bowler = tempDeliveriesList.getBowlerName();
                int runs = tempDeliveriesList.getTotalRuns();
                if(bowlerAndRuns.containsKey(bowler)){
                    bowlerAndRuns.put(bowler, bowlerAndRuns.get(bowler) + runs);
                    bowlerAndFrequency.put(bowler, bowlerAndFrequency.get(bowler) + 1);
                }
                else{
                    bowlerAndRuns.put(bowler, runs);
                    bowlerAndFrequency.put(bowler, 1);
                }
            }
        }
        List<Integer> balls = new ArrayList<>(bowlerAndFrequency.values());
        List<Double> overs = new ArrayList<>();
        for(Integer i : balls){
            double rem = i % OVER_BALL;
            double over = i / OVER_BALL + rem / OVER_BALL;
            overs.add(over);
        }
        int iterator = 0;
        TreeMap<String, Double> topEconomicalBowler = new TreeMap<>();
        for(Map.Entry<String, Integer> entry : bowlerAndRuns.entrySet()) {
            String name = entry.getKey();
            double runs = Math.round(entry.getValue() / overs.get(iterator++) *100.0) / 100.0;
            topEconomicalBowler.put(name, runs);
        }
        return topEconomicalBowler;
    }

    private static HashMap<String, Integer> batsmanRun2016(List<Deliveries> deliveriesList, List<Matches> matchesList){
        List<Integer> matchesPlayedIn2016 = new ArrayList<>();
        for(Matches tempMatchList : matchesList){
            int year = tempMatchList.getMatchYear();
            if(year == YEAR){
                matchesPlayedIn2016.add(tempMatchList.getMatchId());
            }
        }
        HashMap<String, Integer> topBatsman2016 = new HashMap<>();
        for(Deliveries tempDeliveryList : deliveriesList){
            if(matchesPlayedIn2016.contains(tempDeliveryList.getMatchId())){
                String batsMan = tempDeliveryList.getBatsmanName();
                int run = tempDeliveryList.getBatsmanRun();
                if(topBatsman2016.containsKey(batsMan)){
                    topBatsman2016.put(batsMan, topBatsman2016.get(batsMan) + run);
                }
                else {
                    topBatsman2016.put(batsMan, run);
                }
            }
        }
        return topBatsman2016;
    }
}
