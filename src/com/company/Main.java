package com.company;

import java.util.Random;

public class Main {

    public static int[] heroesHealth = {300, 280, 250, 200, 1000, 290};
    public static int[] heroesDamage = {20, 15, 25, 20, 10, 30};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Lucky", "Golem", "Thor"};
    public static int bossHealth = 3800;
    public static int bossDamage = 50;
    public static String bossDefenceType = "";
    public static int healerHealth = 410;
    public static int roundNumber = 0;
    public static int aliveHeroes;

    public static int thorWillHit(){
        Random hit = new Random();
        boolean hitThor = hit.nextBoolean();
        if (hitThor){
            return 1;
        }
        else return 0;
    }
    public static void medicAlive(){
        if (!healerIsDead()){
            aliveHeroes = 1;
        }else aliveHeroes = 0;
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefenceType = heroesAttackType[randomIndex];
        System.out.println("Boss chose: " + bossDefenceType);
    }

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void round() {
        roundNumber++;
        System.out.println("Thor ability = " + thorWillHit());
        medicAlive();
        chooseBossDefence();
        bossHits();
        heroesHit();
        healerHits();
        printStatistics();
        thorWillHit();
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                aliveHeroes = aliveHeroes + 1;
            }
        }
        if (heroesAttackType[3] == bossDefenceType) {

            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0) {
                    if (heroesHealth[i] < bossDamage) {
                        heroesHealth[i] = 0;
                    } else {
                        heroesHealth[i] = heroesHealth[i] - bossDamage;
                    }
                }
            }
            if (heroesHealth[3] > 0) {
                heroesHealth[3] = heroesHealth[3] + bossDamage;
            }
        }
        else if (heroesAttackType[4] == bossDefenceType) {
            heroesHealth[4] = heroesHealth[4] +(bossDamage/5*4) - (bossDamage + ((bossDamage / 5) * 1 * (aliveHeroes -1)));
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i]>0 && heroesHealth[i] > bossDamage/5*4) {

                    heroesHealth[i] = heroesHealth[i] - ((bossDamage / 5) * 4);
                }
                else if (heroesHealth[i] <= bossDamage/5*4){
                    heroesHealth[i] = 0;

                }
            }
        }
        else for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0) {
                    if (heroesHealth[i] < bossDamage) {
                        heroesHealth[i] = 0;
                    } else {
                        heroesHealth[i] = heroesHealth[i] - bossDamage;
                    }
                }
            }

        if (healerHealth > 0) {
            if (healerHealth <= bossDamage) {
                healerHealth = 0;
            } else {
                healerHealth = healerHealth - bossDamage;
            }
        }
        if (thorWillHit()==1 && heroesHealth[5]>0) {
            bossDamage = 0;
        }else bossDamage= 50;

    }

    public static boolean healerIsDead() {
        if (healerHealth <= 0) {
            return true;
        } else return false;

    }

    public static void healerHits() {
        int healerHit = (int) (Math.random() * (100)+1);

        for (int i = 0; i < heroesHealth.length; i++) {
            if (healerIsDead()) {
                break;
            } else if (heroesHealth[i] <= 0) {
                continue;
            } else if (heroesHealth[i] < 100 && heroesHealth [i]>0) {
                heroesHealth[i] = heroesHealth[i] + healerHit;
                System.out.println("Medic will heal " + heroesAttackType[i] + " in the next round for " + healerHit + " health points");
                break;
            }

        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (bossDefenceType == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(8) + 2; // 2,3,4,5,6,7,8,9
                    if (bossHealth < heroesDamage[i] * coeff) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth < heroesDamage[i]) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }


    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 ) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead ) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println(roundNumber + " ROUND ----------------------");
        System.out.println("Boss health: " + bossHealth + " [" + bossDamage + "]");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " [" + heroesDamage[i] + "]");
        }
        if (!healerIsDead()) {
            System.out.println("Medic health: " + healerHealth);
        } else System.out.println("Medic health: " + healerHealth);
        System.out.println();
    }
}